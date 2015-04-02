/*
	SYR2 - Projet - Partie 2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

#include <clientserver.h>


/**
 * If an error is encountered, display it on the server
 * And also send a message to the client
 *
 * Parameters:
 *	 - int err_socket  => The file descriptor to the server socket
 *	 - int err_type  => The error type
 *	 - char* err_message  => The error message
 *   - struct sockaddr* err_destination  => The destination address (client)
 *   - socklen_t destination_length => The length of a sockaddr structure
 *   - int* s_state  => The state of the server
 *
 */
void server_error_encountered(int err_socket, int err_type, char* err_message, struct sockaddr* err_destination, int* s_state) {
	// Display the error encountered
	perror(err_message);

	// Some parameters
	socklen_t destination_length = (socklen_t)sizeof(struct sockaddr);

	// Create the packet to send
	struct packet error_packet;
	create_packet(&error_packet, err_type, err_message);

	// Send an error packet
	if (sendto(err_socket, &error_packet, sizeof(struct packet), 0, err_destination, destination_length) == -1)
		perror("Error during the sending of the error packet.");

	// Then put the server free
	if (s_state != NULL)
		*s_state = S_FREE;
}


/**
 * Initialize the socket 
 *
 * Return:
 *	 - int  => The socket file descriptor or -1 if error
 */
int init_socket() {

	// Create the server socket
	int my_socket = socket(AF_INET, SOCK_DGRAM, 0);

	// If error
	if (my_socket == -1) { perror("Error during the creation of the server socket"); exit(1); }

	// The structure containing the port number
	struct sockaddr_in addr;
	addr.sin_family = AF_INET;
	addr.sin_port = htons(SERVER_PORT);
	addr.sin_addr.s_addr = htonl(INADDR_ANY);

	// Bind the port
	if (bind(my_socket, (struct sockaddr *)&addr, sizeof(struct sockaddr_in)) == -1) { perror("Error during the server socket's bind"); exit(1); }

	// Then return the socket
	return my_socket;
}


/**
 * The main program
 *
 * Parameters:
 *	 - int argc  => The number of arguments passed
 *	 - char** args  => The arguments list
 *
 * Return:
 *	 - int  => The result of the execution
 */
int main(int argc, char** args) {

	/* 	################################################## Initialisations ################################################## */
	// If there are too many arguments
	if (argc > 1) { perror("There are too many arguments. This program requires none"); return 1; }

	
	/* ##### Network structures ##### */
	// Socket creation and bind
	int server_socket = init_socket();

	// The variable to store datas received
	struct packet packet_received;
	struct packet packet_to_send;
	char buffer[BUFFER_SIZE];

	// The structure to store the source informations
	struct sockaddr_in source;
	socklen_t source_length = (socklen_t)sizeof(struct sockaddr);

	// To know the current state of the server
	int server_state = S_FREE;


	/* ##### Audio reader parameters ##### */
	// Some more variables that we'll need to read the audio file
	int sample_rate, sample_size, channels;
	int read_audio, read_init_audio = 0;


	/* ##### Timeout parameters ##### */
	int nb;
	fd_set watch_over;
	struct timeval timeout;



	/* 	################################################## Put the timeout ################################################## */

	// Clear and initialize the fd set
	FD_ZERO(&watch_over);
	FD_SET(server_socket, &watch_over);
	timeout.tv_sec = TIMEOUT_SERVER;  // 5 sec
	timeout.tv_usec = 0;
	nb = select(server_socket+1, &watch_over, NULL, NULL, &timeout);



	/* 	################################################## Serve clients ################################################## */
	while (1) {  // Server always running

		// Clear packets
		clear_packet(&packet_to_send);
		clear_packet(&packet_received);

		// If error during the select
		if (nb < 0) {
			perror("Can't attach the select to the file descriptor");
			return 1;
		}

		// Just consider the client is gone if timeout reached
		if (nb == 0) {
			server_state = S_FREE;
		}

		// If open, just act normally
		if (FD_ISSET(server_socket, &watch_over)) {

			// Wait a packet
			if (recvfrom(server_socket, &packet_received, sizeof(struct packet), 0, (struct sockaddr*)&source, &source_length) != -1) {

				// In function of the type of the packet received
				switch (packet_received.type) {

					// --------------- Receiving the filename ---------------
					case P_FILENAME:

						// If the server is busy, refuse the client asking for a file (it's the first packet sent)
						if (server_state == S_BUSY)
							server_error_encountered(server_socket, P_SERVER_ERROR, "Server busy for the moment. Please try later", (struct sockaddr*)&source, NULL);

						// If not busy, then put this client as the current
						else {
							
							// Put the server busy
							server_state = S_BUSY;

							// Initialize by getting informations about the music to play
							read_init_audio = aud_readinit(packet_received.message, &sample_rate, &sample_size, &channels);

							// If an error happened (maybe the file doesn't exist)
							if (read_init_audio == -1)
								server_error_encountered(server_socket, P_SERVER_ERROR, "Error at opening the audio file, the file requested may be inexistant", (struct sockaddr*)&source, &server_state);

							// If none
							else {

								// Store informations about this file
								snprintf(buffer, sizeof(buffer), "%d %d %d", sample_rate, sample_size, channels);

								// Create the packet to send
								create_packet(&packet_to_send, P_FILE_HEADER, buffer);

								// Send it
								if (sendto(server_socket, &packet_to_send, sizeof(struct packet), 0, (struct sockaddr*)&source, source_length) == -1)
									server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Error at sending the file header", (struct sockaddr*)&source, &server_state);
							}
						}
						break;


					// --------------- Client requesting another block ---------------
					case P_REQ_NEXT_BLOCK:

						// Fill the buffer
						read_audio = read(read_init_audio, buffer, BUFFER_SIZE);

						// If the end of file is reached
						int type = P_BLOCK;
						if (read_audio != BUFFER_SIZE)
							type = P_EOF;

						// Create the packet to send
						create_packet(&packet_to_send, type, buffer);

						// And send it
						if (sendto(server_socket, &packet_to_send, sizeof(struct packet), 0, (struct sockaddr*)&source, source_length) == -1)
							server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Error at sending the next block", (struct sockaddr*)&source, &server_state);

						break;


					// --------------- Client requesting the same packet (if it doesn't received it) ---------------
					case P_REQ_SAME_PACKET:

						// Resend packet previously created
						if (sendto(server_socket, &packet_to_send, sizeof(struct packet), 0, (struct sockaddr*)&source, source_length) == -1)
							server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Error at sending the same block", (struct sockaddr*)&source, &server_state);

						break;


					// --------------- Client received correctly the close transmission ---------------
					case P_CLOSED:

						// Close the descriptor file when it's done
						if ((read_init_audio > 0) && (close(read_init_audio) != 0)) perror("Error at closing the read file descriptor");

						// Free the server
						server_state = S_FREE;

						break;
				}
			}

			// If an error during the receiving of a packet
			else
				server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Error during the receiving of a packet", (struct sockaddr*)&source, &server_state);

		}

	}

	// Then close the socket
	if (close(server_socket) == -1) { perror("Error during the closing of the server socket"); return 1; }

	// If everything's was ok
	return 0;
}
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
 *	 - int argc  => The number of arguments passed
 *	 - char** args  => The arguments list
 *
 * Return:
 *	 - int  => The result of the execution
 */
void server_error_encountered(int err_socket, int err_type, char* err_message, struct sockaddr* err_destination, socklen_t destination_length) {
	// Display the error encountered
	perror(err_message);

	// Create the packet to send
	struct packet error_packet;
	create_packet(&error_packet, err_type, err_message);

	// Send an error packet
	if (sendto(err_socket, &error_packet, sizeof(struct packet), 0, err_destination, destination_length) == -1)
		perror("Error during the sending of the error packet. We can ");
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

	// Socket creation and bind
	int server_socket = init_socket();

	// The variable to store datas received
	struct packet packet_received;
	struct packet packet_to_send;
	char buffer[BUFFER_SIZE];
	struct sockaddr_in source;
	struct sockaddr_in current_client;
	socklen_t source_length = (socklen_t)sizeof(struct sockaddr);

	// Some more variables that we'll need
	int sample_rate, sample_size, channels;
	int read_init_audio, read_audio;

	// Initialize the two structures for the adresses
	memset(&current_client, 0, sizeof(struct sockaddr_in));
	memset(&source, 1, sizeof(struct sockaddr_in));



	/* 	################################################## Serve clients ################################################## */
	while (1) {  // Server always running

		// Reinitialize variables
		clear_packet(&packet_to_send);
		clear_packet(&packet_received);

		// Wait a packet
		if (recvfrom(server_socket, &packet_received, sizeof(struct packet), 0, (struct sockaddr *)&source, &source_length) != -1) {

			// If the server is busy
			if (memcmp(&source, &current_client, sizeof(struct sockaddr_in)) == 0)
				server_error_encountered(server_socket, P_SERVER_ERROR, "Server busy for the moment. Please try later", (struct sockaddr*)&source, source_length);

			else {
				// In function of the type of the packet received
				switch (packet_received.type) {

					// --------------- Receiving the filename ---------------
					case P_FILENAME:

						// Put the server busy
						current_client = source;

						// Initialize by getting informations about the music to play
						read_init_audio = aud_readinit(packet_received.message, &sample_rate, &sample_size, &channels);

						// If an error happened (maybe the file doesn't exist)
						if (read_init_audio == -1) {

							// Send an error message and close connection
							server_error_encountered(server_socket, P_SERVER_ERROR, "Error at opening the audio file, the file requested may be inexistant", (struct sockaddr*)&source, source_length);

							// Free the server
							read_audio = read(read_init_audio, buffer, sample_size);
						}

						// If none
						else {
							// Store informations about this file
							char* sample_rate_emplacement = buffer + sizeof(int);
							char* sample_size_emplacement = buffer + 2*sizeof(int);
							char* channels_emplacement = buffer + 3*sizeof(int);
							*sample_rate_emplacement = sample_rate;
							*sample_size_emplacement = sample_size;
							*channels_emplacement = channels;

							// Create the packet to send
							create_packet(&packet_to_send, P_FILE_HEADER, buffer);

							// Send it
							if (sendto(server_socket, &packet_to_send, sizeof(struct packet), 0, (struct sockaddr*)&source_length, source_length) == -1) {

								// Send an error message and close connection
								server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Error at sending the file header", (struct sockaddr*)&source, source_length);

								// Free the server
								memset(&current_client, 0, sizeof(struct sockaddr_in));
							}
						}
						break;


					// --------------- Client requesting another block ---------------
					case P_REQ_NEXT_BLOCK:

						// Simply read each sample of the audio file
						read_audio = read(read_init_audio, buffer, sample_size);

						// If the EOF is encountered
						int type = P_BLOCK;
						if (read_audio != sample_size)
							type = P_EOF;

						// Create the packet to send
						create_packet(&packet_to_send, type, buffer);

						// And send it
						if (sendto(server_socket, &packet_to_send, sizeof(struct packet), 0, (struct sockaddr*)&source_length, source_length) == -1) {

							// Send an error message and close transmission
							server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Error at sending the next block", (struct sockaddr*)&source, source_length);

							// Free the server
							read_audio = read(read_init_audio, buffer, sample_size);
						}
						break;


					// --------------- Client requesting the same block ---------------
					case P_REQ_SAME_BLOCK:

						// Resend packet previously created
						if (sendto(server_socket, &packet_to_send, sizeof(struct packet), 0, (struct sockaddr*)&source_length, source_length) == -1) {

							// Send an error message and close transmission
							server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Error at sending the same block", (struct sockaddr*)&source, source_length);

							// Free the server
							read_audio = read(read_init_audio, buffer, sample_size);
						}
						break;


					// --------------- Client received correctly the close transmission ---------------
					case P_CLOSED:

						// Free the server
						read_audio = read(read_init_audio, buffer, sample_size);

						break;
				}
			}
		}

		// If an error during the receiving of a packet
		else
			server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Filename was requested but another packet received", (struct sockaddr*)&source, source_length);

	}

	// Then close the socket
	if (close(server_socket) == -1) { perror("Error during the closing of the server socket"); return 1; }  // Think about this ...

	// If everything's was ok
	return 0;
}
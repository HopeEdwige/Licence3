/*
	SYR2 - Projet - Partie 2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

#include <clientserver.h>


/**
 * 
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
	struct sockaddr_in source;
	socklen_t source_length = (socklen_t)sizeof(struct sockaddr);

	// Some more variables that we'll need
	int server_state = SERVER_FREE;
	int sample_rate, sample_size, channels;
	int read_init_audio;



	/* 	################################################## Server clients ################################################## */
	do {

		// Reinitialize variables
		clear_packet(&packet_received);

		// Wait a packet
		if (recvfrom(server_socket, &packet_received, sizeof(struct packet), 0, (struct sockaddr *)&source, &source_length) != -1) {

			// In function of the type of the packet received
			switch (packet_received.type) {

				// Receiving the filename
				case P_FILENAME:

					// If the server isn't busy
					if (server_state == SERVER_FREE) {

						// Put the server busy
						server_state = SERVER_BUSY;

						// Initialize by getting informations about the music to play
						read_init_audio = aud_readinit(packet_received.message, &sample_rate, &sample_size, &channels);

						// If an error happened (maybe the file doesn't exist)
						if (read_init_audio == -1)
							server_error_encountered(server_socket, P_SERVER_ERROR, "Error at opening the audio file, the file requested may be inexistant", (struct sockaddr*)&source, source_length);
					}

					//If it is
					else 
						server_error_encountered(server_socket, P_SERVER_ERROR, "Server busy for the moment. Please try later", (struct sockaddr*)&source, source_length);
					break;
			}
		}

		// If an error during the receiving of a packet
		else
			server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Filename was requested but another packet received", (struct sockaddr*)&source, source_length);

	} while (packet_received.type != P_CLOSE_SERVER);


	// Then close the socket if a client asked to
	if (close(server_socket) == -1) { perror("Error during the closing of the server socket"); return 1; }

	// If everything's was ok
	return 0;
}
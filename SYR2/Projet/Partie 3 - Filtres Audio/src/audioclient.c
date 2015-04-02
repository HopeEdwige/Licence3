/*
	SYR2 - Projet - Partie 2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

#include <clientserver.h>


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
int process_params(int argc, char** args) {
	// The filter
	int filter = F_NONE;

	// The basic command syntax message
	char command_syntax[BUFFER_SIZE];
	strncpy(command_syntax, COMMAND_SYNTAX, BUFFER_SIZE);

	// Check the parameters (between 2 and 4 arguments)
	if ((args[1] == NULL) || (args[2] == NULL) || (argc < 3) || (argc > 5)) {
		fprintf(stderr, "%s\n", command_syntax);
		exit(1);
	}

	// If there's a filter asked
	if (args[3] != NULL) {

		// If mono
		if ((strncmp(args[3], F_MONO_NAME, strlen(F_MONO_NAME)) == 0) && (strlen(args[3]) == strlen(F_MONO_NAME)))
			filter = F_MONO;

		// If with echo
		else if ((strncmp(args[3], F_ECHO_NAME, strlen(F_ECHO_NAME)) == 0) && (strlen(args[3]) == strlen(F_ECHO_NAME)))
			filter = F_ECHO;

		// If an unknown filter
		else {

			// Add the informations about the commands
			strcat(command_syntax, "\n	- ");
			strcat(command_syntax, F_MONO_NAME);
			strcat(command_syntax, "\n	- ");
			strcat(command_syntax, F_ECHO_NAME);
			strcat(command_syntax, "\n	- ");
			strcat(command_syntax, F_UP_NAME);
			strcat(command_syntax, " ");
			strcat(command_syntax, F_UP_DOWN_PARAMETER_NAME);
			strcat(command_syntax, "\n	- ");
			strcat(command_syntax, F_DOWN_NAME);
			strcat(command_syntax, " ");
			strcat(command_syntax, F_UP_DOWN_PARAMETER_NAME);

			// Display it and quit
			fprintf(stderr, "%s\n", command_syntax);
			exit(1);
		}
	}

	// Return the filter
	return filter;
}


/**
 * If an error is encountered, close the connection
 *
 * Parameters:
 *	 - int err_socket  => The file descriptor to the server socket
 *   - char* err_message  => The error message
 *	 - int audio_fd  => The audio file descriptor (if 0, none)
 *
 */
void close_connection(int err_socket, char* err_message, int audio_fd) {

	// Display error message
	perror(err_message);

	// Then close the socket
	if (close(err_socket) == -1) perror("Error during the closing of the client socket");

	// And close the audio file descriptor (audio_fd == 0 if none so no need to close)
	if ((audio_fd != 0) && (close(audio_fd) == -1)) perror("Error during the closing of the audio file descriptor");

	// Close client
	exit(1);
}


/**
 * Initialize the socket 
 *
 * Parameters:
 *	 - char* host  => The name of the host to resolve
 *	 - (struct sockaddr_in*)destination  => The sockaddr for the destination
 *
 * Return:
 *	 - int  => The socket file descriptor or -1 if error
 */
int init_socket(char* host, struct sockaddr_in* destination) {

	/* 	################################################## Name Resolution ################################################## */
	// Structure for the name resolution
	struct hostent* name_resolution;

	// Resolve the ip address here
	name_resolution = gethostbyname(host);

	// If error
	if (name_resolution == NULL) { perror("Error during the name resolution"); exit(1); }

	// Get the ip address (first of the list)
	struct in_addr* ip_addr = (struct in_addr*)name_resolution->h_addr_list[0];



	/* 	################################################## Socket creation and destination configuration ################################################## */
	// Create the client socket
	int client_socket = socket(AF_INET, SOCK_DGRAM, 0);

	// If error
	if (client_socket == -1) { perror("Error during the creation of the client socket"); exit(1); }

	// The structure for informations about the destination
	destination->sin_family = AF_INET;
	destination->sin_port = htons(SERVER_PORT);
	destination->sin_addr = *ip_addr;

	// And in the end return the client socket
	return client_socket;
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

	/* ##### Process parameters and get filter ##### */
	int filter = process_params(argc, args);


	/* ##### Network structures ##### */
	// Initialize the client socket
	struct sockaddr_in destination;
	int client_socket = init_socket(args[1], &destination);

	// Parameters for the packet transmission
	struct packet to_server;
	struct packet from_server;
	socklen_t destination_length = (socklen_t)sizeof(struct sockaddr);


	/* ##### Audio reader parameters ##### */
	// Some more variables that we'll need for reading audio files
	int sample_rate, sample_size, channels;
	int write_audio, write_init_audio = 0;
	char echo_buffer[BUFFER_SIZE];


	/* ##### Timeout parameters ##### */
	int nb;
	fd_set watch_over;
	struct timeval timeout;



	/* 	################################################## Sending the filename ################################################## */

	// The first packet to send is the filename
	create_packet(&to_server, P_FILENAME, args[2]);

	// Send the packet containing the filename
	if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1) {
		perror("Error during the sending of the filename packet");
		return 1;
	}



	/* 	################################################## Talk with the server ################################################## */
	do {

		// Clear and reinitialize the fd set
		FD_ZERO(&watch_over);
		FD_SET(client_socket, &watch_over);
		timeout.tv_sec = 0;
		timeout.tv_usec = TIMEOUT_CLIENT;  // 200ms
		nb = select(client_socket+1, &watch_over, NULL, NULL, &timeout);

		// Clear packets
		clear_packet(&to_server);
		clear_packet(&from_server);

		// If error during the select
		if (nb < 0) {
			perror("Can't attach the select to the file descriptor");
			return 1;
		}

		// Just request the same packet if timeout reached
		if (nb == 0) {
			to_server.type = P_REQ_SAME_PACKET;
			if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1) {
				perror("Can't request same packet");
				return 1;
			}
		}

		// If open, just act normally
		if (FD_ISSET(client_socket, &watch_over)) {

			// Wait a packet
			if (recvfrom(client_socket, &from_server, sizeof(struct packet), 0, (struct sockaddr *)&destination, &destination_length) != -1) {

				// In function of the type of the packet received
				switch (from_server.type) {

					// --------------- An error happened on the server ---------------
					case P_SERVER_ERROR:

						// Display the error
						printf("%s\n", from_server.message);

						// Close connection
						close_connection(client_socket, "Closing due to server error", 0);
						break;


					// --------------- An error happened on the server ---------------
					case P_ERR_TRANSMISSION:

						// Display the error
						printf("%s\n", from_server.message);

						// Close connection
						close_connection(client_socket, "Closing due to server error", 0);
						break;


					// --------------- An error happened on the server ---------------
					case P_FILE_HEADER:

						// To avoid an error saying that we can't put declaration just after this label
						;

						// Get the audio parameters by cutting the message received
						char *token = strtok(from_server.message, " ");
						int i = 0;
						while ((token != NULL) && (i < 3)) {
							switch (i) {

								// First is the sample rate
								case 0:
									sample_rate = atoi(token);
									break;

								// Second is the sample size
								case 1:
									sample_size = atoi(token);
									break;

								// Third is the number of channels
								case 2:

									// If mono forced
									if (filter == F_MONO)
										channels = 1;

									// If normal behavior
									else
										channels = atoi(token);
									break;
							}

							token = strtok(NULL, " ");
							i++;
						}

						// Initialize the write end
						write_init_audio = aud_writeinit(sample_rate, sample_size, channels);

						// If an error happened
						if (write_init_audio < 1) close_connection(client_socket, "Error at getting the audio output device", 0);

						// If everything's ok, request the first block
						clear_packet(&to_server);
						to_server.type = P_REQ_NEXT_BLOCK;

						// Send the request
						if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1)
							close_connection(client_socket, "Error at requesting the first block", write_init_audio);

						break;


					// --------------- A block is received, read it ---------------
					case P_BLOCK:

						// Read the music on the audio output
						write_audio = write(write_init_audio, from_server.message, BUFFER_SIZE);

						// If error during the reading
						if (write_audio == -1) close_connection(client_socket, "Error at writing a block on audio output", write_init_audio);

						// If echo filter is activated, read the echo and store the last value
						if (filter == F_ECHO) {

							// Read the content of the echo buffer
							if (write(write_init_audio, echo_buffer, BUFFER_SIZE) == -1)
								close_connection(client_socket, "Error at writing an echo block on audio output", write_init_audio);

							// Then clear it and store the current buffer
							bzero(echo_buffer, BUFFER_SIZE);
							memcpy(echo_buffer, from_server.message, BUFFER_SIZE);
						}

						// If everything's ok, request the next block
						clear_packet(&to_server);
						to_server.type = P_REQ_NEXT_BLOCK;

						// Send the request
						if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1)
							close_connection(client_socket, "Error at requesting a block", write_init_audio);

						break;


					// --------------- The last block is received, read it ---------------
					case P_EOF:

						// Read the audio file
						write_audio = write(write_init_audio, from_server.message, BUFFER_SIZE);

						// If error during the reading
						if (write_audio == -1) close_connection(client_socket, "Error at writing a block", write_init_audio);

						// If everything's ok, request the next block
						clear_packet(&to_server);

						// Close the connection
						close_connection(client_socket, "The file was correctly read, close the connection, bye", write_init_audio);
						break;
					
				}
			}
		}

		// If an error during the receiving of a packet
		else {
			perror("Error during the receiving of a packet, the server may be busy");
			return 0;
		}

	} while (from_server.type != P_CLOSE_TRANSMISSION);

	// Close the connection
	close_connection(client_socket, "The reading is done, close connection now", write_init_audio);

	// If everything's was ok (but the server is normally just waiting for clients)
	return 0;
}
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

		// If volume modification
		else if ((strncmp(args[3], F_VOLUME_NAME, strlen(F_VOLUME_NAME)) == 0) && (strlen(args[3]) == strlen(F_VOLUME_NAME))) {

			filter = F_VOLUME;

			// Check the upper parameter
			if ((args[4] == NULL) || (atoi(args[4]) < F_VOLUME_PARAMETER_MIN) || (atoi(args[4]) > F_VOLUME_PARAMETER_MAX)) {
				strcat(command_syntax, " / The filter parameter passed isn't correct");
				fprintf(stderr, "%s must be between %d and %d\n", command_syntax, F_VOLUME_PARAMETER_MIN, F_VOLUME_PARAMETER_MAX);
				exit(1);
			}
		}

		// If speed modification
		else if ((strncmp(args[3], F_SPEED_NAME, strlen(F_SPEED_NAME)) == 0) && (strlen(args[3]) == strlen(F_SPEED_NAME))) {

			filter = F_SPEED;

			// Check the upper parameter
			if ((args[4] == NULL) || (atoi(args[4]) < F_SPEED_PARAMETER_MIN) || (atoi(args[4]) > F_SPEED_PARAMETER_MAX)) {
				strcat(command_syntax, " / The filter parameter passed isn't correct");
				fprintf(stderr, "%s must be between %d and %d\n", command_syntax, F_SPEED_PARAMETER_MIN, F_SPEED_PARAMETER_MAX);
				exit(1);
			}
		}

		// If an unknown filter
		else {

			// Add the informations about the commands
			strcat(command_syntax, "\n	- ");
			strcat(command_syntax, F_MONO_NAME);
			strcat(command_syntax, "\n	- ");
			strcat(command_syntax, F_ECHO_NAME);
			strcat(command_syntax, "\n	- ");
			strcat(command_syntax, F_VOLUME_NAME);
			strcat(command_syntax, " ");
			strcat(command_syntax, F_VOLUME_PARAMETER_NAME);
			strcat(command_syntax, "\n	- ");
			strcat(command_syntax, F_SPEED_NAME);
			strcat(command_syntax, " ");
			strcat(command_syntax, F_SPEED_PARAMETER_NAME);

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
	int write_init_audio = 0;


	/* ##### Timeout parameters ##### */
	int nb;
	fd_set watch_over;
	struct timeval timeout;


	/* ##### Filter parameters ##### */
	// For volume filter
	int volume_value;

	// For echo filter
	int nb_buffers_per_echo = 0, nb_samples_per_buffer, current_buffer_position, to_read_position;
	char* echo_buffer;
	char volume_buffer[BUFFER_SIZE];



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
			if (recvfrom(client_socket, &from_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, &destination_length) != -1) {

				// In function of the type of the packet received
				switch (from_server.type) {

					// --------------- An server error happened on the server ---------------
					case P_ERR_TRANSMISSION:
					case P_SERVER_ERROR:

						// Display the error
						printf("%s\n", from_server.message);

						// Close connection
						close_connection(client_socket, "Closing due to server error", 0);
						break;


					// --------------- The first response from the server is received ---------------
					case P_FILE_HEADER:

						// Get the informations about the audio file
						sample_rate = *((int*)(from_server.message));
						sample_size = *((int*)(from_server.message + BUFFER_SPACE));
						channels = *((int*)(from_server.message + 2*BUFFER_SPACE));


						// ----- Filters initialisation -----
						switch (filter) {

							// If none do nothing
							case F_NONE:
								break;

							// If mono, just force the channel to one
							case F_MONO:
								channels = 1;
								break;

							// If volume, get the value of the filter parameter
							case F_VOLUME:
								volume_value = atoi(args[4]);
								nb_samples_per_buffer = BUFFER_SIZE/(sample_size/8);
								break;

							// If echo, allocate the echo buffer
							case F_ECHO:

								// Calculate the number of bytes needed for one second
								nb_buffers_per_echo = (ECHO_IN_SCDS * sample_rate * (sample_size/8)) / BUFFER_SIZE;

								// Allocate the table of buffers
								echo_buffer = malloc(nb_buffers_per_echo * BUFFER_SIZE);

								// The buffer position and the buffer to read put to 0
								current_buffer_position = 0;
								to_read_position = 0;
								break;

							// If speed
							case F_SPEED:

								// Multiply the framerate
								sample_rate = sample_rate * atoi(args[4]);
								break;

							// If unknown
							default:
								close_connection(client_socket, "Unknown filter", write_init_audio);
								break;
						}


						// Initialize the write end
						write_init_audio = aud_writeinit(sample_rate, sample_size, channels);

						// If an error happened
						if (write_init_audio < 1) {

							// If echo filter, free the buffer
							if (filter == F_ECHO) free(echo_buffer);

							// Close the connection
							close_connection(client_socket, "Error at getting the audio output device", 0);
						}

						// If everything's ok, request the first block
						clear_packet(&to_server);
						to_server.type = P_REQ_NEXT_BLOCK;

						// Send the request
						if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1) {

							// If echo filter, free the buffer
							if (filter == F_ECHO) free(echo_buffer);

							// Close the connection
							close_connection(client_socket, "Error at requesting the first block", write_init_audio);
						}

						break;


					// --------------- A block is received, read it ---------------
					case P_BLOCK:

						// Read the music on the audio output (in function of the filter passed)
						switch (filter) {

							// If none, mono or speed
							case F_NONE:
							case F_MONO:
							case F_SPEED:
								if (write(write_init_audio, from_server.message, BUFFER_SIZE) == -1)
									close_connection(client_socket, "Error at writing a block on audio output", write_init_audio);
								break;

							// If echo
							case F_ECHO:

								// Read the content of the buffer received
								if (write(write_init_audio, from_server.message, BUFFER_SIZE) == -1) {

									// Echo filter so free the buffer
									free(echo_buffer);

									// Close connection
									close_connection(client_socket, "Error at writing a block on audio output", write_init_audio);
								}

								// Then put this buffer into the echo buffer if the buffer isn't full
								if (current_buffer_position < nb_buffers_per_echo) {

									memcpy((echo_buffer + current_buffer_position*BUFFER_SIZE), from_server.message, BUFFER_SIZE);
									++current_buffer_position;
								}

								// If the echo buffer is full
								else {

									// Read the current buffer position
									if (write(write_init_audio, (echo_buffer + to_read_position*BUFFER_SIZE), BUFFER_SIZE) == -1) {

										// Echo filter so free the buffer
										free(echo_buffer);

										// Close connection
										close_connection(client_socket, "Error at writing an echo block on audio output", write_init_audio);
									}

									// And replace it with the new one
									memcpy((echo_buffer + to_read_position*BUFFER_SIZE), from_server.message, BUFFER_SIZE);

									// Increment the flag
									to_read_position = (to_read_position+1)%nb_buffers_per_echo;

								}
								break;

							// If upper or lower volume
							case F_VOLUME:

								// Clear the temporary buffer
								memset(volume_buffer, 0, BUFFER_SIZE);

								// Variables used in the loop
								int i;  // The increment var and a temporary value
								int tmp;  // Temporary var

								// Get each sample and multiply its value
								for (i = 0; i < nb_samples_per_buffer; ++i) {

									// Multiply the value of the sample, get a double value
									tmp = *((int*)(from_server.message + i*sizeof(int))) * volume_value;

									// Then store it in the temporary buffer
									*((int*)(volume_buffer + i*sizeof(int))) = tmp;
								}

								// And in the end, read the whole buffer
								if (write(write_init_audio, volume_buffer, BUFFER_SIZE) == -1)
									close_connection(client_socket, "Error at writing a volume changed block on audio output", write_init_audio);

								break;

							// If an unknown filter, error!
							default:
								close_connection(client_socket, "Filter passed unknown", write_init_audio);
								break;

						}  // End of filter's switch

						// If everything's ok, request the next block
						clear_packet(&to_server);
						to_server.type = P_REQ_NEXT_BLOCK;

						// Send the request
						if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1) {

							// If echo filter, free the buffer
							if (filter == F_ECHO) free(echo_buffer);

							// Close connection
							close_connection(client_socket, "Error at requesting a block", write_init_audio);
						}

						break;


					// --------------- The last block is received, read it ---------------
					case P_EOF:

						// Read the music on the audio output (in function of the filter passed)
						switch (filter) {

							// If none, mono or speed
							case F_NONE:
							case F_MONO:
							case F_SPEED:
								if (write(write_init_audio, from_server.message, BUFFER_SIZE) == -1)
									close_connection(client_socket, "Error at writing a block on audio output", write_init_audio);
								break;

							// If echo
							case F_ECHO:

								// Read the content of the buffer received
								if (write(write_init_audio, from_server.message, BUFFER_SIZE) == -1) {

									// Echo filter so free the buffer
									free(echo_buffer);

									// Close connection
									close_connection(client_socket, "Error at writing a block on audio output", write_init_audio);
								}

								// And in the end, just read the whole echo buffer left
								if (write(write_init_audio, (echo_buffer + to_read_position*BUFFER_SIZE), ((current_buffer_position - to_read_position)*BUFFER_SIZE)) == -1) {

									// Echo filter so free the buffer
									free(echo_buffer);

									// Close connection
									close_connection(client_socket, "Error at writing the last echo block on audio output", write_init_audio);
								}

								// Free the echo_buffer in the end
								free(echo_buffer);

								break;

							// If upper or lower volume
							case F_VOLUME:

								// Clear the temporary buffer
								memset(volume_buffer, 0, BUFFER_SIZE);

								// Variables used in the loop
								int i;  // The increment var and a temporary value
								int tmp;  // Temporary var

								// Get each sample and multiply its value
								for (i = 0; i < nb_samples_per_buffer; ++i) {

									// Multiply the value of the sample, get a double value
									tmp = *((int*)(from_server.message + i*sizeof(int))) * volume_value;

									// Then store it in the temporary buffer
									*((int*)(volume_buffer + i*sizeof(int))) = tmp;
								}

								// And in the end, read the whole buffer
								if (write(write_init_audio, volume_buffer, BUFFER_SIZE) == -1)
									close_connection(client_socket, "Error at writing a volume changed block on audio output", write_init_audio);

								break;

							// If an unknown filter, error!
							default:
								close_connection(client_socket, "Filter passed unknown", write_init_audio);
								break;

						}

						// If everything's ok, send the last packet
						clear_packet(&to_server);

						// Close the connection
						close_connection(client_socket, "The file was correctly read, close the connection, bye", write_init_audio);
						break;


					// --------------- Unknown type ---------------
					default:
						close_connection(client_socket, "Packet type unknown", write_init_audio);
						break;

				}
			}
		}

		// If an error during the receiving of a packet
		else {

			// If echo filter, free the buffer (only if it has been initialized)
			if ((filter == F_ECHO) && (nb_buffers_per_echo > 0)) free(echo_buffer);

			// Close connection
			perror("Error during the receiving of a packet, the server may be busy");
			return 0;
		}

	} while (from_server.type != P_CLOSE_TRANSMISSION);

	// Close the connection
	close_connection(client_socket, "The reading is done, close connection now", write_init_audio);

	// If everything's was ok (but the server is normally just waiting for clients)
	return 0;
}

/*
	SYR2 - Projet - Partie 2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

#include <clientserver.h>


/**
 * If an error is encountered, close the connection
 *
 * Parameters:
 *	 - int err_socket  => The file descriptor to the server socket
 *   - char* err_message  => The error message
 *   - struct sockaddr* err_destination  => The destination address (server)
 *	 - int audio_fd  => The audio file descriptor (if 0, none)
 *
 */
void close_connection(int err_socket, char* err_message, struct sockaddr* err_destination, int audio_fd) {

	// Display error message
	perror(err_message);

	// Some parameters
	socklen_t destination_length = (socklen_t)sizeof(struct sockaddr);

	// Create the packet to send
	struct packet error_packet;
	create_packet(&error_packet, P_CLOSED, "");

	// Try three times
	int i = 0;
	while ((sendto(err_socket, &error_packet, sizeof(struct packet), 0, err_destination, destination_length) == -1) && (i < NB_TRIES)) {
		i++;
	}

	// If we couldn't send it
	if (i == NB_TRIES) perror("Error during the client closing connection");

	// Then close the socket
	if (close(err_socket) == -1) perror("Error during the closing of the client socket");

	// And close the audio file descriptor (audio_fd == 0 if none so no need to close)
	if ((audio_fd != 0) && (close(audio_fd) == -1)) perror("Error during the closing of the client socket");

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


	/* 	################################################## Parameters check ################################################## */
	// If there aren't the correct number of arguments
	if (argc != 3) { perror("Three arguments expected. Run with audioclient [server_host_name] [file_name]"); return 1; }

	// Check the parameters
	if (args[1] == NULL) { perror("The first argument is NULL. Run with audioclient [server_host_name] [file_name]"); return 1; }
	if (args[2] == NULL) { perror("The second argument is NULL. Run with audioclient [server_host_name] [file_name]"); return 1; }

	// Initialize the client socket
	struct sockaddr_in destination;
	int client_socket = init_socket(args[1], &destination);

	// Parameters for the packet transmission
	struct packet to_server;
	struct packet from_server;
	char tmp_buf[BUFFER_SIZE];  // A temporary buffer
	socklen_t destination_length = (socklen_t)sizeof(struct sockaddr);

	// Some more variables that we'll need for reading audio files
	int sample_rate, sample_size, channels;
	int write_audio, write_init_audio = 0;
	int nb_blocks, sample_size_byte;



	/* 	################################################## Sending the filename ################################################## */

	// The first packet to send is the filename
	create_packet(&to_server, P_FILENAME, args[2]);

	// Send the packet containing the filename
	if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1) { perror("Error during the sending of the filename packet"); return 1; }




	/* 	################################################## Talk with the server ################################################## */
	do {

		// Clear packets
		clear_packet(&to_server);
		clear_packet(&from_server);

		// Wait a packet
		if (recvfrom(client_socket, &from_server, sizeof(struct packet), 0, (struct sockaddr *)&destination, &destination_length) != -1) {

			// In function of the type of the packet received
			switch (from_server.type) {

				// --------------- An error happened on the server ---------------
				case P_SERVER_ERROR:

					// Display the error
					perror(from_server.message);

					// Close connection
					close_connection(client_socket, "Closing due to server error",(struct sockaddr*)&destination, 0);
					break;


				// --------------- An error happened on the server ---------------
				case P_FILE_HEADER:

					// To avoid an error saying that we can't put declaration just after this label
					;  // Seriously ...

					// Get the audio parameters
					char *token = strtok(from_server.message, " ");
					int i = 0;
					while ((token != NULL) && (i < 5)) {
						switch (i) {
							case 0:
								sample_rate = atoi(token);
								break;

							case 1:
								sample_size = atoi(token);
								break;

							case 2:
								channels = atoi(token);
								break;

							case 3:
								sample_size_byte = atoi(token);
								break;

							case 4:
								nb_blocks = atoi(token);
								break;
						}

						token = strtok(NULL, " ");
						i++;
					}

					// Initialize the write end
					write_init_audio = aud_writeinit(sample_rate, sample_size, channels);

					// If an error happened
					if (write_init_audio < 1) close_connection(client_socket, "Error at getting the audio output device", (struct sockaddr*)&destination, 0);

					// If everything's ok, request the first block
					clear_packet(&to_server);
					to_server.type = P_REQ_NEXT_BLOCK;

					// Send the request
					if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1)
						close_connection(client_socket, "Error at requesting the first block", (struct sockaddr*)&destination, write_init_audio);

					break;


				// --------------- A block is received, read it ---------------
				case P_BLOCK:

					// To avoid an error saying that we can't put declaration just after this label
					/*;  // Seriously ...

					// Read this number of blocks
					int count = 0;
					do {

						// Clear the temporary buffer
						bzero(tmp_buf, BUFFER_SIZE);

						// Fill the temporary buffer
						memcpy((char*)(from_server.message + (sample_size_byte * count)), tmp_buf, sample_size_byte);

						// Just read it
						write_audio = write(write_init_audio, tmp_buf, sample_size_byte);

						// Increments the counter
						count++;

					} while ((count < (nb_blocks-1)) && (write_audio == sample_size_byte));*/

					write_audio = write(write_init_audio, from_server.message, sample_size_byte);
					
					// If error during the reading
					if (write_audio == -1) close_connection(client_socket, "Error at writing a block", (struct sockaddr*)&destination, write_init_audio);

					// If everything's ok, request the next block
					clear_packet(&to_server);
					to_server.type = P_REQ_NEXT_BLOCK;

					// Send the request
					if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1)
						close_connection(client_socket, "Error at requesting a block", (struct sockaddr*)&destination, write_init_audio);

					break;


				// --------------- The last block is received, read it ---------------
				case P_EOF:

					// To avoid an error saying that we can't put declaration just after this label
					/*;  // Seriously ...

					// Read this number of blocks
					int last_count = 0;
					do {

						// Clear the temporary buffer
						bzero(tmp_buf, BUFFER_SIZE);

						// Fill the temporary buffer
						memcpy((char*)(from_server.message + (sample_size_byte * last_count)), tmp_buf, sample_size_byte);

						// Just read it
						write_audio = write(write_init_audio, tmp_buf, sample_size_byte);

						// Increments the counter
						last_count++;

					} while ((count < (nb_blocks-1)) && (write_audio == sample_size_byte));*/

					write_audio = write(write_init_audio, from_server.message, sample_size_byte);

					// If error during the reading
					if (write_audio == -1) close_connection(client_socket, "Error at writing a block", (struct sockaddr*)&destination, write_init_audio);

					// If everything's ok, request the next block
					clear_packet(&to_server);

					// Close the connection
					close_connection(client_socket, "The file was correctly read, close the connection, bye", (struct sockaddr*)&destination, write_init_audio);
					break;
				
			}
		}

		// If an error during the receiving of a packet
		else perror("Error during the receiving of a packet");

	} while (from_server.type != P_CLOSE_TRANSMISSION);

	// Close the connection
	close_connection(client_socket, "Everything's ok, close connection now", (struct sockaddr*)&destination, write_init_audio);

	// If everything's was ok (but the server is normally just waiting for clients)
	return 0;
}
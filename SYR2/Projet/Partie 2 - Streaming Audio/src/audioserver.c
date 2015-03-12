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
		perror("Error during the sending of the error packet");

	// Close the socket
	close(err_socket);

	// Exit the program
	exit(1);
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
	// If there are too many arguments
	if (argc > 1) { perror("There are too many arguments. This program requires none"); return 1; }



	/* 	################################################## Socket creation and bind ################################################## */
	// Create the server socket
	int server_socket = socket(AF_INET, SOCK_DGRAM, 0);

	// If error
	if (server_socket == -1) { perror("Error during the creation of the server socket"); return 1; }

	// The structure containing the port number
	struct sockaddr_in addr;
	addr.sin_family = AF_INET;
	addr.sin_port = htons(SERVER_PORT);
	addr.sin_addr.s_addr = htonl(INADDR_ANY);

	// Bind the port
	if (bind(server_socket, (struct sockaddr *) &addr, sizeof(struct sockaddr_in)) == -1) { perror("Error during the server socket's bind"); return 1; }



	/* 	################################################## Waiting client to connect ################################################## */
	// Create the buffer
	struct packet packet_received;

	// The structure for informations about the source (will be filled)
	struct sockaddr_in source;

	// Its length (for the pointer)
	socklen_t source_length = (socklen_t)sizeof(struct sockaddr);

	// Wait a client to send a packet
	int filenamepacket_receved = recvfrom(server_socket, &packet_received, sizeof(struct packet), 0, (struct sockaddr *) &source, &source_length);

	// If error
	if (filenamepacket_receved == -1) { perror("Error during the receiving of the packet"); return 1; }



	/* 	################################################## Only one client can enter in this section ################################################## */

	/* 	################################################## Treat the filename received and send the response ################################################## */
	// If not the filename packet
	if (packet_received.type != P_FILENAME)
		server_error_encountered(server_socket, P_ERR_TRANSMISSION, "Filename was requested but another packet received", (struct sockaddr*)&addr, source_length);

	// Print to check the datas received
	fprintf(stdout, "%u\n", packet_received.type);
	fprintf(stdout, "%s\n", packet_received.message);

	// Try to open the file
	// The values of the parameters of the audio file (will be filled by aud_readinit)
	int sample_rate, sample_size, channels;

	// Read the music file
	int read_init_audio = aud_readinit(packet_received.message, &sample_rate, &sample_size, &channels);

	// If an error happened (maybe the file doesn't exist)
	if (read_init_audio == -1)
		server_error_encountered(server_socket, P_ERR_FILENOTFOUND, "Error at reading the audio file", (struct sockaddr*)&addr, source_length);







	/* 	################################################## End of single client zone ################################################## */



	/* 	################################################## Socket closing ################################################## */
	// Then close it in the end
	if (close(server_socket) == -1) { perror("Error during the closing of the server socket"); return 1; }

	// If everything's was ok (but the server is normally just waiting for clients)
	return 0;
}
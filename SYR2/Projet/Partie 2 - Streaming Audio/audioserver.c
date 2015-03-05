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
 *	 - int *compteur  => The counter of messages
 *	 - char *tableau  => The table to write in
 *
 * Return:
 *	 - int  => The result of the execution
 */
int main(int argc, char** args) {

	// If there are too many arguments
	if (argc > 1) { perror("There are too many arguments. This program requires none"); return 1; }

	// Create the server socket
	int server_socket = socket(AF_INET, SOCK_DGRAM, 0);

	// If error
	if (server_socket == -1) { perror("Error during the creation of the socket"); return 1; }

	// The structure containing the port number
	struct sockaddr_in addr;
	addr.sin_family = AF_INET;
	addr.sin_port = htons(SERVER_PORT);
	addr.sin_addr.s_addr = htonl(INADDR_ANY);

	// Bind the port
	if (bind(server_socket, (struct sockaddr *) &addr, sizeof(struct sockaddr_in)) == -1) { perror("Error during the socket's bind"); return 1; }

	// Create the buffer
	char server_buffer[BUFFER_SIZE];

	// The structure for informations about the source (will be filled)
	struct sockaddr_in source;

	// Its length (for the pointer)
	socklen_t source_length = (socklen_t) sizeof(struct sockaddr);

	// Wait a packet
	int packets_receved = recvfrom(server_socket, &server_buffer, BUFFER_SIZE, 0, (struct sockaddr *) &source, &source_length);

	// If error
	if (packets_receved == -1) { perror("Error during the receiving of the packet"); return 1; }

	// If everything's was ok (but the server is normally just waiting for clients)
	return 0;
}
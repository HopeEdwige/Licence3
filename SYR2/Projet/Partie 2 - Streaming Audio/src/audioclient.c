/*
	SYR2 - Projet - Partie 2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

#include <clientserver.h>


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

	// Parameters that we'll need
	struct packet to_server;
	struct packet from_server;
	socklen_t destination_length = (socklen_t)sizeof(struct sockaddr);



	/* 	################################################## Sending the filename ################################################## */

	// The first packet to send is the filename
	create_packet(&to_server, P_FILENAME, args[2]);

	// Send the packet
	if (sendto(client_socket, &to_server, sizeof(struct packet), 0, (struct sockaddr*)&destination, destination_length) == -1) { perror("Error during the sending of the packet"); return 1; }




	/* 	################################################## Talk with the server ################################################## */
	do {
		NOTHING
	} while (from_server.type != P_CLOSE_TRANSMISSION);

	// Send the last packet
	clear_packet(to_server);



	/* 	################################################## Socket closing ################################################## */
	// Then close it in the end
	if (close(client_socket) == -1) { perror("Error during the closing of the client socket"); return 1; }

	// If everything's was ok (but the server is normally just waiting for clients)
	return 0;
}
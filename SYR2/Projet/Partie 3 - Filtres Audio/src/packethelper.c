/*
	SYR2 - Projet - Partie 2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

#include <clientserver.h>


/**
 * Create a packet
 *
 * Parameters:
 *	 - struct packet* to_init  	=> The packet to initialize
 *	 - int type  				=> The type of the packet
 *	 - char* content  			=> The content of the packet (pointer)
 *
 */
void create_packet(struct packet* to_init, int type, char* content) {
	to_init->type = type;
	memcpy(to_init->message, content, BUFFER_SIZE);
}


/**
 * Clear a packet
 *
 * Parameters:
 *	 - struct packet* to_clear  	=> The packet to clean
 *
 */
void clear_packet(struct packet* to_clear) {
	to_clear->type = P_ERR_TRANSMISSION;
	bzero(to_clear->message, BUFFER_SIZE);
}
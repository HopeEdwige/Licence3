/*
	SYR2 - Projet - Partie 2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/
/* Only if not defined */
#ifndef CLIENTSERVER_H_
#define CLIENTSERVER_H_


// Include librairies
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <audio.h>
#include <netdb.h>


// Constants
#define SERVER_PORT 2096  // Server Port
#define BUFFER_SIZE 1024  // Buffer Size
// Structure types
#define P_ERR_TRANSMISSION 0
#define P_FILENAME 1
#define P_NEXT_BLOCK 2
#define P_ERR_FILENOTFOUND 3
#define P_REQ_NEXT_BLOCK 4
#define P_EOF 5



// Structures
typedef struct packet {
	int type;
	char message[BUFFER_SIZE];
} packet;


// Methods (public) signatures
void create_packet(struct packet* to_init, int type, char* content);
void clear_packet(struct packet* to_clear);


/* End of if */
#endif /*CLIENTSERVER_H_*/
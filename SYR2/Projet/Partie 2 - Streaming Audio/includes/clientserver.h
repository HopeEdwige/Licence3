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
#include <sys/select.h>
#include <netinet/in.h>
#include <audio.h>
#include <netdb.h>


// Constants
#define SERVER_PORT 2096  // Server Port
#define BUFFER_SIZE 1024  // Buffer Size
#define NB_TRIES 3        // Trying to send a packet when error happens


// Server status
#define S_FREE 0
#define S_BUSY 1


// Message types
#define P_ERR_TRANSMISSION 0
#define P_SERVER_ERROR 1
#define P_FILENAME 2
#define P_FILE_HEADER 3
#define P_REQ_NEXT_BLOCK 4
#define P_REQ_SAME_PACKET 5
#define P_BLOCK 6
#define P_EOF 7
#define P_CLOSE_TRANSMISSION 8
#define P_CLOSED 9


// Filters
#define F_NONE 0
#define F_FORCE_MONO 1
#define F_UP 2
#define F_DOWN 3
#define F_ECHO 4


// Structures
typedef struct packet {
	int type;
	char message[BUFFER_SIZE];
} packet;


// Methods signatures
void create_packet(struct packet* to_init, int type, char* content);
void clear_packet(struct packet* to_clear);


/* End of if */
#endif /*CLIENTSERVER_H_*/
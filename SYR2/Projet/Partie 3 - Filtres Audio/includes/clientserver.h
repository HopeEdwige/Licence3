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
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/select.h>
#include <netinet/in.h>
#include <audio.h>


// Constants
#define SERVER_PORT 2096  // Server Port
#define BUFFER_SIZE 1024  // Buffer Size
#define BUFFER_SPACE 64  // Space between each int put in the buffer (sizeof(int) can be different from a machine to another)


// Timeouts
#define TIMEOUT_CLIENT 200000  // In microseconds
#define TIMEOUT_SERVER 3  // In seconds


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


// Filters name
#define F_MONO_NAME "mono"
#define F_VOLUME_NAME "volume"
#define F_ECHO_NAME "echo"
#define F_SPEED_NAME "speed"
#define F_VOLUME_PARAMETER_NAME "[0 - 5]"
#define F_SPEED_PARAMETER_NAME "[1 - 4]"


// Filters
#define F_NONE 0
#define F_MONO 1
#define F_VOLUME 2
#define F_ECHO 3
#define F_SPEED 4


// Filters parameters
#define F_VOLUME_PARAMETER_MIN 0
#define F_VOLUME_PARAMETER_MAX 5
#define F_SPEED_PARAMETER_MIN 1
#define F_SPEED_PARAMETER_MAX 4
#define COMMAND_SYNTAX "Run with audioclient server_host_name file_name [filter_name] [filter_parameter]"
#define ECHO_IN_SCDS 1  // The number of seconds for the echo



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

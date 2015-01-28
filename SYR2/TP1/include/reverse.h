#ifndef SYR1_TP1
#define SYR1_TP1



// Includes
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>


// Constants
#define BUFFER_SIZE 1024  // Buffer size


// Structures definitions
typedef struct s_list {
	char* content;
	struct s_list* next;
} simple_list;


// Functions declarations
int reverse_file(char* filename);

int reverse_buffer(char* buffer);

int add(simple_list * * list, char* content);

void free_list(simple_list* list);



#endif /*SYR1_TP1*/
#ifndef SYR1_TP1
#define SYR1_TP1

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>

// Buffer size
#define BUFFER_SIZE 1024


int reverse_file(char* filename);

int reverse_buffer(char* buffer);

#endif /*SYR1_TP1*/
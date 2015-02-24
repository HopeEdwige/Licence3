/*
	TP1 - SYR2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

// Includes
#include <stdio.h>
#include <sys/shm.h>
#include <stdlib.h>


// Constants
#define TAILLE 1024


/**
 * This program just delete the shared memory segment
 *
 * Parameters:
 *     - int argc  => The number of arguments
 *     - char** args  => The arguments
 *
 * Return:
 *     - int  => The result of the execution
 */
int main(int argc, char** args) {

	// If there are too many arguments
	if (argc > 1) {
		fprintf(stderr, "%s\n", "There are too many arguments. This program requires none.");
		return 1;  // Exit with an error code
	}

	// Get the location of the shared memory segment
    int id = shmget((key_t)1234,TAILLE+sizeof(int),0600|IPC_CREAT);  // Taille => Tableau // sizeof(int) => compteur

    // Delete the shared memory segment
    if (shmctl(id, IPC_RMID, NULL) < 0) { perror("Error shmctl"); exit(1); }
    return 0;
}
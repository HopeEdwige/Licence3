/*
	TP1 - SYR2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

// Includes
#include <stdio.h>
#include <sys/shm.h>
#include <sys/sem.h>
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
    key_t key = (key_t)1234;
    int id = shmget(key,TAILLE+sizeof(int),0600|IPC_CREAT);  // Taille => Tableau // sizeof(int) => compteur

    // Get the semaphore table
    int my_sem = semget(key, 1, 0600);  // Don't create it if it doesn't exist yet
    if (my_sem > 0) {

        // Destroy it only if it was initialized before
        if (semctl(my_sem, 0, IPC_RMID) == -1) { perror("Error semop DESTROY"); exit(1); }
    }

    // Delete the shared memory segment
    if (shmctl(id, IPC_RMID, NULL) < 0) { perror("Error shmctl"); exit(1); }

    return 0;
}

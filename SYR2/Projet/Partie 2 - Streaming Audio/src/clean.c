/*
    SYR2 - Projet - Partie 2
    ANDRIAMILANTO Tompoariniaina
    BOUCHERIE Thomas
*/

// Includes
#include <stdio.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <stdlib.h>
#include <clientserver.h>


/**
 * This program just close the semaphore
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
		perror("There are too many arguments. This program requires none");
		return 1;  // Exit with an error code
	}

	// Get the location of the shared memory segment
    key_t key = (key_t)SEM_KEY;

    // Get the semaphore table
    int my_sem = semget(key, 1, 0600);

    // Only close it if it's already open
    if (my_sem > 0) {

        // Destroy it only if it was initialized before
        if (semctl(my_sem, 0, IPC_RMID) == -1) { perror("Error semop DESTROY"); return 1; }
    }

    return 0;
}
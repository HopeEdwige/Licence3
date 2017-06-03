/*
    TP1 - SYR2
    ANDRIAMILANTO Tompoariniaina
    BOUCHERIE Thomas
*/

// Includes
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <strings.h>


// Constants
#define TAILLE 1024


/**
 * Write the id of the process in the table
 *
 * Parameters:
 *     - int *compteur  => The counter of messages
 *     - char *tableau  => The table to write in
 *
 * Return:
 *     - int  => The result of the execution
 */
void write_in_table(int *compteur, char *tableau) {
    char message[64], *msg=message;
    snprintf(message, 64, "I'm the process number %d!\n", getpid());

    while ((*compteur<TAILLE)&&(*msg)) {
        tableau[*compteur] = *msg;
        msg++;
        usleep(100000);
        (*compteur)++;
    }
}


/**
 * The main program
 *
 * Parameters:
 *     - int *compteur  => The counter of messages
 *     - char *tableau  => The table to write in
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

    // The variables stored in the shared memory
    int id, *compteur;
    char *tableau;
    key_t key = (key_t)1234;

    // Create the shared memory
    id = shmget(key,TAILLE+sizeof(int),0600|IPC_CREAT);  // Taille => Tableau // sizeof(int) => compteur
    if (id<0) { perror("Error shmget"); exit(1); }


    /* ######################### Semaphore control and cricical zone here ######################### */

    // Create the correct structure for the semaphore implementation
    struct sembuf up = {0, 1, 0};
    struct sembuf down = {0, -1, 0};

    // Get the semaphore table
    int my_sem = semget(key, 1, 0600);

    // If it wasn't created/initialized yet
    if (my_sem == -1) {

        // Create it
        my_sem = semget(key, 1, 0600|IPC_CREAT);

        // Initialize the value of it (only one process at a time)
        if (semctl(my_sem, 0, SETVAL, 1) == -1) { perror("Error semctl"); exit(1); }
    }

    // If after trying to create it an error was thrown
    if (my_sem == -1) { perror("Error semget"); exit(1); }

    // Put DOWN the semaphore
    if (semop(my_sem, &down, 1) == -1) { perror("Error semop DOWN"); exit(1); }

    // Get the value of the counter
    compteur = (int*) shmat(id, 0, 0);
    if (compteur == NULL) { perror("Error shmat"); exit(1); }

    // Get the table's location to store the message
    tableau = (char*)(compteur + 1);

    // Write in the table
    write_in_table(compteur, tableau);

    // Put UP the semaphore
    if (semop(my_sem, &up, 1) == -1) { perror("Error semop UP"); exit(1); }

    /* ######################### End of semaphore control and cricical zone ######################### */


    // Then display it
    printf("%s\n", tableau);

    // Close the shared memory segment (but doesn't delete it!)
    if (shmdt(compteur) < 0) { perror("Error shmdt"); exit(1); }
    return 0;
}

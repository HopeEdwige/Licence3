/*
	TP1 - SYR2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>


// Constants
#define MAX_SIZE 1024  // Max command size


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// Create the buffer to read the command entered
	char command[MAX_SIZE];

	// Try to read the command
	char *result_read = fgets(command, (MAX_SIZE/sizeof(char)), stdin);

	// Check if there wasn't any error
	if (result_read != NULL) {

		// While we have another value than exit
		while (strcmp(command, "exit\n") != 0) {

			// If there was an error during the reading
			if (result_read == NULL)
				return 1;  // Exit with an error code

			// If no error, we can execute the command
			else {

				// Do the fork here
				pid_t result_fork = fork();

				// If the SON
				if (result_fork == 0) {

					// Clean the command (remove the annoying "\n" at the end of it)
					command[strlen(command) - 1] = '\0';

					// Now cut the command into different strings
					char espace = ' ';
					char *command_splitted = strtok(command, &espace);

					// Execute the function passed
					int result_execution = execlp(command_splitted[0], command_splitted);

					// Return the execution result
					return result_execution;
				}


				// If the FATHER
				else {

					// Wait the son to terminate
					int status;
					pid_t result_wait;
					while ((result_wait = wait(&status)) > 0) { }
				}
			}

			// Read another one
			result_read = fgets(command, MAX_SIZE/sizeof(char), stdin);
		}

		// If exit enterred
		return 0;
	}

	// If an error
	return 1;
}
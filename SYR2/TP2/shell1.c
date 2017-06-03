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
			if (result_read == NULL) {
				fprintf(stderr, "%s\n", "The command couldn't been read.");
				return 1;  // Exit with an error code
			}

			// If no error, we can execute the command
			else {

				// Do the fork here
				pid_t result_fork = fork();

				// If the SON
				if (result_fork == 0) {

					// Clean the command ("\n" problem)
					command[strlen(command) - 1] = '\0';

					// Execute the function passed
					int result_execution = execlp(command, command, NULL);

					// If an error occured
					if (result_execution != 0)
						fprintf(stderr, "%s%d\n", "Error during the execution of the command. Exit with code ", result_execution);

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
	fprintf(stderr, "%s\n", "The command couldn't been read.");
	return 1;
}

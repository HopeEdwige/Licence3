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

// Constants
#define MAX_SIZE 1024  // Buffer size


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// Create the buffer to read the command entered
	char* command = malloc(MAX_SIZE);
	char* result_read = malloc(MAX_SIZE);

	// Try to read the command
	result_read = fgets(command, MAX_SIZE/sizeof(char), stdin);

	// Check if there wasn't any error
	if (result_read != NULL) {

		// While we have another value than exit
		while (strcmp(command, "exit\n") != 0) {

			// Display the command passed
			printf("%s", command);

			// Clear the buffer
			bzero(command, MAX_SIZE);

			// Read another one
			result_read = fgets(command, MAX_SIZE/sizeof(char), stdin);

			// If there was an error during the reading
			if (result_read == NULL) {

				// Free the memory then exit
				free(result_read);
				return 1;
			}

			// If no error, we can execute the command
			else {

				
			}
		}

		// If exit enterred
		free(result_read);
		return 0;
	}

	// If an error
	free(command);
	free(result_read);
	return 1;
}
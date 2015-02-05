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
#include <sys/types.h>
#include <sys/wait.h>


// Constants
#define MAX_SIZE 1024  // Max command size



/**
 * Get a command splitted
 *
 * Parameters:
 *     - char *command  => The command string to split
 *     - char **command_parts  => A table of the splitted command
 *
 */
void get_command_parts(char *command, char **command_parts) {
	// Clean the command (remove the annoying "\n" at the end of it)
	command[strlen(command) - 1] = '\0';

	// Now cut the command into different strings
	char *token = strtok(command, " ");

	// An index to know where we're
	int i = 0;

	// Get all the parts
	while (token != NULL) {
		command_parts[i] = token;
		token = strtok(NULL, " ");
		i++;
	}

	// The last one is NULL
	command_parts[i] = NULL;
}


/**
 * The main function to be thrown
 *
 * Return:
 *     - int  => The result of the execution
 */
int main() {

	// Create the buffer to read the command entered
	char command[MAX_SIZE];
	char command2[MAX_SIZE];

	// Try to read the command
	fprintf(stdout, "%s\n", "Please enter the first command:");
	char *result_read = fgets(command, (MAX_SIZE/sizeof(char)), stdin);

	// Close if exit
	if (strcmp(command, "exit\n") == 0)
		return 0;

	// Try to read the second command
	fprintf(stdout, "%s\n", "Please enter the second command:");
	char *result_read2 = fgets(command2, (MAX_SIZE/sizeof(char)), stdin);

	// Close if exit
	if (strcmp(command2, "exit\n") == 0)
		return 0;

	// While we have another value than exit
	while ((strcmp(command, "exit\n") != 0) && (strcmp(command2, "exit\n") != 0)) {

		// If there was an error during the reading
		if (result_read == NULL) {
			fprintf(stderr, "%s\n", "The first command couldn't been read.");
			return 1;  // Exit with an error code
		}

		// Check the second command
		if (result_read2 == NULL) {
			fprintf(stderr, "%s\n", "The second command couldn't been read.");
			return 1;  // Exit with an error code
		}

		// If no error, we can execute the command
		else {

			// The table of commands
			char *command_parts[MAX_SIZE];
			char *command_parts2[MAX_SIZE];

			// Split the commands
			get_command_parts(command, command_parts);
			get_command_parts(command2, command_parts2);

			// Create a pipe here
			int fd[2];
			if (pipe(fd) < 0) {
				fprintf(stderr, "%s\n", "Error during the creation of the pipe.");
				return 1;
			}

			// Do the fork here
			pid_t result_fork = fork();

			// If the SON
			if (result_fork == 0) {

				// Close the unused read end
				close(fd[0]);

				// Redirect the standard output from the pipe
				if (dup2(1, fd[1]) < 0) {  // Less than 0 if error
					fprintf(stderr, "%s\n", "The io redirection failed in the in => out direction.");
					return 1;
				}

				// Then execute the second one
				int result_execution = execvp(command_parts[0], command_parts);

				// If an error occured
				if (result_execution != 0) {
					fprintf(stderr, "%s%d\n", "Error during the execution of the first command. Exit with code ", result_execution);
					return result_execution;
				}
			}

			// If the FATHER
			else {

				// Then create another fork
				pid_t son_fork = fork();

				// If the NEW SON
				if (son_fork == 0) {

					// Close the unused write end
					close(fd[1]);

					// Redirect the standard input from the pipe
					if (dup2(0, fd[0] ) < 0) {  // Less than 0 if error
						fprintf(stderr, "%s\n", "The io redirection failed in the out => in direction.");
						return 1;
					}

					// Then execute the second one
					int second_execution = execvp(command_parts2[0], command_parts2);

					// If an error occured
					if (second_execution != 0) {
						fprintf(stderr, "%s%d\n", "Error during the execution of the second command. Exit with code ", second_execution);
						return second_execution;
					}
				}

				// Wait the sons to terminate
				int status;
				pid_t result_wait;
				while ((result_wait = wait(&status)) > 0) { }
			}
		}

		// Break a line
		fprintf(stdout, "%s\n", "");

		// Read the first command
		fprintf(stdout, "%s\n", "Please enter the first command:");
		result_read = fgets(command, (MAX_SIZE/sizeof(char)), stdin);

		// Close if exit
		if (strcmp(command, "exit\n") == 0)
			return 0;

		// Read the second one
		fprintf(stdout, "%s\n", "Please enter the second command:");
		result_read2 = fgets(command2, (MAX_SIZE/sizeof(char)), stdin);

		// Close if exit
		if (strcmp(command2, "exit\n") == 0)
			return 0;
	}

	// If an error
	fprintf(stderr, "%s\n", "One of the commands couldn't been read.");
	return 1;
}
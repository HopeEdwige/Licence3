/*
	TP1 - SYR2
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

#include <reverse.h>


/**
 * The main function to ne thrown
 *
 * Parameters:
 *     - int argc  => The number of arguments
 *     - char** filename  => The name of the file (only the second element)
 *
 * Return:
 *     - 
 */
int main(int argc, char** filename) {
	return reverse_file(filename[1]);
}


/**
 * Function to display a file in reverse
 */
int reverse_file(char* filename) {
	int result_open = open(filename, O_RDONLY);

	if (result_open >= 0) {

		//Create the buffer
		char* buffer = malloc(BUFFER_SIZE);

		if (buffer != NULL) {

			int result_read = read(result_open, buffer, BUFFER_SIZE);

			if (result_read >= 0) {

				// Display the buffer
				reverse_buffer(buffer);

				int result_close = close(result_open);

				if (result_close == 0) {
					free(buffer);

					return 0;
				}
			}
		}
	}

	return -1;
}


/**
 * Function to display a buffer in reverse
 */
int reverse_buffer(char* buffer) {
	int i;
	for (i = (BUFFER_SIZE - 1); i >= 0; i = (i - sizeof(char))) {
		printf("%c", &buffer[i]);
	}
	return 0;
}
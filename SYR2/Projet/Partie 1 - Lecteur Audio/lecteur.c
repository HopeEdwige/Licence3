/*
	SYR2 - Projet - Partie 1
	ANDRIAMILANTO Tompoariniaina
	BOUCHERIE Thomas
*/

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <unistd.h>
#include "audio.h"


// Constants
#define MAX_FILENAME_SIZE 1024  // Max filename size


/**
 * The main program
 *
 * Parameters:
 *	 - int *compteur  => The counter of messages
 *	 - char *tableau  => The table to write in
 *
 * Return:
 *	 - int  => The result of the execution
 */
int main(int argc, char** args) {

	// If there are too many arguments
	if (argc > 1) { perror("There are too many arguments. This program requires none."); return 1; }

	// Get the name of the audio file
	perror("Please enter the audio file location:");
	char filename[MAX_FILENAME_SIZE];

	// Try to read the filename
	char *result_read = fgets(filename, (MAX_FILENAME_SIZE/sizeof(char)), stdin);

	// Check if there wasn't any error
	if (result_read != NULL) {

		// Clean the filename (remove the annoying "\n" at the end of it)
		filename[strlen(filename) - 1] = '\0';

		/* ######################### Getting File Descriptors Part ######################### */

		// The values of the parameters of the audio file (will be filled by aud_readinit)
		int sample_rate, sample_size, channels;

		// Read the music file
		int read_init_audio = aud_readinit(filename, &sample_rate, &sample_size, &channels);

		// If an error happened
		if (read_init_audio == -1) { perror("Error at reading the audio file"); return 1; }

		/* ##### Insert here the modifications of audio parameters ##### */
		//sample_rate = sample_rate * 2;
		//sample_rate = sample_rate / 2;
		//channels = 1;
		//sample_size = 4;  // 4 Bits  => Put automatically to 16
		//sample_size = 8;  // 8 Bits  => Unaudible

		// If the read was successfull
		fprintf(stderr, "Filename = %s; Sample Rate = %u; Sample Size = %u; Channels = %u; \n", filename, sample_rate, sample_size, channels);

		// Then get the file descriptor of the audio output device
		int write_init_audio = aud_writeinit(sample_rate, sample_size, channels);

		// If an error happened
		if (write_init_audio < 1) { perror("Error at getting the audio output device"); return 1; }



		/* ######################### Reading audio file ######################### */

		// The results variables
		ssize_t read_audio;
		ssize_t write_audio;

		// Create buffers
		char audio_buffer[sample_size];

		// For each audio sample
		do {
			// Simply read each sample of the audio file
			read_audio = read(read_init_audio, audio_buffer, sample_size);

			// And write them after
			write_audio = write(write_init_audio, audio_buffer, sample_size);

			// Clear the buffer after each pass
			bzero(audio_buffer, sample_size);

		} while ((read_audio == sample_size) && (write_audio == sample_size));

		// If an error happened
		if (read_audio == -1) { perror("Error at reading the audio file"); return 1; }

		// If an error happened
		if (write_audio == -1) { perror("Error at playing the audio file"); return 1; }

		// Close the descriptor file when it's done
		if (close(read_init_audio) != 0) { perror("Error at closing the read file descriptor"); return 1; }

		// Close the descriptor file when it's done
		if (close(write_init_audio) != 0) { perror("Error at closing the write file descriptor"); return 1; }

		// If everything went ok
		return 0;
	}

	// If an error
	fprintf(stderr, "%s\n", "The filename couldn't been read.");
	return 1;
}

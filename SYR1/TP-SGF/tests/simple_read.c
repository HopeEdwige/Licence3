#include<stdio.h>
#include<stdlib.h>
#include<syr1_file.h>


SYR1_FILE* file_r1 ;

int main(int argc, char **argv) {

	file_r1 = syr1_fopen("tontons.txt", "r");

	if (file_r1 != NULL) {
		int res;
		do {
			res = syr1_getc(file_r1);
			if (res < 0) {
				if (res == -3)
					printf("\nFin de fichier !\n");
				else if (res == -2)
					printf("\nProblème d'E/S physiques\n");
				else if (res == -1)
					printf("\nProblème de BCF\n");
			} else {
				printf("%c", (char) res);
			}
		} while (res >= 0);
	}
	fflush(stdout);

	syr1_fclose(file_r1);
	return 0;
}

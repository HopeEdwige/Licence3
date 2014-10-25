#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<syr1_file.h>

SYR1_FILE* file_w ;

int main(int argc, char **argv) {

	file_w = syr1_fopen("testABC.txt", "w");
	int j=0;

	if (file_w != NULL) {
		for (j = 0; j < 750; j++) {
			int res = syr1_putc('A' + (j % 22), file_w);
			if (res != 0) {
				printf("Erreur d'écriture pour le %d ème caractère\n",j);
				return -1;
			}
		}
		int res = syr1_fclose(file_w);
		if (res != 0) {
		  printf("Erreur à la fermeture du fichier");
		  return -1;
		}
		return 0;
	}
	printf("Erreur à l'ouverture du fichier");
	return -1;
}


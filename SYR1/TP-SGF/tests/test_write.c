#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<syr1_file.h>

char mess[] = "Bonjour,\n";

extern int physical_io_count;
extern int physical_read_count;
extern int physical_write_count;
int main(int argc, char **argv) {
	int j = 0;
	printf("\n***********************************************************\n");
	printf("Test écriture N°1 : ouverture en écriture du fichier testABC.txt\n");

	SYR1_FILE * file_w = syr1_fopen("testABC.txt", "w");
	if (file_w == NULL) {
		printf("[FAILED] Erreur d'ouverture en écriture pour test.txt\n");
	} else {
		printf("[OK]	Ouverture en écriture réussie\n");
		for (j = 0; j < 10; j++) {
			int res = syr1_fwrite(file_w, 1, strlen(mess), mess);
			if (res != strlen(mess)) {
				printf("[FAILED] Problème pendant l'écriture: code erreur n°%d\n",
						res);
			}
		}

		int res = syr1_fclose(file_w);
		if (res == 0) {
			printf("[OK] 	  Fermeture du fichier réussie\n");
		} else {
			printf("[FAILED] Problème pendant la fermeture : code erreur n°%d\n",res);
		}

		fflush(stdout);
		res = syr1_fclose(file_w);
		if (res == -1) {
			printf("[OK] 	 Impossible de refermer deux fois le même fichier\n");
		} else {
			printf("[FAILED] l'exécution de deux fermetures consécutives d'un même fichier ne provoque pas d'erreurs.\n");
		}
	}

	fflush(stdout);
	printf("\nNombre d'E/S physiques : %d (%d lectures + %d écritures)\n",physical_io_count, physical_read_count,physical_write_count);
	printf("\n***********************************************************\n");
	printf("Test écriture N°2 : Reécriture de 525 caractères dans le fichier testABC.txt\n");

	fflush(stdout);

	file_w = syr1_fopen("testABC.txt", "w");
	if (file_w == NULL) {
		printf("[FAILED] Erreur d'ouverture en écriture pour testABC.txt\n");
	} else {
		printf("[OK]	 Ouverture en écriture réussie\n");
		int nbcar = 0;
		for (j = 0; j < 525; j++) {
			int res = syr1_putc('A' + (nbcar % 10), file_w);
			if (res != 0) {
				printf("[FAILED] Erreur d'écriture pour le %d ème caractère\n",j);
			}
			nbcar++;
		}

		int res = syr1_fclose(file_w);
		if (res == 0) {
			printf("[OK]     Fermeture du fichier réussie\n");
		} else {
			printf("[FAILED] Problème pendant la fermeture : code erreur n°%d\n",res);
		}

		fflush(stdout);
	}

	fflush(stdout);
	printf("\nNombre d'E/S physiques : %d (%d lectures + %d écritures)\n",physical_io_count, physical_read_count,physical_write_count);
	printf("\n***********************************************************\n");
	printf("Test écriture N°3 : Reécriture de 526 caractères dans le fichier test012.txt\n");

	fflush(stdout);

	file_w = syr1_fopen("test012.txt", "w");
	if (file_w == NULL) {
		printf("[FAILED] Erreur d'ouverture en écriture pour test012.txt\n");
	} else {
		printf("[OK]	 Ouverture en écriture réussie\n");
		int nbcar = 0;
		for (j = 0; j < 526; j++) {
			int res = syr1_putc('0' + (nbcar % 10), file_w);
			if (res != 0) {
				printf("[FAILED] Erreur d'écriture pour le %d ème caractère\n",j);
			}
			nbcar++;
		}

		int res = syr1_fclose(file_w);
		if (res == 0) {
			printf("[OK]     Fermeture du fichier réussie\n");
		} else {
			printf("[FAILED] Problème pendant la fermeture : code erreur n°%d\n",res);
		}

		fflush(stdout);
	}

	fflush(stdout);
	printf("\nNombre d'E/S physiques : %d (%d lectures + %d écritures)\n",physical_io_count, physical_read_count,physical_write_count);
	printf("\n***********************************************************\n");
	printf("Test écriture N°4 : relecture du fichier testABC.txt\n");
	SYR1_FILE * file_r = syr1_fopen("testABC.txt", "r");
	if (file_r == NULL) {
		printf("[FAILED] Erreur d'ouverture en relecture pour testABC.txt\n");
	} else {
		printf("[OK]	 Ouverture en relecture réussie\n");
		int nbcar = 0;
		int res = syr1_getc(file_r);
		int nberreur = 0;

		while ((res >= 0) && (nberreur < 5)) {
			if (res != ('A' + (nbcar % 10))) {
				printf("[FAILED] Erreur de relecture '%c/%02X' attendu mais '%c/%02X' lu\n",
						(char) ('A' + (nbcar % 10)),
						(char) ('A' + (nbcar % 10)), res, res);
				nberreur++;
			}
			nbcar++;
			res = syr1_getc(file_r);
		}
		if ((nbcar!=525)) {
			printf("[FAILED] Le nombre de caractère lu ne correspond pas à la taille du fichier sur le disque\n");
		}
		if ((res == -3) && (nberreur == 0) && (nbcar==525)) {
			printf("[OK]     La relecture a fonctionné correctement\n");
		}
	}

	syr1_fclose(file_r);
	printf("\nNombre d'E/S physiques : %d (%d lectures + %d écritures)\n",physical_io_count, physical_read_count,physical_write_count);
	printf("\n***********************************************************\n");
	printf("Test écriture N°5 : relecture du fichier test012.txt \n");
	file_r = syr1_fopen("test012.txt", "r");
	if (file_r == NULL) {
		printf("[FAILED] Erreur d'ouverture en relecture pour test012.txt\n");
	} else {
		printf("[OK]	 Ouverture en relecture réussie\n");

		int nbcar = 0;
		int res = syr1_getc(file_r);
		int nberreur = 0;

		while ((res >= 0) && (nberreur < 5)) {
			if (res != ('0' + (nbcar % 10))) {
				printf("[FAILED] Erreur de relecture '%c/%02X' attendu mais '%c/%02X' lu\n",
						(char) ('0' + (nbcar % 10)),
						(char) ('0' + (nbcar % 10)), res, res);
				nberreur++;
			}			nbcar++;
			res = syr1_getc(file_r);
		}
		if ((nbcar!=526)) {
			printf("[FAILED] Le nombre de caractère lu ne correspond pas à la taille du fichier test2.txt\n");
		}
		if ((res == -3) && (nberreur == 0) && (nbcar==526)) {
			printf("[OK]     La relecture a fonctionné correctement\n");
		}
	}

	syr1_fclose(file_r);
	printf("\nNombre d'E/S physiques : %d (%d lectures + %d écritures)\n",physical_io_count, physical_read_count,physical_write_count);
	printf("\n*********************************************************\n");
	printf("Test écriture N°6 : test de la taille maximum d'un fichier\n");

	file_w = syr1_fopen("test3.txt", "w");
	if (file_w == NULL) {
		printf("[FAILED] Erreur d'ouverture en écriture pour test.txt\n");
	} else {
		printf("[OK]	 Ouverture en écriture réussie\n");
		int nbcar = 0;
		int res = syr1_putc('A' + (nbcar % 10), file_w);

		while (res >= 0) {
			nbcar++;
			res = syr1_putc('A' + (nbcar % 10), file_w);
		}
		if (res == -3) {
			printf("[OK]     Taille maximum de fichier atteinte pour nbcar=0x%04X/%d\n",
					nbcar, nbcar);
		} else {
			printf("[FAILED] Autre erreur : code erreur n°%d\n", res);
		}
	}

	fflush(stdout);
	int res = syr1_fclose(file_w);
	if (res < 0) {
		printf("[FAILED] Erreur fermeture : code erreur n°%d\n", res);
	} else {
		printf("[OK]	 Fermeture réussie\n");
	}
	printf("\nNombre d'E/S physiques : %d (%d lectures + %d écritures)\n",physical_io_count, physical_read_count,physical_write_count);
	printf("\n***********************************************************\n");
	printf("Test écriture N°7 : relecture du fichier de taille maximum\n");

	file_r = syr1_fopen("test3.txt", "r");
	if (file_r == NULL) {
		printf("[FAILED] Erreur d'ouverture en relecture pour test.txt\n");
	} else {
		printf("[OK]	 Ouverture en relecture réussie\n");
		int nbcar = 0;
		int res = syr1_getc(file_r);
		int nberreur = 0;

		while ((res >= 0) && (nberreur < 5)) {
			if (res != ('A' + (nbcar % 10))) {
				printf("[FAILED] Erreur de relecture '%c/%02X' attendu mais '%c/%02X' lu\n",
						(char) ('A' + (nbcar % 10)),
						(char) ('A' + (nbcar % 10)), res, res);
				nberreur++;
			}
			nbcar++;
			res = syr1_getc(file_r);
		}
		if ((res == -3) && (nberreur == 0)) {
			printf("[OK]     La relecture a fonctionné correctement\n");
		} else {
			printf("[FAILED] Problème lors de la relecture du fichier de taille maximale erreur = %d après %d caractères\n",
					res, nbcar);
		}
	}
	syr1_fclose(file_r);

	printf("\nNombre d'E/S physiques : %d (%d lectures + %d écritures)\n",physical_io_count, physical_read_count,physical_write_count);

	return 0;
}

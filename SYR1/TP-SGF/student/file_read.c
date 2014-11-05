#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<syr1_file.h>


/*
	Groupe 2.2
	ANDRIAMILANTO Tompoariniaina
	DANG MINH Anh
*/


/* SYNOPSYS :
 * 	  int syr1_fopen_read(char *name, SYR1_FILE *file) {
 * DESCRIPTION :
 *   Ce sous-programme gère l'ouverture d'un fichier logique en mode lecture.
 * PARAMETRES :
 *   name : chaîne de caratère contenant le nom externe du fichier à ouvrir
 *   file : pointeur sur un Bloc Control Fichier (File Control Bloc)
 * RESULTAT :
 *	0 : ouverture réussie
 *   -1 : autre erreur
 */
int syr1_fopen_read(char *name, SYR1_FILE* file) {
	//Get the copy of the file descriptor from the catalog
	int result_copy = search_entry(name, &(file->descriptor));

	//If it's ok
	if (result_copy == 0) {

		//Create the buffer (a bloc is 512 Bytes long)
		file->buffer = malloc(IO_BLOCK_SIZE);

		//If there was an error during the malloc
		if (file->buffer != NULL) {

			//Put the first bloc into the buffer
			int result_read_block = read_block(file->descriptor.alloc[0], file->buffer);

			//If ok
			if (result_read_block == 1) {
				//Put the read mode
				strcpy(file->mode, "r");

				//The other parameters are initialized to 0
				file->current_block = 0;
				file->file_offset = 0;
				file->block_offset = 0;

				//Everything's ok here
				return 0;
			}
		}
	}

	//If errors
	return -1;
}



/*
 * SYNOPSYS :
 * 	 int syr1_fread(SYR1_FILE *file, int item_size, int nbitem, char* buf)
 * DESCRIPTION :
 *   Ce sous-programme lit nbitem articles de taille item_size dans le fichier
 *   fichier logique passé en paramètre.
 * PARAMETRES :
 *   	 file : pointeur sur un Bloc Control Fichier (File Control Bloc)
 *  item_size : taille d'un article
 *	nb_item : nombre d'article à lire
 * RESULTAT :
 *   le nombre d'articles effectivement lus dans le fichier, sinon un code
 *   d'erreur (cf syr1_getc())
 *	-1 : le BCF est NULL, ou le mode d'ouverture est incorrect
 *	-2 : erreur d'entrée-sorties sur le périphérique de stockage
 *	-3 : fin de fichier
 */
int syr1_fread(SYR1_FILE *file, int item_size, int nbitem, char* buf) {
	int count = 0;
	while (count<nbitem*item_size) {
		int res = syr1_getc(file);
		if (res<0) {
			return res;
		} else {
			buf[count]=(unsigned char) res;
		}
		count++;
	}
	return count/item_size;
}



/*
 * SYNOPSYS :
 * 	 int syr1_getc(SYR1_FILE *file)
 * DESCRIPTION :
 *   Ce sous-programme lit un caractère à partir du fichier passé en paramètre.
 * PARAMETRES :
 *   file : pointeur sur un descripteur de fichier logique (File Control Bloc)
 * RESULTAT :
 *  valeur (convertie en int) du caractère lu dans le fichier, sinon
 *	-1 : le BCF est NULL, ou le mode d'ouverture est incorrect
 *	-2 : erreur d'entrée-sorties sur le périphérique de stockage
 *	-3 : fin de fichier
 */
int syr1_getc(SYR1_FILE *file) {

	//Only if there's a file passed
	if (file != NULL) {

		//Check the mode
		if (strcmp(file->mode, "r") == 0) {

			//If we can read it
			if (file->block_offset <= IO_BLOCK_SIZE) {

				//Read the char
				int ret = (int)file->buffer[file->block_offset];

				//If everything's ok
				if (ret != 0) {
					file->block_offset++;
					file->file_offset++;
					return ret;
				}

				//If no more char to read in the block (so EOF)
				return -3;
			}

			//If we have read all the block
			else {

				//Go to the next block
                file->current_block++;
				int result_read_next_block = read_block(file->descriptor.alloc[file->current_block], file->buffer);

				//If ok
				if (result_read_next_block == 0) {
					file->block_offset = 0;
					file->file_offset++;

					//Then read the next char after
					return syr1_getc(file);
				}

				//If error
				return result_read_next_block;
			}

		}
	}

	//If no BCF or the mode isn't correct
	return -1;
}



/* SYNOPSYS :
 * 	  int syr1_fclose_read(SYR1_FILE* file)
 * DESCRIPTION :
 *   Ce sous-programme gère la fermeture d'un fichier logique.
 * PARAMETRES :
 *   file : pointeur sur un Bloc de Contrôle Fichier (BCF)
 * RESULTAT :
 *	0 : la fermeture a réussi
 *   -1 : problème pendant la libération du descripteur de fichier logique
 *		(ou le fichier logiques file vaut NULL)
 */
int syr1_fclose_read(SYR1_FILE* file) {
	//Here we have all the working case
	if (file != NULL) {
		return free_logical_file(file);
	}

	//If errors
	return -1;
}
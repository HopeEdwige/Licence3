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
 * 	  int syr1_fopen_write(char *name, SYR1_FILE *file) {
 * DESCRIPTION :
 *   Ce sous-programme gère l'ouverture d'un fichier logique en mode écriture.
 * PARAMETRES :
 *   name : chaîne de caratère contenant le nom externe du fichier à ouvrir
 *   file : pointeur sur un Bloc Control Fichier (File Control Bloc)
 * RESULTAT :
 *	0 : ouverture réussie
 *   -1 : autre erreur
 */
int syr1_fopen_write(char *name, SYR1_FILE *file) {
	//Get the copy of the file descriptor from the catalog
	int result_search = search_entry(name, &(file->descriptor));

	//If the file already exists
	if (result_search == 0) {

		//Create the buffer (a bloc is 512 Bytes long)
		file->buffer = malloc(IO_BLOCK_SIZE);

		//If there was an error during the malloc
		if (file->buffer != NULL) {

			//Get the last bloc of this file
			int count = 0;
			while (((int)file->descriptor.alloc[count] != 0) && (count < MAX_BLOCK_PER_FILE)) {
				count++;
			}
			if (count != 0) {
				count--;
			}

			//Put the last bloc into the buffer
			int result_read_block = read_block(file->descriptor.alloc[count], file->buffer);

			//If ok
			if (result_read_block == 1) {

				//Put the write mode
				strcpy(file->mode, "w");

				//Get the current position in the block
				int tmp = 0;
				while (((int)file->buffer[tmp] != 0) && (tmp < IO_BLOCK_SIZE/sizeof(char))) {
					tmp++;
				}

				//The other parameters are initialized
				file->current_block = count;
				file->file_offset = count * ((IO_BLOCK_SIZE/sizeof(char)) - 1) + tmp;
				file->block_offset = tmp;

				//Everything's ok here
				return 0;
			}
		}
	}

	//If there's no entry for this name, create one
	if (result_search == -1) {

		//Create a new entry then
		int result_create_entry = create_entry(name, &(file->descriptor));

		//If ok
		if (result_create_entry == 0) {

			//Try to get a new free block
			int result_get_free_block = get_allocation_unit();

			//If ok
			if (result_get_free_block >= 0) {

				//Put the block number in the implementation table
				file->descriptor.alloc[0] = result_get_free_block;

				//Create the buffer (a bloc is 512 Bytes long)
				file->buffer = malloc(IO_BLOCK_SIZE);

				//Load the block in the buffer
				int result_read_block = read_block(file->descriptor.alloc[0], file->buffer);

				//If the read is ok
				if (result_read_block == 1) {

					//Put the write mode
					strcpy(file->mode, "w");

					//Set some parameters
					file->block_offset = 0;
					file->file_offset = 0;
					file->current_block = 0;

					//Return ok
					return 0;
				}

			}
		}
	}

	//If another errors
	return -1;
}



/*
 * SYNOPSYS :
 * 	 int syr1_fwrite(SYR1_FILE *file, int item_size, int nbitem, char* buf)
 * DESCRIPTION :
 *   Ce sous-programme écrit nbitem articles de taille item_size dans le fichier
 *   fichier paramètre à partir du tampon mémoire.
 * PARAMETRES :
 *  	 file : pointeur sur un Bloc Control Fichier (File Control Bloc)
 *  item_size : taille d'un article
 *	nb_item : nombre d'article à lire
 * RESULTAT :
 *	le nombre d'articles effectivement écrits dans le fichier, sinon un code
 *	d'erreur (cf syr1_putc())
 */
int syr1_fwrite(SYR1_FILE* file, int item_size, int nbitem, char *buffer)  {
	int count = 0;
	while (count<nbitem*item_size) {
		int res = syr1_putc(buffer[count],file);
		if (res<0) {
			return res;
		}
		count++;
	}
	return count;
}



/*
 * SYNOPSYS :
 * 	 int syr1_putc(unsigned char c, SYR1_FILE *file)
 * DESCRIPTION :
 *   Ce sous-programme écrit un caractère dans le fichier passé en paramètre.
 * PARAMETRES :
 *   file : pointeur sur un Bloc Control Fichier (File Control Bloc)
 *	  c : caractère à écrire
 * RESULTAT :
 *	 0 : écriture réussie
 *	-1 : le descripteur de fichier logique est NULL, ou le mode d'ouverture
 *		 du fichier passée en paramètre est incorrect
 *	-2 : erreur d'entrée-sorties sur le périphérique de stockage
 *	-3 : taille maximum de fichier atteinte
 *	-4 : plus de blocs disques libres
 */
int syr1_putc(unsigned char c, SYR1_FILE* file)  {
	//Only if there's a file passed
	if (file != NULL) {

		//Check the mode
		if (strcmp(file->mode, "w") == 0) {

			//If we can write here
			if (file->block_offset < (IO_BLOCK_SIZE/sizeof(char))) {

				//Write the char
				file->buffer[file->block_offset] = (int)c;

				//Increment the BCF parameters
				file->block_offset++;
				file->file_offset++;

				//Ok here
				return 0;
			}

			//If we have writen all the current block
			else {

				//First, we save the current block
				int result_write_block = write_block(file->descriptor.alloc[file->current_block], file->buffer);

				//If ok
				if (result_write_block == 1) {

					//If we have reached the maximum size of a file
					if (file->current_block == MAX_BLOCK_PER_FILE) {
						return -3;
					}

					//Try to get a new free block
					int result_get_free_block = get_allocation_unit();

					//If ok
					if (result_get_free_block >= 0) {

						//Increment the current block
						file->current_block++;

						//Put the block number in the implementation table
						file->descriptor.alloc[file->current_block] = result_get_free_block;

						//Load the block in the buffer
						int result_read_block = read_block(result_get_free_block, file->buffer);

						//If the read is ok
						if (result_read_block == 1) {
							//Set some parameters
							file->block_offset = 0;
							file->file_offset++;

							//Then write the char in this new block
							return syr1_putc(c, file);
						}

					}

					//If no more free blocks
					if (result_get_free_block == -1) {
						return -4;
					}
				}

				//If I/O error
				return -2;

			}

		}
	}

	//If no BCF or the mode isn't correct
	return -1;
}



/* SYNOPSYS :
 * 	  int syr1_fclose_write(SYR1_FILE* file) {
 * DESCRIPTION :
 *   Ce sous-programme gère la fermeture d'un fichier logique.
 * PARAMETRES :
 *   file : pointeur sur un Bloc de Contrôle Fichier (BCF)
 * RESULTAT :
 *	0 : la fermeture a réussi
 *   -1 : problème pendant la libération du descripteur de fichier logique
 *		(ou le fichier logiques file vaut NULL)
 *   -2 : erreur d'entrée-sorties sur le périphérique de stockage
 */
int syr1_fclose_write(SYR1_FILE* file) {
	//Here we have all the working case
	if (file != NULL) {

		//Update the file descriptor
		int result_update_entry = update_entry(&(file->descriptor));

		//If ok
		if (result_update_entry == 0) {
			return free_logical_file(file);
		}

		//If not, return the error
		else {
			return result_update_entry;
		}
	}

	//If errors
	return -1;
}
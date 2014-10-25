#ifndef SYR1_FILE_H_
#define SYR1_FILE_H_

#define MAX_ARTICLE_SIZE 128 // taille maximum d'un article (ici 128)*/
#define MAX_OPENED_FILE 10 // nombre maximum de fichiers logiques (ici 10)*

#include<stdio.h>
#include<directory.h>
#include<physical_io.h>

/*
 * Type structuré décrivant une Bloc Contrôle Fichier (BCF)
 *
 * Le type SYR1_FILE décrit une entrée de la table des BCF, qui
 * contient les informations d'accès (position, tampon d'E/S) à un
 * fichier pour un contexte d'exécution donnée. */

 typedef struct {
      /** Copie en mémoire du contenu du descripteur du fichier physique */
      file_descriptor descriptor;
      /** Adresse du tampon d'Entrée/Sortie */
      unsigned char* buffer;
      /** Mode d'ouverture du fichier ("r" ou "w")*/
      char mode[3];
      /** Le numéro du bloc de données en cours d'utilisation par le BCF*/
      int current_block;
      /** Le numéro du caractère courant dans le fichier logique */
      int file_offset;
      /** Le numéro du caractère courant à l'intérieur du bloc courant */
      int block_offset;
} SYR1_FILE ;


/*
 * SYNOPSYS :
 * 	 SYR1_FILE* alloc_logical_file(char* nom, char* mode)
 * DESCRIPTION :
 *	 alloue une entrée dans la table des descripteurs de fichiers logiques,
 *   la fonction s'assure que le fichier de nom externe nom n'est pas déjà
 *   ouvert dans un mode incompatible avec le mode d'ouverture mode demandé.
 *   Par exemple, si on souhaite ouvrir le fichier toto.txt en mode écriture
 *   alors qu'il est déjà ouvert en lecture, la fonction retournera une erreur.
 * PARAMETRES :
 *   char * nom  : nom du fichier sur lequel on veut initialiser les accès
 *   char * mode : mode d'initilisation ("r" ou "w")
 * RESULTAT :
 *    un pointeur sur un descripteur de fichier logique en cas de
 *    succès, NULL en cas d'échec.
 */

SYR1_FILE* alloc_logical_file(char* nom, char* mode);


/*
 * SYNOPSYS :
 * 	 int free_logical_file(SYR1_FILE* file)
 * DESCRIPTION :
 *   Libère une entrée de la table des fichiers logiques
 * PARAMETRES :
 *   file : pointeur sur un descripteur de fichier logique
 * RESULTAT :
 *      0 : succès
 *     -1 : erreur
 */

int free_logical_file(SYR1_FILE* bcf);


/*
 * SYNOPSYS :
 * 	 int syr1_getc(SYR1_FILE *file)
 * DESCRIPTION :
 *   lit un caractère à partir du fichier passé en paramètre.
 * PARAMETRES :
 *   file : pointeur sur un descripteur de fichier logique (BCF)
 * RESULTAT :
 *  valeur (convertie en int) du caractère lu dans le fichier, sinon
 *    -1 : le BCF est NULL, ou le mode d'ouverture est incorrect
 *    -2 : erreur d'entrée-sortie sur le périphérique de stockage
 *    -3 : fin de fichier
 */

int syr1_getc(SYR1_FILE *file);


/*
 * SYNOPSYS :
 * 	 int syr1_putc(unsigned char c, SYR1_FILE *file)
 * DESCRIPTION :
 *   écrit un caractère dans le fichier passé en paramètre.
 * PARAMETRES :
 *   file : pointeur sur un Bloc de Contrôle Fichier (BCF)
 *      c : caractère à écrire
 * RESULTAT :
 *     0 : écriture réussie
 *    -1 : le BCF est NULL, ou le mode d'ouverture
 *         du fichier passé en paramètre est incorrect
 *    -2 : erreur d'entrée-sortie sur le périphérique de stockage
 *    -3 : fin de fichier
 *    -4 : plus de blocs disques disponibles
 */

int syr1_putc(unsigned char c, SYR1_FILE* file);



/*
 * SYNOPSYS :
 * 	 int syr1_fread(SYR1_FILE *file, int item_size, int nbitem, char* buf)
 * DESCRIPTION :
 *   lit nbitem articles de taille item_size dans le fichier
 *   fichier logique passé en paramètre.
 * PARAMETRES :
 *   	 file : pointeur sur un Bloc de Contrôle Fichier (BCF)
 *  item_size : taille d'un article
 *    nb_item : nombre d'articles à lire
 * RESULTAT :
 *    Le nombre d'items effectivement lus, ou un code d'erreur en cas d'echec
 *    de la lecture :
 *    -1 : le BCF est NULL, ou le mode d'ouverture
 *         du fichier passé en paramètre est incorrect
 *    -2 : erreur d'entrée-sortie sur le périphérique de stockage
 *    -3 : fin de fichier
 */

int syr1_fread(SYR1_FILE* file, int item_size, int nbitem, char *buffer);


/*
 * SYNOPSYS :
 * 	 int syr1_fwrite(SYR1_FILE *file, int item_size, int nbitem, char* buf)
 * DESCRIPTION :
 *   écrit nbitem articles de taille item_size dans le fichier
 *   fichier logique passé en paramètre.
 * PARAMETRES :
 *  	 file : pointeur sur un Bloc de Contrôle Fichier (BCF)
 *  item_size : taille d'un article
 *    nb_item : nombre d'articles à lire
 * RESULTAT :
 *    le nombre d'articles effectivement écrits dans le fichier, sinon un code
 *    d'erreur :
 *    -1 : le BCF est NULL, ou le mode d'ouverture
 *         du fichier passée en paramètre est incorrect
 *    -2 : erreur d'entrée-sorties sur le périphérique de stockage
 *    -3 : fin de fichier
 */

int syr1_fwrite(SYR1_FILE* file, int item_size, int nbitem, char *buffer);



/* SYNOPSYS :
 * 	  SYR1_FILE*  syr1_fopen(char *name, char *mode) {
 * DESCRIPTION :
 *   gère l'ouverture d'un fichier logique.
 * PARAMETRES :
 *   name : chaîne de caratères contenant le nom externe du fichier à ouvrir
 *   mode : chaîne de caratères spécifiant le mode d'ouverture ("r" ou "w")
 * RESULTAT :
 *   La fonction retourne un pointeur sur une entrée de la table des Blocs
 *   Contrôle Fichiers en cas d'ouverture réussie et la valeur NULL en cas
 *   d'échec (un message d'erreur est affiché pour indiquer l'origine de l'erreur).
 */

SYR1_FILE* syr1_fopen(char * nom, char * mode);



/* SYNOPSYS :
 * 	  int syr1_fclose(SYR1_FILE* file) {
 * DESCRIPTION :
 *   gère la fermeture d'un fichier logique.
 * PARAMETRES :
 *   file : pointeur sur un Bloc de Contrôle Fichier (BCF)
 * RESULTAT :
*     0 : la fermeture a réussi
 *   -1 : problème pendant la libération du descripteur de fichier logique
 *        (ou le fichier logique file vaut NULL)
 *   -2 : erreur d'entrée-sortie sur le périphérique de stockage
 */

int syr1_fclose(SYR1_FILE* file);

void print_logical_file(FILE* stream,SYR1_FILE*  file);

#endif /*SYR1_FILE_H_*/

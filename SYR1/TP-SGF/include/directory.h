#ifndef DIRECTORY_H_
#define DIRECTORY_H_

#include<physical_io.h>
#include<stdio.h>

#define FREE_ENTRY 'F'
#define USED_ENTRY 'U'

#define FREE_BLOCK 'F'
#define USED_BLOCK 'U'

// Info sur l'organisation  sur le disque des blocs de la table d'allocation
#define ALLOCTABLE_START 0
#define ALLOCTABLE_SIZE  NB_DISK_BLOCKS
#define ALLOCTABLE_END   (ALLOCTABLE_START+(NB_DISK_BLOCKS/IO_BLOCK_SIZE)-1)

// Info sur l'organisation sur le disque des blocs "directory"
#define DIRECTORY_START (ALLOCTABLE_END+1)
#define DIRECTORY_SIZE  248
#define DIRECTORY_END   (DIRECTORY_START+DIRECTORY_SIZE-1)

// Info sur l'organisation sur le disque des blocs de données
#define DATABLOCKS_START (DIRECTORY_END+1)
#define DATABLOCKS_SIZE  (NB_DISK_BLOCKS-ALLOCTABLE_END-1)
#define DATABLOCKS_END   (NB_DISK_BLOCKS-1)

#define FILENAME_SIZE 16
#define MAX_BLOCK_PER_FILE ((IO_BLOCK_SIZE-8-FILENAME_SIZE)/sizeof(int))

/* Définition de la structure permettant de stocker les 
 * les informations permanentes d'un fichier (descripteur 
 * de fichier)
 */

typedef struct {
  char name[FILENAME_SIZE];      // Nom externe du fichier
  int size;                      // Taille en octets du fichier
  int alloc[MAX_BLOCK_PER_FILE]; // Table d'allocation des blocs de données
} file_descriptor;

/* Définition de la stucture modélisant une entrée 
 * du catalogue
 */

typedef struct {
  int free;             // Marqueur d'emplacement (libre/occupé)
  file_descriptor desc; // Descripteur de fichier
} dir_entry;


/*
 * SYNOPSYS :
 * 	 int search_entry(char* name, file_descriptor* desc)
 * DESCRIPTION :
 *   Recherche sur le catalogue d'un fichier de nom externe name. Si ce
 *   fichier existe dans le catalogue, on recopie en mémoire les informations
 *   du descripteur du fichier à l'adresse desc passée en paramètre.
 * PARAMETRES :
 *    name : nom externe du fichier
 *    desc : pointeur sur la copie mémoire du descripteur de fichier physique
 * RESULTAT :
 *     0 : recherche réussie
 *    -1 : pas de fichier physique correspondant
 *    -2 : Erreur d'entrée/sortie sur le périphérique de stockage
 */

int search_entry(char* name, file_descriptor* desc);


/*
 * SYNOPSYS :
 * 	 int update_entry(file_descriptor* desc)
 * DESCRIPTION :
 *   Mise à jour (sur disque) du catalogue à partir d'une copie mémoire d'un descripteur
 *   de fichier dont l'adresse est passée en paramètre.
 * PARAMETRES :
 *    desc : pointeur sur la copie mémoire du descripteur de fichier physique
 * RESULTAT :
 *     0 : mise à jour réussie
 *    -1 : pas de fichier physique correspondant
 *    -2 : Erreur d'entrée/sortie sur le périphérique de stockage
 */

int update_entry(file_descriptor* desc);


/*
 * SYNOPSYS :
 * 	 int create_entry(char* name, file_descriptor* desc)
 * DESCRIPTION :
 *   Crée une nouvelle entrée (sur le disque) dans le catalogue pour le fichier name,
 *   et copie le contenu du descripteur de fichier associé à cette nouvelle entrée à
 *   l'adresse desc passée en paramètre.
 * PARAMETRES :
 *   name   : nom externe du fichier à créer dans le catalogue
 *    desc  : pointeur sur la copie mémoire du descripteur de fichier physique
 * RESULTAT :
 *     0 : création réussie
 *    -1 : le nom externe est trop long
 *    -2 : Erreur d'entrée/sortie sur le périphérique de stockage
 *    -3 : un fichier avec le même nom existe déjà
 *    -4 : Plus de place dans le catalogue
 */

int create_entry(char* name, file_descriptor* desc);


/*
 * SYNOPSYS :
 * 	 int remove_entry(char* name)
 * DESCRIPTION :
 *   Suppresion (sur le disque) de l'entrée catalogue d'un fichier 
 *   dont le nom externe est passé en paramètre. Les blocs de données
 *   alloués a ce fichier sont libérés.
 * PARAMETRES :
 *   name     : nom externe du fichier à créer dan le catalogue
 * RESULTAT :
 *     0 : suppression réussie 
 *    -1 : le fichier n'existe pas
 *    -2 : Erreur d'entrée/sortie sur le périphérique de stockage
 *    -3 : Incohérence dans la table des blocs libres/occupés
 */

int remove_entry(char* name);


/*
 * SYNOPSYS :
 * 	 void print_entry(file_descriptor *entry);
 * DESCRIPTION :
 *   Affiche sur la S.S les informations contenues dans le descripteur de
 *   fichier passé en paramètre.
 * PARAMETRES :
 *   entry    : pointeur sur le descripteur de fichier
 * RESULTAT :
 *     Aucun
 */

void print_entry(file_descriptor *entry);


/*
 * SYNOPSYS :
 * 	 int get_allocation_unit()
 * DESCRIPTION :
 *   Retourne un numéro de bloc de données libre, et modifie en conséquence la
 *   table des blocs de données libres stockée sur le disque.
 * PARAMETRES :
 *	 aucun
 * RESULTAT :
 *	 en cas de succès, retourne l'adrese disque du bloc de donnée alloué
 *	 -1 : Il n'y a plus de blocs de données disponible dans le SGF.
 *	 -2 : Erreur d'E/S sur le périphérique de stockage
 */

int get_allocation_unit();


/*
 * SYNOPSYS :
 * 	 int free_allocation_unit(int data_block_id)
 * DESCRIPTION :
 *   Libère un bloc de données occupé (dont on a passé le numéro en paramètre),
 *   et modifie en conséquence la table des blocs libres sur le disque.
 * PARAMETRES :
 *	 data_block_id : adresse disque du bloc de données à libérer
 * RESULTAT :
 *	  0 : Si la libération a réussie.
 *	 -1 : Si le bloc à libérer est déjà libre.
 *	 -2 : Si erreur d'E/S sur le périphérique de stockage
 */

int free_allocation_unit(int data_block_id);





#endif /*DIRECTORY_H_*/



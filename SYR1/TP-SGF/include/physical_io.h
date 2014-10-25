#define IMAGE "disk.img"
#define IO_BLOCK_SIZE 512
#define NB_DISK_BLOCKS 4096

extern int verbose;

extern int physical_io_count;

/*
 * SYNOPSYS :
 * 	 int read_block(int disk_addr, unsigned char *buffer)
 * DESCRIPTION :
 *   Lecture d'un bloc d'entrée/sortie sur le périphérique de stockage
 * PARAMETRES :
 *   disk_addr : adresse disque du bloc à lire
 *      buffer : pointeur sur un tampon mémoire
 * RESULTAT :
 *    1 : lecture réussie
 *   -1 : impossible de lire le bloc demandé
 *   -2 : impossible d'accéder au périphérique de stockage
 */

int read_block(int disk_addr, unsigned char *buffer);


/*
 * SYNOPSYS :
 * 	 int write_block(int disk_addr, unsigned char *buffer)
 * DESCRIPTION :
 *    Ecriture d'un bloc d'entrée/sortie sur le périphérique de stockage
 * PARAMETRES :
 *   disk_addr : adresse disque du bloc à écrire
 *      buffer : pointeur sur un tampon mémoire
 * RESULTAT :
 *    1 : écriture réussie
 *   -1 : impossible d'écrire le bloc demandé
 *   -2 : impossible d'accéder au périphérique de stockage
 */

int write_block(int disk_addr, unsigned char *buffer);

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<syr1_file.h>

/* SYNOPSYS :
 * 	  int syr1_fopen_write(char *name, SYR1_FILE *file) {
 * DESCRIPTION :
 *   Ce sous-programme gère l'ouverture d'un fichier logique en mode écriture.
 * PARAMETRES :
 *   name : chaîne de caratère contenant le nom externe du fichier à ouvrir
 *   file : pointeur sur un Bloc Control Fichier (File Control Bloc)
 * RESULTAT :
 *    0 : ouverture réussie
 *   -1 : autre erreur
 */
int syr1_fopen_write(char *name, SYR1_FILE *file) {
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
 *    nb_item : nombre d'article à lire
 * RESULTAT :
 *    le nombre d'articles effectivement écrits dans le fichier, sinon un code
 *    d'erreur (cf syr1_putc())
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
 *      c : caractère à écrire
 * RESULTAT :
 *     0 : écriture réussie
 *    -1 : le descripteur de fichier logique est NULL, ou le mode d'ouverture
 *         du fichier passée en paramètre est incorrect
 *    -2 : erreur d'entrée-sorties sur le périphérique de stockage
 *    -3 : taille maximum de fichier atteinte
 *    -4 : plus de blocs disques libres
 */
int syr1_putc(unsigned char c, SYR1_FILE* file)  {
  return -1;
}



/* SYNOPSYS :
 * 	  int syr1_fclose_write(SYR1_FILE* file) {
 * DESCRIPTION :
 *   Ce sous-programme gère la fermeture d'un fichier logique.
 * PARAMETRES :
 *   file : pointeur sur un Bloc de Contrôle Fichier (BCF)
 * RESULTAT :
 *    0 : la fermeture a réussi
 *   -1 : problème pendant la libération du descripteur de fichier logique
 *        (ou le fichier logiques file vaut NULL)
 *   -2 : erreur d'entrée-sorties sur le périphérique de stockage
 */
int syr1_fclose_write(SYR1_FILE* file) {
  return -1;
}



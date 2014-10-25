#include<stdio.h>
#include<stdlib.h>
#include<syr1_file.h>


extern int physical_io_count;

int main(int argc, char **argv) {

  printf("Test d'ouvertue en lecture d'un fichier non present sur le disque\n");
  SYR1_FILE* file_r1=syr1_fopen("bidon.txt","r");
  if (file_r1==NULL) {
    printf("[OK] 	 Erreur d'ouverture pour le fichier bidon.txt\n");
  } else {
    printf("[FAILED] Ouverture de bidon.txt réussie \n");
  }
  fflush(stdout);

  printf("\nTest d'une 1ère ouverture en lecture d'un fichier présent sur le disque\n");
  file_r1=syr1_fopen("villon.txt","r");
  if (file_r1==NULL) {
    printf("[FAILED] Erreur d'ouverture en lecture pour un fichier existant sur le disque\n");
  } else {
    printf("[OK] 	 Ouverture n°1 de villon.txt réussie \n");
    printf("\nTest de lecture du contenu du fichier n°1\n");
    int nbcar= 0;
    int res;
    do {
      res = syr1_getc(file_r1);
      if (res<0) {
	if (res==-3) printf("\n\n[OK] Fin de fichier après 0x%04X caractères lus\n",nbcar);
	if (res==-2) printf("\n\n[FAILED] Problème d'E/S physiques\n");
	if (res==-1) printf("\n\n[FAILED] Problème de BCF\n");
      } else {
	nbcar++;
	printf("%c", (char)res);
      }
    } while (res>=0);
  }
  fflush(stdout);

  printf("Test d'une 2ème ouverture en lecture du fichier villon.txt\n");
  SYR1_FILE* file_r2=syr1_fopen("villon.txt","r");
  if (file_r2==NULL) {
    printf("[FAILED] Erreur d'ouverture en lecture pour un fichier existant sur le disque\n");
  } else {
    printf("[OK]     Ouverture n°2 de villon.txt réussie \n");
    printf("Test de lecture du contenu du fichier n°2\n");
    int nbcar= 0;
    int res;
    do {
      res = syr1_getc(file_r2);
      if (res<0) {
	if (res==-3) printf("\n\n[OK] Fin de fichier après 0x%04X caractères lus\n",nbcar);
	if (res==-2) printf("\n\n[FAILED] Problème d'E/S physiques\n");
	if (res==-1) printf("\n\n[FAILED] Problème de BCF\n");
      } else {
	nbcar++;
	//printf("%c", (char)res);
      }
    } while (res>=0);
  }
  fflush(stdout);
  syr1_fclose(file_r1);
  syr1_fclose(file_r2);

	printf("Nombre d'E/S physiques : %d\n",physical_io_count);

  return 0;
}

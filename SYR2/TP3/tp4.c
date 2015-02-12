#include <stdio.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <strings.h>

#define TAILLE 1024

void ecrire_tableau(int *compteur, char *tableau) {
  char message[64], *msg=message;
  snprintf(message, 64, "Je suis le processus %d!\n", getpid());

  while ((*compteur<TAILLE)&&(*msg)) {
    tableau[*compteur] = *msg;
    msg++;
    usleep(100000);
    (*compteur)++;
  }
}

int main() {
  int id, *compteur;
  char *tableau;
  key_t key = (key_t)1234;

  id = shmget(key,TAILLE+sizeof(int),0600|IPC_CREAT);  //Taille = Tableau et sizeof(int) = compteur
  if (id<0) { perror("Error shmget"); exit(1); }

  compteur = (int*) shmat(id,0,0);
  if (compteur==NULL) { perror("Error shmat"); exit(1); }

  tableau = (char *)(compteur + 1);
  
  ecrire_tableau(compteur, tableau);
  printf("%s\n", tableau);

  if (shmdt(compteur)<0) { perror("Error shmdt"); exit(1); }
  return 0;
}
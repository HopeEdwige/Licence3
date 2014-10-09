/**************************************************************************
 * L3Informatique						Module SYR1
 * 			TP de programmation en C :
 *		         Test de listes chaînées
 *
 * Groupe 	:
 * Nom Prénom 1 :
 * Nom Prénom 2 :
 *
 **************************************************************************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <termios.h>
#include <unistd.h>
#include "list.h"

/* Compter le nombre d'éléments de la liste paramètre */
static int
list_size(list_elem_t * p_list)
{
  int nb = 0;
  while (p_list != NULL) {
    nb += 1;
    p_list = p_list->next;
  }
  return nb;
}

/* Afficher le contenu de la liste paramètre */
void
print_list(list_elem_t* p_list) {
  list_elem_t * pl = p_list;
  printf("La liste contient %d élément(s)\n", list_size(p_list));
  while(pl != NULL) {
    printf("[%d]",pl->value);
    pl = pl->next;
    if (pl != NULL) {
      printf("->");
    }
  }
}

/* compteur du nombre d'allocations */
extern int nb_malloc;

int
main(int argc, char **argv)
{
  list_elem_t * la_liste = NULL;	// Le pointeur de tête de liste
  char menu[] =
    "\n Programme de test de listes\n"\
    "  't/q' : ajout d'un élément en tête/queue de liste\n"\
    "  'f'   : recherche du ième élément de la liste\n"\
    "  's'   : suppression d'un élément de la liste\n"\
    "  'r'   : renverser l'ordre des éléments de la liste\n"\
    "  'x'   : quitter le programme\n"\
    " Quel est votre choix ?";
  int choix=0;				// choix dans le menu
  int value=0;				// valeur saisie

  printf("%s",menu);
  fflush(stdout);

  while(1) {
    fflush(stdin);
    choix = getchar();
    printf("\n");

    switch(choix) {
    case 'T' :
    case 't' :
      printf("Valeur du nouvel element ? ");
      scanf("%d",&value);
      if (insert_head(&la_liste,value)!=0) {
	printf("Erreur : impossible d'ajouter l'élément %d\n",value);
      };
      break;

    case 'Q' :
    case 'q' :
      printf("Valeur du nouvel element ? ");
      scanf("%d",&value);
      if (insert_tail(&la_liste,value)!=0) {
        printf("Erreur : impossible d'ajouter l'élément %d\n en fin de liste",value);
      }
      break;


    case 'F' :
    case 'f' :
      printf("Index de l'élément à rechercher ? \n");
      scanf("%d",&value);
      list_elem_t* elem = find_element(la_liste,value);
      if (elem==NULL) {
        printf("Erreur : impossible de retrouver l'élément à l'index %d\n",value);
      }
      else {
        printf("L'élément à l'index %d est [%d]\n", value, elem->value);
      }
      break;



    case 's' :
    case 'S' :
      printf("Valeur de l'élément à supprimer ? ");
      scanf("%d",&value);
      if (remove_element(&la_liste,value)!=0) {
        printf("Erreur : impossible de supprimer l'élément à de valeur %d\n",value);
      };
      break;


    case 'r' :
    case 'R' :
      reverse_list(&la_liste);
      break;


    case 'x' :
    case 'X' :
      return 0;

    default:
      break;
    }
    print_list(la_liste);

    if (nb_malloc!=list_size(la_liste)) {
      printf("\nAttention : il y a une fuite mémoire dans votre programme !\n");
      printf("la liste contient %d élement, or il y a %d élément alloués en mémoire !\n",
	     list_size(la_liste),nb_malloc);
    }
    getchar(); // pour consommer un RC et éviter un double affichage du menu !
    printf("%s\n",menu);
  }
  return 0;
}
/**************************************************************************
 * L3Informatique						Module SYR1
 * 			TP de programmation en C
 *			Mise en oeuvre de listes chaînées
 *
 * Groupe 	:  2.2
 * Nom Prénom 1 :  DANG Minh Anh
 * Nom Prénom 2 :  ANDRIAMILANTO Tompoariniaina
 *
 **************************************************************************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "list.h"

int nb_malloc = 0;	// compteur global du nombre d'allocations

/*
 * Je modifie pour tester
 * SYNOPSYS    :
 *   list_elem_t * create_element(int value)
 * DESCRIPTION :
 *   crée un nouveau maillon de liste, dont le champ next a été initialisé à NULL, et
 *   dont le champ value contient l'entier passé en paramètre.
 * PARAMETRES :
 *   value : valeur de l'élément
 * RESULTAT :
 *   NULL en cas d'échec, sinon un pointeur sur une structure de type list_elem_t
 */
static list_elem_t *
create_element (int value)
{
  list_elem_t * newelt = malloc (sizeof (list_elem_t));
  if (newelt != NULL) {
    ++nb_malloc;
    newelt->value = value;
    newelt->next = NULL;
  }
  return newelt;
}

/*
 * SYNOPSYS    :
 *   void free_element(list_elem_t * l)
 * DESCRIPTION :
 *   libère un maillon de liste.
 * PARAMETRES  :
 *   l : pointeur sur le maillon à libérer
 * RESULTAT    :
 *   rien
 */
static void
free_element (list_elem_t * l)
{
  --nb_malloc;
  free (l);
}

/*
 * SYNOPSYS :
 *   int insert_head(list_elem_t * * l, int value)
 * DESCRIPTION :
 *   Ajoute un élément en tête de liste, à l'issue de l'exécution de la fonction,
 *   *l désigne la nouvelle tête de liste.
 * PARAMETRES :
 *   list_elem_t ** l : pointeur sur le  pointeur de tête de liste
 *   int value : valeur de l'élément à ajouter
 * RESULTAT :
 *    0 en cas de succès.
 *   -1 si l'ajout est impossible.
 */
int insert_head (list_elem_t * * l, int value)
{
  if (l == NULL) { return -1; }
  list_elem_t * new_elt = create_element (value);
  if (new_elt == NULL) { return -1; }

  new_elt->next = *l;
  *l = new_elt;
  return 0;
}


/*
 * SYNOPSYS :
 *   list_elem_t * get_tail(list_elem_t * l)
 * DESCRIPTION :
 *    Retourne un pointeur vers le dernier élément de la liste
 * PARAMETRES :
 *   list_elem_t * * l : pointeur sur l'élément de départ
 * RESULTAT :
 *   Renvoie le dernier élement, si erreur ou non présent, renvoie NULL
 */
list_elem_t * get_tail(list_elem_t * l) {
  //Create the iterator element
  list_elem_t * tmp = l;

  //Then travel the list
  if (tmp != NULL) {
    while (tmp->next != NULL) {
      tmp = tmp->next;
    }
  }
  return tmp;
}


/*
 * SYNOPSYS :
 *   int insert_tail(list_elem_t * * l, int value)
 * DESCRIPTION :
 *   Ajoute un élément en queue de la liste (* l désigne la tête de liste)
 * PARAMETRES :
 *   list_elem_t ** l : pointeur sur le pointeur de tête de liste
 *   int value : valeur de l'élément à ajouter
 * RESULTAT :
 *    0 en cas de succès.
 *   -1 si l'ajout est impossible.
 */
int insert_tail(list_elem_t * * l, int value) {
  //Error if the pointer of pointer is null
  if (l==NULL) {
    return -1;
  }

  //Create the new element with the value enterred
  list_elem_t * nouveau = create_element(value);

  //If the element creation does an error 
  if (nouveau == NULL) {
    return -1;
  }

  //If it's the first element of this list
  if (*l == NULL) {
    *l = nouveau;
    return 0;
  }

  //Put the new element as tail of the list
  else {
    list_elem_t * tail = get_tail(*l);

    if(tail == NULL) {
      return -1;
    }

    tail->next = nouveau;
    return 0;
  }
}


/*
 * SYNOPSYS :
 *   list_elem_t * find_element(list_elem_t * l, int index)
 * DESCRIPTION :
 *   Retourne un pointeur sur le maillon à la position n°i de la liste 
 *   (le 1er élément est situé à la position 0).
 * PARAMETRES :
 *   int index : position de l'élément à retrouver
 *   list_elem_t * l : pointeur sur la tête de liste
 * RESULTAT :
 *   - un pointeur sur le maillon de la liste en cas de succès
 *   - NULL si erreur
 */
list_elem_t * find_element(list_elem_t * l, int index) {
  //If the list is empty
  if (l==NULL) {
    return NULL;
  }

  //If another case
  else {
    int i = 0;
    list_elem_t* tmp = l;
    while ((i < index) && (tmp != NULL)) {
      i++;
      tmp = tmp->next;
    }
    return tmp;
  }
}


/*
 * SYNOPSYS :
 *   int remove_element(list_elem_t * * ppl, int value)
 * DESCRIPTION :
 *   Supprime de la liste (dont la tête a été passée en paramètre) le premier élément de
 *   valeur value, et libère l'espace mémoire utilisé par le maillon
 *   ainsi supprimé. 
 *   Attention : à l'issue de la fonction la tête de liste peut avoir été modifiée.
 * PARAMETRES :
 *   list_elem_t ** ppl : pointeur sur le  pointeur de tête de liste
 *   int value  : valeur à supprimer de la liste
 * RESULTAT :
 *    0 en cas de succès.
 *   -1 si erreur
 */
int remove_element(list_elem_t * * ppl, int value) {
  if (ppl==NULL) {
    return -1;
  }

  else if (*ppl==NULL) {
    return -1;
  }

  else {
    list_elem_t* tmp = *ppl;
    list_elem_t* last = tmp;
    while (tmp != NULL) {
      if (tmp->value == value) {
        last->next = tmp->next;
        free_element(tmp);
        return 0;
      }
      else {
        last = tmp;
        tmp = tmp->next;
      }
    }
    return 0;
  }
}


/*
 * SYNOPSYS :
 *   void reverse_list(list_elem_t * * l)
 * DESCRIPTION :
 *   Modifie la liste en renversant l'ordre de ses élements
 *   le 1er élément est placé en dernière position, le 2nd en
 *   avant dernière, etc.)
 * PARAMETRES :
 *   list_elem_t ** l : pointeur sur le pointeur de tête de liste
 * RESULTAT :
 *   aucun 
 */
void reverse_list(list_elem_t * * l) {
	if ((l != NULL) && (*l != NULL)) {
    //A counter
    int size = nb_malloc;
    int count = size - 1;

    //Check if there's more than 1 element
    if (size > 1) {
      //A table of pointers
      list_elem_t* table [size];

      //Then put all the pointers in it in reverse
      list_elem_t* tmp = *l;
      while (tmp != NULL) {
        table[count] = tmp;
        count--;
        tmp = tmp->next;
      }

      //Link the pointer to their true next
      int i = 0;
      while (i < size-2) {
        table[i]->next = table[i+1];
        i++;
      }

      //Put NULL to the last element
      table[size-1]->next = NULL;
    }
  }
}
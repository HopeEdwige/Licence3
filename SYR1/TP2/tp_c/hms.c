/*  Lit un nombre de secondes et le decompose en   */
/*  heures, minutes et secondes                    */

#include <stdio.h>

#define SECPARMN 60L			/* nb de sec dans 1 mn  */
#define MNPARH   60			/* nb de mn dans 1 h    */
#define HPARJ    24			/* nb d'heures par jour */
#define SECPARH (SECPARMN * MNPARH)	/* nb de sec par heure  */
#define SECPARJ (SECPARH * HPARJ)	/* nb de sec par jour   */

int main ()
{
  unsigned long int secondes, reste;
  int jours, heures, minutes;

  printf ("Nombre de secondes ? ");
  scanf ("%lu", &secondes);

  jours = secondes / SECPARJ;
  reste = secondes % SECPARJ;
  heures = reste / SECPARH;
  reste %= SECPARH;
  minutes = reste / SECPARMN;
  reste %= SECPARMN;

  printf ("%lu secondes valent %d jours, %d heures, %d mn et %lu s\n", secondes, jours, heures, minutes, reste);

  return 0;
}

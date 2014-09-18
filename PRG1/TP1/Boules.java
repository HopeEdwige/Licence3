/**
 * Class for the TP1 of PRG1
 * The goal was to sort some balls of different color
 * that are enterred by the user
 * CARE: The Ecriture and Lecture classes aren't given
 */
public class Boules {

//Attributes
	private static int nombreBoules;
	private static char[] tableauBoules;
	

//Functions
	/**
	 * Static method to run the application
	 */
	public static void main(String[] args) {
		//Get the number of balls
		Ecriture.ecrireString("Entrez le nombre de boules voulues : ");
		nombreBoules = Lecture.lireInt();

		//Get the list of balls
		Ecriture.ecrireString("Suite des " + nombreBoules + " boules : ");
		tableauBoules = lireTableauBoules();

		//Some variables that are required to sort all of this correctly
		int r = 0, s = 0, t = nombreBoules -1;

		//While there is some more balls to sort
		while (s <= t) {

			//In function of the current ball to sort
			switch (tableauBoules[s]) {

				//If a green one
				case 'v':
					if (tableauBoules[r] == 'v'){
						r++;
					} else {
						echange(r, s, tableauBoules);
						r++;
						s++;
					}
					break;
					
				//If a white one
				case 'b':
					s++;
					break;
				
				//If a red one
				case 'r':
					if (tableauBoules[t] == 'r'){
						t--;
					} else {
						echange(s, t, tableauBoules);
						t--;
					}
					break;
					
				//If there's an error
				default:
					Ecriture.ecrireString("Erreur : s = " + s + ", boule = " + tableauBoules[s]);
					System.exit(0);
					break;
			}
			
			//Display a picture at the moment
			photo(r, s, t, tableauBoules);
		}

		//And in the end, display the result
		Ecriture.ecrireString("Résultat du tri : ");
		ecrireTableauBoules(tableauBoules);
		Ecriture.ecrireStringln("");
	}
	

	/**
	 * Read the list of balls enterred by the user
	 * @return The list of balls stored in a table
	 */
	private static char[] lireTableauBoules() {
		char[] tab = new char[nombreBoules];
		int i = 0;
		while (i < nombreBoules) {
			char c = Lecture.lireChar();
			tab[i] = c;
			
			if (c == '\n') {
				i--;
			} else {
				i++;
			}
		}
		return tab;
	}
	

	/**
	 * Write the list of balls on the output screen
	 * @param tab The list of balls stored in a table
	 */
	private static void ecrireTableauBoules(char[] tab) {
		for ( int i = 0; i < nombreBoules; i++) {
			Ecriture.ecrireChar(tab[i]);
		}
	}
	

	/**
	 * Switch two balls in the table
	 * @param i The index of the first one
	 * @param j The index of the second
	 * @param tab The balls table 
	 */
	private static void echange(int i, int j, char[] tab) {
		char c = tab[i];
		tab[i] = tab[j];
		tab[j] = c;
	}
	

	/**
	 * Take a picture at a given moment
	 * @param r The first index that we need to sort it
	 * @param s The second
	 * @param t The Third
	 * @param tab The balls table
	 */
	private static void photo(int r, int s, int t, char[] tab) {
		Ecriture.ecrireStringln("r = " + r + ", s = " + s + ", t = " + t);
		ecrireTableauBoules(tab);
		Ecriture.ecrireStringln("");
	}
}
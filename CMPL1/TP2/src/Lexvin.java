import java.io.*;
import java.awt.*;


/**
 * Class for the lexical analyse
 */
public class Lexvin {

	// codage des items
	public static final int BEAUJOLAIS = 0, BOURGOGNE = 1, IDENT = 2, NBENTIER = 3, VIRGULE = 4, PTVIRG = 5, BARRE = 6, AUTRES = 7 ;
	
	// Item table
	public static final String[] item = {
	   "BEAUJ", "BOURG", "IDENT", "NBENT", "  ,  ", "  ;  ", "  /  ", "AUTRE" 
	};

	public static int valNb, numId; // attributs lexicaux
	private static InputStream f; // fichier logique d'entree
	private static TextArea fen; // fenetre d'entree en cours d'analyse
	private static char carlu; // caractere courant
	private static final int NBRES = 2; // nombre de mots reserves
	private static final int MAXID = 200; // nombre maximum d'ident
	private static String[] tabid = new String[MAXID+NBRES]; // table des mots reserves suivis des ident
	private static int itab; // indice de remplissage de tabid


	/**
	 * Wait that the user press enter to continue
	 * @param String mess The message to display
	 */
	private static void attenteSurLecture(String mess) {
		String tempo;
		System.out.println("");
		System.out.print(mess + " pour continuer tapez entree ");
		tempo = Lecture.lireString();
	} // attenteSurLecture


    /**
     * Begin the lexical analyse
     * @param TextArea fenentree The window to display it
     */
	public static void debutAnalyse(TextArea fenentree) {
		String nomfich;
		fen = fenentree;
		carlu = ' ';
        itab = NBRES-1;
		tabid[0] = "BEAUJOLAIS"; tabid[1] = "BOURGOGNE";
		System.out.print("nom du fichier d'entree : ");
		nomfich = Lecture.lireString();
		f = Lecture.ouvrir(nomfich);
		if (f == null) {
			attenteSurLecture("fichier " + nomfich + " inexistant");
			System.exit(0);
		}
	} // debutAnalyse


    /**
     * End an analyse
     */
	public static void finAnalyse() {
    	Lecture.fermer(f);
    	attenteSurLecture("fin d'analyse");
	} // finAnalyse


    /**
     * Read the next char
     */
	private static void lirecar() {
    	carlu = Lecture.lireChar(f); 
    	if (carlu == '\r')
            carlu = ' ' ;
    	fen.append("" + carlu); // la valeur carlu est transformee en chaine 
    	if (Character.isWhitespace(carlu))
            carlu = ' ';
    	else
            carlu = Character.toUpperCase(carlu);
	} // lirecar


    /**
     * Get the value of an element of the id table
     * @param int nid The ident
     * @return String The value or an error message
     */
	public static String repId(int nid) {
		if ((nid >= NBRES) && (nid <= itab))
            return tabid[nid];
		else
            return "Error 404 : String not found";
	} // repId


    /**
     * Read the next char
     * @return int The type of the char read
     */
	public static int liresymb() {
		while (carlu == ' ')
            lirecar();

		String symb = "" + carlu;

		if (symb.equals(",")) {
			lirecar();
            return VIRGULE;
		}
		if (symb.equals(";")) {
			lirecar();
            return PTVIRG;
		}
		if (symb.equals("/")) {
			lirecar();
            return BARRE;
		}

		// If a number
		if ((carlu >= '0') && (carlu <= '9')) {

			// Read the whole number
			lirecar();
			while ((carlu >= '0') && (carlu <= '9')) {
				symb += carlu;
				lirecar();
			}

			// Then store it and return the nbentier ident
			valNb = Integer.parseInt(symb);
			return NBENTIER;
		}

		// If a character
		if ((carlu >= 'A') && (carlu <= 'Z')) {

			// Read the whole word
			lirecar();
			while ((carlu >= 'A') && (carlu <= 'Z')) {
				symb += carlu;
				lirecar();
			}

			// If one of the two type of wine
			if (symb.equals("BEAUJOLAIS"))
                return BEAUJOLAIS;
			if (symb.equals("BOURGOGNE"))
                return BOURGOGNE;

            // Check the word in the ident table
			int i = NBRES;
			boolean found = false;
			while ((i <= itab) && (!found)) {
				if (symb.equals(repId(i))) {
					numId = i;
					found = true;
				}
				i++;
			}

			// If a new word, add it to the ident table
			if ((!found) && (itab < (MAXID - 1))) {
				itab++;
				tabid[itab] = symb;
				numId = itab;
			}

			return IDENT;
		} else {
			return AUTRES;
		}
	} // liresymb


    /**
     * Unused main method
     */
	public static void main(String[] args) {
	   System.out.println("la classe Lexvin ne possede pas de 'main'.") ;
	} // main

} // class Lexvin
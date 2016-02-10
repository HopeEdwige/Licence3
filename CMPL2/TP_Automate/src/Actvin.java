/**
 * Class for the actions
 */
public class Actvin {

	// The action table
	public static final int[][] action = {
		/* etat		BJ   BG IDENT NBENT  ,	  ;	   /  AUTRES  */
		/* 0 */	  { 10,  10,  1,   10,  10,   0,   9,  10   },
		/* 1 */	  { 3,   4,   5,	2,  10,   0,  10,  10   },
		/* 2 */	  { 10,  10,  5,   10,  10,   0,  10,  10   },
		/* 3 */	  { 3,   4,   5,   10,  10,   0,  10,  10   },
		/* 4 */	  { 10,  10,  10,   6,  10,   0,  10,  10   },
		/* 5 */	  { 10,  10,  5,   10,   7,   8,  10,  10   },
		/* 6 */	  { 11,  11,  11,  11,  11,   0,  11,  11   }
	} ;		   

	// Ids for the error type
	private static final int FATALE = 0 , NONFATALE = 1;

	// Some max values
	private static final int MAXCHAUF = 10, MAXLGID = 20;

	// The table of all the chauffeurs we'll have here
	private static Chauffeur[] tabchauf = new Chauffeur[MAXCHAUF];

	// Default values for the chauffeur object parameters
	private static int numchauf, bj = 0, bg = 0, ordin = 0, type = 0, volume = 100, index_nextChauf = 0, already_encountered = -1;
	
	// Some counters to count the total number of each sort of wine
	private static int bj_total = 0, bg_total = 0, ordin_total = 0;

	// The shop's set
	private static SmallSet magdif = new SmallSet();


	/**
	 * Check if the chauffeur is contained in the chauffeur table
	 * @param Chauffeur[] tab The chauffeur table
	 * @param int iDchauf The id of the chauffeur to check
	 * @return int The index of the chauffeur or -1 if not found
	 */
	private static int containsChauffeur (Chauffeur[] tab, int iDchauf) {
		int i = 0;
		while (i < index_nextChauf) {
			if (tabchauf[i].numchauf == iDchauf) return i;
			i++;
		}
		return -1;
	}


	/**
	 * Just display the chauffeurs
	 */
	private static void printChauffeurs() {
		String result = "CHAUFFEUR                   BJ        BG       ORD       NBMAG\n";
		int i = 0;
		while (i < index_nextChauf) {
			Chauffeur current = tabchauf[i];
			result += chaineCadrageGauche(Lexvin.repId(current.numchauf))
					+ chaineCadrageDroite("" + current.bj)
					+ chaineCadrageDroite("" + current.bg)
					+ chaineCadrageDroite("" + current.ordin)
					+ chaineCadrageDroite("" + current.magdif.size()) + "\n";
			i++;
		}
		System.out.println(result);
	}


	/**
	 * Wait that the user press enter to continue
	 * @param String mess The message to display
	 */
	private static void attenteSurLecture(String mess) {
		String tempo;
		System.out.println("");
		System.out.print(mess + " pour continuer tapez entree ") ;
		tempo = Lecture.lireString() ;
	} // attenteSurLecture


	/**
	 * If an error is encountered
	 * @param int te The type of the error encountered
	 * @param String mess The message to display
	 */
	private static void erreur(int te, String messerr) {
		attenteSurLecture(messerr);
		switch (te) {
			case FATALE:
				Vin.errcontr = true;
				break;

			case NONFATALE:
				Vin.etat = Autovin.ETATERR;
				break;

			default:
				attenteSurLecture("parametre incorrect pour erreur");
		}
	} // erreur


	/**
	 * Format a string on 10 chars aligned on the left
	 * @param String ch The string to format
	 * @return The formated string
	 */
	private static String chaineCadrageGauche(String ch) {
		int lgch = Math.min(MAXLGID, ch.length());
		String chres = ch.substring(0, lgch);
		for (int k = lgch; k < MAXLGID; k++)
			chres = chres + " ";
		return chres;
	} // chaineCadrageGauche


	/**
	 * Format a string on 10 chars aligned on the right
	 * @param String ch The string to format
	 * @return The formated string
	 */
	private static String chaineCadrageDroite(String ch) {
		int lgch = Math.min(10, ch.length());
		String chres = ch.substring(0, lgch);
		while (chres.length() < 10)
			chres = " " + chres;
		return chres;
	} // chaineCadrageDroite


	/**
	 * Execute the correct action
	 * @param int numact The id of the action to execute
	 */
	public static void executer(int numact) {
		switch (numact) {
			case -1:
				break;

			// initialisations
			case 0:
				bj = 0; bg = 0; ordin = 0; type = 0; volume = 100;
				bj_total = 0; bg_total = 0; ordin_total = 0;
				break;

			// lecture ident nom chauffeur
			case 1:
				numchauf = Lexvin.numId;
				already_encountered = containsChauffeur(tabchauf, numchauf);
				if ((already_encountered == -1) && ((index_nextChauf + 1) == MAXCHAUF))
					erreur(FATALE, "Nombre maximum de chauffeurs atteint");
				break;

			// lecture volume citerne
			case 2:
				int vol = Lexvin.valNb;
				if ((100 < vol) && (vol <= 200))
					volume = vol;
				break;

			// cas du Beaujolais
			case 3:
				type = 1;
				break;

			// cas du Bourgogne
			case 4:
				type = 2;
				break;

			// lecture ident nom magasin
			case 5:
				if (!magdif.contains(Lexvin.numId))
					magdif.add(Lexvin.numId);
				break;

			// lecture quantite livree a ce magasin
			case 6:
				int qte = Lexvin.valNb;
				if (qte <= 0)
					erreur(NONFATALE, "Quantite livree incorrecte");
				switch (type) {
					case 0:
						ordin += qte;
						break;

					case 1:
						bj += qte;
						break;

					case 2:
						bg += qte;
						break;
				}
				break;

			// lecture ',' => fin de livraison courante
			case 7:
				if ((bj + bg + ordin) > volume)
					erreur(NONFATALE, "Quantite livree supeieure a la capacite de la citerne");
				switch (type) {
					case 0:
						ordin_total += ordin;
						ordin = 0;
						break;

					case 1:
						bj_total += bj;
						bj = 0;
						break;
					case 2:
						bg_total += bg;
						bg = 0;
						break;
				}
				type = 0;
				break;

			// lecture ';' => fin de fiche courante
			case 8:
				switch (type) {
					case 0:
						ordin_total += ordin;
						ordin = 0;
						break;

					case 1:
						bj_total += bj;
						bj = 0;
						break;
						
					case 2:
						bg_total += bg;
						bg = 0;
						break;
				}

				if (already_encountered == -1) {
					tabchauf[index_nextChauf] = new Chauffeur(numchauf, bj_total, bg_total, ordin_total, magdif.clone());
					index_nextChauf++;
				} else {
					Chauffeur current = tabchauf[already_encountered];
					current.bg += bg_total;
					current.bj += bj_total;
					current.ordin += ordin_total;
					current.magdif.union(magdif);
				}
				type = 0; volume = 100;
				bj_total = 0; bg_total = 0; ordin_total = 0;
				magdif.clear();
				printChauffeurs();
				break;

			// lecture '/' fin d'analyse
			case 9:
				int max = -1, vip = 0, i = 0;
				while (i < index_nextChauf) {
					Chauffeur current = tabchauf[i];
					if (current.magdif.size() > max) {
						max = current.magdif.size(); 
						vip = current.numchauf;
					}
					i++;
				}
				System.out.println("Le chauffeur ayant livre le plus de magasins differents ce mois-ci est : " + Lexvin.repId(vip));
				break;

			// lecture 
			case 10:
				erreur(NONFATALE, "Syntaxe invalide");
				break;

			case 11:
				break;

			default:
				attenteSurLecture("action " + numact + " non prevue");
		}
	} // executer


	/**
     * Unused main method
     */
	public static void main(String[] args) {
		System.out.println("La classe Actvin ne possede pas de 'main'.");
	} // main




	/**
	 * Inner class for the Chauffeur objects
	 */
	private static class Chauffeur {
		public int numchauf, bj, bg, ordin;
		public SmallSet magdif;

		/**
		 * The conscructor
		 */
		public Chauffeur(int numchauf, int bj, int bg, int ordin, SmallSet magdif) {
			this.numchauf = numchauf;
			this.bj = bj;
			this.bg = bg; 
			this.ordin = ordin;
			this.magdif = magdif.clone();
		}


		/**
		 * Get a copy of the current object
		 */
		public Chauffeur copie() {return new Chauffeur(numchauf, bj, bg, ordin, magdif);}
	} // class Chauffeur

} // class Actvin
import java.io.*;
import java.util.*;


public class Edl {
	static final int MAXMOD = 5, MAXOBJ = 1000;
	static final int FATALE = 0, NONFATALE = 1;
	static Descripteur[] tabDesc = new Descripteur[MAXMOD+1];
	static int ipo, nMod, nbErr;
	static String nomProg;

	// Les tableaux necessaires
	static String[] nomUnites = new String[MAXMOD+1];
	static int[] transDon = new int[MAXMOD+1];
	static int[] transCode = new int[MAXMOD+1];
	static int[][] adFinale = new int[MAXMOD+1][Descripteur.MAXDEF+1];
	static EltDef[] dicoDef = new EltDef[(MAXMOD+1)*Descripteur.MAXDEF];
	static int flagDicoDef = 0;


	/**
	 * Verifie si nomProc present dans dicoDef
	 */
	static int presentDico(String nomProc) {
		for (int i = 1; i <= flagDicoDef; i++) {
			if (nomProc.equals(dicoDef[i].nomProc))
				return i;
		}
		return 0;
	}


	/**
	 * Si erreur rencontree
	 * @param int te Le type de l'erreur
	 * @param String m Le message d'erreur
	 */
	static void erreur(int te, String m) {
		System.out.println(m);
		if (te == FATALE) {
			System.out.println("ABANDON DE L'EDITION DE LIENS");;
			System.exit(1);
		}
		nbErr = nbErr + 1;
	}


	/**
	 * Recupere les descripteurs et remplis les tables associees
	 */
	static void lireDescripteurs() {
		boolean existe = false;
		String s;
		System.out.println("les noms doivent etre fournis sans suffixe");
		System.out.print("nom du programme : ");
		s = Lecture.lireString();
		nomProg = s;

		// ----- Le programme principal -----
		tabDesc[0] = new Descripteur();
		tabDesc[0].lireDesc(s);
		if (!tabDesc[0].unite.equals("programme"))
			erreur(FATALE,"programme attendu");

		// Enregistre le nom du programme
		nomUnites[0] = s;


		// ----- Les modules -----
		while ((!s.equals("")) && (nMod < MAXMOD)) {

			// Demande le nom du prochain module
			System.out.print("nom de module " + (nMod + 1) + " (RC si termine) ");
			s = Lecture.lireString();

			// S'il n'est pas vide
			if (!s.equals("")) {

				// Incremente le flag du module
				nMod++;

				// Enregistre le nom du module
				nomUnites[nMod] = s;

				// Recupere le descripteur du module correspondant
				tabDesc[nMod] = new Descripteur();
				tabDesc[nMod].lireDesc(s);

				// Si pas un module, erreur
				if (!tabDesc[nMod].unite.equals("module"))
					erreur(FATALE, "module attendu");

				// Met a jour transDon et transCode
				transDon[nMod] = transDon[nMod-1] + tabDesc[nMod-1].tailleGlobaux;
				transCode[nMod] = transCode[nMod-1] + tabDesc[nMod-1].tailleCode;

				// Si tout est bon, ajoute tabDef au dictionnaire
				for (int i = 1; i <= tabDesc[nMod].nbDef; i++) {

					// Si sans dobule declaration
					if (presentDico(tabDesc[nMod].tabDef[i].nomProc) == 0) {

						// Recupere chaque ligne du tabDef et ajoute a l'@ de la proc l'ancien ipo
						dicoDef[flagDicoDef + i] = tabDesc[nMod].tabDef[i];
						dicoDef[flagDicoDef + i].adPo += transCode[nMod];
					}

					// Si double declaration
					else
						erreur(NONFATALE, "Double declaration de " + tabDesc[nMod].tabDef[i].nomProc);
				}

				// Incremente le falg de dico def
				flagDicoDef += tabDesc[nMod].nbDef;
			}
		}
	}


	/**
	 * Creer le fichier executable mapile
	 */
	static void constMap() {

		// Ouvre le fichier en écriture
		OutputStream f1 = Ecriture.ouvrir(nomProg + ".map");
		if (f1 == null) erreur(FATALE, "creation du fichier " + nomProg + ".map impossible");

		// Creer le tableau po
		int[] po = new int[(nMod + 1) * MAXOBJ + 1];



		// ######################### Parcours toutes les unites #########################
		for (int i = 0; i <= nMod; i++) {

			// Ouvre le fichier
			InputStream uniteCourant = Lecture.ouvrir(nomUnites[i] + ".obj");

			// Si probleme lors de l'ouverture du fichier
			if (uniteCourant == null) erreur(FATALE, "Fichier " + nomUnites[i] + ".obj illisible");


			// ######################### Recuperation des vecteurs de translation #########################
			// Creer un nouveau tableau associatif de transitions
			HashMap<Integer, Integer> tabTrans = new HashMap<Integer, Integer>();

			// Variables pour la lecture des transitions
			int adresse;
			int type_trans;

			// Recupere toutes les transitions
			for (int t = 0; t < tabDesc[i].nbTransExt; t++) {

				// Recupere chaque couples
				adresse = Lecture.lireInt(uniteCourant) + transCode[i];
				type_trans = Lecture.lireIntln(uniteCourant);

				// Ajoute le couple
				tabTrans.put(adresse, type_trans);
			}


			// ######################### Recuperation du code mapile brut #########################
			// Valeurs temporaire
			int ref_ext = 1, derniere_instruction = tabDesc[i].tailleCode;
			Integer res_get;

			// Ne prends pas la toute derniere instruction (vide)
			if (i == nMod) derniere_instruction = tabDesc[i].tailleCode-1;

			// Recupere et met dans po tout le code brut
			for (int p = 1; p <= derniere_instruction; p++) {

				// Recupere le code
				po[ipo] = Lecture.lireIntln(uniteCourant);

				// Si contenu dans le vecteur de translation
				res_get = tabTrans.get(ipo);
				if (res_get != null) {

					// En fonction du type de transition
					switch (res_get.intValue()) {

						case Descripteur.TRANSDON:
							po[ipo] += transDon[i];
							break;

						case Descripteur.TRANSCODE:
							po[ipo] += transCode[i];
							break;

						case Descripteur.REFEXT:
							po[ipo] = adFinale[i][ref_ext];
							ref_ext++;
							break;
					}
				}

				// Incremente ipo
				ipo++;
			}  // For de la recuperation des transExt

			// Ferme le fichier
			Lecture.fermer(uniteCourant);

		}  // For du parcours des unites

		// Met a jour le nombre de variables globales a reserver
		po[2] = transDon[nMod] + tabDesc[nMod].tailleGlobaux;

		// Ecrit po dans le fichier au nom du programme
		for (int i = 1; i <= ipo; i++) Ecriture.ecrireStringln(f1, "" + po[i]);

		// Ferme le fichier
		Ecriture.fermer(f1);
		Mnemo.creerFichier(ipo, po, nomProg + ".ima");
	}


	/**
	 * Affiche les tables
	 */
	static void printTables() {

		System.out.println("\n Table TransDon:");
		for (int i = 0; i <= nMod; i++)
			System.out.println("[" + i + "]" + " => " + transDon[i]);

		System.out.println("\n Table TransCode:");
		for (int i = 0; i <= nMod; i++)
			System.out.println("[" + i + "]" + " => " + transCode[i]);

		System.out.println("\n Table DicoDef:");
		for (int i = 1; i <= flagDicoDef; i++)
			System.out.println("[" + i + "]" + " => (" + dicoDef[i].nomProc + ", " + dicoDef[i].adPo + ", " + dicoDef[i].nbParam + ")");

		System.out.println("\n Table AdFinale:");
		for (int y = 0; y <= nMod; y++) {
			System.out.print("[" + y + "]" + " => ");

			for (int x = 1; x <= tabDesc[y].nbRef; x++)
				System.out.print("[" + adFinale[y][x] + "] ");

			if (tabDesc[y].nbRef == 0)
				System.out.print("Pas de reference");

			System.out.println("");
		}
	}


	/**
	 * Lanceur de l'editeur de liens
	 * @param argv Les differents arguments
	 * Le premier est le programme et les autres les modules
	 */
	public static void main (String argv[]) {

		// Affichage simple
		System.out.println("EDITEUR DE LIENS / PROJET LICENCE");
		System.out.println("---------------------------------");
		System.out.println("");

		// Initialisations
		nbErr = 0;
		ipo = 1;
		transCode[0] = 0;
		transDon[0] = 0;



		// #########################  Phase 1  #########################
		// Lit les descripteurs et remplit le dictionnaire des points d'entrees
		lireDescripteurs();

		// Associe les refs aux defs
		for (int i = 0; i <= nMod; i++) {

			// Parcours tabRef
			for (int j = 1; j <= tabDesc[i].nbRef; j++) {

				// Cherche ref
				int ref_id = presentDico(tabDesc[i].tabRef[j].nomProc);

				// Si non present dans les defs, erreur
				if (ref_id == 0)
					erreur(NONFATALE, "Reference sur procedure non definie");

				// Si ok, l'ajoute a adFinale
				else
					adFinale[i][j] = dicoDef[ref_id].adPo;
			}
		}

		// Si des erreurs ont ete rencontres
		if (nbErr > 0) {
			System.out.println("programme executable non produit");
			System.exit(1);
		}

		// Affiche toutes les tables
		printTables();



		// #########################  Phase 2  #########################
		// Creer le fichier executable mapile
		constMap();
		System.out.println("edition de liens terminee");
	}
}

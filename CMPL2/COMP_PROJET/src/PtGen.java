/*********************************************************************************
 *       VARIABLES ET METHODES FOURNIES PAR LA CLASSE UtilLex.java               *
 *       complement a l'ANALYSEUR LEXICAL produit par ANTLR                      *
 *                                                                               *
 *                                                                               *
 *   nom du programme compile sans suffixe : String UtilLex.nomSource            *
 *   ------------------------                                                    *
 *                                                                               *
 *   attributs lexicaux (selon items figurant dans la grammaire):                *
 *   ------------------                                                          *
 *     int UtilLex.valNb = valeur du dernier nombre entier lu (item nbentier)    *
 *     int UtilLex.numId = code du dernier identificateur lu (item ident)        *
 *                                                                               *
 *                                                                               *
 *   methodes utiles :                                                           *
 *   ---------------                                                             *
 *     void UtilLex.messErr(String m)  affichage de m et arret compilation       *
 *     String UtilLex.repId(int nId) delivre l'ident de codage nId               *
 *
 *********************************************************************************
 *     METHODES FOURNIES PAR LA CLASSE PtGen.java                                *
 *     constGen() et constObj()  fournissent les deux fichiers objets            *
 *     void afftabSymb()  affiche la table des symboles                          *
 *********************************************************************************/

import java.io.*;

public class PtGen {


    // constantes manipulees par le compilateur
    // ----------------------------------------

	private static final int

		MAXSYMB=300,MAXOBJ=1000,

		// codes MAPILE :
		RESERVER=1,EMPILER=2,CONTENUG=3,AFFECTERG=4,OU=5,ET=6,NON=7,INF=8,
		INFEG=9,SUP=10,SUPEG=11,EG=12,DIFF=13,ADD=14,SOUS=15,MUL=16,DIV=17,
		BSIFAUX=18,BINCOND=19,LIRENT=20,LIREBOOL=21,ECRENT=22,ECRBOOL=23,
		ARRET=24,EMPILERADG=25,EMPILERADL=26,CONTENUL=27,AFFECTERL=28,
		APPEL=29,RETOUR=30,

	    // types permis :
		ENT=1,BOOL=2,NEUTRE=3,

		// categories possibles :
		CONSTANTE=1,VARGLOBALE=2,VARLOCALE=3,PARAMFIXE=4,PARAMMOD=5,PROC=6,
		DEF=7,REF=8,PRIVEE=9,

		// valeurs booleennes
		VRAI = 1, FAUX = 0;

    // table des symboles
	private static int code, info;

	private static class EltTabSymb {
		public int code,categorie,type,info;
		public EltTabSymb() { }
		public EltTabSymb(int code,int categorie,int type,int info) {
		    this.code=code;this.categorie=categorie;
		    this.type=type;this.info=info;
		}
		public String toString() {
		    final String[] chcat={"",
			"CONSTANTE      ","VARGLOBALE     ","VARLOCALE      ",
			"PARAMFIXE      ","PARAMMOD       ","PROC           ",
			"DEF            ","REF            ","PRIVEE         "};
		    final String[] chtype={"","ENT     ","BOOL    ","NEUTRE  "};
		    String ch="";
		    if (code==-1) ch+="-1"; else ch+="@"+UtilLex.repId(code);
		    while (ch.length()<15) ch+=' ';
		    return ch+chcat[categorie]+chtype[type]+info;
		} //toString
    } // EltTabSymb


	private static EltTabSymb [] tabSymb=new EltTabSymb [MAXSYMB+1];
    private static int it,bc;

    private static int presentIdent(int binf) {
		int i=it;
		while (i>=binf && tabSymb[i].code!=UtilLex.numId) i--;
		if (i>=binf) return i; else return 0;
    }

    private static void placeIdent(int c,int cat,int t,int v) {
    	if (it==MAXSYMB) UtilLex.messErr("debordement de la table des symboles");
    	it=it+1;tabSymb[it]=new EltTabSymb(c,cat,t,v);
   }

    protected static void afftabSymb() { // affiche la table des symboles
    	System.out.println("       code           categorie      type    info");
    	System.out.println("      |--------------|--------------|-------|----");
    	for (int i=1;i<=it;i++) {
    	    if (i==bc) {System.out.print("bc=");Ecriture.ecrireInt(i,3);}
    	    else if (i==it) {System.out.print("it=");Ecriture.ecrireInt(i,3);}
    		else Ecriture.ecrireInt(i,6);

    	    if (tabSymb[i]==null) System.out.println(" reference NULL");
    	    else System.out.println(" "+tabSymb[i]);
    	}
    	System.out.println();
    }


    // controle de type
    // ----------------
    private static void verifEnt() {
    	if (tCour!=ENT) UtilLex.messErr("expression entiere attendue");
    }

    private static void verifBool() {
    	if (tCour!=BOOL) UtilLex.messErr("expression booleenne attendue");
    }


    // pile pour gerer les chaines de reprise et les branchements en avant
    // -------------------------------------------------------------------

    private static class TpileRep { // chaines de reprise iterations, conditionnelles
    	private final int MAXPILEREP=50;
    	private int ip;
    	private int [] T = new int [MAXPILEREP+1];
    	public void empiler(int x) {
    		if (ip==MAXPILEREP)
    			UtilLex.messErr("debordement de la pile de gestion des reprises");
    		ip=ip+1;T[ip]=x;
    		printPile();
    	}
		public int depiler() {
		    if (ip==0)
			UtilLex.messErr("compilateur en croix sur chaine de reprise ");
		    ip=ip-1;
		    printPile();
		    return T[ip+1];
		}
		public TpileRep() {ip=0;}

		public void printPile() {
			String res = "";
			for (int i = ip ; i >= 0 ; i--) res += T[i] + " ; ";
			res = "{ " + res.substring(0, res.length() - 3) + " }";
			System.out.println(res);
		}
    } // TpileRep

    private static TpileRep pileRep = new TpileRep();  ; // chaines de reprise iterations, conditionnelles


    // production du code objet en memoire, dans le tableau po
    // -------------------------------------------------------

    private static int [] po = new int [MAXOBJ+1];
    private static int ipo;

    private static void produire(int codeouarg) {
	if (ipo==MAXOBJ)
	    UtilLex.messErr("debordement : programme objet trop long");
        ipo=ipo+1;po[ipo]=codeouarg;
    }

    // construction du fichier objet sous forme mnemonique
    // ---------------------------------------------------
    private static void constGen() {
    	Mnemo.creerFichier(ipo,po,UtilLex.nomSource+".gen");  // recopie de po sous forme mnemonique
    }

    // construction du fichier objet pour MAPILE
    // -----------------------------------------
    private static void constObj() {
		OutputStream f=Ecriture.ouvrir(UtilLex.nomSource+".obj");
		if (f==null) {
		    System.out.println("impossible de creer "+UtilLex.nomSource+".obj");
		    System.exit(1);
		}
		for (int i=1;i<=ipo;i++)
		    if (vTrans[i]!=-1) Ecriture.ecrireStringln(f,i+"   "+vTrans[i]);
		for (int i=1;i<=ipo;i++) Ecriture.ecrireStringln(f,""+po[i]);
		Ecriture.fermer(f);
    }

    // autres variables et procedures fournies
    // ---------------------------------------
    public static String trinome="NGUYEN NHON, BOUCHERIE, ANDRIAMILANTO"; // RENSEIGNER ICI LES NOMS DU TRINOME, constitue exclusivement de lettres

    private static int tCour; // type de l'expression compilee
    //private static int vCour; // valeur de l'expression compilee le cas echeant  // Jamais utilise
    private static int iVar, nbParam; // indice du numero de la variable en cours dans la table des symboles
    private static int varType, varIndex, varCat; // type et index de l'ident precedemment lu
    private static int nbRef, nbDef; // compteur pour les ref, def de procedure et du nombre de translations

    // compilation separee : vecteur de translation et descripteur
    // -----------------------------------------------------------

    private static int[] vTrans=new int[MAXOBJ+1];

    private static void initvTrans () {
    	for (int i=1;i<=MAXOBJ;i++) vTrans[i]=-1;
    }

    private static Descripteur desc;

    private static void vecteurTrans(int x) { // ajout d'un doublet au vecteur de translation
    	if (x==Descripteur.REFEXT || desc.unite.equals("module")) {
    	    vTrans[ipo]=x; desc.nbTransExt++;
    	}
    }  // descripteur


    // initialisations  a completer
    // -----------------------------

    private static void initialisations() { // a completer si necessaire mais NE RIEN SUPPRIMER
    	initvTrans();
    	desc=new Descripteur(); // initialisation du descripteur pour compilation s?ar?
    	it=0;
    	bc=1;
    	ipo=0;
    	iVar=0;
    	tCour=NEUTRE;
    } // initialisations

 // autres variables et procedures introduites par le trinome


    // code des points de generation a completer
    // -----------------------------------------
    public static void pt(int numGen) {

    	// A RETIRER POUR LE RENDU :
    	// Afficher le numero du point de generation a chaque appel de pt pour le debuggage
    	System.out.print("NumGen: " + numGen + "\n");

    	switch (numGen) {
    		// Initialisation
	    	case 0: initialisations();
	    		break;

	    	// Apres lecture d'un ident
	    	case 1:	code = UtilLex.numId;
	    			break;

	    	// Apres lecture de la valeur d'une constante
	    	case 2:	if (presentIdent(1) == 0) placeIdent(code, CONSTANTE, tCour, info);
	    			else UtilLex.messErr("Constante " + UtilLex.repId(code) + " deja declaree.");
	    			break;

	    	// Apres lecture d'un entier positif
	    	case 3:	tCour = ENT;
	    			info = UtilLex.valNb;
	    			break;

	    	// Apres lecture d'un entier negatif
	    	case 4:	tCour = ENT;
					info = UtilLex.valNb * -1;
					break;

			// Apres lecture de la valeur booleenne vraie
	    	case 5:	tCour = BOOL;
	    			info = VRAI;
	    			break;

	    	// Apres lecture de la valeur booleenne fausse
	    	case 6:	tCour = BOOL;
					info = FAUX;
					break;

			// Apres lecture de declaration du type entier
	    	case 7:	tCour = ENT;
	    			break;

	    	// Apres lecture de declaration du type booleen
	    	case 8:	tCour = BOOL;
					break;

			// Apres lecture d'un ident de variable
	    	case 9:		if (presentIdent(bc) == 0) {

	    					// Enregistre la variable dans tabSymb
	    					if (bc > 1)
	    						placeIdent(UtilLex.numId, VARLOCALE, tCour, iVar);
	    					else
	    						placeIdent(UtilLex.numId, VARGLOBALE, tCour, iVar);

	    					iVar++;

			    		}
						else UtilLex.messErr("Variable " + UtilLex.repId(code) + " deja declaree.");
	    				break;

	    	// Reserver des espaces dans la pile pour les variables
	    	// A la fois pour le prog principal et les procedures
	    	case 91:
	    				// Uniquement si programme
	    				if (desc.unite.equals("programme")) {
		    				produire(RESERVER);

		    				// Si procedure
		    				if  (bc > 1)
		    					produire(iVar - (nbParam + 2));
		    				else
		    					produire(iVar);
	    				}

	    				// Met a jour le descripteur
	    				desc.tailleGlobaux = iVar;
    					break;


	    	// traitement des expressions
	    	// Si expression booleenne, verifie que c'est bien un bool?n
	    	case 10: 	verifBool();
	    				break;

	    	// Effectue l'operation OU
	    	case 11: 	produire(OU);
	    				break;

			// Effectue l'operation ET
	    	case 12:	produire(ET);
	    				break;

			// Effectue l'operation NON
	    	case 13: 	produire(NON);
	    				break;

			// Si expression entiere, verifie que c'est bien un entier
	    	case 14: 	verifEnt();
	    				break;

	    	// Effectue l'operation EGAL
	    	case 15: 	produire(EG);
	    				tCour = BOOL;
	    				break;

	    	// Effectue l'operation DIFFERENT
	    	case 16:	produire(DIFF);
						tCour = BOOL;
	    				break;

	    	// Effectue l'operation SUPERIEUR
	    	case 17:	produire(SUP);
						tCour = BOOL;
	    				break;

	    	// Effectue l'operation SUPERIEUR OU EGAL
	    	case 18:	produire(SUPEG);
						tCour = BOOL;
	    				break;

	    	// Effectue l'operation INFERIEUR
	    	case 19:	produire(INF);
						tCour = BOOL;
	    				break;

	    	// Effectue l'operation INFERIEUR OU EGAL
	    	case 20:	produire(INFEG);
						tCour = BOOL;
	    				break;

	    	// Effectue l'operation ADD
	    	case 21:	produire(ADD);
	    				break;

	    	// Effectue l'operation SOUS
	    	case 22:	produire(SOUS);
	    				break;

	    	// Effectue l'operation MULT
	    	case 23:	produire(MUL);
	    				break;

	    	// Effectue l'operation DIV
	    	case 24:	produire(DIV);
	    				break;

	    	// Empile une valeur entiere
	    	case 25:	produire(EMPILER);
	    				produire(info);
	    				break;

	    	// Instruction de lecture
	    	case 26:	int index = presentIdent(1);

	    				// l'ident n'est pas dans la table des symboles
	    				if (index == 0) UtilLex.messErr("Identifiant " + UtilLex.repId(UtilLex.numId) + " inconnu.");
	    				EltTabSymb elt = tabSymb[index];

	    				//check the type
	    				if (elt.type == ENT) produire(LIRENT);	// lire la valeur
		    			else produire(LIREBOOL);

	    				// tester la categorie
	    				switch (elt.categorie) {
		    				case VARGLOBALE:	produire(AFFECTERG);
		    				    				produire(elt.info);

		    				    				// Ajouter la ligne dans le vecteur de translation
		    									vecteurTrans(Descripteur.TRANSDON);
		    									break;

		    				case VARLOCALE:		produire(AFFECTERL);
		    				    				produire(elt.info);
		    				    				produire(0);
		    									break;

		    				case PARAMMOD:		produire(AFFECTERL);
		    				    				produire(elt.info);
		    				    				produire(1);
		    									break;

		    				default:			UtilLex.messErr("L'identifiant " + UtilLex.repId(UtilLex.numId) + " n'est pas reinscriptible.");
		    									break;
	    				}
	    				break;

	    	// Instruction d'ecriture
	    	case 27:	int index2 = presentIdent(1);
	    				// l'ident n'est pas dans la table des symboles
						if (index2 == 0) UtilLex.messErr("Identifiant " + UtilLex.repId(UtilLex.numId) + " inconnu.");
						EltTabSymb elt2 = tabSymb[index2];
						if (elt2.type == ENT) produire(ECRENT);	// l'afficher
						else produire(ECRBOOL);
						break;

			// Lecture d'un ident de variable
	    	case 28:	varIndex = presentIdent(1);
	    				if (varIndex == 0) UtilLex.messErr("L'identifiant " + UtilLex.repId(UtilLex.numId) + " est inconnu");
						if ((tabSymb[varIndex].categorie == CONSTANTE) || (tabSymb[varIndex].categorie == PARAMFIXE)) UtilLex.messErr("L'identifiant " + UtilLex.repId(UtilLex.numId) + " ne correspond pas a une variable");

	    				tCour = tabSymb[varIndex].type;
	    				varType = tabSymb[varIndex].type;
	    				varCat = tabSymb[varIndex].categorie;
	    				break;

			// Instruction d'affectation
	    	case 29:	// verifier le type de la valeur renseignee
	    				if (varType == ENT) verifEnt();
	    				else verifBool();

	    				// l'affecter
	    				switch (varCat) {

	    					case VARGLOBALE:
		    					produire(AFFECTERG);
		    					produire(tabSymb[varIndex].info);

		    					// Ajouter la ligne dans le vecteur de translation
								vecteurTrans(Descripteur.TRANSDON);
	    						break;

	    					case VARLOCALE:
	    						produire(AFFECTERL);
		    					produire(tabSymb[varIndex].info);
		    					produire(0);
		    					break;

		    				case PARAMMOD:
	    						produire(AFFECTERL);
		    					produire(tabSymb[varIndex].info);
		    					produire(1);
		    					break;

		    				default:
		    					UtilLex.messErr("L'identifiant " + UtilLex.repId(UtilLex.numId) + " n'est pas affectable.");
		    					break;
	    				}
    					break;

	    	// Evaluer une expression conditionnelle
	    	case 30:	// verifier que la derniere expression evaluee etait booleenne
	    				verifBool();
	    				// branchement conditionnel pour ignorer le corps d'instructions si l'expression est FALSE
	    				produire(BSIFAUX);
	    				// produire 0 pour reserver l'emplacement dans la pile d'instructions
	    				produire(0);

	    				// Ajoute une ligne au vecteur de translation
	    				vecteurTrans(Descripteur.TRANSCODE);

	    				// empiler l'adresse de la valeur du saut dans la pile des reprises, elle sera completee
	    				// lorsque le numero de ligne ou effectuer le branchement sera connue
	    				pileRep.empiler(ipo);
	    				break;

	    	// Evaluation d'un ident
	    	case 31:	// verifier si l'ident est present dans la table des ident
	    				int id_symb = presentIdent(1);
						if (id_symb == 0) UtilLex.messErr("L'identifiant " + UtilLex.repId(UtilLex.numId) + " est inconnu");
						// mettre a jour la valeur de tCour
						tCour = tabSymb[id_symb].type;
						// lire la valeur associee a l'ident s'il s'agit d'une variable, empiler directement
						// sa valeur dans la pile d'instructions pour une constante
						switch (tabSymb[id_symb].categorie) {
							case CONSTANTE:
								produire(EMPILER);
								produire(tabSymb[id_symb].info);
								break;

							case VARGLOBALE:
								produire(CONTENUG);
								produire(tabSymb[id_symb].info);

								// Ajouter la ligne dans le vecteur de translation
								vecteurTrans(Descripteur.TRANSDON);
								break;


	    					case VARLOCALE:
	    						produire(CONTENUL);
		    					produire(tabSymb[id_symb].info);
		    					produire(0);
		    					break;

							case PARAMFIXE:
								produire(CONTENUL);
								produire(tabSymb[id_symb].info);
								produire(0);
								break;

							case PARAMMOD:
								produire(CONTENUL);
								produire(tabSymb[id_symb].info);
								produire(1);
								break;

							default:
		    					UtilLex.messErr("Probleme lors de la lecture d'un ident.");
		    					break;

						}
						break;

	    	// Traitement de else dans une instruction if
	    	case 32:	// depiler l'adresse de la valeur du bsifaux produit a l'evaluation
	    				// de l'expression conditionnelle du if pour la completer
	    				int maj_brcond = pileRep.depiler();
	    				// branchement inconditionnel pour ignorer le corps d'instructions de else si
	    				// l'expression etait evaluee a vrai
	    				produire(BINCOND);

						// produire 0 pour reserver l'emplacement dans la pile d'instructions
	    				produire(0);

	    				// Ajoute une ligne au vecteur de translation
	    				vecteurTrans(Descripteur.TRANSCODE);

	    				// empiler l'adresse de la valeur du branchement inconditionnel dans la pile des reprises
	    				// elle sera completee lorsque le numero de ligne de destination sera connu
						pileRep.empiler(ipo);

	    				// completer la valeur du branchement conditionnel produit a l'evaluation de l'expression conditionnelle
	    				po[maj_brcond] = ipo+1;
	    				break;

	    	// Fin de if
	    	case 33: 	// depiler l'adresse du dernier branchement produit
	    				// s'il y avait une composante else, il s'agit du bincond
	    				// permettant d'ignorer ses instructions si la condition etait vraie
	    				// sinon il s'agit du bsifaux produit a l'evaluation de l'expression conditionnelle
						int last_branchement = pileRep.depiler();
						// mettre a jour la valeur du branchement
						po[last_branchement] = ipo+1;

	    				break;

	    	// Debut d'une instruction tant que, empiler l'adresse dans la pile d'instructions
	    	// de l'expression conditionnelle dans la pile des reprises, pour s'y brancher a la fin de chaque
	    	// iteration du corps d'instructions de la boucle
	    	case 34:	pileRep.empiler(ipo+1);
	    				break;

	    	// Fin d'une instruction tant que, mettre a jour la valeur du bincond permettant la reevaluation
	    	// de l'expression conditionnelle de boucle et la valeur du bsifaux permettant la sortie de la boucle
	    	// (produit lors de l'evaluation de l'expression de boucle)
	    	case 35:	// depiler l'adresse dans la pile d'instructions de la valeur du bsifaux
	    				int sortie_boucle = pileRep.depiler();
	    				// depiler l'adresse dans la pile d'instructions de la valeur du bincond
	    				int retour_boucle = pileRep.depiler();
	    				// produire le branchement inconditionnel pour reevaluer l'expression de boucle
	    				produire(BINCOND);
	    				produire(retour_boucle);

	    				// Ajoute une ligne au vecteur de translation
	    				vecteurTrans(Descripteur.TRANSCODE);

	    				// mettre a jour la valeur du bsifaux pour la sortie de la boucle
	    				po[sortie_boucle] = ipo+1;
	    				break;

	    	// Empile une valeur bidon pour dejouter la double depilation
	    	case 36:
	    				pileRep.empiler(0);
	    				break;

	    	// Entre chaque cas du cond
			// Genere le bincond chaine
	    	// Et met a jour le dernier bsifaux
	    	case 37:
	    				int bsifaux = pileRep.depiler();
	    				int bincond = pileRep.depiler();

	    				produire(BINCOND);
	    				produire(bincond);

	    				// Ajoute une ligne au vecteur de translation
	    				vecteurTrans(Descripteur.TRANSCODE);

	    				po[bsifaux] = ipo+1;

	    				pileRep.empiler(ipo);

						break;

			// Pour le cas du aut
			case 38:
						int bsifaux_aut = pileRep.depiler();

	    				produire(BINCOND);
	    				produire(0);

	    				// Ajoute une ligne au vecteur de translation
	    				vecteurTrans(Descripteur.TRANSCODE);

	    				po[bsifaux_aut] = ipo+1;

	    				pileRep.empiler(ipo);

						break;

			// Fin d'un cond, gere le dernier bsifaux ou bincond
			// Gere les bdincond chaines
			case 39:

						int bsifaux_or_bincond = pileRep.depiler();
						int last_bincond = pileRep.depiler();

						po[bsifaux_or_bincond] = ipo+1;

						while (po[last_bincond] != 0) {

							int tmp = po[last_bincond];
							po[last_bincond] = ipo+1;
							last_bincond = tmp;
						}

						po[last_bincond] = ipo+1;

						break;



			/* ############################## Procedures ############################## */
			// Declaration d'une procedure
			case 40:
						if (presentIdent(1) == 0) {
	    					placeIdent(UtilLex.numId, PROC, NEUTRE, ipo+1);
	    					placeIdent(-1, PRIVEE, NEUTRE, 0);  // The last field is to update later
	    					bc = it+1;
	    					nbParam = 0;
						}
						else UtilLex.messErr("Procedure " + UtilLex.repId(code) + " deja declaree.");
						break;

			// Declaration d'un parametre fixe
			case 41:
						if (presentIdent(bc) == 0) {
	    					placeIdent(UtilLex.numId, PARAMFIXE, tCour, nbParam);
	    					nbParam++;
						}
						else UtilLex.messErr("Parametre fixe " + UtilLex.repId(code) + " deja declare.");
						break;

			// Declaration d'un parametre modifiable
			case 42:
						if (presentIdent(bc) == 0) {
	    					placeIdent(UtilLex.numId, PARAMMOD, tCour, nbParam);
	    					nbParam++;
						}
						else UtilLex.messErr("Parametre modifiable " + UtilLex.repId(code) + " deja declare.");
						break;

			// Fin de declaration des parametres (en dessous ce sont les variables locales)
			case 43:
						EltTabSymb ligneParams = tabSymb[bc-1];
						ligneParams.info = nbParam;
						iVar = nbParam + 2;  // Jump 2 for the links data for mapile
						break;

			// Fin de la procedure
			// Produire retour avec le nb de parametres
			case 44:
						// Produit le retour
						produire(RETOUR);
						produire(nbParam);

						// Suppression des variables locales
						it = bc + nbParam-1;

						// Mise a -1 des indents de parametres
						for (int i = it ; i >= bc ; i--) {
							tabSymb[i].code = -1;
						}

						// Quelques reinitialisations
						bc = 1;
						break;

			// Passage de parametres pour l'appel d'une procedure
			case 45:
						int paramModId = presentIdent(1);
						if (paramModId == 0) UtilLex.messErr("Parametre modifiable " + UtilLex.repId(code) + " non trouve.");

						switch (tabSymb[paramModId].categorie) {

							case PARAMMOD:
								produire(EMPILERADL);
								produire(tabSymb[paramModId].info);
								produire(1);
								break;

							case VARLOCALE:
								produire(EMPILERADL);
								produire(tabSymb[paramModId].info);
								produire(0);
								break;

							case VARGLOBALE:
								produire(EMPILERADG);
								produire(tabSymb[paramModId].info);

								// Ajouter la ligne dans le vecteur de translation
								vecteurTrans(Descripteur.TRANSDON);
								break;

							default:
		    					UtilLex.messErr("L'identifiant " + UtilLex.repId(UtilLex.numId) + " n'est pas passable comme parametre.");
		    					break;
						}

						break;

			// Appelle correctement la procedure
			case 46:
				produire(APPEL);
				produire(tabSymb[varIndex].info);  // Nomme varIndex mais c'est plutot l'index de l'ident

				// Si reference extetieure
				if (tabSymb[varIndex+1].categorie == REF)
					vecteurTrans(Descripteur.REFEXT);

				// Si procedure locale
				else
					vecteurTrans(Descripteur.TRANSCODE);

				produire(tabSymb[varIndex+1].info);
				break;




			/* ############################## Definitions des unites (modules et programme) ############################## */
			// Definition d'un programme
			case 47:
				desc.unite = "programme";
				break;

			// Definition d'un module
			case 48:
				desc.unite = "module";
				break;





			/* ############################## Definitions et references de Procedures ############################## */
			// Reference sur une procedure
			case 49:

				// Incremente le compteur de ref
				nbRef++;

				// Place les lignes correspondantes
				if (nbRef == Descripteur.MAXREF) UtilLex.messErr("Nombre maximal de references exterieures atteint");
				placeIdent(UtilLex.numId, PROC, NEUTRE, nbRef);
				placeIdent(-1, REF, NEUTRE, nbParam);

				// Ajoute une ligne dans tabRef
				desc.tabRef[nbRef] = new EltRef(UtilLex.repId(UtilLex.numId), nbParam);

				// Reinitialise nbParam
				nbParam = 0;
				break;

			// Parametre fixe sur une ref
			case 50:
				nbParam++;
				break;

			// Parametre mod sur une ref
			case 51:
				nbParam++;
				break;

			// Fin d'un module
			case 52:

		    	// Mise a jour de tabDef via tabSymb
		    	// Pour chaque ligne de tabDef
		    	for (int i = 1; i <= nbDef; i++) {

		    		// Recupere la ligne dans tabSymb
	    			int j = 1;
	    			boolean found = false;
	    			while ((j < it) && (!found)) {

	    				// Si on a trouve la procedure definie par def
	    				if ((tabSymb[j].categorie == PROC) && (desc.tabDef[i].nomProc.equals(UtilLex.repId(tabSymb[j].code)))) {

			    			// Met a jour l'adresse du programme
			    			desc.tabDef[i].adPo = tabSymb[j].info;
			    			desc.tabDef[i].nbParam = tabSymb[j+1].info;

			    			// Met a jour le type du tabSymb
			    			tabSymb[j+1].categorie = DEF;

			    			// Sort de la boucle
			    			found = true;
	    				}

	    				// Sinon incremente
	    				else j++;
	    			}

	    			// Si non trouve
	    			if (j == it) UtilLex.messErr("Procedure referencee en def non definie");
		    	}
				break;

			// Reference sur une procedure
			case 53:
				if (nbDef == Descripteur.MAXDEF) UtilLex.messErr("Nombre maximal de definitions atteint");
				nbDef++;
				desc.tabDef[nbDef] = new EltDef(UtilLex.repId(UtilLex.numId), 0, 0);
				break;

			// Reinitialisation entre chaque unite
			case 54:

				// Reinitialisation des parametres
		    	nbRef = 0;
		    	nbDef = 0;
		    	desc = new Descripteur();
		    	break;





			/* ############################## Gestion du procedure complet ############################## */
	    	// Fin de la compilation, produire l'instruction d'arret du programme
	    	// et traduire la pile d'instructions en fichier objet pour Mapile et fichier mnemonique
	    	// pour le debuggage
	    	case 123:
	    				// Arret si programme uniquement
	    				if (desc.unite.equals("programme")) produire(ARRET);

						// Met a jour le descripteur
	    				desc.tailleCode = ipo;
	    				desc.nbDef = nbDef;
	    				desc.nbRef = nbRef;

	    				// Creation des fichiers .obj, .gen et .desc
						constObj();
						constGen();
						desc.ecrireDesc(UtilLex.nomSource);
	    				break;

			// Place le bincond vers le debut du programme
			case 124:
						// Uniquement si programme
						if (desc.unite.equals("programme")) {
							produire(BINCOND);
							produire(0);

							// Ajoute une ligne au vecteur de translation
		    				vecteurTrans(Descripteur.TRANSCODE);

							pileRep.empiler(ipo);
						}
						break;

	    	// Met a jour le bincond vers le debut du programme
	    	case 125:
						// Uniquement si programme
						if (desc.unite.equals("programme")) {
							int adrDebut = pileRep.depiler();
		    				po[adrDebut] = ipo+1;
						}
						break;

	    	// Point de generation non gere
	    	default : 	System.out.println("Point de generation non prevu dans votre liste");
	    				break;

	    	}

    		// A RETIRER POUR LE RENDU :
    		// Afficher tCour a chaque point de generation pour le debuggage
    		// System.out.println("; tCour: " + tCour);

        }
}

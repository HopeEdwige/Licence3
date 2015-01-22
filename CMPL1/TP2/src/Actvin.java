public class Actvin {

    private static class Chauffeur {
	public int numchauf, bj, bg, ordin ; 
	public SmallSet magdif ;
	public Chauffeur(int numchauf,int bj,int bg,int ordin,SmallSet magdif) {
	    this.numchauf = numchauf ; this.bj = bj ; this.bg = bg ; 
	    this.ordin = ordin ; this.magdif = magdif.clone() ;
	}
	public Chauffeur copie() {return new Chauffeur(numchauf,bj,bg,ordin,magdif);}
    } // class Chauffeur

    public static final int[][] action = {
	/* état        BJ   BG IDENT NBENT  ,    ;    /  AUTRES  */
	/* 0 */      { 6,   6,   1,    6,   6,   0,   7,   6   },
	/* 1 */      { 2,   2,   4,    3,   6,   0,   6,   6   },
	/* 2 */      { 6,   6,   4,    6,   6,   0,   6,   6   },
	/* 3 */      { 2,   2,   4,    6,   6,   0,   6,   6   },
	/* 4 */      { 6,   6,   6,    5,   3,   0,   6,   6   },
	/* 5 */      { 6,   6,   4,    6,   6,   0,   6,   6   },
	/* 6 */      { 6,   6,   6,    6,   6,   0,   6,   6   }
    } ;	       

    private static final int FATALE = 0 , NONFATALE = 1 ;

    private static final int MAXCHAUF = 10 , MAXLGID = 20 ;
    private static Chauffeur[] tabchauf = new Chauffeur[MAXCHAUF] ;
 
    private static int numchauf, bj, bg, ordin, type, volume ;
    private static SmallSet magdif;
    
    private static void attenteSurLecture(String mess) {
	String tempo ;
	System.out.println("") ;
	System.out.print(mess + " pour continuer tapez entrée ") ;
	tempo = Lecture.lireString() ;
    } // attenteSurLecture

    private static void erreur(int te,String messerr) {
	attenteSurLecture(messerr) ;
	switch (te) {
	case FATALE    : Vin.errcontr = true ; break ;
	case NONFATALE : Vin.etat = Autovin.ETATERR ; break ;
	default : attenteSurLecture("paramètre incorrect pour erreur") ;
	}
    } // erreur
 
    private static String chaineCadrageGauche(String ch) {
	/* en entrée : ch est une chaine de longueur quelconque
	   délivre la chaine ch cadrée à gauche sur MAXLGID caractères
	*/
	int lgch = Math.min(MAXLGID,ch.length()) ;
	String chres = ch.substring(0,lgch) ;
	for (int k = lgch ;k < MAXLGID ; k++) chres = chres + " " ;
	return chres ;
    } // chaineCadrageGauche
  
    public static void executer(int numact) {
	switch (numact) {
	case -1  : break ;
	// initialisations
	case  0  : bj = 0; bg = 0; ordin = 0; type = 0; volume = 100;
	           break ;
	// lecture ident nom chauffeur
	case 1 : numchauf = Lexvin.numId;
			 break;
	// lecture volume citerne
	case 2 : int vol = Lexvin.valNb;
			 if (100 < vol  && vol <= 200) volume = vol;
			 break;
	// cas du Beaujolais
	case 3 : type = 1;
			 break;
	// cas du Bourgogne
	case 4 : type = 2;
			 break;
	// lecture ident nom magasin
	case 5 : if (!magdif.contains(Lexvin.numId)) magdif.add(Lexvin.numId);
			 break;
	// lecture quantite livree a ce magasin
	case 6 : 	int qte = Lexvin.valNb;
				if (qte <= 0) erreur(NONFATALE, "Quantité livrée incorrecte");
				switch (type) {
				case 0 :	ordin += qte; break;
				case 1 :	bj += qte; break;
				case 2 : 	bg += qte; break;
				}
				break;
	// lecture ',' => fin de livraison courante
	case 7 :	// TO DO tester : volume livré < volume citerne
				break;
	default : attenteSurLecture("action " + numact + " non prévue") ;
	}
    } // executer

    public static void main(String[] args) {
	System.out.println("la classe Actvin ne possède pas de 'main'") ;
    } // main

} // class Actvin

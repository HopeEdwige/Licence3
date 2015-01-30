import java.awt.* ;
import javax.swing.JFrame;



public class Vin {
  public static int etat, code ; // numero de l'etat et code de l'item
    public static boolean errcontr ; // pour gerer l'erreur fatale

    private static JFrame fenAffichage = new JFrame () ; // fenetre d'affichage
    private static TextArea fenEntree ; // fenetre d'entree en cours d'analyse
    private static TextArea fenTrace ; // traces : etats, item, action

    private static void afficherEtatAnalyse(int numact, int arrivee) {
	String mess = 
	    "d?art=" + etat + "  item=" + Lexvin.item[code] +
	    "  arrivee=" + arrivee + "  numact=" + numact + "\n";    
	fenTrace.append(mess) ;
    } // afficherEtatAnalyse
 
   private static void interpreteur() {
	int arrivee, numact=0 ;
	Lexvin.debutAnalyse(fenEntree) ;
	etat = 0 ; errcontr = false ;
	Actvin.executer(0) ;
	do {
	    code = Lexvin.liresymb() ;
	    numact = Actvin.action[etat][code] ;
	    arrivee = Autovin.transit[etat][code] ;
	    afficherEtatAnalyse(numact,arrivee) ;
	    Actvin.executer(numact) ;
	    if (errcontr) break ;
	    etat = Autovin.transit[etat][code] ;
	    if (etat==Autovin.FINAL) break ;  
	}
	while (true) ;
	Lexvin.finAnalyse() ;
    } // interpreteur

   public static void main(String[] args) {
	   fenAffichage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       fenAffichage.setTitle("Affichage des traces de l'automate") ;
       fenAffichage.setLocation(430,0) ;
       fenEntree = new TextArea("FICHIER D'ENTREE\n",9,80) ;
       fenEntree.append("----------------\n") ;
       fenEntree.setFont(new Font("Courier",Font.PLAIN,12)) ;
       fenEntree.setEditable(false) ;
       fenAffichage.add(fenEntree,"North") ;
       fenTrace = new TextArea("ETATS, ITEM, ACTION\n",10,80) ;
       fenTrace.append("-------------------\n") ;
       fenTrace.setFont(new Font("Courier",Font.PLAIN,12)) ;
       fenTrace.setEditable(false) ; 
       fenAffichage.add(fenTrace,"South") ;
       fenAffichage.pack() ; 
       fenAffichage.setVisible(true) ;
       interpreteur() ;
       System.exit(0) ;
    } 
} // class Vin

	


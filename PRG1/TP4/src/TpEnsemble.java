import java.awt.Frame;
import java.awt.Font; 
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;

public class TpEnsemble {

    private static final int MAXENS = 5;
    private static SmallSet [] E = new SmallSet[MAXENS];
    private static final Scanner standardInput = new Scanner(System.in);

    private static String[] texteCommande =
    {
	"E[n1] <-- E[n2]",
	"E[n1] <-- ensemble vide",
	"afficher le contenu de E[n1]",
	"ajouter une valeur à E[n1]",
	"ajouter un intervalle de valeurs à E[n1]",
	"déterminer si x appartient à E[n1]",
	"afficher le cardinal de E[n1]",
	"E[n1] <-- complémentaire(E[n1])",
	"E[n1] <-- différence(E[n1],E[n2])",
	"E[n1] <-- différenceSymétrique(E[n1],E[n2])",
	"déterminer si E[n1] et E[n2] sont égaux",
	"déterminer si E[n1] est inclus dans E[n2]",
	"E[n1] <-- intersection(E[n1],E[n2])",
	"retirer une valeur à E[n1]",
	"retirer un intervalle de valeurs à E[n1]",
	"E[n1] <-- union(E[n1],E[n2])",
	"déterminer si E[n1] est vide",
	"arrêt de l'exécution"
    };

    private static String[] nomBouton =
    {"Affecter", "AffecterVide", "Afficher", "Ajouter", "AjouterIntervalle",
     "Appartient", "Cardinal", "Complémentaire", "Différence",
     "DifférenceSymétrique", "Egal", "InclusDans", "Intersection",
     "Oter", "RetirerIntervalle", "Union", "Vide", "Quitter"};

    private static Frame menu = new Frame();
    private final static int H = 150, V = 570, LOCX = 800, LOCY = 10;
    private final static int LARGEURBOUTON = 150, HAUTEURBOUTON = 30;

    private static class Action implements ActionListener {
	private int numAction;
	public Action (int numAction) {
	    this.numAction = numAction;
	}
	public synchronized void actionPerformed(ActionEvent e) {
	    menu.setVisible(false);
	    executer(numAction);
	    menu.setVisible(true);
	}
    } // class Action

    public static void main(String[] args) {
	for (int i = 0; i < MAXENS; ++i) {
	    E[i] = new SmallSet();
	}
	menu.setTitle("TP Ensemble");
	menu.setSize(H, V);
	menu.setLocation(LOCX, LOCY);
	menu.setLayout(null);
	for (int i = 0; i < nomBouton.length; ++i) {
	                       // création des boutons du menu
	    Button bouton = new Button(nomBouton[i]);
	    bouton.addActionListener(new Action(i));
	    bouton.setLocation(0,25 + HAUTEURBOUTON * i);
	    bouton.setSize(LARGEURBOUTON, HAUTEURBOUTON);
	    bouton.setFont(new Font("Serif", Font.BOLD, 14));
	    menu.add(bouton);
	}
	menu.setVisible(true);
    } // main

    private static void executer(int i) { // traitements associés aux boutons
	System.out.println("* "+texteCommande[i]);
	switch (i) {
	case  0 : E[lireN(1)] = E[lireN(2)].copy(); break;
	case  1 : E[lireN(1)].clear(); break;
	case  2 : System.out.println(E[lireN(1)]); break;
	case  3 : E[lireN(1)].add(lireVal("valeur à ajouter : ")); break;
	case  4 : E[lireN(1)].addInterval(lireVal("deb intervalle : "),
					  lireVal("fin intervalle : "));
	    break;
	case  5 : System.out.println(E[lireN(1)].contains(lireVal("x = ")));
	    break;
	case  6 : System.out.println(E[lireN(1)].size()); break;
	case  7 : E[lireN(1)].complement(); break;
	case  8 : E[lireN(1)].difference(E[lireN(2)]); break;
	case  9 : E[lireN(1)].symetricDifference(E[lireN(2)]); break;
	case 10 : System.out.println(E[lireN(1)].equals(E[lireN(2)]));
	    break;
	case 11 : System.out.println(E[lireN(1)].isIncludedIn(E[lireN(2)]));
	    break;
	case 12 : E[lireN(1)].intersection(E[lireN(2)]); break;
	case 13 : E[lireN(1)].remove(lireVal("valeur à retirer : "));
	    break;
	case 14 : E[lireN(1)].removeInterval(lireVal("deb intervalle : "),
					     lireVal("fin intervalle : "));
	    break;
	case 15 : E[lireN(1)].union(E[lireN(2)]); break;
	case 16 : System.out.println(E[lireN(1)].isEmpty());
	    break;
	case 17 : standardInput.close(); System.exit(0);
	}
    } // executer

    private static int lireEnt(String s,int d,int f) {
	// lecture d'un entier >=d et <=f
	int x;
	boolean b;
	do {
	    b = true;
	    x = 0;	
	    System.out.print(s);
	    try {
		x = standardInput.nextInt();
	    }
	    catch (NumberFormatException e) { 
		b = false; 
	    }
	    b = b && x>=d && x<=f;
	    if (!b) {
		System.out.println("valeur incorrecte");
	    }
	} while (!b);
	return x;
    }

    private static int lireN(int i) {
	return lireEnt("  numéro d'ensemble n" + i + " : ", 0, MAXENS - 1);
    }

    private static int lireVal(String s) {
	return lireEnt(s, 0, 255);
    }
}

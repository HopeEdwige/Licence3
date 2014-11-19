import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TpArbre {

	// pour les arbres binaires manipules
	private static final int NUMBER_OF_TREES = 5;
	private static ArrayList<Image> treeSet = 
			new ArrayList<Image>(NUMBER_OF_TREES);
	private static final Scanner standardInput = new Scanner(System.in);

	// pour l'affichage du texte de la commande
	private final static String[] TEXT_COMMANDS = {
			"affichage du dessin contenu dans un fichier",
			"E[i] <-- dessin contenu dans une fenêtre",
			"sauvegarde du dessin E[i] dans un fichier",
			"affichage du dessin contenu dans l'arbre E[i]",
			"hauteur et nombre de noeuds de l'arbre E[i]",
			"teste, par exploration de E[i], si un point est allumé",
			"affectation E[i] <-- E[j]", "E[i] <-- complementaire de E[i]",
			"E[i] <-- rotation à 180 degres de E[j]",
			"E[i] <-- E[j] intersection E[k]", "E[i] <-- E[j] union E[k]",
			"teste si la diagonale de E[i] est entièrement allumée",
			"teste, pour E[i], si deux points sont dans la même feuille",
			"teste si E[i] est inclus dans E[j]",
			"affiche E[i] sous forme d'arbre", "arrêt de l'exécution" };

	// pour l'affichage des items de choix
	private final static String[] ITEM_NAMES = { "readImage", "construct",
			"save", "plotImage", "height", "isPixelOn", "affect",
			"videoInverse", "rotate180", "intersection", "union",
			"testDiagonal", "sameLeaf", "isIncludedIn", "plotTree", "close" };

	private static JFrame ourWindow = new JFrame(); // fenêtre d'affichage
	private static final int SIZE_X = 1024, SIZE_Y = 360;

	private static JMenuBar menuBar = new JMenuBar(); // barre de menu
	private static JMenu menuChoice = new JMenu("MENU"); // colonne "MENU"

	private static final int SHIFT_X = 120; // décalage horizontal de fenêtre de
											// dessin
	private static final int SHIFT_Y = -50; // décalage vertical de fenêtre de
											// dessin
	private static final Dessin window = new RealDessin(SIZE_X - SHIFT_X,
			SIZE_Y);

	private static int numberI, numberJ, numberK; // numéros des arbres (i,j,k)

	// action de reaction au choix effectue avec la souris
	private static class Action implements ActionListener {
		private int actionNumber;

		public Action(int numAction) {
			this.actionNumber = numAction;
		}

		public synchronized void actionPerformed(ActionEvent e) {
			menuBar.remove(menuChoice);
			menuExecution(actionNumber);
			menuBar.add(menuChoice);
		}
	}

	public static void main(String[] args) {
		try {
			for (int i = 0; i < NUMBER_OF_TREES; ++i) {
				treeSet.add(new Image());
			}
			// preparation de la fenetre d'affichage
			ourWindow.setTitle("TP arbres binaires");
			ourWindow.setSize(SIZE_X, SIZE_Y);
			ourWindow.setLocation(0, 0);
			ourWindow.setBackground(Color.white);
			ourWindow.setLayout(null); // le placement n'utilise pas de
										// gestionnaire

			// ajout de la barre menu, du menu choix et des items de choix
			ourWindow.setJMenuBar(menuBar);
			menuChoice.setFont(new Font("Serif", Font.BOLD, 14));
			menuBar.add(menuChoice);
			for (int i = 0; i < ITEM_NAMES.length; ++i) {
				JMenuItem choix = new JMenuItem(ITEM_NAMES[i]);
				choix.setFont(new Font("Serif", Font.BOLD, 12));
				menuChoice.add(choix);
				menuChoice.addSeparator();
				choix.addActionListener(new Action(i));
			}

			// ajout de la fenetre fenDessin
			window.setLocation(SHIFT_X, SHIFT_Y);
			window.setSize(SIZE_X - SHIFT_X, SIZE_Y);
			ourWindow.add(window);
			ourWindow.setVisible(true);
		} catch (AssertionError e) {
			e.printStackTrace();
		}
	}

	private static void menuExecution(int choiceNumber) {
		System.out.println("* " + TEXT_COMMANDS[choiceNumber]);
		acquisition(choiceNumber);
		if ((numberI != -1) && (numberJ != -1) && (numberK != -1)) {
			switch (choiceNumber) {
			case 0:
				Image.readImage(readWindowNumber(), window);
				break;
			case 1:
				treeSet.get(numberI).constructTree(readWindowNumber(), window);
				break;
			case 2:
				treeSet.get(numberI).save();
				break;
			case 3:
				treeSet.get(numberI).plotImage(readWindowNumber(), window);
				break;
			case 4:
				System.out.println("hauteur = " + treeSet.get(numberI).height()
						+ " et nombreNoeuds = "
						+ treeSet.get(numberI).numberOfNodes());
				break;
			case 5:
				int x = readCoords("x");
				int y = readCoords("y");
				if (treeSet.get(numberI).isPixelOn(x, y)) {
					System.out.println("point (" + x + ", " + y + ") allume");
				}
				else {
					System.out.println("point (" + x + ", " + y + ") eteint");
				}
				break;
			case 6:
				treeSet.get(numberI).affect(treeSet.get(numberJ), window);
				break;
			case 7:
				treeSet.get(numberI).videoInverse(window);
				break;
			case 8:
				treeSet.get(numberI).rotate180(treeSet.get(numberJ), window);
				break;
			case 9:
				treeSet.get(numberI).intersection(treeSet.get(numberJ),
						treeSet.get(numberK), window);
				break;
			case 10:
				treeSet.get(numberI).union(treeSet.get(numberJ),
						treeSet.get(numberK), window);
				break;
			case 11:
				if (treeSet.get(numberI).testDiagonal()) {
					System.out.println("diagonale entierement allumee");
				}
				else {
					System.out.println("diagonale non entierement allumee");
				}
				break;
			case 12:
				int x1 = readCoords("x1");
				int y1 = readCoords("y1");
				int x2 = readCoords("x2");
				int y2 = readCoords("y2");
				if (treeSet.get(numberI).sameLeaf(x1, y1, x2, y2)) {
					System.out.println("points dans la meme feuille");
				}
				else {
					System.out.println("points dans feuilles differentes");
				}
				break;
			case 13:
				if (treeSet.get(numberI).isIncludedIn(treeSet.get(numberJ),
						window)) {
					System.out.println("E[" + numberI + "] inclus dans E["
							+ numberJ + "]");
				}
				else {
					System.out.println("E[" + numberI + "] non inclus dans E["
							+ numberJ + "]");
				}
				break;
			case 14:
				treeSet.get(numberI).plotTree();
				break;
			case 15:
				for (int i = NUMBER_OF_TREES; i > 0; --i) {
					treeSet.remove(i - 1);
				}
				standardInput.close();
				Image.closeAll();
				System.exit(0);
			}
		}
	}

	// acquisition des donnees pour les commandes du TP

	private static void acquisition(int choiceNumber) {
		int i = 0, j = 0, k = 0; // valeurs par default
		switch (choiceNumber) {
		case 0:
			break; // Lirefichier
		case 1:
			i = readNumberI();
			break; // Construire : -1<= i<MAXARB
		case 2:
		case 3:
		case 4:
		case 5:
		case 7:
		case 11:
		case 12:
		case 14:
			/*
			 * Sauver, Dessiner, Hauteur, PointAllume, InverseVideo,
			 * TesterDiagonale, MemeFeuille, AfficherArbre : i<>-1 == > E.get(i)
			 * non vide
			 */
			do {
				i = readNumberINotEmpty();
				if (i != -1)
					treeSet.get(i);
			} while ((i != -1) && treeSet.get(i).isEmpty());
			break;
		case 6:
		case 8:
			// Affecter, Rotation180 : i<>-1, j<>-1 = > j<>i et E.get(j) non
			// vide
			i = readNumberI();
			if (i != -1) {
				do {
					j = readNumberJ(i);
					if (j != -1)
						treeSet.get(j);
				} while ((j != -1) && treeSet.get(j).isEmpty());
			}
			break;
		case 13:
			/*
			 * Inclus : i<>-1 et j<>-1 == > E.get(i) non vide, j<>i et E.get(j)
			 * non vide
			 */
			do {
				i = readNumberINotEmpty();
				if (i != -1)
					treeSet.get(i);
			} while ((i != -1) && treeSet.get(i).isEmpty());
			if (i != -1) {
				do {
					j = readNumberJ(i);
					if (j != -1)
						treeSet.get(j);
				} while ((j != -1) && treeSet.get(j).isEmpty());
			}
			break;
		case 9:
		case 10:
			/*
			 * Inter, Union : i<>-1, j<>-1, k<>-1 == > j<>i, k<>i, E.get(j) et
			 * E.get(k) non vides
			 */
			i = readNumberI();
			if (i != -1) {
				do {
					j = readNumberJ(i);
					if (j != -1)
						treeSet.get(j);
				} while ((j != -1) && treeSet.get(j).isEmpty());
				if (j != -1) {
					do {
						k = readNumberK(i, j);
						if (k != -1)
							treeSet.get(k);
					} while ((k != -1) && treeSet.get(k).isEmpty());
				}
			}
			break;
		case 15:
			break; // Quitter
		default:
			System.out.println("acquisition : choix non prevu");
		}
		numberI = i;
		numberJ = j;
		numberK = k;
	}

	private static int readWindowNumber() {
		String string = "numéro de fenetre (entre 1 et "
				+ RealDessin.NUMBER_OF_WINDOWS + ") : ";
		return readInteger(1, RealDessin.NUMBER_OF_WINDOWS, string);
	}

	private static int readCoords(String xy) {
		String string = "coordonnee " + xy + " (entre 0 et 255) : ";
		return readInteger(0, 255, string);
	}

	private static int readNumberI() {
		String string = "numéro i<" + NUMBER_OF_TREES
				+ " d'arbre (-1 pour retour au menu) : ";
		return readInteger(-1, NUMBER_OF_TREES - 1, string);
	}

	private static int readNumberINotEmpty() {
		String string = "numéro i<" + NUMBER_OF_TREES
				+ " d'arbre non vide (-1 pour retour au menu) : ";
		return readInteger(-1, NUMBER_OF_TREES - 1, string);
	}

	private static int readNumberJ(int i) {
		int j;
		String string = "numéro j<" + NUMBER_OF_TREES + " différent de " + i
				+ " d'arbre non vide (-1 pour retour au menu) : ";
		do {
			j = readInteger(-1, NUMBER_OF_TREES - 1, string);
		} while (j == i);
		return j;
	}

	private static int readNumberK(int i, int j) {
		int k;
		String string = "numéro k<" + NUMBER_OF_TREES + " différent de " + i
				+ " et de " + j
				+ " d'arbre non vide (-1 pour retour au menu) : ";
		do {
			k = readInteger(-1, NUMBER_OF_TREES - 1, string);
		} while (k == i || k == j);
		return k;
	}

	private static int readInteger(int min, int max, String ch) {
		int value = -1;
		boolean end;
		do {
			value = -1;
			System.out.print(ch);
			try {
				value = standardInput.nextInt();
				end = (value >= min) && (value <= max);
			} 
			catch (NumberFormatException e) {
				end = false;
			}
		} while (!end);
		return value;
	}
}
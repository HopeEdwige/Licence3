import java.awt.Frame;
import java.awt.Font;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class TpListe {

	private static final int MAX_SET = 5;
	private static final Scanner standardInput = new Scanner(System.in);

	private static ArrayList<OurSet> listSet = new ArrayList<OurSet>(MAX_SET);

	private final static String[] COMMAND_TEXTS = {
			"afficher les entiers appartenant à l'ensemble numéro n1",
			"afficher les rangs présents dans l'ensemble numéro n1",
			"ajouter des valeurs à l'ensemble numéro n1",
			"déterminer si x appartient à l'ensemble numéro n1",
			"afficher le cardinal de l'ensemble numéro n1",
			"l'ensemble numéro n1 <-- diff?ence l'ensemble numéro n1 \\ l'ensemble numéro n2)",
			"l'ensemble numéro n1 <-- diff?ence sym?rique de l'ensemble numéro n1 et l'ensemble numéro n2",
			"déterminer si l'ensemble numéro n1 et l'ensemble numéro n2 sont égaux",
			"déterminer si l'ensemble numéro n1 est inclus dans l'ensemble numéro n2",
			"l'ensemble numéro n1 <-- intersection de l'ensemble numéro n1 et l'ensemble numéro n2",
			"r?nitialiser l'ensemble numéro n1 ?partir d'un fichier",
			"retirer des valeurs à l'ensemble numéro n1",
			"sauvegarder l'ensemble numéro n1 dans un fichier",
			"l'ensemble numéro n1 <-- union de l'ensemble numéro n1 et l'ensemble numéro n2",
			"arr? de l'ex?ution" };

	private final static String[] ITEM_NAMES = { "AfficherEns",
			"AfficherRangs", "Ajouter", "Appartient", "Cardinal", "Diff?ence",
			"Diff?enceSym?rique", "Egaux", "Inclus", "Intersection",
			"R?nitialiser", "Retirer", "Sauvegarder", "Union", "Quitter" };

	private static Frame menu = new Frame();
	private final static int H = 180, V = 480, LOCX = 800, LOCY = 10;
	private final static int BUTTON_WIDTH = 180, BUTTON_HEIGHT = 30;

	private static class Action implements ActionListener {
		private int actionNumber;

		public Action(int actionNumber) {
			this.actionNumber = actionNumber;
		}

		@Override
		public synchronized void actionPerformed(ActionEvent e) {
			menu.setVisible(false);
			execute(actionNumber);
			menu.setVisible(true);
		}
	}

	public static void main(String[] args) {
		try {
			for (int i = 0; i < MAX_SET; ++i) {
				listSet.add(new OurSet());
			}
			menu.setTitle("TP listes");
			menu.setSize(H, V);
			menu.setLocation(LOCX, LOCY);
			menu.setLayout(null);
			for (int i = 0; i < ITEM_NAMES.length; ++i) {
				Button button = new Button(ITEM_NAMES[i]);
				button.addActionListener(new Action(i));
				button.setLocation(0, 25 + BUTTON_HEIGHT * i);
				button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
				button.setFont(new Font("serif", Font.BOLD, 14));
				menu.add(button);
			}
			menu.setVisible(true);
		}
		catch (AssertionError e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	private static void execute(int i) { // traitements associ? aux boutons
		System.out.println("* " + COMMAND_TEXTS[i]);
		switch (i) {
		case 0:
			listSet.get(readSetNumber(1)).print();
			break;
		case 1:
			listSet.get(readSetNumber(1)).printRanks();
			break;
		case 2:
			listSet.get(readSetNumber(1)).add();
			break;
		case 3:
			listSet.get(readSetNumber(1)).containment();
			break;
		case 4:
			listSet.get(readSetNumber(1)).size();
			break;
		case 5:
			listSet.get(readSetNumber(1)).difference(
					listSet.get(readSetNumber(2)));
			break;
		case 6:
			listSet.get(readSetNumber(1)).symmetricDifference(
					listSet.get(readSetNumber(2)));
			break;
		case 7:
			listSet.get(readSetNumber(1)).equals(listSet.get(readSetNumber(2)));
			break;
		case 8:
			listSet.get(readSetNumber(1)).isIncludedIn(
					listSet.get(readSetNumber(2)));
			break;
		case 9:
			listSet.get(readSetNumber(1)).intersection(
					listSet.get(readSetNumber(2)));
			break;
		case 10:
			listSet.get(readSetNumber(1)).restore();
			break;
		case 11:
			listSet.get(readSetNumber(1)).remove();
			break;
		case 12:
			listSet.get(readSetNumber(1)).save();
			break;
		case 13:
			listSet.get(readSetNumber(1)).union(listSet.get(readSetNumber(2)));
			break;
		case 14:
			for (int j = 0; j < MAX_SET; ++j) {
				listSet.set(j, null);
			}
			standardInput.close();
			OurSet.closeAll();
			System.exit(0);
		}
	}

	private static int readSetNumber(int i) {
		int number;
		boolean b;
		do {
			b = true;
			number = 0;
			System.out.print("  numéro d'ensemble n" + i + " (>=0 et <"
					+ MAX_SET + ") : ");
			try {
				number = standardInput.nextInt();
			} catch (NumberFormatException e) {
				b = false;
			}
		} while (!b || (number < 0 || number >= MAX_SET));
		return number;
	}
}

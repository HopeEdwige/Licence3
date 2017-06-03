import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;


/**
 * Group 2.2
 * ANDRIAMILANTO Tompoariniaina
 * DANG Minh Ahn
 *
 * The OurSet class
 */
public class OurSet extends List<SubSet> {

//Attributes
	private static final int MAX_RANG = 128;
	private static final SubSet FLAG_VALUE = new SubSet(MAX_RANG, new SmallSet());
	private static final Scanner standardInput = new Scanner(System.in);


//Methods
	/**
	 * Initializes the OurSet
	 */
	public OurSet() {
		super();
		setFlag(FLAG_VALUE);
	}


	/**
	 * Close the Scanner
	 */
	public static void closeAll() {
		standardInput.close();
	}


	/**
	 * Compare two int
	 * @param int a The first int
	 * @param int b The second int
	 * @return A Comparison attribute
	 */
	private static Comparison compare(int a, int b) {
		if (a < b) {
			return Comparison.INF;
		}
		else if (a == b) {
			return Comparison.EGAL;
		}
		else {
			return Comparison.SUP;
		}
	}


	/**
	 * Print the actual state
	 */
	public void print() {
		System.out.println(" [version corrigée de contenu]");
		this.print(System.out);
	}



	/* 	#####################################################################################
		####################  Appartenance, Ajout, Suppression, Cardinal ####################
		##################################################################################### */
	/**
	 * Display if a value is contained or not
	 */
	public void containment() {
		System.out.println(" valeur cherchée : ");
		int x = readValue(standardInput, 0);
		if (this.containsAux(x)) {
			System.out.println(" valeur présente");
		}
		else {
			System.out.println(" valeur non présente");
		}
	}


	/**
	 * Display the menu to add one or multiple elements
	 */
	public void add() {
		System.out.println(" valeurs à ajouter (-1 pour finir) : ");
		this.addAux(System.in);
		System.out.println(" nouveau contenu :");
		this.printNewState();
	}


	/**
	 * Display the menu to remove one or multiple elements
	 */
	public void remove() {
		System.out.println("  valeurs à remove (-1 pour finir) : ");
		this.removeAux(System.in);
		System.out.println(" nouveau contenu :");
		this.printNewState();
	}


	/**
	 * Display the size of the OurSet
	 */
	public void size() {
		int size = this.sizeAux();
		System.out.println("La taille de l'ensemble est " + size);
	}


	/**
	 * Return if a value is contained in the Set or not
	 * @param int x The value to search
	 * @return true if contained, false if not
	 */
	protected boolean containsAux(int x) {

		//Only if the int has a correct value
		if ((x <= 32767) && (x >= 0)) {
			Iterator<SubSet> it = this.iterator();

			//Go to the SubSet
			while ((!it.isOnFlag()) && (it.getValue().rank < (x/256))) {
				it.goForward();
			}

			//If the correct rank and after if the element is contained
			if (it.getValue().rank == (x/256)) {
				return it.getValue().set.contains(x%256);
			}
		}

		return false;
	}


	/**
	 * Add one or multiple elements read from the keyboard
	 * @param InputStream f The InputStream from which we'll read the entries
	 */
	protected void addAux(InputStream f) {
		int x = f.read();
		Iterator<SubSet> it = this.iterator();

		//Until we read -1
		while (x != -1) {

			//Go to the correct SubSet
			while ((!it.isOnFlag()) && (it.getValue().rank < (x/256))) {
				it.goForward();
			}

			//If the correct rank, add the element to it
			if (it.getValue().rank == (x/256)) {
				it.getValue().set.add(x%256);
			}

			//If not the correct rank, the SubSet isn't created yet
			else {
				//So we'll create it
				SubSet toAdd = new SubSet((x/256), new SmallSet());

				//Add the element to the SmallSet
				toAdd.set.add(x%256);

				//And in the end, add this to the OurSet
				it.addLeft(toAdd);
			}

			//And in the end, put the Iterator to the flag and read the next int
			it.restart();
			x = f.read();
		}
	}


	/**
	 * Remove one or multiple elements read from the keyboard
	 * @param InputStream f The InputStream from which we'll read the entries
	 */
	protected void removeAux(InputStream f) {
		int x = f.read();
		Iterator<SubSet> it = this.iterator();

		//Until we read -1
		while (x != -1) {

			//Go to the correct SubSet
			while ((!it.isOnFlag()) && (it.getValue().rank < (x/256))) {
				it.goForward();
			}

			//If the correct rank, remove the element
			if (it.getValue().rank == (x/256)) {
				it.getValue().set.remove(x%256);
			}

			//And in the end, put the Iterator to the flag and read the next int
			it.restart();
			x = f.read();
		}
	}


	/**
	 * Give the size of this OurSet
	 * @return The number of elements in it
	 */
	protected int sizeAux() {
		int ret = 0;
		Iterator<SubSet> it = this.iterator();

		//Travel all the set
		while (!it.isOnFlag()) {
			ret += it.getValue().size();
			it.goForward();
		}

		//And in the end, return the result
		return ret;
	}




	/* 	#####################################################################################
		############### Différence, DifférenceSymétrique, Intersection, Union ###############
		##################################################################################### */
	/**
	 * Do the difference with another set
	 * @param OurSet set2 The set with which we'll do the difference
	 */
	public void difference(OurSet set2) {
		System.out.println();
		System.out.println("--------------------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("--------------------------------------------------------------");
		System.out.println();
	}

	public void symmetricDifference(OurSet set2) {
		System.out.println();
		System.out.println("--------------------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("--------------------------------------------------------------");
		System.out.println();
	}


	/**
	 * Do the intersection with another set
	 * @param OurSet set2 The set with which we'll do the intersection
	 */
	public void intersection(OurSet set2) {
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();

		//Stop only when we saw all the small sets of set2
		while (!it1.isOnFlag()) {
			//If at the same rank, do the union of the two sets
			if (it1.getValue().rank == it2.getValue().rank) {
				it1.getValue().set.intersection(it2.getValue().set);
				it1.goForward();
				it2.goForward();
			}

			//If the rank of set2 is behind those of this set
			else if (it2.getValue().rank < it1.getValue().rank) {
				it2.goForward();
			}

			//If the rank of this set is behind those of set2
			else {

				//Delete it1.getValue()!
				it1.goForward();
			}
		}
	}


	/**
	 * Do the union with another set
	 * @param OurSet set2 The set with which we'll do the union
	 */
	public void union(OurSet set2) {
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();

		//Stop only when we saw all the small sets of set2
		while (!it2.isOnFlag()) {
			//If at the same rank, do the union of the two sets
			if (it1.getValue().rank == it2.getValue().rank) {
				it1.getValue().set.union(it2.getValue().set);
				it1.goForward();
				it2.goForward();
			}

			//If the rank of set2 is behind those of this set
			else if (it2.getValue().rank < it1.getValue().rank) {
				it1.addLeft(it2.getValue().copy());
				it2.goForward();
			}

			//If the rank of this set is behind those of set2
			else {
				it1.goForward();
			}
		}
	}

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////// Egalité, Inclusion ////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean equals(Object o) {
		System.out.println();
		System.out.println("--------------------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("--------------------------------------------------------------");
		System.out.println();
		return false;
	}

	public void isIncludedIn(OurSet set2) {
		System.out.println();
		System.out.println("--------------------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("--------------------------------------------------------------");
		System.out.println();
	}

	// ATTENTION : fonction nécessaire pour les test
	protected boolean includedAux(OurSet set2) {
		return true;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// //////// Rangs, Restauration, Sauvegarde, Affichage //////////////
	// /////////////////////////////////////////////////////////////////////////////

	public void printRanks() {
		System.out.println(" [version corrigée de rangs]");
		this.printRanksAux();
	}

	private void printRanksAux() {
		int k = 0;
		System.out.println(" Rangs présents :");
		Iterator<SubSet> it = this.iterator();
		while (!it.isOnFlag()) {
			// Ecriture.ecrireInt(it.getValue().rang, 4);
			System.out.print("" + it.getValue().rank + "  ");
			k = k + 1;
			if (k == 10) {
				System.out.println();// Ecriture.ecrireStringln("");
				k = 0;
			}
			it.goForward();
		}
		if (k > 0) {
			System.out.println();// Ecriture.ecrireStringln("");
		}
	}

	public void restore() {
		String fileName = readFileName();
		InputStream inFile;
		try {
			inFile = new FileInputStream(fileName);
			System.out.println(" [version corrigée de restauration]");
			this.clear();
			this.addAux(inFile);
			inFile.close();
			System.out.println(" nouveau contenu :");
			this.printNewState();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("fichier " + fileName + " inexistant");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("probleme de fermeture du fichier " + fileName);
		}
	}

	public void save() {
		System.out.println(" [version corrigée de sauvegarde]");

		OutputStream outFile;
		try {
			outFile = new FileOutputStream(readFileName());
			this.print(outFile);
			outFile.write("-1\n".getBytes());
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("pb ouverture fichier lors de la sauvegarde");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("probleme de fermeture du fichier");
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int k = 0;
		SubSet subSet;
		int startValue;
		Iterator<SubSet> it = this.iterator();
		while (!it.isOnFlag()) {
			subSet = it.getValue();
			startValue = subSet.rank * 256;
			for (int i = 0; i < 256; ++i) {
				if (subSet.set.contains(i)) {
					String number = String.valueOf(startValue + i);
					int numberLength = number.length();
					for (int j = 6; j > numberLength; --j) {
						number += " ";
					}
					result.append(number);
					++k;
					if (k == 10) {
						result.append("\n");
						k = 0;
					}
				}
			}
			it.goForward();
		}
		if (k > 0) {
			result.append("\n");
		}
		return result.toString();
	}

	private void print(OutputStream f) {
		try {
			String string = this.toString();
			f.write(string.getBytes());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printNewState() {
		this.print(System.out);
		System.out.println(" Nombre d'éléments : " + this.sizeAux());
		this.printRanksAux();
	}

	private static int readValue(Scanner s, int min) {
		int value = s.nextInt();
		while (value < min || value > 32767) {
			System.out.println("valeur incorrecte");
			value = s.nextInt();
		}
		return value;
	}

	private static String readFileName() {
		System.out.print(" nom du fichier : ");
		String fileName = standardInput.next();
		return fileName;
	}
}

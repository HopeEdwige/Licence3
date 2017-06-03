import java.util.Scanner;


/**
 * A class to get an array of sorted Pair objects
 */
public class InsertionPair {

	private static final int SIZE_MAX = 10;
	private int size;
	private Pair[] array = new Pair[SIZE_MAX];


	/**
	 * The constructor
	 * Just initializes the size to 0
	 */
	public InsertionPair(){
		this.size = 0;
	}


	/**
	 * Get a copy of the current array
	 * @return Pair[] A copy of the current array
	 */
	public Pair[] toArray(){
		Pair[] copy = new Pair[SIZE_MAX];
		if (size > 0) {
			for (int i = 0; i < this.size; i++) {
				Pair p = new Pair(this.array[i]);
				copy[i] = p;
			}
		}
		return copy;
	}


	/**
	 * Insert a Pair object into the current array
	 * The array is sorted before and after the insertion
	 * @param Pair toInsert The new Pair object to insert
	 * @return boolean true if the element is inserted, false if error encountered
	 */
	public boolean insert(Pair toInsert) {
		boolean result = false;
		if (this.size == SIZE_MAX) {
			result = false;
		} else {
			int i = 0;
			boolean fini = false;
			while (i < this.size && !fini) {
				if (this.array[i].less(toInsert)) {
					i++;
				} else if (this.array[i].equals(toInsert)) {
					fini = true;
				} else {
					this.size ++;
					for (int j = this.size-1; j > i; j--){
						this.array[j] = this.array[j-1];
					}
					this.array[i] = toInsert;
					result = true;
					fini = true;
				}
			}
			if (i == this.size) {
				this.array[this.size] = toInsert;
				this.size ++;
				result = true;
			}

		}

		return result;
	}


	/**
	 * Get a displayable description of this object and its current state
	 * @return String A displayable description of this object
	 */
	public String toString() {
		String ret = "";

		ret = ret + "Array de taille " + this.size + " dont le contenu est ";

		for (int i = 0; i < this.size-1; i++) {
			ret = ret + this.array[i].toString() + ", ";
		}
		ret = ret + this.array[this.size-1].toString() + ".";

		return ret;
	}


	/**
	 * Initializes the values of the array
	 * @param Scanner scanner The scanner object where we'll get the inputs
	 */
	public void createArray(Scanner scanner) {
		int entry = scanner.nextInt();
		int last;
		boolean fini = false;
		while ((entry != -1) && !fini) {
			last = scanner.nextInt();
			if (last != -1) {
				Pair p = new Pair(entry, last);
				this.insert(p);
				entry = scanner.nextInt();
			} else {
				fini = true;
			}
		}
		System.out.println(this.toString());
	}
}

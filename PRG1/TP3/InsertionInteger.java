import java.util.Scanner;


/**
 * A class to get an array of sorted integers
 */
public class InsertionInteger {

	private static final int SIZE_MAX = 10;
	private int size;
	private int[] array = new int[SIZE_MAX];


	/**
	 * The constructor
	 * Just initializes the size to 0
	 */
	public InsertionInteger() {
		this.size = 0;
	}


	/**
	 * Get a copy of the current array
	 * @return int[] A copy of the current array
	 */
	public int[] toArray() {
		int[] copy = new int[SIZE_MAX];
		if (size > 0) {
			for (int i = 0; i < this.size; i++) {
				copy[i] = this.array[i];
			}
		}
		return copy;
	}


	/**
	 * Insert an integer into the current array
	 * The array is sorted before and after the insertion
	 * @param int value The value of the new element to insert
	 * @return boolean true if the element is inserted, false if error encountered
	 */
	public boolean insert(int value) {
		boolean result = false;
		if (this.size == SIZE_MAX) {
			result = false;
		} else {
			int i = 0;
			boolean fini = false;
			while (i < this.size && !fini) {
				if (this.array[i] < value) {
					i++;
				} else if (this.array[i] == value) {
					result = false;
					fini = true;
				} else {
					this.size ++;
					for (int j = this.size-1; j > i; j--){
						this.array[j] = this.array[j-1];
					}
					this.array[i] = value;
					result = true;
					fini = true;
				}
			}
			if (i == this.size) {
				this.array[this.size] = value;
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

		ret = ret + "Array de taille " + this.size + " dont le contenu est [";

		for (int i = 0; i < this.size-1; i++) {
			ret = ret + this.array[i] + ", ";
		}
		ret = ret + this.array[this.size-1] + "].";

		return ret;
	}


	/**
	 * Initializes the values of the array
	 *�@param Scanner scanner The scanner object where we'll get the inputs
	 */
	public void createArray(Scanner scanner) {
		int entry = scanner.nextInt();
		while(entry != -1) {
			this.insert(entry);
			entry = scanner.nextInt();
		}
	}

}

import java.util.Scanner;


public class InsertionInteger {
	
	private static final int SIZE_MAX = 10;
	private int size;
	private int[] array = new int[SIZE_MAX];

	
	public InsertionInteger(){
		this.size = 0;
	}

	
	public int[] toArray(){
		int[] copy = new int[SIZE_MAX];
		if (size > 0) {
			for (int i = 0; i < this.size; i++) {
				copy[i] = this.array[i];
			}
		}
		return copy;
	}
	
	
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
	
	
	public String toString() {
		String ret = "";
		
		ret = ret + "Array de taille " + this.size + " dont le contenu est [";
		
		for (int i = 0; i < this.size-1; i++) {
			ret = ret + this.array[i] + ", ";
		}
		ret = ret + this.array[this.size-1] + "].";
		
		return ret;
	}
	
	
	public void createArray(Scanner scanner) {
		System.out.println("Veuillez entrer la suite d'entiers finis par -1:");
		int entry = scanner.nextInt();
		while(entry != -1) {
			this.insert(entry);
		}
	}
	
}

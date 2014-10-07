/**
 * A class to represent a couple of integers
 */
public class Pair {
	
	public int x;
	public int y;
	

	/**
	 * The constructor initializes the two attributes
	 * @param int a The first integer of the couple
	 * @param int b The second one
	 */
	public Pair(int a, int b){
		this.x = a;
		this.y = b;
	}
	

	/**
	 * The constructor initializes the two attributes
	 * @param Pair p Pair object from which we'll get the values
	 */
	public Pair(Pair p){
		this.x = p.x;
		this.y = p.y;
	}


	/**
	 * Method to know if two Pair objects are equals
	 * @param Pair p Pair object to compare with this one
	 * @return true if the values of the two objects are equals, false if not
	 */
	public boolean equals(Pair p) {
		if (p == null) {
			return false;
		}
		else {
			if (this.x == p.x && this.y == p.y) {
				return true;
			} else {
				return false;
			}
		}
	}

	
	/**
	 * Get a displayable description of this object and its current state
	 * @return String A displayable description of this object
	 */
	public String toString() {
	    return "[" + this.x + ", " + this.y + "]";
	}
	

	/**
	 * Method to know if the current object is lower than another
	 * @param Pair p Pair object to compare with this one
	 * @return true if the values of the current object are lower, false if not
	 */
	public boolean less(Pair p) {
		if (p == null) {
			return false;
		} else {
			if (this.x < p.x || this.x == p.x && this.y < p.y){
				return true;
			} else {
				return false;
			}
		}
	}

}
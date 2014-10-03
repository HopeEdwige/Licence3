// une classe représentant des doublets non modifiables

public class Pair {
	
	public int x;
	public int y;
	
	public Pair(int a, int b){
		this.x = a;
		this.y = b;
	}
	
	public Pair(Pair p){
		this.x = p.x;
		this.y = p.y;
	}


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

	
	public String toString() {
	    return "[" + this.x + ", " + this.y + "]";
	}
	
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

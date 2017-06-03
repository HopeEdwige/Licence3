/**
 * The content of the OurSet class
 */
public class SubSet implements SuperT {

//Attributes
	public final int rank;
	public SmallSet set;


//Methods
	/**
	 * The constructor
	 */
	public SubSet() {
		rank = 0;
		set = new SmallSet();
	}


	/**
	 * The constructor with parameters
	 * @param int r The rank
	 * @param SmallSet f The SmallSet to initialize it
	 */
	public SubSet(int r, SmallSet f) {
		rank = r;
		set = f;
	}


	@Override
	/**
	 * Copy this SubSet
	 */
	public SubSet copy() {
		return new SubSet(rank, set.copy());
	}


	@Override
	/**
	 * Get a text display of this object
	 * @return String A text display of this object
	 */
	public String toString() {
		return "rang = " + rank + ",  " + set;
	}



	@Override
	/**
	 * Check if this object an another one are equals
	 * @param Object o The object to check
	 * @return true if the two objects are equal, false if not
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SubSet)) {
			return false;
		}
		SubSet f = (SubSet)obj;
		return f.rank == rank && f.set.equals(set);
	}
}

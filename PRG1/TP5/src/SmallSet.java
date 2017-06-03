/**
 * Group 2.2
 * ANDRIAMILANTO Tompoariniaina
 * DANG Minh Ahn
 *
 * The SmallSet class
 */
public class SmallSet {

	private boolean[] tab = new boolean[256];


	/**
	 * Constructor
	 */
	public SmallSet() {
		//Initializes the set
		for (int i = 0; i < 256; i++) {
			tab[i] = false;
		}
	}


	/**
	 * Constructor
	 * @param boolean[] t The tab from which to get the values
	 */
	public SmallSet(boolean[] t) {
		//Initializes the set
		for (int i = 0; i < 256; i++) {
			tab[i] = t[i];
		}
	}


	/**
	 * Get the number of values of this set
	 * @return int The number of values
	 */
	public int size() {
		int count = 0;

		for (int i = 0; i < 256; i++) {
			if (tab[i])
				count++;
		}

		return count;
	}


	/**
	 * Check if the set contains a value
	 * @param int x The value to check
	 * @return true if the set contains x, false if not
	 */
	public boolean contains(int x) {
		if ((x >= 0) && (x < 256))
			return tab[x];

		else
			return false;
	}


	/**
	 * Check if the set is empty
	 * @return true if the set is empty, false if not
	 */
	public boolean isEmpty() {
		for (int i = 0; i < 256; i++) {
			if (tab[i])
				return false;
		}

		return true;
	}


	/**
	 * Add a value to the set
	 * @param int x The value to add
	 */
	public void add(int x) {
		if ((x >= 0) && (x < 256))
			tab[x] = true;
	}


	/**
	 * Remove a value from the set
	 * @param int x The value to remove
	 */
	public void remove(int x) {
		if ((x >= 0) && (x < 256))
			tab[x] = false;
	}


	/**
	 * Add multiple values to the set
	 * @param int deb The value from which we begin
	 * @param int fin The value where we'll stop
	 */
	public void addInterval(int deb, int fin) {
		if ((deb >= 0) && (deb < 255) && (fin > 0) && (deb < 256) && (deb < fin)) {
			for (int i = deb; i <= fin; i++) {
				tab[i] = true;
			}
		}
	}


	/**
	 * Remove multiple values
	 * @param int deb The value from which we begin
	 * @param int fin The value where we'll stop
	 */
	public void removeInterval(int deb, int fin) {
		if ((deb >= 0) && (deb < 255) && (fin > 0) && (deb < 256) && (deb < fin)) {
			for (int i = deb; i <= fin; i++) {
				tab[i] = false;
			}
		}
	}


	/**
	 * Do the union with a given set
	 * @param SmallSet f The set
	 */
	public void union(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			if (f.contains(i))
				tab[i] = true;
		}
	}


	/**
	 * Do the intersection with a given set
	 * @param SmallSet f The set
	 */
	public void intersection(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			tab[i] = ((f.contains(i)) && (tab[i])) ? true : false;
		}
	}


	/**
	 * Do the difference with a given set
	 * @param SmallSet f The set
	 */
	public void difference(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			if ((f.contains(i)) && (tab[i]))
				tab[i] = false;
		}
	}


	/**
	 * Do the symetric difference with a given set
	 * @param SmallSet f The set
	 */
	public void symetricDifference(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			tab[i] = ((f.contains(i)) && (tab[i])) ? false : true;
		}
	}


	/**
	 * Replace this set with its complement
	 */
	public void complement() {
		for (int i = 0; i < 256; i++) {
			tab[i] = (tab[i]) ? false : true;
		}
	}


	/**
	 * Clear this set
	 */
	public void clear() {
		for (int i = 0; i < 256; i++) {
			tab[i] = false;
		}
	}


	/**
	 * Check if this set is included in another one
	 * @param SmallSet f The another set
	 * @return true if f is included in this set, false if not
	 */
	public boolean isIncludedIn(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			if ((tab[i]) && (!f.contains(i)))
				return false;
		}

		return true;
	}


	/**
	 * Check if this object an another one are equals
	 * @param Object o The object to check
	 * @return true if the two objects are equal, false if not
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		else if (!(o instanceof SmallSet)) {
			return false;
		}

		else {
			SmallSet ss = (SmallSet)o;
			for (int i = 0; i < 256; i++) {
				if (tab[i] != ss.contains(i))
					return false;
			}

			return true;
		}
	}


	/**
	 * Return a copy of this object
	 * @return A copy of this object
	 */
	public SmallSet copy() {
		return new SmallSet(tab);
	}


	/**
	 * Get a text display of this object
	 * @return String A text display of this object
	 */
	@Override
	public String toString() {
		String ret = "El�ments pr�sents: ";
		for (int i = 0; i < 256; i++) {
			if (this.tab[i])
				ret += i + " ";
		}
		return ret;
	}

}

/**
 * Group 2.2
 * ANDRIAMILANTO Tompoariniaina
 * DANG Minh Ahn
 *
 * The List class
 */
public class List<T extends SuperT> {

	private Element flag;


	/**
	 * Create a new List
	 */
	public List() {
		//Just create a flag and link it to itself
		this.flag = new Element();
		this.flag.right = this.flag;
		this.flag.left = this.flag;
	}


	/**
	 * Get an iterator for this list
	 * @return ListIterator An iterator for this list
	 */
	public ListIterator iterator() {
		return new ListIterator();
	}


	/**
	 * Check if the list is empty or not
	 * @return true if empty, false if not
	 */
	public boolean isEmpty() {
		ListIterator it = this.iterator();

		//Just check if it's on the flag or not
		return (it.isOnFlag());
	}


	/**
	 * Clear the list
	 */
	public void clear() {
		ListIterator it = this.iterator();
		while (!it.isOnFlag()) {
			it.remove();
			it.goForward();
		}
	}


	/**
	 * Set a new flag
	 */
	public void setFlag(T v) {
		//Create the new flag
		Element newFlag = new Element();
		newFlag.value = v;
		newFlag.right = this.flag.right;
		newFlag.left = this.flag.left;

		//Put him
		this.flag = newFlag;
	}


	/**
	 * Add an element on the head
	 */
	public void addHead(T v) {
		ListIterator it = this.iterator();
		it.addLeft(v);
	}


	/**
	 * Add an element on the tail
	 */
	public void addTail(T v) {
		ListIterator it = this.iterator();

		//Go to the flag
		while (!it.isOnFlag()) {
			it.goForward();
		}

		//Add the element on the left of the flag
		it.addLeft(v);
	}


	/**
	 * Create a copy of this object
	 * @return List<T> A copy of this object
	 */
	public List<T> copy()  {
	    List<T> nouvListe = new List<T> ();
		ListIterator p = this.iterator();
		while (!p.isOnFlag()) {
			nouvListe.addTail((T) p.getValue().copy()); // UNE COPIE EST NECESSAIRE !!!
			p.goForward();
		}
		return nouvListe;
	}


	@Override
	/**
	 * Get a correct display of this element
	 * @return String A correct display of this element
	 */
	public String toString() {
		String s = "contenu de la liste : \n";
		ListIterator p = this.iterator();
		while (!p.isOnFlag()) 
		{
			s = s + p.getValue().toString() + " ";
			p.goForward();
		}
		return s;
	}



	/* 	#####################################################################################
		################################## Internal classes #################################
		##################################################################################### */
	/**
	 * The Element class (double links list)
	 */
	private class Element{
		//Attributes
		public T value;
		public Element left, right;
		
		/**
		 * Constructor of Element class
		 */
		public Element() {
			value = null;
			left = null;
			right = null;
		}
	}


	/**
	 * The list iterator class
	 */
	public class ListIterator implements Iterator<T> {

		//The current element
		private Element current;


		/**
		 * Default constructor
		 */
		private ListIterator() {
			//Put the current element on the next of the flag
			this.current = List.this.flag.right;
		}


		@Override
		/**
		 * Go to the next element
		 */
		public void goForward() {
			this.current = this.current.right;
		}


		@Override
		/**
		 * Go to the previous element
		 */
		public void goBackward() {
			this.current = this.current.left;
		}


		@Override
		/**
		 * Go to the first element
		 */
		public void restart() {
			this.current = List.this.flag.right;
		}


		@Override
		/**
		 * Check if on the flag
		 * @return true if on the flag, false if not
		 */
		public boolean isOnFlag() {
			return (this.current.value == null);
		}


		@Override
		/**
		 * Remove the current element and put the current element on the last one
		 */
		public void remove() {	
			try {
				//We can't delete the flag
				assert current != flag : "\n\n\nImpossible de retirer le drapeau\n\n\n";

				//Store the current element in a temporary pointer
				Element tmp = this.current;

				//The current element is the last one
				this.current = this.current.left;

				//And his next is the next of the one which was the current
				this.current.right = tmp.right;

				//And in the end, the one to remove is deleted
				tmp = null;
			}
			catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}


		@Override
		/**
		 * Get the value of the current element
		 * @return T The value of the current element
		 */
		public T getValue() {
			return this.current.value;
		}


		@Override
		/**
		 * Go to the next element and return its value
		 * @return T The value of the next element
		 */
		public T nextValue() {
			this.current = this.current.right;
			return this.current.value;
		}


		@Override
		/**
		 * Add an element on the left
		 * @param T v The new element to add on the left
		 */
		public void addLeft(T v) {
			//Create a new element
			Element newElement = new Element();
			newElement.value = v;
			newElement.right = this.current;
			newElement.left = this.current.left;

			//Link it
			this.current.left.right = newElement;
			this.current.left = newElement;
			this.current = newElement;
		}


		@Override
		/**
		 * Add an element on the right
		 * @param T v The new element to add on the right
		 */
		public void addRight(T v) {
			//Create a new element
			Element newElement = new Element();
			newElement.value = v;
			newElement.right = this.current.right;
			newElement.left = this.current;

			//Link it
			this.current.right.left = newElement;
			this.current.right = newElement;
			this.current = newElement;
		}


		@Override
		/**
		 * Set a value
		 * @return T v The new value of the current element
		 */
		public void setValue(T v) {
			this.current.value = v;
		}


		@Override
		/**
		 * Get a correct display of this element
		 * @return String A correct display of this element
		 */
		public String toString() {
			return "Parcours de liste : pas d'affichage possible\n";
		}

	}
}
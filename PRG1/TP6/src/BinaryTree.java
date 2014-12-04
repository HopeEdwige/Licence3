import java.util.Stack;


/**
 * The binary tree class
 */
public class BinaryTree <T> {

	//Attributes
	private Element root;


	/**
	 * Constructor of binary tree
	 */
	public BinaryTree() {
		//Just create the root
		root = new Element();
	}


	/**
	 * Method to get the iterator
	 */
	public TreeIterator iterator() {
		return new TreeIterator();
	}


	/**
	 * Check if empty or not
	 * @return true if empty, false if not
	 */
	public boolean isEmpty() {
		return this.iterator().isEmpty();
	}



	/* ################################### Inner classes ############################## */
	/**
	 * The Iterator class
	 */
	public class TreeIterator implements Iterator<T> {

		//Attributes
		private Element currentNode;
		private Stack<Element> stack;


		/**
		 * Constructor for the iterator
		 */
		private TreeIterator() {
			stack = new Stack<Element>();
			currentNode = root;
		}


		@Override
		/**
		 * Go to the left son
		 */
		public void goLeft() {
			try	{
				assert !this.isEmpty() : "Le butoir n'a pas de fils";
				assert currentNode.left != null : "Le fils gauche n'existe pas";

				p.push(currentNode);
				currentNode = currentNode.left;
			}
			catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}


		@Override
		/**
		 * Go to the right son
		 */
		public void goRight() {
			try {
				assert !this.isEmpty() : "Le butoir n'a pas de fils";
				assert currentNode.right != null : "Le fils droit d'existe pas";

				p.push(currentNode);
				currentNode = currentNode.right;
			}
			catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}


		@Override
		/**
		 * Go to the father
		 */
		public void goUp() {
			try {
				assert !stack.empty() :"La racine n'a pas de pere";

				currentNode = stack.pop();
			}
			catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}


		@Override
		/**
		 * Go to the root
		 */
		public void goRoot() {
			Element tmp = null;
			while (!stack.empty()) {
				tmp = stack.pop();
			}
			if (tmp != null)
				currentNode = tmp;
			else
				System.out.println("Arbre vide!");
		}


		@Override
		/**
		 * Check if empty or not
		 * @return true if empty, false it not
		 */
		public boolean isEmpty() {
			return (currentNode.left == null && currentNode.right == null);
		}


		@Override public NodeType nodeType() {
			return NodeType.BUTOIR;
		}

		@Override public void remove() {
			try {
				assert !this.isEmpty() : "retirer : Butoir!";
				assert nodeType() != NodeType.DOUBLE : "retirer : retrait d'un noeud double non permis";
				Element newNode = null;

				switch (this.nodeType()) {
					case LEAF:
						newNode = new Element();
						break;

					case SIMPLE-LEFT:
						newNode = currentNode.left;
						break;

					case SIMPLE-RIGHT:
						newNode = currentNode.right;
						break;
				}

				currentNode.value = newNode.value;
				currentNode.left = newNode.left;
				currentNode.right = newNode.right;
			}
			catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		@Override public void clear() {
		}

		@Override public T getValue() {
			return null;
		}

		@Override public void addValue(T v) {
			try {
			assert isEmpty() : "Ajouter : on n'est pas sur un butoir";
			}
			catch (AssertionError e) {
			e.printStackTrace();
			System.exit(0);
			}
		}

		@Override public void setValue(T v) {
		}

		private void ancestor(int i, int j) {
			try {
			assert !stack.empty() : "switchValue : argument trop grand";
			}
			catch (AssertionError e) {
			e.printStackTrace();
			System.exit(0);
			}
			Element x = stack.pop();
			if (j<i) {
			ancestor(i, j+1);
			}
			else {
			T v = x.value;
			x.value = currentNode.value;
			currentNode.value = v;
			}
			stack.push(x);
		}

		@Override public void switchValue(int i) {
			try {
			assert i>= 0 : "switchValue : argument n?atif";
			}
			catch (AssertionError e) {
			e.printStackTrace();
			System.exit(0);
			}
			if (i>0) {
			ancestor(i, 1);
			}
		}
	}


	/**
	 * The Element class
	 */
	private class Element {
		// element de ArbreBinaire <Item>
		public T value;
		public Element left, right;

		/**
		 * The constructor
		 */
		public Element() {
			value = null;
			left = null;
			right = null;
		}

		/**
		 * Check if empty or not
		 * @return true if empty, false it not
		 */
		public boolean isEmpty() {
			return (left == null && right == null);
		}
	}
}
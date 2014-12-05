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
		root.left = new Element();
		root.right = new Element();
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
				//Check if we don't use empty elements
				assert !this.isEmpty() : "Le butoir n'a pas de fils.";
				assert currentNode.left != null : "Le fils gauche n'existe pas.";

				//Put the current node in the father's stack and move
				stack.push(currentNode);
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
				//Check if we don't use empty elements
				assert !this.isEmpty() : "Le butoir n'a pas de fils.";
				assert currentNode.right != null : "Le fils droit n'existe pas.";

				//Put the current node in the father's stack and move
				stack.push(currentNode);
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
				//Check if we don't use empty elements
				assert !stack.empty() : "La racine n'a pas de pere.";

				//Then go to the last father encountered
				if (!stack.empty())
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

			//Empty the stack to get the first father putted in it
			while (!stack.empty()) {
				tmp = stack.pop();
			}

			//If there's at least one element in it
			if (tmp != null)
				currentNode = tmp;
		}


		@Override
		/**
		 * Check if empty or not (if a SENTINEL or not)
		 * @return true if empty, false it not
		 */
		public boolean isEmpty() {
			return currentNode.isEmpty();
		}


		@Override
		/**
		 * Get the NodeType of the current Element
		 * @return The node type of the current Element
		 */
		public NodeType nodeType() {
			//The NodeType to return
			NodeType ret;

			//If a SENTINEL, the two under elements are null
			if (currentNode.isEmpty()) {
				ret = NodeType.SENTINEL;
			}

			//If not, it can be all the others
			else if ((currentNode.left != null) && (currentNode.right != null)) {
				//Check the left
				stack.push(currentNode);
				currentNode = currentNode.left;

				//If the left is a SENTINEL
				if (nodeType() == NodeType.SENTINEL) {
					//Check the right now
					currentNode = stack.peek();
					currentNode = currentNode.right;

					//If the right is a SENTINEL and the left TOO
					if (nodeType() == NodeType.SENTINEL) {
						ret = NodeType.LEAF;
					}

					//If the right isn't a SENTINEL and the left is ONE
					else {
						ret = NodeType.SIMPLE_RIGHT;
					}
				}

				//If not
				else {
					//Check the right now
					currentNode = stack.peek();
					currentNode = currentNode.right;

					//If the right is a SENTINEL and the left NOT
					if (nodeType() == NodeType.SENTINEL) {
						ret = NodeType.SIMPLE_LEFT;
					}

					//If the right isn't a SENTINEL and the left NOT TOO
					else {
						ret = NodeType.DOUBLE;
					}
				}

				//And in the end, just go to the element where we were first
				currentNode = stack.pop();
			}

			//Impossible situation
			else {
				System.out.println("NodeType impossible!");
				ret = NodeType.SENTINEL;
			}

			//Return the result
			return ret;
		}


		@Override
		/**
		 * Remove the current Element
		 */
		public void remove() {
			try {
				//Check if the current Element is correct
				assert !this.isEmpty() : "Retirer : Butoir!";
				assert nodeType() != NodeType.DOUBLE : "Retirer : Retrait d'un noeud double non permis.";

				//If correct, remove it in function of its type
				Element newNode = new Element();
				switch (this.nodeType()) {
					/*case LEAF:
						newNode = new Element();
						break;*/

					case SIMPLE_LEFT:
						newNode = currentNode.left;
						break;

					case SIMPLE_RIGHT:
						newNode = currentNode.right;
						break;
				}

				//And link the current node then
				currentNode.value = newNode.value;
				currentNode.left = newNode.left;
				currentNode.right = newNode.right;
			}
			catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}


		@Override
		/**
		 * Remove all the elements of the subtree which begins from the current Element
		 */
		public void clear() {
			if (!isEmpty()) {
				clearAux(this);
			}
		}


		/**
		 * The aux method to clear the tree
		 */
		private void clearAux(TreeIterator it) {
			//While not empty
			if (!it.isEmpty()) {
				//Left then right then itself
				it.goLeft();
				clearAux(it);
				it.goUp();
				it.goRight();
				it.clearAux(it);
				it.goUp();

				//Just delete the elements (if doesn't work, use the remove method instead)
				it.remove();
			}
		}


		@Override
		/**
		 * Get the value of the current Element
		 * @return T The value contained in the current element
		 */
		public T getValue() {
			return currentNode.value;
		}


		@Override
		/**
		 * Add a new value in the tree
		 * @return T v The new value
		 */
		public void addValue(T v) {
			try {
				//Only if on a SENTINEL
				assert isEmpty() : "Ajouter : On n'est pas sur un butoir.";

				//Create the new element and add it
				currentNode.left = new Element();
				currentNode.right = new Element();
				currentNode.value = v;
			}
			catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}


		@Override
		/**
		 * Set the value of the current Element
		 * @param T v The new value contained in the current element
		 */
		public void setValue(T v) {
			currentNode.value = v;
		}


		@Override
		/**
		 * Switch the value of the current element with the one of the ancestor i
		 * @param int i The ancestor of the i generation
		 */
		public void switchValue(int i) {
			try {
				assert i >= 0 : "SwitchValue : Argument negatif.";
			}
			catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}

			//If i == 0, it's the element itself
			if (i > 0) {
				ancestor(i, 1);
			}
		}


		private void ancestor(int i, int j) {
			//Just don't take more elements that there are in the stack
			try {
				assert !stack.empty() : "SwitchValue : Argument trop grand.";
			}
			catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}

			//Get the ancestor
			Element x = stack.pop();

			//We stop when we come to the right ancestor (j == i)
			if (j < i) {
				ancestor(i, j+1);
			}

			//If this, just switch the value of the two elements
			else {
				T v = x.value;
				x.value = currentNode.value;
				currentNode.value = v;
			}

			//Each time we go up, we put the ancestor seen in the stack
			stack.push(x);
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
			return ((left == null) && (right == null));
		}
	}
}
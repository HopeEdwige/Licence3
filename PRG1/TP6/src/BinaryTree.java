import java.util.Stack;

public class BinaryTree <T> {

    /*
      arbre binaire de <Item> :
      chaînage références pour les fils : Element left, right
      pile des pères : Stack<Element> pile
    */

    private class Element{
	// élément de ArbreBinaire <Item>
	public T value;
	public Element left, right;
	public Element() 
	{
	    value = null;
	    left = null;
	    right = null;
	}
	public boolean isEmpty()
	{
	    return left == null && right == null;
	}
    }

    private Element root;

    public BinaryTree() {
    }

    public TreeIterator iterator ()
    {
	return new TreeIterator();
    }

    public boolean isEmpty()
    {
	return false;
    }

    public class TreeIterator implements Iterator<T>
    {
	private Element currentNode;
	private Stack<Element> stack;

	private TreeIterator() {
	    stack = new Stack<Element>();
	    currentNode = root;
	}

	@Override public void goLeft() {
	    try	{
		assert !this.isEmpty() : "le butoir n'a pas de fils";
		assert currentNode.left != null : "le fils gauche d'existe pas";
	    }
	    catch (AssertionError e) {
		e.printStackTrace();
		System.exit(0);
	    }
	}

	@Override public void goRight() {
	    try {
		assert !this.isEmpty() : "le butoir n'a pas de fils";
		assert currentNode.right != null : "le fils droit d'existe pas";
	    }
	    catch (AssertionError e) {
		e.printStackTrace();
		System.exit(0);
	    }
	}

	@Override public void goUp() {
	    try {
		assert !stack.empty() :" la racine n'a pas de père";
	    }
	    catch (AssertionError e) {
		e.printStackTrace();
		System.exit(0);
	    }
	}

	@Override public void goRoot() {
	}

	@Override public boolean isEmpty() {
	    return false;
	}

	@Override public NodeType nodeType() {
	    return NodeType.BUTOIR;
	}

	@Override public void remove() {
	    try {
		assert nodeType() != NodeType.DOUBLE : 
		"retirer : retrait d'un noeud double non permis";
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
		assert i>= 0 : "switchValue : argument négatif";
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
}

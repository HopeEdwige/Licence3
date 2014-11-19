public class NodeState {

	public final int e;
	
	private static final NodeState ZERO = new NodeState(0);
	private static final NodeState ONE = new NodeState(1);
	private static final NodeState TWO = new NodeState(2);

	private NodeState(int x) {
		e = x;
	}

	public static NodeState valueOf(int x) {
		if (x == 0) {
			return ZERO;
		}
		else if (x == 1) {
			return ONE;
		}
		else if (x == 2) {
			return TWO;
		}
		try {
			assert x == 0 || x == 1 || x == 2 : "Valeur incorrecte pour NodeState";
		}
		catch (AssertionError e) {
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}


        public NodeState copy() {
		return this;
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
		// pas besoin de tester la classe, il n'y a pas d'objets identiques
	}

	@Override
	public int hashCode() {
		return e;
	}

	@Override
	public String toString() {
		return "etat = " + e;
	}
}

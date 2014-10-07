public class Tri {

	public static void triNaif(int[] T) {
		int n = T.length;
		for (int i = 0; i <= n - 2; ++i) {

			int rangmin = i;
			// System.out.println("rangmin = "+rangmin);
			for (int j = i + 1; j <= n - 1; ++j) {
				if (T[j] < T[rangmin]) {
					rangmin = j;
					// System.out.println("rangmin = "+rangmin);
				}
			}

			int aux = T[i];
			T[i] = T[rangmin];
			T[rangmin] = aux;
			// System.out.println("T[i] = "+T[i]+", T[rangmin] = "+T[rangmin]);
		}
	}
	
	
	public static boolean rechDicho(int[] T, int a) {
		int n = T.length;
		int deb = 0, fin = n - 1, milieu = (deb + fin) / 2;
		while ((deb <= fin) && (a != T[milieu])) {
			if (a < T[milieu]) {
				fin = milieu - 1;
			}
			else if (a > T[milieu]) {
				deb = milieu + 1;
			}
			
			milieu = (deb + fin) / 2;
		}
		
		return (deb <= fin);
	}
	

	public static void main(String[] arg) {

		int[] T = { 0, 1, 4, 10, 2, 8, 0, 40, 57, 21, 8 };

		triNaif(T);

		for (int i = 0; i < T.length; ++i) {

			System.out.print("" + T[i] + ", ");
		}
		
		if (rechDicho(T, 8)) {
			System.out.print("Gotcha!");
		}
		else {
			System.out.print("Nope");
		}
	}

}

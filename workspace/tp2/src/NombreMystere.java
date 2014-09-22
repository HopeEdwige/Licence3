public class NombreMystere {

	public static int[] getTab(int x) {
		// pre-requis : i > 0
		// rend le i ème chiffre

		int[] tab = new int[getNbChiffres(x)];
		for (int i = 0; i < tab.length; ++i) {
			tab[i] = getChiffre(x, i);
		}

		return tab;
	}

	public static int getChiffre(int x, int i) {
		int result = x;
		int j = 0;

		while (j < i) {

			result /= 10;
			++j;
		}

		return result % 10;

	}

	public static int getNbChiffres(int x) {
		int result = x;
		int count = 0;
		while (result != 0) {
			result /= 10;
			++count;
		}
		return count;
	}
	
	public static boolean check(int x) {
		//Create the final tab
		int[] tabX2 = getTab(x * x);
		int[] tabX3 = getTab(x * x * x);
		
		int[] tab = new int[tabX2.length + tabX3.length];
		for (int tmpx2 = 0; tmpx2 < tabX2.length; tmpx2++) {
			tab[tmpx2] = tabX2[tmpx2];
		}
		for (int tmpx3 = tabX2.length; tmpx3 < tab.length; tmpx3++) {
			tab[tmpx3] = tabX3[tmpx3 - tabX2.length];
		}
		
		boolean[] ret = new boolean[10];
		for (int i = 0; i < 10; i++) {
			ret[i] = false;
		}

		for (int j = 0; j < tab.length; j++) {
			if (!ret[tab[j]]) {
				ret[tab[j]] = true;
			}
			else {
				return false;
			}
		}
		
		boolean ok = true;
		int tmp = 0;
		while ((ok) && (tmp < 10)) {
			if (!ret[tmp]) {
				ok = false;
			}
			tmp++;
		}
		
		return ok;
	}

	public static void main(String[] args) {
		int n = 0;
		while(getTab(n * n * n).length + getTab(n * n).length <= 10){
			if(check(n)){
				System.out.println(""+n);
			}
			++n;
		}
	}

}

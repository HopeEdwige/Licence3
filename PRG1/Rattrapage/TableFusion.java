package rattrapage_java;

public class TableFusion {

	public static int[] tableFusion(int[] x1, int[] x2) {
		// pré-requis : les tableaux x1 et x2 doivent être triés par ordre
		// croissant

		int[] result = new int[x1.length + x2.length];

		int k = 0;
		int i = 0;
		int j = 0;

		while (k < result.length) {

			if (i < x1.length && j < x2.length) {

				if (x1[i] < x2[j]) {
					result[k] = x1[i++];
				} else {
					result[k] = x2[j++];
				}

			} else if (i < x1.length) {
				result[k] = x1[i++];
			} else if (j < x2.length) {
				result[k] = x2[j++];
			}
			++k;
		}

		/*
		 * while (i < x1.length && j < x2.length) {
		 *
		 * if (x1[i] < x2[j]) { result[i + j] = x1[i++]; } else { result[i + j]
		 * = x2[j++]; } }
		 *
		 * while (i < x1.length) { result[i + j] = x1[i++]; }
		 *
		 * while (j < x2.length) { result[i + j] = x2[j++]; }
		 */

		return result;
	}

	public static void main(String[] arg) {

		// int[] x1 = { 0, 1, 2, 3, 4, 14 };
		// int[] x2 = { 1, 2, 3, 4, 5, 10, 12 };
		int[] x1 = { 1, 2, 3, 4, 5 };
		int[] x2 = { 10, 11, 12, 13, 14, 16 };
		int[] fusion = tableFusion(x1, x2);

		for (int i = 0; i < fusion.length; ++i) {
			System.out.print("" + fusion[i] + " ");
		}
	}
}

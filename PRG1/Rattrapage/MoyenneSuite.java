package rattrapage_java;

public class MoyenneSuite {

	public static int[] getValues(String[] entries) {

		int[] result = new int[entries.length];

		for (int i = 0; i < result.length; ++i) {

			result[i] = Integer.parseInt(entries[i]);
		}

		return result;
	}

	public static double moyenneSuite(int[] values) {

		double result = 0;

		for (int i = 0; i < values.length; ++i) {
			result += values[i];
		}

		return result / values.length;
	}

	public static void main(String[] arg) {
		System.out.println("La Moyenne est " + moyenneSuite(getValues(arg)));
	}

}

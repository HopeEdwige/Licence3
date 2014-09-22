package rattrapage_java;

public class SousSuite {

	public static int sousSuite(int[] suite) {

		int result = 1;

		int compteur = 1;

		for (int i = 1; i < suite.length; ++i) {

			if (suite[i] == suite[i - 1]) {
				++compteur;
			} else {
				compteur = 1;
			}

			if (compteur > result) {
				result = compteur;
			}

		}

		return result;
	}

	public static void main(String[] arg) {

		System.out.println("Longueur de la plus longue sous-suite constante : "
				+ sousSuite(MoyenneSuite.getValues(arg)));
	}

}

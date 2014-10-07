package rattrapage_java;

public class GetChiffre {

	public static int getChiffre(int x, int i) {
		// pre-requis : i > 0
		// rend le i ème chiffre

		int result = x;
		int j = 0;

		while (j < i) {

			result /= 10;
			++j;
		}

		return result % 10;

	}

	public static void main(String[] args) {
		System.out.println("le nombre est : "
				+ getChiffre(Integer.parseInt(args[0]),
						Integer.parseInt(args[1])));

	}
}

package rattrapage_java;

public class PolySecDeg {

	public static double delta(double a, double b, double c) {
		// pre-requis : a != 0
		// resultat : donne le determinant du polynome
		return b * b - 4 * a * c;
	}

	public static void main(String[] args) {

		double x1 = 0;
		double x2 = 0;

		double a = Double.valueOf(args[0]);
		double b = Double.valueOf(args[1]);
		double c = Double.valueOf(args[2]);

		if (a == 0 && b == 0) {

			System.out.print("Pas de solution");

		} else if (a == 0) {

			x1 = c / b;
			System.out.print("La solution est : " + x1);

		} else {

			if (delta(a, b, c) < 0) {

				System.out.print("Pas de solution");

			} else {

				x1 = (-b - Math.sqrt(delta(a, b, c))) / (2 * a);
				x2 = (-b + Math.sqrt(delta(a, b, c))) / (2 * a);

				System.out.print("Le(s) solution(s) sont : " + x1 + " et " + x2);

			}
		}
	}
}

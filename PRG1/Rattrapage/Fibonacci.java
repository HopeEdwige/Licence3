package rattrapage_java;

public class Fibonacci {

	public static int recFibo(int n) {

		int result = 1;

		if (n > 1) {

			result = recFibo(n - 1) + recFibo(n - 2);
		}

		return result;
	}

	public static int itFibo(int n) {

		int result = 1;

		if (n > 1) {

			int fib0 = 1;
			int fib1 = 1;

			for (int i = 0; i < n - 1; ++i) {

				result = fib0 + fib1;
				fib0 = fib1;
				fib1 = result;
			}
		}

		return result;
	}

	public static void main(String[] argv) {

		int rank = Integer.parseInt(argv[0]);

		System.out.println("Recursif : Le terme " + rank
				+ " de la suite de Fibonacci est " + recFibo(rank));
		System.out.println("Iteratif : Le terme " + rank
				+ " de la suite de Fibonacci est " + itFibo(rank));
	}
}

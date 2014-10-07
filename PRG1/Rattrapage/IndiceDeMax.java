package rattrapage_java;

public class IndiceDeMax {

	public static int indiceDeMax(int[] values) {

		int result = 0;

		for (int i = 1; i < values.length; ++i) {

			if (values[i] > values[result]) {

				result = i;
			}
		}
		return result;
	}

	public static void main(String[] arg) {

		int[] values = MoyenneSuite.getValues(arg);
		System.out.println("L'indice du max est " + indiceDeMax(values));
	}

}

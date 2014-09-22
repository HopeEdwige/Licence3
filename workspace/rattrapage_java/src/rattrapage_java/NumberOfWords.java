package rattrapage_java;

public class NumberOfWords {

	public static int numberOfWords(String txt) {

		int result = 1;
		int i = 0;

		while (txt.charAt(i) != '.') {

			if (txt.charAt(i) == ' ' && txt.charAt(i + 1) != ' ') {
				
				++result;
			}
			++i;
		}

		return result;
	}

	public static void main(String[] arg) {

		String txt = "";
		for (int i = 0; i < arg.length; ++i) {
			txt += arg[i] + " ";
		}
		System.out.println("Le nombre de mots est : " + numberOfWords(txt));
	}

}

/**
 * A class to get the "Fourmis" algorithm
 */
public class Fourmis {

	/**
	 * Constructor of this class
	 */
	public Fourmis() {}


	/**
	 * Method to get the next term
	 * @param String ui The actual term
	 * @return String The next term
	 */
	public static String next(String ui) {
		String ret = "";

		System.out.println("Valeur d'entr�e: " + ui);

		//Travel the String
		for (int i = 0; i < ui.length(); i++) {
			//The counter
			int count = 1;

			//Get each char
			char c = ui.charAt(i);

			//While the other letters have the same value
			if (i < ui.length() - 1) {
				while (ui.charAt(i+1) == c) {
					count++;
					i++;
				}
			}

			//Create the string
			ret = ret + count + c;
		}

		System.out.println("Valeur de sortie: " + ret);
		return ret;
	}

}

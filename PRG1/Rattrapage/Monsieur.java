package rattrapage_java;

public class Monsieur {


	public static void main(String[] args){

		int annee =  Integer.valueOf(args[0]);
		String nom = (args[1]);
		String prenom = (args[2]);
		int naissance = Integer.valueOf(args[3]);

		int age = (annee - naissance);




		System.out.println("Monsieur "+ prenom+ " "+ nom + ", vous avez " + age +" ans");
	}
}

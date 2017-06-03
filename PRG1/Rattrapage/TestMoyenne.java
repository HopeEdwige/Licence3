
package rattrapage_java;

public class TestMoyenne {

	static double moyenne(int nb1, int nb2, int nb3){
		// calcule de la valeur moyenne de 3 nombres
		double result = ((double)(nb1 + nb2 + nb3)) / 3 ;
		return result;
	}

	public static void main(String[] args){

		// recuperation des arguments
		int nb1 = Integer.parseInt(args[0]);
		int nb2 = Integer.parseInt(args[1]);
		int nb3 = Integer.parseInt(args[2]);
		// affichage des 3 valeurs moyennes
		System.out.println("Moyenne = "+moyenne(nb1, nb2, nb3));
		

	}


}

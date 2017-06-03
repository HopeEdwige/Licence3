package rattrapage_java;

public class Parallelepipede {

	static int surface(int longueur, int largeur, int hauteur){

		int result = 2 * (longueur * largeur + longueur * hauteur + largeur * hauteur);
		return result;
	}

	static int volume(int longueur, int largeur, int hauteur){

		int result = longueur * largeur * hauteur;
		return result;
	}

	public static void main(String[] args){

		System.out.println("surface(12x3x5) = "+surface(12, 3, 5));
		System.out.println("volume(12x3x5) = "+volume(12, 3, 5));

		System.out.println("surface(4x34x10) = "+surface(4, 34, 10));
		System.out.println("volume(4x34x10) = "+volume(4, 34, 10));
	}
}

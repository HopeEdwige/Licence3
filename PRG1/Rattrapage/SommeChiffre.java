package rattrapage_java;

public class SommeChiffre {
	
	static int sommeChiffre(int number){
		
		int result = 0;
		int i = 0;
		
		while(GetChiffre.getChiffre(number, i) != 0){
			
			result += GetChiffre.getChiffre(number, i++);
		}
		return result;
	}
	
	public static void main(String[] argv){
		
		int number = Integer.parseInt(argv[0]);
		System.out.println( "La somme des chiffres de " + number + " est " + sommeChiffre(number) );
	}

}

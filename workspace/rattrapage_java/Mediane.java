package rattrapage_java;

public class Mediane {
	
	static int med(int nb1, int nb2, int nb3){
		
		int result = 0;
		
		if( (nb1 <= nb2 && nb2 <= nb3) || (nb3 <= nb2 && nb2 <= nb1) ) {result = nb2;}
		
		else if( (nb1 <= nb3 && nb3 <= nb2) || (nb2 <= nb3 && nb3 <= nb1) ){result = nb3;}
		
		else {result = nb1;}
		
		return result;
	}
	public static void main(String[] args){
		
		System.out.println("La mediane est : " + med(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
	}
}

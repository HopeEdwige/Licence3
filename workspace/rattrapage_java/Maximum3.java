package rattrapage_java;

public class Maximum3 {
	
	static int maxi(int nb1, int nb2, int nb3){
		int result = 0;
		if(nb1 <= nb2 && nb3 <= nb2) {result = nb2;}
		else if(nb2 <= nb3 && nb1 <= nb3){result = nb3;}
		else {result = nb1;}
		return result;
	}
	public static void main(String[] args){
		
		System.out.println("Le maximum est : " + maxi(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
	}
}

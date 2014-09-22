package rattrapage_java;	

public class Maximum{

		static int maxi(int nb1, int nb2){
			
			int result = 0;
 
			if(nb1 <= nb2) { result = nb2;}
			else {result = nb1;}

			return result;
			
		}

		static int maxi3(int nb1, int nb2, int nb3){

			return maxi(maxi(nb1, nb2), nb3);
		}



		public static void main(String[] args){
		
			System.out.println("Le nombre max est "+ maxi3(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2])));

		}




	}

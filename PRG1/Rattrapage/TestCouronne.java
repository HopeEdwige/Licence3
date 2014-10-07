package rattrapage_java;

public class TestCouronne {
	
	static double surfaceCercle(double r){
			
		return Math.PI * r * r;
	}
	
	static double surfaceCouronne(double r1, double r2){
		
		return Math.abs( surfaceCercle(r1) - surfaceCercle(r2) ); 
	}

	public static void main(String[] args){
		
		System.out.println("surfaceCouronne = "+surfaceCouronne(Double.valueOf(args[0]), Double.valueOf(args[1])));
	}
}

	

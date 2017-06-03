import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Test;

public class TestInsertionPair {

	@Test
	public void testInsertionPair() {
		try {
			//Read the inputs from the keyboard
			System.out.println("Veuillez entrer une suite d'entiers termin� par -1:");
			Scanner s1 = new Scanner(System.in);
			InsertionPair myList = new InsertionPair();
			myList.createArray(s1);
			System.out.println("Ma liste apr�s les entr�es claviers: " + myList);
			
			//Read from a file
			System.out.println("Veuillez entrer le nom d'un fichier � lire:");
			String nom = Lecture.lireString();
			File f1 = new File(nom);
			Scanner s2 = new Scanner(f1);
			myList.createArray(s2);
			s1.close();
			s2.close();
			System.out.println("Ma liste apr�s lecture du fichier: " + myList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

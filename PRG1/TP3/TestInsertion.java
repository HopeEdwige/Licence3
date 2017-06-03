import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Test;

public class TestInsertion {

	private static boolean equalIntegerArrays(int[] tab1, int[] tab2) {
		int l1 = tab1.length;
		int l2 = tab2.length;
		if (l1 != l2) {
			return false;
		}
		for (int i = 0; i < l1; ++i) {
			if (tab1[i] != tab2[i]) {
				return false;
			}
		}
		return true;
	}

	private static boolean equalPairArrays(Pair[] tab1, Pair[] tab2) {
		int l1 = tab1.length;
		int l2 = tab2.length;
		if (l1 != l2) {
			return false;
		}
		for (int i = 0; i < l1; ++i) {
			if (tab1[i] == null && tab2[i] != null) {
				return false;
			}
			if (tab2[i] == null && tab1[i] != null) {
				return false;
			}
			if (tab1[i] != null && tab2[i] != null && !tab1[i].equals(tab2[i])) {
				return false;
			}
		}
		return true;
	}

	@Test
	public void testInsertionInteger() {
		try {
			int[] tab1 = { 1, 2, 3, 11, 22, 33, 0, 0, 0, 0 };
			int[] tab2 = { 1, 2, 3, 5, 11, 22, 33, 0, 0, 0 };
			int[] tab3 = { 1, 2, 3, 5, 6, 11, 22, 33, 0, 0 };
			int[] tab4 = { 4, 5, 6, 11, 12, 45, 54, 87, 89, 545 };
			File f1 = new File("val1.txt");
			Scanner s1 = new Scanner(f1);
			InsertionInteger tab2Prog = new InsertionInteger();
			tab2Prog.createArray(s1);
			assertTrue("courte liste, test 1",
					equalIntegerArrays(tab2Prog.toArray(), tab1));
			tab2Prog.insert(5);
			assertTrue("courte liste, test 2, un ajout de plus",
					equalIntegerArrays(tab2Prog.toArray(), tab2));
			tab2Prog.insert(5);
			assertTrue("courte liste, test 3, m�me ajout une 2e fois",
					equalIntegerArrays(tab2Prog.toArray(), tab2));
			tab2Prog.insert(6);
			assertTrue("courte liste, test 4, un ajout de plus",
					equalIntegerArrays(tab2Prog.toArray(), tab3));
			File f2 = new File("val2.txt");
			Scanner s2 = new Scanner(f2);
			InsertionInteger tab4Prog = new InsertionInteger();
			tab4Prog.createArray(s2);
			assertTrue("une longue liste d'entiers avec doublons",
					equalIntegerArrays(tab4Prog.toArray(), tab4));
			s1.close();
			s2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertionPair() {
		try {
			Pair pair0 = new Pair(5, 5);
			Pair pair1 = new Pair(6, 89);
			Pair pair2 = new Pair(11, 12);
			Pair pair3 = new Pair(12, 12);
			Pair pair4 = new Pair(12, 45);
			Pair pair5 = new Pair(45, 4);
			Pair pair6 = new Pair(45, 45);
			Pair pair7 = new Pair(54, 7);
			Pair pair8 = new Pair(87, 4);
			Pair pair9 = new Pair(545, 87);
			Pair[] tab1 = { pair0, pair1, pair2, pair3, pair4, pair5, pair6,
					pair7, pair8, pair9 };
			File f1 = new File("val2.txt");
			Scanner s1 = new Scanner(f1);
			InsertionPair tab1Pair = new InsertionPair();
			tab1Pair.createArray(s1);
			assertTrue("une longue liste de doublets",
					equalPairArrays(tab1Pair.toArray(), tab1));
			Pair pair10 = new Pair(1, 2);
			Pair pair11 = new Pair(2, 3);
			Pair pair12 = new Pair(11, 22);
			Pair pair13 = new Pair(33, 1);
			Pair[] tab2 = { pair10, pair11, pair12, pair13, null, null, null,
					null, null, null };
			File f2 = new File("val1.txt");
			Scanner s2 = new Scanner(f2);
			InsertionPair tab2Pair = new InsertionPair();
			tab2Pair.createArray(s2);
			assertTrue(
					"une courte liste de doublets avec doublons et -1 en position impaire",
					equalPairArrays(tab2Pair.toArray(), tab2));
			File f3 = new File("val3.txt");
			// juste une nouvelle avant-derni�re valeur
			Scanner s3 = new Scanner(f3);
			InsertionPair tab3Pair = new InsertionPair();
			tab3Pair.createArray(s3);
			assertTrue(
					"une courte liste de doublets avec doublons et -1 en position paire",
					equalPairArrays(tab3Pair.toArray(), tab2));

			s1.close();
			s2.close();
			s3.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

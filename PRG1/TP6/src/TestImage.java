import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestImage {

	public static boolean compareImages(Image t1, Image t2) {
		Iterator<NodeState> it1 = t1.iterator();
		Iterator<NodeState> it2 = t2.iterator();
		return compareImagesAux(it1, it2);
	}

	private static boolean compareImagesAux(Iterator<NodeState> it1,
			Iterator<NodeState> it2) {
		if (it1.isEmpty()) {
			return it2.isEmpty();
		}
		if (it2.isEmpty()) {
			return false;
		}
		NodeState n1 = it1.getValue();
		NodeState n2 = it2.getValue();
		if (n1.e != n2.e) {
			return false;
		}
		if (n1.e == 1 || n1.e == 0) {
			return true;
		}
		it1.goLeft();
		it2.goLeft();
		boolean bool = compareImagesAux(it1, it2);
		it1.goUp();
		it2.goUp();
		if (bool) {
			it1.goRight();
			it2.goRight();
			bool = bool && compareImagesAux(it1, it2);
			it1.goUp();
			it2.goUp();
		}
		return bool;
	}

	public static int testCorrectness(Image t) {
		Iterator<NodeState> it = t.iterator();
		return testCorrectnessAux(it);
	}

	private static int testCorrectnessAux(Iterator<NodeState> it) {
		// 0 est la réponse correcte (pas d'erreurs)
		// les autres erreurs sont numérotés
		if (it.isEmpty()) {
			return 0;
		}
		NodeState n = it.getValue();
		if (n.e == 2) {
			int tmp = 0;
			it.goLeft();
			if (it.isEmpty()) {
				it.goUp();
				return 1; // 2 a fils gauche vide
			}
			NodeState nLeft = it.getValue();
			if (nLeft.e == 2) {
				tmp = testCorrectnessAux(it);
			}
			it.goUp();
			if (nLeft.e == 2 && tmp > 0) {
				return tmp;
			}
			it.goRight();
			if (it.isEmpty()) {
				it.goUp();
				return 2; // 2 a fils droit vide
			}
			NodeState nRight = it.getValue();
			if (nRight.e == 2) {
				tmp = tmp + testCorrectnessAux(it);
			}
			it.goUp();
			if (nLeft.e != 2 && nLeft.e == nRight.e) {
				return 3; // 2 a deux fils identiques
			} 
			else if (nLeft.e == 2) {
				return tmp;
			} 
			else {
				return 0;
			}
		}
		else {
			it.goLeft();
			if (!it.isEmpty()) {
				it.goUp();
				return 4; // 0 ou 1 a fils gauche non vide
			}
			it.goUp();
			it.goRight();
			if (!it.isEmpty()) {
				it.goUp();
				return 5; // 0 ou 1 a fils droit non vide
			}
			it.goUp();
			return 0;
		}
	}

	public static Image readFile(String file) {
		MockDessin mock = new MockDessin();
		Image im = new Image();
		try {
			Image.xReadImageFromFile(new FileInputStream(file), 1, mock);
			im.constructTree(1, mock);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return im;
	}

	@Test
	public void testPixel1() {
		Image image1 = readFile("a1.arb");
		boolean result = image1.isPixelOn(0, 128);
		assertTrue("pixel a1 0 128", result);

		result = image1.isPixelOn(128, 0);
		assertTrue("pixel a1 128 0", !result);

		result = image1.isPixelOn(192, 128);
		assertTrue("pixel a1 192 128", !result);

		result = image1.isPixelOn(128, 192);
		assertTrue("pixel a1 128 192", !result);

		result = image1.isPixelOn(255, 192);
		assertTrue("pixel a1 255 192", !result);

		result = image1.isPixelOn(192, 255);
		assertTrue("pixel a1 192 255", !result);

	}

	@Test
	public void testPixel2() {
		Image image1 = readFile("a2.arb");
		boolean result = image1.isPixelOn(0, 128);

		assertTrue("pixel a2 0 128", !result);

		result = image1.isPixelOn(128, 0);
		assertTrue("pixel a2 128 0", !result);

		result = image1.isPixelOn(192, 128);
		assertTrue("pixel a2 192 128", !result);

		result = image1.isPixelOn(128, 192);
		assertTrue("pixel a2 128 192", result);

		result = image1.isPixelOn(255, 192);
		assertTrue("pixel a2 255 192", !result);

		result = image1.isPixelOn(192, 255);
		assertTrue("pixel a2 192 255", !result);

		result = image1.isPixelOn(32, 128);
		assertTrue("pixel a2 32 128", result);
	}

	@Test
	public void testPixel3() {
		Image image = readFile("a2.arb");
		boolean bool1 = image.isPixelOn(213, 95);
		boolean bool2 = image.isPixelOn(97, 97);
		boolean bool3 = image.isPixelOn(5, 249);
		boolean bool4 = image.isPixelOn(249, 5);
		boolean bool5 = image.isPixelOn(5, 5);
		assertTrue("isPixelOn test 3", !bool1 && bool2 && !bool3 && !bool4 && !bool5);
	}

	@Test
	public void testUnion1() {
		Image image1 = readFile("a1.arb");
		Image image2 = readFile("a2.arb");
		Image image3 = new Image();
		Image image4 = readFile("test-u12.arb");
		Dessin window = new MockDessin();
		image3.union(image1, image2, window);
		assertTrue("union a1 u a2: arbre conforme ?",
				testCorrectness(image3) == 0);
		assertTrue("union a1 et a2", compareImages(image3, image4));
	}

	@Test
	public void testUnion2() {
		Image image1 = readFile("a1.arb");
		Image image2 = readFile("a5.arb");
		Image image3 = new Image();
		Image image4 = readFile("test-u15.arb");
		Dessin window = new MockDessin();
		image3.union(image1, image2, window);
		assertTrue("union a1 u a5: arbre conforme ?",
				testCorrectness(image3) == 0);
		assertTrue("union a1 et a5", compareImages(image3, image4));
	}

	@Test
	public void testIntersection1() {
		Image image1 = readFile("a1.arb");
		Image image2 = readFile("a2.arb");
		Image image3 = new Image();
		Image image4 = readFile("test-i12.arb");
		Dessin window = new MockDessin();
		image3.intersection(image1, image2, window);
		assertTrue("intersection a1 i a2: arbre conforme ?",
				testCorrectness(image3) == 0);
		assertTrue("intersection a1 et a2", compareImages(image3, image4));
	}

	@Test
	public void testIntersection2() {
		Image image1 = readFile("a2.arb");
		Image image2 = readFile("a6.arb");
		Image image3 = new Image();
		Image image4 = readFile("test-i26.arb");
		Dessin window = new MockDessin();
		image3.intersection(image1, image2, window);
		assertTrue("intersection a2 i a6 : arbre conforme ?",
				testCorrectness(image3) == 0);
		assertTrue("intersection a2 et a6", compareImages(image3, image4));
	}

	@Test
	public void testAffect() {
		Image image1 = readFile("a1.arb");
		Image image2 = new Image();
		Dessin window = new MockDessin();
		image2.affect(image1, window);
		assertTrue("affect a1", compareImages(image1, image2));
	}

	@Test
	public void testRotation() {
		Image image1 = readFile("a1.arb");
		Image image2 = new Image();
		Image image3 = readFile("test-r1.arb");
		Dessin window = new MockDessin();
		image2.rotate180(image1, window);
		assertTrue("rotation a1", compareImages(image2, image3));
	}

	@Test
	public void testVideoInverse() {
		Image image1 = readFile("a2.arb");
		Image image2 = readFile("test-i2.arb");
		Dessin window = new MockDessin();
		image1.videoInverse(window);
		assertTrue("videoInverse a2", compareImages(image1, image2));
	}

	@Test
	public void testDiagonal1() {
		Image image = readFile("d2.arb");
		boolean bool = image.testDiagonal();
		assertTrue("diagonal d2", bool);
	}

	@Test
	public void testDiagonal2() {
		Image image = readFile("d3.arb");
		boolean bool = image.testDiagonal();
		assertTrue("diagonal d3", !bool);
	}

	@Test
	public void testDiagonal3() {
		Image image = readFile("a5.arb");
		boolean bool = image.testDiagonal();
		assertTrue("diagonal a5", !bool);
	}

	@Test
	public void testDiagonal4() {
		Image image = readFile("d4.arb");
		boolean bool = image.testDiagonal();
		assertTrue("diagonal d4", bool);
	}

	@Test
	public void testInclude1() {
		Image image1 = readFile("a2.arb");
		Image image2 = readFile("a6.arb");
		Dessin window = new MockDessin();
		boolean bool = image1.isIncludedIn(image2, window);
		assertTrue("inclusion : a2 in a6", !bool);
	}

	@Test
	public void testInclude2() {
		Image image1 = readFile("a8.arb");
		Image image2 = readFile("a6.arb");
		Dessin window = new MockDessin();
		boolean bool = image1.isIncludedIn(image2, window);
		assertTrue("inclusion : a8 in a6", bool);
	}

	@Test
	public void testInclude3() {
		Image image1 = readFile("a8.arb");
		Image image2 = readFile("a6.arb");
		Dessin window = new MockDessin();
		boolean bool = image2.isIncludedIn(image1, window);
		assertTrue("inclusion : a6 in a8", !bool);
	}
}
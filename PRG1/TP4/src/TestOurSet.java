import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestOurSet {

	public static boolean compareOurSets(OurSet l1, OurSet l2) {
		Iterator<SubSet> it1 = l1.iterator();
		Iterator<SubSet> it2 = l2.iterator();
		while (!it1.isOnFlag()) {
			SubSet s1 = it1.getValue();
			SubSet s2 = it2.getValue();
			if (!compareSubSets(s1, s2)) {
				return false;
			}
			it1.goForward();
			it2.goForward();
		}
		return it2.isOnFlag();
	}

	public static boolean compareSubSets(SubSet s1, SubSet s2) {
		return s1.rank == s2.rank && compareSmallSets(s1.set, s2.set);
	}

	public static boolean compareSmallSets(SmallSet s1, SmallSet s2) {
		if (s1.size() == 0) {
			return false;
		}
		return s1.toString().equals(s2.toString());
	}

	public static boolean testSparsity(OurSet l) {
		Iterator<SubSet> it = l.iterator();
		while (!it.isOnFlag()) {
			SubSet s = it.getValue();
			if (s.set.size() == 0) {
				return false;
			}
			it.goForward();
		}
		return true;
	}

	public static OurSet readFileToOurSet(String file)
			throws FileNotFoundException {
		OurSet set = new OurSet();
		InputStream is = new FileInputStream(file);
		set.addAux(is);
		return set;
	}

	@Test
	public void testSetCreation() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-desordre.ens");
		OurSet ourList2 = readFileToOurSet("f0.ens");
		assertTrue("set creation désordre", compareOurSets(ourList1, ourList2));
	}

	@Test
	public void testContainment() throws FileNotFoundException {
		OurSet ourList = readFileToOurSet("f0.ens");
		boolean bool1 = ourList.containsAux(128);
		boolean bool2 = ourList.containsAux(129);
		boolean bool3 = ourList.containsAux(32767);
		boolean bool4 = ourList.containsAux(22222);
		assertTrue("apparteance", bool1 && !bool2 && bool3 && !bool4);
	}
	
	@Test
	public void testSetAddition() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		InputStream is = new FileInputStream("f1.ens");
		OurSet ourList2 = readFileToOurSet("test-u01.ens");
		ourList1.addAux(is);
		assertTrue("set addition f0 f1", compareOurSets(ourList1, ourList2));
	}

	@Test
	public void testRemoval() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		InputStream is = new FileInputStream("f5.ens");
		OurSet ourList2 = readFileToOurSet("test-d05.ens");
		ourList1.removeAux(is);
		assertTrue("deletion sparsity", testSparsity(ourList1));
		assertTrue("deletion", compareOurSets(ourList1, ourList2));
	}

	@Test
	public void testDifference1() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f3.ens");
		OurSet ourList3 = readFileToOurSet("test-d03.ens");
		ourList1.difference(ourList2);
		assertTrue("difference sparsity 1", testSparsity(ourList1));
		assertTrue("difference 1", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testDifference2() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f3.ens");
		OurSet ourList2 = readFileToOurSet("f0.ens");
		OurSet ourList3 = readFileToOurSet("test-d30.ens");
		ourList1.difference(ourList2);
		assertTrue("difference sparsity 2", testSparsity(ourList1));
		assertTrue("difference 2", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testDifference3() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f1.ens");
		OurSet ourList3 = readFileToOurSet("test-d01.ens");
		ourList1.difference(ourList2);
		assertTrue("difference sparsity 3", testSparsity(ourList1));
		assertTrue("difference 3", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testDifference4() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100-300.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100.ens");
		OurSet ourList3 = readFileToOurSet("test-f-300.ens");
		ourList1.difference(ourList2);
		assertTrue("difference sparsity 4", testSparsity(ourList1));
		assertTrue("difference 100+300 i 100",
				compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testDifference5() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100-301.ens");
		OurSet ourList3 = readFileToOurSet("f4.ens");
		ourList1.difference(ourList2);
		assertTrue("difference sparsity 5", testSparsity(ourList1));
		assertTrue("difference 100 i 100+301",
				compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testIntersection1() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f3.ens");
		OurSet ourList3 = readFileToOurSet("test-i03.ens");
		ourList1.intersection(ourList2);
		assertTrue("intersection sparsity 1", testSparsity(ourList1));
		assertTrue("intersection 1", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testIntersection2() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f3.ens");
		OurSet ourList2 = readFileToOurSet("f0.ens");
		OurSet ourList3 = readFileToOurSet("test-i03.ens");
		ourList1.intersection(ourList2);
		assertTrue("intersection sparsity 2", testSparsity(ourList1));
		assertTrue("intersection 2", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testIntersection3() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100-300.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100-301.ens");
		OurSet ourList3 = readFileToOurSet("test-f-100.ens");
		ourList1.intersection(ourList2);
		assertTrue("intersection sparsity 3", testSparsity(ourList1));
		assertTrue("intersection 100+300 et 100+301",
				compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testIntersection4() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100-300.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100.ens");
		ourList1.intersection(ourList2);
		assertTrue("intersection sparsity 4", testSparsity(ourList1));
		assertTrue("intersection 100+300 et 100",
				compareOurSets(ourList1, ourList2));
	}

	@Test
	public void testIntersection5() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100-301.ens");
		OurSet ourList3 = readFileToOurSet("test-f-100.ens");
		ourList1.intersection(ourList2);
		assertTrue("intersection sparsity 5", testSparsity(ourList1));
		assertTrue("intersection 100 et 100+301",
				compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testUnion1() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f3.ens");
		OurSet ourList3 = readFileToOurSet("test-u03.ens");
		ourList1.union(ourList2);
		assertTrue("union 1", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testUnion2() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f3.ens");
		OurSet ourList2 = readFileToOurSet("f0.ens");
		OurSet ourList3 = readFileToOurSet("test-u03.ens");
		ourList1.union(ourList2);
		assertTrue("union 2", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testUnion3() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100-300.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100.ens");
		OurSet ourList3 = readFileToOurSet("test-f-100-300.ens");
		ourList1.union(ourList2);
		assertTrue("union 100+300 i 100", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testUnion4() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100-301.ens");
		ourList1.union(ourList2);
		assertTrue("union 100 i 100+301", compareOurSets(ourList1, ourList2));
	}

	@Test
	public void testSymmetricDifference1() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f1.ens");
		OurSet ourList2 = readFileToOurSet("f0.ens");
		OurSet ourList3 = readFileToOurSet("test-s01.ens");
		ourList1.symmetricDifference(ourList2);
		assertTrue("symmetric difference sparsity 1", testSparsity(ourList1));
		assertTrue("symmetric difference 1", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testSymmetricDifference2() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f1.ens");
		OurSet ourList3 = readFileToOurSet("test-s01.ens");
		ourList1.symmetricDifference(ourList2);
		assertTrue("symmetric difference sparsity 2", testSparsity(ourList1));
		assertTrue("symmetric difference 2", compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testSymmetricDifference3() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f0.ens");
		OurSet ourList3 = readFileToOurSet("f4.ens");
		ourList1.symmetricDifference(ourList2);
		ourList2.symmetricDifference(ourList2);
		assertTrue("symmetric difference f0 et f0 : sparsity 1",
				testSparsity(ourList1));
		assertTrue("symmetric difference f0 et f0 : sparsity 2",
				testSparsity(ourList2));
		assertTrue("symmetric difference f0 et f0 : résultat 1",
				compareOurSets(ourList1, ourList3));
		assertTrue("symmetric difference f0 et f0 : résultat 2",
				compareOurSets(ourList2, ourList3));
	}

	@Test
	public void testSymmetricDifference4() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100-300.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100.ens");
		OurSet ourList3 = readFileToOurSet("test-f-300.ens");
		ourList1.symmetricDifference(ourList2);
		assertTrue("symmetric difference sparsity 4", testSparsity(ourList1));
		assertTrue("symmetric difference 100+300 et 100",
				compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testSymmetricDifference5() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100-301.ens");
		OurSet ourList3 = readFileToOurSet("test-f-301.ens");
		ourList1.symmetricDifference(ourList2);
		assertTrue("symmetric difference sparsity 5", testSparsity(ourList1));
		assertTrue("symmetric difference 100 et 100+301",
				compareOurSets(ourList1, ourList3));
	}

	@Test
	public void testEquality1() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f0.ens");
		assertTrue("equality 1", ourList1.equals(ourList2));
	}

	@Test
	public void testEquality2() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f0.ens");
		ourList2.addAux(new FileInputStream("f1.ens"));
		assertTrue("equality 2", !ourList1.equals(ourList2));
	}

	@Test
	public void testEquality3() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f0.ens");
		ourList2.addAux(new FileInputStream("f1.ens"));
		assertTrue("equality 3", !ourList2.equals(ourList1));
	}

	@Test
	public void testEquality4() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100-300.ens");
		assertTrue("equality 4", !ourList2.equals(ourList1));
	}

	@Test
	public void testEquality5() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("test-f-100.ens");
		OurSet ourList2 = readFileToOurSet("test-f-100-300.ens");
		assertTrue("equality 5", !ourList1.equals(ourList2));
	}

	@Test
	public void testInclusion1() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f3.ens");
		ourList1.union(ourList2);
		assertTrue("inclusion 1", ourList2.includedAux(ourList1));
	}

	@Test
	public void testInclusion2() throws FileNotFoundException {
		OurSet ourList1 = readFileToOurSet("f0.ens");
		OurSet ourList2 = readFileToOurSet("f3.ens");
		assertTrue("inclusion 2", !ourList2.includedAux(ourList1));
	}

}
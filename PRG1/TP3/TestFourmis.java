import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestFourmis {

	@Test
	public void testNextTerm() {
		String u8 = "31131211131221";
		String u9 = "13211311123113112211";
		String u9Bis = Fourmis.next(u8);
		assertTrue(u9.equals(u9Bis));
	}
}

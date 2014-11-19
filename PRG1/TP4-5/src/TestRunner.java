

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.util.List;

public class TestRunner {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestOurSet.class);
		List<Failure> listOfFailures = result.getFailures();
		for (Failure failure : listOfFailures) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}

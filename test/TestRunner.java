import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.assertEquals;

public class TestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(CompressorTest.class, 
											DecompressorTest.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		assertEquals(result.wasSuccessful(), true);
	}
}  	

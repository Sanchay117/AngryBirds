
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result= JUnitCore.runClasses(PigTest.class);
        System.out.println("Running Pig Tests");
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("All tests successful: " + result.wasSuccessful());
    }
}

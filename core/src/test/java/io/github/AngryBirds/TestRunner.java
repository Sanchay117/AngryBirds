package io.github.AngryBirds;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PigTest.class);

        System.out.println("Running Pig Tests...");

        for (Failure failure : result.getFailures()) {
            System.out.println("Failure Details:");
            System.out.println(failure.toString());
            // Print the exception's full stack trace
            failure.getException().printStackTrace();
        }

        System.out.println("Test Run Successful: " + result.wasSuccessful());

        System.out.println("Running Material Tests...");
        Result result2 = JUnitCore.runClasses(MaterialTest.class);

        for (Failure failure : result2.getFailures()) {
            System.out.println("Failure Details:");
            System.out.println(failure.toString());
            // Print the exception's full stack trace
            failure.getException().printStackTrace();
        }

        System.out.println("Test Run Successful: " + result2.wasSuccessful());

        System.out.println("Tests Run: " + (result.getRunCount() + result2.getRunCount()));
        System.out.println("Tests Failed: " + (result.getFailureCount() + result2.getFailureCount()));
    }
}

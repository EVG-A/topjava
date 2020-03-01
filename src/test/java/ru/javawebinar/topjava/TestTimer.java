package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

public class TestTimer implements TestRule {

    private static TestTimer testTimer;
    private long startTime;
    private String methodName;
    private List<String> testTimerMessages = new ArrayList<>();

    private TestTimer() {

    }

    public static TestTimer getInstance() {
        if (testTimer == null) {
            testTimer = new TestTimer();
        }
        return testTimer;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        if (description.isTest()) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    startTime = System.currentTimeMillis();
                    methodName = description.getMethodName();
                    try {
                        base.evaluate();
                    } finally {
                        after();
                    }
                }
            };
        } else if (description.isSuite()) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    try {
                        base.evaluate();
                    } finally {
                        afterClass();
                    }
                }
            };
        } else {
            return base;
        }
    }

    void after() {
        long totalTime = System.currentTimeMillis() - startTime;
        String message = methodName + " test execution time " + totalTime + " ms";
        testTimerMessages.add(message);
        System.out.println(message);
    }

    void afterClass() {
        System.out.println("Final report:");
        testTimerMessages.forEach(System.out::println);
    }
}

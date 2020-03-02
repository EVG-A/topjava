package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;

import java.math.BigDecimal;

import static org.slf4j.LoggerFactory.getLogger;

public class TestTimer implements TestRule {

    private static final Logger log = getLogger(TestTimer.class);

    @Override
    public Statement apply(Statement base, Description description) {
        if (description.isTest()) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    BigDecimal startTime = new BigDecimal(before());
                    String methodName = description.getMethodName();
                    try {
                        base.evaluate();
                    } finally {
                        after(startTime, methodName);
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

    long before() {
        return System.nanoTime();
    }

    void after(BigDecimal startTime, String methodName) {
        BigDecimal currentTime = new BigDecimal(System.nanoTime());
        BigDecimal totalTime = currentTime.subtract(startTime).divide(new BigDecimal(1000000));

        String message = String.format("%-25s%10d%4s", methodName, totalTime.intValue(), "ms");
        log.info(message);
        MealTestData.TEST_TIMER_MESSAGES.add(message);
    }

    void beforeClass() {
    }

    void afterClass() {
        StringBuilder text = new StringBuilder("Final report:");
        text.append(System.lineSeparator());
        String head = String.format("%-25s%10s%4s", "Method Name", "Total Time", "(ms)");
        text.append(head);
        text.append(System.lineSeparator());
        text.append("---------------------------------------");
        text.append(System.lineSeparator());
        MealTestData.TEST_TIMER_MESSAGES.forEach(message -> text.append(message).append(System.lineSeparator()));
        log.info(text.toString());
    }
}

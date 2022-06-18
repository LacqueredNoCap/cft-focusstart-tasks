package lacquered.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.function.IntToDoubleFunction;

/**
 * Ряд (1000/1001)^n (n=1,2,...) сходится к 1000
 * https://www.wolframalpha.com/input?i=series+%281000%2F1001%29%5En++from+1+to+inf
 */

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            int firstIndex = 1;
            int lastIndex = Validator.validateInput(scanInput());
            int numberOfThreads = Runtime.getRuntime().availableProcessors();
            IntToDoubleFunction series = n -> Math.pow((double)1000/1001, n);

            Validator.validateIndices(firstIndex, lastIndex);
            Validator.validateNumberOfThreads(numberOfThreads);

            MultithreadingSeriesCalculator calculator = new MultithreadingSeriesCalculator(
                    series,
                    firstIndex,
                    lastIndex,
                    numberOfThreads
            );

            double result = calculator.calculate();
            log.info("" + result);

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }

    private static String scanInput() {
        Scanner scan = new Scanner(System.in);
        return scan.next();
    }
}

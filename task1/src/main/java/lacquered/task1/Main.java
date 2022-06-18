package lacquered.task1;

import lacquered.task1.cli.CommandLineParser;
import lacquered.task1.multTable.MultiplicationTableBuilder;

public class Main {
    public static void main(String[] args) {

        try {
            MultiplicationTableBuilder tableBuilder =
                    CommandLineParser.parseCommandLine(args);

            System.out.println(tableBuilder.getMultiplicationTable());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

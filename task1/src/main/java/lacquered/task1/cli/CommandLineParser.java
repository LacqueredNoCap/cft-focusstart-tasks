package lacquered.task1.cli;

import lacquered.task1.multTable.MultiplicationTableBuilder;
import lacquered.task1.multTable.MultiplicationTableBuilderImp;
import lacquered.task1.utils.DataValidator;

public class CommandLineParser {

    public static MultiplicationTableBuilder parseCommandLine(String[] args) throws IllegalArgumentException{

        if (args.length > 0 && args[0].equals("-help")) {
            throw new IllegalArgumentException(Helper.help());
        }

        if (args.length == 0) {
            throw new IllegalArgumentException("Не задан размер таблицы." + Helper.getHelpCommand());
        }

        if (!DataValidator.isDigit(args[0])) {
            throw new IllegalArgumentException("Некорректно задан размер таблицы." + Helper.getHelpCommand());
        }

        int multiplicationTableSize = Integer.parseInt(args[0]);

        return new MultiplicationTableBuilderImp(multiplicationTableSize);
    }
}

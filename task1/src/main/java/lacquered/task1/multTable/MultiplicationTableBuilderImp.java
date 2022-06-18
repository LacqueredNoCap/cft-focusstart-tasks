package lacquered.task1.multTable;

import lacquered.task1.cli.Helper;
import lacquered.task1.utils.DataValidator;

public class MultiplicationTableBuilderImp implements MultiplicationTableBuilder {

    private final MultiplicationTable multiplicationTable;

    public MultiplicationTableBuilderImp(int size) {
        MultiplicationTableBuilderImp.validateSettings(size);

        this.multiplicationTable = new MultiplicationTable(size);
    }

    private static void validateSettings(int multiplicationTableSize) throws IllegalArgumentException{
        if (!DataValidator.isCorrectMultiplicationTableSize(multiplicationTableSize)) {
            throw new IllegalArgumentException("Введен недопустимый размер таблицы." + Helper.getHelpCommand());
        }
    }

    @Override
    public String getMultiplicationTable() {
        return multiplicationTable.toString();
    }
}

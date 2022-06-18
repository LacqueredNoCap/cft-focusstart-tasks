package lacquered.task1.cli;

import lacquered.task1.utils.DataValidator;

public final class Helper {

    private Helper() {}

    public static String help() {
        return "Размер таблицы умножения задается при запуске через аргументы командной строки.\n" +
                "Доступный размер таблицы: от " +
                DataValidator.getMinMultiplicationTableSize() + " до " +
                DataValidator.getMaxMultiplicationTableSize();
    }

    public static String getHelpCommand() {
        return " Пожалуйста, введите команду заново или воспользуйтесь \"-help\".\n";
    }
}

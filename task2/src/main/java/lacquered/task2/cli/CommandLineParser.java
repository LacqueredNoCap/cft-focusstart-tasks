package lacquered.task2.cli;

import org.apache.log4j.Logger;

import lacquered.task2.settings.Settings;
import lacquered.task2.settings.SettingsImp;

public final class CommandLineParser {

    private final static Logger log = Logger.getLogger(CommandLineParser.class);

    private CommandLineParser() {}

    public static Settings parseCommandLine(String[] args) throws IllegalArgumentException {

        log.debug("Начался парсинг аргументов командной строки");

        if (args.length < 2) {
            throw new IllegalArgumentException("Введены не все параметры");
        }

        if (!(args[0].equals("-c") || args[0].equals("-f"))) {
            throw new IllegalArgumentException("Не задан способ вывода данных");
        }

        String outputFileName = args[0].equals("-f") && args.length > 2 ?
                args[2] : Settings.DEFAULT_OUTPUT_FILE_NAME;
        Settings settings = new SettingsImp(args[0], args[1], outputFileName);

        log.debug("Закончился парсинг аргументов командной строки");

        return settings;
    }
}

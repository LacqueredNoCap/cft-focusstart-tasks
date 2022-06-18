package lacquered.task2;

import lacquered.task2.cli.CommandLineParser;
import lacquered.task2.figures.Figure;
import lacquered.task2.utils.FigureReader;
import lacquered.task2.utils.FigureWriter;
import org.apache.log4j.Logger;
import lacquered.task2.settings.Settings;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class Main {

    private final static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Начало работы программы");

        try {
            Settings settings = CommandLineParser.parseCommandLine(args);
            Figure figure = FigureReader.readParameters(settings);
            FigureWriter.writeFigure(figure, settings);

            log.info("Корректное завершение работы программы");
        } catch (IllegalArgumentException | FileNotFoundException | NoSuchElementException e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }

    }
}

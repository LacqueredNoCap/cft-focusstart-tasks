package lacquered.task2.utils;

import lacquered.task2.figures.Figure;
import org.apache.log4j.Logger;
import lacquered.task2.settings.Settings;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public final class FigureWriter {

    private final static Logger log = Logger.getLogger(FigureWriter.class);

    private FigureWriter() {}

    public static void writeFigure(Figure figure, Settings settings) throws FileNotFoundException {
        PrintWriter pw;

        if (settings.getOutputMethod().equals("-c")) {
            pw = new PrintWriter(System.out);
            log.debug("Вывод данных о фигуре в консоль");

        } else {
            try {
                pw = new PrintWriter(settings.getOutputFileName());
                log.debug("Вывод данных о фигуре в " + settings.getOutputFileName());
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("Файл " + settings.getOutputFileName() + " не найден");
            }
        }

        pw.print(figure);
        pw.close();
    }
}

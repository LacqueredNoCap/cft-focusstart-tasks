package lacquered.task2.utils;

import org.apache.log4j.Logger;

import lacquered.task2.figures.*;
import lacquered.task2.settings.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class FigureReader {

    private final static Logger log = Logger.getLogger(FigureReader.class);

    private FigureReader() {}

    public static Figure readParameters(Settings settings) throws IllegalArgumentException, FileNotFoundException, NoSuchElementException {

        log.debug("Началось чтение данных из " + settings.getInputFileName());

        Figure figure;

        File file = new File(settings.getInputFileName());

        try (Scanner scan = new Scanner(file)) {

            String figureName;
            FigureType figureType;

            try {
                figureName = scan.nextLine().trim();
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("Файл " + settings.getInputFileName() + " оказался пустым");
            }

            try {
                figureType = FigureType.valueOf(figureName);
                log.debug("Успешно считан тип фигуры");
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Задан неверный код фигуры");
            }

            int num = figureType.getNumberOfParams();

            double[] values = new double[num];
            for (int i = 0; i < num; i++) {
                try {
                    values[i] = Double.parseDouble(scan.next());
                    log.debug("Успешно считан " + (i+1) + " параметр фигуры");
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Введены не все параметры фигуры");
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Введено нечисловое значение для параметра фигуры");
                }
            }

            figure = switch (figureType) {
                case CIRCLE -> new Circle(values[0]);
                case RECTANGLE -> new Rectangle(values[0], values[1]);
                case TRIANGLE -> new Triangle(values[0], values[1], values[2]);
            };
            log.debug("Успешно создан объект " + figure.getName());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл " + settings.getInputFileName() + " не найден");
        }

        log.debug("Закончилось чтение данных из " + settings.getInputFileName());

        return figure;
    }
}

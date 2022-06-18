package lacquered.task2.figures;

import java.util.Locale;

public class Rectangle extends Figure {
    private final double length;
    private final double width;

    public Rectangle(double a, double b) {

        if (isInvalidLength(a, b)) {
            throw new IllegalArgumentException("Введено недопустимое значение длины");
        }

        name = "Прямоугольник";
        length = Math.max(a, b);
        width = Math.min(a, b);

        calculateParameters();
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getDiagonal() {
        return Math.hypot(length, width);
    }

    @Override
    protected double calculatePerimeter() {
        return 2 * (length + width);
    }

    @Override
    protected double calculateArea() {
        return length * width;
    }

    @Override
    public String toString() {
        return toStringFigure() +
                String.format(Locale.ENGLISH, """
                Диагональ: %.2f см
                Длина: %.2f см
                Высота: %.2f см
                """, getDiagonal(), length, width);
    }
}

package lacquered.task2.figures;

import java.util.Locale;

public class Circle extends Figure {
    private final double radius;

    public Circle(double radius) {

        if (isInvalidLength(radius)) {
            throw new IllegalArgumentException("Введено недопустимое значение длины");
        }

        name = "Круг";
        this.radius = radius;
        calculateParameters();
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return 2 * radius;
    }

    @Override
    protected double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    protected double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public String toString() {
        return toStringFigure() +
                String.format(Locale.ENGLISH, """
                Радиус: %.2f см
                Диаметр: %.2f см
                """, radius, getDiameter());
    }
}

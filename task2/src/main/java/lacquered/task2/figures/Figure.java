package lacquered.task2.figures;

import java.util.Locale;

public abstract class Figure {
    protected static final double UPPER_LIMIT_LENGTH = 1000.0;

    protected String name;
    protected double area;
    protected double perimeter;

    protected void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    protected void setArea(double area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    protected void calculateParameters() {
        setPerimeter(calculatePerimeter());
        setArea(calculateArea());
    }

    protected abstract double calculatePerimeter();

    protected abstract double calculateArea();

    protected boolean isInvalidLength(double... distances) {
        for (double distance : distances) {
            if (!(distance > 0.0 && distance <= UPPER_LIMIT_LENGTH)) return true;
        }
        return false;
    }

    protected String toStringFigure() {
        return String.format(Locale.ENGLISH, """
                Тип фигуры: %s
                Площадь: %.2f кв.см
                Периметр: %.2f см
                """, name, area, perimeter);
    }
}

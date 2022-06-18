package lacquered.task2.figures;

import java.util.Locale;

public class Triangle extends Figure {
    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(double a, double b, double c) throws IllegalArgumentException{
        if (isInvalidLength(a, b, c)) {
            throw new IllegalArgumentException("Введено недопустимое значение длины");
        }
        if (!isTriangle(a, b, c)) {
            throw new IllegalArgumentException("Введены недопустимые стороны для треугольника");
        }

        name = "Треугольник";
        sideA = a;
        sideB = b;
        sideC = c;

        calculateParameters();
    }

    public double getSideA() {
        return sideA;
    }

    public double getSideB() {
        return sideB;
    }

    public double getSideC() {
        return sideC;
    }

    public double getOppositeAngleToSideA() {
        return cosineTheoremForAngle(sideA, sideB, sideC);
    }

    public double getOppositeAngleToSideB() {
        return cosineTheoremForAngle(sideB, sideA, sideC);
    }

    public double getOppositeAngleToSideC() {
        return cosineTheoremForAngle(sideC, sideA, sideB);
    }

    private static boolean isTriangle(double a, double b, double c) {
        return (a < b + c) && (b < a + c) && (c < a + b);
    }

    private double cosineTheoremForAngle(double oppositeSide, double side1, double side2) {
        // Теорема косинусов для противолежащего угла
        return Math.toDegrees(
                Math.acos(
                        (Math.pow(side1, 2) + Math.pow(side2, 2) - Math.pow(oppositeSide, 2)) /
                                (2 * side1 * side2)));
    }

    @Override
    protected double calculatePerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    protected double calculateArea() {
        return sideA * sideB * Math.sin(Math.toRadians(getOppositeAngleToSideC())) / 2.0f;
    }

    @Override
    public String toString() {
        return toStringFigure() +
                String.format(Locale.ENGLISH, """
                Длина стороны a: %.2f см, величина противолежащего угла: %.2f град
                Длина стороны b: %.2f см, величина противолежащего угла: %.2f град
                Длина стороны c: %.2f см, величина противолежащего угла: %.2f град
                """, sideA, getOppositeAngleToSideA(), sideB, getOppositeAngleToSideB(),
                        sideC, getOppositeAngleToSideC());
    }
}

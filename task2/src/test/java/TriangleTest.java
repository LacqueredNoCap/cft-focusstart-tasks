import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lacquered.task2.figures.Triangle;

public class TriangleTest {

    private final Triangle triangle = new Triangle(20.0, 10.0, Math.sqrt(3) * 10);

    @Test
    public void returnCorrectArea_ifPassTriangle() {
        Assertions.assertEquals(Math.sqrt(3) * 50, triangle.getArea(), 0.001);
    }

    @Test
    public void returnCorrectPerimeter_ifPassTriangle() {
        Assertions.assertEquals(30 + Math.sqrt(3) * 10, triangle.getPerimeter(), 0.001);
    }

    @Test
    public void returnCorrectOppositeAngleOfSideA_ifPassTriangle() {
        Assertions.assertEquals(90.0, triangle.getOppositeAngleToSideA(), 0.001);
    }

    @Test
    public void returnCorrectOppositeAngleOfSideB_ifPassTriangle() {
        Assertions.assertEquals(30.0, triangle.getOppositeAngleToSideB(), 0.001);
    }

    @Test
    public void returnCorrectOppositeAngleOfSideC_ifPassTriangle() {
        Assertions.assertEquals(60.0, triangle.getOppositeAngleToSideC(), 0.001);
    }

    @Test
    public void throwIllArgEx_WithSpecificMessage_ifPassAtLeastOneInvalidValue_lessThanLowerLimit() {
        try {
            new Triangle(10.55, -34.9, 27.4);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(),
                    "Введено недопустимое значение длины");
        }
    }

    @Test
    public void throwIllArgEx_WithSpecificMessage_ifPassAtLeastOneInvalidValue_greaterThanUpperLimit() {
        try {
            new Triangle(5200.0, 29.82, 84.56);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(),
                    "Введено недопустимое значение длины");
        }
    }

    @Test
    public void throwIllArgEx_WithSpecificMessage_ifPassInvalidValues_forTriangle() {
        try {
            new Triangle(10.0, 20.0, 50.0);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(),
                    "Введены недопустимые стороны для треугольника");
        }
    }

}

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lacquered.task2.figures.Circle;

public class CircleTest {

    @Test
    public void returnCorrectDiameter_ifPassValue() {
        Circle circle = new Circle(5.5);
        Assertions.assertEquals(11.0, circle.getDiameter(), 0.001);
    }

    @Test
    public void returnCorrectArea_ifPassValue() {
        double radius = 7.0 / Math.sqrt(Math.PI);
        Circle circle = new Circle(radius);
        Assertions.assertEquals(49.0, circle.getArea(), 0.001);
    }

    @Test
    public void returnCorrectPerimeter_ifPassValue() {
        double radius = 25.25 / Math.PI;
        Circle circle = new Circle(radius);
        Assertions.assertEquals(50.5, circle.getPerimeter(), 0.001);
    }

    @Test
    public void throwIllArgEx_ifPassInvalidValue_lessThanLowerLimit() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Circle(-1.0));
    }

    @Test
    public void throwIllArgEx_ifPassInvalidValue_greaterThanUpperLimit() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Circle(1500.0));
    }
}

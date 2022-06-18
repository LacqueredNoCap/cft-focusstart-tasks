import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lacquered.task2.figures.Rectangle;

public class RectangleTest {

    private final Rectangle rectangle = new Rectangle(72.0, 65.0);

    @Test
    public void returnCorrectLength_ifPassRectangle() {
        Assertions.assertEquals(72.0, rectangle.getLength(), 0.001);
    }

    @Test
    public void returnCorrectWidth_ifPassRectangle() {
        Assertions.assertEquals(65.0, rectangle.getWidth(), 0.001);
    }

    @Test
    public void returnCorrectArea_ifPassRectangle() {
        Assertions.assertEquals(4680, rectangle.getArea(), 0.001);
    }

    @Test
    public void returnCorrectPerimeter_ifPassRectangle() {
        Assertions.assertEquals(274, rectangle.getPerimeter(), 0.001);
    }

    @Test
    public void returnCorrectDiagonal_ifPassRectangle() {
        Assertions.assertEquals(97.0, rectangle.getDiagonal(), 0.001);
    }

    @Test
    public void throwIllArgEx_ifPassAtLeastOneInvalidValue_lessThanLowerLimit() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Rectangle(-101.8, 18.5));
    }

    @Test
    public void throwIllArgEx_ifPassAtLeastOneInvalidValue_greaterThanUpperLimit() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Rectangle(38.75, 1070.5));
    }

}

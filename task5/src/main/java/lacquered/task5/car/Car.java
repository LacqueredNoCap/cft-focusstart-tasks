package lacquered.task5.car;

public class Car {
    private static int counter = 0;

    private final int id;

    public Car() {
        id = getNextCounter();
    }

    private static synchronized int getNextCounter() {
        return ++counter;
    }

    @Override
    public String toString() {
        return "Car{" + id + "}";
    }
}

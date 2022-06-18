package lacquered.task5.producer;

import lacquered.task5.car.Car;
import lacquered.task5.storage.Storage;
import org.apache.log4j.Logger;

public class CarProducer extends AbstractStorageProducer<Car> {
    private static final Logger log = Logger.getLogger(CarProducer.class);

    public CarProducer(Storage<Car> storage, long producerTimeMs) {
        super(storage, producerTimeMs);

    }

    @Override
    public Car produce() {
        Car car = new Car();
        log.info("produced new car=" + car);
        return car;
    }
}

package lacquered.task5.consumer;

import lacquered.task5.car.Car;
import lacquered.task5.storage.Storage;
import org.apache.log4j.Logger;

public class CarConsumer extends AbstractStorageConsumer<Car> {
    private static final Logger log = Logger.getLogger(CarConsumer.class);

    public CarConsumer(Storage<Car> storage, long consumerTimeMs) {
        super(storage, consumerTimeMs);
    }

    @Override
    public void consume(Car car) {
        log.info("consumed car=" + car);
    }
}

package lacquered.task5.carFactory;

import java.util.OptionalInt;
import java.util.Properties;

public class CarFactoryConfigurator {
    private static final int MIN_CONSUMER_COUNT = 1;
    private static final int MAX_CONSUMER_COUNT = 100;
    private static final int MIN_PRODUCER_COUNT = 1;
    private static final int MAX_PRODUCER_COUNT = 100;
    private static final int MIN_STORAGE_SIZE = 1;
    private static final int MIN_PRODUCER_TIME = 1;
    private static final int MIN_CONSUMER_TIME = 1;

    private final int consumerCount;
    private final int producerCount;
    private final int producerTimeMS;
    private final int consumerTimeMS;
    private final int storageSize;

    public CarFactoryConfigurator(Properties properties) {
        this.consumerCount = getIntegerProperty(properties, "consumerCount")
                .orElseThrow(() -> new IllegalStateException("consumerCount is unavailable"));
        validateParameter("consumerCount", consumerCount, MIN_CONSUMER_COUNT, MAX_CONSUMER_COUNT);

        this.producerCount = getIntegerProperty(properties, "producerCount")
                .orElseThrow(() -> new IllegalStateException("producerCount is unavailable"));
        validateParameter("producerCount", producerCount, MIN_PRODUCER_COUNT, MAX_PRODUCER_COUNT);

        this.consumerTimeMS = getIntegerProperty(properties, "consumerTime")
                .orElseThrow(() -> new IllegalStateException("consumerTime is unavailable"));
        validateParameter("consumerTime", consumerTimeMS, MIN_CONSUMER_TIME);

        this.producerTimeMS = getIntegerProperty(properties, "producerTime")
                .orElseThrow(() -> new IllegalStateException("producerTime is unavailable"));
        validateParameter("producerTime", producerTimeMS, MIN_PRODUCER_TIME);

        this.storageSize = getIntegerProperty(properties, "storageSize")
                .orElseThrow(() -> new IllegalStateException("storageSize is unavailable"));
        validateParameter("storageSize", storageSize, MIN_STORAGE_SIZE);
    }

    public CarFactory createCarFactory() {
        CarFactory carFactory = new CarFactory(storageSize);
        for (int producerID = 0; producerID < producerCount; producerID++) {
            carFactory.addProducer(producerID + 1, producerTimeMS);
        }
        for (int consumerID = 0; consumerID < consumerCount; consumerID++) {
            carFactory.addConsumer(consumerID + 1, consumerTimeMS);
        }
        return carFactory;
    }

    private OptionalInt getIntegerProperty(Properties properties, String key) {
        try {
            return OptionalInt.of(Integer.parseInt(properties.getProperty(key)));
        } catch (NumberFormatException e) {
            return OptionalInt.empty();
        }
    }

    private void validateParameter(String parameterName, int value, int minValue) {
        this.validateParameter(parameterName, value, minValue, Integer.MAX_VALUE);
    }

    private void validateParameter(String parameterName, int value, int minValue, int maxValue) {
        if (value < minValue || value > maxValue)
            throw new IllegalStateException(parameterName + "=" + value + " not from valid interval:["
                    + minValue + ", " + maxValue + "]");
    }
}

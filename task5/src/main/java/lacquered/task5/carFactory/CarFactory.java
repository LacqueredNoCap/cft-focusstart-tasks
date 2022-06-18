package lacquered.task5.carFactory;

import lacquered.task5.car.Car;
import lacquered.task5.storage.Storage;
import lacquered.task5.consumer.CarConsumer;
import lacquered.task5.producer.CarProducer;

import java.util.ArrayList;
import java.util.List;

public class CarFactory {
    private final Storage<Car> storage;

    private final List<Thread> producers;
    private final List<Thread> consumers;

    public CarFactory(int storageSize) {
        this.storage = new Storage<>(storageSize);
        this.producers = new ArrayList<>();
        this.consumers = new ArrayList<>();
    }

    public void addProducer(int producerID, int producerTimeMS) {
        Thread producer = new Thread(new CarProducer(storage, producerTimeMS));
        producer.setName("Producer-" + producerID);
        producers.add(producer);
    }

    public void addConsumer(int consumerID, int consumerTimeMS) {
        Thread consumer = new Thread(new CarConsumer(storage, consumerTimeMS));
        consumer.setName("Consumer-" + consumerID);
        consumers.add(consumer);
    }

    public void start() {
        if (isStarted()) {
            return;
        }
        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);
    }

    private boolean isStarted() {
        return producers.stream().anyMatch(Thread::isAlive) &&
                consumers.stream().anyMatch(Thread::isAlive);
    }

    public void stop() {
        producers.forEach(Thread::interrupt);
        consumers.forEach(Thread::interrupt);
    }
}














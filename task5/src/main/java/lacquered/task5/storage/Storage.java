package lacquered.task5.storage;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Storage<T> {
    private static final Logger log = Logger.getLogger(Storage.class);

    private final List<T> cars;
    private final int maxSize;

    public Storage(int size) {
        this.cars = new ArrayList<>(size);
        this.maxSize = size;
    }

    public void put(T item) {
        cars.add(item);
        log.info("added to storage " + item + ", current size: " + cars.size());
    }

    public T take() {
        T item = cars.remove(0);
        log.info("removed from storage " + item + ", current size: " + cars.size());

        return item;
    }

    public boolean isEmpty() {
        return cars.isEmpty();
    }

    public boolean isFull() {
        return cars.size() == maxSize;
    }
}

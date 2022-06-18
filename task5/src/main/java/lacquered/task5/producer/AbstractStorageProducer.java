package lacquered.task5.producer;

import lacquered.task5.storage.Storage;

public abstract class AbstractStorageProducer<T> implements Runnable {
    private final long producerTimeMs;
    private final Storage<T> storage;

    public AbstractStorageProducer(Storage<T> storage, long producerTimeMs) {
        this.storage = storage;
        this.producerTimeMs = producerTimeMs;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(producerTimeMs);
            } catch (InterruptedException e) {
                return;
            }

            synchronized (storage) {
                while (storage.isFull()) {
                    try {
                        storage.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                T producedItem = produce();
                storage.put(producedItem);
                storage.notifyAll();
            }
        }
    }

    protected abstract T produce();
}

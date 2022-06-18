package lacquered.task5.consumer;

import lacquered.task5.storage.Storage;

public abstract class AbstractStorageConsumer<T> implements Runnable {
    private final long consumerTimeMs;
    private final Storage<T> storage;

    public AbstractStorageConsumer(Storage<T> storage, long consumerTimeMs) {
        this.storage = storage;
        this.consumerTimeMs = consumerTimeMs;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(consumerTimeMs);
            } catch (InterruptedException e) {
                return;
            }

            synchronized (storage) {
                while (storage.isEmpty()) {
                    try {
                        storage.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                consume(storage.take());
                storage.notifyAll();
            }
        }
    }

    protected abstract void consume(T object);
}

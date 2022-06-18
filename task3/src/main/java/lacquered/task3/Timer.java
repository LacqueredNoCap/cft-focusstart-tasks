package lacquered.task3;

import lacquered.task3.common.GameChange;
import lacquered.task3.common.GameChangeType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

public class Timer implements GameChangeListener {

    private final List<IntConsumer> tickCallbacks;
    private final Thread timerThread;
    private volatile boolean isPaused;

    public Timer() {
        tickCallbacks = new CopyOnWriteArrayList<>();
        isPaused = true;
        timerThread = new Thread(getTimerRunnable());
        timerThread.setDaemon(true);
    }

    private Runnable getTimerRunnable() {
        return () -> {
            int ticksAfterPause = 0;
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticksAfterPause++;
                if (!isPaused) {
                    acceptAllCallbacks(ticksAfterPause);
                } else {
                    ticksAfterPause = 0;
                }
            }
        };
    }

    private void acceptAllCallbacks(int ticks) {
        tickCallbacks.forEach(tickConsumer -> tickConsumer.accept(ticks));
    }

    public void start() {
        isPaused = false;
        if (!timerThread.isAlive()) {
            timerThread.start();
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void pause() {
        isPaused = true;
    }

    public void stop() {
        pause();
        timerThread.interrupt();
    }

    public void addListener(IntConsumer tickCallback) {
        tickCallbacks.add(tickCallback);
    }

    public void removeListener(IntConsumer tickCallback) {
        tickCallbacks.remove(tickCallback);
    }

    @Override
    public void update(GameChange gameChange) {
        if (gameChange.getChangeType() == GameChangeType.START_GAME) {
            start();
        }
        if (gameChange.getChangeType() == GameChangeType.LOSE ||
                gameChange.getChangeType() == GameChangeType.WIN ||
                gameChange.getChangeType() == GameChangeType.INIT_GAME_FIELD) {
            pause();
        }
    }
}
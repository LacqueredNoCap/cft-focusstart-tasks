package lacquered.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;

public class MultithreadingSeriesCalculator {

    private static final Logger log = LoggerFactory.getLogger(MultithreadingSeriesCalculator.class);

    private IntToDoubleFunction series;
    private int firstIndex;
    private int lastIndex;
    private int numberOfThreads;

    public MultithreadingSeriesCalculator(IntToDoubleFunction series, int firstIndex, int lastIndex, int numberOfThreads) {
        this.series = series;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        this.numberOfThreads = numberOfThreads;
    }

    public double calculate() {
        if (numberOfThreads == 1) {
            return singleThreadedSeriesCalculation();
        }
        List<Task> tasks = createTaskList();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Double>> taskFutures = submitTasksThreadListAndGetFutureList(executor, tasks);
        double result = getResultOfTaskList(taskFutures);
        executor.shutdown();

        return result;
    }

    private double singleThreadedSeriesCalculation() {
        double result = 0.0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            result += series.applyAsDouble(i);
        }
        return result;
    }

    private List<Task> createTaskList() {
        List<Task> taskList = new ArrayList<>();
        int taskFirstIndex = firstIndex;
        int iterations = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            taskFirstIndex += iterations;
            iterations = countNumberOfIterationsForTask(i);
            Task task = new Task(series, taskFirstIndex, iterations + taskFirstIndex - 1);
            taskList.add(task);
        }
        return taskList;
    }

    private int countNumberOfIterationsForTask(int threadIndex) {
        int numberOfIterations = lastIndex - firstIndex + 1;
        return numberOfIterations / numberOfThreads + ((threadIndex < numberOfIterations % numberOfThreads) ? 1 : 0);
    }

    private List<Future<Double>> submitTasksThreadListAndGetFutureList(
            ExecutorService executor,
            List<Task> taskList) {
        return taskList.stream()
                .map(executor::submit)
                .collect(Collectors.toList());
    }

    private double getResultOfTaskList(List<Future<Double>> taskFutures) {
        double result = 0.0;
        for (Future<Double> futureTask : taskFutures) {
            try {
                result += futureTask.get();
            } catch (ExecutionException e) {
                log.error("При подсчете результата этой подзадачи возникло исключение. " +
                        "Этот результат будет пропущен при общем суммировании", e);
            } catch (InterruptedException e) {
                log.error("При подсчете результата задачи возникло исключение. " +
                        "Будет выведен текущий результат, все оставшиеся подзадачи будут пропущены", e);
                return result;
            }
        }
        return result;
    }

    public IntToDoubleFunction getSeries() {
        return series;
    }

    public void setSeries(IntToDoubleFunction series) {
        this.series = series;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }
}

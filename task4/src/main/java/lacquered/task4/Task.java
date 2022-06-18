package lacquered.task4;

import java.util.concurrent.Callable;
import java.util.function.IntToDoubleFunction;

public class Task implements Callable<Double> {

    private IntToDoubleFunction function;
    private int firstIndex;
    private int lastIndex;

    public Task(IntToDoubleFunction function, int firstIndex, int lastIndex) {
        this.function = function;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    @Override
    public Double call() {
        double result = 0.0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            result += function.applyAsDouble(i);
        }
        return result;
    }

    public IntToDoubleFunction getFunction() {
        return function;
    }

    public void setFunction(IntToDoubleFunction function) {
        this.function = function;
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
}

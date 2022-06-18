package lacquered.task1.utils;

public class DataValidator {

    private static final int minMultiplicationTableSize = 1;
    private static final int maxMultiplicationTableSize = 32;

    public static boolean isDigit(String data) {
        try {
            Integer.parseInt(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isCorrectMultiplicationTableSize(int multiplicationTableSize) {
        return multiplicationTableSize >= minMultiplicationTableSize &&
                multiplicationTableSize <= maxMultiplicationTableSize;
    }

    public static int getMinMultiplicationTableSize() {
        return minMultiplicationTableSize;
    }

    public static int getMaxMultiplicationTableSize() {
        return maxMultiplicationTableSize;
    }
}

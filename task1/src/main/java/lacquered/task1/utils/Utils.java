package lacquered.task1.utils;

public final class Utils {

    private Utils() {}

    public static int numberOfDigits(int n) {
        return String.valueOf(n).length();
    }

    public static String separateLine(int size, int width) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append("-".repeat(width)).append("+");
        }

        return sb.substring(0, sb.length() - 1);
    }
}

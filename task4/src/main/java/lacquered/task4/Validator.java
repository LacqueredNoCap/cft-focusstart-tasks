package lacquered.task4;

public final class Validator {

    private static final int MIN_ALLOWED_INDEX = 1;
    private static final int MAX_ALLOWED_INDEX = 2_000_000_000;

    private Validator() {}

    public static void validateNumberOfThreads(int numberOfThreads) throws IllegalArgumentException {
        if (numberOfThreads <= 0) {
            throw new IllegalArgumentException("Количество потоков должно быть больше нуля");
        }
    }

    public static void validateIndices(int firstIndex, int lastIndex) throws IllegalArgumentException {
        if (!isInRange(firstIndex)) {
            throw new IllegalArgumentException(String.format(
                    "Первый индекс (%s) должен принадлежать диапазону [%s, %s]",
                    lastIndex, MIN_ALLOWED_INDEX, MAX_ALLOWED_INDEX));
        }

        if (!isInRange(lastIndex)) {
            throw new IllegalArgumentException(String.format(
                    "Последний индекс (%s) должен принадлежать диапазону [%s, %s]",
                    lastIndex, MIN_ALLOWED_INDEX, MAX_ALLOWED_INDEX));
        }

        if (firstIndex > lastIndex) {
            throw new IllegalArgumentException(String.format(
                    "Последний индекс (%s) должен быть строго больше первого индекса (%s)",
                    lastIndex, firstIndex));
        }
    }

    public static int validateInput(String s) {
        int lastIndex;
        try {
            lastIndex = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Введено нечисловое значение: " + s);
        }
        return lastIndex;
    }

    private static boolean isInRange(int index) {
        return index >= MIN_ALLOWED_INDEX && index <= MAX_ALLOWED_INDEX;
    }
}

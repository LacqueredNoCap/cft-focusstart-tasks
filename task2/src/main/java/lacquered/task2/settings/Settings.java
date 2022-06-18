package lacquered.task2.settings;

public interface Settings {
    String DEFAULT_OUTPUT_FILE_NAME = "output.txt";

    String getInputFileName();

    String getOutputFileName();

    String getOutputMethod();
}

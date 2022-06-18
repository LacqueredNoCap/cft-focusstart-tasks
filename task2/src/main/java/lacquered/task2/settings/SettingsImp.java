package lacquered.task2.settings;

public class SettingsImp implements Settings{

    private final String outputMethod;
    private final  String inputFileName;
    private final String outputFileName;

    public SettingsImp(String outputMethod, String inputFileName, String outputFileName) {
        this.outputMethod = outputMethod;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    @Override
    public String getInputFileName() {
        return inputFileName;
    }

    @Override
    public String getOutputFileName() {
        return outputFileName;
    }

    @Override
    public String getOutputMethod() {
        return outputMethod;
    }

}

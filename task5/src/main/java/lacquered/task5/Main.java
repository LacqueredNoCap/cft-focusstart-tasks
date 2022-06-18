package lacquered.task5;

import org.apache.log4j.Logger;
import lacquered.task5.carFactory.CarFactory;
import lacquered.task5.carFactory.CarFactoryConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    private static final String PROPERTIES_FILE_NAME = "application.properties";

    public static void main(String[] args) {
        try {
            Properties factoryProperties = loadProperties();
            CarFactoryConfigurator factoryConfigurator = new CarFactoryConfigurator(factoryProperties);
            CarFactory carFactory = factoryConfigurator.createCarFactory();
            carFactory.start();
        } catch (IOException e) {
            log.error("No such file \"" + PROPERTIES_FILE_NAME + "\"", e);
        } catch (IllegalStateException e) {
            log.error("Some properties are incorrect", e);
        }
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream propertiesInputStream = Main.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            properties.load(propertiesInputStream);
        }
        return properties;
    }
}

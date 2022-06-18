package lacquered.task3.records;

import java.io.Serializable;

public record PlayerRecord(String name, int score) implements Serializable {

    @Override
    public String toString() {
        return name + " - " + score;
    }
}

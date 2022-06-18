package lacquered.task6.protocol.message;

import java.io.Serializable;

public record User(String name) implements Serializable {

    @Override
    public String toString() {
        return name;
    }
}

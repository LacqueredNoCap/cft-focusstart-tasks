package lacquered.task3.records;

import org.apache.log4j.Logger;

import java.io.*;

public class HighScoresTable {

    private static final Logger log = Logger.getLogger(HighScoresTable.class);

    private final String tableFileName;

    /**
     * NOVICE - [0], MEDIUM - [1], EXPERT - [2]
     */
    private PlayerRecord[] records;

    public HighScoresTable(String fileName) {
        tableFileName = fileName;
        records = new PlayerRecord[3];

        for (int i = 0; i < records.length; i++) {
            records[i] = new PlayerRecord("No record yet", Integer.MAX_VALUE);
        }

        readTable();
    }

    public boolean isHighScore(int score, int index) {
        return records[index].score() > score;
    }

    public PlayerRecord getRecord(int index) {
        return records[index];
    }

    public void setRecord(PlayerRecord record, int index) {
        this.records[index] = record;

        log.info("Set new record: " + record + ", position: " + index);

        writeTable();
    }

    private void readTable() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(tableFileName))) {

            records = (PlayerRecord[]) ois.readObject();

            log.info("High scores table read");
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    private void writeTable() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(tableFileName))) {

            oos.writeObject(records);

            log.info("High scores table written");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

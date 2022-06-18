package lacquered.task1.multTable;

import lacquered.task1.utils.Utils;

public class MultiplicationTable {

    private final int[][] table;

    MultiplicationTable(int size) {

        int[][] table = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = (i + 1) * (j + 1);
            }
        }

        this.table = table;
    }

    @Override
    public String toString() {
        int firstColumnWidth = Utils.numberOfDigits(table.length);
        int otherColumnsWidth = Utils.numberOfDigits(table[table.length - 1][table.length - 1]);

        // Разделяющая линия типа --+---+---+---+...+---+---+---
        String separateLine = Utils.separateLine(table.length + 1, otherColumnsWidth)
                                   .substring(otherColumnsWidth - firstColumnWidth);


        // Выстраивание таблицы

        StringBuilder sb = new StringBuilder();

        // Первая строка
        sb.append(makeFirstLine(firstColumnWidth, otherColumnsWidth, separateLine));

        // Остальные строки
        for (int i = 0; i < table.length; i++) {
            sb.append(" ".repeat(firstColumnWidth - Utils.numberOfDigits(table[0][i])))
                    .append(table[0][i]);

            sb.append(makeAnotherLine(i, otherColumnsWidth, separateLine));
        }

        return sb.toString();
    }


    private StringBuilder makeFirstLine(int firstColumnWidth, int otherColumnsWidth, String separateLine) {
        StringBuilder sb = new StringBuilder();

        sb.append(" ".repeat(firstColumnWidth));
        for (int i = 0; i < table.length; i++) {
            sb.append("|")
                    .append(" ".repeat(otherColumnsWidth - Utils.numberOfDigits(table[0][i])))
                    .append(table[0][i]);
        }
        sb.append("\n")
                .append(separateLine)
                .append("\n");

        return sb;
    }


    private StringBuilder makeAnotherLine(int line, int otherColumnsWidth, String separateLine) {
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < table.length; j++) {
            sb.append("|")
                    .append(" ".repeat(otherColumnsWidth - Utils.numberOfDigits(table[line][j])))
                    .append(table[line][j]);
        }
        sb.append("\n")
                .append(separateLine)
                .append("\n");

        return sb;
    }
}

package minesweeper;

import java.util.List;
import java.util.Map;

public class Field {
    final String SAFE = ".";
    final String MINE = "X";
    final int FIELD_SIZE = 9;
    private final String[][] field;

    public Field() {
        field = new String[FIELD_SIZE][FIELD_SIZE];
    }

    public int getSize() {
        return FIELD_SIZE;
    }

    public void print() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == null) {
                    System.out.print(SAFE);
                } else {
                    System.out.print(field[i][j]);
                }
            }
            System.out.println();
        }
    }

    public void resetMines() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = null;
            }
        }
    }

    public void setMines(Map<Integer, List<Integer>> mines) {
        for (int row : mines.keySet()) {
            for (int column : mines.get(row)) {
                field[row][column] = MINE;
            }
        }
    }
}

package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Field {
    final String SAFE = ".";
    final String MINE = "X";
    final String GUEST = "*";
    final int FIELD_SIZE = 9;
    private final int[][] field;

    private List<Integer> guest;
    private List<Integer> mines;

    public Field() {
        mines = new ArrayList<>();
        guest = new ArrayList<>();
        field = new int[FIELD_SIZE][FIELD_SIZE];
    }

    public int getSize() {
        return FIELD_SIZE;
    }

    public void print() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < field.length; i++) {
            System.out.printf("%d|", i + 1);
            for (int j = 0; j < field[0].length; j++) {
                String text = switch (field[i][j]) {
                    case -1 -> GUEST;
                    case 0, 9 -> SAFE;
                    default -> "" + field[i][j];
                };
                System.out.print(text);
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }

    public void resetMines() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = 0;
            }
        }
    }

    /**
     * Set mines to field
     * @param mines position of mine (= row * numberOfColumn + column)
     */
    public void setMines(List<Integer> mines) {
        for (int minePosition: mines) {
            int row = minePosition / getSize();
            int column = minePosition % getSize();
            field[row][column] = 9;
            updateStepAroundMines(row, column);
        }
        this.mines = mines;
    }

    /**
     * Guest mine by position
     * @param position = row * numberOfColumn + column
     * @return true: finish game
     */
    public boolean guestMine(int row, int column) {
        if (field[row][column] > 0 && field[row][column] < 9) {
            System.out.println("There is a number here!");
            return false;
        }
        int position = row * getSize() + column;
        if (guest.contains(position)) {
            guest.remove((Integer)position);
            field[row][column] = 0;
        } else {
            guest.add(position);
            field[row][column] = -1;
        }
        if (guest.size() != mines.size()) {
            return false;
        }
        for (int num : guest) {
            if (!mines.contains(num)) {
                return false;
            }
        }
        return true;
    }

    private void updateStepAroundMines(int row, int column) {
        for (int i = Math.max(0, row - 1); i <= Math.min(field.length - 1, row + 1); i++) {
            for (int j = Math.max(0, column - 1); j <= Math.min(field.length - 1, column + 1); j++) {
                field[i][j] = Math.min(9, field[i][j] + 1);
            }
        }
    }
}

package minesweeper;

import java.sql.Array;
import java.util.*;

public class Field {
    final String SAFE = ".";
    final String MINE = "X";
    final String GUEST = "*";
    final String FREE = "/";
    final int FIELD_SIZE = 9;
    private final int[][] field;
    private final String[][] displayField;

    private List<Integer> guest;
    private List<Integer> mines;

    private int freeCheckedCount;

    public Field() {
        mines = new ArrayList<>();
        guest = new ArrayList<>();
        field = new int[FIELD_SIZE][FIELD_SIZE];
        displayField = new String[FIELD_SIZE][FIELD_SIZE];
        freeCheckedCount = 0;
    }

    public int getSize() {
        return FIELD_SIZE;
    }

    public void printSecret() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < field.length; i++) {
            System.out.printf("%d|", i + 1);
            for (int j = 0; j < field[0].length; j++) {
                String text = switch (field[i][j]) {
                    case -1 -> GUEST;
                    case 0 -> SAFE;
                    case 9 -> MINE;
                    default -> "" + field[i][j];
                };
                System.out.print(text);
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }

    public void print(boolean displayMine) {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < displayField.length; i++) {
            System.out.printf("%d|", i + 1);
            for (int j = 0; j < displayField[0].length; j++) {
                if (displayMine && field[i][j] == 9) {
                    System.out.print(MINE);
                } else if (displayField[i][j] == null) {
                    System.out.print(SAFE);
                } else {
                    System.out.print(displayField[i][j]);
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
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
     * @param row = row (start from 0)
     * @param column column (start from 0)
     * @return true: finish game
     */
    public boolean guestMine(int row, int column) {
//        if (field[row][column] > 0 && field[row][column] < 9) {
//            System.out.println("There is a number here!");
//            return false;
//        }
        int position = row * getSize() + column;
        if (guest.contains(position)) {
            guest.remove((Integer)position);
            displayField[row][column] = null;
        } else {
            guest.add(position);
            displayField[row][column] = GUEST;
        }
        print(false);
        if (guest.size() != mines.size()) {
            return false;
        }
        for (int num : guest) {
            if (!mines.contains(num)) {
                return false;
            }
        }
        System.out.println("Congratulations! You found all mines!");
        return true;
    }

    public boolean setFree(int row, int column) {
        if (field[row][column] == 9) {
            print(true);
            System.out.println("You stepped on a mine and failed!");
            return true;
        }
        Deque<Integer> deque = new ArrayDeque<>();
        deque.push(row * getSize() + column);
        while (!deque.isEmpty()) {
            int num = deque.pop();
            row = num / getSize();
            column = num % getSize();
            if (displayField[row][column] != null && !GUEST.equals(displayField[row][column])) {
                continue;
            }
            if (field[row][column] == 0) {
                displayField[row][column] = FREE;
                freeCheckedCount += 1;
                for (int i = Math.max(0, row - 1); i <= Math.min(getSize() - 1, row + 1); i++) {
                    for (int j = Math.max(0, column - 1); j <= Math.min(getSize() - 1, column + 1); j++) {
                        if (i == row && j == column) {
                            continue;
                        }
                        if (GUEST.equals(displayField[i][j]) && field[i][j] != 9) {
                            displayField[i][j] = null;
                        }
                        if (displayField[i][j] != null) {
                            continue;
                        }
                        if (field[i][j] == 0) {
                            deque.push(i * getSize() + j);
                        } else {
                            displayField[i][j] = "" + field[i][j];
                            freeCheckedCount += 1;
                        }
                    }
                }
            } else {
                displayField[row][column] = "" + field[row][column];
                freeCheckedCount += 1;
            }
        }
        print(false);
        boolean isFinish = freeCheckedCount == getSize() * getSize() - mines.size();
        if (isFinish) {
            System.out.println("Congratulations! You found all mines!");
        }
        return isFinish;
    }

    private void updateStepAroundMines(int row, int column) {
        for (int i = Math.max(0, row - 1); i <= Math.min(field.length - 1, row + 1); i++) {
            for (int j = Math.max(0, column - 1); j <= Math.min(field.length - 1, column + 1); j++) {
                field[i][j] = Math.min(9, field[i][j] + 1);
            }
        }
    }
}

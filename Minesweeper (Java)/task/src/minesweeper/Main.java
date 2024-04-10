package minesweeper;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Field field = new Field();
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field?");
        int numberOfMines = Integer.parseInt(scanner.nextLine());
        Map<Integer, List<Integer>> mines = new HashMap<>();
        while (numberOfMines > 0) {
            int row = new Random().nextInt(field.getSize());
            int column = new Random().nextInt(field.getSize());
            List<Integer> minesInRow = mines.get(row);
            if (minesInRow == null) {
                minesInRow = new ArrayList<>();
            }
            if (minesInRow.contains(column)) {
                continue;
            }
            minesInRow.add(column);
            mines.put(row, minesInRow);
            numberOfMines -= 1;
        }
        field.setMines(mines);
        field.print();
    }
}

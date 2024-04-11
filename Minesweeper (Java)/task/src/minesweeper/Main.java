package minesweeper;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Field field = new Field();
        Scanner scanner = new Scanner(System.in);

        // Setup field
        System.out.print("How many mines do you want on the field? ");
        int numberOfMines = Integer.parseInt(scanner.nextLine());
        List<Integer> mines = new ArrayList<>();
        int totalSpace = field.getSize() * field.getSize();
        while (numberOfMines > 0) {
            int index = new Random().nextInt(totalSpace);
            if (mines.contains(index)) {
                continue;
            }
            mines.add(index);
            numberOfMines -= 1;
        }
        field.setMines(mines);
        field.print();

        // Find mines
        boolean isFinish = false;
        while (!isFinish) {
           System.out.print("Set/delete mines marks (x and y coordinates): ");
           String input = scanner.nextLine();
           int column = Integer.parseInt(input.split(" ")[0]) - 1;
           int row = Integer.parseInt(input.split(" ")[1]) - 1;
           isFinish = field.guestMine(row, column);
           field.print();
        }
        System.out.println("Congratulations! You found all mines!");
    }
}

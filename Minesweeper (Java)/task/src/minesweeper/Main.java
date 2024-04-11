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
//        field.printSecret();
//
//        System.out.println("\n\n\n");
        field.print(false);

        // Find mines
        boolean isFinish = false;
        while (!isFinish) {
           System.out.print("Set/unset mines marks or claim a cell as free: ");
           String[] input = scanner.nextLine().split(" ");
           int column = Integer.parseInt(input[0]) - 1;
           int row = Integer.parseInt(input[1]) - 1;
           String mode = input[2];
           if ("free".equals(mode)) {
               isFinish = field.setFree(row, column);
           } else if ("mine".equals(mode)) {
               isFinish = field.guestMine(row, column);
           } else  {
               break;
           }
        }
    }
}

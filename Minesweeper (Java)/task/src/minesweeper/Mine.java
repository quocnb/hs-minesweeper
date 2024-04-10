package minesweeper;

public class Mine {
    private int row;
    private int column;

    public Mine(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean equals(Mine obj) {
        return row == obj.row && column == obj.column;
    }
}

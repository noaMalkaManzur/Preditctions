package Defenitions;

public class GridDTO
{
    private int rows;
    private int cols;

    public GridDTO(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}

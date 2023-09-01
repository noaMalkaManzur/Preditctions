package definition.world.impl;

import execution.instance.enitty.EntityInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Grid {
    private int rows;
    private int cols;
    private  List<Cell> cells = new ArrayList<>();

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    boolean checkIfValidCoordinate(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() <= rows && coordinate.getY() >= 0 && coordinate.getY() <= cols;
    }
    public Coordinate getRandomCoordinate(EntityInstance entityInstance) {
        Random random = new Random();

        while (true) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            Coordinate coordinate = new Coordinate(x, y);

            boolean occupied = cells.stream()
                    .anyMatch(cell -> cell.getCoordinate().getX() == x && cell.getCoordinate().getY() == y && cell.getIsOccupied());

            if (!occupied) {
                Cell newCell = new Cell(coordinate, true, entityInstance);
                cells.add(newCell);
                return coordinate;
            }
        }
    }


    private int distance(Coordinate source, int x, int y) {
        int dx = Math.abs(source.getX() - x);
        int dy = Math.abs(source.getY() - y);
        return Math.max(dx, dy);
    }
    public Collection<Coordinate> findEnvironmentCells(Coordinate source, int rank) {
        List<Coordinate> environmentCells = new ArrayList<>();
        if(checkIfValidCoordinate(source)) {
            for (int i = source.getX() - rank; i <= source.getX() + rank; i++) {
                for (int j = source.getY() - rank; j <= source.getY() + rank; j++) {
                    if (checkIfValidCoordinate(new Coordinate(i, j)) && distance(source, i, j) <= rank) {
                        environmentCells.add(new Coordinate(i, j));
                    }
                }
            }
        }
        else{
            throw new IllegalArgumentException("Source coordinate is out of boundaries");
        }

        return environmentCells;
    }

}

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
    public Coordinate getRandomCoordinateInit(EntityInstance entityInstance) {
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
    public Coordinate getNextMove(EntityInstance entityInstance) {
        Random random = new Random();
        Coordinate resCoordinate = entityInstance.getCoordinate();
        int x = resCoordinate.getX();
        int y = resCoordinate.getY();

        Coordinate leftCoordinate = new Coordinate((x - 1 + rows) % rows, y);
        Coordinate rightCoordinate = new Coordinate((x + 1) % rows, y);
        Coordinate upCoordinate = new Coordinate(x, (y + 1 + cols) % cols);
        Coordinate downCoordinate = new Coordinate(x, (y - 1 + cols) % cols);

        boolean validMoveFound = false;

        while (!validMoveFound) {
            int randomNum = random.nextInt(4);
            switch (randomNum) {
                case 0:
                    if (checkIfValidCoordinate(leftCoordinate) && isCoordinateUnoccupied(leftCoordinate, cells)) {
                        resCoordinate = leftCoordinate;
                        validMoveFound = true;
                    }
                    break;
                case 1:
                    if (checkIfValidCoordinate(rightCoordinate) && isCoordinateUnoccupied(rightCoordinate, cells)) {
                        resCoordinate = rightCoordinate;
                        validMoveFound = true;
                    }
                    break;
                case 2:
                    if (checkIfValidCoordinate(upCoordinate) && isCoordinateUnoccupied(upCoordinate, cells)) {
                        resCoordinate = upCoordinate;
                        validMoveFound = true;
                    }
                    break;
                case 3:
                    if (checkIfValidCoordinate(downCoordinate) && isCoordinateUnoccupied(downCoordinate, cells)) {
                        resCoordinate = downCoordinate;
                        validMoveFound = true;
                    }
                    break;
            }
        }

        return resCoordinate;
    }

    private boolean isCoordinateUnoccupied(Coordinate coordinateToCheck, List<Cell> cells) {
        return cells.stream()
                .noneMatch(cell -> cell.getCoordinate().getX() == coordinateToCheck.getX() && cell.getCoordinate().getY() == coordinateToCheck.getY());    }

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

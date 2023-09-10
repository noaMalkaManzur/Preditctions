package definition.world.impl;

import execution.context.Context;
import execution.instance.enitty.EntityInstance;

import java.util.*;
import java.util.stream.IntStream;

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
        initCells();
    }

    boolean checkIfValidCoordinate(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < rows && coordinate.getY() >= 0 && coordinate.getY() < cols;
    }
    private void initCells()
    {
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                cells.add(new Cell(new Coordinate(i,j),false,null));
            }
        }
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
                int index = findCoordinateIndex(coordinate);
                Cell newCell = new Cell(coordinate, true, entityInstance);
                if(index != -1)
                    cells.set(index,newCell);
                return coordinate;
            }
        }
    }
    public Coordinate getNextMove(EntityInstance entityInstance) {
        Coordinate resCoordinate = entityInstance.getCoordinate();
        int x = resCoordinate.getX();
        int y = resCoordinate.getY();
        int index = findCoordinateIndex(resCoordinate);
        int indexToMove;

        Coordinate upCoordinate = new Coordinate((x - 1 + rows) % rows, y);
        Coordinate downCoordinate = new Coordinate((x + 1) % rows, y);
        Coordinate rightCoordinate = new Coordinate(x, (y + 1 + cols) % cols);
        Coordinate leftCoordinate = new Coordinate(x, (y - 1 + cols) % cols);

        boolean validMoveFound = false;

        List<Integer> directions = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(directions);

        for (int direction : directions) {
            switch (direction) {
                case 0:
                    if (checkIfValidCoordinate(leftCoordinate) && isCoordinateUnoccupied(leftCoordinate, cells)) {
                        cells.set(index, new Cell(resCoordinate, false, null));
                        resCoordinate = leftCoordinate;
                        indexToMove = findCoordinateIndex(resCoordinate);
                        cells.set(indexToMove, new Cell(resCoordinate, true, entityInstance));
                        validMoveFound = true;
                    }
                    break;
                case 1:
                    if (checkIfValidCoordinate(rightCoordinate) && isCoordinateUnoccupied(rightCoordinate, cells)) {
                        cells.set(index, new Cell(resCoordinate, false, null));
                        resCoordinate = rightCoordinate;
                        indexToMove = findCoordinateIndex(resCoordinate);
                        cells.set(indexToMove, new Cell(resCoordinate, true, entityInstance));
                        validMoveFound = true;
                    }
                    break;
                case 2:
                    if (checkIfValidCoordinate(upCoordinate) && isCoordinateUnoccupied(upCoordinate, cells)) {
                        cells.set(index, new Cell(resCoordinate, false, null));
                        resCoordinate = upCoordinate;
                        indexToMove = findCoordinateIndex(resCoordinate);
                        cells.set(indexToMove, new Cell(resCoordinate, true, entityInstance));
                        validMoveFound = true;
                    }
                    break;
                case 3:
                    if (checkIfValidCoordinate(downCoordinate) && isCoordinateUnoccupied(downCoordinate, cells)) {
                        cells.set(index, new Cell(resCoordinate, false, null));
                        resCoordinate = downCoordinate;
                        indexToMove = findCoordinateIndex(resCoordinate);
                        cells.set(indexToMove, new Cell(resCoordinate, true, entityInstance));
                        validMoveFound = true;
                    }
                    break;
            }

            if (validMoveFound) {
                break; // Exit the loop if a valid move is found
            }
        }

        return resCoordinate;
    }

    private boolean isCoordinateUnoccupied(Coordinate coordinateToCheck, List<Cell> cells) {
        return cells.stream()
                .anyMatch(cell -> cell.getCoordinate().getX() == coordinateToCheck.getX() && cell.getCoordinate().getY() == coordinateToCheck.getY() && !cell.getIsOccupied()) ;}

    private int distance(Coordinate source, int x, int y) {
        int dx = Math.abs(source.getX() - x);
        int dy = Math.abs(source.getY() - y);
        return Math.max(dx, dy);
    }
    public Collection<Cell> findEnvironmentCells(Coordinate source, int rank, Context context) {

        if (!checkIfValidCoordinate(source)) {
            throw new IllegalArgumentException("Source coordinate is out of boundaries");
        }
        List<Cell> environmentCells = new ArrayList<>();

        context.getEntityManager().getInstances().forEach(entityInstance -> {
            for (int i = source.getX() - rank; i <= source.getX() + rank; i++) {
                for (int j = source.getY() - rank; j <= source.getY() + rank; j++) {
                    Coordinate currentCoordinate = new Coordinate(i, j);
                    if (checkIfValidCoordinate(currentCoordinate) && distance(source, i, j) <= rank) {
                        environmentCells.add(new Cell(currentCoordinate,true,entityInstance ));
                    }
                }
            }
        });
        return environmentCells;
    }
    private int findCoordinateIndex(Coordinate cord)
    {
        return IntStream.range(0, cells.size())
                .filter(i -> cells.get(i).getCoordinate().getX() == cord.getX() && cells.get(i).getCoordinate().getY() == cord.getY())
                .findFirst()
                .orElse(-1);
    }
}

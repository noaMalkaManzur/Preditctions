package definition.world.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Grid {
    private int rows;
    private int cols;

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Grid(int N, int M) {
        this.rows = N;
        this.cols = M;
    }

    boolean checkIfValidCoordinate(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() <= rows && coordinate.getY() >= 0 && coordinate.getY() <= cols;
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

package definition.world.impl;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int N;
    private int M;

    Grid(int N, int M) {
        this.N = N;
        this.M = M;
    }

    boolean checkIfValidCoordinate(Coordinate coordinate) {
        if (coordinate.getX() >= 0 && coordinate.getX() <= N - 1 && coordinate.getY() >= 0 && coordinate.getY() <= M - 1) {
            return true;
        }
        return false;
    }

    private int wrapX(int x) {
        return (x % N + N) % N;
    }

    private int wrapY(int y) {
        return (y % M + M) % M;
    }

    List<Coordinate> findEnvironmentCells(Coordinate source, int rank) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        List<Coordinate> arrCoordinate = new ArrayList<>();
        if (checkIfValidCoordinate(source)) {

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                        for (int k = 0; k < 8; k++) {
                            int ni = i + dx[k];
                            int nj = j + dy[k];
                            double distance = Math.sqrt((ni - source.getX()) * (ni - source.getX()) +
                                    (nj - source.getY()) * (nj - source.getY()));

                            if (Math.abs(distance - rank) < 1e-6) {
                                arrCoordinate.add(new Coordinate(ni, nj));
                            }

                        }

                    }
                }


        }
        else{
            throw new IllegalArgumentException("Source coordinate is out of boundaries");
        }
        return arrCoordinate;
    }

}

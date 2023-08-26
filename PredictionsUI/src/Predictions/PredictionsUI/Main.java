package Predictions.PredictionsUI;


import JavaFx.run.PredictionsApp;
import definition.world.impl.Coordinate;

import static javafx.application.Application.launch;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class Main {

    public static class InfiniteGrid
    {
        private final int n;
        private final int m;

        public InfiniteGrid(int n, int m) {
            this.n = n;
            this.m = m;
        }

        public Collection<Coordinate> findEnvironmentCells(Coordinate source, int rank) {
            List<Coordinate> environmentCells = new ArrayList<>();

            for (int i = source.getX() - rank; i <= source.getX() + rank; i++) {
                for (int j = source.getY() - rank; j <= source.getY() + rank; j++) {
                    if (isInGrid(i, j) && distance(source, i, j) <= rank) {
                        environmentCells.add(new Coordinate(i, j));
                    }
                }
            }

            return environmentCells;
        }

        private boolean isInGrid(int x, int y) {
            return x >= 0 && x <= n && y >= 0 && y <= m;
        }

        private int distance(Coordinate source, int x, int y) {
            int dx = Math.abs(source.getX() - x);
            int dy = Math.abs(source.getY() - y);
            return Math.max(dx, dy);
        }


    }
    public static void main(String[] args) {
        InfiniteGrid grid = new InfiniteGrid(4, 4);
        Coordinate source = new Coordinate(2, 1);
        int rank = 1;
        Collection<Coordinate> environmentCells = grid.findEnvironmentCells(source, rank);

        for (Coordinate coordinate : environmentCells) {
            System.out.println(coordinate.getX() + ", " + coordinate.getY());
        }
    }
}
    /*public static void main(String[] args)
    {
        //PredictionsManagment predictionsManagment = new PredictionsManagment();
        //predictionsManagment.run();
        //launch(PredictionsApp.class);



    }}*/


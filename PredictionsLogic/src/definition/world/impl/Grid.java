package definition.world.impl;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int N;
    private int M;
    Grid(int N, int M){
        this.N = N;
        this.M = M;
    }
    boolean checkIfValidCoordinate(Coordinate coordinate){
        if(coordinate.getX() >=0 && coordinate.getX()<= N-1 && coordinate.getY()>= 0 && coordinate.getY()<=M-1){
            return true;
        }
        return false;
    }
    List<Coordinate> findEnvironmentCells(Coordinate source, int rank){
        List<Coordinate> arrCoordinate = new ArrayList<>();
        for (int i= 0; i<= N-1 ;i++){
            for(int j = 0; j<=M-1; j++){

            }
        }
        return arrCoordinate;

    }
}

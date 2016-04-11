package YuliiaAvdieionokHorse;

import java.util.Arrays;

public class HorseFigure implements Figure {

    public int[][] nextStep(int[] startPoint) {
        int row = startPoint[0];
        int cell = startPoint[1];

        return new int[][]{
                {row - 1, cell - 2},
                {row + 1, cell - 2},
                {row - 1, cell + 2},
                {row + 1, cell + 2},
                {row - 2, cell - 1},
                {row + 2, cell - 1},
                {row - 2, cell + 1},
                {row + 2, cell + 1}};
    }

}

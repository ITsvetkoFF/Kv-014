package YuliiaSiroshtanHorse.bean;

import YuliiaSiroshtanHorse.exception.InvalidMatrixException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class Board {
    public final static int COORDINATE_X = 0;
    public final static int COORDINATE_Y = 1;
    public final static int VALUE = 2;

    private int matrix[][];
    private int width;
    private int height;
    private List<Integer> start;

    public Board(int[][] matrix) throws InvalidMatrixException {
        this.matrix = matrix;
        initializeStart();
        this.height = matrix.length;
        this.width = matrix[0].length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Integer> getStart() {
        return start;
    }

    public List<Integer> getStartCoodinates() {
        return new ArrayList<>(Arrays.asList(start.get(COORDINATE_X), start.get(COORDINATE_Y)));
    }

    public int getCellValue(int x, int y) {
        return matrix[x][y];
    }

    public int getCellValue(List<Integer> coordinates) {
        return getCellValue(coordinates.get(COORDINATE_X), coordinates.get(COORDINATE_Y));
    }

    public boolean isCellFree(int x, int y) {
        return matrix[x][y] == Cell.FREE.getValue();
    }

    public boolean isCellFree(List<Integer> coordinates) {
        return isCellFree(coordinates.get(COORDINATE_X), coordinates.get(COORDINATE_Y));
    }

    public boolean isFinish(int x, int y) {
        return matrix[x][y] == Cell.FINISH.getValue();
    }

    public boolean isFinish(List<Integer> coordinates) {
        return isFinish(coordinates.get(COORDINATE_X), coordinates.get(COORDINATE_Y));
    }

    public synchronized void update(List<Integer> coordinates, int value) {
        matrix[coordinates.get(COORDINATE_X)][coordinates.get(COORDINATE_Y)] = value;
    }

    private void initializeStart() throws InvalidMatrixException {
        for (int i = 0; i < matrix.length; i++) {
            if (start != null) {
                break;
            }
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == Cell.START.getValue()) {
                    start = new ArrayList<>(Arrays.asList(i, j, 0));
                    break;
                }
            }
        }
        if (start == null) {
            throw new InvalidMatrixException("Matrix has no start mark");
        }
    }

    public enum Cell {
        FREE(0),
        WALL(-1),
        START(-2),
        FINISH(-3);

        private int value;

        Cell(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}

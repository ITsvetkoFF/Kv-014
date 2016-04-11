package YuliiaSiroshtanHorse.service;

import YuliiaSiroshtanHorse.bean.Board;
import YuliiaSiroshtanHorse.bean.Knight;
import YuliiaSiroshtanHorse.bean.Piece;
import YuliiaSiroshtanHorse.exception.InvalidMatrixException;
import YuliiaSiroshtanHorse.exception.NoWayOutException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PathFinder {
    private Piece piece;
    private Board board;
    private List<Integer> finishCoordinates;
    private int lastStep;
    private Queue<List<Integer>> queue = new ConcurrentLinkedQueue<>();

    public int[][] execute(int[][] matrix) throws InvalidMatrixException, NoWayOutException {
        board = new Board(matrix);
        piece = new Knight(board);
        return execute(piece);
    }

    public int[][] twoDimensionalListToArray(List<List<Integer>> list) {
        if (list.size() == 0) {
            return null;
        }
        int[][] result = new int[list.size()][list.get(0).size()];
        int i = 0;
        for (List<Integer> coordinates : list) {
            result[i] = new int[] {coordinates.get(0), coordinates.get(1)};
            i++;
        }
        return result;
    }

    public int[][] execute(Piece piece) throws NoWayOutException {
        this.piece = piece;
        board = piece.getBoard();

        addNextMovesToQueue(board.getStart());

        while(queue.size() > 0 && finishCoordinates == null) {
            List<Integer> coordinates = queue.poll();
            board.update(coordinates, coordinates.get(Board.VALUE));
            addNextMovesToQueue(coordinates);
        }

        if (finishCoordinates == null) {
            throw new NoWayOutException("Can't find a way out");
        }

        return twoDimensionalListToArray(getPath());
    }

    private void addNextMovesToQueue(List<Integer> currentCell) {
        int currentValue = currentCell.get(Board.VALUE);
        int nextValue = currentValue + 1;
        for (List<Integer> nextCoordinates : piece.getPossibleMoves(currentCell)) {
            if (board.isFinish(nextCoordinates)) {
                finishCoordinates = nextCoordinates;
                lastStep = nextValue;
                break;
            }
            if (board.isCellFree(nextCoordinates)) {
                nextCoordinates.add(nextValue);
                boolean unique = true;
                for (List<Integer> coordinates : queue) {
                    if (coordinates.equals(nextCoordinates)) {
                        unique = false;
                        break;
                    }
                }
                if (unique) {
                    queue.add(nextCoordinates);
                }
            }
        }
    }

    private List<List<Integer>> getPath() {
        List<List<Integer>> path = new ArrayList<>();
        path.add(finishCoordinates);
        setPreviousStep(path, lastStep - 1);
        path.add(board.getStartCoodinates());
        Collections.reverse(path);
        return path;
    }

    private void setPreviousStep(List<List<Integer>> path, int previousValue) {
        if (previousValue < 1) {
            return;
        }
        List<Integer> lastCell = path.get(path.size() - 1);
        for (List<Integer> possibleStep : piece.getPossibleMoves(lastCell)) {
            if (board.getCellValue(possibleStep) == previousValue) {
                path.add(possibleStep);
                setPreviousStep(path, previousValue - 1);
                break;
            }
        }
    }
}

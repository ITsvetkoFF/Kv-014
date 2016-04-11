package YuliiaSiroshtanHorse.service;

import YuliiaSiroshtanHorse.bean.Board;
import YuliiaSiroshtanHorse.bean.Knight;
import YuliiaSiroshtanHorse.bean.Piece;
import YuliiaSiroshtanHorse.exception.NoWayOutException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * The purpose of this class is to find the shortest path that a
 * piece can traverse to get from start to finish point.
 * This class uses concurrency to find path more efficiently so it can be responsible
 * for running several threads simultaneously.
 */
public class ConcurrentPathFinder {
    private Piece piece;
    private Board board;
    private List<Integer> finishCoordinates;
    private int lastStep;
    private Queue<Future<List<List<Integer>>>> futureQueue = new ConcurrentLinkedQueue<>();
    private Queue<List<Integer>> coordinatesQueue = new ConcurrentLinkedQueue<>();
    private ExecutorService pool = Executors.newCachedThreadPool();

    /**
     * This method is designed to find the shortest possible path for a khight chess piece
     * to get from a start to finish point marked on the provided matrix.
     *
     * @param matrix matrix to find the path for.
     * @return array of coordinates that present path steps. Start and finish points are included.
     */
    public int[][] execute(int[][] matrix) {
        board = new Board(matrix);
        piece = new Knight(board);
        return execute(piece);
    }

    /**
     * This method is designed to find the shortest possible path for a given chess piece
     * to get from a start to finish point marked on the provided matrix.
     *
     * @param piece piece to find the path for.
     * @return array of coordinates that present path steps. Start and finish points are included.
     */
    public int[][] execute(Piece piece) {
        this.piece = piece;
        board = piece.getBoard();

        Future<List<List<Integer>>> initialCommand = pool.submit(() -> getNextMoves(board.getStart()));
        futureQueue.add(initialCommand);

        while(finishCoordinates == null) {
            if (!waitForFutureQueue() && finishCoordinates == null) {
                break;
            }
            for (Future<List<List<Integer>>> future : futureQueue) {
                if (future.isDone()) {
                    try {
                        coordinatesQueue.addAll(future.get());
                    } catch (InterruptedException | ExecutionException e) {
                        //
                    }
                    futureQueue.remove();
                } else {
                    break;
                }
            }
            for (List<Integer> coordinates : coordinatesQueue) {
                Future<List<List<Integer>>> command = pool.submit(() -> getNextMoves(coordinates));
                futureQueue.add(command);
                coordinatesQueue.remove();
            }
        }

        if (finishCoordinates == null) {
            throw new NoWayOutException("Can't find a way out");
        }

        pool.shutdownNow();

        return twoDimensionalListToArray(getPath());
    }

    private boolean waitForFutureQueue() {
        for (int i = 0; futureQueue.size() == 0 && i < 10; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                //
            }
        }
        return futureQueue.size() != 0;
    }

    private List<List<Integer>> getNextMoves(List<Integer> currentCell) {
        List<List<Integer>> nextMoves = new ArrayList<>();
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
                nextMoves.add(nextCoordinates);
                board.update(nextCoordinates, nextCoordinates.get(Board.VALUE));
            }
        }
        return nextMoves;
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
}

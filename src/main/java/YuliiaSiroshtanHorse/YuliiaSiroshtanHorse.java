package YuliiaSiroshtanHorse;

import YuliiaSiroshtanHorse.bean.Board;
import YuliiaSiroshtanHorse.bean.Knight;
import YuliiaSiroshtanHorse.bean.Piece;
import YuliiaSiroshtanHorse.exception.InvalidMatrixException;
import YuliiaSiroshtanHorse.exception.NoWayOutException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class YuliiaSiroshtanHorse {
    private static Piece piece;
    private static Board board;
    private static List<Integer> finishCoordinates;
    private static int lastStep;
    private static Queue<Future<List<List<Integer>>>> futureQueue = new ConcurrentLinkedQueue<>();
    private static Queue<List<Integer>> coordinatesQueue = new ConcurrentLinkedQueue<>();
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static int[][] compute(int[][] matrix) {
        try {
            board = new Board(matrix);
        } catch (InvalidMatrixException e) {
            //
        }
        piece = new Knight(board);
        int[][] result = null;
        try {
            result = compute(piece);
        } catch (NoWayOutException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int[][] compute(Piece piece) throws NoWayOutException {
        YuliiaSiroshtanHorse.piece = piece;
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

    private static boolean waitForFutureQueue() {
        for (int i = 0; futureQueue.size() == 0 && i < 10; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                //
            }
        }
        return futureQueue.size() != 0;
    }

    private static List<List<Integer>> getNextMoves(List<Integer> currentCell) {
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

    private static List<List<Integer>> getPath() {
        List<List<Integer>> path = new ArrayList<>();
        path.add(finishCoordinates);
        setPreviousStep(path, lastStep - 1);
        path.add(board.getStartCoodinates());
        Collections.reverse(path);
        return path;
    }

    private static void setPreviousStep(List<List<Integer>> path, int previousValue) {
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

    public static int[][] twoDimensionalListToArray(List<List<Integer>> list) {
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

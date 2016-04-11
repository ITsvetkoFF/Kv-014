package BohdanCherniakhHorse;


import java.util.*;

/**
 * Classname: HorsePathProcessor
 * Version: 1.0
 * Created: 10.04.2016
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 * Version 2, December 2004
 * <p>
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 * <p>
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 * <p>
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 * <p>
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 */
public class HorsePathProcessor {
    private static final int NORMAL_POINT = 0;
    private static final int NO_PATH = -1;
    private static final int START_POINT = -2;
    private static final int FINISH_POINT = -3;

    private int[][] board;
    private boolean[][] marked;
    private Map<Point, Point> previousVertex = new HashMap<>();
    Queue<Point> processQueue = new LinkedList<>();

    private long startTime;
    private long finishTime;
    private int[][] foundPath = null;

    public HorsePathProcessor(int[][] board) {
        startTime = System.currentTimeMillis();
        this.board = new int[board.length][];
        marked = new boolean[board.length][];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = Arrays.copyOf(board[i], board[i].length);
            marked[i] = new boolean[board.length];
        }
        findPath();
    }

    public long tookTime() {
        return finishTime - startTime;
    }

    public int[][] getPath() {
        return foundPath;
    }

    public boolean isPathExists() {
        return foundPath != null;
    }
    private void findPath() {
        Point start = findStartPoint();
        Point finish = findFinishPoint();
        if (start == null) {
            throw new IllegalArgumentException("The board has no starting point");
        }

        if (finish == null) {
            throw new IllegalArgumentException("The board has no finish point");
        }

        processQueue.add(start);
        previousVertex.put(start, start);

        while (!processQueue.isEmpty()) {
            Point currentPoint = processQueue.remove();

            if (isVisited(currentPoint.getX(), currentPoint.getY())) {
                continue;
            }

            marked[currentPoint.getX()][currentPoint.getY()] = true;

            for (int i = -1; i < 2; i += 2) {
                for (int j = -2; j < 3; j += 4) {
                    int newX = currentPoint.getX() + i;
                    int newY = currentPoint.getY() + j;
                    if (isAvailableMove(newX, newY) && !isVisited(newX, newY)) {
                        Point newPoint = new Point(newX, newY);
                        processQueue.add(newPoint);
                        previousVertex.put(newPoint, currentPoint);
                    }

                    newX = currentPoint.getX() + j;
                    newY = currentPoint.getY() + i;
                    if (isAvailableMove(newX, newY) && !isVisited(newX, newY)) {
                        Point newPoint = new Point(newX, newY);
                        processQueue.add(newPoint);
                        previousVertex.put(newPoint, currentPoint);
                    }
                }
            }

            if (currentPoint.equals(finish)) {
                Deque<Point> tempPath = new LinkedList<>();
                while (!finish.equals(start)) {
                    tempPath.push(finish);
                    finish = previousVertex.get(finish);
                }
                tempPath.push(start);
                foundPath = new int[tempPath.size()][];

                int indexer = 0;
                for (Point point : tempPath) {
                    foundPath[indexer] = new int[]{point.getY(), point.getX()};
                    indexer++;
                }

                finishTime = System.currentTimeMillis();
                return;
            }
        }
    }

    private boolean isVisited(int newX, int newY) {
        return marked[newX][newY];
    }

    private boolean isAvailableMove(int i, int j) {
        if (!isXInRange(i)) {
            return false;
        }

        if ((j < 0) || (j >= board[i].length)) {
            return false;
        }

        if (board[i][j] == NO_PATH) {
            return false;
        }

        return true;
    }

    private boolean isXInRange(int coordinate) {
        return ((coordinate >= 0) && (coordinate < board.length));
    }

    private Point findStartPoint() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == START_POINT) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    private Point findFinishPoint() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == FINISH_POINT) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }
}

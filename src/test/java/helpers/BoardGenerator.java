package helpers;

import java.awt.*;
import java.util.ArrayList;

public class BoardGenerator {

    public static int[][] generate(int size, Point pStart, Point pFinish) {
        int[][] board = getBoard(size, pStart, pFinish);
        return board;
    }

    public static int[][] generateWithObstacles(int size, Point pStart, Point pFinish, ArrayList<Rectangle> rects) {
        int[][] board = getBoard(size, pStart, pFinish);
        for (Rectangle rect : rects) {
            for (int i = rect.x; i < rect.x + rect.width; i++) {
                for (int j = rect.y; j < rect.y + rect.height ; j++) {
                    board[i][j] = -1;
                }
            }
        }
        return board;
    }

    private static int[][] getBoard(int size, Point pStart, Point pFinish) {
        int[][] board = new int[size][size];
        for(int i = 0; i<size; i++) {
            for(int j = 0; j<size; j++) {
                board[i][j] = 0;
            }
        }
        board[pStart.x][pStart.y] = -2;
        board[pFinish.x][pFinish.y] = -3;
        return board;
    }
}

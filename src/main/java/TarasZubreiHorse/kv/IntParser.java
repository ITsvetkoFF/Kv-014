package TarasZubreiHorse.kv;

import jdk.nashorn.internal.ir.Block;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Taras on 09.04.2016.
 */
public class IntParser {
    private Point start;

    public IntParser() {}

    public BlockType[][] parse(int[][] board) {
        BlockType[][] matrix = new BlockType[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                switch (board[i][j]) {
                    case 0: matrix[i][j] = BlockType.NORMAL; break;
                    case -2: matrix[i][j] = BlockType.START; start = new Point(i,j); break;
                    case -1: matrix[i][j] = BlockType.DISABLED; break;
                    case -3: matrix[i][j] = BlockType.DESTINATION; break;
                }
            }
        }
        return matrix;
    }

    public Point getStart() {
        return start;
    }
}

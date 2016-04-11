package YuliiaSiroshtanHorse.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends Piece {

    public Knight(Board board) {
        super(board);
    }

    @Override
    public List<List<Integer>> getPossibleMoves(List<Integer> currentCoordinates) {
        if (currentCoordinates.size() < 2) {
            throw new IllegalArgumentException("2 coordinates expected");
        }
        int x = currentCoordinates.get(0);
        int y = currentCoordinates.get(1);
        List<List<Integer>> possibleMoves = new ArrayList<>();
        for (int i = x - 2; i <= x + 2; i++) {
            int dif = Math.abs(x - i);
            switch (dif) {
                case 0:
                    break;
                case 1:
                    if (isMoveValid(i, y - 2)) {
                        possibleMoves.add(new ArrayList<>(Arrays.asList(i, y - 2)));
                    }
                    if (isMoveValid(i, y + 2)) {
                        possibleMoves.add(new ArrayList<>(Arrays.asList(i, y + 2)));
                    }
                    break;
                case 2:
                    if (isMoveValid(i, y - 1)) {
                        possibleMoves.add(new ArrayList<>(Arrays.asList(i, y - 1)));
                    }
                    if (isMoveValid(i, y + 1)) {
                        possibleMoves.add(new ArrayList<>(Arrays.asList(i, y + 1)));
                    }
                    break;
            }
        }
        return possibleMoves;
    }

    private boolean isMoveValid(int x, int y) {
        return (x >= 0) && (x < board.getHeight())
                && (y >= 0) && (y < board.getWidth())
                && board.getCellValue(x, y) != Board.Cell.WALL.getValue();
    }
}

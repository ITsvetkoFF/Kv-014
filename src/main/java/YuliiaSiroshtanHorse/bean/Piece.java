package YuliiaSiroshtanHorse.bean;

import java.util.List;

abstract public class Piece {
    protected Board board;

    public Piece(Board board) {
        this.board = board;
    }

    abstract public List<List<Integer>> getPossibleMoves(List<Integer> currentCoordinates);

    public Board getBoard() {
        return board;
    }
}

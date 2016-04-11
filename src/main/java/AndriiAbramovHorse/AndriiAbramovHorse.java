package AndriiAbramovHorse;

import AndriiAbramovHorse.bfs.Boot;

public class AndriiAbramovHorse {
    public static int[][] compute(int[][] board) {
        return Boot.findPath(board);
    }
}

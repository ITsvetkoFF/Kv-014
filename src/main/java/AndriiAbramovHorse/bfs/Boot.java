package AndriiAbramovHorse.bfs;

import AndriiAbramovHorse.bfs.helpers.Field;

public class Boot {

    public static int[][] findPath(int[][] field) {
        final int[][] path = new Field(field).processBFS();
        return path;

    }

}

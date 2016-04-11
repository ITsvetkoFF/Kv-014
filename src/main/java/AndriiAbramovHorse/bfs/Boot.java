package AndriiAbramovHorse.bfs;

import AndriiAbramovHorse.bfs.helpers.Field;

public class Boot {

    public static int[][] findPath(int[][] field) {

        return new Field(field).processBFS();

    }


}

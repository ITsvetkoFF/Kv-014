package AndriiAbramovHorse.bfs;

import AndriiAbramovHorse.bfs.helpers.Field;
import AndriiAbramovHorse.bfs.parse.FieldParser;
import AndriiAbramovHorse.bfs.parse.FileFieldParser;
import AndriiAbramovHorse.bfs.parse.XLSXFieldParser;

import java.io.File;
import java.io.IOException;

public class Boot {

    public static int[][] findPath(int[][] field) {

        return new Field(field).processBFS();

    }


}

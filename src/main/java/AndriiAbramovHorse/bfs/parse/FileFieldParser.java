package AndriiAbramovHorse.bfs.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileFieldParser implements FieldParser {

    private int[][] field;
    private File source;

    public FileFieldParser(File source) throws IOException {

        if (source == null)
            throw new NullPointerException("arg file is null");

        if (!source.exists())
            throw new FileNotFoundException(String.format("file %s doesn't exist", source.getName()));

        this.source = source;

        readFromFile();
    }

    private void readFromFile() throws IOException {
        List<String> lines = Files.readAllLines(this.source.toPath());

        int currRow = 0;
        int currCol = 0;

        this.field = new int[lines.size()][];

        for (String line : lines) {
            String[] parts = line.trim().split(" +");
            List<Integer> row = Stream.of(parts)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            this.field[currRow] = new int[row.size()];

            for (int num : row)
                this.field[currRow][currCol++] = num;

            currRow++;
            currCol = 0;

        }
    }


    @Override
    public int[][] getFieldMapping() {
        return this.field;
    }
}

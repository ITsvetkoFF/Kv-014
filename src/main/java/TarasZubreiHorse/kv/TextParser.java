package TarasZubreiHorse.kv;

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
public class TextParser implements Parsable {
    private Point start;

    public TextParser() {}

    @Override
    public BlockType[][] parse(String path) {
        ArrayList<List<BlockType>> matrix = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(line -> {
                matrix.add(new LinkedList<>());
                Stream.of(line.split(" "))
                        .map(Integer::parseInt)
                        .forEach(el -> {
                            switch (el) {
                                case 0: matrix.get(matrix.size()-1).add(BlockType.NORMAL); break;
                                case -2: matrix.get(matrix.size()-1).add(BlockType.START); start = new Point(matrix.get(matrix.size()-1).size()-1, matrix.size()-1); break;
                                case -1: matrix.get(matrix.size()-1).add(BlockType.DISABLED); break;
                                case -3: matrix.get(matrix.size()-1).add(BlockType.DESTINATION); break;
                            }
                        });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        BlockType[][] result = new BlockType[matrix.size()][matrix.get(0).size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = matrix.get(i).toArray(new BlockType[matrix.get(i).size()]);
        }
        return result;
    }

    @Override
    public void write(List<Point> points, String fromPath, String toPath, long scriptTime, long startTime) {
        String msg = //"Points:\n" + points.stream().map(Point::toString).collect(Collectors.joining(", "))
                "Moves: " + (points.size()+1)
                + "\nTotal time: " + (System.currentTimeMillis()-startTime)
                + "\nScript time: " + scriptTime;
        try {
            Files.write(Paths.get(toPath), msg.getBytes());
        } catch (IOException e) {e.printStackTrace();}
    }

    @Override
    public Point getStart() {
        return start;
    }
}

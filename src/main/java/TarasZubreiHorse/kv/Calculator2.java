package TarasZubreiHorse.kv;


import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Taras on 08.04.2016.
 */

public class Calculator2 implements Calc {

    private final Movable actor;
    private final Point[][] matrix;
    private final BlockType[][] map;

    public Calculator2(final BlockType[][] map, final Movable actor){
        this.map = Arrays.copyOf(map, map.length);
        this.actor = actor;
        this.matrix = new Point[map.length][map[0].length];
    }
    @Override
    public List<Point> find(final Point point) {
        map[point.x][point.y] = BlockType.START;
        if (map[point.x][point.y] == BlockType.DESTINATION)
            return Arrays.asList(point);
        else
            return find(move(point));
    }
    private List<Point> find(List<Point> points) {
        while (true) {
            final List<Point> newPoints = new LinkedList<>();
            for (final Point point : points) {
                if (map[point.x][point.y] == BlockType.DESTINATION)
                    return getPath(point);
                else
                    newPoints.addAll(move(point));
            }
            if (newPoints.isEmpty()) {
                System.err.println("Can't find");
                final List<Point> result = getPath(matrix[points.get(points.size()-1).x][points.get(points.size()-1).y]);
                result.add(points.get(points.size()-1));
                return result;
            }
            points = newPoints;
        }
    }

    private List<Point> move(final Point start) {
        final List<Point> result = Stream.of(actor.getMoves(start))
                .filter(point -> point.x >= 0 && point.x < matrix.length && point.y >= 0 && point.y < matrix[0].length)
                .filter(point -> matrix[point.x][point.y] == null)
                .filter(point -> map[point.x][point.y] != BlockType.DISABLED)
                .collect(Collectors.toList());
        result.forEach(point -> matrix[point.x][point.y] = start);
        return result;
    }

    private List<Point> getPath(final Point finish) {
        final List<Point> points = new LinkedList<>();
        Point point = finish;
        do {
            points.add(0, point);
            point = matrix[point.x][point.y];
        }
        while (map[point.x][point.y] != BlockType.START );
        return points;
    }
}

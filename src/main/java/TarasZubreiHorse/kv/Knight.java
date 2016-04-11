package TarasZubreiHorse.kv;


import java.util.HashSet;

/**
 * Created by Taras on 05.02.2016.
 */
public class Knight implements Movable {

    public Knight() {}
    @Override
    public Point[] getMoves(Point start) {
        return new Point[]{ new Point(start.x +2, start.y +1),
                            new Point(start.x +2, start.y -1),
                            new Point(start.x -2, start.y +1),
                            new Point(start.x -2, start.y -1),
                            new Point(start.x +1, start.y +2),
                            new Point(start.x +1, start.y -2),
                            new Point(start.x -1, start.y +2),
                            new Point(start.x -1, start.y -2)};
    }

    @Override
    public Point[] move(Point start, Point end) {

        HashSet<Point> points = new HashSet<>();
        for (int i = Math.min(start.x, end.x); i <= Math.max(start.x, end.x) ; i++)
            points.add(new Point(i, (Math.abs(start.x - end.x) == 1?end:start).y));
        for (int i = Math.min(start.y, end.y); i <= Math.max(start.y, end.y) ; i++)
            points.add(new Point((Math.abs(start.x - end.x) == 2?end:start).x, i));
        points.remove(start);
        return points.toArray(new Point[points.size()]);
    }
}

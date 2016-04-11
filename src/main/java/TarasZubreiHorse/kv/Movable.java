package TarasZubreiHorse.kv;

/**
 * Created by Taras on 05.04.2016.
 */
public interface Movable {
    public Point[] getMoves(final Point start);
    public Point[] move(final Point start, final Point end);
}

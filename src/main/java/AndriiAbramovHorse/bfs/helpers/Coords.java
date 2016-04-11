package AndriiAbramovHorse.bfs.helpers;

import java.util.Objects;

/**
 * Created by Abrasha on 04-Apr-16.
 */
public class Coords {

    private final int x;
    private final int y;

    public Coords(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return x == coords.x &&
                y == coords.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" +
                "y=" + y +
                ", x=" + x +
                ']';
    }
}

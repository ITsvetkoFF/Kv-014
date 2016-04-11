package AndriiAbramovHorse.bfs.helpers;

public class Node {

    private final bfs.helpers.Coords coords;
    private int mark;

    public Node(int y, int x) {
        this.coords = new bfs.helpers.Coords(y, x);
        this.mark = 0;
    }

    public bfs.helpers.Coords getCoords() {
        return coords;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public boolean isAvailableToStepIn() {
        return this.mark == bfs.helpers.Field.FREE || this.mark == bfs.helpers.Field.FINISH;
    }
}

package AndriiAbramovHorse.bfs.helpers;

public class Node {

    private final Coords coords;
    private int mark;

    public Node(int y, int x) {
        this.coords = new Coords(y, x);
        this.mark = 0;
    }

    public Coords getCoords() {
        return coords;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public boolean isAvailableToStepIn() {
        return this.mark == Field.FREE || this.mark == Field.FINISH;
    }
}

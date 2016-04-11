package AndriiAbramovHorse.bfs.helpers;

import AndriiAbramovHorse.bfs.figures.Horse;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Abrasha on 04-Apr-16.
 */
public class Field {
    public static final int FINISH = -3;
    public static final int START = -2;
    public static final int OBSTACLE = -1;
    public static final int FREE = 0;
    private final int height;
    private final int width;
    private final List<Coords> possibleSteps;
    private Node[][] field;
    private Coords startCoords;
    private Coords finishCoords;

    public Field(int[][] field) {

        if (field == null || field[0] == null)
            throw new NullPointerException("arg field | field[0] cannot be null");


        this.height = field.length;
        this.width = field[0].length;

        this.possibleSteps = new Horse().getPossibleMoves(); // TODO

        initField(field);
    }

    private void initField(int[][] field) {
        this.field = new Node[height][width];

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                final Node current = new Node(y, x);

                final int type = field[y][x];

                if (type == START)
                    this.startCoords = new Coords(y, x);

                if (type == FINISH)
                    this.finishCoords = new Coords(y, x);

                current.setMark(type);

                this.field[y][x] = current;
            }
        }
    }

    private Node getNodeAt(AndriiAbramovHorse.bfs.helpers.Coords coords) {
        return this.field[coords.getY()][coords.getX()];
    }

    public int[][] processBFS() {

        final Queue<Node> nodesToProcess = new LinkedList<>();

        final Node startNode = getNodeAt(startCoords);

        nodesToProcess.add(startNode);

        int depth = 1;
        startNode.setMark(depth);


        while (!nodesToProcess.isEmpty()) {


            final Node current = nodesToProcess.remove();

            if (finishCoords.equals(current.getCoords())) {
                return returnTrip(current.getMark());
            }

            depth = current.getMark() + 1;

            final int currY = current.getCoords().getY();
            final int currX = current.getCoords().getX();

            final int currentDeep = depth;

            possibleSteps.stream().map(e -> new Coords(currY + e.getY(), currX + e.getX()))
                    .filter(this::isAccessible)
                    .map(this::getNodeAt)
                    .filter(Node::isAvailableToStepIn)
                    .forEach(e -> {
                                e.setMark(currentDeep);
                                nodesToProcess.add(e);
                            }
                    );

        }

        throw new RuntimeException("No path found"); // ERROR : FINISH NOT APPROACHED
    }

    private int[][] returnTrip(int depth) {

        final int Y = 0;
        final int X = 1;

        int[][] result = new int[depth][2];

        result[0][Y] = startCoords.getY();
        result[0][X] = startCoords.getX();

        result[depth - 1][Y] = finishCoords.getY();
        result[depth - 1][X] = finishCoords.getX();

        final Node finishNode = getNodeAt(finishCoords);
        Node current = getCloser(finishNode);

        int level = depth - 2; // 2 - number of added cells manually

        while (!current.getCoords().equals(startCoords)) {

            final AndriiAbramovHorse.bfs.helpers.Coords cc = current.getCoords();
            result[level][Y] = cc.getY();
            result[level][X] = cc.getX();

            current = getCloser(current);
            level--;
        }

        return result;
    }

    private Node getCloser(Node toNode) {

        final int x = toNode.getCoords().getX();
        final int y = toNode.getCoords().getY();
        final int withMark = toNode.getMark() - 1;

        return possibleSteps.stream()
                .map(e -> new AndriiAbramovHorse.bfs.helpers.Coords(y + e.getY(), x + e.getX()))
                .filter(this::isAccessible)
                .map(this::getNodeAt)
                .filter(e -> e.getMark() == withMark)
                .findFirst()
                .get();

    }

    private boolean isAccessible(Coords coords) {
        final int x = coords.getX();
        final int y = coords.getY();

        return (x >= 0 && y >= 0 && x < width && y < height);
    }

}

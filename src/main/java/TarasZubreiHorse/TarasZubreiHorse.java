package TarasZubreiHorse;
import TarasZubreiHorse.kv.*;

import java.util.List;

public class TarasZubreiHorse {

    public static int[][] compute(int[][] board) {
        Movable actor = new Knight();

        IntParser parser = new IntParser();
        Calc calc = new Calculator2(parser.parse(board), actor);
        List<Point> points = calc.find(parser.getStart());
        points.add(0, parser.getStart());
        // using board
        // return [[0,0], [1,2], ... ]
        int[][] result = new int[points.size()][2];
        for (int i = 0; i < points.size(); i++) {
            result[i] = new int[] {points.get(i).x, points.get(i).y};
        }
        return new int[][]{};
    }
}

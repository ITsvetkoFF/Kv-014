package bfs.figures;

import bfs.helpers.Coords;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Abrasha on 04-Apr-16.
 */
public class Horse implements bfs.figures.Figure {
    public List<Coords> getPossibleMoves() {
        List<Coords> moves = Arrays.asList(
                new Coords(-1, -2),
                new Coords(1, -2), //

                new Coords(-1, 2),
                new Coords(1, 2), // bottom

                new Coords(-2, 1),
                new Coords(-2, -1), // left

                new Coords(2, -1),
                new Coords(2, 1) // right

        );

        return moves;
    }
}

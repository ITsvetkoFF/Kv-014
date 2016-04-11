package helpers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TestGenerator {
    public static Collection<Object[]> getTestBoards() {
        ArrayList<Rectangle> rects1 = new ArrayList<Rectangle>();
        rects1.add(new Rectangle(2, 0, 2, 998));
        rects1.add(new Rectangle(8, 2, 3, 998));
        ArrayList<Rectangle> rects2 = new ArrayList<Rectangle>();
        rects2.add(new Rectangle(2, 2, 996, 996));

        return Arrays.asList(new Object[][]{
                {BoardGenerator.generate(BoardSizes.SMALL, new Point(4, 8), new Point(0, 0)),
                        5},
                {BoardGenerator.generate(BoardSizes.SMALL, new Point(0, 0), new Point(49, 49)),
                        35},
                {BoardGenerator.generate(BoardSizes.SMALL, new Point(49, 49), new Point(0, 0)),
                        35},
                {BoardGenerator.generate(BoardSizes.SMALL, new Point(22, 22), new Point(23, 23)),
                        3},
                {BoardGenerator.generate(BoardSizes.LARGE, new Point(0, 0), new Point(999, 999)),
                        667},
                {BoardGenerator.generate(BoardSizes.LARGE, new Point(0, 0), new Point(999, 998)),
                        668},
                {BoardGenerator.generate(BoardSizes.LARGE, new Point(999, 0), new Point(0, 999)),
                        667},
                {BoardGenerator.generateWithObstacles(BoardSizes.LARGE, new Point(0, 0), new Point(999, 999), rects1),
                        1665},
                {BoardGenerator.generateWithObstacles(BoardSizes.LARGE, new Point(0, 0), new Point(999, 999), rects2),
                        999}
        });
    }

}

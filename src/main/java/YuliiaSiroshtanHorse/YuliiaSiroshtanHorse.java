package YuliiaSiroshtanHorse;

import YuliiaSiroshtanHorse.service.ConcurrentPathFinder;

public class YuliiaSiroshtanHorse {
    public static int[][] compute(int[][] matrix) {
        return new ConcurrentPathFinder().execute(matrix);
    }
}

package IlliaDoroshenkoHorse;

public class IlliaDoroshenkoHorse {
    public static int[][] compute(int[][] board) {
        // using board
        // return [[0,0], [1,2], ... ]
		Main finder = new Main();
        return finder.getPath(board);
    }
}

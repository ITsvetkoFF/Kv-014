package YuliiaAvdieionokHorse;

public class YuliiaAvdieionokHorse {
    public static int[][] compute(int[][] board) {
        Figure currentHorseFigure =
                new FilterStepFigure(new HorseFigure(), board);
        BreadFirstDirPath breadFirstDirPath = new BreadFirstDirPath(currentHorseFigure);
        return breadFirstDirPath.findPath(board);
    }
}

package YuliiaAvdieionokHorse;


import java.util.*;

public class FilterStepFigure implements Figure {

    private Figure figure;
    private int[][] inputPath;
    private final int rowSize;
    private final int maxCellNumber;
    Set<Integer> possibleSteps = new HashSet<Integer>();
    {
        possibleSteps.add(0);
        possibleSteps.add(-2);
        possibleSteps.add(-3);
    }

    public FilterStepFigure(Figure figure, int[][] inputPath) {

        this.figure = figure;
        this.inputPath = inputPath;
        this.rowSize = inputPath[0].length;
        this.maxCellNumber = inputPath.length;
    }


    @Override
    public int[][] nextStep(int[] startPoint) throws IllegalArgumentException {
        if (!isPointOnBoard(startPoint)) {
            throw new IllegalArgumentException("");
        }
        int[][] allSteps = figure.nextStep(startPoint);
        Collection<Point> validPointsCollection = getValidPoints(allSteps);

        if (validPointsCollection.isEmpty()) {
            return new int[0][0];
        } else {
            return getResultArray(validPointsCollection);
        }
    }


    private int[][] getResultArray(Collection<Point> validPointsCollection) {
        int[][] resultArray = new int[validPointsCollection.size()][];
        Iterator<Point> iterator = validPointsCollection.iterator();
        int stepIndex = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            int[] step = {point.getX(), point.getY()};
            resultArray[stepIndex++] = step;
        }
        return resultArray;
    }


    private Collection<Point> getValidPoints(int[][] allSteps) {
        List<Point> allowStep = new ArrayList<Point>();
        for (int[] step : allSteps) {

            if (isPointOnBoard(step) && isPointStepAllowed(step)) {
                allowStep.add(new Point(step[0], step[1]));
            }
        }
        return allowStep;
    }


    public boolean isPointStepAllowed(int[] step) {
        int cellNumber = step[0];
        int rowNumber = step[1];
        return possibleSteps.contains(inputPath[rowNumber][cellNumber]);
    }


//    private boolean isPointStepAllowed(int[] step) {
//        int cellNumber = step[0];
//        int rowNumber = step[1];
//
//        if (possibleSteps.contains(inputPath[rowNumber][cellNumber])) {
//            return true;
//        } else if (inputPath[rowNumber][cellNumber] == -3) {
//            return true;
//        } else if (inputPath[rowNumber][cellNumber] == -2) {
//            return true;
//        }
//        return false;
//    }


    protected boolean isPointOnBoard(int[] step) {

        if ((step[0] < 0) || (step[1] < 0)) {
            return false;
        }
        if ((step[0] >= rowSize) || (step[1] >= maxCellNumber)) {
            return false;
        }
        return true;
    }
}


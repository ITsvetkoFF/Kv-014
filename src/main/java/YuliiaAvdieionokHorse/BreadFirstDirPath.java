package YuliiaAvdieionokHorse;

import java.util.*;

public class BreadFirstDirPath {


    private Set<Point> markedPoint = new HashSet<Point>();
    private Map<Point, Integer> distanceColl = new HashMap<Point, Integer>();
    private Queue<Point> queue = new LinkedList<Point>();
    private Map<Point,Point> pathMap = new HashMap<Point, Point>();
    private Figure figure;
    private List<Point> resultCollection = new ArrayList<Point>();

    public BreadFirstDirPath(Figure figure) {

        this.figure = figure;
    }

    public int[][] findPath(final int[][] inputPath) {
        Point start = getStartPoint(inputPath);
        Point finalPoint = getFinalPoint(inputPath);
        markedPoint.add(start);
        distanceColl.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Point currentPoint = queue.poll();
            if(currentPoint.equals(finalPoint)){
                continue;
            }
            int[] currentPointCoordinates = {currentPoint.getX(), currentPoint.getY()};
            int[][] possibleFigureStep = figure.nextStep(currentPointCoordinates);

            for (int[] currentFigureStep : possibleFigureStep) {
                Point currentFigurePoint = new Point(currentFigureStep[0], currentFigureStep[1]);
                bfd(currentPoint, currentFigurePoint);
            }
        }
        Point currentPoint = getFinalPoint(inputPath);

        while (pathMap.containsKey(currentPoint)){
            resultCollection.add(currentPoint);
            currentPoint=pathMap.get(currentPoint);

        }
        resultCollection.add(start);

        return getResultArrayInRightOrder(resultCollection);
    }

    private int[][] getResultArrayInRightOrder(Collection<Point> resultCollection) {
        int[][] resultArray = new int[resultCollection.size()][];
        Collections.reverse((List<Point>) resultCollection);
        Iterator<Point> iterator = resultCollection.iterator();
        int stepIndex = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            int[] step = {point.getX(), point.getY()};
            resultArray[stepIndex++] = step;
        }
        return resultArray;
    }

    private void bfd(Point currentPoint,Point currentFigurePoint) {

        if (!markedPoint.contains(currentFigurePoint)) {
            pathMap.put(currentFigurePoint,currentPoint);
            int distanceParentPoint= distanceColl.get(currentPoint);
            distanceColl.put(currentFigurePoint, distanceParentPoint+1);
            markedPoint.add(currentFigurePoint);
            queue.add(currentFigurePoint);
        } else {
            int distanceOfExistingPath = distanceColl.get(currentFigurePoint);
            int distanceOfCurrentPoint = distanceColl.get(currentPoint)+1;
            if(distanceOfCurrentPoint<distanceOfExistingPath){
                distanceColl.put(currentFigurePoint, distanceOfCurrentPoint);
                pathMap.put(currentFigurePoint,currentPoint);
            }
        }
    }

    private Point getStartPoint(final int[][] inputPath) {

        for(int row=0;row<inputPath.length;row++){
            int[] currentRow=inputPath[row];
            for(int column=0;column<currentRow.length;column++){
                if(currentRow[column]==-2){
                    return new Point(column,row);
                }
            }
        }
        return null;
    }

    private Point getFinalPoint(final int[][] inputPath) {

        for(int row=0;row<inputPath.length;row++){
            int[] currentRow=inputPath[row];
            for(int column=0;column<currentRow.length;column++){
                if(currentRow[column]==-3){
                    return new Point(column,row);
                }
            }
        }
        return null;
    }
}

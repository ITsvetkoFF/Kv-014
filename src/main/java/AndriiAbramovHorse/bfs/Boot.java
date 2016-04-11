package bfs;

import bfs.helpers.Field;
import bfs.parse.FieldParser;
import bfs.parse.FileFieldParser;
import bfs.parse.XLSXFieldParser;

import java.io.File;
import java.io.IOException;

public class Boot {

    public static void main(String[] args) throws IOException {

        if (args.length < 2)
            throw new IllegalArgumentException("Error. No file provided");

        int[][] answer;

        switch (args[0]) {
            case "-raw":
                answer = findPathFromFile(args[1]);
                break;
            case "-xlsx":
                answer = findPathFromXSLX(args[1]);
                break;
            default:
                throw new IllegalArgumentException("No such option " + args[0]);
        }

        if (args.length > 2 && args[2].equalsIgnoreCase("-v"))
            print(answer);

    }

    public static int[][] findPathFromFile(String source) throws IOException {

        final long globalStartTime = time();
        final FieldParser parser = new FileFieldParser(new File(source));

        final long parsingExecutionTime = time() - globalStartTime;

        final long bfsStartTime = time();
        final int[][] path = new Field(parser.getFieldMapping()).processBFS();
        final long bfsExecutionTime = time() - bfsStartTime;

        final long globalFinishTime = time() - globalStartTime;

        System.out.printf("Parsing time   = %4d ms\n", parsingExecutionTime / 1_000_000);
        System.out.printf("Algorithm time = %4d ms\n", bfsExecutionTime / 1_000_000);
        System.out.printf("Overall time   = %4d ms\n", globalFinishTime / 1_000_000);
        System.out.printf("Length of path = %4d steps\n", path.length - 1);

        return path;
    }

    public static int[][] findPath(int[][] field) {

        final long bfsStartTime = time();

        final int[][] path = new Field(field).processBFS();

        final long bfsExecutionTime = time() - bfsStartTime;


        System.out.printf("Algorithm time = %4d ms\n", bfsExecutionTime / 1_000_000);
        System.out.printf("Length of path = %4d steps\n", path.length - 1);

        return path;

    }

    public static int[][] findPathFromXSLX(String source) throws IOException {

        final long globalStartTime = time();
        final XLSXFieldParser parser = new XLSXFieldParser(new File(source));

        final long parsingExecutionTime = time() - globalStartTime;

        final long bfsStartTime = time();

        final int[][] path = new Field(parser.getFieldMapping()).processBFS();

        final long bfsExecutionTime = time() - bfsStartTime;

        final long globalFinishTime = time() - globalStartTime;

        parser.putDownHorseWay(path, globalFinishTime, bfsExecutionTime); // writing path to excel file


        System.out.printf("Parsing time   = %4d ms\n", parsingExecutionTime / 1_000_000);
        System.out.printf("Algorithm time = %4d ms\n", bfsExecutionTime / 1_000_000);
        System.out.printf("Overall time   = %4d ms\n", globalFinishTime / 1_000_000);
        System.out.printf("Length of path = %4d steps\n", path.length - 1);

        return path;

    }

    public static long time() {
        return System.nanoTime();
    }


    public static void print(int[][] array) {
        for (int[] row : array) {
            for (int cell : row) {
                System.out.print(String.format("%4d", cell));
            }
            System.out.println();
        }
    }


}

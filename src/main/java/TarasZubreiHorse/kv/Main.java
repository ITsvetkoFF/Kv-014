package TarasZubreiHorse.kv;

import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Taras on 05.04.2016.
 */
public class Main {
    public static void main(String[] args) {
//        excelTest();
//        matrixTest();
        textTest();
    }
//    public static void excelTest() {
//        Movable actor = new Knight();
//        Parsable parser = new ExcelParser();
//        long startTime = System.currentTimeMillis();
//        BlockType[][] matrix = parser.parse(System.getProperty("user.dir") + "/src/test/resources/kv008horse.xlsx");
//        Calc calc = new Calculator2(matrix, actor);
//        long startScriptTime = System.currentTimeMillis();
//        List<Point> points = calc.find(parser.getStart());
//        long scriptTime = System.currentTimeMillis() - startScriptTime;
//        parser.write(points, System.getProperty("user.dir") + "/src/test/resources/kv008horse.xlsx", System.getProperty("user.dir") + "/src/test/resources/result.xlsx", scriptTime, startTime);
//    }
    public static void textTest() {
        Movable actor = new Knight();
        Parsable parser = new TextParser();
        long startTime = System.currentTimeMillis();
        /*System.getProperty("user.dir") + "/src/test/resources/test.txt"*/
        BlockType[][] matrix = parser.parse(Paths.get("./src/test/resources/test.txt").toAbsolutePath().toString());
        Calc calc = new Calculator2(matrix, actor);
        long startScriptTime = System.currentTimeMillis();
        List<Point> points = calc.find(parser.getStart());
        long scriptTime = System.currentTimeMillis() - startScriptTime;
        parser.write(points, System.getProperty("user.dir") + "/src/test/resources/test.txt", System.getProperty("user.dir") + "/src/test/resources/result.txt", scriptTime, startTime);
    }
    public static void matrixTest(){
        Movable actor = new Knight();
        BlockType[][] matrix = new BlockType[1000][1000];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = BlockType.NORMAL;
            }
        }
        matrix[matrix.length-1][matrix[matrix.length-1].length-1] = BlockType.DESTINATION;
        Calc calc = new Calculator2(matrix, actor);
        long startTime = System.currentTimeMillis();
        List<Point> points = calc.find(new Point());
        long scriptTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + scriptTime);
    }

}

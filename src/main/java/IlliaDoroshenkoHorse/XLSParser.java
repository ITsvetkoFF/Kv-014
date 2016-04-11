package IlliaDoroshenkoHorse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ilya on 07.04.2016.
 */
public class XLSParser {
    private static final int ROWS_BEFORE_FIELD = 2;

    public static void writeToFile(String name, List<Node> list, long time1, long time2) {
        InputStream in = null;
        XSSFWorkbook wb = null;
        FileOutputStream os = null;
        try {
            in = new FileInputStream(name);
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = wb.getSheetAt(0);
        Row firstRow = sheet.getRow(1);
        firstRow.getCell(17).setCellValue(time1);
        firstRow.getCell(41).setCellValue(time2);

        for (Node n : list) {

            Row row = sheet.getRow(n.getI() + 1 + ROWS_BEFORE_FIELD);
            Cell cell = row.getCell(n.getJ() + 1);
            cell.setCellValue("x");
        }

        try {
            os = new FileOutputStream(name);
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void p2(String name) {
        InputStream in = null;
        XSSFWorkbook wb = null;
        List<Integer> parsedLine = new ArrayList<>();
        int cols = 0;

        try {
            in = new FileInputStream(name);
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = wb.getSheetAt(0);
        sheet.getRow(3).getCell(1).setCellValue("fe");

        try {
            FileOutputStream os = new FileOutputStream(name);
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] parse(String name) {
        InputStream in = null;
        XSSFWorkbook wb = null;
        List<Integer> parsedLine = new ArrayList<>();
        int cols = 0;

        try {
            in = new FileInputStream(name);
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        it.next();
        while (it.hasNext()) {
            Row row = it.next();

            Iterator<Cell> cells = row.iterator();
            cols = 0;
            while (cells.hasNext()) {
                Cell cell = cells.next();
                cols++;
                if (cell.getCellStyle().getFillForegroundColor() == 0) {
                    parsedLine.add(-1);
                } else {
                    if (cell.getStringCellValue().equals("s")) {
                        parsedLine.add(-2);
                    } else if (cell.getStringCellValue().equals("f")) {
                        parsedLine.add(-3);
                    } else {
                        parsedLine.add(0);
                    }
                }
            }
        }

        return formArray(parsedLine, cols);
    }

    private static int[][] formArray(List<Integer> list, int cols) {
        int m = list.size() / cols;
        int n = cols;
        int[][] result = new int[m][n];
        int cnt = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = list.get(cnt++);
            }
        }
        return result;
    }
}

package bfs.parse;

import bfs.helpers.Field;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLSXFieldParser implements FieldParser {

    private final XSSFSheet sheet;
    private final XSSFWorkbook workbook;
    private final File source;
    private int[][] field;
    private BorderedAreaParser.Area area;

    public XLSXFieldParser(File source) throws IOException {

        this.source = source;

        final FileInputStream fileInputStream = new FileInputStream(source);
        this.workbook = new XSSFWorkbook(fileInputStream);
        this.sheet = workbook.getSheetAt(0);

        fileInputStream.close();

        this.parse();

    }

    private void parse() throws IOException {

        area = new BorderedAreaParser(sheet).getBounds();

        final int startRow = area.getStartRowIndex();
        final int startCol = area.getStartColIndex();

        field = new int[area.getSize().getHeight() + 1][area.getSize().getWidth() + 1];
        int x = 0;
        int y = 0;

        for (int row = startRow; row <= startRow + area.getSize().getHeight(); row++) {
            for (int col = startCol; col <= startCol + area.getSize().getWidth(); col++) {

                Cell cell = sheet.getRow(row).getCell(col);

                XSSFCellStyle style = (XSSFCellStyle) cell.getCellStyle();
                XSSFColor color = style.getFillBackgroundXSSFColor();

                if (cell.getStringCellValue().toLowerCase().equalsIgnoreCase("s")) {
                    field[y][x] = Field.START;
                } else if (cell.getStringCellValue().toLowerCase().equalsIgnoreCase("f")) {
                    field[y][x] = Field.FINISH;
                } else {
                    field[y][x] = color == null ? Field.FREE : Field.OBSTACLE;
                }

                x++;
            }
            x = 0;
            y++;
        }

    }

    public void putDownHorseWay(int[][] path, long globalExecutionTime, long bfsExecutionTime) throws IOException {

        final int startRow = area.getStartRowIndex();
        final int startCol = area.getStartColIndex();

        for (int[] point : path) {

            final int y = point[0];
            final int x = point[1];

            final Cell cell = getCellAt(startRow + y, startCol + x);

            int value = field[y][x];
            if (value == Field.START) {
                cell.setCellValue("S");
            } else if (value == Field.FINISH) {
                cell.setCellValue("F");
            } else if (value != Field.OBSTACLE) {
                cell.setCellValue(value);
            }
        }

        Cell overallTimeCell = getCellAt(1, 17); // Sorry for hardcoded values
        Cell algorithmTimeCell = getCellAt(1, 41);// Sorry for hardcoded values

        overallTimeCell.setCellValue(globalExecutionTime);
        algorithmTimeCell.setCellValue(bfsExecutionTime);

        FileOutputStream outputStream = new FileOutputStream(source);
        workbook.write(outputStream);
        outputStream.close();


    }

    private Cell getCellAt(int y, int x) {
        return sheet.getRow(y).getCell(x);
    }

    @Override
    public int[][] getFieldMapping() {
        return field;
    }


}

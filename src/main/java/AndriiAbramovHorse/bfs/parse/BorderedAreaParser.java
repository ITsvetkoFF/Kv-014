package AndriiAbramovHorse.bfs.parse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class BorderedAreaParser {

    private final XSSFSheet sheet;

    public BorderedAreaParser(XSSFSheet sheet) {
        this.sheet = sheet;
    }

    public Area getBounds() {

        Cell LT = null;
        Cell RT = null;
        Cell LB = null;

        for (final Row row : sheet) {

            for (Cell cell : row) {
                if (/*LT == null && */isLeftTopCorner(cell)) {
                    LT = cell;
                    continue;
                }
                if (/*LB == null && */isLeftBottomCorner(cell)) {
                    LB = cell;
                    continue;
                }
                if (/*RT == null && */isRightTopCorner(cell)) {
                    RT = cell;
                }

            }

        }

        if (LT == null || LB == null || RT == null)
            throw new IllegalArgumentException("Sheet is bad formatted");

        int width = RT.getColumnIndex() - LT.getColumnIndex();
        int height = LB.getRowIndex() - LT.getRowIndex();

        return new Area(new Size(width, height), LT.getRowIndex(), LT.getColumnIndex());


    }

    private boolean isLeftTopCorner(Cell cell) {
        XSSFCellStyle style = (XSSFCellStyle) cell.getCellStyle();

        return style.getBorderLeftEnum() != BorderStyle.NONE && style.getBorderTopEnum() != BorderStyle.NONE;

    }

    private boolean isLeftBottomCorner(Cell cell) {
        XSSFCellStyle style = (XSSFCellStyle) cell.getCellStyle();

        return style.getBorderLeftEnum() != BorderStyle.NONE && style.getBorderBottomEnum() != BorderStyle.NONE;

    }

    private boolean isRightTopCorner(Cell cell) {
        XSSFCellStyle style = (XSSFCellStyle) cell.getCellStyle();

        return style.getBorderRightEnum() != BorderStyle.NONE && style.getBorderTopEnum() != BorderStyle.NONE;

    }

    public final class Area {
        private final Size size;
        private final int startRowIndex;
        private final int startColIndex;

        public Area(Size size, int startRowIndex, int startColIndex) {
            this.size = size;
            this.startRowIndex = startRowIndex;
            this.startColIndex = startColIndex;
        }

        public Size getSize() {
            return size;
        }

        public int getStartRowIndex() {
            return startRowIndex;
        }

        public int getStartColIndex() {
            return startColIndex;
        }

        @Override
        public String toString() {
            return "Area{" +
                    "startColIndex=" + startColIndex +
                    ", startRowIndex=" + startRowIndex +
                    ", size=" + size +
                    '}';
        }
    }

    public final class Size {
        private final int width;
        private final int height;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public String toString() {
            return "Size{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

}

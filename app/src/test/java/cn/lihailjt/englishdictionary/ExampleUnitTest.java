package cn.lihailjt.englishdictionary;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

import static org.apache.poi.ss.usermodel.CellType.*;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void showExcel() throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("C://Users/Administrator/Desktop/Dictionary.xls")));
        HSSFSheet sheet = null;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
            sheet = workbook.getSheetAt(i);
            for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
                HSSFRow row = sheet.getRow(j);
                if (row != null) {
                    for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
                        if (row.getCell(k) != null) { // getCell 获取单元格数据
                            System.out.print(row.getCell(k) + "\t");
                        } else {
                            System.out.print("\t");
                        }
                    }
                }
                System.out.println(""); // 读完一行后换行
            }
            System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
        }
    }
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
//        DataFormatter formatter = new DataFormatter();
//        Sheet sheet1 = wb.getSheetAt(0);
//        for (Row row : sheet1) {
//            for (Cell cell : row) {
//                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
//                System.out.print(cellRef.formatAsString());
//                System.out.print(" - ");
//
//                // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
//                String text = formatter.formatCellValue(cell);
//                System.out.println(text);
//
//                // Alternatively, get the value and format it yourself
//                switch (cell.getCellTypeEnum()) {
//                    case STRING:
//                        System.out.println(cell.getRichStringCellValue().getString());
//                        break;
//                    case NUMERIC:
//                        if (DateUtil.isCellDateFormatted(cell)) {
//                            System.out.println(cell.getDateCellValue());
//                        } else {
//                            System.out.println(cell.getNumericCellValue());
//                        }
//                        break;
//                    case BOOLEAN:
//                        System.out.println(cell.getBooleanCellValue());
//                        break;
//                    case FORMULA:
//                        System.out.println(cell.getCellFormula());
//                        break;
//                    case BLANK:
//                        System.out.println();
//                        break;
//                    default:
//                        System.out.println();
//                }
//            }
//        }
    }
}
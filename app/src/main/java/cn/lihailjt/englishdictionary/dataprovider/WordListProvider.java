package cn.lihailjt.englishdictionary.dataprovider;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="bbmgt@163.com">lihai</a>
 * @version 1.0.0
 *          Created by lihai on 2018/4/28.
 */

public class WordListProvider {
    public static List<MyWord> getExcelData(String filePath) throws Exception {
        List<MyWord> wordList = new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
        HSSFSheet sheet = null;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
            sheet = workbook.getSheetAt(i);
            for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
                HSSFRow row = sheet.getRow(j);
                if (row != null) {
                    MyWord myWord = new MyWord();
                    for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
                        if (row.getCell(k) != null) { // getCell 获取单元格数据
                            System.out.print("{"+k+"}"+row.getCell(k) + "\t");
                            String temp = row.getCell(k).toString();
                            switch (k){
                                case 0://单词
                                    myWord.setWord(temp);
                                case 1://音标
                                    myWord.setPhonetic_symbol(temp);
                                case 2://释义
                                    myWord.setMean(temp);
                                case 3://词组
                                    myWord.setPhrase(temp);
                                case 4://派生词
                                    myWord.setDerivative(temp);
                                case 5://考频率
                                    myWord.setFrequency(temp);
                                case 6://例句
                                    myWord.setSentence(temp);
                            }
                        } else {
                            System.out.print("\t");
                        }
                    }
                    wordList.add(myWord);
                    myWord.setNum(wordList.size());
                }
                System.out.println(""); // 读完一行后换行
            }
            System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
        }
        return wordList;
    }
}

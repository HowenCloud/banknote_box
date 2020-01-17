package com.ixilink.banknote_box.common.util;

import com.ixilink.banknote_box.common.model.ExcelDataModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 导入Excel
 * @author 张皓峰
 * @date 2019-12-04 10:15
 */
@Slf4j
public class ExcelUtil {
    //工作薄
    private Workbook workbook;
    //需匹配的sheet名称
    private String[] sheetNames;
    //存储数据的主键（对应表格标题）
    private String[] titles;
    //大标题行
    private Integer maxTitleIndex;
    //表格标题行
    private Integer titleIndex = 1;
    //其他数据的行
    private int[] otherIndex;
    //是否模糊匹配sheet （null：匹配全部；true：模糊匹配；false：精准匹配）
    private Boolean like;
    //数据开始行
    private int dataIndex = 2;
    //Excel全部数据
    private List<ExcelDataModel> data = new ArrayList<>();

    private ExcelUtil() {
    }
    public ExcelUtil(File excel) {
        this.workbook = getWorkbook(excel);
        init();
    }
    public ExcelUtil(File excel, String[] titles, Integer maxTitleIndex, Integer titleIndex, int[] otherIndex, int dataIndex, boolean like, String... sheetNames) {
        this.workbook = getWorkbook(excel);
        this.titles = titles;
        this.maxTitleIndex = maxTitleIndex;
        this.titleIndex = titleIndex;
        this.otherIndex = otherIndex;
        this.dataIndex = dataIndex;
        this.like = like;
        this.sheetNames = sheetNames;
        init();
    }

    private void init(){
        //获取sheet总数
        int sheetCount = 0;
        if (like==null){
            sheetCount = 1;
        }else {
            sheetCount = workbook.getNumberOfSheets();
        }

        for (int i = 0; i < sheetCount; i++) {
            // 获取表
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            log.debug("sheetName:"+sheet.getSheetName());
            log.debug("getPhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
            log.debug("getLastRowNum:"+sheet.getLastRowNum());

            boolean flag = true;
            if (like != null){
                flag = false;
                if (like){
                    for (String name : this.sheetNames){
                        if (sheetName.contains(name)) flag = true;
                    }
                }
                else {
                    for (String name : this.sheetNames){
                        if (sheetName.equals(name)) flag = true;
                    }
                }
            }

            if (flag){
                ExcelDataModel excelDataModel = new ExcelDataModel();

                //总标题
                if (maxTitleIndex != null ){
                    Row row0 = sheet.getRow(maxTitleIndex-1);
                    row0.getCell(0).setCellType(CellType.STRING);
                    excelDataModel.setMaxTitle(row0.getCell(0).getStringCellValue());
                }
                //表格标题
                Row row1 = sheet.getRow(titleIndex-1);
                int size = titles==null?row1.getPhysicalNumberOfCells():titles.length;
                String[] titless = new String[size];
                for (int t = 0; t < size; t++) {
                    row1.getCell(t).setCellType(CellType.STRING);
                    titless[t] = row1.getCell(t).getStringCellValue();
                }
                excelDataModel.setTitles(titless);
                //map键
                if (titles == null) {
                    titles = new String[size];
                    for (int t = 0; t < size; t++) {
                        row1.getCell(t).setCellType(CellType.STRING);
                        titles[t] = row1.getCell(t).getStringCellValue();
                    }
                }
                //其他数据
                if (otherIndex != null){
                    for (int o: otherIndex){
                        Row rowO = sheet.getRow(o-1);
                        for (int t = 0; t < size; t++) {
                            rowO.getCell(t).setCellType(CellType.STRING);
                            String stringCellValue = rowO.getCell(t).getStringCellValue();
                            excelDataModel.getOther().put("other-"+o+"-"+(t+1),stringCellValue);
                        }
                    }
                }

                // 获取表格数据
                for (int j = dataIndex-1; j <= sheet.getLastRowNum(); j++) {
                    // 获取行
                    Row row = sheet.getRow(j);
                    String stringCellValue = row.getCell(0).getStringCellValue();
                    if (stringCellValue == null || stringCellValue.trim().equals("")) break;
                    // 一行数据
                    Map<String, Object> map = new HashMap<>();
                    // 获取单元格中的值
                    for (int t = 0; t < titles.length; t++) {
                        row.getCell(t).setCellType(CellType.STRING);
                        String value = row.getCell(t).getStringCellValue();
                        map.put(titles[t], value);
                    }
                    excelDataModel.getData().add(map);
                }
                data.add(excelDataModel);
            }
        }
    }


    public List<Map<String,Object>> getTableData(){
        return data.get(0).getData();
    }
    public ExcelDataModel getExcelData(){
        return data.get(0);
    }
    public List<ExcelDataModel> getAllData(){
        return data;
    }


    /**
     * 功能描述：
     * 〈获取Excel工作薄〉
     * @param file Excel文件
     * @date 2019-12-04 10:35
     * @return Workbook 工作薄
     */
    private Workbook getWorkbook(File file){
        Workbook workbook = null;
        try {
            if (file.getPath().endsWith("xls")) {
                log.debug("这是2003版本");
                workbook = new HSSFWorkbook(new FileInputStream(file));
            } else if (file.getPath().endsWith("xlsx")){
                workbook = new XSSFWorkbook(new FileInputStream(file));
                log.debug("这是2007版本");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
}

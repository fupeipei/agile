package com.yusys.agile.issue.excel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @Description: excel写入Handler
 *  @author: zhao_yd
 *  @Date:  4:56 下午
 *
 */
public class SpinnerWriteHandler implements SheetWriteHandler {

    private Map<Integer,String []> mapDropDown = new ConcurrentHashMap<>();

    public SpinnerWriteHandler(Map<Integer,String []> mapDropDown){
        this.mapDropDown = mapDropDown;
    }

    public SpinnerWriteHandler(){

    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        ///开始设置下拉框
        DataValidationHelper helper = sheet.getDataValidationHelper();//设置下拉框
        for (Map.Entry<Integer, String[]> entry : mapDropDown.entrySet()) {
            /***起始行、终止行、起始列、终止列**/
            CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, entry.getKey(), entry.getKey());
            /***设置下拉框数据**/
            DataValidationConstraint constraint = helper.createExplicitListConstraint(entry.getValue());
            DataValidation dataValidation = helper.createValidation(constraint, addressList);
            /***处理Excel兼容性问题**/
            if (dataValidation instanceof XSSFDataValidation) {
                dataValidation.setSuppressDropDownArrow(true);
                dataValidation.setShowErrorBox(true);
            } else {
                dataValidation.setSuppressDropDownArrow(false);
            }
            sheet.addValidationData(dataValidation);
        }

        /***处理Excel样式**/
        Row row = sheet.getRow(0);
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        row.setHeight((short) 500);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            sheet.setColumnWidth(i, 5000);
            Cell cell = row.getCell(i);
            cell.setCellStyle(setStyle(workbook));
        }
        row.setHeight((short) (205*7));
    }

    /***
     * 设置红色字体样式
     * @param wb
     * @return
     */
    public static CellStyle setStyle(Workbook wb){
        Font dataFont = wb.createFont();
        dataFont.setColor(HSSFColor.RED.index);
        dataFont.setFontName("宋体");
        dataFont.setFontHeight((short) 240);
        dataFont.setBold(true);
        dataFont.setFontHeightInPoints((short) 10);
        CellStyle dataStyle = wb.createCellStyle();
        dataStyle.setFont(dataFont);
        dataStyle.setWrapText(true);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        return dataStyle;
    }

}

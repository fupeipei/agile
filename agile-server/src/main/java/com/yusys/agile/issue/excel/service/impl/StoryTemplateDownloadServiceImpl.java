package com.yusys.agile.issue.excel.service.impl;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.yusys.agile.issue.excel.service.DownloadExcelTempletService;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.utils.CollectionUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  @Description: 故事模版下载
 *  @author: zhao_yd
 *  @Date: 2021/5/26 10:08 上午
 *
 */

@Service("StoryTemplateDownloadServiceImpl")
public class StoryTemplateDownloadServiceImpl implements DownloadExcelTempletService, SheetWriteHandler {

    @Autowired
    private Sprintv3Service sprintv3Service;


    @Override
    public void download() {

    }



    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Map<Integer,String []> mapDropDown = new HashMap<>();
        //关联的迭代
        String[] sprintInfo = getSprintInfo();
        mapDropDown.put(3,sprintInfo);
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

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    private String[] getSprintInfo(){
        Long systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
        List<SprintListDTO> sprintListDTOS = sprintv3Service.getEffectiveSprintsBySystemId(systemId);
        if(CollectionUtils.isNotEmpty(sprintListDTOS)){
            Set<String> collect = sprintListDTOS.stream().
                    map(s-> s.getSprintId() + "+" + s.getSprintName()).collect(Collectors.toSet());
            return collect.toArray(new String[0]);
        }
        return new String[]{};
    }

}

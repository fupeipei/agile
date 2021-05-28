package com.yusys.agile.easyexcel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

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


    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        ///开始设置下拉框
        DataValidationHelper helper = sheet.getDataValidationHelper();//设置下拉框
        if(MapUtils.isNotEmpty(mapDropDown)){
            for (Map.Entry<Integer, String[]> entry : mapDropDown.entrySet()) {
                if(entry.getValue().length >0 ){
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
            }
        }
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

}

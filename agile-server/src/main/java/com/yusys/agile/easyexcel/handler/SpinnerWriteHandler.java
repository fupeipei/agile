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
    private int index;
    public SpinnerWriteHandler(Map<Integer,String []> mapDropDown){
        this.mapDropDown = mapDropDown;
        this.index = 0;
    }


    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

//        //获取一个workbook
//        Sheet sheet = writeSheetHolder.getSheet();
//        //设置下拉框
//        DataValidationHelper helper = sheet.getDataValidationHelper();
//        //定义sheet的名称
//        String hiddenName = "hidden";
//        //1.创建一个隐藏的sheet 名称为 hidden
//        Workbook workbook = writeWorkbookHolder.getWorkbook();
//        Sheet hidden = workbook.createSheet(hiddenName);
//        for (Map.Entry<Integer, String[]> entry : mapDropDown.entrySet()) {
//            //下拉框的起始行,结束行,起始列,结束列
//            CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, entry.getKey(), entry.getKey());
//            //获取excel列名
//            String excelLine = getExcelLine(entry.getKey());
//            //2.循环赋值
//            String[] values = entry.getValue();
//            for (int i = 0, length = values.length; i < length; i++) {
//                // 3:表示你开始的行数  3表示 你开始的列数
//                hidden.createRow(i).createCell(entry.getKey()).setCellValue(values[i]);
//            }
//            //4.=hidden!$H:$1:$H$50  sheet为hidden的 H1列开始H50行数据获取下拉数组
//            String refers = "="+hiddenName + "!$"+excelLine+
//                    "$1:$"+excelLine +"$"+ (values.length+1);
//            //5 将刚才设置的sheet引用到你的下拉列表中
//            DataValidationConstraint constraint = helper.createFormulaListConstraint(refers);
//            DataValidation dataValidation = helper.createValidation(constraint, addressList);
//            writeSheetHolder.getSheet().addValidationData(dataValidation);
//        }
//        //设置列为隐藏
//        int hiddenIndex = workbook.getSheetIndex("hidden");
//        if (!workbook.isSheetHidden(hiddenIndex)) {
//        Sheet sheet = writeSheetHolder.getSheet();
//        }
        DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();

        // k 为存在下拉数据集的单元格下表 v为下拉数据集
        mapDropDown.forEach((k, v) -> {
            // 创建sheet，突破下拉框255的限制
            // 获取一个workbook
            Workbook workbook = writeWorkbookHolder.getWorkbook();
            // 定义sheet的名称
            String sheetName = "sheet" + k;
            // 1.创建一个隐藏的sheet 名称为 proviceSheet
            Sheet proviceSheet = workbook.createSheet(sheetName);
            // 从第二个工作簿开始隐藏
            this.index++;
            int hiddenIndex = workbook.getSheetIndex(sheetName);
            if (!workbook.isSheetHidden(hiddenIndex)) {
                workbook.setSheetHidden(hiddenIndex, true);
            }
            //workbook.setSheetHidden(this.index, true);
            // 2.循环赋值（为了防止下拉框的行数与隐藏域的行数相对应，将隐藏域加到结束行之后）
            if(v.length>0){
                for (int i = 0, length = v.length; i < length; i++) {
                    // i:表示你开始的行数 0表示你开始的列数
                    proviceSheet.createRow(i).createCell(0).setCellValue(v[i]);
                }
                Name category1Name = workbook.createName();
                category1Name.setNameName(sheetName);
                // 4 $A$1:$A$N代表 以A列1行开始获取N行下拉数据
                category1Name.setRefersToFormula(sheetName + "!$A$1:$A$" + (v.length));
                // 5 将刚才设置的sheet引用到你的下拉列表中,1表示从行的序号1开始（开始行，通常行的序号为0的行是表头），50表示行的序号50（结束行），表示从行的序号1到50，k表示开始列序号和结束列序号
                CellRangeAddressList addressList = new CellRangeAddressList(1, 5000, k, k);
                DataValidationConstraint constraint8 = helper.createFormulaListConstraint(sheetName);
                DataValidation dataValidation3 = helper.createValidation(constraint8, addressList);
                // 阻止输入非下拉选项的值
                dataValidation3.setErrorStyle(DataValidation.ErrorStyle.STOP);
                dataValidation3.setShowErrorBox(true);
                dataValidation3.setSuppressDropDownArrow(true);
                dataValidation3.createErrorBox("提示", "此值与单元格定义格式不一致");
                // validation.createPromptBox("填写说明：","填写内容只能为下拉数据集中的单位，其他单位将会导致无法入仓");
                writeSheetHolder.getSheet().addValidationData(dataValidation3);
            }

        });

    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        throw new UnsupportedOperationException();
    }


    /**
     * 返回excel列标A-Z-AA-ZZ
     * @param num
     * @return
     */
    public static String getExcelLine(int num) {
        String line = "";
        int first = num/26;
        int second = num % 26;
        if (first>0) {
            line = (char)('A'+first-1)+"";
        }
        line += (char)('A'+second)+"";
        return line;
    }

}

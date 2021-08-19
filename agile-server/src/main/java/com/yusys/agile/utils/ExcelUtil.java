package com.yusys.agile.utils;

import com.yusys.agile.excel.enums.ExcelColEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by seemotions on 2018/1/12.
 */
public class ExcelUtil {
    private ExcelUtil() {
    }

    public static boolean is2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean is2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }


    private static String EPIC_EXPORT_TEMPLATE = "epicExportTemplate.xlsx";
    private static String EPIC_IMPORT_TEMPLATE = "epicImportTemplate.xlsx";

    private static String FEATURE_EXPORT_TEMPLATE = "featureExportTemplate.xlsx";
    private static String FEATURE_IMPORT_TEMPLATE = "featureImportTemplate.xlsx";

    private static String STORY_EXPORT_TEMPLATE = "storyExportTemplate.xlsx";
    private static String STORY_IMPORT_TEMPLATE = "storyImportTemplate.xlsx";
    private static String TASK_IMPORT_TEMPLATE = "taskImportTemplate.xlsx";

    private static String IMPORT_TEMPLATE = "importTemplate";

    public static final String EXPORT_TEMPLATE = "exportTemplate";

    public static final String SHEET_NAME = "sheetName";

    public static final String EPIC_SHEET = "epics";
    public static final String featureSheet = "features";
    public static final String storySheet = "storys";
    public static final String taskSheet = "tasks";
    public static final String formulaFault = "单元格带有公式";
    private static final String NUMBER_PATTERN = "^[0-9]+([.]{1}[0-9]+){0,1}$";
    private static final String INTEGER_PATTERN = "^\\d+$";
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * XSSFCell边框样式
     *
     * @param wb
     * @return
     */
    public static XSSFCellStyle getBorderThin(XSSFWorkbook wb) {
        XSSFCellStyle style = wb.createCellStyle();

        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        return style;
    }

    /**
     * 获取表头样式
     */
    public static XSSFCellStyle getHeadStyle(XSSFWorkbook wb) {
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        return style;
    }

    /**
     * XSSFCell居中+边框样式
     *
     * @param wb
     * @return
     */
    public static XSSFCellStyle getCenterAndBorderStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        cellStyle.setAlignment(HorizontalAlignment.LEFT); // 居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        return cellStyle;
    }

    public static XSSFCellStyle getForeColorStyle(XSSFWorkbook wb, IndexedColors color) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(color.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    /**
     * 合并单元格并设置内容
     *
     * @param wb
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public static void mergeAndSetValue(XSSFWorkbook wb, XSSFSheet sheet, String value, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        Cell cell;
        XSSFRow row = sheet.getRow(firstRow);
        if (row != null) {
            cell = row.createCell(firstCol);
        } else {
            row = sheet.createRow(firstRow);
            cell = row.createCell(firstCol);
        }

        cell.setCellValue(value);
        cell.setCellStyle(ExcelUtil.getCenterAndBorderStyle(wb));
    }

    /**
     * 获取sheet页数据
     *
     * @param sheet
     * @return
     */
    public static List<Row> getRows(Sheet sheet, Integer start) {
        if (sheet == null) {
            return new ArrayList<>();
        }
        int last = sheet.getLastRowNum();
        if (last < start) {
            return new ArrayList<>();
        }
        List<Row> rows = new ArrayList<>(last + 1);
        for (int i = start; i <= last; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                rows.add(row);
            }
        }
        return rows;
    }

    /**
     * 最兼容的！为sheet页设置下拉选,重要事情说三遍！
     * “必须”已包含，一个储存下拉选的隐藏页！
     * “必须”已包含，一个储存下拉选的隐藏页！
     * “必须”已包含，一个储存下拉选的隐藏页！
     *
     * @param wb
     * @param sheetIndex 需要设置下拉选的sheet页Index
     * @param hiddenName 设置的隐藏数据sheet页名称
     * @param colEnum    下拉选数据，来源列
     * @param strs       下拉选内容
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public static void setDropDownOption(Workbook wb, Integer sheetIndex, String hiddenName, String areaName, ExcelColEnum colEnum, List<String> strs,
                                         Integer firstRow, Integer lastRow, Integer firstCol, Integer lastCol) {

        if (wb instanceof XSSFWorkbook) {
            //根据选中列插入数据
            XSSFSheet hidden = (XSSFSheet) wb.getSheet(hiddenName);
            bindData(colEnum, strs, hidden);

            XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(sheetIndex);
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
            //设置下拉选区域
            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
            //设置数据选择区域
            Name categoryName = wb.createName();
            categoryName.setNameName(areaName);
            categoryName.setRefersToFormula(hiddenName + "!$" + colEnum.name() + "$1:$" + colEnum.name() + "$" + strs.size());
            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                    .createFormulaListConstraint(areaName);
            //设置下拉选，及数据校验
            XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);

        } else if (wb instanceof HSSFWorkbook) {
            //根据选中列插入数据
            XSSFSheet hidden = (XSSFSheet) wb.getSheet(hiddenName);
            bindData(colEnum, strs, hidden);
            HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(sheetIndex);
            //设置下拉选区域
            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
            //设置数据选择区域
            Name categoryName = wb.createName();
            categoryName.setNameName(areaName);
            categoryName.setRefersToFormula(hiddenName + "!$" + colEnum.name() + "$1:$" + colEnum.name() + "$" + strs.size());
            DVConstraint dvConstraint = DVConstraint
                    .createFormulaListConstraint(areaName);
            //设置下拉选，及数据校验
            DataValidation validation = new HSSFDataValidation(addressList, dvConstraint);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);
        }
    }

    private static void bindData(ExcelColEnum colEnum, List<String> strs, XSSFSheet hidden) {
        for (int i = 0; i < strs.size(); i++) {
            Row row = hidden.getRow(i);
            if (row == null) {
                hidden.createRow(i).createCell(colEnum.getIndex()).setCellValue(strs.get(i));
            } else {
                row.createCell(colEnum.getIndex()).setCellValue(strs.get(i));
            }
        }
    }

    public static Map<String, String> getExcelInfo(Integer excelType) {
        //1：EPIC；2：FEATURE，3：STORY
        Map<String, String> map = new HashMap<>();
        switch (excelType) {
            case 1:
                map.put(IMPORT_TEMPLATE, EPIC_IMPORT_TEMPLATE);
                map.put(EXPORT_TEMPLATE, EPIC_EXPORT_TEMPLATE);
                map.put(SHEET_NAME, EPIC_SHEET);
                break;
            case 2:
                map.put(IMPORT_TEMPLATE, FEATURE_IMPORT_TEMPLATE);
                map.put(EXPORT_TEMPLATE, FEATURE_EXPORT_TEMPLATE);
                map.put(SHEET_NAME, featureSheet);
                break;
            case 3:
                map.put(IMPORT_TEMPLATE, STORY_IMPORT_TEMPLATE);
                map.put(EXPORT_TEMPLATE, STORY_EXPORT_TEMPLATE);
                map.put(SHEET_NAME, storySheet);
                break;
            case 4:
                map.put(IMPORT_TEMPLATE, TASK_IMPORT_TEMPLATE);
                //map.put(EXPORT_TEMPLATE, STORY_EXPORT_TEMPLATE);
                map.put(SHEET_NAME, taskSheet);
                break;
            default:
                break;
        }
        return map;
    }

    /**
     * 设置response，写输出流
     *
     * @param response
     * @param fileName
     * @param wb
     * @throws IOException
     */
    public static void writeOutStream(HttpServletResponse response, @PathVariable String fileName, Workbook wb) throws IOException {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setCharacterEncoding("UTF-8");
        response.flushBuffer();
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        try {
            out.close();
        } catch (IOException e) {
            LOGGER.error("error:{}", e);
        }
    }

    /**
     * 判断字符串是否是数字
     *
     * @param value
     * @return
     */
    public static boolean isNumber(String value) {

        if (StringUtils.isEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        return pattern.matcher(value).matches();
    }

    /**
     * 判断字符串是否是正整数
     *
     * @param value
     * @return
     */
    public static boolean isPositiveInteger(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile(INTEGER_PATTERN);
        return pattern.matcher(value).matches();
    }

    /**
     * 判断是否最多只有一位小数
     *
     * @param value
     * @return
     */
    public static boolean isOneDecimal(String value) {
        boolean isOneDecimal = false;

        if (StringUtils.isEmpty(value) || !(isNumber(value))) {
            return isOneDecimal;
        }

        if (StringUtils.contains(value, ".")) {
            String declmalStr = StringUtils.substringAfterLast(value, ".");
            if (declmalStr.length() > 1) {
                return isOneDecimal;
            } else {
                isOneDecimal = true;
            }
        } else {
            isOneDecimal = true;
        }
        return isOneDecimal;
    }

    /**
     * 校验时间格式，例2019-01-01
     *
     * @param value
     * @return
     */
    public static boolean isMatchDate(String value) {
        if (value.matches("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-" +
                "(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-" +
                "(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置下拉列表
     */
    public static void setDataValidation(XSSFWorkbook workbook, Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol,
                                         int endCol, int hiddenSheetNum) {

        String hiddenSheetName = "hidden" + hiddenSheetNum;
        //将下拉框数据放到新的sheet里，然后excle通过新的sheet数据加载下拉框数据
        Sheet hidden = workbook.createSheet(hiddenSheetName);
        //创建单元格对象
        Cell cell = null;
        //遍历我们上面的数组，将数据取出来放到新sheet的单元格中
        for (int i = 0, length = textList.length; i < length; i++) {
            //取出数组中的每个元素
            String name = textList[i];
            //根据i创建相应的行对象（说明我们将会把每个元素单独放一行）
            Row row = hidden.createRow(i);
            //创建每一行中的第一个单元格
            cell = row.createCell(0);
            //然后将数组中的元素赋值给这个单元格
            cell.setCellValue(name);
        }


        // 创建名称，可被其他单元格引用
        Name namedCell = workbook.createName();
        namedCell.setNameName(hiddenSheetName);
        // 设置名称引用的公式
        namedCell.setRefersToFormula(hiddenSheetName + "!$A$1:$A$" + textList.length);
        //加载数据,将名称为hidden的sheet中的数据转换为List形式
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(hiddenSheetName);

        // 如果设置第一列的3-65534行为下拉列表
        // (3, 65534, 0, 0) ====> (起始行,结束行,起始列,结束列)
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 将设置下拉选的位置和数据的对应关系 绑定到一起
        DataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) workbook.getSheet(hiddenSheetName));
        DataValidationConstraint formulaListConstraint = dvHelper.createFormulaListConstraint(namedCell.getRefersToFormula());
        DataValidation dataValidation = dvHelper.createValidation(formulaListConstraint, regions);
        //将数据赋给下拉列表
        sheet.addValidationData(dataValidation);

        //将第sheet设置为隐藏
        workbook.setSheetHidden(hiddenSheetNum, true);
    }

    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static void addHead(String headName, Row row, int columnIndex) {
        row.createCell(columnIndex).setCellValue(headName);
    }

    /**
     * 获得单元格中内容，返回字符串格式
     *
     * @param cell
     * @return
     */
    public static String getValue(Cell cell) throws IOException {

        String result;
        if (cell == null) {
            return "";
        }
        switch (cell.getCellTypeEnum()) {
            // 数字类型
            case NUMERIC:
                // 处理日期格式、时间格式
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {// 时间
                        sdf = new SimpleDateFormat("HH:mm");
                    } else if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("yyyyMM")) {
                        sdf = new SimpleDateFormat("yyyyMM");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = DateUtil.getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    String valueStr = String.valueOf(value);
                    //int vi = (int) value;
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if ("General".equals(temp)) {
                        format.applyPattern("#");
                    }
                    if (StringUtils.endsWith(valueStr, ".0")) {
                        result = StringUtils.substringBefore(valueStr, ".");
                    } else {
                        result = Double.toString(value);
                    }
                    result = Double.toString(value);

                }
                break;
            // String类型
            case STRING:
                result = cell.getRichStringCellValue().toString();
                break;
            // 公式
            case FORMULA:
                cell.setCellType(CellType.NUMERIC);
                try {
                    if (DateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                        SimpleDateFormat sdf = null;
                        if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {// 时间
                            sdf = new SimpleDateFormat("HH:mm");
                        } else {// 日期
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                        }
                        Date date = cell.getDateCellValue();
                        result = sdf.format(date);
                    } else if (cell.getCellStyle().getDataFormat() == 58) {// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = DateUtil.getJavaDate(value);
                        result = sdf.format(date);
                    } else {
                        double value = cell.getNumericCellValue();
                        CellStyle style = cell.getCellStyle();
                        DecimalFormat format = new DecimalFormat();
                        String temp = style.getDataFormatString();
                        // 单元格设置成常规
                        if ("General".equals(temp)) {
                            format.applyPattern("#");
                        }
                        result = format.format(value);

                    }
                } catch (IllegalStateException e) {
                    result = formulaFault;
                }
                break;
            // 空值
            case _NONE:
                result = "";
                break;
            default:
                result = "";
                break;
        }
        if (result == null) {
            return "";
        }
        result = result.trim();

        // 处理横杠
        // if (StringUtils.equals(result, "-") || StringUtils.equals(result,
        // "—")
        // || StringUtils.equals(result, "——")
        // || StringUtils.equals(result, "--")) {
        // result = "";
        // }

        return result;
    }

    public static Workbook getWb(String fileName, FileInputStream fis) throws IOException {
        Workbook wb = null;
        if (ExcelUtil.is2003(fileName)) {
            wb = new HSSFWorkbook(fis);
        } else if (ExcelUtil.is2007(fileName)) {
            wb = new XSSFWorkbook(fis);
        }
        return wb;
    }

    public static CellStyle getMistakeStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    /**
     * 功能描述: 查询单元格数据
     *
     * @param cell
     * @return java.lang.String
     * @date 2021/2/10
     */
    public static String getStringData(Cell cell) {
        String cellValue = "";
        if (null != cell) {
            //判断数据类型
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    cellValue = cell.getStringCellValue().trim();
                    break;
                case NUMERIC:
                    cellValue = (Double)cell.getNumericCellValue() + "";
                    break;
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case FORMULA:
                    cellValue = cell.getCellFormula() + "";
                    break;
                case BLANK:
                    cellValue = null;
                    break;
                case ERROR:
                    cellValue = null;
                    break;
                default:
                    cellValue = null;
                    break;
            }
        }
        return cellValue;
    }
}

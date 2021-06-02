package com.yusys.agile.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.yusys.portal.util.excel.vo.ExcelVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 *  @Description: Excel操作工具类
 *  @author: zhao_yd
 *  @Date: 2021/5/21 9:12 上午
 *
 */

@Slf4j
public class ExcelUtil {
    /**
     * xls
     */
    public static final String XLS = ".xls";
    /**
     * xlsx
     */
    public static final String XLSX = ".xlsx";

    private ExcelUtil() {

    }

    /**
     * 读取Excel文件
     * @param inputStream 文件流
     * @param size  size 文件标题行数,读取时忽略标题
     * @return
     */
    public static List<List<String>> readExcel(InputStream inputStream, int size) {

        long startTime = System.currentTimeMillis();
        List<List<String>> list = new ArrayList<>();
        try {
            //todo 内存优化可以使用 ExcelListener
            List<LinkedHashMap> data = EasyExcel.read(inputStream).sheet().headRowNumber(size).doReadSync();
            for (LinkedHashMap map : data) {
                // 将该行数据添加至返回结果list
                list.add(convertRowDataToList(map));
            }
        } catch (Exception e) {
            log.info("解析excel失败{}", e);
        } finally {
            try {
                if (Optional.ofNullable(inputStream).isPresent()) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.info("IO流关闭异常", e);
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("已读取{}条数据,耗时{}ms", list.size(), endTime - startTime);
        return list;
    }


    /**
     * easyExcel读取文件LinkHashMap转List(循环当前行每列参数)
     * @param map
     * @return
     */
    public static List<String> convertRowDataToList(Map map) {

        if (null == map) {
            return new ArrayList<>();
        }
        List<String> rowData = new ArrayList<>();
        int size = map.size();
        // 将该行第line列数据添加至该行数据list
        for (int line = 0; line < size; line++) {
            String lineValue = null == map.get(line) ? null : map.get(line).toString();
            rowData.add(lineValue);
        }

        return rowData;
    }

    /**
     * 导出工具类
     * @param vo
     * @param response
     */
    public static void export(ExcelVo vo, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        ExcelWriter excelWriter = null;
        try {
            // 文件名
            String fileName = vo.getFileName() + vo.getFileType();

            // 通过fileName创建一个Excel工作簿
            excelWriter = EasyExcel.write(dealResponse(fileName, response)).build();

            // 导出数据list
            List<List<String>> exportDataList = new ArrayList<>();

            // Sheet编号
            int sheetNo = 0;

            log.info("需导出条数:{}", vo.getStringList().size());
            sheetNo = exportByList(excelWriter, vo, exportDataList, sheetNo);

            write(excelWriter, vo, exportDataList, sheetNo);

            long endTime = System.currentTimeMillis();

            log.info("导出成功,耗时:{}ms", endTime - startTime);
        } catch (Exception e) {
            log.info("导出失败！", e);
        }finally {
            // 关闭操作流
            if(Optional.ofNullable(excelWriter).isPresent()){
                excelWriter.finish();
            }

        }
    }

    /**
     * 直接导出List
     *
     * @param excelWriter
     * @param vo
     * @param exportDataList
     * @param sheetNo
     * @return
     */
    public static int exportByList(ExcelWriter excelWriter, ExcelVo vo,
                                   List<List<String>> exportDataList, int sheetNo) {
        for (List<String> paramList : vo.getStringList()) {
            // 将该行数据加入到导出对象
            exportDataList.add(paramList);
            // 因2003版本excel导出条数限制,限制单个sheet导入条数为50000条,当导入50000条时新建sheet
            if (exportDataList.size() == 50000) {
                // 将前50000条数据导出list
                write(excelWriter, vo, exportDataList, sheetNo);
                // 清空已导出数据
                exportDataList.clear();
                // Sheet号加1操作
                sheetNo++;
            }
        }

        return sheetNo;
    }

    /**
     * 新建Sheet,并将导出数据exportDataList写入Sheet,最后将sheet写入Excel工作簿
     * @param excelWriter
     * @param vo
     * @param exportDataList
     * @param sheetNo
     */
    public static void write(ExcelWriter excelWriter, ExcelVo vo, List<List<String>> exportDataList, int sheetNo) {
        // 创建Sheet
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, "Sheet" + (sheetNo + 1)).build();

        // 设置首行标题
        convertCellText(writeSheet, vo.getCellText());

        try {
            // 设置列宽(根据前两行数据长度,设置对应列宽) 因自动适应列宽会遍历全部导出数据,当数据量过大时会影响效率且easyExcel封装的自动适应方法官方文档标明不精确
            setColumnWidth(writeSheet, vo.getCellText(), exportDataList);
        } catch (Exception e) {
            log.info("设置列宽异常", e);
        }

        // 将sheet写入Excel工作簿
        excelWriter.write(exportDataList, writeSheet);
    }


    /**
     * 设置首行标题
     * @param writeSheet
     * @param cellText
     */
    public static void convertCellText(WriteSheet writeSheet, List<String> cellText) {
        List<List<String>> headList = new ArrayList<>();

        for (String cell : cellText) {
            headList.add(Arrays.asList(cell));
        }

        if (CollectionUtils.isNotEmpty(headList)) {
            log.info("首行标题:{}", headList);
            writeSheet.setHead(headList);
        }
    }


    /**
     * 设置列宽
     * @param writeSheet
     * @param headList
     * @param lists
     * @throws UnsupportedEncodingException
     */
    public static void setColumnWidth(WriteSheet writeSheet, List<String> headList, List<List<String>> lists) throws UnsupportedEncodingException {
        // 列宽长度list
        List<Integer> byteLengthList = new ArrayList<>();

        List<String> dataList = new ArrayList<>();
        // 首行数据
        if (CollectionUtils.isNotEmpty(lists) && null != lists.get(0)) {
            dataList = lists.get(0);
        }

        int headListSize = headList.size();
        int dataListSize = dataList.size();

        // 取两行数据中最大长度作为遍历条件
        int length = headListSize > dataListSize ? headListSize : dataListSize;
        for (int i = 0; i < length; i++) {
            // 当前列-列宽
            Integer byteLength;

            int a = 0;
            // 当前列-首行标题字符串长度
            if (i < headListSize) {
                a = StringUtils.isNotBlank(headList.get(i)) ? headList.get(i).getBytes(StandardCharsets.UTF_8).length : 0;
            }

            int b = 0;
            // 当前列-首行数据字符串长度
            if (i < dataListSize) {
                b = StringUtils.isNotBlank(dataList.get(i)) ? dataList.get(i).getBytes(StandardCharsets.UTF_8).length : 0;
            }

            if (0 >= a && 0 >= b) {
                // 如果前两行单元格内容为空,该列默认字符串长度15
                byteLength = 15;
            } else {
                // 取前两行单元格内容最大长度值作为该列列宽
                byteLength = a > b ? a : b;
            }
            // 将该列列宽放入列宽长度list
            byteLengthList.add(byteLength);

        }

        log.info("导出时对应列宽:{}", byteLengthList);
        // 列编号
        Map<Integer, Integer> columnWidth = new HashMap<>();
        for (int line = 0; line < byteLengthList.size(); line++) {
            // 循环设置列宽
            columnWidth.put(line, (byteLengthList.get(line) + 5) * 256); // 在原单元格宽度基础上+5, 防止展示过于紧凑
        }

        writeSheet.setColumnWidthMap(columnWidth);
    }


    /**
     * response响应统一处理
     * @param fileName
     * @param response
     * @return
     * @throws IOException
     */
    public static OutputStream dealResponse(String fileName, HttpServletResponse response) throws IOException {

        response.reset();
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + XLSX);
        response.setCharacterEncoding("UTF-8");

        return response.getOutputStream();
    }




}

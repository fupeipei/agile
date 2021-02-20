package com.yusys.agile.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/** cmp_sync_result
 * 需要导入两张表
 * 1. issue 表
 *    该表中需要插入的字段：issue_id, title (需求主题)，issue_type (值为10)，state （值为 U），project_id （766673668150255616），create_uid （999988886666），
 *    cmp_sync_result （值为1），is_archive （值为1）
 * 2. sys_extend_field_detail 表
 *    该表中需要插入的字段：formalReqCode(局方需求编号) ,bjSource(需求类型), status(需求状态), topic(需求主题), ifKey (是否重点需求),
 *    relatedSystem (系统相关性) , planDeployDate （批次上线时间）该字段有可能为空，type （部署类型），planStates （需求计划状态），ifGroup （是否集团需求）
 *    groupAskLatestDevTime （集团要求最晚开发时间），groupAskLatestTestTime （集团要求最晚联调时间），groupAskLatestOnlineTime （集团要求最晚上线时间）
 */
public class BjGjExcelReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(BjGjExcelReader.class);
    private static final AtomicInteger id = new AtomicInteger(1); // 设置 issue_id 起始值
    private static final String outputPath = "D:/importExcel/sql_files/";
    private static final String EPCI_INSERT_SQL = "INSERT INTO req.issue(issue_id, title, create_time, issue_type, state, project_id, create_uid, cmp_sync_result, is_archive) VALUES (";
    private static final String SYS_EXTEND_FIELD_DETAIL_INSERT_SQL = "INSERT INTO req.sys_extend_field_detail(issue_id, field_id, field_name, value, state, create_uid) VALUES (";
    private static final long projectId = 766673668150255616L;
    private static final long createUid = 999988886666L;
    private static final int batchSize = 300;

    // 系统扩展表字段名
    private static final String formalReqCodeName = "正式需求编号（局方）";
    private static final String bjSourceName = "来源（需求类型）";
    private static final String statusName = "需求状态";
    private static final String topicName = "需求主题";
    private static final String ifKeyName = "是否是重点需求";
    private static final String relatedSystemName = "系统相关性";
    private static final String planDeployDateName = "部署批次";
    private static final String typeName = "部署类型";
    private static final String planStatesName = "需求计划状态";
    private static final String ifGroupName = "是否是集团需求";
    private static final String groupAskLatestDevTimeName = "集团要求最晚开发时间";
    private static final String groupAskLatestTestTimeName = "集团要求最晚联调时间";
    private static final String groupAskLatestOnlineTimeName = "集团要求最晚上线时间";

    // 系统扩展字段ID
    private static final String formalReqCode = "formalReqCode";
    private static final String bjSource = "bjSource";
    private static final String status = "status";
    private static final String topic = "topic";
    private static final String ifKey = "ifKey";
    private static final String relatedSystem = "relatedSystem";
    private static final String planDeployDate = "planDeployDate";
    private static final String type = "type";
    private static final String planStates = "planStates";
    private static final String ifGroup = "ifGroup";
    private static final String groupAskLatestDevTime = "groupAskLatestDevTime";
    private static final String groupAskLatestTestTime = "groupAskLatestTestTime";
    private static final String groupAskLatestOnlineTime = "groupAskLatestOnlineTime";


    // 系统扩展表字段导入列名索引编号
    private static final int formalReqCodeIndex = 1; // 局方需求编号
    private static final int bjSourceIndex = 3; // 需求类型
    private static final int statusIndex = 4; // 需求状态
    private static final int topicIndex = 5; // 需求主题
    private static final int ifKeyIndex = 6; // 是否重点需求
    private static final int relatedSystemIndex = 7; // 系统相关性
    private static final int planDeployDateIndex = 9; // 批次上线时间
    private static final int typeIndex = 12; // 部署类型
    private static final int planStatesIndex = 13; // 需求计划状态
    private static final int ifGroupIndex = 14; // 是否集团需求
    private static final int groupAskLatestDevTimeIndex = 15; // 集团要求最晚开发时间
    private static final int groupAskLatestTestTimeIndex = 16; // 集团要求最晚联调时间
    private static final int groupAskLatestOnlineTimeIndex = 17; // 集团要求最晚上线时间
    private static final int issueCreateTimeIndex = 18; //需求下发时间

    // 事务语句
    private static final String beginTransactionSql = "START TRANSACTION;";
    private static final String commitTransactionSql = "COMMIT;";

    public void  doRead(String excelPath) {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String sqlFileName = outputPath + dataFormat.format(new Date()) + ".sql";
        try {
            File excel = new File(excelPath);
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    LOGGER.info("文件类型错误!");
                    return;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
                LOGGER.info("firstRowIndex: "+firstRowIndex);
                LOGGER.info("lastRowIndex: "+lastRowIndex);

                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    // 增加事务拼接语句
                    if ( (rIndex - 1) % batchSize == 0) {
                        writeFileContent(sqlFileName,beginTransactionSql);
                    }
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        // 插入issue - epic 记录
                        int issue_id = id.getAndIncrement();
                        StringBuilder issueEpicBuilder = new StringBuilder(100);
                        issueEpicBuilder.append(EPCI_INSERT_SQL);
                        issueEpicBuilder.append(issue_id).append(",");
                        int firstCellIndex = row.getFirstCellNum(); // 从 0 开始
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列插入Epic扩展字段
                            StringBuilder sysExtendFieldBuilder = new StringBuilder(100);
                            sysExtendFieldBuilder.append(SYS_EXTEND_FIELD_DETAIL_INSERT_SQL);
                            // 过滤掉不需要解析的列
                            if (cIndex == 0 || cIndex == 2 || cIndex == 8 || cIndex == 10 || cIndex == 11) {
                                continue;
                            }

                            if (topicIndex == cIndex) {
                                //  Epic记录设置需求主题(title)
                                Cell cell = row.getCell(cIndex);
                                if (cell != null) {
                                    issueEpicBuilder.append("'").append(cell.getStringCellValue()).append("',");
                                } else {
                                    issueEpicBuilder.append("NULL").append(",");
                                }
                            } else if (issueCreateTimeIndex == cIndex) {
                                // Epic的下发时间
                                Cell cell = row.getCell(cIndex);
                                if (cell != null) {
                                    issueEpicBuilder.append("'").append(cell.getStringCellValue()).append("',");
                                } else {
                                    issueEpicBuilder.append("NULL").append(",");
                                }
                            }else {
                                Cell cell = row.getCell(cIndex);
                                if (cell != null) {
                                    sysExtendFieldBuilder.append(issue_id).append(",");
                                    // 生成系统扩展字段插入语句
                                    if (cIndex == formalReqCodeIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,formalReqCode,formalReqCodeName,cellValue,sqlFileName);
                                    } else if (cIndex == bjSourceIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,bjSource,bjSourceName,cellValue,sqlFileName);
                                    } else if (cIndex == statusIndex) {
                                        Double cellValue = cell.getNumericCellValue();
                                        String statusValue = String.valueOf(cellValue.intValue());
                                        writeSqlFile(sysExtendFieldBuilder,status, statusName,statusValue,sqlFileName);
                                    } else if (cIndex == ifKeyIndex) {
                                        Double cellValue = cell.getNumericCellValue();
                                        String ifKeyValue = String.valueOf(cellValue.intValue());
                                        writeSqlFile(sysExtendFieldBuilder,ifKey,ifKeyName,ifKeyValue,sqlFileName);
                                    } else if (cIndex == relatedSystemIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,relatedSystem,relatedSystemName,cellValue,sqlFileName);
                                    } else if (cIndex == planDeployDateIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,planDeployDate,planDeployDateName,cellValue,sqlFileName);
                                    } else if (cIndex == typeIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,type,typeName,cellValue,sqlFileName);
                                    } else if (cIndex == planStatesIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,planStates,planStatesName,cellValue,sqlFileName);
                                    } else if (cIndex == ifGroupIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,ifGroup,ifGroupName,cellValue,sqlFileName);
                                    } else if (cIndex == groupAskLatestDevTimeIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,groupAskLatestDevTime,groupAskLatestDevTimeName,cellValue,sqlFileName);
                                    } else if (cIndex == groupAskLatestTestTimeIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,groupAskLatestTestTime,groupAskLatestTestTimeName,cellValue,sqlFileName);
                                    } else if (cIndex == groupAskLatestOnlineTimeIndex) {
                                        String cellValue = cell.getStringCellValue();
                                        writeSqlFile(sysExtendFieldBuilder,groupAskLatestOnlineTime,groupAskLatestOnlineTimeName,cellValue,sqlFileName);
                                    }
                                }
                            }
                        }
                        // 生产 Epic 插入语句
                        issueEpicBuilder.append(10).append(","); // issue_type
                        issueEpicBuilder.append("'E'").append(","); // state
                        issueEpicBuilder.append(projectId).append(","); // project_id
                        issueEpicBuilder.append(createUid).append(","); // create_uid
                        issueEpicBuilder.append(1).append(","); // cmp_sync_result
                        issueEpicBuilder.append(1).append(");"); // is_archive
                        String insertEpicSql = issueEpicBuilder.toString();
                        writeFileContent(sqlFileName,insertEpicSql);
                        if (rIndex % batchSize == 0) {
                            writeFileContent(sqlFileName,commitTransactionSql);
                        }
                    }
                }
                writeFileContent(sqlFileName,commitTransactionSql);
            } else {
                LOGGER.info("找不到指定的文件: " +  excelPath);
                return;
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        LOGGER.info("SQL文件生成完毕");
    }

    private void writeSqlFile(StringBuilder sysExtendFieldBuilder, String fieldId, String fieldName, String value, String sqlFileName) {
        String insertSysExtendFieldSql = createSysExtendFieldSqlContent(sysExtendFieldBuilder,fieldId,fieldName,value);
        writeFileContent(sqlFileName,insertSysExtendFieldSql);
    }

    private String createSysExtendFieldSqlContent(StringBuilder sysExtendFieldBuilder, String fieldId, String fieldName, String value) {
        sysExtendFieldBuilder.append("'").append(fieldId).append("',");
        sysExtendFieldBuilder.append("'").append(fieldName).append("',");
        sysExtendFieldBuilder.append("'").append(value).append("',");
        sysExtendFieldBuilder.append("'E'").append(",");
        sysExtendFieldBuilder.append(createUid).append(");");
        return sysExtendFieldBuilder.toString();
    }

    private void writeFileContent(String file, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
            out.write(content + "\r\n");
        }catch (Exception e) {
            LOGGER.info(e.getMessage());
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.info(e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        String excelPath = "D:/importExcel/jufang/局方有DMP中没有的需求.xlsx";
        BjGjExcelReader excelReader = new BjGjExcelReader();
        excelReader.doRead(excelPath);
    }
}


package com.yusys.agile.excel.service.impl;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.excel.domain.CheckResult;
import com.yusys.agile.excel.domain.ExcelIssue;
import com.yusys.agile.excel.domain.Mistake;
import com.yusys.agile.excel.domain.MistakeDesc;
import com.yusys.agile.excel.enums.ExcelFieldEnum;
import com.yusys.agile.excel.service.ExcelService;
import com.yusys.agile.externalapiconfig.dao.ExternalApiConfigMapper;
import com.yusys.agile.fault.domain.ExternalApiConfig;
import com.yusys.agile.fault.domain.ExternalApiConfigExample;
import com.yusys.agile.file.domain.FileInfo;
import com.yusys.agile.file.service.FileService;
import com.yusys.agile.headerfield.domain.HeaderField;
import com.yusys.agile.headerfield.enums.FieldTypeEnum;
import com.yusys.agile.headerfield.enums.IsCustomEnum;
import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.domain.IssueSystemRelp;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueListDTO;
import com.yusys.agile.issue.dto.IssueStringDTO;
import com.yusys.agile.issue.enums.*;
import com.yusys.agile.issue.service.*;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.sysextendfield.SysExtendFieldDetailDTO;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.service.SysExtendFieldDetailService;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.domain.Sprint;
import com.yusys.agile.sprint.domain.SprintExample;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.service.SprintService;
import com.yusys.agile.utils.DateTools;
import com.yusys.agile.utils.ExcelUtil;
import com.yusys.agile.utils.ReflectObjectUtil;
import com.yusys.agile.utils.StringUtil;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * excel导入导出业务实现
 *
 * @create 2021/2/1
 */
@Service("excelService")
public class ExcelServiceImpl implements ExcelService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private EpicService epicService;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FileService fileService;

    @Autowired
    private IssueMapper issueMapper;

    @Autowired
    private SprintMapper sprintMapper;

    @Autowired
    private IssueSystemRelpService issueSystemRelpService;

    private static final String[] EPIC_HEAD_LINE = {"*需求名称", "需求描述", "迭代", "系统", "开始日期", "结束日期", "预计工时", "需求优先级", "重要程度", "业务价值"};

    private static final String[] FEATURE_HEAD_LINE = {"*需求名称", "需求描述", "迭代", "系统", "父工作项", "开始日期", "结束日期", "预计工时", "需求优先级", "重要程度", "业务价值"};

    private static final String[] STORY_HEAD_LINE = {"*故事名称", "故事描述", "迭代", "系统", "父工作项", "开始日期", "结束日期", "预计工时", "需求优先级", "重要程度", "业务价值"};
    private static final String[] TASK_HEAD_LINE = {"*故事ID", "*任务标题", "任务描述", "*任务类型", "预计工时"};
    private static final int FIRST_ROW_NUM = 1;
    private static final int REQ_NAME_LENGTH = 20;
    private static final int ORI_REQID_LENGTH = 50;

    /**
     * 全量导出
     */
    private static final Byte ALL_TYPE = 1;

    /**
     * 局部导出
     */
    private static final Byte PART_TYPE = 2;

    /**
     * 工作项导出阈值
     */
    private static final String MAX_ISSUE_EXPORT_THRESHOLD_KEY = "ISSUE_MAX_EXPORT_THRESHOLD";

    @Autowired
    private HeaderFieldService headerFieldService;

    @Autowired
    private ExternalApiConfigMapper externalApiConfigMapper;

    @Autowired
    private IssueFactory issueFactory;

    @Autowired
    private SysExtendFieldDetailService sysExtendFieldDetailService;


    /**
     * 功能描述:下载模版
     *
     * @param excelType IssueTypeEnum epic:1 feature:2 story:3
     * @param projectId
     * @param request
     * @param response
     * @return void
     * @date 2021/2/1
     */
    @Override
    public void downloadTemplateByExcelType(Byte excelType, Long projectId, Long sprintId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String> map = ExcelUtil.getExcelInfo(excelType.intValue());
        String templateName = map.get("importTemplate");
        String sheetName = map.get("sheetName");
        String filePath = File.separator + templateName;
        Resource resource = resourceLoader.getResource("classpath:excelTemplate" + filePath);
        XSSFWorkbook wb = new XSSFWorkbook(resource.getInputStream());

        Sheet sheet = wb.getSheet(sheetName);
        int hiddenSheetNum = 1;
        // 任务模版
        if (IssueTypeEnum.TYPE_TASK.CODE.equals(excelType)) {
            // 故事id
            String[] storyNames = getStoryNamesBySprintId(sprintId);
            if (storyNames != null && storyNames.length > NumberConstant.ZERO) {
                hiddenSheetNum++;
                ExcelUtil.setDataValidation(wb, sheet, storyNames, NumberConstant.ONE, 1000, 0, 0, hiddenSheetNum);
            }
            // 任务类型
            String[] taskTypeNames = TaskTypeEnum.getNames();
            if (taskTypeNames != null && taskTypeNames.length > NumberConstant.ZERO) {
                hiddenSheetNum++;
                ExcelUtil.setDataValidation(wb, sheet, taskTypeNames, NumberConstant.ONE, 1000, 3, 3, hiddenSheetNum);
            }

            // TODO 下载模版带上自定义字段


        } else {
            // 系统名
            String[] systemNames = getSystemNamesByProjectId(projectId);
            if (systemNames != null && systemNames.length > NumberConstant.ZERO) {
                hiddenSheetNum++;
                ExcelUtil.setDataValidation(wb, sheet, systemNames, NumberConstant.ONE, 1000, 3, 3, hiddenSheetNum);
            }
            // 迭代名
            String[] sprintNames = getUnFinishedSprintNamesByProjectId(projectId);
            if (sprintNames != null && sprintNames.length > NumberConstant.ZERO) {
                hiddenSheetNum++;
                ExcelUtil.setDataValidation(wb, sheet, sprintNames, NumberConstant.ONE, 1000, 2, 2, hiddenSheetNum);
            }
            // 研发需求和业务需求需要增加父工作项
            if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(excelType) || IssueTypeEnum.TYPE_STORY.CODE.equals(excelType)) {
                //父工作项
                List<String> parentIssueNameList = issueService.getTemplateParentIssueList(projectId, excelType);
                if (CollectionUtils.isNotEmpty(parentIssueNameList)) {
                    String[] parentIssueNames = new String[parentIssueNameList.size()];
                    parentIssueNameList.toArray(parentIssueNames);
                    hiddenSheetNum++;
                    ExcelUtil.setDataValidation(wb, sheet, parentIssueNames, NumberConstant.ONE, 1000, 4, 4, hiddenSheetNum);
                }
            }
        }
//
//        LOGGER.info("关闭隐藏sheet的hiddenSheetNum={}",hiddenSheetNum);
//        for(int i=2;i<=hiddenSheetNum;i++){
//            //将第sheet设置为隐藏
//            wb.setSheetHidden(hiddenSheetNum, true);
//        }

        ExcelUtil.writeOutStream(response, URLEncoder.encode(templateName, "UTF-8"), wb);
    }


    /**
     * 功能描述: excel导入
     *
     * @param excelType
     * @param projectId
     * @param multiReq
     * @param request
     * @param response
     * @return
     * @date 2021/2/1
     */
    @Override
    public ControllerResponse importExcel(Byte excelType, Long projectId, Long sprintId, MultipartHttpServletRequest multiReq, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartFile file = multiReq.getFile("file");
        // upload
        String fileName = file.getOriginalFilename();
        fileName = new String(fileName.getBytes(), "UTF-8");
        fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        Sheet sheet = null;
        Workbook wb = null;
        FileInputStream fis = (FileInputStream) file.getInputStream();
        if (ExcelUtil.isExcel2003(fileName)) {
            wb = new HSSFWorkbook(fis);
        } else if (ExcelUtil.isExcel2007(fileName)) {
            wb = new XSSFWorkbook(fis);
        }
        Map<String, String> map = ExcelUtil.getExcelInfo(excelType.intValue());
        if (wb != null) {
            sheet = wb.getSheet(map.get("sheetName"));
        }
        if (sheet != null && sheet.getLastRowNum() > NumberConstant.ZERO) {
            // 校验列表头
            CheckResult check = checkFormat(projectId, sprintId, sheet, excelType);
            if (!check.isTemplate()) {
                wb.close();
                return ControllerResponse.fail("模板表头结构被更改，请重新下载模板!");
            }
            if (CollectionUtils.isNotEmpty(check.getMistakes())) {
                //向excel表格中写入错误
                fileName = UUID.randomUUID() + ".xlsx";
                // 把错误内容写进excel并下载
                List<Mistake> mistakes = check.getMistakes();
                FileInfo fileInfo = writeMistakeToExcel(wb, mistakes, fileName, excelType);
                return ControllerResponse.fail(fileInfo);
            }
            // 将excel内容入库
            importExcelToDb(sheet, projectId, sprintId, excelType);
            return ControllerResponse.success("EXCEL导入成功");
        } else {
            if (wb != null) {
                wb.close();
            }
            return ControllerResponse.fail("文件内容为空，请重新上传!");
        }
    }

    /**
     * @param mistakes
     * @param fileName
     * @param excelType
     * @return FileResult
     * @descrption 功能描述: 将错误信息回写到Excel
     * @date 2021/2/1
     */
    private FileInfo writeMistakeToExcel(Workbook wb, List<Mistake> mistakes, String fileName, int excelType) throws Exception {
        Map<String, String> map = ExcelUtil.getExcelInfo(excelType);
        if (CollectionUtils.isNotEmpty(mistakes)) {
            for (Mistake mistake : mistakes) {
                if (null == mistake) {
                    continue;
                }
                Sheet sheet = wb.getSheet(map.get("sheetName"));
                Row row = sheet.getRow(mistake.getRowIndex());
                int cellNum = mistake.getColumnIndex();
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(mistake.getDescribe());
                CellStyle style = wb.createCellStyle();
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        byte[] bytes = baos.toByteArray();
        //ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        FileItem fileItem = getFileItem(fileName, bytes);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return fileService.upload(multipartFile);
    }

    /**
     * @param fileName
     * @param bytes
     * @return
     * @description 获取FileItem
     * @date 2021/2/1
     */
    private FileItem getFileItem(String fileName, byte[] bytes) {
        FileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        FileItem fileItem = diskFileItemFactory.createItem(fileName, "text/plain", true, fileName);
        try (OutputStream outputStream = fileItem.getOutputStream()) {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return fileItem;
    }

    /**
     * 功能描述: 数据入库
     *
     * @param sheet
     * @param projectId
     * @param excelType
     * @return void
     * @date 2021/2/1
     */
    private void importExcelToDb(Sheet sheet, Long projectId, Long sprintId, Byte excelType) {
        int last = sheet.getLastRowNum() + 1;

        for (int i = FIRST_ROW_NUM; i < last; i++) {
            Row currentRow = sheet.getRow(i);
            IssueDTO issueDTO = assembleIssueDtoByExcelType(currentRow, projectId, sprintId, excelType);
            issueDTO.setBatchCreate(true);
            switch (excelType) {
                case 1:
                    epicService.createEpic(issueDTO);
                    break;
                case 2:
                    featureService.createFeature(issueDTO);
                    break;
                case 3:
                    storyService.createStory(issueDTO);
                    break;
                case 4:
                    taskService.createTask(issueDTO);
                    break;
                default:
                    break;
            }

        }


    }

    /**
     * 功能描述: 根据类型组装入库对象
     *
     * @param currentRow
     * @param projectId
     * @param excelType
     * @return com.yusys.agile.issue.dto.IssueDTO
     * @date 2021/2/1
     */
    private IssueDTO assembleIssueDtoByExcelType(Row currentRow, Long projectId, Long sprintId, Byte excelType) {

        IssueDTO issueDTO = new IssueDTO();
        int i = 0;
        issueDTO.setProjectId(projectId);


        if (IssueTypeEnum.TYPE_TASK.CODE.equals(excelType)) {
            issueDTO.setSprintId(sprintId);
            issueDTO.setStageId(TaskStatusEnum.TYPE_ADD_STATE.CODE);
            // 故事id
            String storyStr = StringUtils.substringBefore(ExcelUtil.getStringData(currentRow.getCell(i)), "+");
            issueDTO.setParentId(StringUtils.isNotBlank(storyStr) ? Long.valueOf(storyStr) : null);
            // 任务标题
            issueDTO.setTitle(ExcelUtil.getStringData(currentRow.getCell(++i)));
            // 任务描述
            issueDTO.setDescription(ExcelUtil.getStringData(currentRow.getCell(++i)));
            // 任务类型
            String taskTypeStr = ExcelUtil.getStringData(currentRow.getCell(++i));
            issueDTO.setTaskType(StringUtils.isNotBlank(taskTypeStr) ? TaskTypeEnum.getCode(taskTypeStr) : null);
            // 预计工时
            String planWorkStr = ExcelUtil.getStringData(currentRow.getCell(++i));
            issueDTO.setPlanWorkload(StringUtils.isNotBlank(planWorkStr) ? Integer.parseInt(planWorkStr) : null);
        } else {
            // 名称
            issueDTO.setTitle(ExcelUtil.getStringData(currentRow.getCell(i)));
            // 描述
            issueDTO.setDescription(ExcelUtil.getStringData(currentRow.getCell(++i)));
            // 迭代
            String sprintStr = StringUtils.substringBefore(ExcelUtil.getStringData(currentRow.getCell(++i)), "+");
            issueDTO.setSprintId(StringUtils.isNotBlank(sprintStr) ? Long.valueOf(sprintStr) : null);
            // 系统
            String systemStr = StringUtils.substringBefore(ExcelUtil.getStringData(currentRow.getCell(++i)), "+");
            Long systemId = StringUtils.isNotBlank(systemStr) ? Long.valueOf(systemStr) : null;
            issueDTO.setSystemId(StringUtils.isNotBlank(systemStr) ? Long.valueOf(systemStr) : null);
            // 系统可能是多个，所以要塞dto的list
            if (null != systemId) {
                List<Long> systemIds = Lists.newArrayList();
                systemIds.add(systemId);
                issueDTO.setSystemIds(systemIds);
            }

            // 父工作项
            if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(excelType) || IssueTypeEnum.TYPE_STORY.CODE.equals(excelType)) {
                String parentStr = StringUtils.substringBefore(ExcelUtil.getStringData(currentRow.getCell(++i)), "+");
                issueDTO.setParentId(StringUtils.isNotBlank(parentStr) ? Long.valueOf(parentStr) : null);
            }
            // 开始日期
            issueDTO.setBeginDate(getDate(currentRow.getCell(++i)));
            // 结束日期
            issueDTO.setEndDate(getDate(currentRow.getCell(++i)));
            // 预计工时
            String planWorkStr = ExcelUtil.getStringData(currentRow.getCell(++i));
            issueDTO.setPlanWorkload(StringUtils.isNotBlank(planWorkStr) ? Integer.parseInt(planWorkStr) : null);
            // 需求优先级
            issueDTO.setPriority(IssuePriorityEnum.getCode(ExcelUtil.getStringData(currentRow.getCell(++i))));
            // 重要程度
            issueDTO.setImportance(IssueImportanceEnum.getCode(ExcelUtil.getStringData(currentRow.getCell(++i))));
            // 业务价值
            String orderStr = ExcelUtil.getStringData(currentRow.getCell(++i));
            issueDTO.setOrder(StringUtils.isNotBlank(orderStr) ? Integer.parseInt(orderStr) : null);

            // --验收标准 只有故事才有验收标准--
//            if(IssueTypeEnum.TYPE_STORY.CODE.equals(excelType)) {
//                String acceptanceStr = ExcelUtil.getStringData(currentRow.getCell(++i));
//                if (StringUtils.isNotBlank(acceptanceStr)) {
//                    String[] acceptanceArr = StringUtils.split(acceptanceStr, "\n");
//                    List<IssueAcceptanceDTO> issueAcceptanceDTOS = Lists.newArrayList();
//                    for (String temp : acceptanceArr) {
//                        IssueAcceptanceDTO issueAcceptanceDTO = new IssueAcceptanceDTO();
//                        issueAcceptanceDTO.setIsSelect(new Byte("0"));
//                        issueAcceptanceDTO.setStandard(temp);
//                        issueAcceptanceDTOS.add(issueAcceptanceDTO);
//                    }
//                    issueDTO.setIssueAcceptanceDTOS(issueAcceptanceDTOS);
//                }
//
//            }
            // 默认初始状态
            Long[] stages = {StageConstant.FirstStageEnum.READY_STAGE.getValue()};
            issueDTO.setStages(stages);
            // todo 自定义字段
        }


        return issueDTO;
    }

    private Date getDate(Cell cell) {
        Date finishTime = new Date();
        if (cell == null) {
            return null;
        }
        if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
            finishTime = cell.getDateCellValue();
        } else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateStr = cell.getStringCellValue();
            try {
                finishTime = sdf.parse(dateStr);
            } catch (ParseException e) {
                LOGGER.error("DataParseException{}", e.getMessage());
            }
        }
        return finishTime;
    }


    /**
     * 功能描述: 格式校验
     *
     * @param projectId
     * @param sheet
     * @param excelType
     * @return com.yusys.agile.excel.domain.CheckResult
     * @date 2021/2/1
     */
    private CheckResult checkFormat(Long projectId, Long sprintId, Sheet sheet, Byte excelType) {
        CheckResult check = new CheckResult();
        if (!checkHeadLine(projectId, sheet.getRow(NumberConstant.ZERO), excelType)) {
            check.setTemplate(false);
            return check;
        } else {
            check.setTemplate(true);
        }
        //创建错误集合
        List<Mistake> mistakes = checkFormatMistake(sheet, projectId, sprintId, excelType);
        if (CollectionUtils.isNotEmpty(mistakes)) {
            check.setMistakes(mistakes);
        }
        return check;
    }


    /**
     * 功能描述: 格式校验
     *
     * @param sheet
     * @param projectId
     * @return java.util.List<com.yusys.agile.excel.domain.Mistake>
     * @date 2021/2/1
     */
    private List<Mistake> checkFormatMistake(Sheet sheet, Long projectId, Long sprintId, Byte excelType) {
        List<Mistake> mistakes = new ArrayList<>();
        // 任务导入特殊处理
        if (IssueTypeEnum.TYPE_TASK.CODE.equals(excelType)) {
            mistakes = checkFormatForTask(sheet, projectId, sprintId);
        } else {
            mistakes = checkFormatForEpicAndFeatueAndStory(sheet, projectId, excelType);
        }
        return mistakes;
    }

    /**
     * 功能描述: task格式校验
     *
     * @param sheet
     * @param projectId
     * @return java.util.List<com.yusys.agile.excel.domain.Mistake>
     * @date 2021/2/1
     */
    private List<Mistake> checkFormatForTask(Sheet sheet, Long projectId, Long sprintId) {
        List<Mistake> mistakes = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum() + 1;
        for (int i = FIRST_ROW_NUM; i < lastRowNum; i++) {
            Cell cell;
            StringBuilder mistakeDesc = new StringBuilder();
            Mistake mistake;
            Row row = sheet.getRow(i);
            if (row == null) {
                sheet.createRow(i);
                mistake = new Mistake();
                mistake.setRowIndex(i);
                mistakeDesc.append("导入文件中间行不可为空");
                addAndReversMistake(mistakes, mistake, mistakeDesc);
                continue;
            }

            // 故事ID
            int j = 0;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            String cellVal = ExcelUtil.getStringData(cell);
            if (cellVal == null || cellVal.isEmpty()) {
                mistakeDesc.append("故事ID" + MistakeDesc.NOTNULLHINT);
            }
            // 不在下拉选择中
            if (cell != null && StringUtils.isNotBlank(cellVal) && !checkDataValue(cellVal, getStoryNamesBySprintId(sprintId))) {
                mistakeDesc.append("故事ID" + MistakeDesc.NOT_IN_SELECT_AND_TEMPLATE);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);

            // 任务标题
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cellVal == null || cellVal.isEmpty()) {
                mistakeDesc.append("任务标题" + MistakeDesc.NOTNULLHINT);
            }
            if (cellVal != null && !cellVal.isEmpty() && cellVal.length() > 200) {
                mistakeDesc.append("任务标题" + MistakeDesc.TITTLEBIGTHINT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);

            // 任务描述 咱不校验
            j++;

            // 任务类型
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cellVal == null || cellVal.isEmpty()) {
                mistakeDesc.append("任务类型" + MistakeDesc.NOTNULLHINT);
            }
            // 不在下拉选择中
            if (cell != null && StringUtils.isNotBlank(cellVal) && !checkDataValue(cellVal, TaskTypeEnum.getNames())) {
                mistakeDesc.append("任务类型" + MistakeDesc.NOT_IN_SELECT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);

            // 预计工时
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cell != null && StringUtils.isNotBlank(cellVal) && (!CellType.NUMERIC.equals(cell.getCellTypeEnum()) || !StringUtil.isPureDigital(cellVal))) {
                mistakeDesc.append("预计工时" + MistakeDesc.PURE_DIGITAL);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);

        }
        return mistakes;
    }

    /**
     * 功能描述: epic、feature、story格式校验
     *
     * @param sheet
     * @param projectId
     * @return java.util.List<com.yusys.agile.excel.domain.Mistake>
     * @date 2021/2/1
     */
    private List<Mistake> checkFormatForEpicAndFeatueAndStory(Sheet sheet, Long projectId, Byte excelType) {
        List<Mistake> mistakes = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum() + 1;
        // epic Featue story模版相近，共用相同的
        // 遍历校验
        for (int i = FIRST_ROW_NUM; i < lastRowNum; i++) {
            Cell cell;
            StringBuilder mistakeDesc = new StringBuilder();
            Mistake mistake;
            Row row = sheet.getRow(i);
            if (row == null) {
                sheet.createRow(i);
                mistake = new Mistake();
                mistake.setRowIndex(i);
                mistakeDesc.append("导入文件中间行不可为空");
                addAndReversMistake(mistakes, mistake, mistakeDesc);
                continue;
            }
            // 需求标题
            int j = 0;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            String cellVal = ExcelUtil.getStringData(cell);
            if (cellVal == null || cellVal.isEmpty()) {
                mistakeDesc.append("需求标题" + MistakeDesc.NOTNULLHINT);
            }
            if (cellVal != null && !cellVal.isEmpty() && cellVal.length() > 200) {
                mistakeDesc.append("需求标题" + MistakeDesc.TITTLEBIGTHINT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);
            // 需求描述 暂不校验
            j++;
            // 迭代,校验必须是下拉框中的
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            // 不在下拉选择中
            if (cell != null && StringUtils.isNotBlank(cellVal) && !checkDataValue(cellVal, getUnFinishedSprintNamesByProjectId(projectId))) {
                mistakeDesc.append("迭代" + MistakeDesc.NOT_IN_SELECT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);
            // 系统，必须在下拉选择中
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cell != null && StringUtils.isNotBlank(cellVal) && !checkDataValue(cellVal, getSystemNamesByProjectId(projectId))) {
                mistakeDesc.append("系统" + MistakeDesc.NOT_IN_SELECT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);
            // 研发需求和故事要多一列父工作项
            if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(excelType) || IssueTypeEnum.TYPE_STORY.CODE.equals(excelType)) {
                j++;
                cell = row.getCell(j);
                mistake = new Mistake(i, j);
                mistakeDesc = new StringBuilder();
                cellVal = ExcelUtil.getStringData(cell);
                List<String> list = issueService.getTemplateParentIssueList(projectId, excelType);
                if (cell != null && StringUtils.isNotBlank(cellVal) && !checkDataValue(cellVal, list.toArray(new String[list.size()]))) {
                    mistakeDesc.append("父工作项" + MistakeDesc.NOT_IN_SELECT);
                }
                addAndReversMistake(mistakes, mistake, mistakeDesc);
            }
            // 开始时间
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cell != null && StringUtils.isNotBlank(cellVal) && !isValidDate(cell)) {
                mistakeDesc.append("开始时间" + MistakeDesc.DATEFORMATHINT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);
            // 结束时间
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cell != null && StringUtils.isNotBlank(cellVal) && !isValidDate(cell)) {
                mistakeDesc.append("结束时间" + MistakeDesc.DATEFORMATHINT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);
            // 预计工时 必须是正整数
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cell != null && StringUtils.isNotBlank(cellVal) && (!CellType.NUMERIC.equals(cell.getCellTypeEnum()) || !StringUtil.isPureDigital(cellVal))) {
                mistakeDesc.append("预计工时" + MistakeDesc.PURE_DIGITAL);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);
            // 需求优先级
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cell != null && StringUtils.isNotBlank(cellVal) && null == IssuePriorityEnum.getCode(cellVal)) {
                mistakeDesc.append("需求优先级" + MistakeDesc.NOT_IN_SELECT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);
            // 重要程度
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cell != null && StringUtils.isNotBlank(cellVal) && null == IssueImportanceEnum.getCode(cellVal)) {
                mistakeDesc.append("重要程度" + MistakeDesc.NOT_IN_SELECT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);
            // 业务价值
            j++;
            cell = row.getCell(j);
            mistake = new Mistake(i, j);
            mistakeDesc = new StringBuilder();
            cellVal = ExcelUtil.getStringData(cell);
            if (cell != null && StringUtils.isNotBlank(cellVal)
                    && (!CellType.NUMERIC.equals(cell.getCellTypeEnum()) || !StringUtil.isPureDigital(cellVal) || !checkServiceValue(cell.getNumericCellValue()))
            ) {
                mistakeDesc.append("业务价值" + MistakeDesc.FUNCTIONSCALFORMATHINT);
            }
            addAndReversMistake(mistakes, mistake, mistakeDesc);
        }

        return mistakes;
    }

    /**
     * 功能描述: 判断单元格中数据是否是在下拉选择中
     *
     * @param value
     * @param values
     * @return boolean
     * @date 2021/2/1
     */
    private boolean checkDataValue(String value, String[] values) {
        if (null == values || values.length == 0) {
            return false;
        }

        for (String date : values) {
            if (value.equals(date)) {

                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述:业务价值只能在1-100之间
     *
     * @param value
     * @return boolean
     * @date 2021/2/1
     */
    private boolean checkServiceValue(double value) {
        int level = (int) value;
        return level > 0 && level < 101;
    }

    /**
     * 功能描述:校验时间格式 yyyy/mm/dd
     *
     * @param cell
     * @return boolean
     * @date 2021/2/1
     */
    private boolean isValidDate(Cell cell) {
        boolean isValidDate = false;
        CellType cellType = cell.getCellTypeEnum();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String sDate = simpleDateFormat.format(new Date());
        Date date = null;
        try {
            date = simpleDateFormat.parse(sDate);
        } catch (ParseException e) {
            isValidDate = false;
        }
        if (cellType.equals(CellType.NUMERIC) && cell.getDateCellValue().compareTo(date) != -1) {
            isValidDate = true;
        } else if (cellType.equals(CellType.STRING)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                String dateStr = cell.getStringCellValue();
                sdf.setLenient(false);
                Date finishTime = sdf.parse(dateStr);
                if (finishTime.compareTo(date) != -1) {
                    isValidDate = true;
                }
            } catch (ParseException e) {
                isValidDate = false;
            }
        } else {
            isValidDate = false;
        }
        return isValidDate;
    }

    /**
     * 功能描述: 拼接错误信息
     *
     * @param mistakes
     * @param mistake
     * @param mistakeDesc
     * @return void
     * @date 2021/2/1
     */
    private void addAndReversMistake(List<Mistake> mistakes, Mistake mistake, StringBuilder mistakeDesc) {
        if (mistakeDesc != null && mistakeDesc.length() > 0) {
            mistake.setDescribe(mistakeDesc.toString());
            mistakes.add(mistake);
        }
    }

    /**
     * 功能描述: 校验列头
     *
     * @param projectId
     * @param row
     * @param excelType 1epic 2feature 3story
     * @return boolean
     * @date 2021/2/1
     */
    private boolean checkHeadLine(Long projectId, Row row, Byte excelType) {
        List<String> fieldNameList = new ArrayList<>();
        String[] headLine;
        switch (excelType) {
            case 1:
                // 固定字段
                headLine = EPIC_HEAD_LINE;
                // todo 自定义字段
                //fieldNameList = customFieldDao.fieldNameList(projectId, 1, 1);
                break;
            case 2:
                headLine = FEATURE_HEAD_LINE;
                //fieldNameList = customFieldDao.fieldNameList(projectId, 2, 1);
                break;
            case 3:
                headLine = STORY_HEAD_LINE;
                //fieldNameList = customFieldDao.fieldNameList(projectId, 3, 1);
                break;
            case 4:
                headLine = TASK_HEAD_LINE;
                //fieldNameList = customFieldDao.fieldNameList(projectId, 3, 1);
                break;
            default:
                headLine = new String[0];
        }
        //校验默认的字段是否相同
        for (int i = 0; i < headLine.length; i++) {
            if (!row.getCell(i).getStringCellValue().equals(headLine[i])) {
                return false;
            }
        }
        //校验自定义字段列是否相同
        if (CollectionUtils.isNotEmpty(fieldNameList)) {
            for (int i = 0; i < fieldNameList.size(); i++) {
                if (!row.getCell(headLine.length + i).getStringCellValue().equals(fieldNameList.get(i))) {
                    return false;
                }
            }

            // TODO 自定义字段
        }
        return true;
    }

    /**
     * 功能描述:获取未结束的迭代
     *
     * @param projectId
     * @return java.lang.String[]
     * @date 2021/2/1
     */
    private String[] getUnFinishedSprintNamesByProjectId(Long projectId) {
        List<SprintDTO> result = sprintService.queryUnFinishedByProjectId(null, projectId, 1, 10000);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        List<String> list = Lists.newArrayList();
        for (SprintDTO temp : result) {
            Long sprintId = temp.getSprintId();
            String sprintName = temp.getSprintName();
            list.add(sprintId + "+" + sprintName);
        }
        return list.toArray(new String[list.size()]);
    }


    /**
     * 功能描述:获取系统系所有的系统名
     *
     * @param projectId
     * @return java.lang.String[]
     * @date 2021/2/1
     */
    private String[] getSystemNamesByProjectId(Long projectId) {
        List<SsoSystem> systemList = iFacadeSystemApi.querySystemsByProjectId(projectId);
        if (CollectionUtils.isEmpty(systemList)) {
            return null;
        }

        List<String> list = Lists.newArrayList();
        for (SsoSystem temp : systemList) {
            Long systemId = temp.getSystemId();
            String systemName = temp.getSystemName();
            list.add(systemId + "+" + systemName);

        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 功能描述:获取迭代下的故事名
     *
     * @param sprintId
     * @return java.lang.String[]
     * @date 2021/2/1
     */
    private String[] getStoryNamesBySprintId(Long sprintId) {
        IssueExample example = new IssueExample();
        IssueExample.Criteria criteria = example.createCriteria().andIssueTypeEqualTo(IssueTypeEnum.TYPE_STORY.CODE)
                .andSprintIdEqualTo(sprintId).andStateEqualTo(StateEnum.U.getValue());

        List<Issue> storyList = issueMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(storyList)) {
            return null;
        }

        List<String> list = Lists.newArrayList();
        for (Issue story : storyList) {
            Long issueId = story.getIssueId();
            String title = story.getTitle();
            list.add(issueId + "+" + title);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * @param issueType
     * @param projectId
     * @return
     * @description 根据项目id查询列表头字段
     * @date 2021/2/1
     */
    private List<HeaderField> queryHeaderFieldList(Byte issueType, Long projectId) {
        List<HeaderField> headerFields = null;
        SecurityDTO securityDTO = new SecurityDTO();
        securityDTO.setProjectId(projectId);
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        securityDTO.setUserId(userId);
        Map<String, List<HeaderField>> headerMap = headerFieldService.queryHeaderFields(securityDTO, issueType, null);
        if (MapUtils.isNotEmpty(headerMap)) {
            headerFields = headerMap.get("visibleFields");
        }
        return headerFields;
    }

    /**
     * @param issueType
     * @return
     * @description 获取sheet名字
     * @date 2021/2/1
     */
    private String getSheetname(Byte issueType) {
        String sheetname = "Sheet1";
        if (IssueTypeEnum.TYPE_EPIC.CODE.equals(issueType)) {
            sheetname = "业务需求";
        } else if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(issueType)) {
            sheetname = "研发需求";
        } else if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
            sheetname = "故事";
        } else if (IssueTypeEnum.TYPE_TASK.CODE.equals(issueType)) {
            sheetname = "任务";
        }
        return sheetname;
    }

    /**
     * 功能描述:导出工作项数据
     *
     * @param issueType
     * @param projectId
     * @param mapStr
     * @return Workbook
     * @date 2021/2/1
     */
    @Override
    public Workbook exportIssueDatas(Byte issueType, Long projectId, Map<String, Object> mapStr) throws Exception {
        Assert.notNull(issueType, "工作项类型不能为空");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(getSheetname(issueType));
        List<HeaderField> headerFieldList = queryHeaderFieldList(issueType, projectId);
        if (CollectionUtils.isNotEmpty(headerFieldList)) {
            //创建列头字段行
            createColumnHeadField(workbook, sheet, headerFieldList);
            List<Issue> issueList = null;
            //选中数据导出
            String ageSize = getIssueMaxPageSize(MAX_ISSUE_EXPORT_THRESHOLD_KEY);
            if (null != ageSize) {
                mapStr.put("pageSize", ageSize);
            }
            IssueStringDTO issueStringDTO = JSON.parseObject(JSON.toJSONString(mapStr), IssueStringDTO.class);
            List<Long> issueIdList = issueStringDTO.getIssueIds();
            if (CollectionUtils.isNotEmpty(issueIdList)) {
                issueList = queryIssueList(null, null, issueIdList, PART_TYPE);
            } else {
                issueList = queryIssueList(projectId, mapStr, null, ALL_TYPE);
            }
            List<IssueListDTO> issueResultList = dealQueryIssueList(projectId, mapStr, issueList);
            if (CollectionUtils.isNotEmpty(issueResultList)) {
                dealIssueBody(workbook, sheet, issueType, headerFieldList, issueResultList);
            }
        }
        return workbook;
    }


    /**
     * @param workbook
     * @param sheet
     * @param headerFieldList
     * @description 创建工作项列头
     * @date 2021/2/1
     */
    private void createColumnHeadField(XSSFWorkbook workbook, XSSFSheet sheet, List<HeaderField> headerFieldList) {
        //列头字体
        XSSFFont columnHeadFont = workbook.createFont();
        columnHeadFont.setFontName("宋体");
        columnHeadFont.setFontHeightInPoints((short) 10);
        columnHeadFont.setBold(true);
        //列头样式
        XSSFCellStyle columnHeadStyle = workbook.createCellStyle();
        columnHeadStyle.setFont(columnHeadFont);
        columnHeadStyle.setAlignment(HorizontalAlignment.CENTER);
        columnHeadStyle.setBorderTop(BorderStyle.THIN);
        columnHeadStyle.setBorderBottom(BorderStyle.THIN);
        columnHeadStyle.setBorderLeft(BorderStyle.THIN);
        columnHeadStyle.setBorderRight(BorderStyle.THIN);
        //设置填充方案
        columnHeadStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置预定义填充颜色
        //columnHeadStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 135, (byte) 206, (byte) 250}));
        //列头标题
        XSSFRow columnHeadFieldRow = sheet.createRow(0);
        columnHeadFieldRow.setRowStyle(columnHeadStyle);
        for (int i = 0; i < headerFieldList.size(); i++) {
            sheet.setColumnWidth(i, 6000);
            XSSFCell cell = columnHeadFieldRow.createCell(i);
            cell.setCellStyle(columnHeadStyle);
            cell.setCellValue(headerFieldList.get(i).getFieldName());
        }
    }

    /**
     * @param workbook
     * @param sheet
     * @param issueType
     * @param headerFieldList
     * @param issueResultList
     * @description 处理工作项body数据
     * @date 2021/2/1
     */
    private void dealIssueBody(XSSFWorkbook workbook, XSSFSheet sheet, Byte issueType, List<HeaderField> headerFieldList, List<IssueListDTO> issueResultList) throws Exception {
        for (int i = 0; i < issueResultList.size(); i++) {
            int rowIndex = i + 1;
            XSSFRow row = sheet.createRow(rowIndex);
            IssueListDTO issue = issueResultList.get(i);
            for (int j = 0; j < headerFieldList.size(); j++) {
                String cellValue = "";
                HeaderField headerField = headerFieldList.get(j);
                String fieldCode = headerField.getFieldCode();
                //判断是否自定义字段
                byte isCustom = headerField.getIsCustom();
                if (IsCustomEnum.FALSE.getValue().equals(isCustom)) {
                    Byte fieldType = headerField.getFieldType();
                    cellValue = getPredefineFieldValue(issue, issueType, fieldCode, fieldType);
                } else if (IsCustomEnum.TRUE.getValue().equals(isCustom)) {
                    List<IssueCustomFieldDTO> customFieldList = issue.getCustomFieldList();
                    if (CollectionUtils.isNotEmpty(customFieldList)) {
                        Map<String, List<IssueCustomFieldDTO>> map = customFieldList.stream().collect(Collectors.groupingBy(IssueCustomFieldDTO::getFieldCode));
                        if (map.containsKey(fieldCode)) {
                            List<IssueCustomFieldDTO> list = map.get(fieldCode);
                            IssueCustomFieldDTO issueCustomField = list.get(0);
                            String fieldValue = issueCustomField.getFieldValue();
                            Byte filedType = issueCustomField.getFieldType();
                            cellValue = dealCustomdefineFieldValue(fieldCode, fieldValue, filedType);
                        }
                    }
                }
                createIssueBody(workbook, sheet, row, j, cellValue);
            }
        }
    }

    /**
     * @param workbook
     * @param sheet
     * @param columnIndex
     * @param cellValue
     * @description 创建工作项内容1
     * @date 2021/2/1
     */
    private void createIssueBody(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, int columnIndex, String cellValue) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFCell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cellValue);
    }

    /**
     * @param issue
     * @param issueType
     * @param fieldName
     * @param fieldType
     * @return
     * @throws Exception
     * @description 查询预定义字段值
     * @date 2021/2/1
     */
    private String getPredefineFieldValue(IssueListDTO issue, Byte issueType, String fieldName, Byte fieldType) {
        //Map<String, String> sysExtendFieldDetailMap = getSysExtendFieldDetail(issue, issueType);
        Map<String, String> sysExtendFieldDetailMap = Maps.newHashMap();
        List<SysExtendFieldDetailDTO> sysExtendFieldDetailList = issue.getSysExtendFieldDetailList();
        if (CollectionUtils.isNotEmpty(sysExtendFieldDetailList)) {
            for (SysExtendFieldDetailDTO sysExtendFieldDetail : sysExtendFieldDetailList) {
                sysExtendFieldDetailMap.put(sysExtendFieldDetail.getFieldId(), sysExtendFieldDetail.getValue());
            }
        }
        //sysExtendFieldDetailMap.putAll(getVersionNameMap(issue, issueType));
        //处理扩展字段
        if (sysExtendFieldDetailMap.containsKey(fieldName)) {
            String value = sysExtendFieldDetailMap.get(fieldName);
            //return dealFieldValue(value, fieldName, fieldType);
            return filterFieldEnumValue(fieldName, value);
        } else {
            //处理工作项字段
            Object value = null;
            try {
                Field field = issue.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                value = field.get(issue);
                Type type = field.getGenericType();
                //return dealFieldValue(value, fieldName, fieldType);
                return dealObjectFieldValue(fieldName, value, type, fieldType);
            } catch (Exception e) {
                //LOGGER.error("getPredefineFieldValue occur exception,fieldName:{}, value:{}, message:{}", fieldName, value, e.getMessage());
                return "";
            }
        }
    }

    /**
     * @param fieldName
     * @param value
     * @param type
     * @param fieldType
     * @return
     * @description 处理对象字段值
     * @date 2021/2/1
     */
    private String dealObjectFieldValue(String fieldName, Object value, Type type, Byte fieldType) {
        String result = "";
        if (null != value) {
            String types = type.toString();
            if ("interface java.util.Map".equals(types)) {
                Map map = (Map) value;
                if (map.containsKey("name")) {
                    result = String.valueOf(map.get("name"));
                }
            } else if ("class java.util.Date".equals(types)) {
                result = dealDateObjectTypeConvert(value, fieldType);
            } else {
                result = String.valueOf(value);
            }
            result = filterFieldEnumValue(fieldName, result);
        }
        return result;
    }

    /**
     * @param value
     * @param fieldType
     * @return
     * @description 处理日期对象类型转换
     * @date 2021/2/2
     */
    private String dealDateObjectTypeConvert(Object value, Byte fieldType) {
        String result = "";
        if (FieldTypeEnum.DATE.getValue().equals(fieldType)) {
            Date date = (Date) value;
            if (null != date) {
                result = DateTools.parseToString(date, DateTools.YYYY_MM_DD);
            }
        }
        if (FieldTypeEnum.TIMEDATE.getValue().equals(fieldType)) {
            Date datetime = (Date) value;
            if (null != datetime) {
                result = DateTools.parseToString(datetime, DateTools.YYYY_MM_DD_HH_MM_SS);
            }
        }
        return result;
    }

    /**
     * @param fieldName
     * @param result
     * @return
     * @description 过滤字段枚举值
     * @date 2021/2/2
     */
    private String filterFieldEnumValue(String fieldName, String result) {
        //过滤根据id查名称字段
        if (StringUtils.isNotBlank(result)) {
            //需求主体
            if ("reqSubject".equals(fieldName)) {
                result = ExcelFieldEnum.ReqSubject.getFieldName(result);
            }
            //需求计划状态
            else if ("planStates".equals(fieldName)) {
                result = ExcelFieldEnum.ReqPlanStates.getFieldName(result);
            }
            //需求组
            else if ("reqGroup".equals(fieldName)) {
                result = ExcelFieldEnum.ReqGroup.getFieldName(result);
            }
            //部署类型
            else if ("type".equals(fieldName)) {
                result = ExcelFieldEnum.DeployType.getFieldName(result);
            }
            //交维子类型
            else if ("subInterType".equals(fieldName)) {
                result = ExcelFieldEnum.SubInterType.getFieldName(result);
            }
            //代码配置比
            /*else if ("bizType".equals(fieldName)) {
                result = CmpReqCodeConfigEnums.getName(result);
            }*/
            //部署说明
            else if ("deployIllustration".equals(fieldName)) {
                List<String> deployIllustrationList = JSONArray.parseArray(result, String.class);
                StringBuilder sb = new StringBuilder();
                if (CollectionUtils.isNotEmpty(deployIllustrationList)) {
                    for (String illustration : deployIllustrationList) {
                        sb.append(ExcelFieldEnum.DeployIllustration.getFieldName(illustration)).append(",");
                    }
                    int position = sb.toString().lastIndexOf(",");
                    result = sb.substring(0, position);
                }
            }
            /*
            else if ("moduleId".equals(fieldName)) {
                Module module = moduleMapper.selectByPrimaryKey(Long.valueOf(result));
                if (null != module) {
                    result = module.getModuleName();
                }
            }
            else if ("systemId".equals(fieldName)) {
                LOGGER.info("fieldName:{},result:{}", fieldName, result);
                SsoSystem system = IFacadeSystemApi.querySystemBySystemId(Long.valueOf(result));
                if (null != system) {
                    result = system.getSystemName();
                }
            }
            else if ("createUid".equals(fieldName) || "updateUid".equals(fieldName) || "handler".equals(fieldName)) {
                LOGGER.info("result:{}",result);
                SsoUserDTO user = iFacadeUserApi.queryUserById(Long.valueOf(result));
                if (null != user) {
                    result = user.getUserName();
                }
            }*/
        }
        if (StringUtils.isEmpty(result) || "null".equals(result)) {
            result = "";
        }
        return result;
    }

    /**
     * @param issue
     * @param issueType
     * @return
     * @description 获取版本名称
     * @date 2021/2/2
     */
    private Map<String, String> getVersionNameMap(IssueListDTO issue, Byte issueType) {
        Map<String, String> versionNameMap = Maps.newHashMap();
        Long issueId = issue.getIssueId();
        Long epicIssueId = issueFactory.getEpicId(issueId, issueType);
        if (null != epicIssueId) {
            //版本名称
            VersionManagerDTO versionManagerDTO = queryVersionManageInfo(epicIssueId);
            if (null != versionManagerDTO) {
                versionNameMap.put("versionName", versionManagerDTO.getVersionName());
            }
        }
        return versionNameMap;
    }


    /**
     * @param issue
     * @param issueType
     * @return
     * @description 获取扩展字段
     * 1、字段属性需要加上要求上线时间
     * 2、研发需求导出需要带上版本计划名称+客户需求编号+局方需求编号
     * 3、用户故事导出需要带上版本计划名称
     * @date 2021/2/2
     */
    private Map<String, String> getSysExtendFieldDetail(IssueListDTO issue, Byte issueType) {
        Map<String, String> sysExtendFieldDeatilMap = Maps.newHashMap();
        Long issueId = issue.getIssueId();
        //查询所有工作项类型的扩展字段
        List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getSysExtendFieldDetail(issueId);
        if (CollectionUtils.isNotEmpty(sysExtendFieldDetailList)) {
            for (SysExtendFieldDetail sysExtendFieldDetail : sysExtendFieldDetailList) {
                sysExtendFieldDeatilMap.put(sysExtendFieldDetail.getFieldId(), sysExtendFieldDetail.getValue());
            }
        }
        //业务需求工作项id
        Long epicIssueId = issueFactory.getEpicId(issueId, issueType);
        if (null != epicIssueId) {
            //版本名称
            VersionManagerDTO versionManagerDTO = queryVersionManageInfo(epicIssueId);
            if (null != versionManagerDTO) {
                sysExtendFieldDeatilMap.put("versionName", versionManagerDTO.getVersionName());
            }
            sysExtendFieldDetailList = sysExtendFieldDetailService.getSysExtendFieldDetail(epicIssueId);
            if (CollectionUtils.isNotEmpty(sysExtendFieldDetailList)) {
                for (SysExtendFieldDetail sysExtendFieldDetail : sysExtendFieldDetailList) {
                    String fieldId = sysExtendFieldDetail.getFieldId();
                    if ("bizNum".equals(fieldId)) {
                        sysExtendFieldDeatilMap.put("bizNum", sysExtendFieldDetail.getValue());
                    } else if ("askLineTime".equals(fieldId)) {
                        sysExtendFieldDeatilMap.put("askLineTime", sysExtendFieldDetail.getValue());
                    } else if ("formalReqCode".equals(fieldId)) {
                        sysExtendFieldDeatilMap.put("formalReqCode", sysExtendFieldDetail.getValue());
                    }
                }
            }
        }
        return sysExtendFieldDeatilMap;
    }

    /**
     * @param epicId
     * @return
     * @description 根据epicId查询版本管理信息
     * @date 2021/2/2
     */
    private VersionManagerDTO queryVersionManageInfo(Long epicId) {
        VersionManagerDTO versionManagerDTO = null;

        return versionManagerDTO;
    }

    /**
     * @param value
     * @param fieldName
     * @param fieldType
     * @return
     * @description 处理字段值
     * @date 2021/2/2
     */
    private String dealCustomdefineFieldValue(String fieldName, Object value, Byte fieldType) {
        String result = null;
        try {
            //根据字段类型取值
            if (FieldTypeEnum.TEXT.getValue().equals(fieldType) || FieldTypeEnum.TEXTAREA.getValue().equals(fieldType) || FieldTypeEnum.NUMBER.getValue().equals(fieldType)) {
                result = null != value ? value.toString() : null;
            } else if (FieldTypeEnum.SELECT.getValue().equals(fieldType)) {
                Map map = (Map) value;
                if (null != map) {
                    if (map.containsKey("name")) {
                        result = String.valueOf(map.get("name"));
                    }
                }
            } else {
                result = dealDateObjectTypeConvert(value, fieldType);
            }
        } catch (Exception e) {
            LOGGER.error("dealCustomdefineFieldValue fieldName:{},value:{},exception,message:{}", fieldName, value, e.getMessage());
        }
        if (StringUtils.isEmpty(result) || "null".equals(result)) {
            result = "";
        }
        return result;
    }

    /**
     * @param projectId
     * @param mapStr
     * @param issueIdList
     * @param type
     * @return
     * @description 查询issue集合
     * @date 2021/2/2
     */
    private List<Issue> queryIssueList(Long projectId, Map<String, Object> mapStr, List<Long> issueIdList, Byte type) throws Exception {
        List<Issue> issueList = null;
        if (ALL_TYPE.equals(type)) {
            issueList = issueService.queryIssueList(mapStr);
        } else if (PART_TYPE.equals(type)) {
            IssueExample example = new IssueExample();
            example.setOrderByClause("issue_id desc");
            example.createCriteria().andIssueIdIn(issueIdList);
            issueList = issueMapper.selectByExample(example);
        }
        return issueList;
    }

    /**
     * @param projectId
     * @param mapStr
     * @param issues
     * @return
     * @description 处理查询到的工作项列表
     * @date 2021/2/2
     */
    private List<IssueListDTO> dealQueryIssueList(Long projectId, Map<String, Object> mapStr, List<Issue> issues) {
        List<IssueListDTO> issueListDTOS = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(issues)) {
            Map<Long, List<Issue>> issueRestltMap = issues.stream().collect(Collectors.groupingBy(Issue::getIssueId));
            List<Long> allIssueId = issueMapper.listAllIssueId(Lists.newArrayList(issueRestltMap.keySet()));
            if (CollectionUtils.isNotEmpty(allIssueId)) {
                //项目下的当前页
                Map<String, Map> map = issueService.issueMap( null);
                if (MapUtils.isNotEmpty(map)) {
                    IssueStringDTO issueStringDTO = JSON.parseObject(JSON.toJSONString(mapStr), IssueStringDTO.class);
                    for (Issue issue : issues) {
                        IssueListDTO issueListDTO = ReflectObjectUtil.copyProperties(issue, IssueListDTO.class);
                        issueService.arrangeIssueListDTO(issue, issueListDTO, map);
                        String rootIds = issueStringDTO.getRootIds();
                        //LOGGER.info("rootIds:{}", rootIds);
                        issueService.sortIssueDTO(QueryTypeEnum.TYPE_VALID.CODE, rootIds, issueListDTO, map);
                        //查询非Epic的客户需求编号、局方需求编号、要求上线时间
                        Byte issueType = Byte.parseByte(mapStr.get("issueType").toString());
                        if (issueType.compareTo(IssueTypeEnum.TYPE_EPIC.CODE) > 0) {
                            Map map1 = issueService.queryIssueEpic(issueListDTO.getIssueId(), issueType);
                        }
                        issueListDTOS.add(issueListDTO);
                    }
                }
            }
        }
        return issueListDTOS;
    }

    /**
     * 功能描述:导出工作项数据
     *
     * @param issueType
     * @param projectId
     * @param mapStr
     * @return Workbook
     * @date 2021/2/2
     */
    /*@Override
    public Workbook exportIssueDatas(Byte issueType, Long projectId, Map<String, Object> mapStr) throws Exception {
        IssueStringDTO issueStringDTO = JSON.parseObject(JSON.toJSONString(mapStr, SerializerFeature.WriteMapNullValue), IssueStringDTO.class);
        Assert.notNull(issueType, "工作项类型不能为空");
        List<Issue> issues = null;
        List<ExcelIssue> excelIssueList = null;
        issueStringDTO.setIssueType(String.valueOf(issueType));
        //选中的数据
        List<Long> issueIds = issueStringDTO.getIssueIds();
        //全量导出
        if (CollectionUtils.isEmpty(issueIds)) {
            try {
                issues = issueService.queryIssueList(mapStr, projectId);
                if (CollectionUtils.isNotEmpty(issues)) {
                    excelIssueList = dealExcelIssueList(projectId, issues);
                }
            } catch (Exception e) {
                LOGGER.error("queryIssueList method occur exception, param projectId:{}, return message:{}", projectId, e.getMessage());
            }
        } else {
            //选中导出
            IssueExample issueExample = new IssueExample();
            issueExample.createCriteria().andIssueIdIn(issueIds);
            issues = issueMapper.selectByExample(issueExample);
            if (CollectionUtils.isNotEmpty(issues)) {
                excelIssueList = dealExcelIssueList(projectId, issues);
            } else {
                LOGGER.warn("导出工作项类型为:{},工作项编号为:{}的数据不存在", IssueTypeEnum.getName(issueType), issueIds);
            }
        }
        //数据写入excel
        Map<String, String> map = ExcelUtil.getExcelInfo(issueType.intValue());
        String templateName = map.get(ExcelUtil.EXPORT_TEMPLATE);
        String sheetName = map.get(ExcelUtil.SHEET_NAME);
        Resource resource = new ClassPathResource("excelTemplate" + File.separator + templateName);
        XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());
        if (CollectionUtils.isNotEmpty(excelIssueList)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            convertIssueListDataToRow(sheet, cellStyle, excelIssueList);
            //TODO 后期针对不同工作项类型需单独处理
            *//*if (IssueTypeEnum.TYPE_EPIC.CODE.equals(issueType)) {
            } else if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(issueType)) {
            } else if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
            }*//*
        }
        return workbook;
    }*/

    /**
     * @param projectId
     * @param issues
     * @description 处理工作项列表
     * @date 2021/2/2
     */
    private List<ExcelIssue> dealExcelIssueList(Long projectId, List<Issue> issues) {
        List<ExcelIssue> excelIssueList = Lists.newArrayList();
        for (Issue issue : issues) {
            ExcelIssue excelIssue = new ExcelIssue();
            Integer order = issue.getOrder();
            Long sprintId = issue.getSprintId();
            Long systemId = issue.getSystemId();
            Long issueId = issue.getIssueId();
            excelIssue.setIssueId(issueId);
            excelIssue.setTitle(issue.getTitle());
            excelIssue.setBeginDate(null != issue.getBeginDate() ? DateUtil.toString(DateUtil.YYYY_MM_DD_SLASH, issue.getBeginDate()) : null);
            excelIssue.setEndDate(null != issue.getEndDate() ? DateUtil.toString(DateUtil.YYYY_MM_DD_SLASH, issue.getEndDate()) : null);
            excelIssue.setPlanWorkload(null != issue.getPlanWorkload() ? String.valueOf(issue.getPlanWorkload()) : null);
            excelIssue.setPriority(null != issue.getPriority() ? IssuePriorityEnum.getName(issue.getPriority()) : null);
            excelIssue.setImportance(null != issue.getImportance() ? IssueImportanceEnum.getName(issue.getImportance()) : null);
            excelIssue.setOrder(null != order ? String.valueOf(order) : null);
            excelIssue.setSprintId(sprintId);
            excelIssue.setSystemId(systemId);
            excelIssue.setIssueId(issue.getIssueId());
            excelIssueList.add(excelIssue);

            // systemids
            //系统
            List<Long> systemIds = new ArrayList<>();
            List<IssueSystemRelp> listIssueSystemRelp = issueSystemRelpService.listIssueSystemRelp(issueId);
            for (IssueSystemRelp issueSystemRelp : listIssueSystemRelp) {
                systemIds.add(issueSystemRelp.getSystemId());
            }
            excelIssue.setSystemIds(systemIds);
        }
        //系统
        Map<Long, String> systemMap = Maps.newHashMap();
        //迭代
        Map<Long, String> sprintMap = Maps.newHashMap();
        //根据项目id查询系统名称
        List<SsoSystem> systemList = iFacadeSystemApi.querySystemsByProjectId(projectId);
        if (CollectionUtils.isNotEmpty(systemList)) {
            for (SsoSystem ssoSystem : systemList) {
                systemMap.put(ssoSystem.getSystemId(), ssoSystem.getSystemName());
            }
        }
        //根据项目id查询迭代名称
        SprintExample sprintExample = new SprintExample();
        sprintExample.createCriteria().andProjectIdEqualTo(projectId);
        List<Sprint> sprintList = sprintMapper.selectByExample(sprintExample);
        if (CollectionUtils.isNotEmpty(sprintList)) {
            for (Sprint sprint : sprintList) {
                sprintMap.put(sprint.getSprintId(), sprint.getSprintName());
            }
        }
        for (ExcelIssue excelIssue : excelIssueList) {
            if (MapUtils.isNotEmpty(systemMap)) {
//                Long systemId = excelIssue.getSystemId();
//                excelIssue.setSystemName(systemMap.get(systemId));
                List<Long> systemIds = excelIssue.getSystemIds();
                StringBuilder systemName = new StringBuilder();
                for (int i = 0; i < systemIds.size(); i++) {

                    if (i == 0) {
                        systemName.append(systemMap.get(systemIds.get(i)));
                    } else {
                        systemName.append("," + systemMap.get(systemIds.get(i)));
                    }
                }
                excelIssue.setSystemName(systemName.toString());
            }
            if (MapUtils.isNotEmpty(sprintMap)) {
                Long sprintId = excelIssue.getSprintId();
                excelIssue.setSprintName(sprintMap.get(sprintId));
            }
        }
        return excelIssueList;
    }

    /**
     * @param sheet
     * @param cellStyle
     * @param excelIssueList
     * @description 转换工作项列表数据到excel行
     * @date 2021/2/2
     */
    private void convertIssueListDataToRow(XSSFSheet sheet, XSSFCellStyle cellStyle, List<ExcelIssue> excelIssueList) {
        int startRowIndex = 1;
        for (ExcelIssue excelIssue : excelIssueList) {
            XSSFRow row = sheet.createRow(startRowIndex++);
            fillIssueRowDataToCell(row, cellStyle, excelIssue);
        }
    }

    /**
     * @param row
     * @param cellStyle
     * @param excelIssue
     * @description 填充工作项行数据到单元格
     * @date 2021/2/2
     */
    private void fillIssueRowDataToCell(XSSFRow row, XSSFCellStyle cellStyle, ExcelIssue excelIssue) {
        int columnIndex = 0;
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getTitle());
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getBeginDate());
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getEndDate());
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getPlanWorkload());
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getPriority());
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getImportance());
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getOrder());
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getSprintName());
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getSystemName());
        setCellValue(row, cellStyle, columnIndex++, excelIssue.getIssueId());
    }

    /**
     * @param row
     * @param cellStyle
     * @param columnIndex
     * @param cellValue
     * @description 设置单元格值
     * @date 2021/2/2
     */
    private void setCellValue(XSSFRow row, XSSFCellStyle cellStyle, Integer columnIndex, Object cellValue) {
        XSSFCell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyle);
        if (null != cellValue) {
            cell.setCellValue(cellValue.toString());
        }
    }

    /**
     * @return
     * @description 查询工作项每页显示最大记录数
     * @date 2021/2/2
     */
    private String getIssueMaxPageSize(String fieldName) {
        String issuePageSizeThreshold = null;
        ExternalApiConfigExample example = new ExternalApiConfigExample();
        example.createCriteria().andFieldNameEqualTo(fieldName);
        List<ExternalApiConfig> externalApiConfigList = externalApiConfigMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(externalApiConfigList)) {
            issuePageSizeThreshold = externalApiConfigList.get(0).getFieldValue();
        }
        return issuePageSizeThreshold;
    }

}
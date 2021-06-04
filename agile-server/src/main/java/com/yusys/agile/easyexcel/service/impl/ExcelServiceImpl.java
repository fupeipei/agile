package com.yusys.agile.easyexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.easyexcel.ExcelUtil;
import com.yusys.agile.easyexcel.enums.ExcelTypeEnum;
import com.yusys.agile.easyexcel.handler.SpinnerWriteHandler;
import com.yusys.agile.easyexcel.service.DownloadExcelTempletService;
import com.yusys.agile.easyexcel.service.ExcelTempletFactory;
import com.yusys.agile.easyexcel.service.IExcelService;
import com.yusys.agile.easyexcel.vo.ExcelCommentField;
import com.yusys.agile.easyexcel.vo.ExcelVo;
import com.yusys.agile.externalapiconfig.dao.ExternalApiConfigMapper;
import com.yusys.agile.fault.domain.ExternalApiConfig;
import com.yusys.agile.fault.domain.ExternalApiConfigExample;
import com.yusys.agile.file.domain.FileInfo;
import com.yusys.agile.file.service.FileService;
import com.yusys.agile.headerfield.domain.HeaderField;
import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueExportDTO;
import com.yusys.agile.issue.dto.IssueStringDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.TaskTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.service.TaskService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;

import com.yusys.agile.utils.CollectionUtil;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Description: excel 实现类
 * @author: zhao_yd
 * @Date: 2021/5/25 8:45 下午
 */
@Slf4j
@Service
public class ExcelServiceImpl implements IExcelService {

    @Autowired
    private StoryService storyService;
    @Autowired
    private IssueFactory issueFactory;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FileService fileService;
    @Autowired
    private HeaderFieldService headerFieldService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private IssueMapper issueMapper;
    @Autowired
    private ExternalApiConfigMapper externalApiConfigMapper;
    @Autowired
    private SSprintMapper sSprintMapper;
    @Autowired
    private IFacadeSystemApi iFacadeSystemApi;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Autowired
    private IStageService stageService;

    /**
     * 缓存数据
     */
    private static Map<Long,String> systemMap = new ConcurrentHashMap<>();

    private static Map<Long,String> userMap = new ConcurrentHashMap<>();

    private static final String MAX_ISSUE_EXPORT_THRESHOLD_KEY = "ISSUE_MAX_EXPORT_THRESHOLD";
    private static final String[] STORY_HEAD_LINE = {"*故事名称", "故事描述", "验收标准", "迭代", "优先级", "父工作项", "故事点", "开始日期", "结束日期", "预计工时"};
    private static final String[] TASK_HEAD_LINE = {"*故事ID", "*任务标题", "任务描述", "*任务类型", "预计工时"};

    /**
     * 全量导出
     */
    private static final Byte ALL_TYPE = 1;
    /**
     * 局部导出
     */
    private static final Byte PART_TYPE = 2;

    @Override
    public void downLoadTemplate(Byte excelType, HttpServletResponse response, ExcelCommentField filed) {
        String type = ExcelTypeEnum.getFieldName(excelType);
        DownloadExcelTempletService downloadExcelTempletService = ExcelTempletFactory.get(type);
        downloadExcelTempletService.download(response, filed);
    }

    @Override
    public FileInfo uploadStorys(MultipartFile file, ExcelCommentField field) throws Exception {
        //1、检查文件类型
        checkFileType(file);

        InputStream inputStream = file.getInputStream();
        //2、从第一行开始读，带表头
        List<List<String>> data = ExcelUtil.readExcel(inputStream, 0);

        //3、校验数据（必填项、数据格式等等）
        List<List<String>> copyData = CollectionUtil.deepCopy(data);
        boolean hasError = checkData(copyData, (byte) 3);

        //4、传错误文件
        if (hasError) {
            return uploadFile(copyData, "storyImportError.xlsx", "storys", IssueTypeEnum.getName((byte) 3), field);
        }
        List<JSONObject> jsonObjects = analysisStoryData(data);

        //5、存入数据库
        if (CollectionUtils.isNotEmpty(jsonObjects)) {
            for (JSONObject jsonObject : jsonObjects) {
                IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
                Long issueId = storyService.createStory(issueDTO);
                //批量新增或者批量更新扩展字段值
                issueDTO.setIssueType(new Byte("3"));
                issueDTO.setIssueId(issueId);
                issueFactory.batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
            }
        }
        return null;
    }

    @Override
    public FileInfo uploadTasks(MultipartFile file, ExcelCommentField field) throws Exception {
        Long sprintId = field.getSprintId();
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        SSprint sprint= sSprintMapper.selectByPrimaryKey(sprintId);
        Long teamId = sprint.getTeamId();
        checkIsSm(userId,teamId);
        //检查文件类型
        checkFileType(file);
        List<List<String>> data = ExcelUtil.readExcel(file.getInputStream(), 0);
        List<List<String>> copyData = CollectionUtil.deepCopy(data);
        boolean hasError = checkData(copyData, (byte) 4);
        if (hasError) {
            return uploadFile(copyData, "taskImportError.xlsx", "tasks", IssueTypeEnum.getName((byte) 4), field);
        }
        List<JSONObject> jsonObjects = analysisTaskData(data);
        if (CollectionUtils.isNotEmpty(jsonObjects)) {
            for (JSONObject jsonObject : jsonObjects) {
                IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
                taskService.createTask(issueDTO);
            }
        }
        return null;
    }


    private List<JSONObject> analysisTaskData(List<List<String>> data) {
        List<JSONObject> jsonObjects = Lists.newArrayList();
        for (int i = 1; i < data.size(); i++) {
            List<String> issueFiles = data.get(i);
            int headSize = data.get(0).size();
            int size = issueFiles.size();
            if (size < headSize) {
                fillNull(issueFiles, size, headSize);
            }
            IssueDTO issueDTO = new IssueDTO();
            // 故事id
            String storyInfo = issueFiles.get(0);
            if (StringUtils.isNotBlank(storyInfo)) {
                String s = StringUtils.substringBefore(storyInfo, "+");
                issueDTO.setParentId(Long.valueOf(s));
            }
            issueDTO.setTitle(issueFiles.get(1));
            issueDTO.setDescription(StringUtils.isNotBlank(issueFiles.get(2)) ? issueFiles.get(2) : null);
            issueDTO.setTaskType(TaskTypeEnum.getCode(issueFiles.get(3)));
            issueDTO.setPlanWorkload(StringUtils.isNotBlank(issueFiles.get(4)) ? Integer.valueOf(issueFiles.get(4)) : null);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(issueDTO));
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    /**
     * 解析故事数据
     *
     * @param data
     * @return
     * @throws Exception
     */
    private List<JSONObject> analysisStoryData(List<List<String>> data) throws Exception {
        List<JSONObject> jsonObjects = Lists.newArrayList();
        int headSize = data.get(0).size();
        for (int i = 1; i < data.size(); i++) {
            List<String> issueFiles = data.get(i);
            int size = issueFiles.size();
            if (size < headSize) {
                fillNull(issueFiles, size, headSize);
            }
            IssueDTO issueDTO = new IssueDTO();
            issueDTO.setTitle(issueFiles.get(0));
            issueDTO.setDescription(issueFiles.get(1));
            issueDTO.setAcceptanceCriteria(issueFiles.get(2));
            String sprintInfo = issueFiles.get(3);
            if (StringUtils.isNotBlank(sprintInfo)) {
                String sprintId = StringUtils.substringBefore(sprintInfo, "+");
                issueDTO.setSprintId(Long.valueOf(sprintId));
            }
            issueDTO.setPriority(StringUtils.isNotBlank(issueFiles.get(4)) ? Byte.valueOf(issueFiles.get(4)) : null);
            issueDTO.setParentId(StringUtils.isNotBlank(issueFiles.get(5)) ? Long.valueOf(issueFiles.get(5)) : null);
            issueDTO.setBeginDate(StringUtils.isNotBlank(issueFiles.get(7)) ? DateUtil.formatStrToDate(issueFiles.get(7)) : null);
            issueDTO.setEndDate(StringUtils.isNotBlank(issueFiles.get(8)) ? DateUtil.formatStrToDate(issueFiles.get(8)) : null);
            issueDTO.setPlanWorkload(StringUtils.isNotBlank(issueFiles.get(9)) ? Integer.valueOf(issueFiles.get(9)) : null);
            SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
            issueDTO.setTenantCode(userInfo.getTenantCode());
            issueDTO.setSystemId(userInfo.getSystemId());
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(issueDTO));
            jsonObject.put("storyPoint", issueFiles.get(6));
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    private void fillNull(List<String> issueFiles, int size, int headSize) {
        for (int i = size; i < headSize; i++) {
            issueFiles.add(i, null);
        }
    }

    /**
     * 校验Excel数据
     *
     * @param copyData
     */
    private boolean checkData(List<List<String>> copyData, Byte type) {
        if (copyData.size() <= 1) {
            throw new BusinessException("导入数据为空，请检查!");
        }
        //1、校验表头数据
        boolean result = checkHeadLine(copyData.get(0), type);
        if (!result) {
            throw new BusinessException("导入模版不正确，请检查!");
        }

        int headSize = copyData.get(0).size();
        //2、表格数据校验
        return checExcelData(copyData, copyData, headSize, false, type);
    }


    private void checkFileType(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(ExcelUtil.XLS) && !originalFilename.endsWith(ExcelUtil.XLSX)) {
            throw new BusinessException("只支持导入.xls、.xlsx类型的文件，请检查!");
        }
    }

    /**
     * 上传错误文件到服务器
     *
     * @param copyData
     * @param templateName
     * @param sheetName
     * @return
     * @throws Exception
     */
    public FileInfo uploadFile(List<List<String>> copyData, String templateName, String sheetName, String type, ExcelCommentField field) throws Exception {
        //写错误文件上传文件服务器
        copyData.remove(0);
        log.info("错误数据信息:{}", JSONObject.toJSONString(copyData));
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        DownloadExcelTempletService templetService = ExcelTempletFactory.get(type);
        SpinnerWriteHandler spinnerWriteHandler = new SpinnerWriteHandler(templetService.getDropDownInfo(field));
        ClassPathResource classPathResource = new ClassPathResource("excelTemplate/" + templateName);
        ExcelWriter writer = EasyExcel.write(os)
                .withTemplate(classPathResource.getInputStream())
                .autoCloseStream(Boolean.TRUE)
                .registerWriteHandler(spinnerWriteHandler)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        writer.write(copyData, writeSheet);
        writer.finish();
        byte[] content = os.toByteArray();
        FileItem fileItem = getFileItem(UUID.randomUUID().toString() + ".xlsx", content);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return fileService.upload(multipartFile);
    }

    /**
     * 检查Excel文件数据
     * @param data
     * @param copyData
     * @param headSize
     * @param hasError
     * @param type
     * @return
     */
    public boolean checExcelData(List<List<String>> data, List<List<String>> copyData, int headSize, boolean hasError, byte type) {
        for (int i = 1; i < data.size(); i++) {
            List<String> line = data.get(i);
            List<String> fileResult = copyData.get(i);
            if (fileResult.size() < headSize) {
                fillNull(fileResult, fileResult.size(), headSize);
            }
            if (line.size() < copyData.size()) {
                fillNull(line, line.size(), copyData.size());
            }
            if (IssueTypeEnum.TYPE_STORY.CODE.equals(type)) {
                if (StringUtils.isBlank(line.get(0))) {
                    fileResult.add(headSize, "故事名称不能为空");
                    hasError = true;
                    continue;
                }

            } else if (IssueTypeEnum.TYPE_TASK.CODE.equals(type)) {
                if (StringUtils.isBlank(line.get(0))) {
                    fileResult.add(headSize, "故事ID不能为空");
                    hasError = true;
                    continue;
                }
                if (StringUtils.isBlank(line.get(1))) {
                    fileResult.add(headSize, "任务标题不能为空");
                    hasError = true;
                    continue;
                }
                if (StringUtils.isBlank(line.get(3))) {
                    fileResult.add(headSize, "任务类型不能为空");
                    hasError = true;
                    continue;
                }
            }
        }
        return hasError;
    }

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
     * 功能描述: 校验列头
     * @param excelhead
     * @param excelType
     * @return
     */
    private boolean checkHeadLine(List<String> excelhead, Byte excelType) {
        String[] headLine;
        switch (excelType) {
            case 3:
                headLine = STORY_HEAD_LINE;
                break;
            case 4:
                headLine = TASK_HEAD_LINE;
                break;
            default:
                headLine = new String[0];
        }
        List<String> list = Arrays.asList(headLine);
        return CollectionUtils.isEqualCollection(list, excelhead);
    }


    @Override
    public void exportIssues(Byte issueType, Long systemId, Map<String, Object> map, HttpServletResponse response) throws Exception {

        List<HeaderField> headerFieldList = queryHeaderFieldList(issueType);
        if (CollectionUtils.isNotEmpty(headerFieldList)) {
            //获取表头数据
            List<String> headers = headerFieldList.stream().map(
                    headerField -> headerField.getFieldName()).collect(Collectors.toList());

            List<Issue> issueList;
            //选中数据导出
            String ageSize = getIssueMaxPageSize(MAX_ISSUE_EXPORT_THRESHOLD_KEY);
            if (null != ageSize) {
                map.put("pageSize", ageSize);
            }
            IssueStringDTO issueStringDTO = JSON.parseObject(JSON.toJSONString(map), IssueStringDTO.class);
            List<Long> issueIdList = issueStringDTO.getIssueIds();
            if (CollectionUtils.isNotEmpty(issueIdList)) {
                issueList = queryIssueList(null, issueIdList, PART_TYPE);
            } else {
                issueList = queryIssueList(map, null, ALL_TYPE);
            }

            List<List<String>> excelData = getExcelData(issueList, headerFieldList);
            ExcelVo vo = new ExcelVo();
            vo.setFileName(UUID.randomUUID().toString());
            vo.setFileType(ExcelUtil.XLSX);
            vo.setCellText(headers);
            vo.setStringList(excelData);
            ExcelUtil.export(vo, response);
        }
    }

    private List<List<String>> getExcelData(List<Issue> issueList, List<HeaderField> headerFieldList){

        List<List<String>> dataResult = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(issueList)) {

            List<IssueExportDTO> exportDTOList = transformaData(issueList);
            log.info("issue数据转换后:{}",JSONObject.toJSONString(exportDTOList));
            for(IssueExportDTO issue:exportDTOList){

                List<String> fieldList = Lists.newArrayList();
                Class<?> issueClass = issue.getClass();
                Field[] fields = issueClass.getDeclaredFields();

                for (HeaderField headerField : headerFieldList) {
                    for (Field field : fields) {
                        try {
                            field.setAccessible(true);
                            String fieldCode = headerField.getFieldCode();
                            String name = field.getName();

                            if (StringUtils.equals(fieldCode, name)) {
                                Object o = field.get(issue);
                                fieldList.add(o == null ? null:String.valueOf(o));
                                break;
                            }
                        } catch (Exception e) {
                            log.info("反射获取issue数据异常:{}", e.getMessage());
                        }
                    }
                }
                dataResult.add(fieldList);
            }
        }
        return dataResult;
    }

    private List<IssueExportDTO> transformaData(List<Issue> issues){
        List<IssueExportDTO> exportDTOList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(issues)){
            for(Issue issue : issues){
                IssueExportDTO target = new IssueExportDTO();
                Class<?> sourceClass = issue.getClass();
                Class<?> targetClass = target.getClass();
                Field[] sourceFields = sourceClass.getDeclaredFields();
                Field t;

                for (Field s : sourceFields) {
                    try {
                        s.setAccessible(true);
                        String name = s.getName();
                        t = targetClass.getDeclaredField(name);
                        t.setAccessible(true);
                        String result = null;
                        if("issueType".equals(name)){
                            result = IssueTypeEnum.getName((Byte) s.get(issue));
                        }else if("sprintId".equals(name)){
                            Long sprintId = s.get(issue) == null ? null : Long.valueOf(String.valueOf(s.get(issue)));
                            if(Optional.ofNullable(sprintId).isPresent()){
                                SSprintWithBLOBs sprint = sSprintMapper.selectByPrimaryKey(sprintId);
                                result = sprint.getSprintName();
                            }

                        }else if("systemId".equals(name)){
                            Long systemId = s.get(issue) == null ? null : Long.valueOf(String.valueOf(s.get(issue)));
                            if(Optional.ofNullable(systemId).isPresent() && !systemMap.containsKey(systemId)){
                                try {
                                    SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(systemId);
                                    systemMap.put(systemId,ssoSystem.getSystemName());
                                }catch (Exception e){
                                    log.info("远程获取系统信息异常:{}",e.getMessage());
                                }
                            }
                            result = systemMap.get(systemId);

                        }else if("createUid".equals(name) || "handler".equals(name) || "updateUid".equals(name)){
                            Long userId = s.get(issue) == null ? null : Long.valueOf(String.valueOf(s.get(issue)));
                            if(Optional.ofNullable(userId).isPresent() && !userMap.containsKey(userId)){
                                try {
                                    SsoUser user = iFacadeUserApi.queryUserById(userId);
                                    userMap.put(userId,user.getUserName());
                                }catch (Exception e){
                                    log.info("远程获取人员信息异常：{}",e.getMessage());
                                }
                            }
                            result = userMap.get(userId);

                        }else if("stageId".equals(name)){
                            Long stageId = s.get(issue) == null ? null : Long.valueOf(String.valueOf(s.get(issue)));
                            String firstStageName = StageConstant.FirstStageEnum.getFirstStageName(stageId);

                            Method method = sourceClass.getMethod("getLaneId", new Class[]{});
                            method.setAccessible(true);
                            Object resultValue = method.invoke(issue, new Object[]{});
                            Long laneId = s.get(issue) == null ? null : Long.valueOf(String.valueOf(resultValue));
                            KanbanStageInstance stageInfo = stageService.getStageInfoByStageId(laneId);
                            StringBuffer buffer = new StringBuffer();
                            if(Optional.ofNullable(firstStageName).isPresent()){
                                buffer.append(firstStageName);
                                if(Optional.ofNullable(stageInfo).isPresent()){
                                    buffer.append("/").append(stageInfo.getStageName());
                                }
                            }
                            result = buffer.length() > 0 ? buffer.toString() : null;

                        }else if("taskType".equals(name)){
                            Integer taskType = s.get(issue) == null ? null : Integer.valueOf(String.valueOf(s.get(issue)));
                            String tasKName  = TaskTypeEnum.getName(taskType);
                            result = tasKName;
                        }

                        Type genericType = s.getGenericType();
                        String typeName = genericType.getTypeName();
                        if("java.util.Date".equals(typeName)){
                            Date date = s.get(issue) == null ? null :(Date)s.get(issue);
                            if(Optional.ofNullable(date).isPresent()){
                                try {
                                    String formatDate = DateUtil.formatDateToStr2(date);
                                    result = formatDate;
                                }catch (Exception e){
                                    log.info("日期转换异常:{}",e.getMessage());
                                }
                            }
                        }
                        t.set(target, result == null? (s.get(issue) == null ? null :s.get(issue).toString()) : result);
                    } catch (Exception e) {
                        log.warn("|类转换异常|{}", s.toString());
                        continue;
                    }
                }
                exportDTOList.add(target);
            }
        }
        return exportDTOList;
    }


    /**
     * 查询列表头字段
     *
     * @param issueType
     * @return
     */
    private List<HeaderField> queryHeaderFieldList(Byte issueType) {
        List<HeaderField> headerFields = null;
        SecurityDTO securityDTO = new SecurityDTO();
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        securityDTO.setUserId(userId);
        Map<String, List<HeaderField>> headerMap = headerFieldService.queryHeaderFields(securityDTO, issueType, null);
        if (MapUtils.isNotEmpty(headerMap)) {
            headerFields = headerMap.get("fields");
        }
        return headerFields;
    }

    /**
     * 查询issue列表
     *
     * @param mapStr
     * @param issueIdList
     * @param type
     * @return
     * @throws Exception
     */
    private List<Issue> queryIssueList(Map<String, Object> mapStr, List<Long> issueIdList, Byte type) throws Exception {
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
     * 查询工作项每页显示最大记录数
     *
     * @param fieldName
     * @return
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

    private void checkIsSm(Long userId, Long teamId) {
        boolean b = iFacadeUserApi.checkIsTeamSm(userId, teamId);
        if (!b) {
            throw new BusinessException("只有本迭代的PO权限才允许操作");
        }
    }

}

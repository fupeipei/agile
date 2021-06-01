package com.yusys.agile.easyexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.easyexcel.ExcelUtil;
import com.yusys.agile.easyexcel.enums.ExcelTypeEnum;
import com.yusys.agile.easyexcel.vo.ExcelCommentFiled;
import com.yusys.agile.easyexcel.service.DownloadExcelTempletService;
import com.yusys.agile.easyexcel.service.ExcelTempletFactory;
import com.yusys.agile.easyexcel.service.IExcelService;
import com.yusys.agile.file.domain.FileInfo;
import com.yusys.agile.file.service.FileService;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.TaskTypeEnum;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.service.TaskService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.utils.CollectionUtil;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *  @Description: excel 实现类
 *  @author: zhao_yd
 *  @Date: 2021/5/25 8:45 下午
 *
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

    private static final String[] STORY_HEAD_LINE = {"*故事名称", "故事描述", "验收标准", "迭代", "优先级", "父工作项", "故事点", "开始日期", "结束日期", "预计工时"};
    private static final String[] TASK_HEAD_LINE = {"*故事ID", "*任务标题", "任务描述", "*任务类型", "预计工时"};
    private static final int FIRST_ROW_NUM = 1;
    @Override
    public void downLoadTemplate(Byte excelType, HttpServletResponse response, ExcelCommentFiled filed) {
        String type = ExcelTypeEnum.getFieldName(excelType);
        DownloadExcelTempletService downloadExcelTempletService = ExcelTempletFactory.get(type);
        downloadExcelTempletService.download(response,filed);
    }

    @Override
    public FileInfo uploadStorys(Long systemId, MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        //从第一行开始读，待表头
        List<List<String>> data = ExcelUtil.readExcel(inputStream, 0);
        //校验数据（必填项、数据格式等等）
        FileInfo fileInfo = checkData(data, (byte)3,"storyImportError.xlsx","storys");
        if(Optional.ofNullable(fileInfo).isPresent()){
            return fileInfo;
        }
        List<JSONObject> jsonObjects = analysisStoryData(data);
        //存入数据库
        if(CollectionUtils.isNotEmpty(jsonObjects)){
            for(JSONObject jsonObject :jsonObjects){
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
    public FileInfo uploadTasks(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        //从第一行开始读，待表头
        List<List<String>> data = ExcelUtil.readExcel(inputStream, 0);
        //校验数据（必填项、数据格式等等）
        FileInfo fileInfo = checkData(data, (byte)4,"taskImportError.xlsx","tasks");
        if(Optional.ofNullable(fileInfo).isPresent()){
            return fileInfo;
        }
        List<JSONObject> jsonObjects = analysisTaskData(data);
        //checkData(data);
        if(CollectionUtils.isNotEmpty(jsonObjects)){
            for(JSONObject jsonObject :jsonObjects){
                IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
                taskService.createTask(issueDTO);

            }
        }
        return null;
    }

    private List<JSONObject> analysisTaskData(List<List<String>> data) {
        List<JSONObject> jsonObjects = Lists.newArrayList();
        for(int i = 1 ; i<data.size();i++){
            List<String> issueFiles =  data.get(i);
            int headSize = data.get(0).size();
            int size = issueFiles.size();
            if(size < headSize){
                fillNull(issueFiles,size,headSize);
            }
            IssueDTO issueDTO = new IssueDTO();
            // 故事id
            String storyInfo = issueFiles.get(0);
            if(StringUtils.isNotBlank(storyInfo)){
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

    private List<JSONObject> analysisStoryData(List<List<String>> data) throws Exception {
        List<JSONObject> jsonObjects = Lists.newArrayList();
        int headSize = data.get(0).size();
        for(int i = 1 ; i<data.size();i++){
            List<String> issueFiles =  data.get(i);
            int size = issueFiles.size();
            if(size < headSize){
                fillNull(issueFiles,size,headSize);
            }
            IssueDTO issueDTO = new IssueDTO();
            issueDTO.setTitle(issueFiles.get(0));
            issueDTO.setDescription(issueFiles.get(1));
            issueDTO.setAcceptanceCriteria(issueFiles.get(2));
            String sprintInfo = issueFiles.get(3);
            if(StringUtils.isNotBlank(sprintInfo)){
                String sprintId = StringUtils.substringBefore(sprintInfo, "+");
                issueDTO.setSprintId(Long.valueOf(sprintId));
            }
            issueDTO.setPriority(StringUtils.isNotBlank(issueFiles.get(4))? Byte.valueOf(issueFiles.get(4)) : null);
            issueDTO.setParentId(StringUtils.isNotBlank(issueFiles.get(5))? Long.valueOf(issueFiles.get(5)) : null);
            issueDTO.setBeginDate(StringUtils.isNotBlank(issueFiles.get(7))? DateUtil.formatStrToDate(issueFiles.get(7)) : null);
            issueDTO.setEndDate(StringUtils.isNotBlank(issueFiles.get(8))? DateUtil.formatStrToDate(issueFiles.get(8)) : null);
            issueDTO.setPlanWorkload(StringUtils.isNotBlank(issueFiles.get(9))? Integer.valueOf(issueFiles.get(9)) : null);
            SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
            issueDTO.setTenantCode(userInfo.getTenantCode());
            issueDTO.setSystemId(userInfo.getSystemId());
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(issueDTO));
            jsonObject.put("storyPoint",issueFiles.get(6));
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    private void fillNull(List<String> issueFiles,int size,int headSize) {
        for(int i = size;i<headSize;i++){
            issueFiles.add(i,null);
        }
    }

    /**
     * 校验Excel数据
     * @param data
     */
    private FileInfo checkData(List<List<String>> data, Byte type,String templateName,String sheetName) throws Exception {
        if(data.size() <=1){
            throw new BusinessException("导入数据为空，请检查!");
        }
        //1、校验表头数据
        boolean result = checkHeadLine(data.get(0),type);
        if(!result){
            throw new BusinessException("导入模版不正确，请检查!");
        }

        List<List<String>> copyData = CollectionUtil.deepCopy(data);
        int headSize = data.get(0).size();

        //2、校验表格中的数据
        boolean hasError = false;
        hasError = checExcelData(data,copyData,headSize,hasError,type);

        //3、写错误文件上传文件服务器
        if(hasError){
            copyData.remove(0);
            log.info("错误数据信息:{}",JSONObject.toJSONString(copyData));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ClassPathResource classPathResource = new ClassPathResource("excelTemplate/"+templateName);
            ExcelWriter writer = EasyExcel.write(os)
                    .withTemplate(classPathResource.getInputStream())
                    .autoCloseStream(Boolean.TRUE)
                    .build();
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
            writer.write(copyData,writeSheet);
            writer.finish();
            byte[] content = os.toByteArray();
            FileItem fileItem = getFileItem(UUID.randomUUID().toString()+".xlsx", content);
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            return fileService.upload(multipartFile);
        }
        return null;
    }

    public boolean checExcelData(List<List<String>> data,List<List<String>> copyData,int headSize, boolean hasError,byte type){
        for(int i = 1;i<data.size(); i++){
            List<String> line = data.get(i);
            List<String> fileResult = copyData.get(i);
            if(fileResult.size() < headSize){
                fillNull(fileResult,fileResult.size(),headSize);
            }

            if(IssueTypeEnum.TYPE_STORY.CODE.equals(type)){
                if(StringUtils.isBlank(line.get(0))){
                    fileResult.add(headSize,"故事名称不能为空");
                    hasError = true;
                    continue;
                }
            }else if(IssueTypeEnum.TYPE_TASK.CODE.equals(type)){

                if(StringUtils.isBlank(line.get(0))){
                    fileResult.add(headSize,"故事ID不能为空");
                    hasError = true;
                    continue;
                }
                if(StringUtils.isBlank(line.get(1))){
                    fileResult.add(headSize,"任务标题不能为空");
                    hasError = true;
                    continue;
                }
                if(StringUtils.isBlank(line.get(3))){
                    fileResult.add(headSize,"任务类型不能为空");
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


}

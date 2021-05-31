package com.yusys.agile.easyexcel.service.impl;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.easyexcel.ExcelUtil;
import com.yusys.agile.easyexcel.enums.ExcelTypeEnum;
import com.yusys.agile.easyexcel.vo.ExcelCommentFiled;
import com.yusys.agile.easyexcel.service.DownloadExcelTempletService;
import com.yusys.agile.easyexcel.service.ExcelTempletFactory;
import com.yusys.agile.easyexcel.service.IExcelService;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 *  @Description: excel 实现类
 *  @author: zhao_yd
 *  @Date: 2021/5/25 8:45 下午
 *
 */
@Service
public class ExcelServiceImpl implements IExcelService {

    @Autowired
    private StoryService storyService;
    @Autowired
    private IssueFactory issueFactory;

    private static final String[] STORY_HEAD_LINE = {"*故事名称", "故事描述", "验收标准", "迭代", "优先级", "父工作项", "故事点", "开始日期", "结束日期", "预计工时"};
    private static final String[] TASK_HEAD_LINE = {"*故事ID", "*任务标题", "任务描述", "*任务类型", "预计工时"};

    @Override
    public void downLoadTemplate(Byte excelType, HttpServletResponse response, ExcelCommentFiled filed) {
        String type = ExcelTypeEnum.getFieldName(excelType);
        DownloadExcelTempletService downloadExcelTempletService = ExcelTempletFactory.get(type);
        downloadExcelTempletService.download(response,filed);
    }

    @Override
    public void uploadStorys(Long systemId, MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        //从第一行开始读，待表头
        List<List<String>> data = ExcelUtil.readExcel(inputStream, 0);
        //校验数据（必填项、数据格式等等）
        checkData(data);
        List<JSONObject> jsonObjects = analysis(data);
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
    }

    private List<JSONObject> analysis(List<List<String>> data) throws Exception {
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
                String[] split = sprintInfo.split("-");
                issueDTO.setSprintId(Long.valueOf(split[0]));
            }
            issueDTO.setPriority(StringUtils.isNotBlank(issueFiles.get(4))? Byte.valueOf(issueFiles.get(4)) : null);
            issueDTO.setParentId(StringUtils.isNotBlank(issueFiles.get(5))? Long.valueOf(issueFiles.get(5)) : null);
            issueDTO.setBeginDate(StringUtils.isNotBlank(issueFiles.get(7))? DateUtil.formatStrToDate(issueFiles.get(7)) : null);
            issueDTO.setEndDate(StringUtils.isNotBlank(issueFiles.get(8))? DateUtil.formatStrToDate(issueFiles.get(8)) : null);
            issueDTO.setPlanWorkload(StringUtils.isNotBlank(issueFiles.get(9))? Integer.valueOf(issueFiles.get(9)) : null);
            issueDTO.setTenantCode(UserThreadLocalUtil.getUserInfo().getTenantCode());
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
    private void checkData(List<List<String>> data){

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

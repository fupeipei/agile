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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
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
        for(int i = 1 ; i<data.size();i++){
            List<String> issueFiles =  data.get(i);
            IssueDTO issueDTO = new IssueDTO();
            issueDTO.setTitle(issueFiles.get(0));
            issueDTO.setDescription(issueFiles.get(1));
            issueDTO.setAcceptanceCriteria(issueFiles.get(2));
            String sprintInfo = issueFiles.get(3);
            if(StringUtils.isNotBlank(sprintInfo)){
                String[] split = sprintInfo.split("-");
                issueDTO.setSprintId(Long.valueOf(split[0]));
            }
            issueDTO.setPriority(Byte.valueOf(issueFiles.get(4)));
            issueDTO.setParentId(Long.valueOf(issueFiles.get(5)));
            issueDTO.setBeginDate(DateUtil.formatStrToDate(issueFiles.get(7)));
            issueDTO.setEndDate(DateUtil.formatStrToDate(issueFiles.get(8)));
            issueDTO.setPlanWorkload(Integer.valueOf(issueFiles.get(9)));
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(issueDTO));
            jsonObject.put("storyPoint",issueFiles.get(6));
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    /**
     * 校验Excel数据
     * @param data
     */
    private void checkData(List<List<String>> data){

    }
}

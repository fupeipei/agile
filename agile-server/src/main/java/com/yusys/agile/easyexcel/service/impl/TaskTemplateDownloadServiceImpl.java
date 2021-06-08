package com.yusys.agile.easyexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yusys.agile.easyexcel.vo.ExcelCommentField;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import com.google.common.collect.Lists;
import com.yusys.agile.easyexcel.ExcelUtil;
import com.yusys.agile.easyexcel.handler.SpinnerWriteHandler;
import com.yusys.agile.easyexcel.service.DownloadExcelTempletService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.TaskTypeEnum;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.portal.model.common.enums.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *  @Description: 任务模版下载
 *  @author: zhao_yd
 *  @Date: 2021/5/28 3:56 下午
 *
 */

@Slf4j
@Service("taskTemplateDownloadService")
public class TaskTemplateDownloadServiceImpl implements DownloadExcelTempletService {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private Sprintv3Service sprintv3Service;
    @Resource
    private IssueMapper issueMapper;

    @Override
    public void download(HttpServletResponse response, ExcelCommentField field) {
        Map<Integer, String[]> mapDropDown = getDropDownInfo(field);
        SpinnerWriteHandler spinnerWriteHandler = new SpinnerWriteHandler(mapDropDown);
        ClassPathResource classPathResource = new ClassPathResource("excelTemplate/taskImportTemplate.xlsx");
        try {
            EasyExcel.write(ExcelUtil.dealResponse("taskImportTemplate",response)).withTemplate(classPathResource.getInputStream())
                    .autoCloseStream(Boolean.TRUE)
                    .sheet("tasks")
                    .registerWriteHandler(spinnerWriteHandler)
                    .doWrite(new ArrayList());
        } catch (IOException e) {
            log.error("导出task模版异常:{}",e.getMessage());
        }
    }

    @Override
    public Map<Integer, String[]> getDropDownInfo(ExcelCommentField field) {
        //下拉填充数据
        Map<Integer,String []> mapDropDown = new HashMap<>();
        String[] sprintInfo = getStoryNamesBySprintId(field.getSprintId());
        // 任务类型
        String[] taskTypeNames = TaskTypeEnum.getNames();
        mapDropDown.put(0,sprintInfo);
        mapDropDown.put(3,taskTypeNames);
        return mapDropDown;
    }

    /**
     * 功能描述:获取迭代下的故事名
     * @param sprintId 迭代id
     * @return java.lang.String[]
     * @date 2021/2/1
     */
    private String[] getStoryNamesBySprintId(Long sprintId) {
        IssueExample example = new IssueExample();
        example.setOrderByClause(" priority desc,create_time desc");
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
}

package com.yusys.agile.deployrecord.service.impl;

import com.alibaba.excel.util.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yusys.agile.deployrecord.service.DeployRecordService;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.cicd.feign.api.tools.IDeployCodeLogApi;
import com.yusys.cicd.feign.api.tools.IToolsChangeApi;
import com.yusys.cicd.model.tools.dto.TDeployCodeLogDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeployRecordServiceImpl implements DeployRecordService {



    @Resource
    private IToolsChangeApi toolsChangeApi;
    @Resource
    private IDeployCodeLogApi iDeployCodeLogApi;
    @Resource
    private IssueService issueService;

    @Override
    public PageInfo queryDeployRecord(long issueId, Byte issueType, Integer pageNum, Integer pageSize) {

        PageInfo pageInfo = new PageInfo();
        pageNum = null == pageNum ? 1 : pageNum;
        pageSize = null == pageSize ? 20 : pageSize;
        List<TDeployCodeLogDTO > logDTOS =  Lists.newArrayList();
        List<Long> longList = Lists.newArrayList();
        List<Long> longListParam = Lists.newArrayList();
        longListParam.add(issueId);

        //issueType = 2 feature
        if(IssueTypeEnum.TYPE_FEATURE.CODE.compareTo(issueType)>0){
            longList = issueService.getIssueIds(longListParam);
            longListParam = Lists.newArrayList();
            longListParam.addAll(longList);
        }
        //issueType = 3 故事
        if(IssueTypeEnum.TYPE_STORY.CODE.compareTo(issueType)>0){
            longList = issueService.getIssueIds(longListParam);
            longListParam = Lists.newArrayList();
            longListParam.addAll(longList);
        }
        //issueType = 4 任务
        if(IssueTypeEnum.TYPE_TASK.CODE.compareTo(issueType)>0){
            longList = issueService.getIssueIds(longListParam);
            longListParam = Lists.newArrayList();
            longListParam.addAll(longList);
        }
        if(CollectionUtils.isEmpty(longListParam)){
            return pageInfo;
        }
        List<String> commitIdList = toolsChangeApi.getCommitChange(longListParam);
        if(CollectionUtils.isEmpty(commitIdList)){
            return pageInfo;
        }
        Map<String, Object> models = new HashMap<>();
        models.put("commitIdList",commitIdList);
        models.put("pageNum",pageNum);
        models.put("pageSize",pageSize);
        pageInfo   =  iDeployCodeLogApi.queryByCommitId(models);
        return pageInfo;
    }
}

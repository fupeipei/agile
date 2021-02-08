package com.yusys.agile.issue.service;

import com.yusys.agile.issue.dto.CustomizeIssueDTO;

import java.util.List;

public interface CustomizeIssueService {

    /**
     *   : zhangtao11
     * @Date: 2020/08/17 11:22
     * @Description: 查询关联的工作项
     * @param issueIds
     * @Return: java.util.List<com.yusys.agile.issue.dto.CustomizeIssueDTO>
     */
    List<CustomizeIssueDTO> getRelatedIssues(List<Long> issueIds);

    /**
     * @description 根据任务或缺陷编号查询feature
     *  
     * @date 2020/09/17
     * @param issueId
     * @return
     */
    CustomizeIssueDTO getFeatureByTaskOrFaultId(Long issueId);
}

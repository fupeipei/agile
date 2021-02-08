package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.IssueSystemRelp;

import java.util.List;

public interface IssueSystemRelpService {
    /**
     *
     * @Date: 2020/5/13 11:04
     * @Description: 批量插入工作项和系统关联关系
     * @Param: issueId
     * @param systemIds
     * @Return: void
     */
    void batchInsert(Long issueId,List<Long> systemIds);

    /**
     *
     * @Date: 2020/5/13 18:09
     * @Description: 查询工作项关联的系统列表
     * @Param: * @param issueId
     * @Return: java.util.List<com.yusys.agile.issue.domain.IssueSystemRelp>
     */
    List<IssueSystemRelp> listIssueSystemRelp(Long issueId);

    /**
     *
     * @Date: 2020/5/13 18:11
     * @Description: 删除工作项关联的系统
     * @Param: * @param issueId
     * @Return: void
     */
    void deleteByIssueId(Long issueId);


    /**
     *
     * @Date: 2020/5/13 18:09
     * @Description: 查询工作项关联的系统列表
     * @Param: * @param issueId
     * @Return: java.util.List<com.yusys.agile.issue.domain.IssueSystemRelp>
     */
    List<IssueSystemRelp> listIssueSystemRelpByProjectId(List<Long> issueIds);
}

package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.IssueModuleRelp;

import java.util.List;

public interface IssueModuleRelpService {

    /**
     * @param issueId
     * @param systemIds
     * @Date 2020-06-24
     * @Description 批量插入工作项和模块关联关系
     */
    void batchInsert(Long issueId, List<Long> systemIds);

    /**
     * @param issueId
     * @return List<IssueModuleRelp>
     * @Date 2020-06-24
     * @Description 查询工作项关联的模块列表
     */
    List<IssueModuleRelp> listIssueModuleRelp(Long issueId);

    /**
     * @Date: 2021/2/24 17:23
     * @Description: 删除工作项关联的模块
     * @Param: * @param issueId
     * @Return: void
     */
    void deleteByIssueId(Long issueId);
}

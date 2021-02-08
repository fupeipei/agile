package com.yusys.agile.issue.service;

/**
 * @ClassName BjCleanIssueService
 * @Description TODO
 *
 * @Date 2020/11/30 下午12:31
 * @Version 1.0
 */
public interface BjCleanIssueService {

    /**
     * 按年份查询需求数据，进行清洗去重，排除无需部署内容。
     */
    void cleanBjIssueData();

    /**
     * 查询排期每月无需部署需求列表(排除开发+配置类)
     */
    void cleanBjIssueDataByNoDeploy();
}

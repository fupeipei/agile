package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.CleanIssueData;
import com.yusys.agile.issue.domain.CleanIssueDataExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CleanIssueDataMapper {
    long countByExample(CleanIssueDataExample example);

    int deleteByExample(CleanIssueDataExample example);

    int insert(CleanIssueData record);

    int insertSelective(CleanIssueData record);

    List<CleanIssueData> selectByExample(CleanIssueDataExample example);

    int updateByExampleSelective(@Param("record") CleanIssueData record, @Param("example") CleanIssueDataExample example);

    int updateByExample(@Param("record") CleanIssueData record, @Param("example") CleanIssueDataExample example);

    /**
     * 根绝排期年份获取所有的需求列表
     * @param year
     * @return
     */
    List<CleanIssueData> getAllIssue(@Param("year") String year);

    /**
     *根据year年份排期模糊删除数据
     * @param year
     */
    void deleteCleanIssueByBizScheduling(@Param("bizPlanStatus") String bizPlanStatus,@Param("year") String year);

    /**
     *批量插入数据
     * @param issues
     */
    void batchInsert(@Param("issues") List<CleanIssueData> issues);

    List<CleanIssueData> getIssuesNoDeploy(@Param("reqScheduling") String reqScheduling);
}
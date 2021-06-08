package com.yusys.agile.issue.dao;


import com.yusys.agile.issue.domain.IssueStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


public interface IssueStatusMapper {

    IssueStatus getBySprintAndDate(@Param("sprintId") Long sprintId, @Param("sprintDate") Date sprintDate, @Param("issueType") Byte issueType);

    Integer create(IssueStatus status);

    Integer update(IssueStatus status);

    /**
     * @Author maxp2
     * @Date 2021/5/28
     * @Description 统计迭代下任务数
     * @param sprintId
     * @Return com.yusys.agile.issue.domain.IssueStatus
     */
    IssueStatus getTaskStatus(@Param("sprintId") Long sprintId);

    /**
     * @Author maxp2
     * @Date 2021/5/28
     * @Description 统计迭代下故事数
     * @param sprintId
     * @Return com.yusys.agile.issue.domain.IssueStatus
     */
    IssueStatus getStoryStatus(@Param("sprintId") Long sprintId);
}

package com.yusys.agile.issue.dao;


import com.yusys.agile.issue.domain.IssueStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Date;



public interface IssueStatusMapper {

    IssueStatus getBySprintAndDate(@Param("sprintId") Long sprintId, @Param("sprintDate") Date sprintDate,@Param("issueType")Byte issueType);

    Integer create(IssueStatus status);

    Integer update(IssueStatus status);
}

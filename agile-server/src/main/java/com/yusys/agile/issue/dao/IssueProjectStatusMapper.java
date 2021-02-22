package com.yusys.agile.issue.dao;


import com.yusys.agile.issue.domain.IssueProjectStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


public interface IssueProjectStatusMapper {

    Integer create(IssueProjectStatus status);

    Integer update(IssueProjectStatus status);

    IssueProjectStatus getByProjectAndDate(@Param("projectId") Long projectId, @Param("calculateDate") Date calculateDate, @Param("issueType") Byte issueType);
}

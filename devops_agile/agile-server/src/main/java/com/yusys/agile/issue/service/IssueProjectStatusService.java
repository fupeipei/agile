package com.yusys.agile.issue.service;


import com.yusys.agile.issue.domain.IssueProjectStatus;

import java.util.Date;


public interface IssueProjectStatusService {

    Integer create(IssueProjectStatus status);

    IssueProjectStatus getByProjectAndDate(Long projectId, Date calculateDate, Byte issueType);

    Integer update(IssueProjectStatus storyStatus);
}

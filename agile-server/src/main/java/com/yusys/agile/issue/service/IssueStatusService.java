package com.yusys.agile.issue.service;


import com.yusys.agile.issue.domain.IssueStatus;

import java.util.Date;


public interface IssueStatusService {

    Integer create(IssueStatus storyStatus);

    IssueStatus getBySprintAndDate(Long sprintId, Date sprintDate, Byte issueType);


    Integer update(IssueStatus storyStatus);
}

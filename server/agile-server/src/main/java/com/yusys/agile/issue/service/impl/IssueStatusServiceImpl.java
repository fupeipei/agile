package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueStatusMapper;
import com.yusys.agile.issue.domain.IssueStatus;
import com.yusys.agile.issue.service.IssueStatusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 仪表盘-迭代情况-工作项状况
 */
@Service
public class IssueStatusServiceImpl implements IssueStatusService {
    @Resource
    private IssueStatusMapper issueStatusDao;

    @Override
    public Integer create(IssueStatus issueStatus) {
        return issueStatusDao.create(issueStatus);
    }

    @Override
    public IssueStatus getBySprintAndDate(Long sprintId, Date sprintDate,Byte issueType) {
        return issueStatusDao.getBySprintAndDate(sprintId,sprintDate,issueType);
    }

    @Override
    public Integer update(IssueStatus storyStatus) {
        return issueStatusDao.update(storyStatus);
    }

}

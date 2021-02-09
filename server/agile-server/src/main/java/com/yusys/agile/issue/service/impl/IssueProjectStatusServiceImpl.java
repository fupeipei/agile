package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueProjectStatusMapper;
import com.yusys.agile.issue.domain.IssueProjectStatus;
import com.yusys.agile.issue.service.IssueProjectStatusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 仪表盘-迭代情况-工作项状况
 */
@Service
public class IssueProjectStatusServiceImpl implements IssueProjectStatusService {
    @Resource
    private IssueProjectStatusMapper issueProjectStatusMapper;

    @Override
    public Integer create(IssueProjectStatus issueStatus) {
        return issueProjectStatusMapper.create(issueStatus);
    }

    @Override
    public IssueProjectStatus getByProjectAndDate(Long projectId, Date calculateDate, Byte issueType) {
        return issueProjectStatusMapper.getByProjectAndDate(projectId, calculateDate, issueType);
    }

    @Override
    public Integer update(IssueProjectStatus storyStatus) {
        return issueProjectStatusMapper.update(storyStatus);
    }

}

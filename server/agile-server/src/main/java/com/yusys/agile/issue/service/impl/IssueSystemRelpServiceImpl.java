package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueSystemRelpMapper;
import com.yusys.agile.issue.domain.IssueSystemRelp;
import com.yusys.agile.issue.domain.IssueSystemRelpExample;
import com.yusys.agile.issue.service.IssueSystemRelpService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IssueSystemRelpServiceImpl implements IssueSystemRelpService {

    @Resource
    private IssueSystemRelpMapper issueSystemRelpMapper;

    @Override
    public void batchInsert(Long issueId, List<Long> systemIds) {
        List<IssueSystemRelp> issueSystemRelpList = new ArrayList<>();
        IssueSystemRelp issueSystemRelp;
        for (Long systemId : systemIds) {
            issueSystemRelp = new IssueSystemRelp();
            issueSystemRelp.setIssueId(issueId);
            issueSystemRelp.setSystemId(systemId);
            issueSystemRelpList.add(issueSystemRelp);
        }
        if (CollectionUtils.isNotEmpty(issueSystemRelpList)) {
            issueSystemRelpMapper.batchInsert(issueSystemRelpList, issueId);
        }
    }

    @Override
    public List<IssueSystemRelp> listIssueSystemRelp(Long issueId) {
        IssueSystemRelpExample example = new IssueSystemRelpExample();
        example.createCriteria().andIssueIdEqualTo(issueId);
        return issueSystemRelpMapper.selectByExample(example);
    }

    @Override
    public void deleteByIssueId(Long issueId) {
        issueSystemRelpMapper.deleteByIssueId(issueId);
    }


    @Override
    public List<IssueSystemRelp> listIssueSystemRelpByProjectId(List<Long> issueIds) {
        if (issueIds == null || (issueIds.isEmpty())) {
            return Lists.newArrayList();
        }
        IssueSystemRelpExample example = new IssueSystemRelpExample();
        example.createCriteria().andIssueIdIn(issueIds);
        return issueSystemRelpMapper.selectByExample(example);
    }
}

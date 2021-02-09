package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueModuleRelpMapper;
import com.yusys.agile.issue.domain.IssueModuleRelp;
import com.yusys.agile.issue.domain.IssueModuleRelpExample;
import com.yusys.agile.issue.service.IssueModuleRelpService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IssueModuleRelpServiceImpl implements IssueModuleRelpService {

    @Resource
    private IssueModuleRelpMapper issueModuleRelpMapper;

    @Override
    public void batchInsert(Long issueId, List<Long> moduleIds) {
        List<IssueModuleRelp> issueModuleRelpList = new ArrayList<>();
        IssueModuleRelp issueModuleRelp;
        for (Long moduleId : moduleIds) {
            issueModuleRelp = new IssueModuleRelp();
            issueModuleRelp.setIssueId(issueId);
            issueModuleRelp.setModuleId(moduleId);
            issueModuleRelpList.add(issueModuleRelp);
        }
        if (CollectionUtils.isNotEmpty(issueModuleRelpList)) {
            issueModuleRelpMapper.batchInsert(issueModuleRelpList, issueId);
        }
    }

    @Override
    public List<IssueModuleRelp> listIssueModuleRelp(Long issueId) {
        IssueModuleRelpExample example = new IssueModuleRelpExample();
        example.createCriteria().andIssueIdEqualTo(issueId);
        return issueModuleRelpMapper.selectByExample(example);
    }

    @Override
    public void deleteByIssueId(Long issueId) {
        issueModuleRelpMapper.deleteByIssueId(issueId);
    }
}

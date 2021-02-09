package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueAttachmentMapper;
import com.yusys.agile.issue.domain.IssueAttachment;
import com.yusys.agile.issue.domain.IssueAttachmentExample;
import com.yusys.agile.issue.dto.IssueAttachmentDTO;
import com.yusys.agile.issue.service.IssueAttachmentService;
import com.yusys.portal.util.code.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 9:20
 */
@Service
public class IssueAttachmentServiceImpl implements IssueAttachmentService {

    private static final Logger log = LoggerFactory.getLogger(IssueAttachmentServiceImpl.class);

    @Resource
    private IssueAttachmentMapper issueAttachmentMapper;

    @Override
    public List<IssueAttachmentDTO> listIssueAttachment(Long issueId) {
        List<IssueAttachmentDTO> issueAttachmentDTOList = new ArrayList<>();
        IssueAttachmentExample example = new IssueAttachmentExample();
        IssueAttachmentExample.Criteria criteria = example.createCriteria();
        criteria.andIssueIdEqualTo(issueId);
        List<IssueAttachment> issueAttachmentList = issueAttachmentMapper.selectByExample(example);

        try {
            issueAttachmentDTOList = ReflectUtil.copyProperties4List(issueAttachmentList, IssueAttachmentDTO.class);
        } catch (Exception e) {
            log.error("列表转换出错{}", e.getMessage());
        }
        return issueAttachmentDTOList;
    }

    @Override
    public int deleteAttach(Long attachmentId) {
        return issueAttachmentMapper.deleteByPrimaryKey(attachmentId);
    }

    @Override
    public int createBatchAttachment(List<IssueAttachment> newAttachment) {
        return issueAttachmentMapper.createBatchAttachment(newAttachment);
    }

    @Override
    public int updateByPrimaryKeySelective(IssueAttachment record) {
        return issueAttachmentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void deleteAttachmentByIssueId(Long issueId) {
        IssueAttachmentExample issueAttachmentExample = new IssueAttachmentExample();
        IssueAttachmentExample.Criteria criteria = issueAttachmentExample.createCriteria();
        criteria.andIssueIdEqualTo(issueId);
        issueAttachmentMapper.deleteByExample(issueAttachmentExample);
    }
}

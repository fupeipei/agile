package com.yusys.agile.issueTemplate.server.impl;


import com.yusys.agile.issueTemplate.domain.IssueCustomFieldV3;
import com.yusys.agile.issueTemplate.dao.IssueCustomFieldV3Mapper;
import com.yusys.agile.issueTemplate.server.IssueCustomFieldV3Service;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class IssueCustomFieldV3ServiceImpl implements IssueCustomFieldV3Service {

    @Resource
    private IssueCustomFieldV3Mapper issueCustomFieldV3Mapper;

    @Override
    public int deleteByPrimaryKey(Long extendId) {
        return issueCustomFieldV3Mapper.deleteByPrimaryKey(extendId);
    }

    @Override
    public int insert(IssueCustomFieldV3 record) {
        return issueCustomFieldV3Mapper.insert(record);
    }

    @Override
    public int insertOrUpdate(IssueCustomFieldV3 record) {
        return issueCustomFieldV3Mapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(IssueCustomFieldV3 record) {
        return issueCustomFieldV3Mapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(IssueCustomFieldV3 record) {
        return issueCustomFieldV3Mapper.insertSelective(record);
    }

    @Override
    public IssueCustomFieldV3 selectByPrimaryKey(Long extendId) {
        return issueCustomFieldV3Mapper.selectByPrimaryKey(extendId);
    }

    @Override
    public int updateByPrimaryKeySelective(IssueCustomFieldV3 record) {
        return issueCustomFieldV3Mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IssueCustomFieldV3 record) {
        return issueCustomFieldV3Mapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<IssueCustomFieldV3> list) {
        return issueCustomFieldV3Mapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<IssueCustomFieldV3> list) {
        return issueCustomFieldV3Mapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<IssueCustomFieldV3> list) {
        return issueCustomFieldV3Mapper.batchInsert(list);
    }

}


package com.yusys.agile.issueTemplate.server.impl;

import com.yusys.agile.issueTemplate.domain.IssueCustomRelationV3;
import com.yusys.agile.issueTemplate.dao.IssueCustomRelationV3Mapper;
import com.yusys.agile.issueTemplate.server.IssueCustomRelationV3Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IssueCustomRelationV3ServiceImpl implements IssueCustomRelationV3Service {

    @Resource
    private IssueCustomRelationV3Mapper issueCustomRelationV3Mapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return issueCustomRelationV3Mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IssueCustomRelationV3 record) {
        return issueCustomRelationV3Mapper.insert(record);
    }

    @Override
    public int insertOrUpdate(IssueCustomRelationV3 record) {
        return issueCustomRelationV3Mapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(IssueCustomRelationV3 record) {
        return issueCustomRelationV3Mapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(IssueCustomRelationV3 record) {
        return issueCustomRelationV3Mapper.insertSelective(record);
    }

    @Override
    public IssueCustomRelationV3 selectByPrimaryKey(Long id) {
        return issueCustomRelationV3Mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(IssueCustomRelationV3 record) {
        return issueCustomRelationV3Mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(IssueCustomRelationV3 record) {
        return issueCustomRelationV3Mapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<IssueCustomRelationV3> list) {
        return issueCustomRelationV3Mapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<IssueCustomRelationV3> list) {
        return issueCustomRelationV3Mapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<IssueCustomRelationV3> list) {
        return issueCustomRelationV3Mapper.batchInsert(list);
    }

}

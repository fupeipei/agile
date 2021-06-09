package com.yusys.agile.issueTemplate.server;


import com.yusys.agile.issueTemplate.domain.IssueCustomRelationV3;

import java.util.List;

public interface IssueCustomRelationV3Service {


    int deleteByPrimaryKey(Long id);

    int insert(IssueCustomRelationV3 record);

    int insertOrUpdate(IssueCustomRelationV3 record);

    int insertOrUpdateSelective(IssueCustomRelationV3 record);

    int insertSelective(IssueCustomRelationV3 record);

    IssueCustomRelationV3 selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IssueCustomRelationV3 record);

    int updateByPrimaryKey(IssueCustomRelationV3 record);

    int updateBatch(List<IssueCustomRelationV3> list);

    int updateBatchSelective(List<IssueCustomRelationV3> list);

    int batchInsert(List<IssueCustomRelationV3> list);

}

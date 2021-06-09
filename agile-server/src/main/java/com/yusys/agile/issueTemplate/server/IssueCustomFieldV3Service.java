package com.yusys.agile.issueTemplate.server;


import com.yusys.agile.issueTemplate.domain.IssueCustomFieldV3;

import java.util.List;

public interface IssueCustomFieldV3Service {


    int deleteByPrimaryKey(Long extendId);

    int insert(IssueCustomFieldV3 record);

    int insertOrUpdate(IssueCustomFieldV3 record);

    int insertOrUpdateSelective(IssueCustomFieldV3 record);

    int insertSelective(IssueCustomFieldV3 record);

    IssueCustomFieldV3 selectByPrimaryKey(Long extendId);

    int updateByPrimaryKeySelective(IssueCustomFieldV3 record);

    int updateByPrimaryKey(IssueCustomFieldV3 record);

    int updateBatch(List<IssueCustomFieldV3> list);

    int updateBatchSelective(List<IssueCustomFieldV3> list);

    int batchInsert(List<IssueCustomFieldV3> list);

}


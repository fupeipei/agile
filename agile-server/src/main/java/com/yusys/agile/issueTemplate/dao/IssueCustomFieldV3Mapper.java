package com.yusys.agile.issueTemplate.dao;

import com.yusys.agile.issueTemplate.domain.IssueCustomFieldV3;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueCustomFieldV3Mapper {
    /**
     * delete by primary key
     *
     * @param extendId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long extendId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(IssueCustomFieldV3 record);

    int insertOrUpdate(IssueCustomFieldV3 record);

    int insertOrUpdateSelective(IssueCustomFieldV3 record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(IssueCustomFieldV3 record);

    /**
     * select by primary key
     *
     * @param extendId primary key
     * @return object by primary key
     */
    IssueCustomFieldV3 selectByPrimaryKey(Long extendId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(IssueCustomFieldV3 record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(IssueCustomFieldV3 record);

    int updateBatch(List<IssueCustomFieldV3> list);

    int updateBatchSelective(List<IssueCustomFieldV3> list);

    int batchInsert(@Param("list") List<IssueCustomFieldV3> list);
}
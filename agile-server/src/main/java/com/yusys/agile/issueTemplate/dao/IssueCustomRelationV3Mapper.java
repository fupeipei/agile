package com.yusys.agile.issueTemplate.dao;

import com.yusys.agile.issueTemplate.domain.IssueCustomRelationV3;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueCustomRelationV3Mapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(IssueCustomRelationV3 record);

    int insertOrUpdate(IssueCustomRelationV3 record);

    int insertOrUpdateSelective(IssueCustomRelationV3 record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(IssueCustomRelationV3 record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    IssueCustomRelationV3 selectByPrimaryKey(Long id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(IssueCustomRelationV3 record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(IssueCustomRelationV3 record);

    int updateBatch(List<IssueCustomRelationV3> list);

    int updateBatchSelective(List<IssueCustomRelationV3> list);

    int batchInsert(@Param("list") List<IssueCustomRelationV3> list);

    /**
     * 查询模板绑定的自定义字段
     *
     * @param systemId  系统标识
     * @param issueType 需求类型
     * @return {@link List< IssueCustomRelationV3 >}
     */
    List<IssueCustomRelationV3> queryTemplateCustomField(@Param("systemId") Long systemId, @Param("issueType") int issueType);

    /**
     * 删除绑定
     *
     * @param systemId  系统标识
     * @param issueType 需求类型
     * @return {@link Integer}
     */
    Integer removeBinding(@Param("systemId") Long systemId, @Param("issueType") int issueType);
}
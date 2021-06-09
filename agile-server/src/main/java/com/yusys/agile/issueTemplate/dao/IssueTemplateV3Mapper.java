package com.yusys.agile.issueTemplate.dao;

import com.yusys.agile.issueTemplate.domain.IssueTemplateV3;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueTemplateV3Mapper {
    /**
     * delete by primary key
     *
     * @param issueTemplateId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long issueTemplateId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(IssueTemplateV3 record);

    int insertOrUpdate(IssueTemplateV3 record);

    int insertOrUpdateSelective(IssueTemplateV3 record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(IssueTemplateV3 record);

    /**
     * select by primary key
     *
     * @param issueTemplateId primary key
     * @return object by primary key
     */
    IssueTemplateV3 selectByPrimaryKey(Long issueTemplateId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(IssueTemplateV3 record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(IssueTemplateV3 record);

    int updateBatch(List<IssueTemplateV3> list);

    int updateBatchSelective(List<IssueTemplateV3> list);

    int batchInsert(@Param("list") List<IssueTemplateV3> list);

    /**
     * 查询问题模板由系统id和问题类型
     *
     * @param systemId  系统标识
     * @param issueType 问题类型
     * @return {@link IssueTemplateV3}
     * @author 张宇
     */
    IssueTemplateV3 queryIssueTemplateBySystemIdAndIssueType(@Param("systemId") Long systemId, @Param("issueType") int issueType);
}
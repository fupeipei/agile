package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.SIssueCustomRelation;
import com.yusys.agile.issue.domain.SIssueCustomRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SIssueCustomRelationMapper {
    long countByExample(SIssueCustomRelationExample example);

    int deleteByExample(SIssueCustomRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SIssueCustomRelation record);

    int insertSelective(SIssueCustomRelation record);

    List<SIssueCustomRelation> selectByExampleWithBLOBs(SIssueCustomRelationExample example);

    List<SIssueCustomRelation> selectByExample(SIssueCustomRelationExample example);

    SIssueCustomRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SIssueCustomRelation record, @Param("example") SIssueCustomRelationExample example);

    int updateByExampleWithBLOBs(@Param("record") SIssueCustomRelation record, @Param("example") SIssueCustomRelationExample example);

    int updateByExample(@Param("record") SIssueCustomRelation record, @Param("example") SIssueCustomRelationExample example);

    int updateByPrimaryKeySelective(SIssueCustomRelation record);

    int updateByPrimaryKeyWithBLOBs(SIssueCustomRelation record);

    int updateByPrimaryKey(SIssueCustomRelation record);
    /**
     * 按工作项类型，查询出 issue_custom_relation 中的fieldId
     * @author zhaofeng
     * @date 2021/6/10 11:01
     * @param issueType
     */
    List<Long> getAppliedByissueType(@Param("issueType") Byte issueType);
    /**
     * 按自定义字段id修改数据有效状态
     *
     * @param fieldId 自定义字段id
     * @param state   数据有效状态值
     * @author zhaofeng
     * @date 2021/6/3 17:23
     */
    void updateStateByFieldId(@Param("fieldId") Long fieldId, @Param("state") String state);

    /**
     * 按主键id修改数据有效状态
     *
     * @param id    主键
     * @param state 数据有效状态值
     * @author zhaofeng
     * @date 2021/6/9 15:17
     */
    void updateStateById(@Param("id") Long id, @Param("state") String state);

    /**
     * 按 issue_type 和 field_id 查询当前系统的关系
     *
     * @param issueType
     * @param id
     * @param fieldId
     * @author zhaofeng
     * @date 2021/6/11 14:02
     */
    SIssueCustomRelation selectByIssueTypeAndFieldId(@Param("issueType") Byte issueType, @Param("fieldId") Long fieldId, @Param("systemId") Long systemId);

    /**
     * 查询是否必填
     *
     * @param issueType 需求类型
     * @param fieldId   字段id
     * @param systemId  系统标识
     * @return {@link String}
     */
    String queryRequired(@Param("issueType") Byte issueType, @Param("fieldId") Long fieldId, @Param("systemId") Long systemId);
}
package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.UserAttention;
import com.yusys.agile.issue.domain.UserAttentionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAttentionMapper {
    long countByExample(UserAttentionExample example);

    int deleteByExample(UserAttentionExample example);

    int deleteByPrimaryKey(Long attentionId);

    int insert(UserAttention record);

    int insertSelective(UserAttention record);

    List<UserAttention> selectByExample(UserAttentionExample example);

    UserAttention selectByPrimaryKey(Long attentionId);

    int updateByExampleSelective(@Param("record") UserAttention record, @Param("example") UserAttentionExample example);

    int updateByExample(@Param("record") UserAttention record, @Param("example") UserAttentionExample example);

    int updateByPrimaryKeySelective(UserAttention record);

    int updateByPrimaryKey(UserAttention record);

    List<Long> selectIssueIdByExample(UserAttentionExample example);
}
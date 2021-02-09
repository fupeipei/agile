package com.yusys.agile.headerfielduser.dao;

import com.yusys.agile.headerfielduser.domain.HeaderFieldUser;
import com.yusys.agile.headerfielduser.domain.HeaderFieldUserExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface HeaderFieldUserMapper {
    long countByExample(HeaderFieldUserExample example);

    int deleteByExample(HeaderFieldUserExample example);

    int deleteByPrimaryKey(Long headerUserId);

    int insert(HeaderFieldUser record);

    int insertSelective(HeaderFieldUser record);

    List<HeaderFieldUser> selectByExampleWithBLOBs(HeaderFieldUserExample example);

    List<HeaderFieldUser> selectByExample(HeaderFieldUserExample example);

    HeaderFieldUser selectByPrimaryKey(Long headerUserId);

    int updateByExampleSelective(@Param("record") HeaderFieldUser record, @Param("example") HeaderFieldUserExample example);

    int updateByExampleWithBLOBs(@Param("record") HeaderFieldUser record, @Param("example") HeaderFieldUserExample example);

    int updateByExample(@Param("record") HeaderFieldUser record, @Param("example") HeaderFieldUserExample example);

    int updateByPrimaryKeySelective(HeaderFieldUser record);

    int updateByPrimaryKeyWithBLOBs(HeaderFieldUser record);

    int updateByPrimaryKey(HeaderFieldUser record);
}
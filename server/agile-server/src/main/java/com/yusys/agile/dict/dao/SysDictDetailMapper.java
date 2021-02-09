package com.yusys.agile.dict.dao;

import com.yusys.agile.dict.domain.SysDictDetail;
import com.yusys.agile.dict.domain.SysDictDetailExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysDictDetailMapper {
    long countByExample(SysDictDetailExample example);

    int deleteByExample(SysDictDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysDictDetail record);

    int insertSelective(SysDictDetail record);

    List<SysDictDetail> selectByExample(SysDictDetailExample example);

    SysDictDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysDictDetail record, @Param("example") SysDictDetailExample example);

    int updateByExample(@Param("record") SysDictDetail record, @Param("example") SysDictDetailExample example);

    int updateByPrimaryKeySelective(SysDictDetail record);

    int updateByPrimaryKey(SysDictDetail record);
}
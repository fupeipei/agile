package com.yusys.agile.requirement.dao;

import com.yusys.agile.requirement.domain.SysField;
import com.yusys.agile.requirement.domain.SysFieldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysFieldMapper {
    long countByExample(SysFieldExample example);

    int deleteByExample(SysFieldExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysField record);

    int insertSelective(SysField record);

    List<SysField> selectByExample(SysFieldExample example);

    SysField selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysField record, @Param("example") SysFieldExample example);

    int updateByExample(@Param("record") SysField record, @Param("example") SysFieldExample example);

    int updateByPrimaryKeySelective(SysField record);

    int updateByPrimaryKey(SysField record);
}
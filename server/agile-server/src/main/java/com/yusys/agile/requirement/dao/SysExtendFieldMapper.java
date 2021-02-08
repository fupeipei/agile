package com.yusys.agile.requirement.dao;

import com.yusys.agile.requirement.domain.SysExtendField;
import com.yusys.agile.requirement.domain.SysExtendFieldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysExtendFieldMapper {
    long countByExample(SysExtendFieldExample example);

    int deleteByExample(SysExtendFieldExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysExtendField record);

    int insertSelective(SysExtendField record);

    List<SysExtendField> selectByExample(SysExtendFieldExample example);

    SysExtendField selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysExtendField record, @Param("example") SysExtendFieldExample example);

    int updateByExample(@Param("record") SysExtendField record, @Param("example") SysExtendFieldExample example);

    int updateByPrimaryKeySelective(SysExtendField record);

    int updateByPrimaryKey(SysExtendField record);
}
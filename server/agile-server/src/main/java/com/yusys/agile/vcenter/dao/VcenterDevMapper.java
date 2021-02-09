package com.yusys.agile.vcenter.dao;

import com.yusys.agile.vcenter.domain.VcenterDev;
import com.yusys.agile.vcenter.domain.VcenterDevExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface VcenterDevMapper {
    long countByExample(VcenterDevExample example);

    int deleteByExample(VcenterDevExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VcenterDev record);

    int insertSelective(VcenterDev record);

    List<VcenterDev> selectByExample(VcenterDevExample example);

    VcenterDev selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VcenterDev record, @Param("example") VcenterDevExample example);

    int updateByExample(@Param("record") VcenterDev record, @Param("example") VcenterDevExample example);

    int updateByPrimaryKeySelective(VcenterDev record);

    int updateByPrimaryKey(VcenterDev record);
}
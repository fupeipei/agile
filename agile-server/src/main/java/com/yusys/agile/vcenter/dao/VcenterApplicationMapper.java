package com.yusys.agile.vcenter.dao;

import com.yusys.agile.vcenter.domain.VcenterApplication;
import com.yusys.agile.vcenter.domain.VcenterApplicationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VcenterApplicationMapper {
    long countByExample(VcenterApplicationExample example);

    int deleteByExample(VcenterApplicationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VcenterApplication record);

    int insertSelective(VcenterApplication record);

    List<VcenterApplication> selectByExample(VcenterApplicationExample example);

    VcenterApplication selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VcenterApplication record, @Param("example") VcenterApplicationExample example);

    int updateByExample(@Param("record") VcenterApplication record, @Param("example") VcenterApplicationExample example);

    int updateByPrimaryKeySelective(VcenterApplication record);

    int updateByPrimaryKey(VcenterApplication record);
}
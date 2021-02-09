package com.yusys.agile.vcenter.dao;

import com.yusys.agile.vcenter.domain.VcenterVmIp;
import com.yusys.agile.vcenter.domain.VcenterVmIpExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface VcenterVmIpMapper {
    long countByExample(VcenterVmIpExample example);

    int deleteByExample(VcenterVmIpExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VcenterVmIp record);

    int insertSelective(VcenterVmIp record);

    List<VcenterVmIp> selectByExample(VcenterVmIpExample example);

    VcenterVmIp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VcenterVmIp record, @Param("example") VcenterVmIpExample example);

    int updateByExample(@Param("record") VcenterVmIp record, @Param("example") VcenterVmIpExample example);

    int updateByPrimaryKeySelective(VcenterVmIp record);

    int updateByPrimaryKey(VcenterVmIp record);

    int updateByIp(String Ip);
}
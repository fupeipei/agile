package com.yusys.agile.zentao.dao;

import com.yusys.agile.zentao.domain.ZtModule;
import com.yusys.agile.zentao.domain.ZtModuleExample;
import com.yusys.portal.common.register.TargetDs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZtModuleMapper {

    long countByExample(ZtModuleExample example);

    int deleteByExample(ZtModuleExample example);

    int deleteByPrimaryKey(Integer id);
    @TargetDs("zentao")
    int insert(ZtModule record);
    @TargetDs("zentao")
    int insertSelective(ZtModule record);
    @TargetDs("zentao")
    List<ZtModule> selectByExampleWithBLOBs(ZtModuleExample example);
    @TargetDs("zentao")
    List<ZtModule> selectByExample(ZtModuleExample example);
    @TargetDs("zentao")
    ZtModule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZtModule record, @Param("example") ZtModuleExample example);

    int updateByExampleWithBLOBs(@Param("record") ZtModule record, @Param("example") ZtModuleExample example);

    int updateByExample(@Param("record") ZtModule record, @Param("example") ZtModuleExample example);

    int updateByPrimaryKeySelective(ZtModule record);

    int updateByPrimaryKeyWithBLOBs(ZtModule record);

    int updateByPrimaryKey(ZtModule record);
}
package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.domain.VersionCapacity;
import com.yusys.agile.versionmanager.domain.VersionCapacityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VersionCapacityMapper {
    long countByExample(VersionCapacityExample example);

    int deleteByExample(VersionCapacityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VersionCapacity record);

    int insertSelective(VersionCapacity record);

    List<VersionCapacity> selectByExample(VersionCapacityExample example);

    VersionCapacity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VersionCapacity record, @Param("example") VersionCapacityExample example);

    int updateByExample(@Param("record") VersionCapacity record, @Param("example") VersionCapacityExample example);

    int updateByPrimaryKeySelective(VersionCapacity record);

    int updateByPrimaryKey(VersionCapacity record);
}
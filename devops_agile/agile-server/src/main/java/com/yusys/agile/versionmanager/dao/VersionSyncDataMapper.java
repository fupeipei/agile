package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.domain.VersionSyncData;
import com.yusys.agile.versionmanager.domain.VersionSyncDataExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VersionSyncDataMapper {
    long countByExample(VersionSyncDataExample example);

    int deleteByExample(VersionSyncDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VersionSyncData record);

    int insertSelective(VersionSyncData record);

    List<VersionSyncData> selectByExample(VersionSyncDataExample example);

    VersionSyncData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VersionSyncData record, @Param("example") VersionSyncDataExample example);

    int updateByExample(@Param("record") VersionSyncData record, @Param("example") VersionSyncDataExample example);

    int updateByPrimaryKeySelective(VersionSyncData record);

    int updateByPrimaryKey(VersionSyncData record);

    List<VersionSyncData> getVersionSyncDataList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int updateVersionSyncSuccessDatas(VersionSyncData record);

    int batchInsertVersionSyncData(@Param("list") List<VersionSyncData> list);
}
package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.domain.VersionIssueSyncData;
import com.yusys.agile.versionmanager.domain.VersionIssueSyncDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VersionIssueSyncDataMapper {
    long countByExample(VersionIssueSyncDataExample example);

    int deleteByExample(VersionIssueSyncDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VersionIssueSyncData record);

    int insertSelective(VersionIssueSyncData record);

    List<VersionIssueSyncData> selectByExample(VersionIssueSyncDataExample example);

    VersionIssueSyncData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VersionIssueSyncData record, @Param("example") VersionIssueSyncDataExample example);

    int updateByExample(@Param("record") VersionIssueSyncData record, @Param("example") VersionIssueSyncDataExample example);

    int updateByPrimaryKeySelective(VersionIssueSyncData record);

    int updateByPrimaryKey(VersionIssueSyncData record);
}
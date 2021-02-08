package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.domain.VersionFeatureSyncData;
import com.yusys.agile.versionmanager.domain.VersionFeatureSyncDataExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VersionFeatureSyncDataMapper {
    long countByExample(VersionFeatureSyncDataExample example);

    int deleteByExample(VersionFeatureSyncDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VersionFeatureSyncData record);

    int insertSelective(VersionFeatureSyncData record);

    List<VersionFeatureSyncData> selectByExample(VersionFeatureSyncDataExample example);

    VersionFeatureSyncData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VersionFeatureSyncData record, @Param("example") VersionFeatureSyncDataExample example);

    int updateByExample(@Param("record") VersionFeatureSyncData record, @Param("example") VersionFeatureSyncDataExample example);

    int updateByPrimaryKeySelective(VersionFeatureSyncData record);

    int updateByPrimaryKey(VersionFeatureSyncData record);

    int batchSaveVersionFeatureSyncData(@Param("featureSyncDataList") List<VersionFeatureSyncData> featureSyncDataList);

    List<VersionFeatureSyncData> getRequireBranchSyncList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
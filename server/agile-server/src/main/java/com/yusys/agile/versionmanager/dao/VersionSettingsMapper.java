package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.domain.VersionSettings;
import com.yusys.agile.versionmanager.domain.VersionSettingsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VersionSettingsMapper {
    long countByExample(VersionSettingsExample example);

    int deleteByExample(VersionSettingsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VersionSettings record);

    int insertSelective(VersionSettings record);

    List<VersionSettings> selectByExample(VersionSettingsExample example);

    VersionSettings selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VersionSettings record, @Param("example") VersionSettingsExample example);

    int updateByExample(@Param("record") VersionSettings record, @Param("example") VersionSettingsExample example);

    int updateByPrimaryKeySelective(VersionSettings record);

    int updateByPrimaryKey(VersionSettings record);
}
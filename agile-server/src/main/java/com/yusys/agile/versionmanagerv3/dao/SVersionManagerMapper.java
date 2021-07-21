package com.yusys.agile.versionmanagerv3.dao;

import com.yusys.agile.versionmanagerV3.SVersionManagerDTO;
import com.yusys.agile.versionmanagerv3.domain.SVersionManager;
import com.yusys.agile.versionmanagerv3.domain.SVersionManagerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SVersionManagerMapper {
    long countByExample(SVersionManagerExample example);

    int deleteByExample(SVersionManagerExample example);

    int deleteByPrimaryKey(Long versionManagerId);

    int insert(SVersionManager record);

    int insertSelective(SVersionManager record);

    List<SVersionManager> selectByExampleWithBLOBs(SVersionManagerExample example);

    List<SVersionManager> selectByExample(SVersionManagerExample example);

    SVersionManager selectByPrimaryKey(Long versionManagerId);

    int updateByExampleSelective(@Param("record") SVersionManager record, @Param("example") SVersionManagerExample example);

    int updateByExampleWithBLOBs(@Param("record") SVersionManager record, @Param("example") SVersionManagerExample example);

    int updateByExample(@Param("record") SVersionManager record, @Param("example") SVersionManagerExample example);

    int updateByPrimaryKeySelective(SVersionManager record);

    int updateByPrimaryKeyWithBLOBs(SVersionManager record);

    int updateByPrimaryKey(SVersionManager record);

    List<SVersionManagerDTO> queryVersionManagerListByExample(String searchKey, Long systemId);
}
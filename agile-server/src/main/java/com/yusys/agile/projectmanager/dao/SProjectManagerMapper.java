package com.yusys.agile.projectmanager.dao;

import com.yusys.agile.projectmanager.domain.SProjectManager;
import com.yusys.agile.projectmanager.domain.SProjectManagerExample;
import java.util.List;

import com.yusys.agile.projectmanager.dto.ProjectManagerDto;
import org.apache.ibatis.annotations.Param;

public interface SProjectManagerMapper {
    long countByExample(SProjectManagerExample example);

    int deleteByExample(SProjectManagerExample example);

    int deleteByPrimaryKey(Long projectId);

    int insert(SProjectManager record);

    int insertSelective(SProjectManager record);

    List<SProjectManager> selectByExampleWithBLOBs(SProjectManagerExample example);

    List<SProjectManager> selectByExample(SProjectManagerExample example);

    SProjectManager selectByPrimaryKey(Long projectId);

    int updateByExampleSelective(@Param("record") SProjectManager record, @Param("example") SProjectManagerExample example);

    int updateByExampleWithBLOBs(@Param("record") SProjectManager record, @Param("example") SProjectManagerExample example);

    int updateByExample(@Param("record") SProjectManager record, @Param("example") SProjectManagerExample example);

    int updateByPrimaryKeySelective(SProjectManager record);

    int updateByPrimaryKeyWithBLOBs(SProjectManager record);

    int updateByPrimaryKey(SProjectManager record);

    List<ProjectManagerDto> queryProjectManagerList(@Param("searchKey") String searchKey);

    List<ProjectManagerDto> queryProjectManagerListByUserId(@Param("userId") Long userId);
}
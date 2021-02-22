package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.domain.VersionManager;
import com.yusys.agile.versionmanager.domain.VersionManagerExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface VersionManagerMapper {
    long countByExample(VersionManagerExample example);

    int deleteByExample(VersionManagerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VersionManager record);

    int insertSelective(VersionManager record);

    List<VersionManager> selectByExample(VersionManagerExample example);

    VersionManager selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VersionManager record, @Param("example") VersionManagerExample example);

    int updateByExample(@Param("record") VersionManager record, @Param("example") VersionManagerExample example);

    int updateByPrimaryKeySelective(VersionManager record);

    int updateByPrimaryKey(VersionManager record);

    /**
     * 更新版本计划交付个数
     *
     * @param versionPlanData
     * @Date:2020-09-16
     */
    void updatePlanDeliveryNumber(VersionManager versionPlanData);

    VersionManager selectSyncVersionPlanInfoByPrimaryKey(Long versionPlanId);

    /**
     * 根据项目id获取版本计划
     *
     * @param projectId
     * @return
     * @date 2021/3/30
     */
    List<VersionManager> selectGroupVersion(@Param("projectId") Long projectId);


    /**
     * @param projectId
     * @param versionName
     * @Date 2021/2/18
     * @Description 根据需求名称及客户需求编号获取版本计划
     * @Return java.util.List<com.yusys.agile.versionmanager.domain.VersionManager>
     */
    List<VersionManager> selectVersionByBizNumAndTitle(@Param("projectId") Long projectId, @Param("versionName") String versionName,
                                                       @Param("bizBacklogIds") List<Long> bizBacklogIds);

}
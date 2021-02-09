package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.dto.VersionIssueDTO;
import com.yusys.agile.versionmanager.dto.VersionIssueSysExtendFieldDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2021/2/11
 */
public interface VersionIssueSysExtendMapper {

    /**
     * @param versionIdList
     * @Date 2021/2/8
     * @Description 获取版本下的业务需求信息及扩展字段信息
     * @Return java.util.List<com.yusys.agile.versionmanager.dto.VersionIssueDTO>
     */
    List<VersionIssueDTO> selectEpicIssueByVersionId(@Param("versionIds") List<Long> versionIdList);


    /**
     * @param issueIds
     * @Date 2021/2/8
     * @Description 获取业务需求下的研发需求信息及扩展字段信息
     * @Return java.util.List<com.yusys.agile.versionmanager.dto.VersionIssueSysExtendFieldDetailDTO>
     */
    List<VersionIssueSysExtendFieldDetailDTO> selectFeatureIssueByEpicId(@Param("issueIds") List<Long> issueIds);
}

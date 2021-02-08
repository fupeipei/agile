package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.dto.VersionIssueDTO;
import com.yusys.agile.versionmanager.dto.VersionIssueSysExtendFieldDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *    maxp
 * @Date: 2020/11/11
 */
public interface VersionIssueSysExtendMapper {

    /**
     *    maxp
     * @Date 2020/11/17
     * @Description 获取版本下的业务需求信息及扩展字段信息
     * @param versionIdList
     * @Return java.util.List<com.yusys.agile.versionmanager.dto.VersionIssueDTO>
     */
    List<VersionIssueDTO> selectEpicIssueByVersionId(@Param("versionIds") List<Long> versionIdList);


    /**
     *    maxp
     * @Date 2020/11/17
     * @Description 获取业务需求下的研发需求信息及扩展字段信息
     * @param issueIds
     * @Return java.util.List<com.yusys.agile.versionmanager.dto.VersionIssueSysExtendFieldDetailDTO>
     */
    List<VersionIssueSysExtendFieldDetailDTO> selectFeatureIssueByEpicId(@Param("issueIds") List<Long> issueIds);
}

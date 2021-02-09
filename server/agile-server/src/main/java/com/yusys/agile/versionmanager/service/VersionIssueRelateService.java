package com.yusys.agile.versionmanager.service;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueStringDTO;
import com.yusys.agile.versionmanager.domain.VersionIssueRelate;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName VersionIssueRelateService
 * @Description TODO
 * @Date 2020/8/21 20:45
 * @Version 1.0
 */
public interface VersionIssueRelateService {

    /**
     * 功能描述  根据工作项类型或者迭代ID获取为关联版本计划的工作项
     *
     * @param versionId     版本ID
     * @param issueType     工作项类型
     * @param sprintId      迭代ID
     * @param BAPerson
     * @param bizNum
     * @param formalReqCode
     * @param pageSize
     * @param pageNum
     * @param idOrTitle
     * @param securityDTO
     * @return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     * @date 2020/10/21
     */


    List<IssueDTO> getVersionNoRelateIssues(Long versionId, Byte issueType, Long sprintId, String BAPerson, String bizNum, String formalReqCode, String askLineTime, String relatedSystem, Integer pageSize, Integer pageNum, String idOrTitle, SecurityDTO securityDTO);

    /**
     * 版本计划关联工作项
     *
     * @param issueRelates
     * @param securityDTO
     * @return
     */
    void bindVersionRelateIssues(List<VersionIssueRelate> issueRelates, SecurityDTO securityDTO, String province);


    /**
     * 根据工作项类型或者迭代ID获取已关联版本计划的工作项
     *
     * @param versionId   版本ID
     * @param securityDTO
     * @return
     */
    PageInfo getVersionRelateIssues(Long versionId, Map<String, Object> map, SecurityDTO securityDTO) throws Exception;


    /**
     * 功能描述 移除已关联的工作项
     *
     * @param issueIds
     * @param securityDTO
     * @return void
     * @date 2020/8/28
     */
    void removeVersionRelateIssues(Long oldVersionId, Long newVersionId, List<Long> issueIds, SecurityDTO securityDTO);

    /**
     * 根据版本移除已关联的工作项
     * 功能描述
     *
     * @param versionId
     * @return void
     * @date 2020/8/31
     */
    void removeRelateIssuesByVersionId(Long versionId);

    /**
     * 功能描述 获取版本计划中绑定的需求与对应的系统分支列表的映射关系
     *
     * @param issueRelates
     * @return java.util.Map<java.lang.Long, java.util.List < java.lang.Long>>
     * @date 2021/3/19
     */
    Map<Long, List<Long>> getBindingReqIdToSystemBranchIdListMap(List<VersionIssueRelate> issueRelates);

    /**
     * 功能描述 根据版本id和项目id获取工作项目id
     *
     * @param issueStringDTO
     * @param projectId
     * @return
     * @date 2021/3/18
     */
    List<VersionIssueRelate> queryVersionIssueRelatList(IssueStringDTO issueStringDTO, Long projectId);


    /**
     * 功能描述  根据工作项Id查询版本计划ID
     *
     * @param issueId
     * @return com.yusys.agile.versionmanager.domain.VersionIssueRelate
     * @date 2020/10/19
     */
    VersionIssueRelate queryVersionIssueRelate(Long issueId);


    void audit(List<IssueDTO> issueDTONoDeployList, Long versionPlanId);

}

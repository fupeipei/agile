package com.yusys.agile.versionmanager.service;

import com.yusys.agile.versionmanager.domain.VersionManager;
import com.yusys.agile.versionmanager.dto.ReasonDTO;
import com.yusys.agile.versionmanager.dto.VersionManagerDTO;
import com.alibaba.fastjson.JSONObject;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName VersionManagerService
 * @Description TODO
 * @Date 2021/2/5 16:15
 * @Version 1.0
 */
public interface VersionManagerService {

    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据项目ID和版本名称模糊查询版本计划
     * 2021/2/18 新增根据需求名称以及客户需求编号查询版本计划列表
     *
     * @param versionName
     * @param projectId
     * @return
     */
    List<VersionManagerDTO> getAllVersions(String versionName, Long projectId, String issueName, String bizNum, String approvalStatus);

    /**
     * 新增/编辑版本
     *
     * @param managerDTO
     * @param securityDTO
     * @return
     */
    void createOrUpdate(VersionManagerDTO managerDTO, SecurityDTO securityDTO);

    /**
     * 删除版本：根据版本ID删除版本
     *
     * @param versionId
     * @return
     */
    void deleteVersion(Long versionId, Long projectId);

    /**
     * 根据版本号获取版本计划
     *
     * @param versionId
     * @return
     */
    VersionManagerDTO getVersionInfo(Long versionId);

    int updateVersionPlanStatusAfterSendingReview(Long versionPlanId, String versionState, int reviewCount, Long userId);

    int updateSyncToRmpStatus(Long versionPlanId, Integer syncFailed);

    void syncVersionPlanInfo(Long versionPlanId, String tenantName);

    /**
     * 创建错误返回信息
     *
     * @param reason
     * @return
     */
    JSONObject createFailedResult(String reason);


    /**
     * 功能描述 校验局方需求管理平台返回的报文信息
     *
     * @param jsonObject
     * @return java.lang.String
     * @date 2021/3/18
     */
    String checkRequirementManagementPlatformResponseMsg(JSONObject jsonObject);

    /**
     * 功能描述
     *
     * @param
     * @return com.alibaba.fastjson.JSONObject
     * @date 2021/3/18
     */
    JSONObject createSuccessResult();


    /**
     * 功能描述 根据版本id获取版本状态
     *
     * @param versionPlanId
     * @return java.lang.String
     * @date 2021/3/18
     */
    String getVersionStateById(Long versionPlanId);

    /**
     * 根据项目id获取版本信息
     *
     * @param projectId 项目id
     * @return
     * @date 2021/3/18
     */
    List<VersionManagerDTO> getAllByVersionNameAndProjectId(String versionName, Integer pageNum, Integer pageSize, Long projectId);

    /**
     * 根据项目ID和版本名称模糊查询版本计划
     *
     * @param versionId
     * @return
     */
    List<VersionManagerDTO> getOtherVersionInfo(Long versionId);

    /**
     * 功能描述 更新版本状态
     *
     * @param versionPlanId
     * @param state
     * @return int
     * @date 2021/2/5
     */
    int updateVersionPlanStateById(Long versionPlanId, String state);

    /**
     * 功能描述 解绑原因大类
     *
     * @param
     * @return list
     * @date 2021/2/7
     */
    List<ReasonDTO> getReasonClassifyList();

    /**
     * 功能描述 获取解绑具体原因
     *
     * @param classifyId
     * @return list
     * @date 2021/2/7
     */
    List<ReasonDTO> getReasonClassifyValuesMap(Integer classifyId);

    /**
     * 根据项目id获取版本计划
     *
     * @param projectId
     * @return
     * @date 2021/2/7
     */
    List<VersionManagerDTO> getAllVersionInfo(Long projectId);


    /**
     * 根据部署类型、部署日期查询版本
     *
     * @param planDeployDate
     * @param deployType
     * @return
     */
    List<VersionManager> gerVersionInfoByDeployType(String planDeployDate, Byte deployType) throws Exception;

    /**
     * 功能描述
     *
     * @param newVersionPlanId
     * @return import com.yusys.agile.versionmanager.domain.VersionManager;
     * @date 2021/2/24
     */
    VersionManager versionInfoById(Long newVersionPlanId);

    /**
     * 功能描述 获取所有未发版的版本号
     *
     * @param
     * @return java.util.List<java.lang.Long>
     * @date 2021/2/27
     */
    List<Long> getVersionPlanByState();

    /**
     * 功能描述 获取所有版本号
     *
     * @param
     * @return java.util.List<java.lang.Long>
     * @date 2021/2/27
     */
    List<Long> getAllVersionPlan();

    /**
     * @param epicId
     * @return
     * @description 根据epicId查询版本管理信息
     * @date 2020/12/4
     */
    VersionManagerDTO queryVersionManageInfo(Long epicId);

    /**
     * 功能描述  计算版本下所有的epic数量及各个子系统数量
     *
     * @param
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @date 2020/12/29
     */
    Map<String, Integer> countIssue(Long versionPlanId);
}

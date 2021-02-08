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
 *
 * @Date   2021/2/5 16:15
 * @Version 1.0
 */
public interface VersionManagerService {

    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据项目ID和版本名称模糊查询版本计划
     * 2020/11/18 新增根据需求名称以及客户需求编号查询版本计划列表
     * @param versionName
     * @param projectId
     * @return
     */
    List<VersionManagerDTO> getAllVersions(String versionName, Long projectId,String issueName,String bizNum,String approvalStatus);

    /**
     * 新增/编辑版本
     * @param managerDTO
     * @param securityDTO
     * @return
     */
    void createOrUpdate(VersionManagerDTO managerDTO, SecurityDTO securityDTO);
    /**
     * 删除版本：根据版本ID删除版本
     * @param versionId
     * @return
     */
    void deleteVersion(Long versionId,Long projectId);

    /**
     * 根据版本号获取版本计划
     * @param versionId
     * @return
     */
    VersionManagerDTO getVersionInfo(Long versionId);

    int updateVersionPlanStatusAfterSendingReview(Long versionPlanId, String versionState, int reviewCount, Long userId);

    int updateSyncToRmpStatus(Long versionPlanId, Integer syncFailed);

    void syncVersionPlanInfo(Long versionPlanId, String tenantName);

    /**
     * 创建错误返回信息
     * @param reason
     * @return
     */
    JSONObject createFailedResult(String reason);



    /**
     *功能描述 校验局方需求管理平台返回的报文信息
     *
     * @date 2020/9/18
      * @param jsonObject
     * @return java.lang.String
     */
    String checkRequirementManagementPlatformResponseMsg(JSONObject jsonObject);

    /**
     *功能描述
     *
     * @date 2020/9/18
      * @param
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject createSuccessResult();


    /**
     *功能描述 根据版本id获取版本状态
     *
     * @date 2020/9/18
      * @param versionPlanId
     * @return java.lang.String
     */
    String getVersionStateById(Long versionPlanId);

    /**
     * 根据项目id获取版本信息
     * @param projectId 项目id
     *
     * @date 2020/9/18
     * @return
     */
    List<VersionManagerDTO> getAllByVersionNameAndProjectId(String versionName,Integer pageNum,Integer pageSize,Long projectId);

    /**
     * 根据项目ID和版本名称模糊查询版本计划
     * @param versionId
     * @return
     */
    List<VersionManagerDTO> getOtherVersionInfo(Long versionId);

    /**
     *功能描述 更新版本状态
     *
     * @date  2021/2/5
      * @param versionPlanId
     * @param state
     * @return int
     */
    int updateVersionPlanStateById(Long versionPlanId, String state);

    /**
     *功能描述 解绑原因大类
     *
     * @date  2021/2/7
      * @param
     * @return list
     */
    List<ReasonDTO>  getReasonClassifyList();

    /**
     *功能描述 获取解绑具体原因
     *
     * @date  2021/2/7
      * @param classifyId
     * @return list
     */
    List<ReasonDTO> getReasonClassifyValuesMap(Integer classifyId);

    /**
     * 根据项目id获取版本计划
     *
     * @date 2021/2/7
     * @param projectId
     * @return
     */
    List<VersionManagerDTO> getAllVersionInfo(Long projectId);


    /**
     * 根据部署类型、部署日期查询版本
     * @param planDeployDate
     * @param deployType
     * @return
     */
    List<VersionManager> gerVersionInfoByDeployType(String planDeployDate , Byte deployType) throws Exception;

  /**
   *功能描述
   *
   * @date 2020/11/24
    * @param newVersionPlanId
   * @return import com.yusys.agile.versionmanager.domain.VersionManager;
   */
    VersionManager versionInfoById(Long newVersionPlanId);

    /**
     *功能描述 获取所有未发版的版本号
     *
     * @date 2020/11/27
      * @param
     * @return java.util.List<java.lang.Long>
     */
    List<Long> getVersionPlanByState();

    /**
     *功能描述 获取所有版本号
     *
     * @date 2020/11/27
     * @param
     * @return java.util.List<java.lang.Long>
     */
    List<Long> getAllVersionPlan();

    /**
     * @param epicId
     * @return
     * @description 根据epicId查询版本管理信息
     *
     * @date 2020/12/4
     */
    VersionManagerDTO queryVersionManageInfo(Long epicId);

    /**
     *功能描述  计算版本下所有的epic数量及各个子系统数量
     *
     * @date 2020/12/29
      * @param
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     */
    Map<String,Integer> countIssue(Long versionPlanId);
}

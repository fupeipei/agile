package com.yusys.agile.requirement.service;

import com.yusys.agile.requirement.domain.SysExtendFieldDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysExtendFieldDetailService {


  int save(SysExtendFieldDetail sysExtendFieldDetail);

  /**
   *   :shenfeng
   * 根据工作项id列表获取工作项扩展字段列表
   * @param issueIds
   * @return
   */
  List<SysExtendFieldDetail> getIssueExtendDetailList(List<Long> issueIds);

  /**
   *    maxp
   * @Date 2020/10/19
   * @Description 批量插入扩展字段信息
   * @param sysExtendFieldDetailList
   * @Return void
   */
  void batchSave(List<SysExtendFieldDetail> sysExtendFieldDetailList);

  /**
   *   :shenfeng
   * @Date:2020-09-16
   * 根据业务需求编号更新审批状态
   * @param bizNumList
   * @param approveFailedStatus
   */
  void updateApprovalStatusByBizNums(List<String> bizNumList, Byte approveFailedStatus);

  /**
   * 根据业需需求编号查询工作项扩展字段
   * @param bizNumList
   * @return
   */
  List<SysExtendFieldDetail> getEpicSysExtendDetailByBizNums(List<String> bizNumList);

  /**
   *功能描述 统计没有审批结果的业务需求的个数
   *
   * @date 2020/9/18
    * @param bizBacklogIds
   * @return int
   */
  int countEmptyApprovalResultByBizBacklogIds(List<Long> bizBacklogIds);

  /**
   *功能描述 统计是否有审批不通过的需求
   *
   * @date 2020/9/18
    * @param bizBacklogIds
   * @return int
   */
  int countApprovalFailedBizBacklog(@Param("bizBacklogIds") List<Long> bizBacklogIds);

  /**
   *功能描述  获取issue的某个扩展字段对象
   *
   * @date 2020/9/18
    * @param bizBacklogId
   * @param fieldId
   * @return com.yusys.agile.requirement.domain.SysExtendFieldDetail
   */
  SysExtendFieldDetail getSysExtendFieldDetail(Long bizBacklogId, String fieldId);
  /**
   *功能描述  获取issue的某个扩展字段对象
   *
   * @date 2020/9/18
   * @param bizBacklogIds
   * @param fieldId
   * @return com.yusys.agile.requirement.domain.SysExtendFieldDetail
   */
  List<SysExtendFieldDetail> getSysExtendFieldDetails(List<Long> bizBacklogIds, String  fieldId);
  /**
   *功能描述 更新
   *
   * @date 2020/9/18
    * @param sysExtendFieldDetail
   * @return int
   */
  int update(SysExtendFieldDetail sysExtendFieldDetail);

  /**
   *功能描述 重置局方审批结果
   *
   * @date  2021/2/5
    * @param notUnbindingApprovalFailedBizBacklogIds
   * @param approveFailed
   * @return int
   */
  int updateApproveResultByPKs(List<Long> notUnbindingApprovalFailedBizBacklogIds, String approveFailed);

  /**
   *功能描述 更新审批结束时间
   *
   * @date  2021/2/5
    * @param failedBizBacklogIds
   * @param date
   * @return void
   */
  void updateApprovalEndTime(List<Long> failedBizBacklogIds, Date date);

  /**
   *功能描述 重置实际要求上线时间
   *
   * @date  2021/2/5
    * @param approvalSuccessUnbindingBizBacklogIds
   * @return void
   */
  void resetActualAskLineTimeByPKs(List<Long> approvalSuccessUnbindingBizBacklogIds);

  /**
   *功能描述 重置审批状态 审批开始时间  审批结束时间 局方审批结果 实际要求上线时间
   *
   * @date  2021/2/5
    * @param bindingBizBacklogIds
   * @return void
   */
  void resetApprovalAllByPKs(List<Long> bindingBizBacklogIds);

  /**
   *功能描述 审批通过后置需求的部署类型为版本计划的部署类型
   *
   * @date  2021/2/5
    * @param deployType
   * @param successBizBacklogIds
   * @return int
   */
  int updateSuccessBizBacklogDeployType(Byte deployType, List<Long> successBizBacklogIds);

  /**
   *功能描述 重置需求审批状态为未审核
   *
   * @date  2021/2/5
    * @param unbindingBizBacklogId
   * @return void
   */
  void resetUnbindingBizBacklogByPk(Long unbindingBizBacklogId);

  /**
   *功能描述 更新审批开始时间和局方审批状态
   *
   * @date 2020/9/25
    * @param reviewingBizBacklogIds
   * @return void
   */
  void updateApproveStartTimeAndApproveState(List<Long> reviewingBizBacklogIds,String startTime);

  /**
   * @description 查询业务需求扩展信息
   *  
   * @date  2021/2/7
   * @param issueId
   * @return
   */
  List<SysExtendFieldDetail> getSysExtendFieldDetail(Long issueId);

  /**
   *    maxp
   * @Date 2020/10/19
   * @Description 批量更新需求扩展信息
   * @param sysExtendFieldDetailListUpdate
   * @Return int
   */
  int batchUpdate(List<SysExtendFieldDetail> sysExtendFieldDetailListUpdate);

  /**
    *功能描述  根据需求Ids，与fieldIds查询扩展字段的
    *
    * @date 2020/10/21
    * @param bizBacklogIds
   * @param sysExtendFieldDetails
    * @return java.util.List<com.yusys.agile.requirement.domain.SysExtendFieldDetail>
   */
  List<Long> getSysExtendFieldDetailByIds(List<Long> bizBacklogIds, List<SysExtendFieldDetail>  sysExtendFieldDetails);

  /**
   *功能描述 删除某个扩展属性
   *
   * @date 2020/10/22
    * @param issueId
   * @param type
   * @return int
   */

  int delSysExtendFieldDetail(Long issueId, String type);

  /**
   *功能描述 根据bizNum获取epicId
   *
   * @date 2020/10/29
    * @param issueId
   * @return com.yusys.agile.requirement.domain.SysExtendFieldDetail
   */
  SysExtendFieldDetail getEpicIdByBizNum(String issueId);

  /**
   *功能描述  根据某个属性和值查询扩展表信息
   *
   * @date 2020/11/1
    * @param prop
   * @param propValue
   * @return java.util.List<com.yusys.agile.requirement.domain.SysExtendFieldDetail>
   */
  List<SysExtendFieldDetail> getSysExtendFieldDetailByProp(String prop, String propValue);

  /**
   *功能描述  根据工作项id查询扩展表中多个属性的值
   *
   * @date 2020/11/11
    * @param issueId
   * @param extendFieldIdList
   * @return java.util.List<com.yusys.agile.requirement.domain.SysExtendFieldDetail>
   */
  List<SysExtendFieldDetail> getSysExtendFieldDetailList(Long issueId, List<String> extendFieldIdList);

  /**
   *功能描述  根据需求Ids，与fieldIds查询扩展字段的
   *
   * @date 2020/10/21
   * @return java.util.List<com.yusys.agile.requirement.domain.SysExtendFieldDetail>
   */
  List<Long> getSysExtendFieldDetail(Map<String,Object> map, List issueIds);

  /**
   *功能描述 获取一组epic下某个属性的扩展属性值
   *
   * @date 2020/11/12
    * @param epicIdList
   * @param fieldId
   * @return java.util.List<com.yusys.agile.requirement.domain.SysExtendFieldDetail>
   */
  List<SysExtendFieldDetail> getSysExtendFieldDetailForEpicList(List<Long> epicIdList, String fieldId);

  /**
   *功能描述 根据局方需求编号获取分期
   *
   * @date 2020/11/18
    * @param formalReqCode
   * @return java.util.List<java.lang.String>
   */
  List<String> getPlanDeployDateListByFormalReqCode(String formalReqCode);

  /**
   *功能描述 根据局方需求编号获取客户需求编号列表
   *
   * @date 2021/2/7
    * @param formalReqCode
   * @return java.util.List<java.lang.String>
   */
  List<String> getBizNumListByFormalReqCode(String formalReqCode);

  /**
   * @description 批量更新需求实际上线时间
   *  
   * @date 2021/2/7
   * @param epicIdList
   * @param actualOnlineTime
   */
  void batchUpdateEpicActualOnlineTime(List<Long> epicIdList, String actualOnlineTime);

  /**
   *功能描述 校验局方需求编号下所有的客户需求是否都已上线
   *
   * @date 2020/12/14
    * @param formalReqCode
   * @return boolean
   */
  boolean checkEpicIsAllOnline(String formalReqCode);

  /**
   *功能描述 获取局方需求下面未上线的客户需求
   *
   * @date 2020/12/14
    * @param formalReqCode
   * @return java.util.List<com.yusys.agile.requirement.domain.SysExtendFieldDetail>
   */
  List<SysExtendFieldDetail> getNotOnlineEpic(String formalReqCode);

  /**
   *功能描述 获取需求计划是无需部署且待审核状态的需求（Epic）
   *
   * @date 2020/12/15
    * @param fieldId
   * @param fieldValue
   * @return java.util.List<com.yusys.agile.requirement.domain.SysExtendFieldDetail>
   */
  List<SysExtendFieldDetail> getNoDeployAndToBePublish(String fieldId, String fieldValue);
}

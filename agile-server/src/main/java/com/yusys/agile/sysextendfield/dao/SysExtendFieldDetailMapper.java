package com.yusys.agile.sysextendfield.dao;

import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysExtendFieldDetailMapper {
    long countByExample(SysExtendFieldDetailExample example);

    int deleteByExample(SysExtendFieldDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysExtendFieldDetail record);

    int insertSelective(SysExtendFieldDetail record);

    List<SysExtendFieldDetail> selectByExample(SysExtendFieldDetailExample example);

    SysExtendFieldDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysExtendFieldDetail record, @Param("example") SysExtendFieldDetailExample example);

    int updateByExample(@Param("record") SysExtendFieldDetail record, @Param("example") SysExtendFieldDetailExample example);

    int updateByPrimaryKeySelective(SysExtendFieldDetail record);

    int updateByPrimaryKey(SysExtendFieldDetail record);

    void batchSave(@Param("sysExtendFieldDetailList") List<SysExtendFieldDetail> sysExtendFieldDetailList);

    void updateApprovalStatusByBizNums(List<String> bizNumList, Byte approveFailedStatus);

    int updateSelective(SysExtendFieldDetail record);

    int countEmptyApprovalResultByBizBacklogIds(@Param("bizBacklogIds") List<Long> bizBacklogIds);

    int countApprovalFailedBizBacklog(@Param("bizBacklogIds") List<Long> bizBacklogIds);

    int updateSuccessBizBacklogDeployType(@Param("deployType") Byte deployType, @Param("successBizBacklogIds") List<Long> successBizBacklogIds);

    int updateByPrimaryKeySelectiveWithNull(SysExtendFieldDetail sysExtendFieldDetail);

    int batchUpdate(@Param(value = "list") List<SysExtendFieldDetail> sysExtendFieldDetailListUpdate);

    List<Long> getSysExtendFieldDetailByIds(@Param("bizBacklogIds") List<Long> bizBacklogIds, @Param("sysExtendFieldDetails") List<SysExtendFieldDetail> sysExtendFieldDetails);

    List<Map> getSysExtendFieldMap(Long issueId);

    List<Long> getSysExtendFieldDetail(@Param("map") Map<String, Object> map, @Param("issueIds") List<Long> issueIds);

    List<SysExtendFieldDetail> getTmpSysExtendFieldMap();

    List<SysExtendFieldDetail> getPlanDeployDateListByFormalReqCode(String formalReqCode);

    List<SysExtendFieldDetail> getBizNumListByFormalReqCode(String formalReqCode);

    /**
     * @param epicIdList
     * @description 批量删除需求实际上线时间
     * @date 2021/2/7
     */
    int batchDelEpicActualOnlineTime(List<Long> epicIdList);

    /**
     * @param sysExtendFieldDetails
     * @description 批量保存需求实际上线时间
     * @date 2021/2/7
     */
    int batchInsertEpicActualOnlineTime(List<SysExtendFieldDetail> sysExtendFieldDetails);

    void batchUpdateReqSchedulingByIssueIdList(@Param("notCanceledAndOnlineIssueIdList") List<Long> notCanceledAndOnlineIssueIdList, @Param("reqScheduling") String reqScheduling);

    List<SysExtendFieldDetail> getSysExtendFieldDetailByBizNum(String bizNum);

    List<SysExtendFieldDetail> checkEpicIsNotAllOnline(@Param("formalReqCode") String formalReqCode);

    /**
     * 功能描述 根据局方需求编号加上其他扩展属性查询扩展属性表
     *
     * @param formalReqCode
     * @param fieldId
     * @param fieldValue
     * @return java.util.List<com.yusys.agile.requirement.domain.SysExtendFieldDetail>
     * @date 2020/12/15
     */
    List<SysExtendFieldDetail> getSysExtendFieldDetailByFormalReqCodeAndProp(@Param("formalReqCode") String formalReqCode, @Param("fieldId") String fieldId, @Param("fieldValue") String fieldValue);

    List<SysExtendFieldDetail> getNoDeployAndToBePublish(String fieldId, String fieldValue);
}
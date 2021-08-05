package com.yusys.agile.risk.service;

import com.yusys.agile.risk.dto.RiskManagerDTO;
import com.alibaba.fastjson.JSONObject;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.Date;
import java.util.List;

/**
 * @ClassName RiskManagerService
 * @Description TODO
 * @Date 2020/8/11 11:35
 * @Version 1.0
 */
public interface RiskManagerService {

    /**
     * 根据标题模糊查询风险管理分页列表
     *
     * @param title       标题
     * @param pageNum     分页数
     * @param pageSize    分页条数
     * @return
     */
    List<RiskManagerDTO> getRiskPages(String title, Byte riskStatus, Integer pageNum, Integer pageSize, String projectName);

    /**
     * 创建/新增风险点
     *
     * @param riskManagerDTO
     * @param securityDTO
     */
    void create(RiskManagerDTO riskManagerDTO, SecurityDTO securityDTO);

    /**
     * 根据主键ID删除风险管理信息
     *
     * @param riskId
     */
    void delete(Long riskId);

    /**
     * 根据主键ID查询风险管理信息
     *
     * @param riskId
     * @return
     */
    RiskManagerDTO getRiskInfo(Long riskId);

    /**
     * 根据项目ID查询风险总条数和风险待处理条数
     *
     * @param projectId
     * @return
     */
    JSONObject getRiskAnalysisCount(Long projectId);

    /**
     * 获取风险管理列表
     *
     * @param riskStartTime 风险开始时间
     * @param riskEndTime   风险开始时间
     * @param securityDTO
     * @return
     */
    List<RiskManagerDTO> getRisksColl(Date riskStartTime, Date riskEndTime, SecurityDTO securityDTO);
}

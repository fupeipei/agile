package com.yusys.agile.risk.rest;

import com.yusys.agile.risk.dto.RiskManagerDTO;
import com.yusys.agile.risk.enums.RiskLevelEnum;
import com.yusys.agile.risk.enums.RiskStatusEnum;
import com.yusys.agile.risk.service.RiskManagerService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName RiskManagerController
 * @Description 风险管理控制类
 * @Date 2020/8/11 11:34
 * @Version 1.0
 */
@RestController
@RequestMapping("/risk")
public class RiskManagerController {

    @Autowired
    private RiskManagerService riskManagerService;

    /**
     * 获取风险管理分页列表
     *
     * @param title       标题
     * @param pageNum     分页数
     * @param pageSize    分页条数
     * @param projectName
     * @return
     */
    @GetMapping("/pages")
    public ControllerResponse getRiskPages(@RequestParam(value = "title", required = false) String title,
                                           @RequestParam(value = "riskStatus", required = false) Byte riskStatus,
                                           @RequestParam(value = "projectName", required = false) String projectName,
                                           @RequestParam(value = "projectId", required = false)Long projectId,
                                           @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<RiskManagerDTO> list = riskManagerService.getRiskPages(title, riskStatus, pageNum, pageSize, projectName,projectId);
        return ControllerResponse.success(new PageInfo<>(list));
    }

    /**
     * 创建/更新风险管理
     *
     * @param riskManagerDTO
     * @param securityDTO
     * @return
     */
    @PostMapping("/createOrUpdate")
    public ControllerResponse create(@RequestBody RiskManagerDTO riskManagerDTO, SecurityDTO securityDTO) {
        riskManagerService.create(riskManagerDTO, securityDTO);
        return ControllerResponse.success("风险管理保存成功");
    }

    /**
     * @param riskId
     * @return
     */
    @DeleteMapping("/delete/{riskId}")
    public ControllerResponse delete(@PathVariable Long riskId) {
        riskManagerService.delete(riskId);
        return ControllerResponse.success("风险管理删除成功");
    }

    /**
     * 获取所有的风险等级枚举列表
     *
     * @return
     */
    @GetMapping("/level")
    public ControllerResponse getALLRiskLevel() {
        return ControllerResponse.success(RiskLevelEnum.getAllRiskLevel());
    }

    /**
     * 获取所有的风险状态枚举列表
     *
     * @return
     */
    @GetMapping("/status")
    public ControllerResponse getALLRiskStatus() {
        return ControllerResponse.success(RiskStatusEnum.getAllRiskStatus());
    }

    /**
     * 根据风险主键ID查询风险详情
     *
     * @param riskId
     * @return
     */
    @GetMapping("/{riskId}")
    public ControllerResponse getRiskInfo(@PathVariable Long riskId) {
        RiskManagerDTO riskManagerDTO = riskManagerService.getRiskInfo(riskId);
        return ControllerResponse.success(riskManagerDTO);
    }

    /**
     * 根据项目ID查询风险总条数和风险待处理条数
     *
     * @param securityDTO
     * @return
     */
    @GetMapping("/analysis/count")
    public ControllerResponse getRiskAnalysisCount(SecurityDTO securityDTO) {
        Long projectId = securityDTO.getProjectId();
        JSONObject riskAnalysisCount = riskManagerService.getRiskAnalysisCount(projectId);
        return ControllerResponse.success(riskAnalysisCount);
    }

    /**
     * 获取风险管理列表
     *
     * @param riskStartTime 风险开始时间
     * @param securityDTO
     * @return
     */
    @GetMapping("/collection")
    public ControllerResponse getRisksColl(@RequestParam(value = "riskStartTime", required = false) Date riskStartTime,
                                           @RequestParam(value = "riskEndTime", required = false) Date riskEndTime,
                                           SecurityDTO securityDTO) {
        List<RiskManagerDTO> list = riskManagerService.getRisksColl(riskStartTime, riskEndTime, securityDTO);
        return ControllerResponse.success(list);
    }
}

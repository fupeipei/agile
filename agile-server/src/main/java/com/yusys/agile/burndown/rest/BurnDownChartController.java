package com.yusys.agile.burndown.rest;

import com.yusys.agile.burndown.service.BurnDownChartService;
import com.yusys.agile.utils.result.ResultObject;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
public class BurnDownChartController {
    private static final Logger LOG = LoggerFactory.getLogger(BurnDownChartController.class);
    @Resource
    private BurnDownChartService burnDownChartService;

    /**
     * 计算每日剩余工作量和每日剩余故事数--燃尽图
     */
    @PostMapping("/burndown/calculate")
    public void calculateWorkload() {
        LOG.info("burndown calculate start time:{}", new Date());
        burnDownChartService.calculateWorkload();
        burnDownChartService.calculateStorys();
    }

    /**
     * @Author maxp2
     * @Date 2021/5/27
     * @Description 计算迭代周期内的工作量燃尽图
     * @param sprintId
     * @Return com.yusys.agile.utils.result.ResultObject
     */
    @GetMapping("/burndowns/{sprintId}")
    public ResultObject getBySprint(@PathVariable("sprintId") Long sprintId) {
        return ResultObject.success(burnDownChartService.getBySprint(sprintId));
    }

    /**
     * 计算迭代周期内的任务数燃尽图
     *
     * @param sprintId 迭代ID
     * @return
     */
    @GetMapping("/burndowns/tasks/{sprintId}")
    public ResultObject getTasksBySprint(@PathVariable("sprintId") Long sprintId) {
        return ResultObject.success(burnDownChartService.getTasksBySprint(sprintId));
    }

    /**
     * 燃尽图查看每日剩余故事数
     *
     * @param springtId
     * @return
     */
    @GetMapping("/burndowns/storys/{sprintId}")
    public ResultObject getStoryBySprint(@PathVariable("sprintId") Long springtId) {
        return ResultObject.success(burnDownChartService.getStorysBySprint(springtId));
    }

    /**
     * 计算迭代周期内的故事点燃尽图
     * @author zhaofeng
     * @date 2021/5/26 11:14
     * @param sprintId 迭代ID
     */
    @GetMapping("/burndowns/storyPoint/{sprintId}")
    public ControllerResponse getStoryPointBySprint(@PathVariable("sprintId") Long sprintId){
        return ControllerResponse.success(burnDownChartService.getStoryPointBySprint(sprintId));
    }

    /**
     * 根据迭代ID获取当前迭代内团队成员工作量和任务数
     *
     * @param sprintId 迭代ID
     * @return
     */
    @GetMapping("/burndowns/tasks/member/analysis/{sprintId}")
    public ResultObject getTaskMemberAnalysis(@PathVariable("sprintId") Long sprintId) {
        return ResultObject.success(burnDownChartService.getTaskMemberAnalysis(sprintId));
    }
}

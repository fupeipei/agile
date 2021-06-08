package com.yusys.agile.dashboard.rest.impl;

import com.yusys.agile.burndown.rest.BurnDownChartController;
import com.yusys.agile.dashboard.rest.DashBoardController;
import com.yusys.agile.dashboard.service.DashBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Date: 2021/2/1
 */
@RestController
public class DashBoardControllerImpl implements DashBoardController {

    private static final Logger LOG = LoggerFactory.getLogger(BurnDownChartController.class);

    @Resource
    private DashBoardService dashBoardService;

    /**
     * 仪表盘-迭代-工作项状态个数统计
     */
    @Override
    @PostMapping("/dashboard/sprint/calculate")
    public void calculateStatus() {
        LOG.info("dashboard calculate start time:{}", new Date());
        //迭代中每天工作项状态
        dashBoardService.calculateIssueStatus();
        LOG.info("dashboard calculate end time:{}", new Date());
    }

    /**
     * 仪表盘-迭代-工作项状态个数统计
     */
    @Override
    @PostMapping("/dashboard/project/calculate")
    public void calculateProjectStatus() {
        LOG.info("dashboard calculate start time:{}", new Date());
        //项目中每天工作项状态
        //dashBoardService.calculateProjectStatus();
        LOG.info("dashboard calculate end time:{}", new Date());
    }
}

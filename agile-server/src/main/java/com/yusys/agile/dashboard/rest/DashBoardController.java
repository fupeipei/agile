package com.yusys.agile.dashboard.rest;

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
public class DashBoardController {

    private static final Logger LOG = LoggerFactory.getLogger(DashBoardController.class);

    @Resource
    private DashBoardService dashBoardService;

    /**
     * 仪表盘-迭代-工作项状态个数统计
     */
    @PostMapping("/dashboard/sprint/calculate")
    public void calculateStatus() {
        LOG.info("dashboard calculate start time:{}", new Date());
        //迭代中每天工作项状态
        dashBoardService.calculateIssueStatus();
        LOG.info("dashboard calculate end time:{}", new Date());
    }

}

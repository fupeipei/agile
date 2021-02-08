package com.yusys.agile.scheduletask;

import com.yusys.agile.constant.ScheduleConstant;
import com.yusys.agile.dashboard.service.DashBoardService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**

 * @Date: 2021/2/4
 */
@Component
@JobHandler(ScheduleConstant.TaskHandler.DASH_BOARD_DAY)
public class DashBoardTask extends IJobHandler {
    @Resource
    private DashBoardService dashBoardService;

    @Override
    public ReturnT<String> execute(String s){
        dashBoardService.calculateIssueStatus();
        dashBoardService.calculateProjectStatus();
        return ReturnT.SUCCESS;
    }
}

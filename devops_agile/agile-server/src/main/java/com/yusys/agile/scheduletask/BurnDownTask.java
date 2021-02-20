package com.yusys.agile.scheduletask;

import com.yusys.agile.burndown.service.BurnDownChartService;
import com.yusys.agile.constant.ScheduleConstant;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @Date: 2021/2/8 10:47
 * @Description:
 */
@Component
@JobHandler(ScheduleConstant.TaskHandler.BURNDOWN_DAY)
public class BurnDownTask extends IJobHandler {
    @Resource
    private BurnDownChartService burnDownChartService;

    @Override
    public ReturnT<String> execute(String s){
        burnDownChartService.calculateWorkload();
        burnDownChartService.calculateStorys();
        return ReturnT.SUCCESS;
    }
}

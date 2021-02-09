package com.yusys.agile.scheduletask;

import com.yusys.agile.constant.ScheduleConstant;
import com.yusys.agile.sprint.service.SprintService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Date: 2021/2/18
 */
@Component
@JobHandler(ScheduleConstant.TaskHandler.SPRINT_CHANGE_STATUS)
public class SprintChangeStatusTask extends IJobHandler {
    @Resource
    private SprintService sprintService;

    @Override
    public ReturnT<String> execute(String s) {
        sprintService.changeStatusDaily();
        return ReturnT.SUCCESS;
    }
}

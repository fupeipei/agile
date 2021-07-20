package com.yusys.agile.scheduleplan.rest;

import com.yusys.agile.scheduleplan.service.SchedulePlanService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/schedulePlan")
public class SchedulePlanController {

    @Autowired
    private SchedulePlanService schedulePlanService;

    @ApiOperation(value = "查询工作项拆分待办事项")
    @GetMapping("/epicSplit/list")
    public ControllerResponse queryToDoList(@RequestParam(value = "epicId",required = false) Long epicId,
                                       @RequestParam(value = "title",required = false)String title) {
        return ControllerResponse.success(schedulePlanService.queryToDoList(epicId,title));
    }

    @ApiOperation(value = "处理待办工作项")
    @GetMapping("/dealTodoList")
    public ControllerResponse dealTodoList(@RequestParam(value = "relateId") Long relateId) {
        try {
            schedulePlanService.dealToDoList(relateId);
        }catch (Exception e){
            log.info("处理待办工作项异常：{}",e.getMessage());
        }
        return ControllerResponse.success("操作成功");
    }
}

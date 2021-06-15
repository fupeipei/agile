package com.yusys.agile.leankanban.rest;

import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  @Description: 精益看板控制器
 *  @author: zhao_yd
 *  @Date: 2021/6/11 5:30 下午
 *
 */

@RestController
@RequestMapping("/leanKanban")
public class LeanKanbanController {

    @Autowired
    private LeanKanbanService leanKanbanService;

    @GetMapping("/getLeanKanbanInfo")
    public ControllerResponse viewEdit(@RequestParam("teamId") Long teamId) {
        return ControllerResponse.success(leanKanbanService.queryLeanKanbanInfo(teamId));
    }
}

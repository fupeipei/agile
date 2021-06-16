package com.yusys.agile.leankanban.rest;

import com.google.common.collect.Lists;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 *  @Description: 精益看板控制器
 *  @author: zhao_yd
 *  @Date: 2021/6/11 5:30 下午
 *
 */

@Slf4j
@RestController
@RequestMapping("/leanKanban")
public class LeanKanbanController {

    @Autowired
    private LeanKanbanService leanKanbanService;
    @Autowired
    protected IssueService issueService;

    /**
     * 根据团队ID获取看板信息
     *
     * @param teamId
     * @return
     */
    @GetMapping("/getLeanKanbanInfo")
    public ControllerResponse viewEdit(@RequestParam("teamId") Long teamId) {
        return ControllerResponse.success(leanKanbanService.queryLeanKanbanInfo(teamId));
    }

    /**
     * 根据看板ID获取issue工作项列表
     *
     * @param kanbanId
     * @param issueType
     * @return
     */
    @GetMapping("/issue/getIssueTrees")
    public ControllerResponse getIssueTrees(@RequestParam("kanbanId") String kanbanId,
                                            @RequestParam(value = "issueType" ,required = false) Byte issueType) {
        try {
            if(Optional.ofNullable(issueType).isPresent()){
                issueType = IssueTypeEnum.TYPE_FEATURE.CODE;
            }
            Long id = Long.valueOf(kanbanId);
            return ControllerResponse.success(issueService.getIssueTrees(id,issueType));
        } catch (Exception e) {
            log.info("获取工作项树信息异常:{}",e.getMessage());
        }
        return ControllerResponse.success(Lists.newArrayList());
    }


}

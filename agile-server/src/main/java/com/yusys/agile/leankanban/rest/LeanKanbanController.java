package com.yusys.agile.leankanban.rest;

import com.google.common.collect.Lists;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.portal.common.exception.BusinessException;
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
    public ControllerResponse getIssueTrees(@RequestParam("kanbanId") Long kanbanId,
                                            @RequestParam(value = "issueType" ,required = false) Byte issueType) {
        try {
            if(!Optional.ofNullable(issueType).isPresent()){
                issueType = IssueTypeEnum.TYPE_FEATURE.CODE;
            }
            return ControllerResponse.success(issueService.getIssueTrees(kanbanId,issueType));
        } catch (Exception e) {
            log.info("获取工作项树信息异常:{}",e.getMessage());
        }
        return ControllerResponse.success(Lists.newArrayList());
    }



    @GetMapping("/issue/dragIssueCard")
    public ControllerResponse dragIssueCard(@RequestParam("issueId") Long issueId,
                                            @RequestParam("fromStageId") Long fromStageId,
                                            @RequestParam(value = "fromLaneId",required =  false) Long fromLaneId,
                                            @RequestParam(value = "stageId") Long stageId,
                                            @RequestParam(value = "laneId" ,required = false) Long laneId) {
        try {

            boolean b = issueService.checkIssueState(issueId, fromStageId, fromLaneId);
            if(!b){
                throw new BusinessException("卡片拖动失败,卡片位置不在当前位置,请刷新");
            }
            return ControllerResponse.success(issueService.dragIssueCard(issueId,stageId,laneId));
        } catch (Exception e) {
            log.info("拖拽卡片异常:{}",e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
    }

}

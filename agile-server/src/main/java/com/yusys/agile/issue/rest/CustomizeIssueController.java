package com.yusys.agile.issue.rest;

import com.yusys.agile.issue.dto.CustomizeIssueDTO;
import com.yusys.agile.issue.service.CustomizeIssueService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/customizeIssue")
public class CustomizeIssueController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizeIssueController.class);

    @Resource
    private CustomizeIssueService customizeIssueService;

    /**
     * @param issueIds
     * @return
     * @description 查询工作项信息
     * @date 2020/08/18
     */
    @PostMapping("/task/getRelatedIssues")
    public ControllerResponse<List<CustomizeIssueDTO>> getRelatedIssues(@RequestBody List<Long> issueIds) {
        try {
            return ControllerResponse.success(customizeIssueService.getRelatedIssues(issueIds));
        } catch (Exception e) {
            LOGGER.error("getRelatedIssues occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询任务关联的工作项异常");
        }
    }

    /**
     * @param issueId
     * @return
     * @description 根据任务或缺陷编号查询feature
     * @date 2020/09/17
     */
    @GetMapping("/feature/getFeatureByTaskOrFaultId")
    public ControllerResponse<CustomizeIssueDTO> getFeatureByTaskOrFaultId(@RequestParam("issueId") Long issueId) {
        try {
            return ControllerResponse.success(customizeIssueService.getFeatureByTaskOrFaultId(issueId));
        } catch (Exception e) {
            LOGGER.error("getFeatureByTaskOrFaultId occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("");
        }
    }
}

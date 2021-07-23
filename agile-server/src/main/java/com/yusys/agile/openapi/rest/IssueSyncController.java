package com.yusys.agile.openapi.rest;


import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.openapi.service.IssueSyncService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;



@RestController
public class IssueSyncController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueSyncController.class);

    @Resource
    private IssueSyncService issueSyncService;
    /**
     * 功能描述  同步需求
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/21
     */
    @PostMapping("/issue/sync/add")
    public ControllerResponse issueSyncAdd(@RequestBody List<IssueDTO> list) {

        try {
             issueSyncService.issueSyncAdd(list);
        } catch (Exception e) {
            LOGGER.error("同步新增Issue异常", e);
            return ControllerResponse.fail("同步新增Issue异常：" + e.getMessage());
        }
        return ControllerResponse.success("同步新增Issue成功");
    }
    /**
     * 功能描述  同步需求
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/21
     */
    @PostMapping ("/issue/sync/update")
    public ControllerResponse issueSyncUpdate(@RequestBody List<IssueDTO> list) {

        try {
            issueSyncService.issueSyncUpdate(list);
        } catch (Exception e) {
            LOGGER.error("同步新增Issue异常", e);
            return ControllerResponse.fail("同步新增Issue异常：" + e.getMessage());
        }
        return ControllerResponse.success("同步新增Issue成功");
    }
    /**
     * 功能描述  同步需求
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/21
     */
    @PostMapping ("/issue/sync/delete")
    public ControllerResponse issueSyncDelete(@RequestBody List<Long> list) {

        try {
            issueSyncService.issueSyncDelete(list);
        } catch (Exception e) {
            LOGGER.error("同步新增Issue异常", e);
            return ControllerResponse.fail("同步新增Issue异常：" + e.getMessage());
        }
        return ControllerResponse.success("同步新增Issue成功");
    }
    /**
     * 功能描述  需求状态变更
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/21
     */
    @PostMapping("/issue/sync/stage")
    public ControllerResponse getIssueList(@RequestBody List<IssueDTO> list) {

        try {
            issueSyncService.issueSyncStageIdAndLaneId(list);
        } catch (Exception e) {
            LOGGER.error("更新Issue状态异常", e);
            return ControllerResponse.fail("更新Issue状态异常：" + e.getMessage());
        }
        return ControllerResponse.success("更新Issue状态成功");
    }
}

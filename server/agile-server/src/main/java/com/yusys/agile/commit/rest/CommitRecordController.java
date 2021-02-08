package com.yusys.agile.commit.rest;

import com.yusys.agile.commit.dto.CommitDTO;
import com.yusys.agile.commit.service.CommitService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description 代码提交控制类
 * @date 2021/2/1
 */
@RequestMapping("/commit")
@RestController
public class CommitRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitRecordController.class);

    @Resource
    private CommitService commitService;

    /**
     * @description 查询人员提交记录
     * @date 2021/2/1
     * @param projectId
     * @param commitDTO
     * @return
     */
    @PostMapping("/queryMemberCommitRecord")
    public ControllerResponse queryMemberCommitRecord(@RequestHeader Long projectId, @RequestBody CommitDTO commitDTO){
        commitDTO.setProjectId(projectId);
        try {
            return ControllerResponse.success(commitService.getMemberCommitRecord(commitDTO));
        } catch (Exception e) {
            LOGGER.error("queryMemberCommitRecord method occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询成员代码提交数据异常");
        }
    }

    /**
     * @description 查询工作项提交记录
     * @date 2021/2/1
     * @param issueId
     * @return
     */
    @GetMapping("/queryIssueCommitRecord")
    public ControllerResponse queryIssueCommitRecord(@RequestParam("issueId") Long issueId, @RequestParam("issueType") Byte issueType, Integer pageNumber, Integer pageSize) {
        try {
           // return ControllerResponse.success(commitService.getIssueCommitRecord(issueId, issueType, pageNumber, pageSize));
        } catch (Exception e) {
            LOGGER.error("queryIssueCommitRecord method occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询工作项提交记录数据异常");
        }
        return  null;
    }
}

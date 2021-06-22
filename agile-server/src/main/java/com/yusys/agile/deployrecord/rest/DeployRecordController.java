package com.yusys.agile.deployrecord.rest;



import com.yusys.agile.commit.rest.CommitRecordController;
import com.yusys.agile.deployrecord.service.DeployRecordService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 流水线部署记录
 * @date 2021/2/1
 */
@RequestMapping("/deployRecord")
@RestController
public class DeployRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitRecordController.class);

    @Autowired
    DeployRecordService deployRecordService;

    /**
     * @param issueId
     * @param issueType
     * @return
     * @description 查询任务的部署记录
     * @date 2021/06/01
     */
    @GetMapping("/query")
    public ControllerResponse queryDeployRecord( @RequestParam(value = "issueId") Long issueId,
                                                 @RequestParam(value = "issueType") Byte issueType,
                                                 @RequestParam("pageNum") Integer pageNum,
                                                 @RequestParam("pageSize") Integer pageSize) {
        try {
            return ControllerResponse.success(deployRecordService.queryDeployRecord(issueId,issueType,pageNum,pageSize));
        } catch (Exception e) {
            LOGGER.error("queryMemberCommitRecord method occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询部署记录数据异常");
        }
    }

}

package com.yusys.agile.buildrecords.rest;

import com.yusys.agile.buildrecords.service.BuildDeployService;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName BuildRecordsController
 * @Description 工作项构建记录操作类
 * @Date 2021/2/1
 * @Version 1.0
 */
@RestController
@RequestMapping("/issue/flow/records")
public class BuildRecordsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildRecordsController.class);

    /*@Resource
    private ICmsChangeClient iCmsChangeClient;*/

    @Resource
    private BuildDeployService buildDeployService;

    /**
     * 根据工作项ID，获取流水线构建分页数据
     * @param issueId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/build")
    public ControllerResponse getIssueBuildRecords(@RequestParam("issueId") Long issueId,
                                                   @RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize){
        //PageInfo pageInfo = iCmsChangeClient.queryBuildInstanceByTaskId(issueId.toString(), pageNum, pageSize);
        //return ControllerResponse.success(pageInfo);
        try {
            PageInfo pageInfo = buildDeployService.queryBuildRecord(issueId, pageNum, pageSize);
            return ControllerResponse.success(pageInfo);
        } catch (Exception e) {
            LOGGER.error("getIssueBuildRecords occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询工作项构建记录失败");
        }
    }

    /**
     * 根据工作项ID，获取流水线部署分页数据
     * @param issueId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/deploy")
    public ControllerResponse getIssueDeployRecords(@RequestParam("issueId") Long issueId,
                                                    @RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize){
        //PageInfo pageInfo = iCmsChangeClient.queryDeployInstanceByTaskId(issueId.toString(), pageNum, pageSize);
        //return ControllerResponse.success(pageInfo);
        try {
            PageInfo pageInfo = buildDeployService.queryDeployRecord(issueId, pageNum, pageSize);
            return ControllerResponse.success(pageInfo);
        } catch (Exception e) {
            LOGGER.error("getIssueDeployRecords occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询工作项部署记录失败");
        }
    }
}

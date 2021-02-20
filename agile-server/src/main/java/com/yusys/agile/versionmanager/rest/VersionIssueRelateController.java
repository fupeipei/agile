package com.yusys.agile.versionmanager.rest;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.versionmanager.domain.VersionIssueRelate;
import com.yusys.agile.versionmanager.service.VersionIssueRelateService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName VersionIssueRelateController
 * @Description 版本与工作项关联控制类
 *
 * @Date 2020/8/21 20:30
 * @Version 1.0
 */
@RestController
@RequestMapping("/version/issue/relate")
public class VersionIssueRelateController {
    private static final Logger log = LoggerFactory.getLogger(VersionIssueRelateController.class);
    private String province="yusys";
    @Resource
    private VersionIssueRelateService versionIssueRelateService;


    /**
     * 功能描述 根据工作项类型或者迭代ID获取未关联版本计划的工作项
     *
     * @param versionId
     * @param issueType
     * @param sprintId
     * @param pageSize
     * @param pageNum
     * @param securityDTO
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2021/2/2
     */
    @GetMapping("")
    public ControllerResponse getVersionNoRelateIssues(@RequestParam(value = "id", required = false) Long versionId,
                                                       @RequestParam(value = "issueType", required = false) Byte issueType,
                                                       @RequestParam(value = "sprintId", required = false) Long sprintId,
                                                       @RequestParam(value = "BAPerson", required = false) String BAPerson,
                                                       @RequestParam(value = "bizNum", required = false) String bizNum,
                                                       @RequestParam(value = "formalReqCode", required = false) String formalReqCode,
                                                       @RequestParam(value = "askLineTime", required = false) String askLineTime,
                                                       @RequestParam(value = "relatedSystem", required = false) String relatedSystem,
                                                       @RequestParam("pageSize") Integer pageSize,
                                                       @RequestParam("pageNum") Integer pageNum,
                                                       @RequestParam(value = "idOrTitle", required = false) String idOrTitle,
                                                       SecurityDTO securityDTO) {
        List<IssueDTO> list = versionIssueRelateService.getVersionNoRelateIssues(versionId, issueType, sprintId, BAPerson, bizNum, formalReqCode, askLineTime, relatedSystem, pageSize, pageNum, idOrTitle, securityDTO);
        return ControllerResponse.success(new PageInfo<>(list));
    }

    /**
     * 版本计划关联工作项
     *
     * @param issueRelates
     * @param securityDTO
     * @return
     */
    @PostMapping("/bind")
    public ControllerResponse bindVersionRelateIssues(@RequestBody List<VersionIssueRelate> issueRelates, SecurityDTO securityDTO) {
            versionIssueRelateService.bindVersionRelateIssues(issueRelates, securityDTO, province);
            return ControllerResponse.success("版本计划关联工作项成功");
    }
}

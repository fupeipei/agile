package com.yusys.agile.issueTemplate.rest;

import com.yusys.agile.issueTemplate.domain.IssueTemplateV3;
import com.yusys.agile.issueTemplate.server.IssueTemplateV3Service;
import com.yusys.portal.model.common.dto.ControllerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "需求模板管理")
public class IssueTemplateV3Controller {
    @Resource
    private IssueTemplateV3Service issueTemplateV3Service;

    /**
     * 查询需求模板
     *
     * @param systemId  系统标识
     * @param issueType 问题类型
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @ApiOperation(value = "查询需求模板")
//    @GetMapping("/issueTemplate/query")
    public ControllerResponse queryIssueTemplate(@RequestHeader(name = "systemId") Long systemId, int issueType) {
        return ControllerResponse.success(issueTemplateV3Service.queryIssueTemplate(systemId, issueType));
    }

    /**
     * 编辑需求模板
     *
     * @param issueTemplateV3 问题的模板
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @ApiOperation(value = "编辑需求模板")
//    @PostMapping("/editIssueTemplate")
    public ControllerResponse editIssueTemplate(@RequestBody IssueTemplateV3 issueTemplateV3) {
        issueTemplateV3Service.editIssueTemplate(issueTemplateV3);
        return ControllerResponse.success();
    }


}

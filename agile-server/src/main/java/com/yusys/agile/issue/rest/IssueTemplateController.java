package com.yusys.agile.issue.rest;

import com.yusys.agile.issue.domain.IssueCustomRelationList;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.issue.service.IssueTemplateService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * :
 *
 * @Date: 2021/2/20
 * @Description: TODO
 */
@RestController
public class IssueTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(IssueTemplateController.class);


    @Resource
    IssueTemplateService issueTemplateService;
    @Resource
    IssueCustomRelationService issueCustomRelationService;


    /**
     * 功能描述  工作项与模板初始化查询接口
     *
     * @param issueType
     * @param securityDTO
     * @return java.util.Map
     * @date 2020/8/3
     */
    @GetMapping("/issueTemplate/query")
    public ControllerResponse query(Byte issueType, SecurityDTO securityDTO) {
        return ControllerResponse.success(issueTemplateService.query(issueType, securityDTO));
    }

    /**
     * 功能描述  移除应用的自定义字段
     *
     * @param id
     * @return
     * @date 2021/2/31
     */
    @DeleteMapping("/issueCustomRelation/delete/{id}")
    public ControllerResponse deleteIssueCustomRelation(@PathVariable("id") long id, SecurityDTO securityDTO) {
        try {
            issueCustomRelationService.deleteIssueCustomRelation(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
        return ControllerResponse.success();
    }

    /**
     * 功能描述  应用的自定义字段保存接口
     *
     * @param securityDTO
     * @param idList
     * @return void
     * @date 2020/8/3
     */
    @PostMapping("/issueCustomRelation/save")
    public ControllerResponse saveIssueCustomRelation(SecurityDTO securityDTO, @RequestBody IssueCustomRelationList idList) {
        try {
            issueCustomRelationService.saveIssueCustomRelation(securityDTO, idList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
        return ControllerResponse.success();
    }

    /**
     * 分页查询未应用的自定义字段，不传系统id时，查询系统外的
     * @author zhaofeng
     * @date 2021/6/3 16:19
     * @param issueType
     * @param fieldName
     * @param systemId 系统id
     */
    @GetMapping("/issueCustomRelation/getUnApplied")
    public ControllerResponse getUnApplied(
            @RequestParam(name = "issueType") Byte issueType,
            @RequestParam(name = "fieldName", required = false) String fieldName,
            @RequestParam(name = "systemId", required = false) Long systemId) {
        return ControllerResponse.success(issueCustomRelationService.getUnApplied(systemId, issueType, fieldName));
    }


    @GetMapping("/issueTemplate/init")
    public ControllerResponse initIssueTemplate(Long systemId) {
        try {
            issueTemplateService.initIssueTemplate(systemId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
        return ControllerResponse.success();

    }

}

package com.yusys.agile.issue.rest;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.issue.domain.IssueCustomRelationList;
import com.yusys.agile.issue.domain.SIssueCustomRelation;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.issue.service.IssueTemplateService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
     */
    @GetMapping("/issueTemplate/query")
    public ControllerResponse query(Byte issueType, SecurityDTO securityDTO) {
        return ControllerResponse.success(issueTemplateService.query(issueType, securityDTO));
    }

    /**
     * 移除自定义字段
     * @author zhaofeng
     * @date 2021/6/9 15:12
     * @param id
     * @param securityDTO
     */
    @DeleteMapping("/issueCustomRelation/delete/{id}")
    public ControllerResponse deleteIssueCustomRelation(@PathVariable("id") long id, SecurityDTO securityDTO) {
        issueCustomRelationService.deleteIssueCustomRelation(id);
        return ControllerResponse.success();
    }

    /**
     * 应用的自定义字段保存接口
     * @author zhaofeng
     * @date 2021/6/11 9:41
     * @param securityDTO
     * @param idList
     */
    @PostMapping("/issueCustomRelation/save")
    public ControllerResponse saveIssueCustomRelation(SecurityDTO securityDTO, @RequestBody IssueCustomRelationList idList) {
        issueCustomRelationService.saveIssueCustomRelation(securityDTO, idList);
        return ControllerResponse.success();
    }

    /**
     * 分页-查询未应用的自定义字段
     * @author zhaofeng
     * @date 2021/6/3 16:19
     * @param issueType
     * @param fieldName
     */
    @GetMapping("/issueCustomRelation/getUnApplied")
    public ControllerResponse getUnApplied(
            SecurityDTO security,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNum", required = false) Integer pageNum,
            @RequestParam(name = "issueType") Byte issueType,
            @RequestParam(name = "fieldName", required = false) String fieldName) {
        Long systemId = security.getSystemId();
        List<CustomFieldDTO> list = issueCustomRelationService.getUnApplied(systemId, issueType, fieldName, pageSize, pageNum);
        return ControllerResponse.success(new PageInfo<>(list));
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

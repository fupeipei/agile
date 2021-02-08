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
 *   :
 * @Date: 2020/7/30
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
     *功能描述  工作项与模板初始化查询接口
     *   
     * @date 2020/8/3
     * @param issueType
     * @param securityDTO
     * @return java.util.Map
     */
    @GetMapping("/issueTemplate/query")
    public ControllerResponse query(Byte  issueType, SecurityDTO securityDTO){
        return  ControllerResponse.success(issueTemplateService.query(issueType,securityDTO));
    }
    /**
     *功能描述  移除应用的自定义字段
     *   
     * @date 2020/7/31
     * @param id
     * @return
     */
    @DeleteMapping("/issueCustomRelation/delete/{id}")
    public  ControllerResponse deleteIssueCustomRelation(@PathVariable("id")long id,SecurityDTO securityDTO){
       try{
           issueCustomRelationService.deleteIssueCustomRelation(id);
       }
       catch (Exception e){
           logger.error(e.getMessage());
           return ControllerResponse.fail(e.getMessage());
       }
        return  ControllerResponse.success();
    }
    /**
     *功能描述  应用的自定义字段保存接口
     *   
     * @date 2020/8/3
     * @param securityDTO
     * @param idList
     * @return void
     */
    @PostMapping("/issueCustomRelation/save")
    public ControllerResponse saveIssueCustomRelation(SecurityDTO securityDTO, @RequestBody IssueCustomRelationList idList){
        try{
            issueCustomRelationService.saveIssueCustomRelation(securityDTO,idList);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
        return  ControllerResponse.success();
    }
    /**
      *功能描述 分页查询未应用的自定义字段
      *   
      * @date 2020/8/3
      * @param securityDTO
     * @param issueType
     * @param fieldName
      * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/issueCustomRelation/getUnApplied")
    public ControllerResponse getUnApplied(SecurityDTO securityDTO,Byte issueType,String fieldName){
        return ControllerResponse.success(issueCustomRelationService.getUnApplied(securityDTO,issueType,fieldName));
    }


    @GetMapping("/issueTemplate/init")
    public ControllerResponse initIssueTemplate(Long projectId){
        try{
            issueTemplateService.initIssueTemplate(projectId);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
        return  ControllerResponse.success();

    }

}

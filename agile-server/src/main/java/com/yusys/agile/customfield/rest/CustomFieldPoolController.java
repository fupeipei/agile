package com.yusys.agile.customfield.rest;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.customfield.service.CustomFieldPoolService;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目内自定义字段
 * 项目内工作项只是用作字段池
 *
 * @create 2021/2/1
 */
@RestController
@Api(tags = "自定义字段")
@RequestMapping("/field")
public class CustomFieldPoolController {

    /**
     * log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFieldPoolController.class);

    @Autowired
    private CustomFieldPoolService customFieldPoolService;

    @Autowired
    private IssueCustomRelationService customRelationService;


    /**
     * 添加自定义字段
     *
     * @param customFieldDTO 自定义字段dto
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @PostMapping("/addCustomField")
    @ApiOperation(value = "添加自定义字段")
    public ControllerResponse addCustomField(@RequestBody CustomFieldDTO customFieldDTO, @RequestHeader(name = "systemId") Long systemId) {
        customFieldDTO.setSystemId(systemId);
        customFieldPoolService.addCustomField(customFieldDTO);
        return ControllerResponse.success();
    }

    /**
     * 编辑自定义字段
     *
     * @param customFieldDTO 自定义字段dto
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @PostMapping("/editCustomField")
    public ControllerResponse editCustomField(@RequestBody CustomFieldDTO customFieldDTO, @RequestHeader(name = "systemId") Long systemId) {
        customFieldDTO.setSystemId(systemId);
        customFieldPoolService.editCustomField(customFieldDTO);
        return ControllerResponse.success();
    }

    /**
     * 删除自定义字段
     * @author zhaofeng
     * @date 2021/6/3 16:59
     * @param fieldId
     */
    @DeleteMapping("/deleteCustomField/{fieldId}")
    public ControllerResponse deleteCustomField(@PathVariable Long fieldId) {
        customFieldPoolService.deleteCustomField(fieldId);
        return ControllerResponse.success();
    }

    /**
     * 查询自定义字段
     * @author zhaofeng
     * @date 2021/6/3 14:44
     * @param fieldId
     */
    @GetMapping("/getCustomField/{fieldId}")
    public ControllerResponse getCustomField(@PathVariable("fieldId") Long fieldId) {
        CustomFieldDTO field = customFieldPoolService.getCustomField(fieldId);
        return ControllerResponse.success(field);
    }

    /**
     * 分页查询自定义字段
     * @author zhaofeng
     * @date 2021/6/3 16:10
     * @param fieldName 字段名称，可不传
     * @param pageNum
     * @param pageSize
     */
    @GetMapping("/listAllCustomFields")
    public ControllerResponse listAllCustomFields(
            SecurityDTO security,
            @RequestParam(name = "fieldName", required = false) String fieldName,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "pageSize") Integer pageSize) {
        Long systemId = security.getSystemId();
        List<CustomFieldDTO> list = customFieldPoolService.listAllCustomFields(systemId, fieldName, pageNum, pageSize);
        return ControllerResponse.success(new PageInfo<>(list));
    }


    /**
     * 按系统id和工作项类型查询，如果系统id没传，则只按工作项类型查询
     * @author zhaofeng
     * @date 2021/6/3 15:09
     * @param issueType 工作项类型
     */
    @GetMapping("/listCustomFieldsByIssueType")
    public ControllerResponse listCustomFieldsByIssueType(
            @RequestParam(name = "issueType") Byte issueType,
            SecurityDTO security) {
        Long systemId = security.getSystemId();
        List<CustomFieldDTO> list = customRelationService.getCustomFieldList(systemId, issueType);
        return ControllerResponse.success(list);
    }


}
package com.yusys.agile.customfield.rest;

import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.customfield.service.CustomFieldPoolService;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.common.dto.ControllerResponse;
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
    public ControllerResponse addCustomField(@RequestBody CustomFieldDTO customFieldDTO) {
        customFieldPoolService.addCustomField(customFieldDTO);
        return ControllerResponse.success();
    }
//    /**
//     * 功能描述: 新增自定义字段
//     *
//     * @param customFieldDTO
//     * @return
//     * @date 2021/2/1
//     */
//    public ControllerResponse addCustomField(@RequestBody CustomFieldDTO customFieldDTO, @RequestHeader(name = "projectId") Long projectId) {
//        try {
//            customFieldDTO.setProjectId(projectId);
//            customFieldPoolService.addCustomField(customFieldDTO);
//        } catch (Exception e) {
//            LOGGER.error("新增自定义字段失败！e:{}" + e);
//            return ControllerResponse.fail("新增自定义字段失败！" + e.getMessage());
//        }
//
//        return ControllerResponse.success();
//
//    }

    /**
     * 编辑自定义字段
     *
     * @param customFieldDTO 自定义字段dto
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @PostMapping("/editCustomField")
    public ControllerResponse editCustomField(@RequestBody CustomFieldDTO customFieldDTO) {
        customFieldPoolService.editCustomField(customFieldDTO);
        return ControllerResponse.success();
    }
//    /**
//     * 功能描述: 修改自定义字段
//     *
//     * @param customFieldDTO
//     * @return
//     * @date 2021/2/1
//     */
//    public ControllerResponse editCustomField(@RequestBody CustomFieldDTO customFieldDTO, @RequestHeader(name = "projectId") Long projectId) {
//        try {
//            customFieldDTO.setProjectId(projectId);
//            customFieldPoolService.editCustomField(customFieldDTO);
//        } catch (Exception e) {
//            LOGGER.error("修改自定义字段失败！e:{}" + e);
//            return ControllerResponse.fail("修改自定义字段失败！" + e.getMessage());
//        }
//
//        return ControllerResponse.success();
//
//    }

    /**
     * 功能描述: 删除自定义字段
     *
     * @param fieldId
     * @return
     * @date 2021/2/1
     */
    @DeleteMapping("/deleteCustomField/{fieldId}")
    public ControllerResponse deleteCustomField(@PathVariable Long fieldId) {
        try {
            customFieldPoolService.deleteCustomField(fieldId);
        } catch (Exception e) {
            LOGGER.error("删除自定义字段失败！e:{}" + e);
            return ControllerResponse.fail("删除自定义字段失败！" + e.getMessage());
        }

        return ControllerResponse.success();

    }

    /**
     * 功能描述: 查询自定义字段
     *
     * @param fieldId
     * @return
     * @date 2021/2/1
     */
    @GetMapping("/getCustomField/{fieldId}")
    public ControllerResponse getCustomField(@PathVariable Long fieldId) {
        return ControllerResponse.success(customFieldPoolService.getCustomField(fieldId));
    }

    /**
     * 功能描述: 分页查询自定义字段
     *
     * @param fieldName
     * @param pageNum
     * @param pageSize
     * @return
     * @date 2021/2/1
     */
    @GetMapping("/listAllCustomFields")
    public ControllerResponse listAllCustomFields(@RequestParam(name = "fieldName", required = false) String fieldName,
                                                  @RequestParam(name = "pageNum") Integer pageNum,
                                                  @RequestParam(name = "pageSize") Integer pageSize,
                                                  @RequestHeader(name = "projectId") Long projectId) {
        return ControllerResponse.success(new PageInfo<>(customFieldPoolService.listAllCustomFields(fieldName, pageNum, pageSize, projectId)));
    }


    /**
     * 功能描述: 根据项目和类型查询展示的自定义字段
     *
     * @param issueType
     * @param projectId
     * @return
     * @date 2021/2/1
     */
    @GetMapping("/listCustomFieldsByIssueType")
    public ControllerResponse listCustomFieldsByIssueType(@RequestParam(name = "issueType") Byte issueType, @RequestHeader(name = "projectId") Long projectId, @RequestParam(name = "projectId", required = false) Long paramProjectId) {
        Long finalProjectId = null;
        if (null != paramProjectId) {
            finalProjectId = paramProjectId;
        } else {
            finalProjectId = projectId;
        }
        // 某类型的工作项展示的的自定义字段
        List<CustomFieldDTO> customFieldDTOList = customRelationService.getCustomFieldDTO(finalProjectId, issueType);
        Map map = new HashMap<>();
        map.put("list", customFieldDTOList);
        return ControllerResponse.success(map);

    }


}
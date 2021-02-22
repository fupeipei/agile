package com.yusys.agile.set.stage.rest.impl;

import com.yusys.agile.set.stage.dto.KanbanTemplateDTO;
import com.yusys.agile.set.stage.exception.KanbanTemplateException;
import com.yusys.agile.set.stage.service.KanbanTemplateService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description 看板模板控制器
 * @date 2020/08/14
 */
@RequestMapping("/kanbanTemplate")
@RestController
public class KanbanTemplateControllerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(KanbanTemplateControllerImpl.class);

    @Resource
    private KanbanTemplateService kanbanTemplateService;

    /**
     * @param templateName
     * @param pageNum
     * @param pageSize
     * @return
     * @description 分页查询看板模板列表
     * @date 2020/07/26
     */
    @GetMapping("/getKanbanTemplateList")
    public ControllerResponse getKanbanTemplateList(@RequestHeader("tenantCode") String tenantCode, @RequestParam(name = "templateName", required = false) String templateName,
                                                    @RequestParam(name = "pageNum", required = false) Integer pageNum, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            return ControllerResponse.success(kanbanTemplateService.getKanbanTemplateList(tenantCode, templateName, pageNum, pageSize));
        } catch (Exception e) {
            LOGGER.error("getKanbanTemplateList occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询看板模板列表异常");
        }
    }

    /**
     * @param kanbanTemplateDTO
     * @return
     * @description 创建看板模板
     */
    @PostMapping("/createKanbanTemplate")
    public ControllerResponse createKanbanTemplate(@RequestHeader("tenantCode") String tenantCode, @RequestBody KanbanTemplateDTO kanbanTemplateDTO) {
        try {
            kanbanTemplateDTO.setTenantCode(tenantCode);
            Long kanbanTemplateId = kanbanTemplateService.createKanbanTemplate(kanbanTemplateDTO);
            if (null != kanbanTemplateId) {
                return ControllerResponse.success("看板模板创建成功");
            } else {
                return ControllerResponse.fail("看板模板创建失败");
            }
        } catch (Exception e) {
            LOGGER.error("createKanbanTemplate occur exception, message:{}", e.getMessage());
            if (e instanceof KanbanTemplateException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("项目模板创建异常");
        }
    }

    /**
     * @param kanbanTemplateDTO
     * @return
     * @description 编辑看板模板
     * @date 2020/08/14
     */
    @PostMapping("/editKanbanTemplate")
    public ControllerResponse editKanbanTemplate(@RequestHeader("tenantCode") String tenantCode, @RequestBody KanbanTemplateDTO kanbanTemplateDTO) {
        try {
            kanbanTemplateDTO.setTenantCode(tenantCode);
            int result = kanbanTemplateService.editKanbanTemplate(kanbanTemplateDTO);
            if (result > 1) {
                return ControllerResponse.success("看板模板编辑成功");
            }
            return ControllerResponse.fail("看板模板编辑失败");
        } catch (Exception e) {
            LOGGER.error("editKanbanTemplate occur exception, message:{}", e.getMessage());
            if (e instanceof KanbanTemplateException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("看板模板编辑异常");
        }
    }

    /**
     * @param templateId
     * @return
     * @description 删除看板模板
     * @date 2020/08/25
     */
    @DeleteMapping("/deleteKanbanTemplate/{templateId}/{defaultTemplate}")
    public ControllerResponse deleteKanbanTemplate(@PathVariable Long templateId, @PathVariable Byte defaultTemplate) {
        try {
            int count = kanbanTemplateService.deleteKanbanTemplate(templateId, defaultTemplate);
            if (count > 0) {
                return ControllerResponse.success("看板模板删除成功");
            }
            return ControllerResponse.fail("看板模板删除失败");
        } catch (Exception e) {
            LOGGER.error("deleteKanbanTemplate occur exception, message:{}", e.getMessage());
            if (e instanceof KanbanTemplateException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("看板模板删除异常");
        }
    }

    /**
     * @param tenantCode
     * @return
     * @description 查询默认看板模板
     * @date 2020/08/25
     */
    @GetMapping("/getDefaultKanbanTemplate")
    public KanbanTemplateDTO getDefaultKanbanTemplateByTenantCode(@RequestParam("tenantCode") String tenantCode) {
        KanbanTemplateDTO kanbanTemplate = null;
        try {
            kanbanTemplate = kanbanTemplateService.getDefaultKanbanTemplateByTenantCode(tenantCode);
        } catch (Exception e) {
            LOGGER.error("getKanbanTemplateByTenantCode occur exception, message:{}", e.getMessage());
        }
        return kanbanTemplate;
    }

    /**
     * @param kanbanTemplateDTO
     * @return
     * @description 创建默认看板
     * @date 2020/08/25
     */
    @PostMapping("/createDefaultKanbanTemplate")
    public Long createDefaultKanbanTemplate(@RequestBody KanbanTemplateDTO kanbanTemplateDTO) {
        Long kanbanTemplateId = null;
        try {
            kanbanTemplateId = kanbanTemplateService.createKanbanTemplate(kanbanTemplateDTO);
        } catch (Exception e) {
            LOGGER.error("createDefaultKanbanTemplate occur exception, message:{}", e.getMessage());
        }
        return kanbanTemplateId;
    }
}

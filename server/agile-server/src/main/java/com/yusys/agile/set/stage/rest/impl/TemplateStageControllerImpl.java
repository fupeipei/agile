package com.yusys.agile.set.stage.rest.impl;

import com.yusys.agile.set.stage.dto.TemplateStageDTO;
import com.yusys.agile.set.stage.service.TemplateStageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 模板阶段控制类
 * @date 2020/05/15
 */
@RestController
@RequestMapping("/templateStage")
public class TemplateStageControllerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateStageControllerImpl.class);

    @Resource
    private TemplateStageService templateStageService;

    /**
     * @param tenantCode
     * @param templateId
     * @return
     * @description 根据租户编码和模板编号查询模板阶段列表
     * @date 2020/05/15
     */
    @GetMapping("/queryTemplateStagesByTemplateId")
    public List<TemplateStageDTO> queryTemplateStagesByTemplateId(@RequestParam("tenantCode") String tenantCode, @RequestParam("templateId") Long templateId) {
        LOGGER.info("queryTemplateStagesByTemplateId param tenantCode:{}, templateId:{}", tenantCode, templateId);
        return templateStageService.queryTemplateStagesByTemplateId(tenantCode, templateId);
    }
}

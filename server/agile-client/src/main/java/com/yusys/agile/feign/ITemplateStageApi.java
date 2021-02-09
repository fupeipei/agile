package com.yusys.agile.feign;

import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.set.stage.dto.TemplateStageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "agile-server")//url = "http://localhost:8060"
public interface ITemplateStageApi {

    /**
     * @param level
     * @return
     * @description 根据层级查询模板阶段列表
     */
    @GetMapping("/agile/templateStage/queryTemplateStageListByLevel")
    public List<TemplateStageDTO> queryTemplateStageListByLevel(@RequestParam("level") Byte level);

    /**
     * @param tenantCode
     * @param templateId
     * @return
     * @description 根据租户编码和模板编码查询模板阶段列表
     */
    @GetMapping("/agile/templateStage/queryTemplateStagesByTemplateId")
    public List<TemplateStageDTO> queryTemplateStagesByTemplateId(@RequestParam("tenantCode") String tenantCode, @RequestParam("templateId") Long templateId);

    /**
     * @param kanbanStageInstances
     * @return
     * @description 新建项目初始化看板阶段模板数据
     */
    @PostMapping("/agile/stage/initKanbanStageTemplateDatas")
    public int initKanbanStageTemplateDatas(@RequestBody List<KanbanStageInstanceDTO> kanbanStageInstances);
}

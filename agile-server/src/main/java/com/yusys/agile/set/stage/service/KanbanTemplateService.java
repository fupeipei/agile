package com.yusys.agile.set.stage.service;

import com.yusys.agile.set.stage.domain.KanbanTemplate;
import com.yusys.agile.set.stage.dto.KanbanTemplateDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description 看板模板业务接口
 * @date 2020/07/26
 */
public interface KanbanTemplateService {

    /**
     * @param tenantCode
     * @param templateName
     * @param pageNum
     * @param pageSize
     * @return
     * @description 分页查询看板模板列表
     * @date 2020/07/26
     */
    PageInfo<List<KanbanTemplate>> getKanbanTemplateList(String tenantCode, String templateName, Integer pageNum, Integer pageSize);

    /**
     * @param kanbanTemplateDTO
     * @return
     * @description 创建看板模板
     * @date 2020/07/26
     */
    Long createKanbanTemplate(KanbanTemplateDTO kanbanTemplateDTO);

    /**
     * @param kanbanTemplateDTO
     * @return
     * @description 编辑看板模板
     * @date 2020/08/14
     */
    int editKanbanTemplate(KanbanTemplateDTO kanbanTemplateDTO);

    /**
     * @param templateId
     * @param defaultTemplate
     * @return
     * @description 删除看板模板
     * @date 2020/08/14
     */
    int deleteKanbanTemplate(Long templateId, Byte defaultTemplate);

    /**
     * @param tenantCode
     * @return
     * @description
     */
    KanbanTemplateDTO getDefaultKanbanTemplateByTenantCode(String tenantCode);
}

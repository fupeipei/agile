package com.yusys.agile.set.stage.service;

import com.yusys.agile.set.stage.domain.KanbanTemplate;
import com.yusys.agile.set.stage.dto.KanbanTemplateDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description 看板模板业务接口
 *  
 * @date 2020/07/26
 */
public interface KanbanTemplateService {

    /**
     * @description 分页查询看板模板列表
     *  
     * @date 2020/07/26
     * @param tenantCode
     * @param templateName
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<List<KanbanTemplate>> getKanbanTemplateList(String tenantCode, String templateName, Integer pageNum, Integer pageSize);

    /**
     * @description 创建看板模板
     *  
     * @date 2020/07/26
     * @param kanbanTemplateDTO
     * @return
     */
    Long createKanbanTemplate(KanbanTemplateDTO kanbanTemplateDTO);

    /**
     * @description 编辑看板模板
     *  
     * @date 2020/08/14
     * @param kanbanTemplateDTO
     * @return
     */
    int editKanbanTemplate(KanbanTemplateDTO kanbanTemplateDTO);

    /**
     * @description 删除看板模板
     *  
     * @date 2020/08/14
     * @param templateId
     * @param defaultTemplate
     * @return
     */
    int deleteKanbanTemplate(Long templateId, Byte defaultTemplate);

    /**
     * @description
     * @param tenantCode
     * @return
     */
    KanbanTemplateDTO getDefaultKanbanTemplateByTenantCode(String tenantCode);
}

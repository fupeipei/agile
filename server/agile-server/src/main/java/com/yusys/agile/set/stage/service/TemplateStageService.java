package com.yusys.agile.set.stage.service;

import com.yusys.agile.set.stage.domain.TemplateStage;
import com.yusys.agile.set.stage.dto.TemplateStageDTO;

import java.util.List;

/**
 * @description 模板阶段业务类
 *  
 * @date 2020/05/15
 */
public interface TemplateStageService {

    /**
     * @description 根据租户编码和模板编号查询模板阶段列表
     *  
     * @date 2020/05/15
     * @param tenantCode
     * @param templateId
     * @return java.util.List
     */
    public List<TemplateStageDTO> queryTemplateStagesByTemplateId(String tenantCode, Long templateId);

    /**
     * @description 创建模板阶段
     *  
     * @date 2020/08/06
     * @param templateId
     * @param templateStages
     * @return
     */
    public int createTemplateStages(Long templateId, List<TemplateStage> templateStages);

    /**
     * @description 编辑看板模板阶段
     *  
     * @date 2020/08/14
     * @param templateStages
     * @return
     */
    public int alterTempalteStages(List<TemplateStage> templateStages);

    /**
     * @description 通过模板编号删除模板阶段
     * @param templateStage
     * @return
     */
    public int delTemplateStagesByTemplateId(TemplateStage templateStage);
}

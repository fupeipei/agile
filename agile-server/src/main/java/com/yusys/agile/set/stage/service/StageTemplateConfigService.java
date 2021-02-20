package com.yusys.agile.set.stage.service;

import com.yusys.agile.set.stage.domain.StageTemplateConfig;
import com.yusys.agile.set.stage.dto.StageTemplateConfigDTO;

import java.util.List;

/**
 * @description 阶段模板配置业务
 *  
 * @date 2020/08/05
 */
public interface StageTemplateConfigService {

    /**
     * @description 取阶段模板配置列表
     *  
     * @date 2020/08/05
     * @return
     */
    List<StageTemplateConfig> getStageTemplateConfigList();

    /**
     * @description 根据层级取阶段模板配置列表
     *  
     * @date 2020/08/05
     * @param level
     * @return
     */
    List<StageTemplateConfig> getStageTemplateConfigListByLevel(byte level);

    /**
     * @description 根据层级取默认阶段模板配置列表
     *  
     * @date 2020/08/25
     * @param level
     * @return
     */
    List<StageTemplateConfigDTO> getDefaultStageTemplateConfigListByLevel(byte level);

    /**
     * @description 查询二阶模板配置最大阶段数据
     *  
     * @date 2021/2/6
     * @return
     */
    StageTemplateConfigDTO getMaxSecondStageTemplateConfigData();
}

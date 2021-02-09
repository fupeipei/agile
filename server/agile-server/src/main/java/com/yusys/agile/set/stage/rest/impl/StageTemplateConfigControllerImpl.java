package com.yusys.agile.set.stage.rest.impl;

import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.agile.set.stage.dto.StageTemplateConfigDTO;
import com.yusys.agile.set.stage.service.StageTemplateConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 阶段模板配置控制器
 * @date 2020/08/06
 */
@RestController
@RequestMapping("/stageTemplateConfig")
public class StageTemplateConfigControllerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(StageTemplateConfigControllerImpl.class);

    @Resource
    private StageTemplateConfigService stageTemplateConfigService;

    /**
     * @return
     * @description 查询阶段模板配置数据
     * @date 2020/08/05
     */
    @GetMapping("/getStageTemplateConfigList")
    public ControllerResponse getStageTemplateConfigList() {
        try {
            return ControllerResponse.success(stageTemplateConfigService.getStageTemplateConfigList());
        } catch (Exception e) {
            LOGGER.error("getStageTemplateConfigList method occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询阶段模板配置数据异常");
        }
    }

    /**
     * @param level
     * @return
     * @description 查询默认阶段模板配置列表
     * @date 2020/08/25
     */
    @GetMapping("/getStageTemplateConfigListByLevel")
    public List<StageTemplateConfigDTO> getDefaultStageTemplateConfigListByLevel(@RequestParam Byte level) {
        List<StageTemplateConfigDTO> list = null;
        try {
            list = stageTemplateConfigService.getDefaultStageTemplateConfigListByLevel(level);
        } catch (Exception e) {
            LOGGER.error("getDefaultStageTemplateConfigListByLevel method occur exception, message:{}", e.getMessage());
        }
        return list;
    }
}

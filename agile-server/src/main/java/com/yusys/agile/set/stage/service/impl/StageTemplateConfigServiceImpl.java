package com.yusys.agile.set.stage.service.impl;

import com.yusys.agile.set.stage.dao.StageTemplateConfigMapper;
import com.yusys.agile.set.stage.domain.StageTemplateConfig;
import com.yusys.agile.set.stage.domain.StageTemplateConfigExample;
import com.yusys.agile.set.stage.dto.StageTemplateConfigDTO;
import com.yusys.agile.set.stage.service.StageTemplateConfigService;
import com.yusys.agile.utils.ReflectObjectUtil;
import com.yusys.portal.model.common.enums.StateEnum;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 阶段模板配置业务类
 * @date 2020/08/06
 */
@Service
public class StageTemplateConfigServiceImpl implements StageTemplateConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StageTemplateConfigServiceImpl.class);

    @Resource
    private StageTemplateConfigMapper stageTemplateConfigMapper;

    /**
     * @return
     * @description 取阶段模板配置列表
     * @date 2020/08/05
     */
    @Override
    public List<StageTemplateConfig> getStageTemplateConfigList() {
        List<StageTemplateConfig> firstStages = getStageConfigListByLevel((byte) 1);
        /*if (CollectionUtils.isNotEmpty(firstStages)) {
            for (StageTemplateConfig firstStage : firstStages) {
                Long parentId = firstStage.getStageId();
                if (parentId == StageConstant.FirstStageEnum.READY_STAGE.getValue() || parentId == StageConstant.FirstStageEnum.FINISH_STAGE.getValue()){
                    continue;
                }
                List<StageTemplateConfig> secondStages = getStageConfigListByLevel((byte)2);
                if (CollectionUtils.isNotEmpty(secondStages)) {
                    secondStages.forEach(secondStage -> {
                        secondStage.setParentId(parentId);
                    });
                }
                firstStage.setSecondStages(secondStages);
            }
        }*/
        return firstStages;
    }

    private List<StageTemplateConfig> getStageConfigListByLevel(Byte level) {
        StageTemplateConfigExample stageTemplateConfigExample = new StageTemplateConfigExample();
        stageTemplateConfigExample.setOrderByClause("order_id");
        stageTemplateConfigExample.createCriteria()
                .andLevelEqualTo(level)
                .andStateEqualTo(StateEnum.U.getValue());
        List<StageTemplateConfig> result = stageTemplateConfigMapper.selectByExample(stageTemplateConfigExample);
        LOGGER.info("getStageTemplateConfigList method param level:{},return result:{}", level, result);
        return result;
    }

    @Override
    public List<StageTemplateConfig> getStageTemplateConfigListByLevel(byte level) {
        return getStageConfigListByLevel(level);
    }

    @Override
    public List<StageTemplateConfigDTO> getDefaultStageTemplateConfigListByLevel(byte level) {
        List<StageTemplateConfigDTO> list = null;
        List<StageTemplateConfig> stageTemplateConfigList = getStageConfigListByLevel(level);
        if (CollectionUtils.isNotEmpty(stageTemplateConfigList)) {
            try {
                list = ReflectObjectUtil.copyProperties4List(stageTemplateConfigList, StageTemplateConfigDTO.class);
            } catch (Exception e) {
                LOGGER.error("getDefaultStageTemplateConfigListByLevel covert stageTemplateConfigList to StageTemplateConfigDTOList occur exception, message:{}", e.getMessage());
            }
        }
        return list;
    }

    @Override
    public StageTemplateConfigDTO getMaxSecondStageTemplateConfigData() {
        StageTemplateConfigDTO stageTemplateConfigDTO = null;
        StageTemplateConfigExample example = new StageTemplateConfigExample();
        example.setOrderByClause("stage_id desc");
        example.createCriteria().andLevelEqualTo((byte) 2);
        List<StageTemplateConfig> list = stageTemplateConfigMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            stageTemplateConfigDTO = ReflectObjectUtil.copyProperties(list.get(0), StageTemplateConfigDTO.class);
        }
        return stageTemplateConfigDTO;
    }
}

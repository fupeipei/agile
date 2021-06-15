package com.yusys.agile.set.stage.service.impl;

import com.yusys.agile.set.stage.dao.StageTemplateConfigMapper;
import com.yusys.agile.set.stage.domain.StageTemplateConfig;
import com.yusys.agile.set.stage.domain.StageTemplateConfigExample;
import com.yusys.agile.set.stage.dto.StageTemplateConfigDTO;
import com.yusys.agile.set.stage.service.StageTemplateConfigService;
import com.yusys.agile.utils.ReflectObjectUtil;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *  @Description: 阶段模板配置业务类
 *  @author: zhao_yd
 *  @Date: 2021/6/11 1:47 下午
 *
 */

@Service
public class StageTemplateConfigServiceImpl implements StageTemplateConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StageTemplateConfigServiceImpl.class);

    @Resource
    private StageTemplateConfigMapper stageTemplateConfigMapper;

    /**
     * 取阶段模板配置列表
     *
     * @return
     */
    @Override
    public List<StageTemplateConfig> getStageTemplateConfigList() {
        List<StageTemplateConfig> firstStages = getStageConfigListByLevel((byte) 1);
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

    @Override
    public List<StageTemplateConfigDTO> getDefaultStages() {
        StageTemplateConfigExample stageTemplateConfigExample = new StageTemplateConfigExample();
        stageTemplateConfigExample.setOrderByClause("order_id");
        stageTemplateConfigExample.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue());
        List<StageTemplateConfig> stageTemplateConfigs = stageTemplateConfigMapper.selectByExample(stageTemplateConfigExample);
        List<StageTemplateConfigDTO> result = null;
        try {
            result = ReflectUtil.copyProperties4List(stageTemplateConfigs, StageTemplateConfigDTO.class);
        } catch (Exception e) {
            LOGGER.info("获取精益看板模版异常：{}",e.getMessage());
        }
        return result;
    }
}

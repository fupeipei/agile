package com.yusys.agile.set.stage.service.impl;

import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.dao.TemplateStageMapper;
import com.yusys.agile.set.stage.domain.StageTemplateConfig;
import com.yusys.agile.set.stage.domain.TemplateStage;
import com.yusys.agile.set.stage.domain.TemplateStageExample;
import com.yusys.agile.set.stage.dto.StageTemplateConfigDTO;
import com.yusys.agile.set.stage.dto.TemplateStageDTO;
import com.yusys.agile.set.stage.service.StageTemplateConfigService;
import com.yusys.agile.set.stage.service.TemplateStageService;
import com.google.common.collect.Lists;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @description 模板阶段业务实现类
 *  
 * @date 2020/05/15
 */
@Service
public class TemplateStageServiceImpl implements TemplateStageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateStageServiceImpl.class);

    @Resource
    private TemplateStageMapper templateStageMapper;

    @Resource
    private StageTemplateConfigService stageTemplateConfigService;

    /**
     * @description 根据租户编码和模板编号查询模板阶段
     *  
     * @date 2020/05/15
     * @param templateId
     * @return
     */
    @Override
    public List<TemplateStageDTO> queryTemplateStagesByTemplateId(String tenantCode, Long templateId) {
        List<TemplateStageDTO> templateStages = null;
        TemplateStageExample templateStageExample = new TemplateStageExample();
        templateStageExample.setOrderByClause("config_id asc");
        TemplateStageExample.Criteria criteria = templateStageExample.createCriteria();
        criteria.andTemplateIdEqualTo(templateId)
                    .andTenantCodeEqualTo(tenantCode)
                        .andStateEqualTo("U");
        List<TemplateStage> templateStageList = templateStageMapper.selectByExample(templateStageExample);
        if (CollectionUtils.isNotEmpty(templateStageList)) {
            try {
                templateStages = ReflectUtil.copyProperties4List(templateStageList, TemplateStageDTO.class);
            } catch (Exception e) {
                LOGGER.error("queryTemplateStagesByTemplateId method convert templateStageList to templateStages occur exception, message:{}", e.getMessage());
            }
        }
        return templateStages;
    }

    /**
     * @description 创建模板阶段
     *  
     * @date 2020/08/06
     * @param templateId
     * @param templateStages
     * @return
     */
    @Override
    public int createTemplateStages(Long templateId, List<TemplateStage> templateStages) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(templateStages)) {
            List<TemplateStage> finalTemplateStages = dealTemplateStages(templateId, templateStages);
            if (CollectionUtils.isNotEmpty(finalTemplateStages)) {
                //批量保存模板阶段
                result = templateStageMapper.batchInsert(finalTemplateStages);
            }
        }
        return result;
    }

    /**
     * @description 处理模板阶段
     *  
     * @date 2020/08/10
     * @param templateId
     * @param templateStages
     * @return
     */
    private List<TemplateStage> dealTemplateStages(Long templateId, List<TemplateStage> templateStages) {
        //模板阶段集合
        List<TemplateStage> templateStageList = Lists.newArrayList();
        //一阶段id
        List<Long> parentStageIdList = Lists.newArrayList();
        //租户编码
        String tenantCode = templateStages.get(0).getTenantCode();
        TemplateStage templateStage = null;
        for (TemplateStage template : templateStages) {
            Long stageId = template.getStageId();
            if (stageId != StageConstant.FirstStageEnum.READY_STAGE.getValue() && stageId != StageConstant.FirstStageEnum.FINISH_STAGE.getValue()) {
                parentStageIdList.add(stageId);
            }
            templateStage = new TemplateStage();
            templateStage = spliceTemplateStage(templateId, tenantCode, StageConstant.StageLevelEnum.FIRST_LEVEL_STAGE.getValue(),null, template, templateStage);
            templateStageList.add(templateStage);
        }
        //查询二阶段模板数据
        List<StageTemplateConfig> secondStageTemplateList = stageTemplateConfigService.getStageTemplateConfigListByLevel((byte)2);
        if (CollectionUtils.isNotEmpty(parentStageIdList) && CollectionUtils.isNotEmpty(secondStageTemplateList)) {
            StageTemplateConfigDTO maxStageTemplateConfigDTO = stageTemplateConfigService.getMaxSecondStageTemplateConfigData();
            Long maxStageTemplateConfigStageId = maxStageTemplateConfigDTO.getStageId();
            Long tempStageTemplateConfig = maxStageTemplateConfigStageId;
            int i = 0;
            for (Long parentStageId : parentStageIdList) {
                try {
                    List<TemplateStage> secondTemplateStageList = ReflectUtil.copyProperties4List(secondStageTemplateList, TemplateStage.class);
                    for (TemplateStage secondTemplate : secondTemplateStageList) {
                        templateStage = new TemplateStage();
                        if (i == 0) {
                            templateStage = spliceTemplateStage(templateId, tenantCode, StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue(), parentStageId, secondTemplate, templateStage);
                        } else {
                            tempStageTemplateConfig++;
                            secondTemplate.setStageId(tempStageTemplateConfig);
                            secondTemplate.setOrderId(tempStageTemplateConfig.intValue());
                            templateStage = spliceTemplateStage(templateId, tenantCode, StageConstant.StageLevelEnum.SECOND_LEVEL_STAGE.getValue(), parentStageId, secondTemplate, templateStage);
                        }
                        templateStageList.add(templateStage);
                    }
                    i++;
                } catch (Exception e) {
                    LOGGER.error("[dealTemplateStages] convert secondStageTemplateList to secondTemplateStageList occur exception, message:{}", e.getMessage());
                }
            }
        }
        return templateStageList;
    }

    /**
     * @description 拼装模板阶段
     * @param templateId
     * @param tenantCode
     * @param level
     * @param parentStageId
     * @param template
     * @param templateStage
     * @return
     */
    private TemplateStage spliceTemplateStage(Long templateId, String tenantCode, int level, Long parentStageId, TemplateStage template, TemplateStage templateStage) {
        templateStage.setTemplateId(templateId);
        templateStage.setStageId(template.getStageId());
        templateStage.setStageName(template.getStageName());
        if (level == 1) {
            templateStage.setParentId(-1L);
            Byte selected = template.getIsShow();
            if (0 == selected) {
                templateStage.setIsShow((byte)0);
            } else {
                templateStage.setIsShow((byte)1);
            }
        } else if (level == 2) {
            templateStage.setParentId(parentStageId);
        }
        templateStage.setLevel((byte)level);
        templateStage.setOrderId(template.getOrderId());
        templateStage.setState(StateEnum.U.getValue());
        templateStage.setTenantCode(tenantCode);
        templateStage.setCreateUid(UserThreadLocalUtil.getUserInfo().getUserId());
        templateStage.setCreateTime(new Date());
        return templateStage;
    }

    /**
     * @description 编辑看板模板阶段
     *  
     * @date 2020/08/14
     * @param templateStages
     * @return
     */
    @Override
    public int alterTempalteStages(List<TemplateStage> templateStages) {
        int count = 0;
        LOGGER.info("alterKanbanTempalteStages param templateStages:{}", templateStages);
        Iterator<TemplateStage> iterator = templateStages.iterator();
        while (iterator.hasNext()) {
            TemplateStage templateStage = iterator.next();
            Long stageId = templateStage.getStageId();
            if (stageId == StageConstant.FirstStageEnum.READY_STAGE.getValue() || stageId == StageConstant.FirstStageEnum.FINISH_STAGE.getValue()) {
                iterator.remove();
            }
        }
        //更新模板阶段
        for (TemplateStage templateStage : templateStages) {
            count += templateStageMapper.updateByTemplateStageId(templateStage);
        }
        return count;
    }

    /**
     * @description 根据模板编号删除模板阶段
     *  
     * @date 2020/08/21
     * @param templateStage
     * @return
     */
    @Override
    public int delTemplateStagesByTemplateId(TemplateStage templateStage) {
        LOGGER.info("delTemplateStagesByTemplateId param templateStage:{}", templateStage);
        return templateStageMapper.updateByTemplateIdSelective(templateStage);
    }
}

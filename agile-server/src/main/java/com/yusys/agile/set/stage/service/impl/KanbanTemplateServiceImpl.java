package com.yusys.agile.set.stage.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.yusys.agile.set.stage.dao.KanbanTemplateMapper;
import com.yusys.agile.set.stage.dao.TemplateStageMapper;
import com.yusys.agile.set.stage.dto.KanbanTemplateDTO;
import com.yusys.agile.set.stage.exception.KanbanTemplateException;
import com.yusys.agile.set.stage.service.KanbanTemplateService;
import com.yusys.agile.set.stage.service.StageTemplateConfigService;
import com.yusys.agile.set.stage.service.TemplateStageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yusys.agile.set.stage.domain.*;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description 看板模板业务类
 * @date 2020/07/28
 */
@Service
public class KanbanTemplateServiceImpl implements KanbanTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KanbanTemplateServiceImpl.class);

    /**
     * 默认看板模板
     */
    private static final byte DEFAULT_KANBAN_TEMPLATE = 1;

    /**
     * 新增类型
     */
    private static final int CREATE_TYPE = 1;

    /**
     * 修改类型
     */
    private static final int ALTER_TYPE = 2;

    /**
     * 删除类型
     */
    private static final int DELETE_TYPE = 3;

    @Resource
    private KanbanTemplateMapper kanbanTemplateMapper;

    @Resource
    private TemplateStageService templateStageService;

    @Resource
    private StageTemplateConfigService stageTemplateConfigService;

    @Resource
    private TemplateStageMapper templateStageMapper;

    /**
     * @param tenantCode
     * @param templateName
     * @param pageNum
     * @param pageSize
     * @return
     * @description 查询看板模板列表
     * @date 2020/07/28
     */
    @Override
    public PageInfo<List<KanbanTemplate>> getKanbanTemplateList(String tenantCode, String templateName, Integer pageNum, Integer pageSize) {
        pageNum = null == pageNum ? 1 : pageNum;
        pageSize = null == pageSize ? 20 : pageSize;
        PageMethod.startPage(pageNum, pageSize);
        KanbanTemplateExample kanbanTemplateExample = new KanbanTemplateExample();
        kanbanTemplateExample.setOrderByClause("default_template desc,template_id asc");
        KanbanTemplateExample.Criteria criteria = kanbanTemplateExample.createCriteria();
        criteria.andTenantCodeEqualTo(tenantCode)
                .andStateEqualTo(StateEnum.U.getValue());
        if (StringUtils.isNotBlank(templateName)) {
            criteria.andTemplateNameLike("%" + templateName + "%");
        }
        List<KanbanTemplate> kanbanTemplates = kanbanTemplateMapper.selectByExample(kanbanTemplateExample);
        return new PageInfo(kanbanTemplates);
    }

    /**
     * @param kanbanTemplateDTO
     * @return
     * @description 创建项目模板
     * @date 2020/07/26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createKanbanTemplate(KanbanTemplateDTO kanbanTemplateDTO) {
        validateKanbanTemplateDuplicate(kanbanTemplateDTO);
        KanbanTemplate kanbanTemplate = constructKanbanTemplate(kanbanTemplateDTO, CREATE_TYPE);
        //新建项目模板
        int count = kanbanTemplateMapper.insert(kanbanTemplate);
        if (count != 1) {
            throw new RuntimeException("创建项目模板失败");
        }
        List<TemplateStage> templateStages = constructTemplateStage(kanbanTemplateDTO, CREATE_TYPE);
        //看板模板主键
        Long kanbanTemplateId = kanbanTemplate.getTemplateId();
        //新建模板阶段
        count = templateStageService.createTemplateStages(kanbanTemplateId, templateStages);
        if (count < 1) {
            throw new RuntimeException("创建模板阶段失败");
        }
        return kanbanTemplateId;
    }

    /**
     * @param kanbanTemplateDTO
     * @return
     * @description 校验看板模板是否重复
     * @date 2020/07/28
     */
    private void validateKanbanTemplateNameDuplicate(KanbanTemplateDTO kanbanTemplateDTO) {
        String templateName = kanbanTemplateDTO.getTemplateName();
        if (StringUtils.isBlank(templateName)) {
            throw new KanbanTemplateException("模板名必填");
        }
        String tenantCode = kanbanTemplateDTO.getTenantCode();
        KanbanTemplateExample kanbanTemplateExample = new KanbanTemplateExample();
        KanbanTemplateExample.Criteria criteria = kanbanTemplateExample.createCriteria();
        criteria.andTemplateNameEqualTo(templateName)
                .andTenantCodeEqualTo(tenantCode)
                .andStateEqualTo(StateEnum.U.getValue());
        Long templateId = kanbanTemplateDTO.getTemplateId();
        if (null != templateId) {
            criteria.andTemplateIdNotEqualTo(templateId);
        }
        long count = kanbanTemplateMapper.countByExample(kanbanTemplateExample);
        if (count != 0) {
            throw new KanbanTemplateException("当前租户下模板名称重复");
        }
    }

    /**
     * @param kanbanTemplateDTO
     * @description 校验默认看板模板是否重复
     * @date 2020/07/28
     */
    private void validateKanbanTemplateDuplicate(KanbanTemplateDTO kanbanTemplateDTO) {
        validateKanbanTemplateNameDuplicate(kanbanTemplateDTO);
        Byte defaultTemplate = kanbanTemplateDTO.getDefaultTemplate();
        //校验默认模板是否存在
        if (null != defaultTemplate) {
            if (1 == defaultTemplate) {
                KanbanTemplateExample kanbanTemplateExample = new KanbanTemplateExample();
                kanbanTemplateExample.createCriteria()
                        .andDefaultTemplateEqualTo(defaultTemplate)
                        .andTenantCodeEqualTo(kanbanTemplateDTO.getTenantCode())
                        .andStateEqualTo(StateEnum.U.getValue());
                long count = kanbanTemplateMapper.countByExample(kanbanTemplateExample);
                if (count != 0) {
                    throw new KanbanTemplateException("当前租户下已存在默认模板");
                }
            }
        } else {
            throw new KanbanTemplateException("是否默认模板参数必填");
        }
    }

    /**
     * @param kanbanTemplateDTO
     * @param type
     * @return
     * @description 构造看板模板
     */
    private KanbanTemplate constructKanbanTemplate(KanbanTemplateDTO kanbanTemplateDTO, int type) {
        KanbanTemplate kanbanTemplate = ReflectUtil.copyProperties(kanbanTemplateDTO, KanbanTemplate.class);
        if (type == CREATE_TYPE) {
            kanbanTemplate.setCreateUid(UserThreadLocalUtil.getUserInfo().getUserId());
            kanbanTemplate.setCreateTime(new Date());
            kanbanTemplate.setState(StateEnum.U.getValue());
        }
        if (type == ALTER_TYPE) {
            kanbanTemplate.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
            kanbanTemplate.setUpdateTime(new Date());
            kanbanTemplate.setState(StateEnum.U.getValue());
        }
        if (type == DELETE_TYPE) {
            kanbanTemplate.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
            kanbanTemplate.setUpdateTime(new Date());
            kanbanTemplate.setState(StateEnum.E.getValue());
        }
        return kanbanTemplate;
    }

    /**
     * @param kanbanTemplateDTO
     * @param type
     * @return
     * @description 构造模板阶段
     */
    private List<TemplateStage> constructTemplateStage(KanbanTemplateDTO kanbanTemplateDTO, int type) {
        List<Long> checkedStageIdList = kanbanTemplateDTO.getCheckedStageIdList();
        if (CollectionUtils.isEmpty(checkedStageIdList)) {
            throw new KanbanTemplateException("模板一级阶段必选");
        }
        //查询一阶段模板配置信息
        List<StageTemplateConfig> stageTemplateConfigList = stageTemplateConfigService.getStageTemplateConfigListByLevel((byte) 1);
        if (CollectionUtils.isEmpty(stageTemplateConfigList)) {
            throw new KanbanTemplateException("阶段模板配置表无一阶段数据");
        }
        List<TemplateStage> templateStageList = null;
        try {
            templateStageList = ReflectUtil.copyProperties4List(stageTemplateConfigList, TemplateStage.class);
            Long templateId = kanbanTemplateDTO.getTemplateId();
            templateStageList.forEach(templateStage -> {
                if (null != templateId) {
                    templateStage.setTemplateId(templateId);
                }
                Long templateStageId = templateStage.getStageId();
                if (checkedStageIdList.contains(templateStageId)) {
                    templateStage.setIsShow((byte) 0);
                } else {
                    templateStage.setIsShow((byte) 1);
                }
                if (type == CREATE_TYPE) {
                    templateStage.setCreateUid(UserThreadLocalUtil.getUserInfo().getUserId());
                    templateStage.setCreateTime(new Date());
                    templateStage.setState(StateEnum.U.getValue());
                }
                if (type == ALTER_TYPE) {
                    templateStage.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
                    templateStage.setUpdateTime(new Date());
                    templateStage.setState(StateEnum.U.getValue());
                }
                if (type == DELETE_TYPE) {
                    templateStage.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
                    templateStage.setUpdateTime(new Date());
                    templateStage.setState(StateEnum.E.getValue());
                }
                templateStage.setTenantCode(kanbanTemplateDTO.getTenantCode());
            });
        } catch (Exception e) {
            LOGGER.error("[constructTemplateStage] convert stageTemplateConfigList to templateStageList occur exception, message:{}", e.getMessage());
        }
        return templateStageList;
    }

    /**
     * @param kanbanTemplateDTO
     * @return
     * @description 编辑看板模板
     * @date 2020/08/14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editKanbanTemplate(KanbanTemplateDTO kanbanTemplateDTO) {
        KanbanTemplate kanbanTemplate = getKanbanTemplateByPrimaryKey(kanbanTemplateDTO.getTemplateId(), kanbanTemplateDTO.getDefaultTemplate());
        if (null == kanbanTemplate) {
            throw new KanbanTemplateException("该看板模板不存在");
        }
        Byte defaultTemplate = kanbanTemplateDTO.getDefaultTemplate();
        if (defaultTemplate == DEFAULT_KANBAN_TEMPLATE) {
            throw new KanbanTemplateException("默认看板模板禁止编辑");
        }
        validateKanbanTemplateNameDuplicate(kanbanTemplateDTO);
        kanbanTemplate = constructKanbanTemplate(kanbanTemplateDTO, ALTER_TYPE);
        kanbanTemplate.setState(StateEnum.U.getValue());
        int count = kanbanTemplateMapper.updateByPrimaryKeySelective(kanbanTemplate);
        if (count < 1) {
            throw new RuntimeException("编辑看板模板失败");
        }
        List<TemplateStage> templateStages = constructTemplateStage(kanbanTemplateDTO, ALTER_TYPE);
        int result = templateStageService.alterTempalteStages(templateStages);
        LOGGER.info("invoke alterTempalteStages method param templateStages:{}, return affect rows:{}", templateStages, result);
        count += result;
        return count;
    }

    /**
     * @param templateId
     * @return
     * @description 根据模板编号查询模板
     * @date 2020/08/24
     */
    private KanbanTemplate getKanbanTemplateByPrimaryKey(Long templateId, Byte defaultTemplate) {
        KanbanTemplate kanbanTemplate = null;
        KanbanTemplateExample kanbanTemplateExample = new KanbanTemplateExample();
        kanbanTemplateExample.createCriteria()
                .andTemplateIdEqualTo(templateId)
                .andDefaultTemplateEqualTo(defaultTemplate)
                .andStateEqualTo(StateEnum.U.getValue());
        List<KanbanTemplate> kanbanTemplates = kanbanTemplateMapper.selectByExample(kanbanTemplateExample);
        if (CollectionUtils.isNotEmpty(kanbanTemplates)) {
            kanbanTemplate = kanbanTemplates.get(0);
        }
        return kanbanTemplate;
    }

    /**
     * @param templateId
     * @param defaultTemplate
     * @return
     * @description 删除看板模板
     * @date 2020/08/14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteKanbanTemplate(Long templateId, Byte defaultTemplate) {
        if (defaultTemplate == DEFAULT_KANBAN_TEMPLATE) {
            throw new KanbanTemplateException("默认看板模板禁止删除");
        }
        KanbanTemplate kanbanTemplate = getKanbanTemplateByPrimaryKey(templateId, defaultTemplate);
        if (null == kanbanTemplate) {
            throw new KanbanTemplateException("该看板模板不存在");
        }
        //删除看板模板
        KanbanTemplateDTO kanbanTemplateDTO = new KanbanTemplateDTO();
        kanbanTemplateDTO.setTemplateId(templateId);
        kanbanTemplate = constructKanbanTemplate(kanbanTemplateDTO, DELETE_TYPE);
        int count = kanbanTemplateMapper.updateByPrimaryKeySelective(kanbanTemplate);
        if (count != 1) {
            throw new RuntimeException("删除看板模板异常");
        }
        //排除无模板阶段数据
        TemplateStageExample templateStageExample = new TemplateStageExample();
        templateStageExample.createCriteria().andTemplateIdEqualTo(templateId).andStateEqualTo(StateEnum.U.getValue());
        List<TemplateStage> templateStages = templateStageMapper.selectByExample(templateStageExample);
        if (CollectionUtils.isNotEmpty(templateStages)) {
            //删除模板阶段
            TemplateStage templateStage = new TemplateStage();
            templateStage.setTemplateId(templateId);
            templateStage.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
            templateStage.setUpdateTime(new Date());
            templateStage.setState(StateEnum.E.getValue());
            int result = templateStageService.delTemplateStagesByTemplateId(templateStage);
            if (result < 1) {
                throw new RuntimeException("删除看板-模板阶段异常");
            }
        }
        return count;
    }

    /**
     * @param tenantCode
     * @return
     * @description 根据租户编码查询默认看板模板
     * @date 2020/08/24
     */
    @Override
    public KanbanTemplateDTO getDefaultKanbanTemplateByTenantCode(String tenantCode) {
        KanbanTemplateDTO kanbanTemplateDTO = null;
        KanbanTemplateExample kanbanTemplateExample = new KanbanTemplateExample();
        kanbanTemplateExample.createCriteria()
                .andTenantCodeEqualTo(tenantCode)
                .andDefaultTemplateEqualTo((byte) 1)
                .andStateEqualTo(StateEnum.U.getValue());
        List<KanbanTemplate> kanbanTemplates = kanbanTemplateMapper.selectByExample(kanbanTemplateExample);
        if (CollectionUtils.isNotEmpty(kanbanTemplates)) {
            KanbanTemplate kanbanTemplate = kanbanTemplates.get(0);
            kanbanTemplateDTO = ReflectUtil.copyProperties(kanbanTemplate, KanbanTemplateDTO.class);
        }
        return kanbanTemplateDTO;
    }
}

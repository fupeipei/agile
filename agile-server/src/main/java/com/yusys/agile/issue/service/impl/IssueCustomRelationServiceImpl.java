package com.yusys.agile.issue.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.yusys.agile.customfield.dao.SCustomFieldPoolMapper;
import com.yusys.agile.customfield.domain.SCustomFieldPool;
import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.customfield.enums.FieldRequiredEnum;
import com.yusys.agile.customfield.service.CustomFieldPoolService;
import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.agile.issue.dao.SIssueCustomRelationMapper;
import com.yusys.agile.issue.domain.IssueCustomRelationList;
import com.yusys.agile.issue.domain.SIssueCustomRelation;
import com.yusys.agile.issue.domain.SIssueCustomRelationExample;
import com.yusys.agile.issue.service.IssueCustomFieldService;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.issue.service.IssueTemplateService;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import jodd.util.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * :
 *
 * @Date: 2021/2/31
 * @Description: TODO
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IssueCustomRelationServiceImpl implements IssueCustomRelationService {

    @Resource
    SIssueCustomRelationMapper issueCustomRelationMapper;
    @Resource
    SCustomFieldPoolMapper customFieldPoolMapper;
    @Resource
    IssueCustomFieldService issueCustomFieldService;
    @Resource
    CustomFieldPoolService customFieldPoolService;
    @Resource
    IssueTemplateService issueTemplateService;
    @Resource
    HeaderFieldService headerFieldService;

    /**
     * 按系统id和工作项类型查询
     * @author zhaofeng
     * @date 2021/6/3 15:47
     * @param systemId
     * @param issueType
     */
    @Override
    public List<SIssueCustomRelation> getIssueCustomRelations(Long systemId, Byte issueType) {
        //查询自定义字段集合，并转换成map结构
        List<CustomFieldDTO> customFieldDTOList = customFieldPoolService.listAllCustomFields(systemId, "", null, null);
        Map<Long, List<CustomFieldDTO>> listMap = customFieldDTOList.stream().collect(Collectors.groupingBy(CustomFieldDTO::getFieldId));
        //查询关联关系
        SIssueCustomRelationExample example = new SIssueCustomRelationExample();
        SIssueCustomRelationExample.Criteria criteria = example.createCriteria();
        if(issueType != null){
            criteria.andIssueTypeEqualTo(issueType);
        }
        if(systemId != null){
            criteria.andSystemIdEqualTo(systemId);
        }else{
            criteria.andSystemIdIsNull();
        }
        criteria.andStateEqualTo(StateEnum.U.getValue());
        example.setOrderByClause("sort asc");
        List<SIssueCustomRelation> list = issueCustomRelationMapper.selectByExampleWithBLOBs(example);
        list.stream().forEach(item->{
            if (listMap.containsKey(item.getFieldId())) {
                CustomFieldDTO customFieldDTO = listMap.get(item.getFieldId()).get(0);
                item.setFieldType(Byte.parseByte(customFieldDTO.getFieldType().toString()));
                item.setFieldContent(customFieldDTO.getFieldContent());
                item.setFieldName(customFieldDTO.getFieldName());
            }
        });
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteIssueCustomRelation(Long id) {
        //工作项自定义字段表
        issueCustomRelationMapper.updateStateById(id, StateEnum.E.getValue());
        //列头
        headerFieldService.deleteCustomFieldByFieldId(id);
        //数据表
        issueCustomFieldService.deleteCustomFileByIssueCustomRelationId(id);
    }

    /**
     * 功能描述  应用的自定义字段保存接口
     *
     * @param securityDTO
     * @param idList
     * @return void
     * @date 2020/8/3
     */
    @Override
    public void saveIssueCustomRelation(SecurityDTO securityDTO, IssueCustomRelationList idList) {
        //模板保存
        if (idList.getIssueTemplate() != null) {
            issueTemplateService.editIssueCustomRelation(securityDTO, idList.getIssueTemplate());
        }
        //应用字段保存
        //查询所有关联关系，不论 state=U还是state=E
        List<Long> longList = issueCustomRelationMapper.getAppliedByissueType(idList.getIssueType());
        if (!CollectionUtils.isEmpty(idList.getIssueCustomRelationList())) {
            List<SIssueCustomRelation> issueCustomRelationList = idList.getIssueCustomRelationList();
            for (int i = 0; i < issueCustomRelationList.size(); i++) {
                SIssueCustomRelation issueCustomRelation = issueCustomRelationList.get(i);
                issueCustomRelation.setIssueType(idList.getIssueType());
                issueCustomRelation.setSystemId(securityDTO.getSystemId());
                issueCustomRelation.setSort(i + longList.size() + 1);
                //如果不存在则insert
                if (!longList.contains(issueCustomRelation.getFieldId())) {
                    issueCustomRelationMapper.insertSelective(issueCustomRelation);
                    //保存到列头表中
                    headerFieldService.saveCustomFieldByFieldId(securityDTO.getSystemId(), issueCustomRelation.getId(), idList.getIssueType());
                } else {
                    SIssueCustomRelation newIssueCustomRelation = issueCustomRelationMapper.selectByIssueTypeAndFieldId(issueCustomRelation.getIssueType(), issueCustomRelation.getFieldId(), securityDTO.getSystemId());
                    //否则update，并将state=U，
                    //如果之前保存过，但是被删除了，这样做可以恢复之前的关联关系
                    //恢复 s_issue_custom_relation 的值
                    newIssueCustomRelation.setSort(i + 1);
                    newIssueCustomRelation.setState(StateEnum.U.getValue());
                    issueCustomRelationMapper.updateByPrimaryKeySelective(newIssueCustomRelation);
                    //恢复列头
                    headerFieldService.recoveryCustomFieldByFieldId(newIssueCustomRelation.getId());
                    //恢复数据表
                    issueCustomFieldService.recoveryCustomFileByIssueCustomRelationId(newIssueCustomRelation.getId());
                }
            }
        }
    }


    @Override
    public List<CustomFieldDTO> getUnApplied(Long systemId, Byte issueType, String fieldName) {
        //查询自定义字段集合，并转换成map结构
        List<CustomFieldDTO> customFields = customFieldPoolService.listAllCustomFields(systemId, fieldName, null, null);
        Map<Long, List<CustomFieldDTO>> listMap = customFields.stream().collect(Collectors.groupingBy(CustomFieldDTO::getFieldId));
        List<CustomFieldDTO> list = customFieldPoolMapper.getUnAppByIssueType(issueType, fieldName, systemId);
        return list;
    }

    @Override
    public List<CustomFieldDTO> getCustomFieldList(Long systemId, Byte issueType) {
        List<SIssueCustomRelation> list = getIssueCustomRelations(systemId, issueType);
        List<CustomFieldDTO> rest = list.stream().map(item -> {
            CustomFieldDTO customFieldDTO = new CustomFieldDTO();
            //主键
            customFieldDTO.setFieldId(item.getId());
            //详情
            JSONObject jsonObject = JSON.parseObject(item.getFieldContent());
            customFieldDTO.setFieldContent(jsonObject.toString());
            //名称
            customFieldDTO.setFieldName(item.getFieldName());
            //字段类型
            customFieldDTO.setFieldType(Integer.parseInt(item.getFieldType().toString()));
            //是否必填
            customFieldDTO.setRequired(item.getRequired());
            return customFieldDTO;
        }).collect(Collectors.toList());
        return rest;
    }

    @Override
    public void deleteIssueCustomRelationByFieldId(Long fieldId) {
        //查询出工作项与自定义字段的所有的关联关系
        SIssueCustomRelationExample example = new SIssueCustomRelationExample();
        example.createCriteria().andFieldIdEqualTo(fieldId);
        List<SIssueCustomRelation> issueCustomRelationList = issueCustomRelationMapper.selectByExample(example);
        //逻辑删除工作项与自定义字段的所有的关联关系
        issueCustomRelationMapper.updateStateByFieldId(fieldId, StateEnum.E.getValue());
        for (SIssueCustomRelation relation : issueCustomRelationList) {
            Long relationId = relation.getId();
            //逻辑删除列头
            headerFieldService.deleteCustomFieldByFieldId(relationId);
            //逻辑删除自定义字段表单值
            issueCustomFieldService.deleteCustomFileByIssueCustomRelationId(relationId);
        }
    }
}

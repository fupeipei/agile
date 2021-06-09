package com.yusys.agile.issue.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
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
        }
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
        List<Long> longList = issueCustomRelationMapper.getAppliedByissueType(idList.getIssueType());
        if (idList.getIssueCustomRelationList() != null && idList.getIssueCustomRelationList().size() > 0) {
            for (int i = 0; i < idList.getIssueCustomRelationList().size(); i++) {
                SIssueCustomRelation issueCustomRelation = idList.getIssueCustomRelationList().get(i);
                issueCustomRelation.setIssueType(idList.getIssueType());
                issueCustomRelation.setProjectId(securityDTO.getProjectId());
                issueCustomRelation.setSort(i + longList.size() + 1);
                if (!longList.contains(issueCustomRelation.getFieldId())) {
                    issueCustomRelationMapper.insertSelective(issueCustomRelation);
                    //保存到列头表中
                    headerFieldService.saveCustomFieldByFieldId(securityDTO.getProjectId(), issueCustomRelation.getId(), idList.getIssueType());
                } else {
                    issueCustomRelation.setSort(i + 1);
                    issueCustomRelationMapper.updateByPrimaryKeySelective(issueCustomRelation);
                }
            }
        }
    }

    /**
     * 查询未被应用的自定义字段，当系统id为空时，查询系统外的
     * @author zhaofeng
     * @date 2021/6/3 16:26
     * @param systemId
     * @param issueType
     * @param fieldName
     */
    @Override
    public List<SIssueCustomRelation> getUnApplied(Long systemId, Byte issueType, String fieldName) {
        List<SIssueCustomRelation> result = Lists.newArrayList();
        //查询自定义字段集合，并转换成map结构
        List<CustomFieldDTO> customFieldList = customFieldPoolService.listAllCustomFields(systemId, fieldName, null, null);
        Map<Long, List<CustomFieldDTO>> listMap = customFieldList.stream().collect(Collectors.groupingBy(CustomFieldDTO::getFieldId));
        //查询关联关系
        SIssueCustomRelationExample example = new SIssueCustomRelationExample();
        SIssueCustomRelationExample.Criteria criteria = example.createCriteria();
        if(issueType != null){
            criteria.andIssueTypeEqualTo(issueType);
        }
        if(systemId != null){
            criteria.andSystemIdEqualTo(systemId);
        }
        example.setOrderByClause("sort asc");
        List<Long> idList = issueCustomRelationMapper.getAppliedByissueType(issueType);
        List<SIssueCustomRelation> list = issueCustomRelationMapper.selectByExampleWithBLOBs(example);
        list.stream().forEach(item->{
            if (listMap.containsKey(item.getFieldId()) && !idList.contains(item.getFieldId())) {
                CustomFieldDTO customFieldDTO = listMap.get(item.getFieldId()).get(0);
                item.setFieldType(Byte.parseByte(customFieldDTO.getFieldType().toString()));
                item.setFieldContent(customFieldDTO.getFieldContent());
                item.setFieldName(customFieldDTO.getFieldName());
            }
        });
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
            Boolean b = true;
            if (Objects.equals(FieldRequiredEnum.no_required.getCode(), item.getRequired())) {
                b = false;
            }
            jsonObject.put("required", b);
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

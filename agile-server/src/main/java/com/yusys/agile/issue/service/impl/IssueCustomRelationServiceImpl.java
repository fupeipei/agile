package com.yusys.agile.issue.service.impl;

import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.customfield.service.CustomFieldPoolService;
import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.agile.issue.dao.IssueCustomRelationMapper;
import com.yusys.agile.issue.domain.IssueCustomRelation;
import com.yusys.agile.issue.domain.IssueCustomRelationExample;
import com.yusys.agile.issue.domain.IssueCustomRelationList;
import com.yusys.agile.issue.service.IssueCustomFieldService;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.issue.service.IssueTemplateService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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
    IssueCustomRelationMapper issueCustomRelationMapper;
    @Resource
    IssueCustomFieldService issueCustomFieldService;
    @Resource
    CustomFieldPoolService customFieldPoolService;
    @Resource
    IssueTemplateService issueTemplateService;
    @Resource
    HeaderFieldService headerFieldService;

    /**
     * 功能描述  根据项目id与工作项类型查询自定义字段
     *
     * @param projectId
     * @param issueType
     * @return java.util.List<com.yusys.agile.issue.domain.IssueCustomRelation>
     * @date 2021/2/31
     */
    @Override
    public List<IssueCustomRelation> getIssueCustomRelations(Long projectId, Byte issueType) {
        List<CustomFieldDTO> customFieldDTOList = customFieldPoolService.listAllCustomFields("", null, null, projectId);
        Map<Long, List<CustomFieldDTO>> listMap = customFieldDTOList.stream().collect(Collectors.groupingBy(CustomFieldDTO::getFieldId));
        List<IssueCustomRelation> result = Lists.newArrayList();
        IssueCustomRelationExample issueCustomRelationExample = new IssueCustomRelationExample();
        IssueCustomRelationExample.Criteria criteria = issueCustomRelationExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        if (issueType != null) {
            criteria.andIssueTypeEqualTo(issueType);
        }
        issueCustomRelationExample.setOrderByClause("sort asc");
        result = issueCustomRelationMapper.selectByExampleWithBLOBs(issueCustomRelationExample);
        if (result.isEmpty()) {
            return result;
        }
        for (IssueCustomRelation issueCustomRelation : result) {
            if (listMap.containsKey(issueCustomRelation.getFieldId())) {
                CustomFieldDTO customFieldDTO = listMap.get(issueCustomRelation.getFieldId()).get(0);
                issueCustomRelation.setFieldType(Byte.parseByte(customFieldDTO.getFieldType().toString()));
                issueCustomRelation.setFieldContent(customFieldDTO.getFieldContent());
                issueCustomRelation.setFieldName(customFieldDTO.getFieldName());
            }
        }
        return result;
    }

    /**
     * 功能描述  移除应用的自定义字段
     *
     * @param id
     * @return
     * @date 2021/2/31
     */
    @Override
    public void deleteIssueCustomRelation(Long id) {
        IssueCustomRelation issueCustomRelation = issueCustomRelationMapper.selectByPrimaryKey(id);
        //工作项自定义字段表
        issueCustomRelationMapper.deleteByPrimaryKey(id);
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
        List<Long> longList = issueCustomRelationMapper.getAppliedByissueType(securityDTO.getProjectId(), idList.getIssueType());
        if (idList.getIssueCustomRelationList() != null && idList.getIssueCustomRelationList().size() > 0) {
            for (int i = 0; i < idList.getIssueCustomRelationList().size(); i++) {
                IssueCustomRelation issueCustomRelation = idList.getIssueCustomRelationList().get(i);
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
     * 功能描述  分页查询未应用的自定义字段
     *
     * @param securityDTO
     * @param issueType
     * @param fieldName
     * @return java.util.List<com.yusys.agile.issue.domain.IssueCustomRelation>
     * @date 2020/8/3
     */
    @Override
    public List<IssueCustomRelation> getUnApplied(SecurityDTO securityDTO, Byte issueType, String fieldName) {
        List<IssueCustomRelation> result = Lists.newArrayList();
        List<CustomFieldDTO> customFieldDTOList = customFieldPoolService.listAllCustomFields(fieldName, null, null, securityDTO.getProjectId());
        if (customFieldDTOList != null && customFieldDTOList.size() > 0) {
            IssueCustomRelationExample example = new IssueCustomRelationExample();
            example.createCriteria()
                    .andProjectIdEqualTo(securityDTO.getProjectId())
                    .andIssueTypeEqualTo(issueType);
            List<Long> idList = issueCustomRelationMapper.getAppliedByissueType(securityDTO.getProjectId(), issueType);
            for (CustomFieldDTO customFieldDTO : customFieldDTOList) {
                if (!idList.contains(customFieldDTO.getFieldId())) {
                    IssueCustomRelation issueCustomRelation = new IssueCustomRelation();
                    issueCustomRelation.setFieldId(customFieldDTO.getFieldId());
                    issueCustomRelation.setFieldName(customFieldDTO.getFieldName());
                    issueCustomRelation.setFieldType(Byte.parseByte(customFieldDTO.getFieldType().toString()));
                    issueCustomRelation.setFieldContent(customFieldDTO.getFieldContent());
                    issueCustomRelation.setProjectId(securityDTO.getProjectId());
                    issueCustomRelation.setRequired("E");
                    result.add(issueCustomRelation);
                }
            }
        }
        return result;
    }

    @Override
    public List<CustomFieldDTO> getCustomFieldDTO(Long projectId, Byte issueType) {
        List<CustomFieldDTO> result = Lists.newArrayList();
        List<IssueCustomRelation> issueCustomRelationList = getIssueCustomRelations(projectId, issueType);
        for (IssueCustomRelation issueCustomRelation : issueCustomRelationList) {
            CustomFieldDTO customFieldDTO = new CustomFieldDTO();
            customFieldDTO.setFieldId(issueCustomRelation.getId());
            JSONObject jsonObject = JSON.parseObject(issueCustomRelation.getFieldContent());
            Boolean b = true;
            if ("E".equals(issueCustomRelation.getRequired())) {
                b = false;
            }
            jsonObject.put("required", b);
            customFieldDTO.setFieldContent(jsonObject.toString());
            customFieldDTO.setFieldName(issueCustomRelation.getFieldName());
            customFieldDTO.setFieldType(Integer.parseInt(issueCustomRelation.getFieldType().toString()));
            customFieldDTO.setRequired(issueCustomRelation.getRequired());
            result.add(customFieldDTO);
        }
        return result;
    }

    @Override
    public void deleteIssueCustomRelationByFieldId(Long fieldId) {
        IssueCustomRelationExample issueCustomRelation = new IssueCustomRelationExample();
        issueCustomRelation.createCriteria().andFieldIdEqualTo(fieldId);
        List<IssueCustomRelation> issueCustomRelationList = issueCustomRelationMapper.selectByExample(issueCustomRelation);
        //工作项自定义字段表
        issueCustomRelationMapper.deleteByExample(issueCustomRelation);
        //数据表
        for (int i = 0; i < issueCustomRelationList.size(); i++) {
            //列头
            headerFieldService.deleteCustomFieldByFieldId(issueCustomRelationList.get(i).getId());
            issueCustomFieldService.deleteCustomFileByIssueCustomRelationId(issueCustomRelationList.get(i).getId());

        }
    }
}

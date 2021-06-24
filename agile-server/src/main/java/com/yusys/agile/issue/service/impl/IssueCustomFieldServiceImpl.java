package com.yusys.agile.issue.service.impl;

import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.customfield.service.CustomFieldPoolService;
import com.yusys.agile.issue.dao.SIssueCustomFieldMapper;
import com.yusys.agile.issue.dao.SIssueCustomRelationMapper;
import com.yusys.agile.issue.domain.SIssueCustomField;
import com.yusys.agile.issue.domain.SIssueCustomFieldExample;
import com.yusys.agile.issue.domain.SIssueCustomRelation;
import com.yusys.agile.issue.domain.SIssueCustomRelationExample;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;
import com.yusys.agile.issue.service.IssueCustomFieldService;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.issue.service.IssueService;
import com.google.common.collect.Lists;
import com.yusys.agile.utils.ReflectObjectUtil;
import com.yusys.portal.model.common.enums.StateEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Date: 9:22
 */
@Service
@Transactional
public class IssueCustomFieldServiceImpl implements IssueCustomFieldService {

    private static final Logger log = LoggerFactory.getLogger(IssueCustomFieldServiceImpl.class);

    @Resource
    private SIssueCustomFieldMapper issueCustomFieldMapper;

    @Resource
    SIssueCustomRelationMapper issueCustomRelationMapper;

    @Autowired
    private CustomFieldPoolService customFieldPoolService;

    @Autowired
    private IssueCustomRelationService customRelationService;

    @Autowired
    private IssueService issueService;

    @Override
    public List<IssueCustomFieldDTO> listCustomField(Long systemId, Long issueId, Byte issueType) {
        // 只查询应用的自定义字段
        //List<IssueCustomFieldDTO> issueCustomFieldDTOList  = issueCustomFieldMapper.listCustomField(issueId);
        //List<HeaderFieldDTO> headerFieldDTOS = customFieldPoolService.listAllCustomFields(null, issueType.toString(), StateEnum.U.getValue(), null, null, projectId);
        // 某类型的工作项展示的的自定义字段
        List<CustomFieldDTO> customFieldDTOList = customRelationService.getCustomFieldList(systemId, issueType);
        List<IssueCustomFieldDTO> issueCustomFieldDTOList = Lists.newArrayList();
        for (CustomFieldDTO customFieldDTO : customFieldDTOList) {
            IssueCustomFieldDTO issueCustomFieldDTO = assembleIssueCustomFieldDTO(customFieldDTO, issueId);
            issueCustomFieldDTOList.add(issueCustomFieldDTO);
        }

        return issueCustomFieldDTOList;
    }

    /**
     * 功能描述: 组装对象
     *
     * @param customFieldDTO
     * @return com.yusys.agile.issue.dto.IssueCustomFieldDTO
     * @date 2021/2/21
     */
    private IssueCustomFieldDTO assembleIssueCustomFieldDTO(CustomFieldDTO customFieldDTO, Long issueId) {
        IssueCustomFieldDTO issueCustomFieldDTO = new IssueCustomFieldDTO();
        issueCustomFieldDTO.setFieldId(customFieldDTO.getFieldId());
        issueCustomFieldDTO.setFieldName(customFieldDTO.getFieldName());
        issueCustomFieldDTO.setFieldType(customFieldDTO.getFieldType().byteValue());
        issueCustomFieldDTO.setFieldContent(customFieldDTO.getFieldContent());
        issueCustomFieldDTO.setSubjectId(issueId);

        // 查询实际数据，可能没有
        SIssueCustomFieldExample example = new SIssueCustomFieldExample();
        example.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue())
                .andFieldIdEqualTo(customFieldDTO.getFieldId())
                .andIssueIdEqualTo(issueId);
        List<SIssueCustomField> issueCustomFields = issueCustomFieldMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(issueCustomFields)) {
            issueCustomFieldDTO.setDetailId(issueCustomFields.get(0).getExtendId());
            issueCustomFieldDTO.setFieldValue(issueCustomFields.get(0).getFieldValue());
        }

        return issueCustomFieldDTO;

    }

    @Override
    public int createBatch(List<SIssueCustomField> fields) {
        return issueCustomFieldMapper.createBatch(fields);
    }

    @Override
    public void deleteInFieldIdList4Issus(List<Long> fieldIds, Long issueId) {
        issueCustomFieldMapper.deleteInFieldIdList4Issus(fieldIds, issueId);
    }

    @Override
    public void deleteCustomFileByIssueId(Long issueId) {
        issueCustomFieldMapper.deleteCustomFileByIssueId(issueId);
    }

    /**
     * 功能描述: 修改自定义字段
     * 如果有externId主键走修改，没有走新增
     *
     * @param fieldsAfterEdit
     * @return void
     * @date 2021/2/21
     */
    @Override
    public void editCustomFields(List<SIssueCustomField> fieldsAfterEdit) {
        List<SIssueCustomField> addCustomFieldList = Lists.newArrayList();
        for (SIssueCustomField tempIssueCustomField : fieldsAfterEdit) {
                // 新增时必须要填写实际值，修改时可以允许把值去掉
            if (StringUtils.isBlank(tempIssueCustomField.getFieldValue())) {
                    continue;
            }
            //先删除
            SIssueCustomFieldExample sIssueCustomFieldExample = new SIssueCustomFieldExample();
            sIssueCustomFieldExample.createCriteria()
                    .andStateEqualTo(StateEnum.U.getValue())
                    .andIssueIdEqualTo(tempIssueCustomField.getIssueId())
                    .andFieldIdEqualTo(tempIssueCustomField.getFieldId());
            issueCustomFieldMapper.deleteByExample(sIssueCustomFieldExample);
            //再新增
            addCustomFieldList.add(tempIssueCustomField);
        }
        if (CollectionUtils.isNotEmpty(addCustomFieldList)) {
            createBatch(addCustomFieldList);
        }


    }

    @Override
    public void deleteCustomFileByIssueCustomRelationId(Long issueCustomRelationId) {
        //逻辑删除（修改数据有效状态）
        issueCustomFieldMapper.updateStateByCustomRelationId(issueCustomRelationId, StateEnum.E.getValue());
    }

    @Override
    public List<SIssueCustomField> selectIssueIdByProjectId(Long projectId) {
        List<Long> issueIds = issueService.selectIssueIdByProjectId(projectId, null);
        if (issueIds.isEmpty()) {
            return Lists.newArrayList();
        }
        SIssueCustomFieldExample issueCustomFieldExample = new SIssueCustomFieldExample();
        issueCustomFieldExample.createCriteria()
                .andIssueIdIn(issueIds);
        return issueCustomFieldMapper.selectByExample(issueCustomFieldExample);
    }

    @Override
    public void recoveryCustomFileByIssueCustomRelationId(Long issueCustomRelationId) {
        //（修改数据有效状态）
        issueCustomFieldMapper.updateStateByCustomRelationId(issueCustomRelationId, StateEnum.U.getValue());
    }

    @Override
    public List<IssueCustomFieldDTO> listCustomFieldByIssueId(Long issueId) {
        List<IssueCustomFieldDTO> issueCustomFieldDTOS = Lists.newArrayList();
        SIssueCustomFieldExample sIssueCustomFieldExample = new SIssueCustomFieldExample();
        sIssueCustomFieldExample.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue())
                .andIssueIdEqualTo(issueId);
        List<SIssueCustomField> issueCustomFields =  issueCustomFieldMapper.selectByExample(sIssueCustomFieldExample);
        if(CollectionUtils.isNotEmpty(issueCustomFields)){
            for (SIssueCustomField sIssueCustomField:issueCustomFields) {
                CustomFieldDTO customFieldDTO = listCustomPoolFieldByssueCustomFieldId(sIssueCustomField.getFieldId());
                if(Optional.ofNullable(customFieldDTO).isPresent()){
                    IssueCustomFieldDTO issueCustomFieldDTO = ReflectObjectUtil.copyProperties(sIssueCustomField,IssueCustomFieldDTO.class);
                    issueCustomFieldDTO.setFieldContent(customFieldDTO.getFieldContent());
                    issueCustomFieldDTO.setFieldType(Byte.parseByte(customFieldDTO.getFieldType().toString()));
                    issueCustomFieldDTOS.add(issueCustomFieldDTO);
                }
            }
        }
        return issueCustomFieldDTOS;
    }
    public CustomFieldDTO listCustomPoolFieldByssueCustomFieldId(Long fieldId){
        CustomFieldDTO customFieldDTO = null;
        SIssueCustomRelationExample sIssueCustomRelationExample = new SIssueCustomRelationExample();
        sIssueCustomRelationExample.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue())
                .andIdEqualTo(fieldId);
        List<SIssueCustomRelation> sIssueCustomRelations = issueCustomRelationMapper.selectByExample(sIssueCustomRelationExample);
        if(CollectionUtils.isNotEmpty(sIssueCustomRelations)){
            customFieldDTO =   customFieldPoolService.getCustomField(sIssueCustomRelations.get(0).getFieldId());
        }
       return customFieldDTO;
    }


}

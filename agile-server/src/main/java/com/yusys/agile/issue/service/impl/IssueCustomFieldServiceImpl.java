package com.yusys.agile.issue.service.impl;

import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.customfield.service.CustomFieldPoolService;
import com.yusys.agile.issue.dao.SIssueCustomFieldMapper;
import com.yusys.agile.issue.domain.SIssueCustomField;
import com.yusys.agile.issue.domain.SIssueCustomFieldExample;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;
import com.yusys.agile.issue.service.IssueCustomFieldService;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.issue.service.IssueService;
import com.google.common.collect.Lists;
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

/**
 * @Date: 9:22
 */
@Service
@Transactional
public class IssueCustomFieldServiceImpl implements IssueCustomFieldService {

    private static final Logger log = LoggerFactory.getLogger(IssueCustomFieldServiceImpl.class);

    @Resource
    private SIssueCustomFieldMapper issueCustomFieldMapper;


    @Autowired
    private CustomFieldPoolService customFieldPoolService;

    @Autowired
    private IssueCustomRelationService customRelationService;

    @Autowired
    private IssueService issueService;

    @Override
    public List<IssueCustomFieldDTO> listCustomField(Long issueId, Byte issueType, Long projectId) {
        // 只查询应用的自定义字段
        //List<IssueCustomFieldDTO> issueCustomFieldDTOList  = issueCustomFieldMapper.listCustomField(issueId);
        //List<HeaderFieldDTO> headerFieldDTOS = customFieldPoolService.listAllCustomFields(null, issueType.toString(), StateEnum.U.getValue(), null, null, projectId);
        // 某类型的工作项展示的的自定义字段
        List<CustomFieldDTO> customFieldDTOList = customRelationService.getCustomFieldList(projectId, issueType);
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
        example.createCriteria().andFieldIdEqualTo(customFieldDTO.getFieldId()).andIssueIdEqualTo(issueId);
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
            // 修改
            if (null != tempIssueCustomField.getExtendId()) {
                SIssueCustomFieldExample example = new SIssueCustomFieldExample();
                example.createCriteria().andExtendIdEqualTo(tempIssueCustomField.getExtendId());
                issueCustomFieldMapper.updateByExampleSelective(tempIssueCustomField, example);
            } else {
                // 新增时必须要填写实际值，修改时可以允许把值去掉
                if (StringUtils.isBlank(tempIssueCustomField.getFieldValue())) {
                    continue;
                }
                addCustomFieldList.add(tempIssueCustomField);
            }
        }

        // 新增
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
}

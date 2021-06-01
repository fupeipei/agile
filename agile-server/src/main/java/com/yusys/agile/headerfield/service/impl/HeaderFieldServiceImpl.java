package com.yusys.agile.headerfield.service.impl;

import com.yusys.agile.constant.StringConstant;
import com.yusys.agile.customfield.domain.CustomFieldPoolExample;
import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.customfield.service.CustomFieldPoolService;
import com.yusys.agile.externalapiconfig.dao.util.ExternalApiConfigUtil;
import com.yusys.agile.headerfield.dao.HeaderFieldMapper;
import com.yusys.agile.headerfield.domain.HeaderField;
import com.yusys.agile.headerfield.domain.HeaderFieldExample;
import com.yusys.agile.headerfield.enums.FieldTypeEnum;
import com.yusys.agile.headerfield.enums.IsCustomEnum;
import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.agile.headerfielduser.domain.HeaderFieldUser;
import com.yusys.agile.headerfielduser.service.HeaderFieldUserService;
import com.yusys.agile.issue.domain.IssueCustomField;
import com.yusys.agile.issue.domain.IssueCustomRelation;
import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;
import com.yusys.agile.issue.enums.IssueHistoryRecordTypeEnum;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.issue.utils.IssueHistoryRecordFactory;
import com.yusys.agile.utils.ObjectUtil;
import com.yusys.agile.versionmanager.constants.VersionConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * :
 *
 * @Date: 2020/4/13
 */
@Service("headerFieldService")
public class HeaderFieldServiceImpl implements HeaderFieldService {
    private static final Logger logger = LoggerFactory.getLogger(HeaderFieldServiceImpl.class);

    @Resource
    private HeaderFieldMapper headerFieldMapper;
    @Resource
    private HeaderFieldUserService headerFieldUserService;
    @Resource
    private CustomFieldPoolService customFieldPoolService;
    @Resource
    private IssueCustomRelationService issueCustomRelationService;
    @Resource
    private ExternalApiConfigUtil externalApiConfigUtil;

    /**
     * 功能描述  查询所有的列头字段
     *
     * @param
     * @return java.util.List<com.yusys.agile.headerfield.dto.HeaderFieldDTO>
     * @date 2020/4/13
     */
    @Override
    public List<HeaderField> queryAllHeaderFields(SecurityDTO securityDTO, Byte category, Byte isFilter) {
        /** 查询自定义字段，先注释掉自定义字段
        List<CustomFieldDTO> customFieldDTOList = customFieldPoolService.listAllCustomFields("", null, null, securityDTO.getSystemId());
        Map<Long, List<CustomFieldDTO>> listMap = customFieldDTOList.stream().collect(Collectors.groupingBy(CustomFieldDTO::getFieldId));
        List<IssueCustomRelation> issueCustomRelationList = issueCustomRelationService.getIssueCustomRelations(securityDTO.getProjectId(), category);
        Map<Long, List<IssueCustomRelation>> longListMap = issueCustomRelationList.stream().collect(Collectors.groupingBy(IssueCustomRelation::getId));
        List<HeaderField> allHeaderField = Lists.newArrayList();
        List<HeaderField> allHeaderFieldCategoryIsNull = Lists.newArrayList();
        List<HeaderField> allHeaderFieldFault = Lists.newArrayList();
        List<HeaderField> allHeaderFieldCustom = Lists.newArrayList();
        HeaderFieldExample headerFieldExampleForCustom = new HeaderFieldExample();
        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        HeaderFieldExample.Criteria headerFieldExampleCriteria = headerFieldExample.createCriteria();
        HeaderFieldExample headerFieldExampleTemp = new HeaderFieldExample();
        HeaderFieldExample.Criteria headerFieldExampleTempCriteria = headerFieldExampleTemp.createCriteria();

        //初始化基础数据
        headerFieldExampleCriteria.andIsCustomEqualTo(IsCustomEnum.FALSE.getValue()).andCategoryIsNull();
        //如果是fault，过滤null与5的
        headerFieldExampleTempCriteria.andIsCustomEqualTo(IsCustomEnum.FALSE.getValue()).andCategoryEqualTo(category);
        if (isFilter != null && Byte.parseByte("1") == isFilter) {
            //只查询time、select、time_date
            List<Byte> values = Lists.newArrayList();
            values.add(Byte.parseByte("2"));
            values.add(Byte.parseByte("3"));
            values.add(Byte.parseByte("5"));
            List<String> list = Lists.newArrayList();
            list.add("taskType");
            list.add("issueType");
            headerFieldExampleCriteria.andFieldTypeIn(values).andFieldCodeNotIn(list);
            headerFieldExampleTempCriteria.andFieldTypeIn(values).andFieldCodeNotIn(list);
        }
        allHeaderFieldCategoryIsNull = headerFieldMapper.selectByExampleWithBLOBs(headerFieldExample);
        allHeaderFieldFault = headerFieldMapper.selectByExampleWithBLOBs(headerFieldExampleTemp);
        headerFieldExampleForCustom.createCriteria()
                .andCategoryEqualTo(category)
                .andProjectIdEqualTo(securityDTO.getProjectId())
                .andIsCustomEqualTo(IsCustomEnum.TRUE.getValue());
        allHeaderFieldCustom = headerFieldMapper.selectByExampleWithBLOBs(headerFieldExampleForCustom);
        allHeaderFieldCustom.forEach(HeaderField -> {
            if (longListMap.containsKey(Long.parseLong(HeaderField.getFieldCode())) && listMap.containsKey(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId())) {
                HeaderField.setFieldName(listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldName());
                HeaderField.setFieldType(Byte.parseByte(listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldType().toString()));
                HeaderField.setFieldContent(listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldContent());
                HeaderField.setRequired(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getRequired());
                HeaderField.setFieldPoolCode("pool_code" + listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldId());
                HeaderField.setFieldCode("pool_code" + listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldId());
            }

        });
        allHeaderFieldCategoryIsNull.addAll(allHeaderFieldFault);
        allHeaderField.addAll(allHeaderFieldCategoryIsNull);
        allHeaderField.addAll(allHeaderFieldCustom);
        //处理fieldType对应的name
        for (int i = 0; i < allHeaderField.size(); i++) {
            allHeaderField.get(i).setFieldTypeName(FieldTypeEnum.getName(allHeaderField.get(i).getFieldType()));
        }
        return allHeaderField;
         **/
        List<HeaderField> allHeaderField = Lists.newArrayList();
        List<HeaderField> allHeaderFieldCategoryIsNull = Lists.newArrayList();
        List<HeaderField> allHeaderFieldFault = Lists.newArrayList();

        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        HeaderFieldExample.Criteria headerFieldExampleCriteria = headerFieldExample.createCriteria();
        HeaderFieldExample headerFieldExampleTemp = new HeaderFieldExample();
        HeaderFieldExample.Criteria headerFieldExampleTempCriteria = headerFieldExampleTemp.createCriteria();

        //初始化基础数据
        headerFieldExampleCriteria.andIsCustomEqualTo(IsCustomEnum.FALSE.getValue()).andCategoryIsNull();
        //如果是fault，过滤null与5的
        headerFieldExampleTempCriteria.andIsCustomEqualTo(IsCustomEnum.FALSE.getValue()).andCategoryEqualTo(category);
        if (isFilter != null && Byte.parseByte("1") == isFilter) {
            //只查询time、select、time_date
            List<Byte> values = Lists.newArrayList();
            values.add(Byte.parseByte("2"));
            values.add(Byte.parseByte("3"));
            values.add(Byte.parseByte("5"));
            List<String> list = Lists.newArrayList();
            list.add("taskType");
            list.add("issueType");
            headerFieldExampleCriteria.andFieldTypeIn(values).andFieldCodeNotIn(list);
            headerFieldExampleTempCriteria.andFieldTypeIn(values).andFieldCodeNotIn(list);
        }
        allHeaderFieldCategoryIsNull = headerFieldMapper.selectByExampleWithBLOBs(headerFieldExample);
        allHeaderFieldFault = headerFieldMapper.selectByExampleWithBLOBs(headerFieldExampleTemp);

        allHeaderFieldCategoryIsNull.addAll(allHeaderFieldFault);
        allHeaderField.addAll(allHeaderFieldCategoryIsNull);

        //处理fieldType对应的name
        for (int i = 0; i < allHeaderField.size(); i++) {
            allHeaderField.get(i).setFieldTypeName(FieldTypeEnum.getName(allHeaderField.get(i).getFieldType()));
        }
        return allHeaderField;
    }

    /**
     * 功能描述
     *
     * @param projectId
     * @param isCustom
     * @param categary
     * @return java.util.List<com.yusys.agile.headerfield.domain.HeaderField>
     * @date 2020/4/17
     */
    @Override
    public List<HeaderField> getFieldIdList(Long projectId, Byte isCustom, Byte categary) {
        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        //初始化基础数据
        HeaderFieldExample.Criteria criteria = headerFieldExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andIsCustomEqualTo(isCustom).andCategoryEqualTo(categary);
        List<HeaderField> allHeaderField = headerFieldMapper.selectByExample(headerFieldExample);
        return allHeaderField;
    }

    /**
     * 功能描述
     *
     * @param newFieldList
     * @param oldFieldList
     * @param issueType
     * @param issueId
     * @param projectId
     * @return java.util.List<com.yusys.agile.issue.domain.IssueHistoryRecord>
     * @date 2020/4/17
     */
    @Override
    public List<IssueHistoryRecord> generateHistory(List<IssueCustomField> newFieldList, List<IssueCustomFieldDTO> oldFieldList, Byte issueType, Long issueId, Long projectId) {
        HeaderFieldExample example = new HeaderFieldExample();
        HeaderFieldExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId).andCategoryEqualTo(issueType);
        List<HeaderField> customFieldList = headerFieldMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(customFieldList)) {
            return new ArrayList<>();
        }
        Map<Long, String> newFieldMap = new HashMap<>();
        for (HeaderField field : customFieldList) {
            newFieldMap.put(field.getFieldId(), null);
        }
        Map<Long, String> oldFieldMap = new HashMap<>();
        oldFieldMap.putAll(newFieldMap);
        if (newFieldList != null && !newFieldList.isEmpty()) {
            for (IssueCustomField newField : newFieldList) {
                newFieldMap.put(newField.getFieldId(), newField.getFieldValue());
            }
        }
        if (oldFieldList != null && !oldFieldList.isEmpty()) {
            for (IssueCustomFieldDTO oldField : oldFieldList) {
                oldFieldMap.put(oldField.getFieldId(), oldField.getFieldValue());
            }
        }
        List<IssueHistoryRecord> issueHistoryList = new ArrayList<>();
        for (Long key : newFieldMap.keySet()) {
            if (!ObjectUtil.equals(newFieldMap.get(key), oldFieldMap.get(key))) {
                IssueHistoryRecord issueHistoryRecord = IssueHistoryRecordFactory.createHistoryRecord(issueId, IsCustomEnum.TRUE.getValue(), IssueHistoryRecordTypeEnum.TYPE_NORMAL_TEXT.CODE, String.valueOf(key));
                issueHistoryRecord.setOldValue(oldFieldMap.get(key));
                issueHistoryRecord.setNewValue(newFieldMap.get(key));
                issueHistoryList.add(issueHistoryRecord);
            }
        }
        return issueHistoryList;
    }

    /**
     * 功能描述 根据key获取列头数据
     *
     * @param updateList
     * @return java.util.List<com.yusys.agile.headerfield.domain.HeaderField>
     * @date 2020/4/15
     */
    @Override
    public List<HeaderField> getAllHeaderField(List<Long> updateList) {
        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        HeaderFieldExample.Criteria criteria = headerFieldExample.createCriteria();
        if (updateList != null && !updateList.isEmpty()) {
            criteria.andFieldIdIn(updateList);
        }
        return headerFieldMapper.selectByExample(headerFieldExample);
    }

    /**
     * 功能描述 初始化列头字段,查询条件
     *
     * @param securityDTO
     * @param category
     * @return java.util.Map
     * @date 2020/4/16
     */
    @Override
    public Map queryHeaderFields(SecurityDTO securityDTO, Byte category, Byte isFilter) {
        Map<String, List> result = new HashMap<>();
        try {
            //查询所有列头字段
            List<HeaderField> fields = queryAllHeaderFields(securityDTO, category, isFilter);
            result.put("fields", fields);
            List<HeaderFieldUser> visibleFields = headerFieldUserService.queryVisibleHeaderFields(securityDTO, category, isFilter);
            if (visibleFields == null || visibleFields.isEmpty()) {
                result.put("visibleFields", fields);
            } else {
                Map<Long, List<HeaderField>> fieldsMap = fields.stream().collect(Collectors.groupingBy(HeaderField::getFieldId));
                List<HeaderField> visibleHeaderFields = Lists.newArrayList();
                for (int i = 0; i < visibleFields.size(); i++) {
                    if (fieldsMap.containsKey(visibleFields.get(i).getFieldId())) {
                        visibleHeaderFields.add(fieldsMap.get(visibleFields.get(i).getFieldId()).get(0));
                    }
                }
                result.put("visibleFields", visibleHeaderFields);
            }
            //处理fieldType对应的name
            for (String str : result.keySet()) {
                for (int i = 0; i < result.get(str).size(); i++) {
                    HeaderField headerField = (((HeaderField) result.get(str).get(i)));
                    headerField.setFieldTypeName(FieldTypeEnum.getName(headerField.getFieldType()));
                }
            }
        } catch (Exception e) {
            logger.error("初始化列头字段异常", e);
        }
        return result;
    }

    @Override
    public Integer deleteCustomFieldByFieldId(Long fieldId) {
        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        headerFieldExample.createCriteria()
                .andFieldCodeEqualTo(fieldId.toString())
                .andIsCustomEqualTo(Byte.parseByte("1"));
        List<HeaderField> headerFields = headerFieldMapper.selectByExample(headerFieldExample);
        for (int i = 0; i < headerFields.size(); i++) {
            headerFieldUserService.deleteCustomField(headerFields.get(i).getFieldId());
            headerFieldMapper.deleteByPrimaryKey(headerFields.get(i).getFieldId());
        }
        return headerFields.size();
    }

    @Override
    public Integer saveCustomFieldByFieldId(Long projectId, Long fieldId, Byte issueType) {
        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        headerFieldExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andFieldCodeEqualTo(fieldId.toString())
                .andCategoryEqualTo(issueType);
        if (headerFieldMapper.selectByExample(headerFieldExample).size() > 0) {
            return headerFieldMapper.selectByExample(headerFieldExample).size();
        }
        HeaderField headerField = new HeaderField();
        headerField.setFieldCode(fieldId.toString());
        headerField.setProjectId(projectId);
        headerField.setCategory(issueType);
        headerField.setIsCustom(IsCustomEnum.TRUE.getValue());
        headerField.setFieldGroup("custom");
        return headerFieldMapper.insertSelective(headerField);
    }

    @Override
    public Map getAllHeaderFieldContNotNull() {
        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        headerFieldExample.createCriteria().andFieldCodeIsNotNull();
        List<HeaderField> headerFields = headerFieldMapper.selectByExampleWithBLOBs(headerFieldExample);
        Map<String, HashMap<String, String>> mapMap = new HashMap<>();
        for (int i = 0; i < headerFields.size(); i++) {
            HashMap map = new HashMap<String, String>();
            if (headerFields.get(i).getFieldContent() != null && !headerFields.get(i).getFieldContent().equals("")) {
                JSONObject jsonObject = JSON.parseObject(headerFields.get(i).getFieldContent());
                JSONArray jsonArray = jsonObject.getJSONArray("optionList");
                for (int j = 0; j < jsonArray.size(); j++) {
                    map.put(jsonArray.getJSONObject(j).getString("key"), jsonArray.getJSONObject(j).getString("value"));
                }
                mapMap.put(headerFields.get(i).getFieldCode(), map);
            }
        }
        return mapMap;
    }

    @Override
    public List<HeaderField> getAllHeaderFieldByProjectId(Long projectId) {
        List<CustomFieldDTO> customFieldDTOList = customFieldPoolService.listAllCustomFields("", null, null, projectId);
        Map<Long, List<CustomFieldDTO>> listMap = customFieldDTOList.stream().collect(Collectors.groupingBy(CustomFieldDTO::getFieldId));
        List<IssueCustomRelation> issueCustomRelationList = issueCustomRelationService.getIssueCustomRelations(projectId, null);
        Map<Long, List<IssueCustomRelation>> longListMap = issueCustomRelationList.stream().collect(Collectors.groupingBy(IssueCustomRelation::getId));
        List<HeaderField> headerFields = Lists.newArrayList();
        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        HeaderFieldExample.Criteria criteria = headerFieldExample.createCriteria();
        criteria.andIsCustomEqualTo(Byte.parseByte("1"));
        headerFields = headerFieldMapper.selectByExample(headerFieldExample);
        headerFields.forEach(HeaderField -> {
            if (longListMap.containsKey(Long.parseLong(HeaderField.getFieldCode())) && listMap.containsKey(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId())) {
                HeaderField.setFieldName(listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldName());
                HeaderField.setFieldType(Byte.parseByte(listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldType().toString()));
                HeaderField.setFieldContent(listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldContent());
                HeaderField.setRequired(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getRequired());
                HeaderField.setFieldPoolCode("pool_code" + listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldId());
            }
        });
        return headerFields;
    }

    /**
     * 租户下的自定义字段列头数据
     * @param tenantCode
     * @return
     */
    @Override
    public List<HeaderField> getAllHeaderFieldByTenantCode(String tenantCode) {
        return  orderHeaderFieldDTO(customFieldPoolService.listAllCustomFieldsByTenantCode(tenantCode));
    }

    /**
     * 组织列表自定义字段数据
     * @param customFieldDTOList
     * @return
     */
    public List<HeaderField> orderHeaderFieldDTO( List<CustomFieldDTO> customFieldDTOList){
        Map<Long, List<CustomFieldDTO>> listMap = customFieldDTOList.stream().collect(Collectors.groupingBy(CustomFieldDTO::getFieldId));
        List<IssueCustomRelation> issueCustomRelationList = issueCustomRelationService.getIssueCustomRelations(null, null);
        Map<Long, List<IssueCustomRelation>> longListMap = issueCustomRelationList.stream().collect(Collectors.groupingBy(IssueCustomRelation::getId));
        List<HeaderField> headerFields = Lists.newArrayList();
        HeaderFieldExample headerFieldExample = new HeaderFieldExample();
        HeaderFieldExample.Criteria criteria = headerFieldExample.createCriteria();
        criteria.andIsCustomEqualTo(Byte.parseByte("1"));
        headerFields = headerFieldMapper.selectByExample(headerFieldExample);
        headerFields.forEach(HeaderField -> {
            if (longListMap.containsKey(Long.parseLong(HeaderField.getFieldCode())) && listMap.containsKey(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId())) {
                HeaderField.setFieldName(listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldName());
                HeaderField.setFieldType(Byte.parseByte(listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldType().toString()));
                HeaderField.setFieldContent(listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldContent());
                HeaderField.setRequired(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getRequired());
                HeaderField.setFieldPoolCode("pool_code" + listMap.get(longListMap.get(Long.parseLong(HeaderField.getFieldCode())).get(0).getFieldId()).get(0).getFieldId());
            }
        });
        return headerFields;
    }

}

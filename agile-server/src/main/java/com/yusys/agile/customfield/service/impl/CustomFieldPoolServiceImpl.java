package com.yusys.agile.customfield.service.impl;

import com.yusys.agile.constant.StringConstant;
import com.yusys.agile.customfield.dao.CustomFieldPoolMapper;
import com.yusys.agile.customfield.domain.CustomFieldPool;
import com.yusys.agile.customfield.domain.CustomFieldPoolExample;
import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.customfield.service.CustomFieldPoolService;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.github.pagehelper.PageHelper;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自定义字段池实现类
 *
 * @create 2021/2/1
 */
@Service("customFieldPoolService")
public class CustomFieldPoolServiceImpl implements CustomFieldPoolService {

    /**
     * log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFieldPoolServiceImpl.class);

    @Autowired
    private CustomFieldPoolMapper customFieldPoolMapper;

    @Autowired
    private IssueCustomRelationService issueCustomRelationService;

    /**
     * 功能描述: 新增自定义字段
     *
     * @param customFieldDTO
     * @return void
     * @date 2021/2/1
     */
    @Override
    public void addCustomField(CustomFieldDTO customFieldDTO) throws Exception {
        if (null == customFieldDTO || StringUtils.isBlank(customFieldDTO.getFieldName())
                || null == customFieldDTO.getProjectId()) {
            throw new BusinessException("入参错误！");
        }
        String fieldName = customFieldDTO.getFieldName();
        Long projectId = customFieldDTO.getProjectId();
        // 查询同一项目下是否有名字相同的自定义字段
        if (checkPoolSameName(fieldName, projectId, null)) {
            throw new BusinessException("该字段名[" + fieldName + "]在项目中已经存在！");
        }
        CustomFieldPool customFieldPool = ReflectUtil.copyProperties(customFieldDTO, CustomFieldPool.class);
        customFieldPool.setState(StateEnum.U.getValue());
        customFieldPoolMapper.insert(customFieldPool);
    }

    /**
     * 功能描述: 检查是否有重名
     *
     * @param fieldName
     * @param projectId
     * @return boolean
     * @date 2021/2/1
     */
    private boolean checkPoolSameName(String fieldName, Long projectId, Long fieldId) {
        CustomFieldPoolExample example = new CustomFieldPoolExample();
        CustomFieldPoolExample.Criteria criteria = example.createCriteria().andFieldNameEqualTo(fieldName)
                .andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue());
        // 去除本身的名字校验
        if (null != fieldId) {
            criteria.andFieldIdNotEqualTo(fieldId);
        }

        List<CustomFieldPool> sameNameList = customFieldPoolMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(sameNameList)) {
            return true;
        }

        return false;
    }

    /**
     * 功能描述: 修改自定义字段
     * 只允许修改name和comment
     *
     * @param customFieldDTO
     * @return void
     * @date 2021/2/1
     */
    @Override
    public void editCustomField(CustomFieldDTO customFieldDTO) throws Exception {
        if (null == customFieldDTO || null == customFieldDTO.getFieldId()) {
            throw new BusinessException("入参错误！");
        }

        String fieldName = customFieldDTO.getFieldName();
        if (StringUtils.isNotBlank(fieldName) && checkPoolSameName(fieldName, customFieldDTO.getProjectId(), customFieldDTO.getFieldId())) {
            throw new BusinessException("该字段名[" + fieldName + "]在项目中已经存在！");
        }
        //只允许修改名字和备注
        CustomFieldPool customFieldPool = new CustomFieldPool();
        customFieldPool.setFieldId(customFieldDTO.getFieldId());
        customFieldPool.setFieldName(StringUtils.isNotBlank(fieldName) ? fieldName : null);
        customFieldPool.setComment(StringUtils.isNotBlank(customFieldDTO.getComment()) ? customFieldDTO.getComment() : null);
        customFieldPoolMapper.updateByPrimaryKeySelective(customFieldPool);
    }

    /**
     * 功能描述: 删除自定义字段
     *
     * @param fieldId
     * @return void
     * @date 2021/2/1
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomField(Long fieldId) throws Exception {
        issueCustomRelationService.deleteIssueCustomRelationByFieldId(fieldId);
        customFieldPoolMapper.deleteByPrimaryKey(fieldId);
    }


    @Override
    public List<CustomFieldDTO> listAllCustomFields(String fieldName, Integer pageNum, Integer pageSize, Long projectId) {
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        CustomFieldPoolExample example = new CustomFieldPoolExample();
        CustomFieldPoolExample.Criteria criteria = example.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue())
                .andProjectIdEqualTo(projectId);
        if (StringUtils.isNotBlank(fieldName)) {
            criteria.andFieldNameLike(StringConstant.PERCENT_SIGN + fieldName + StringConstant.PERCENT_SIGN);
        }
        // 排序
        example.setOrderByClause("create_time desc");

        return customFieldPoolMapper.selectDTOByExampleWithBLOBs(example);


    }


    /**
     * 功能描述: 查询自定义字段
     *
     * @param fieldId
     * @return com.yusys.agile.customfield.dto.CustomFieldDTO
     * @date 2021/2/31
     */
    @Override
    public CustomFieldDTO getCustomField(Long fieldId) {
        CustomFieldPool customFieldPool = customFieldPoolMapper.selectByPrimaryKey(fieldId);
        if (null == customFieldPool) {
            return null;
        }
        CustomFieldDTO customFieldDTO = ReflectUtil.copyProperties(customFieldPool, CustomFieldDTO.class);
        return customFieldDTO;
    }

    @Override
    public List<CustomFieldDTO> listAllCustomFieldsByTenantCode(String tenantCode) {
        CustomFieldPoolExample example = new CustomFieldPoolExample();
        CustomFieldPoolExample.Criteria criteria = example.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue());
        if (StringUtils.isNotBlank(tenantCode)) {
            criteria.andTenantCodeEqualTo(tenantCode);
        }
        // 排序
        example.setOrderByClause("create_time desc");

        return customFieldPoolMapper.selectDTOByExampleWithBLOBs(example);
    }
}
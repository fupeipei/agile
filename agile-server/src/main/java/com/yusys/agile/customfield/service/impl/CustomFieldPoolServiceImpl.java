package com.yusys.agile.customfield.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.yusys.agile.constant.StringConstant;
import com.yusys.agile.customfield.dao.SCustomFieldPoolMapper;
import com.yusys.agile.customfield.domain.SCustomFieldPool;
import com.yusys.agile.customfield.domain.SCustomFieldPoolExample;
import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.customfield.service.CustomFieldPoolService;
import com.yusys.agile.issue.service.IssueCustomFieldService;
import com.yusys.agile.issue.service.IssueCustomRelationService;
import com.yusys.agile.utils.StringUtil;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private SCustomFieldPoolMapper customFieldPoolMapper;

    @Autowired
    private IssueCustomRelationService issueCustomRelationService;


    /**
     * 添加自定义字段
     *
     * @param customFieldDTO 自定义字段dto
     * @return {@link String}
     * @author 张宇
     */
    @Override
    public String addCustomField(CustomFieldDTO customFieldDTO) {
        if (null == customFieldDTO || StringUtils.isBlank(customFieldDTO.getFieldName())) {
            throw new BusinessException("入参错误！");
        }
        SCustomFieldPool customFieldPool = new SCustomFieldPool();
        BeanUtils.copyProperties(customFieldDTO, customFieldPool);
        //system为空则为系统外自定义字段,反之则为系统内自定义字段
        //>0 返回false
        if (!customFieldPoolMapper.checkCustomFieldRepeatability(customFieldPool.getFieldId(), customFieldPool.getFieldName(), customFieldPool.getSystemId())) {
            throw new BusinessException("该字段名[" + customFieldPool.getFieldName() + "]在项目中已经存在！");
        }
        customFieldPool.setState(StateEnum.U.getValue());
        customFieldPoolMapper.insert(customFieldPool);
        return "自增字段新增成功";

    }
//    /**
//     * 功能描述: 新增自定义字段
//     *
//     * @param customFieldDTO
//     * @return void
//     * @date 2021/2/1
//     */
//    public void addCustomField(CustomFieldDTO customFieldDTO) throws Exception {
//        if (null == customFieldDTO || StringUtils.isBlank(customFieldDTO.getFieldName())
//                || null == customFieldDTO.getProjectId()) {
//            throw new BusinessException("入参错误！");
//        }
//        String fieldName = customFieldDTO.getFieldName();
//        Long projectId = customFieldDTO.getProjectId();
//        // 查询同一项目下是否有名字相同的自定义字段
//        if (checkPoolSameName(fieldName, projectId, null)) {
//            throw new BusinessException("该字段名[" + fieldName + "]在项目中已经存在！");
//        }
//        SCustomFieldPool customFieldPool = ReflectUtil.copyProperties(customFieldDTO, SCustomFieldPool.class);
//        customFieldPool.setState(StateEnum.U.getValue());
//        customFieldPoolMapper.insert(customFieldPool);
//    }


    /**
     * 编辑自定义字段
     *
     * @param customFieldDTO 自定义字段dto
     * @return {@link String}
     * @author 张宇
     */
    @Override
    public String editCustomField(CustomFieldDTO customFieldDTO) {
        if (null == customFieldDTO || null == customFieldDTO.getFieldId()) {
            throw new BusinessException("入参错误！");
        }

        if (ObjectUtil.isEmpty(customFieldDTO.getFieldName()) && ObjectUtil.isEmpty(customFieldDTO.getComment())) {
            return "修改成功";
        }

        SCustomFieldPool customFieldPool = new SCustomFieldPool();
        BeanUtils.copyProperties(customFieldDTO, customFieldPool);

        //>0 返回false
        if (!customFieldPoolMapper.checkCustomFieldRepeatability(customFieldPool.getFieldId(), customFieldPool.getFieldName(), customFieldPool.getSystemId())) {
            throw new BusinessException("该字段名[" + customFieldPool.getFieldName() + "]在项目中已经存在！");
        }
        //只允许修改名字和备注
        customFieldPoolMapper.editCustomField(customFieldPool.getFieldId(),customFieldPool.getFieldName(), customFieldPool.getComment());
        return "修改成功";
    }

    //    /**
//     * 功能描述: 修改自定义字段
//     * 只允许修改name和comment
//     *
//     * @param customFieldDTO
//     * @return void
//     * @date 2021/2/1
//     */
//    @Override
//    public void editCustomField(CustomFieldDTO customFieldDTO) throws Exception {
//        if (null == customFieldDTO || null == customFieldDTO.getFieldId()) {
//            throw new BusinessException("入参错误！");
//        }
//
//        String fieldName = customFieldDTO.getFieldName();
//        if (StringUtils.isNotBlank(fieldName) && checkPoolSameName(fieldName, customFieldDTO.getProjectId(), customFieldDTO.getFieldId())) {
//            throw new BusinessException("该字段名[" + fieldName + "]在项目中已经存在！");
//        }
//        //只允许修改名字和备注
//        SCustomFieldPool customFieldPool = new SCustomFieldPool();
//        customFieldPool.setFieldId(customFieldDTO.getFieldId());
//        customFieldPool.setFieldName(StringUtils.isNotBlank(fieldName) ? fieldName : null);
//        customFieldPool.setComment(StringUtils.isNotBlank(customFieldDTO.getComment()) ? customFieldDTO.getComment() : null);
//        customFieldPoolMapper.updateByPrimaryKeySelective(customFieldPool);
//    }


    /**
     * 功能描述: 检查是否有重名
     *
     * @param fieldName
     * @param projectId
     * @return boolean
     * @date 2021/2/1
     */
    private boolean checkPoolSameName(String fieldName, Long projectId, Long fieldId) {
        SCustomFieldPoolExample example = new SCustomFieldPoolExample();
        SCustomFieldPoolExample.Criteria criteria = example.createCriteria().andFieldNameEqualTo(fieldName)
                .andProjectIdEqualTo(projectId).andStateEqualTo(StateEnum.U.getValue());
        // 去除本身的名字校验
        if (null != fieldId) {
            criteria.andFieldIdNotEqualTo(fieldId);
        }

        List<SCustomFieldPool> sameNameList = customFieldPoolMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(sameNameList)) {
            return true;
        }

        return false;
    }


    /**
     * 删除自定义字段
     * @author zhaofeng
     * @date 2021/6/3 16:59
     * @param fieldId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomField(Long fieldId){
        //逻辑删除字段属性值，以便后续数据恢复
        issueCustomRelationService.deleteIssueCustomRelationByFieldId(fieldId);
        customFieldPoolMapper.updateStateById(fieldId, StateEnum.E.getValue());
    }


    @Override
    public List<CustomFieldDTO> listAllCustomFields(Long systemId, String fieldName, Integer pageNum, Integer pageSize) {
        // 不传page信息时查全部数据
        if (null != pageNum && null != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        SCustomFieldPoolExample example = new SCustomFieldPoolExample();
        SCustomFieldPoolExample.Criteria criteria = example.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue());
        if (StringUtils.isNotBlank(fieldName)) {
            criteria.andFieldNameLike(StringConstant.PERCENT_SIGN + fieldName + StringConstant.PERCENT_SIGN);
        }
        if(systemId != null){
            criteria.andSystemIdEqualTo(systemId);
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
        SCustomFieldPool customFieldPool = customFieldPoolMapper.selectByPrimaryKey(fieldId);
        if (null == customFieldPool) {
            return null;
        }
        CustomFieldDTO customFieldDTO = ReflectUtil.copyProperties(customFieldPool, CustomFieldDTO.class);
        return customFieldDTO;
    }

    @Override
    public List<CustomFieldDTO> listAllCustomFieldsByTenantCode(String tenantCode) {
        SCustomFieldPoolExample example = new SCustomFieldPoolExample();
        SCustomFieldPoolExample.Criteria criteria = example.createCriteria()
                .andStateEqualTo(StateEnum.U.getValue());
        if (StringUtils.isNotBlank(tenantCode)) {
            criteria.andTenantCodeEqualTo(tenantCode);
        }
        // 排序
        example.setOrderByClause("create_time desc");

        return customFieldPoolMapper.selectDTOByExampleWithBLOBs(example);
    }
}
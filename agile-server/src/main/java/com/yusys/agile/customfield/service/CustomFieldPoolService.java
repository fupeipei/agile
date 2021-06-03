package com.yusys.agile.customfield.service;

import com.yusys.agile.customfield.dto.CustomFieldDTO;

import java.util.List;

/**
 * 自定义字段service
 *
 * @create 2021/2/1
 */
public interface CustomFieldPoolService {

    /**
     * 添加自定义字段
     *
     * @param customFieldDTO 自定义字段dto
     * @return {@link String}
     * @author 张宇
     */
    String addCustomField(CustomFieldDTO customFieldDTO);


    /**
     * 编辑自定义字段
     *
     * @param customFieldDTO 自定义字段dto
     * @return {@link String}
     * @author 张宇
     */
    String editCustomField(CustomFieldDTO customFieldDTO);

    /**
     * 功能描述: 删除自定义字段
     *
     * @param fieldId
     * @return void
     * @date 2021/2/1
     */
    void deleteCustomField(Long fieldId) throws Exception;

    /**
     * 功能描述: 列表查询自定义字段
     *
     * @param fieldName
     * @param pageNum
     * @param pageSize
     * @param projectId
     * @return java.util.List<com.yusys.agile.customfield.dto.CustomFieldDTO>
     * @date 2021/2/1
     */
    List<CustomFieldDTO> listAllCustomFields(String fieldName, Integer pageNum, Integer pageSize, Long projectId);


    /**
     * 功能描述: 查询自定义字段
     *
     * @param fieldId
     * @return com.yusys.agile.customfield.dto.CustomFieldDTO
     * @date 2021/2/1
     */
    CustomFieldDTO getCustomField(Long fieldId);

    /**
     * 功能描述: 查询租户下自定义字池
     *
     * @param tenantCode
     * @return java.util.List<com.yusys.agile.customfield.dto.CustomFieldDTO>
     * @date 2021/2/1
     */
    List<CustomFieldDTO> listAllCustomFieldsByTenantCode(String tenantCode);
}

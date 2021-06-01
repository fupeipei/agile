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
     * 功能描述: 新增自定义字段
     *
     * @param customFieldDTO
     * @return void
     * @date 2021/2/1
     */
    void addCustomField(CustomFieldDTO customFieldDTO) throws Exception;

    /**
     * 功能描述:修改自定义字段
     *
     * @param customFieldDTO
     * @return void
     * @date 2021/2/1
     */
    void editCustomField(CustomFieldDTO customFieldDTO) throws Exception;

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

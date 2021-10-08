package com.yusys.agile.customfield.dao;

import com.yusys.agile.customfield.domain.SCustomFieldPool;
import com.yusys.agile.customfield.domain.SCustomFieldPoolExample;
import com.yusys.agile.customfield.dto.CustomFieldDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SCustomFieldPoolMapper {
    long countByExample(SCustomFieldPoolExample example);

    int deleteByExample(SCustomFieldPoolExample example);

    int deleteByPrimaryKey(Long fieldId);

    int insert(SCustomFieldPool record);

    int insertSelective(SCustomFieldPool record);

    List<SCustomFieldPool> selectByExampleWithBLOBs(SCustomFieldPoolExample example);

    List<SCustomFieldPool> selectByExample(SCustomFieldPoolExample example);

    SCustomFieldPool selectByPrimaryKey(Long fieldId);

    int updateByExampleSelective(@Param("record") SCustomFieldPool record, @Param("example") SCustomFieldPoolExample example);

    int updateByExampleWithBLOBs(@Param("record") SCustomFieldPool record, @Param("example") SCustomFieldPoolExample example);

    int updateByExample(@Param("record") SCustomFieldPool record, @Param("example") SCustomFieldPoolExample example);

    int updateByPrimaryKeySelective(SCustomFieldPool record);

    int updateByPrimaryKeyWithBLOBs(SCustomFieldPool record);

    int updateByPrimaryKey(SCustomFieldPool record);
    /**
     * 条件查询
     * @author zhaofeng
     * @date 2021/6/3 17:13
     * @param example
     */
    List<CustomFieldDTO> selectDTOByExampleWithBLOBs(SCustomFieldPoolExample example);

    /**
     * 检查自定义字段可重复性
     *
     * @param fieldName 字段名
     * @param systemId  系统标识
     * @param FieldId   字段id
     * @return boolean
     * @author 张宇
     */
    boolean checkCustomFieldRepeatability(@Param("fieldId") Long FieldId, @Param("fieldName") String fieldName, @Param("systemId") Long systemId);


    /**
     * 编辑自定义字段
     *
     * @param fieldName 字段名
     * @param comment   评论
     * @author 张宇
     */
    void editCustomField(@Param("fieldId") Long FieldId, @Param("fieldName") String fieldName, @Param("comment") String comment);

    /**
     * 修改数据有效状态
     * @author zhaofeng
     * @date 2021/6/3 17:13
     * @param fieldId 主键
     * @param state   状态值
     */
    void updateStateById(@Param("fieldId") Long fieldId,@Param("state") String state);
    /**
     * 按工作项类型，查询未应用的自定义的字段
     * @author zhaofeng
     * @date 2021/6/10 15:32
     * @param issueType
     * @param fieldName
     */
    List<CustomFieldDTO> getUnAppByIssueType(@Param("issueType") Byte issueType, @Param("fieldName") String fieldName, @Param("systemId") Long systemId,@Param("fieldTypes") List<Integer> fieldTypes);
}
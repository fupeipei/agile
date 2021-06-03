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
     * 功能描述: 分页查询
     *
     * @param example
     * @return java.util.List<com.yusys.agile.customfield.dto.CustomFieldDTO>
     * @date 2021/2/31
     */
    List<CustomFieldDTO> selectDTOByExampleWithBLOBs(SCustomFieldPoolExample example);
}
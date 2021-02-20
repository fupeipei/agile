package com.yusys.agile.customfield.dao;

import com.yusys.agile.customfield.domain.CustomFieldPool;
import com.yusys.agile.customfield.domain.CustomFieldPoolExample;
import java.util.List;

import com.yusys.agile.customfield.dto.CustomFieldDTO;
import org.apache.ibatis.annotations.Param;

public interface CustomFieldPoolMapper {
    long countByExample(CustomFieldPoolExample example);

    int deleteByExample(CustomFieldPoolExample example);

    int deleteByPrimaryKey(Long fieldId);

    int insert(CustomFieldPool record);

    int insertSelective(CustomFieldPool record);

    List<CustomFieldPool> selectByExampleWithBLOBs(CustomFieldPoolExample example);

    List<CustomFieldPool> selectByExample(CustomFieldPoolExample example);

    CustomFieldPool selectByPrimaryKey(Long fieldId);

    int updateByExampleSelective(@Param("record") CustomFieldPool record, @Param("example") CustomFieldPoolExample example);

    int updateByExampleWithBLOBs(@Param("record") CustomFieldPool record, @Param("example") CustomFieldPoolExample example);

    int updateByExample(@Param("record") CustomFieldPool record, @Param("example") CustomFieldPoolExample example);

    int updateByPrimaryKeySelective(CustomFieldPool record);

    int updateByPrimaryKeyWithBLOBs(CustomFieldPool record);

    int updateByPrimaryKey(CustomFieldPool record);

    /**
     * 功能描述: 分页查询

     * @date 2021/2/31
     * @param example
     * @return java.util.List<com.yusys.agile.customfield.dto.CustomFieldDTO>
     */
    List<CustomFieldDTO> selectDTOByExampleWithBLOBs(CustomFieldPoolExample example);
}
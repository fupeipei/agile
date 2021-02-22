package com.yusys.agile.headerfield.dao;

import com.yusys.agile.headerfield.domain.HeaderField;
import com.yusys.agile.headerfield.domain.HeaderFieldExample;

import java.util.List;

import com.yusys.agile.headerfield.dto.HeaderFieldDTO;
import org.apache.ibatis.annotations.Param;

public interface HeaderFieldMapper {
    long countByExample(HeaderFieldExample example);

    int deleteByExample(HeaderFieldExample example);

    int deleteByPrimaryKey(Long fieldId);

    int insert(HeaderField record);

    int insertSelective(HeaderField record);

    List<HeaderField> selectByExampleWithBLOBs(HeaderFieldExample example);

    List<HeaderField> selectByExample(HeaderFieldExample example);

    HeaderField selectByPrimaryKey(Long fieldId);

    int updateByExampleSelective(@Param("record") HeaderField record, @Param("example") HeaderFieldExample example);

    int updateByExampleWithBLOBs(@Param("record") HeaderField record, @Param("example") HeaderFieldExample example);

    int updateByExample(@Param("record") HeaderField record, @Param("example") HeaderFieldExample example);

    int updateByPrimaryKeySelective(HeaderField record);

    int updateByPrimaryKeyWithBLOBs(HeaderField record);

    int updateByPrimaryKey(HeaderField record);

    /**
     * 功能描述: 分页查询自定义字段
     *
     * @param example
     * @return java.util.List<com.yusys.agile.headerfield.dto.HeaderFieldDTO>
     * @date 2021/2/16
     */
    List<HeaderFieldDTO> selectDTOByExampleWithBLOBs(HeaderFieldExample example);
}
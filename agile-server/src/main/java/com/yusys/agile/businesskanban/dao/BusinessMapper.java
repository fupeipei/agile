package com.yusys.agile.businesskanban.dao;

import com.yusys.agile.businesskanban.domain.Business;
import com.yusys.agile.businesskanban.domain.BusinessExample;
import com.yusys.agile.businesskanban.domain.BusinessWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusinessMapper {
    long countByExample(BusinessExample example);

    int deleteByExample(BusinessExample example);

    int deleteByPrimaryKey(Long businessId);

    int insert(BusinessWithBLOBs record);

    int insertSelective(BusinessWithBLOBs record);

    List<BusinessWithBLOBs> selectByExampleWithBLOBs(BusinessExample example);

    List<Business> selectByExample(BusinessExample example);

    BusinessWithBLOBs selectByPrimaryKey(Long businessId);

    int updateByExampleSelective(@Param("record") BusinessWithBLOBs record, @Param("example") BusinessExample example);

    int updateByExampleWithBLOBs(@Param("record") BusinessWithBLOBs record, @Param("example") BusinessExample example);

    int updateByExample(@Param("record") Business record, @Param("example") BusinessExample example);

    int updateByPrimaryKeySelective(BusinessWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BusinessWithBLOBs record);

    int updateByPrimaryKey(Business record);
}
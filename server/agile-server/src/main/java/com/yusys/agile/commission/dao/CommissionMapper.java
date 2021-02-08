package com.yusys.agile.commission.dao;

import com.yusys.agile.commission.domain.Commission;
import com.yusys.agile.commission.domain.CommissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommissionMapper {
    long countByExample(CommissionExample example);

    int deleteByExample(CommissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Commission record);

    int insertSelective(Commission record);

    List<Commission> selectByExample(CommissionExample example);

    Commission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Commission record, @Param("example") CommissionExample example);

    int updateByExample(@Param("record") Commission record, @Param("example") CommissionExample example);

    int updateByPrimaryKeySelective(Commission record);

    int updateByPrimaryKey(Commission record);

    int updateByIssueIdSelective(Commission record);

    int updateCommissionByIssueId(Commission record);

    int updateCommissionByPrimaryKey(Commission record);

    List<Commission> selectCommissionList(Commission record);
}
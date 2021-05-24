package com.yusys.agile.commission.dao;

import com.yusys.agile.commission.domain.SCommission;
import com.yusys.agile.commission.domain.SCommissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommissionMapper {
    long countByExample(SCommissionExample example);

    int deleteByExample(SCommissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SCommission record);

    int insertSelective(SCommission record);

    List<SCommission> selectByExample(SCommissionExample example);

    SCommission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SCommission record, @Param("example") SCommissionExample example);

    int updateByExample(@Param("record") SCommission record, @Param("example") SCommissionExample example);

    int updateByPrimaryKeySelective(SCommission record);

    int updateByPrimaryKey(SCommission record);

    int updateByIssueIdSelective(SCommission record);

    int updateCommissionByIssueId(SCommission record);

    int updateCommissionByPrimaryKey(SCommission record);

    List<SCommission> selectCommissionList(SCommission record);
}
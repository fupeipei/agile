package com.yusys.agile.commission.dao;

import com.yusys.agile.commission.domain.SCommissionRecord;
import com.yusys.agile.commission.domain.SCommissionRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommissionRecordMapper {
    long countByExample(SCommissionRecordExample example);

    int deleteByExample(SCommissionRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SCommissionRecord record);

    int insertSelective(SCommissionRecord record);

    List<SCommissionRecord> selectByExample(SCommissionRecordExample example);

    SCommissionRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SCommissionRecord record, @Param("example") SCommissionRecordExample example);

    int updateByExample(@Param("record") SCommissionRecord record, @Param("example") SCommissionRecordExample example);

    int updateByPrimaryKeySelective(SCommissionRecord record);

    int updateByPrimaryKey(SCommissionRecord record);
}
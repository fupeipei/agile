package com.yusys.agile.commission.dao;

import com.yusys.agile.commission.domain.CommissionRecord;
import com.yusys.agile.commission.domain.CommissionRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommissionRecordMapper {
    long countByExample(CommissionRecordExample example);

    int deleteByExample(CommissionRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommissionRecord record);

    int insertSelective(CommissionRecord record);

    List<CommissionRecord> selectByExample(CommissionRecordExample example);

    CommissionRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommissionRecord record, @Param("example") CommissionRecordExample example);

    int updateByExample(@Param("record") CommissionRecord record, @Param("example") CommissionRecordExample example);

    int updateByPrimaryKeySelective(CommissionRecord record);

    int updateByPrimaryKey(CommissionRecord record);
}
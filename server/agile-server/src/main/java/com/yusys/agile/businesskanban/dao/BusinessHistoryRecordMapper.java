package com.yusys.agile.businesskanban.dao;

import com.yusys.agile.businesskanban.domain.BusinessHistoryRecord;
import com.yusys.agile.businesskanban.domain.BusinessHistoryRecordExample;
import com.yusys.agile.businesskanban.domain.BusinessHistoryRecordWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusinessHistoryRecordMapper {
    long countByExample(BusinessHistoryRecordExample example);

    int deleteByExample(BusinessHistoryRecordExample example);

    int deleteByPrimaryKey(Long recordId);

    int insert(BusinessHistoryRecordWithBLOBs record);

    int insertSelective(BusinessHistoryRecordWithBLOBs record);

    List<BusinessHistoryRecordWithBLOBs> selectByExampleWithBLOBs(BusinessHistoryRecordExample example);

    List<BusinessHistoryRecord> selectByExample(BusinessHistoryRecordExample example);

    BusinessHistoryRecordWithBLOBs selectByPrimaryKey(Long recordId);

    int updateByExampleSelective(@Param("record") BusinessHistoryRecordWithBLOBs record, @Param("example") BusinessHistoryRecordExample example);

    int updateByExampleWithBLOBs(@Param("record") BusinessHistoryRecordWithBLOBs record, @Param("example") BusinessHistoryRecordExample example);

    int updateByExample(@Param("record") BusinessHistoryRecord record, @Param("example") BusinessHistoryRecordExample example);

    int updateByPrimaryKeySelective(BusinessHistoryRecordWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BusinessHistoryRecordWithBLOBs record);

    int updateByPrimaryKey(BusinessHistoryRecord record);

    /**
     *   : zhaoyd6
     * @Date: 2020/5/7
     * @Description: 批量创建历史记录
     * @Param: [records]
     * @Return: int
     *
     */
    void batchCreate(@Param("collection") List<BusinessHistoryRecordWithBLOBs> records);

}
package com.yusys.agile.actionlog.dao;

import com.yusys.agile.actionlog.domain.SActionLog;
import com.yusys.agile.actionlog.domain.SActionLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SActionLogMapper {
    /**
    * countByExample
    * @param example example
    * @return long long
    */
    long countByExample(SActionLogExample example);

    /**
    * deleteByExample
    * @param example example
    * @return int int
    */
    int deleteByExample(SActionLogExample example);

    /**
    * deleteByPrimaryKey
    * @param actionLogId actionLogId
    * @return int int
    */
    int deleteByPrimaryKey(Long actionLogId);

    /**
    * insert
    * @param record record
    * @return int int
    */
    int insert(SActionLog record);

    /**
    * insertSelective
    * @param record record
    * @return int int
    */
    int insertSelective(SActionLog record);

    /**
    * selectByExample
    * @param example example
    * @return List<SActionLog> List<SActionLog>
    */
    List<SActionLog> selectByExample(SActionLogExample example);

    /**
    * selectByPrimaryKey
    * @param actionLogId actionLogId
    * @return SActionLog SActionLog
    */
    SActionLog selectByPrimaryKey(Long actionLogId);

    /**
    * updateByExampleSelective
    * @param record record
    * @param example example
    * @return int int
    */
    int updateByExampleSelective(@Param("record") SActionLog record, @Param("example") SActionLogExample example);

    /**
    * updateByExample
    * @param record record
    * @param example example
    * @return int int
    */
    int updateByExample(@Param("record") SActionLog record, @Param("example") SActionLogExample example);

    /**
    * updateByPrimaryKeySelective
    * @param record record
    * @return int int
    */
    int updateByPrimaryKeySelective(SActionLog record);

    /**
    * updateByPrimaryKey
    * @param record record
    * @return int int
    */
    int updateByPrimaryKey(SActionLog record);

}
/**
* @mbg.generated
* generator on Tue May 25 17:07:57 CST 2021
*/
package com.yusys.agile.actionlog.service;

import com.yusys.agile.actionlog.domain.SActionLog;
import java.util.List;

public interface SActionLogService {
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
    * selectByPrimaryKey
    * @param actionLogId actionLogId
    * @return SActionLog SActionLog
    */
    SActionLog selectByPrimaryKey(Long actionLogId);

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


    public int insertLog( String actionCode,
                          Long objId,
                          Long objType,
                          String remark,
                          String result);
}
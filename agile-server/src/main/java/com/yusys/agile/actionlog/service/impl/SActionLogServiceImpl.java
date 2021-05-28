/**
* @mbg.generated
* generator on Tue May 25 17:07:57 CST 2021
*/
package com.yusys.agile.actionlog.service.impl;

import com.yusys.agile.actionlog.dao.SActionLogMapper;
import com.yusys.agile.actionlog.domain.SActionLog;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SActionLogServiceImpl implements com.yusys.agile.actionlog.service.SActionLogService {
    private final SActionLogMapper sActionLogMapper;

    public SActionLogServiceImpl(SActionLogMapper sActionLogMapper) {
        this.sActionLogMapper=sActionLogMapper;
    }

    /**
    * deleteByPrimaryKey
    * @param actionLogId actionLogId
    * @return int int
    */
    @Override
    public int deleteByPrimaryKey(Long actionLogId) {
        return sActionLogMapper.deleteByPrimaryKey(actionLogId);
    }

    /**
    * insert
    * @param record record
    * @return int int
    */
    @Override
    public int insert(SActionLog record) {
        return sActionLogMapper.insert(record);
    }

    /**
    * insertSelective
    * @param record record
    * @return int int
    */
    @Override
    public int insertSelective(SActionLog record) {
        return sActionLogMapper.insertSelective(record);
    }

    /**
    * selectByPrimaryKey
    * @param actionLogId actionLogId
    * @return SActionLog SActionLog
    */
    @Override
    public SActionLog selectByPrimaryKey(Long actionLogId) {
        return sActionLogMapper.selectByPrimaryKey(actionLogId);
    }

    /**
    * updateByPrimaryKeySelective
    * @param record record
    * @return int int
    */
    @Override
    public int updateByPrimaryKeySelective(SActionLog record) {
        return sActionLogMapper.updateByPrimaryKeySelective(record);
    }

    /**
    * updateByPrimaryKey
    * @param record record
    * @return int int
    */
    @Override
    public int updateByPrimaryKey(SActionLog record) {
        return sActionLogMapper.updateByPrimaryKey(record);
    }


    /**
     *
     * @param actionCode
     * @param objId
     * @param objType
     * @param remark
     * @param result
     * @return
     */
    @Override
    public int insertLog( String actionCode,
                          Long objId,
                          Long objType,
                          String remark,
                          String result){
        int insert=0;
        try{
            SActionLog build = SActionLog.builder()
                    .actionCode(actionCode)
                    .objId(objId)
                    .objType(objType)
                    .remark(remark)
                    .result(result).build();
             insert = this.insert(build);
        }catch (Exception e){
            log.error("操作日志报错_insertLog",e);
        }
        return insert;
    }
}
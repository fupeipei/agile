package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.domain.ReasonClassifyValues;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReasonClassifyValuesDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ReasonClassifyValues record);

    int insertSelective(ReasonClassifyValues record);

    ReasonClassifyValues selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReasonClassifyValues record);

    int updateByPrimaryKey(ReasonClassifyValues record);

    /**
     * Customer extend api
     *
     * @date 2021/2/5
     */
    List<ReasonClassifyValues> selectReasonClassifyValuesByClassifyId(@Param("classifyId") Integer classifyId);

    String selectChangeReasonByReasonId(@Param("changeReasonId") Integer changeReasonId);
}
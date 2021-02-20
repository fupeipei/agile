package com.yusys.agile.versionmanager.dao;


import com.yusys.agile.versionmanager.domain.ReasonClassify;

import java.util.List;

public interface ReasonClassifyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ReasonClassify record);

    int insertSelective(ReasonClassify record);

    ReasonClassify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReasonClassify record);

    int updateByPrimaryKey(ReasonClassify record);

    /**
     *  custom method start
     *
     * @date  2021/2/5
     */
    List<ReasonClassify> selectAllReasonClassify();
}
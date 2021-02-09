package com.yusys.agile.user.dao;

import com.yusys.agile.user.domain.ReqUserRlat;
import com.yusys.agile.user.domain.ReqUserRlatExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ReqUserRlatMapper {
    long countByExample(ReqUserRlatExample example);

    int deleteByExample(ReqUserRlatExample example);

    int deleteByPrimaryKey(Long relateId);

    int insert(ReqUserRlat record);

    int insertSelective(ReqUserRlat record);

    List<ReqUserRlat> selectByExample(ReqUserRlatExample example);

    ReqUserRlat selectByPrimaryKey(Long relateId);

    int updateByExampleSelective(@Param("record") ReqUserRlat record, @Param("example") ReqUserRlatExample example);

    int updateByExample(@Param("record") ReqUserRlat record, @Param("example") ReqUserRlatExample example);

    int updateByPrimaryKeySelective(ReqUserRlat record);

    int updateByPrimaryKey(ReqUserRlat record);

    /**
     * 功能描述: 批量插入用户关系
     *
     * @param reqUserRlats
     * @return void
     * @date 2020/8/12
     */
    void insertBatch(@Param("collection") List<ReqUserRlat> reqUserRlats);
}
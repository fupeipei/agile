package com.yusys.agile.zentao.dao;

import com.yusys.agile.zentao.domain.ZtProjectproduct;
import com.yusys.agile.zentao.domain.ZtProjectproductExample;
import com.yusys.agile.zentao.domain.ZtProjectproductKey;
import java.util.List;

import com.yusys.portal.common.register.TargetDs;
import org.apache.ibatis.annotations.Param;

public interface ZtProjectproductMapper {
    long countByExample(ZtProjectproductExample example);
    @TargetDs("zentao")
    int deleteByExample(ZtProjectproductExample example);
    @TargetDs("zentao")
    int deleteByPrimaryKey(ZtProjectproductKey key);
    @TargetDs("zentao")
    int insert(ZtProjectproduct record);
    @TargetDs("zentao")
    int insertSelective(ZtProjectproduct record);
    @TargetDs("zentao")
    List<ZtProjectproduct> selectByExample(ZtProjectproductExample example);
    @TargetDs("zentao")
    ZtProjectproduct selectByPrimaryKey(ZtProjectproductKey key);
    @TargetDs("zentao")
    int updateByExampleSelective(@Param("record") ZtProjectproduct record, @Param("example") ZtProjectproductExample example);

    int updateByExample(@Param("record") ZtProjectproduct record, @Param("example") ZtProjectproductExample example);

    int updateByPrimaryKeySelective(ZtProjectproduct record);

    int updateByPrimaryKey(ZtProjectproduct record);
}
package com.yusys.agile.zentao.dao;

import com.yusys.agile.zentao.domain.ZtProduct;
import com.yusys.agile.zentao.domain.ZtProductExample;
import com.yusys.agile.zentao.domain.ZtProductWithBLOBs;
import java.util.List;

import com.yusys.portal.common.register.TargetDs;
import org.apache.ibatis.annotations.Param;

public interface ZtProductMapper {
    @TargetDs("zentao")
    long countByExample(ZtProductExample example);
    @TargetDs("zentao")
    int deleteByExample(ZtProductExample example);
    @TargetDs("zentao")
    int deleteByPrimaryKey(Integer id);
    @TargetDs("zentao")
    int insert(ZtProductWithBLOBs record);
    @TargetDs("zentao")
    int insertSelective(ZtProductWithBLOBs record);
    @TargetDs("zentao")
    List<ZtProductWithBLOBs> selectByExampleWithBLOBs(ZtProductExample example);
    @TargetDs("zentao")
    List<ZtProduct> selectByExample(ZtProductExample example);
    @TargetDs("zentao")
    ZtProductWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZtProductWithBLOBs record, @Param("example") ZtProductExample example);

    int updateByExampleWithBLOBs(@Param("record") ZtProductWithBLOBs record, @Param("example") ZtProductExample example);

    int updateByExample(@Param("record") ZtProduct record, @Param("example") ZtProductExample example);

    int updateByPrimaryKeySelective(ZtProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ZtProductWithBLOBs record);

    int updateByPrimaryKey(ZtProduct record);
}
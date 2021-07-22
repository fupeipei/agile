package com.yusys.agile.zentao.dao;

import com.yusys.agile.zentao.domain.ZtProjectproduct;
import com.yusys.agile.zentao.domain.ZtProjectproductExample;
import com.yusys.agile.zentao.domain.ZtProjectproductKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZtProjectproductMapper {
    long countByExample(ZtProjectproductExample example);

    int deleteByExample(ZtProjectproductExample example);

    int deleteByPrimaryKey(ZtProjectproductKey key);

    int insert(ZtProjectproduct record);

    int insertSelective(ZtProjectproduct record);

    List<ZtProjectproduct> selectByExample(ZtProjectproductExample example);

    ZtProjectproduct selectByPrimaryKey(ZtProjectproductKey key);

    int updateByExampleSelective(@Param("record") ZtProjectproduct record, @Param("example") ZtProjectproductExample example);

    int updateByExample(@Param("record") ZtProjectproduct record, @Param("example") ZtProjectproductExample example);

    int updateByPrimaryKeySelective(ZtProjectproduct record);

    int updateByPrimaryKey(ZtProjectproduct record);
}
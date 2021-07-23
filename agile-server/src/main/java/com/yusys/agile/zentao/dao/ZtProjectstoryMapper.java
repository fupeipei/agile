package com.yusys.agile.zentao.dao;

import com.yusys.agile.zentao.domain.ZtProjectstory;
import com.yusys.agile.zentao.domain.ZtProjectstoryExample;
import java.util.List;

import com.yusys.portal.common.register.TargetDs;
import org.apache.ibatis.annotations.Param;

public interface ZtProjectstoryMapper {
    long countByExample(ZtProjectstoryExample example);

    int deleteByExample(ZtProjectstoryExample example);
    @TargetDs("zentao")
    int insert(ZtProjectstory record);
    @TargetDs("zentao")
    int insertSelective(ZtProjectstory record);
    @TargetDs("zentao")
    List<ZtProjectstory> selectByExample(ZtProjectstoryExample example);
    @TargetDs("zentao")
    int updateByExampleSelective(@Param("record") ZtProjectstory record, @Param("example") ZtProjectstoryExample example);
    @TargetDs("zentao")
    int updateByExample(@Param("record") ZtProjectstory record, @Param("example") ZtProjectstoryExample example);
}
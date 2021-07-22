package com.yusys.agile.zentao.dao;

import com.yusys.agile.zentao.domain.ZtProjectstory;
import com.yusys.agile.zentao.domain.ZtProjectstoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZtProjectstoryMapper {
    long countByExample(ZtProjectstoryExample example);

    int deleteByExample(ZtProjectstoryExample example);

    int insert(ZtProjectstory record);

    int insertSelective(ZtProjectstory record);

    List<ZtProjectstory> selectByExample(ZtProjectstoryExample example);

    int updateByExampleSelective(@Param("record") ZtProjectstory record, @Param("example") ZtProjectstoryExample example);

    int updateByExample(@Param("record") ZtProjectstory record, @Param("example") ZtProjectstoryExample example);
}
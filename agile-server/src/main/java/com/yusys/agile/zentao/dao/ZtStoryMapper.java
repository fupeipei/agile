package com.yusys.agile.zentao.dao;

import com.yusys.agile.zentao.domain.ZtStory;
import com.yusys.agile.zentao.domain.ZtStoryExample;
import com.yusys.agile.zentao.domain.ZtStoryWithBLOBs;
import java.util.List;

import com.yusys.portal.common.register.TargetDs;
import org.apache.ibatis.annotations.Param;


public interface ZtStoryMapper {
    long countByExample(ZtStoryExample example);

    int deleteByExample(ZtStoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ZtStoryWithBLOBs record);
    @TargetDs("zentao")
    int insertSelective(ZtStoryWithBLOBs record);
    @TargetDs("zentao")
    List<ZtStoryWithBLOBs> selectByExampleWithBLOBs(ZtStoryExample example);
    @TargetDs("zentao")
    List<ZtStory> selectByExample(ZtStoryExample example);

    ZtStoryWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZtStoryWithBLOBs record, @Param("example") ZtStoryExample example);

    int updateByExampleWithBLOBs(@Param("record") ZtStoryWithBLOBs record, @Param("example") ZtStoryExample example);

    int updateByExample(@Param("record") ZtStory record, @Param("example") ZtStoryExample example);

    int updateByPrimaryKeySelective(ZtStoryWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ZtStoryWithBLOBs record);

    int updateByPrimaryKey(ZtStory record);
}
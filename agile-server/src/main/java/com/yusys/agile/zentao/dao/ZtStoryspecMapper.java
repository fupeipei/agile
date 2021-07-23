package com.yusys.agile.zentao.dao;

import com.yusys.agile.zentao.domain.ZtStoryspec;
import com.yusys.agile.zentao.domain.ZtStoryspecExample;
import com.yusys.agile.zentao.domain.ZtStoryspecWithBLOBs;
import java.util.List;

import com.yusys.portal.common.register.TargetDs;
import org.apache.ibatis.annotations.Param;

public interface ZtStoryspecMapper {
    long countByExample(ZtStoryspecExample example);

    int deleteByExample(ZtStoryspecExample example);
    @TargetDs("zentao")
    int insert(ZtStoryspecWithBLOBs record);
    @TargetDs("zentao")
    int insertSelective(ZtStoryspecWithBLOBs record);
    @TargetDs("zentao")
    List<ZtStoryspecWithBLOBs> selectByExampleWithBLOBs(ZtStoryspecExample example);
    @TargetDs("zentao")
    List<ZtStoryspec> selectByExample(ZtStoryspecExample example);

    int updateByExampleSelective(@Param("record") ZtStoryspecWithBLOBs record, @Param("example") ZtStoryspecExample example);

    int updateByExampleWithBLOBs(@Param("record") ZtStoryspecWithBLOBs record, @Param("example") ZtStoryspecExample example);

    int updateByExample(@Param("record") ZtStoryspec record, @Param("example") ZtStoryspecExample example);
}
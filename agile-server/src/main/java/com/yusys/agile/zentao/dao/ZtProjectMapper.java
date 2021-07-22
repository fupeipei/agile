package com.yusys.agile.zentao.dao;

import com.yusys.agile.zentao.domain.ZtProject;
import com.yusys.agile.zentao.domain.ZtProjectExample;
import com.yusys.agile.zentao.domain.ZtProjectWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZtProjectMapper {
    long countByExample(ZtProjectExample example);

    int deleteByExample(ZtProjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ZtProjectWithBLOBs record);

    int insertSelective(ZtProjectWithBLOBs record);

    List<ZtProjectWithBLOBs> selectByExampleWithBLOBs(ZtProjectExample example);

    List<ZtProject> selectByExample(ZtProjectExample example);

    ZtProjectWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZtProjectWithBLOBs record, @Param("example") ZtProjectExample example);

    int updateByExampleWithBLOBs(@Param("record") ZtProjectWithBLOBs record, @Param("example") ZtProjectExample example);

    int updateByExample(@Param("record") ZtProject record, @Param("example") ZtProjectExample example);

    int updateByPrimaryKeySelective(ZtProjectWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ZtProjectWithBLOBs record);

    int updateByPrimaryKey(ZtProject record);
}
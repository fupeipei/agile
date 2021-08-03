package com.yusys.agile.projectmanager.dao;

import com.yusys.agile.projectmanager.domain.SStaticProjectData;
import com.yusys.agile.projectmanager.domain.SStaticProjectDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SStaticProjectDataMapper {
    long countByExample(SStaticProjectDataExample example);

    int deleteByExample(SStaticProjectDataExample example);

    int deleteByPrimaryKey(Long staticProjectDataId);

    int insert(SStaticProjectData record);

    int insertSelective(SStaticProjectData record);

    List<SStaticProjectData> selectByExample(SStaticProjectDataExample example);

    SStaticProjectData selectByPrimaryKey(Long staticProjectDataId);

    int updateByExampleSelective(@Param("record") SStaticProjectData record, @Param("example") SStaticProjectDataExample example);

    int updateByExample(@Param("record") SStaticProjectData record, @Param("example") SStaticProjectDataExample example);

    int updateByPrimaryKeySelective(SStaticProjectData record);

    int updateByPrimaryKey(SStaticProjectData record);
}
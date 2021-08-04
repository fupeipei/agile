package com.yusys.agile.projectmanager.dao;

import com.yusys.agile.projectmanager.domain.SProjectProductLineRel;
import com.yusys.agile.projectmanager.domain.SProjectProductLineRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SProjectProductLineRelMapper {
    long countByExample(SProjectProductLineRelExample example);

    int deleteByExample(SProjectProductLineRelExample example);

    int deleteByPrimaryKey(Long projectProductLineRelId);

    int insert(SProjectProductLineRel record);

    int insertSelective(SProjectProductLineRel record);

    List<SProjectProductLineRel> selectByExample(SProjectProductLineRelExample example);

    SProjectProductLineRel selectByPrimaryKey(Long projectProductLineRelId);

    int updateByExampleSelective(@Param("record") SProjectProductLineRel record, @Param("example") SProjectProductLineRelExample example);

    int updateByExample(@Param("record") SProjectProductLineRel record, @Param("example") SProjectProductLineRelExample example);

    int updateByPrimaryKeySelective(SProjectProductLineRel record);

    int updateByPrimaryKey(SProjectProductLineRel record);

    void batchInsertProjectProductLineRel(@Param("sProjectProductLineRels") List<SProjectProductLineRel> sProjectProductLineRels);


}
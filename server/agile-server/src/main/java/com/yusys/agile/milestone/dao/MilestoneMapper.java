package com.yusys.agile.milestone.dao;

import com.yusys.agile.milestone.domain.Milestone;
import com.yusys.agile.milestone.domain.MilestoneExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MilestoneMapper {
    long countByExample(MilestoneExample example);

    int deleteByExample(MilestoneExample example);

    int deleteByPrimaryKey(Long milestoneId);

    int insert(Milestone record);

    int insertSelective(Milestone record);

    List<Milestone> selectByExample(MilestoneExample example);

    Milestone selectByPrimaryKey(Long milestoneId);

    int updateByExampleSelective(@Param("record") Milestone record, @Param("example") MilestoneExample example);

    int updateByExample(@Param("record") Milestone record, @Param("example") MilestoneExample example);

    int updateByPrimaryKeySelective(Milestone record);



    int updateByPrimaryKey(Milestone record);

    /**
     * 功能描述:修改里程碑时实际完成时间必须要带进去

     * @date   2021/2/5
     * @param record
     * @return int
     */
    int updateByPrimaryKeySelectiveWithNull(Milestone record);
}
package com.yusys.agile.sprintv3.dao;

import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintExample;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SSprintMapper {
    long countByExample(SSprintExample example);

    int deleteByExample(SSprintExample example);

    int deleteByPrimaryKey(Long sprintId);

    int insert(SSprintWithBLOBs record);

    int insertSelective(SSprintWithBLOBs record);

    List<SSprintWithBLOBs> selectByExampleWithBLOBs(SSprintExample example);

    List<SSprint> selectByExample(SSprintExample example);

    SSprintWithBLOBs selectByPrimaryKey(Long sprintId);

    int updateByExampleSelective(@Param("record") SSprintWithBLOBs record, @Param("example") SSprintExample example);

    int updateByExampleWithBLOBs(@Param("record") SSprintWithBLOBs record, @Param("example") SSprintExample example);

    int updateByExample(@Param("record") SSprint record, @Param("example") SSprintExample example);

    int updateByPrimaryKeySelective(SSprintWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SSprintWithBLOBs record);

    int updateByPrimaryKey(SSprint record);

    int CheckSprintName(@Param("sprintName") String sprintName, @Param("tenantCode") String tenantCode);

    List<Long> getUnStartIds(Date date);


    int arrangeTeam(@Param("sprintId") Long sprintId, @Param("teamId") Long teamId);


    void changeStatusTOProgressByIds(@Param("sprintIds") List<Long> sprintIds);

    /**
     * 查询租户下所有
     *
     * @param params
     * @return
     */
    List<SprintListDTO> queryAllSprint(@Param("params") HashMap<String, Object> params);

    /**
     * 取消迭代
     *
     * @param sprintId 迭代id
     * @return int
     */
    int cancelSprint(long sprintId);


    /**
     * 迭代存在
     *
     * @param sprintId 迭代id
     * @return int
     */
    int sprintExist(@Param("sprintId") long sprintId);
}
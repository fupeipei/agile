package com.yusys.agile.sprint.dao;

import com.yusys.agile.sprint.domain.Sprint;
import com.yusys.agile.sprint.domain.SprintExample;
import com.yusys.agile.sprint.domain.SprintWithBLOBs;

import java.util.Date;
import java.util.List;

import com.yusys.agile.sprint.dto.SprintDTO;
import org.apache.ibatis.annotations.Param;

public interface SprintMapper {
    long countByExample(SprintExample example);

    int deleteByExample(SprintExample example);

    int deleteByPrimaryKey(Long sprintId);

    int insert(SprintWithBLOBs record);

    int insertSelective(SprintWithBLOBs record);

    List<SprintWithBLOBs> selectByExampleWithBLOBs(SprintExample example);

    List<Sprint> selectByExample(SprintExample example);

    SprintWithBLOBs selectByPrimaryKey(Long sprintId);

    int updateByExampleSelective(@Param("record") SprintWithBLOBs record, @Param("example") SprintExample example);

    int updateByExampleWithBLOBs(@Param("record") SprintWithBLOBs record, @Param("example") SprintExample example);

    int updateByExample(@Param("record") Sprint record, @Param("example") SprintExample example);

    int updateByPrimaryKeySelective(SprintWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SprintWithBLOBs record);

    int updateByPrimaryKey(Sprint record);


    /**
     * @param example

     * @Date 2021/2/4
     * @Description 查询未关联故事或任务的迭代
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintDTO>
     */
    List<SprintDTO> selectByExampleDTO(SprintExample example);

    int arrangeTeam(@Param("sprintId") Long sprintId, @Param("teamId") Long teamId);

    int countInvoved4Team(Long teamId);

    List<String> getVersionNumber();

    List<Long> getSprintIdListByTeamId(Long teamId);

    Sprint getOneSprintByTeamId(Long teamId);

    Integer getPlanWorkload(Long sprintId);

    /**
     *
     * @Date: 18:43
     * @Description: 获取剩余工作量
     * @Param: * @param sprintId
     * @Return: int
     */
    int getRemainWorkload(Long sprintId);

    /**
     *
     * @Date: 18:43
     * @Description: 获取项目下的所有迭代列表
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.sprint.domain.SprintWithBLOBs>
     */
    List<SprintWithBLOBs> getByProjectId(Long projectId);

    /**
     * @param now

     * @Date 2021/2/18
     * @Description 根据当前时间获取迭代id
     * @Return java.util.List<java.lang.Long>
     */
    List<Long> getUnStartIds(Date now);

    /**
     * @param sprintIds

     * @Date 2021/2/18
     * @Description 根据迭代id将迭代未开始状态改为进行中
     * @Return int
     */
    int changeStatusTOProgressByIds(@Param("sprintIds") List<Long> sprintIds);

    /**
     * @param sprintId

     * @Date 2021/2/21
     * @Description 获取迭代信息去掉文本
     * @Return com.yusys.agile.sprint.domain.SprintWithBLOBs
     */
    SprintWithBLOBs selectByPrimaryKeyNotText(Long sprintId);

    /**
     * @param sprintId

     * @Date 2021/2/23
     * @Description 编辑迭代为已完成状态
     * @Return int
     */
    int completeById(Long sprintId);
}
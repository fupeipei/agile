package com.yusys.agile.sprintv3.dao;

import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintExample;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.yusys.agile.sprintv3.queryModel.UserWorkloadQueryModel;
import com.yusys.agile.sprintv3.responseModel.SprintMembersWorkHours;
import com.yusys.agile.sprintv3.responseModel.SprintOverView;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.portal.model.facade.entity.SsoUser;
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

    List<SprintListDTO> queryOtherSprint(@Param("params") HashMap<String, Object> params);

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

    /**
     * 迭代创建人
     *
     * @param sprintId 迭代id
     * @param userId   用户id
     * @return int
     */
    boolean creatUser(@Param("sprintId") long sprintId, @Param("userId") long userId);

    /**
     * 迭代未完成的故事
     * 迭代完成
     *
     * @param sprintId 迭代id
     * @return int
     */
    int querySprintUnfinishedStoryNumber(long sprintId);

    /**
     * 查询迭代故事
     *
     * @param sprintId 迭代id
     * @return int
     */
    int querySprintStoryNumBer(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType);

    /**
     * 迭代完成
     *
     * @param sprintId 迭代id
     */
    void sprintFinish(long sprintId);

    /**
     * 查询迭代状态
     *
     * @param sprintId 迭代id
     * @return int
     */
    Byte querySprintStatus(long sprintId);

    /**
     * 检查迭代Po
     *
     * @return boolean
     */
    boolean checkSprintPo(@Param("sprintId") long sprintId, @Param("userId") long userId);

    /**
     * 查询有效的迭代通过id
     *
     * @param sprintId 迭代id
     * @return {@link SSprintWithBLOBs}
     */
    SSprintWithBLOBs queryValidSprintById(long sprintId);

    /**
     * 获取迭代信息去掉文本
     *
     * @param sprintId
     * @return
     */
    SSprintWithBLOBs selectByPrimaryKeyNotText(Long sprintId);

    /**
     * 团队进入迭代时，按照teamId和sprint名称查询
     *
     * @param teamId
     * @param sprint
     * @author zhaofeng
     * @date 2021/5/24 17:20
     */
    List<SprintListDTO> selectByIdAndName(@Param("teamId") Long teamId, @Param("sprint") String sprint);

    /**
     * 查询迭代完成的故事点
     *
     * @param sprintId 迭代id
     * @return int
     */
    int querySprintFinishedStoryPoint(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询迭代故事点
     *
     * @param sprintId 迭代id
     * @return int
     */
    int querySprintStoryPoint(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType);


    /**
     * 查询迭代工作负载数量
     *
     * @param sprintId 迭代id
     * @return int
     */
    int querySprintWorkload(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType);

    /**
     * 查询迭代已完成的任务数量
     *
     * @param sprintId 迭代id
     * @return int
     */
    int querySprintFinishedTaskNumber(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询迭代任务数量
     *
     * @param sprintId 迭代id
     * @return int
     */
    int querySprintTaskNumber(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType);

    /**
     * 根据系统id查询进行中、未开始的迭代信息
     *
     * @param systemId
     * @return
     */
    List<SprintListDTO> selectBySystemId(@Param("systemId") Long systemId);

    /**
     * @param sprintId 迭代id
     * @return {@link List<Long>}
     */
    List<Long> querySprintUserIds(long sprintId);


    /**
     * 查询迭代已完成的故事数量
     *
     * @param sprintId  迭代id
     * @param IssueType 问题类型
     * @param Status    状态
     * @return int
     */
    int querySprintFinishedStoryNumber(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询迭代已完成的工作负载
     *
     * @param sprintId  迭代id
     * @param IssueType 问题类型
     * @param Status    状态
     * @return int
     */
    int querySprintFinishedWorkload(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);


    /**
     * 无人认领的工作时间
     *
     * @param sprintId  迭代id
     * @param IssueType 问题类型
     * @param Status    状态
     * @return int
     */
    int unclaimedWorkHours(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);


    /**
     * 查询用户实际工作负载
     *
     * @param sprintId 迭代id
     * @param userId   用户id
     * @return int
     */
    Integer queryUserActualWorkload(@Param("sprintId") long sprintId, @Param("userId") Long userId);


    /**
     * 查询用户剩余工作量
     *
     * @param sprintId  迭代id
     * @param userId    用户id
     * @param IssueType 问题类型
     * @param Status    状态
     * @return int
     */
    int queryUserResidueWorkload(@Param("sprintId") long sprintId, @Param("userId") Long userId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询用户的任务数量
     *
     * @param sprintId 迭代id
     * @param userId   用户id
     * @return int
     */
    int queryUserTaskNumber(@Param("sprintId") long sprintId, @Param("userId") Long userId);
}
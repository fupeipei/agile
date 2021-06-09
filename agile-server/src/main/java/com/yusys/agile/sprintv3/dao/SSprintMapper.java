package com.yusys.agile.sprintv3.dao;

import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.StoryStatusEnum;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintExample;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    int CheckSprintNameExistInTeam(@Param("sprintName") String sprintName, @Param("teamId") long teamId);

    List<Long> getUnStartIds(Date date);

    int arrangeTeam(@Param("sprintId") Long sprintId, @Param("teamId") Long teamId);

    void changeStatusTOProgressByIds(@Param("sprintIds") List<Long> sprintIds);

    /**
     * 条件分页查询租户下所有迭代
     *
     * @param params
     * @author zhaofeng
     * @date 2021/6/2 11:33
     */
    List<SprintListDTO> queryAllSprint(@Param("params") HashMap<String, Object> params);

    /**
     * 条件分页查询租户下与我相关的迭代
     *
     * @param params
     * @author zhaofeng
     * @date 2021/6/2 11:33
     */
    List<SprintListDTO> queryOtherSprint(@Param("params") HashMap<String, Object> params);

    /**
     * 取消迭代
     *
     * @param sprintId 迭代id
     * @return int
     * @author 张宇
     */
    int cancelSprint(long sprintId);

    /**
     * 迭代存在
     *
     * @param sprintId 迭代id
     * @return int
     * @author 张宇
     */
    int sprintExist(@Param("sprintId") long sprintId);


    /**
     * 迭代未完成的故事
     * 迭代完成
     *
     * @param sprintId 迭代id
     * @return int
     * @author 张宇
     */
    boolean querySprintExistUnfinishedStory(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询迭代故事
     *
     * @param sprintId 迭代id
     * @return int
     * @author 张宇
     */
    int querySprintStoryNumBer(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType);

    /**
     * 迭代完成
     *
     * @param sprintId 迭代id
     * @author 张宇
     */
    void sprintFinish(long sprintId);

    /**
     * 查询迭代状态
     *
     * @param sprintId 迭代id
     * @return int
     * @author 张宇
     */
    Byte querySprintStatus(long sprintId);


    /**
     * 查询有效的迭代通过id
     *
     * @param sprintId 迭代id
     * @return {@link SSprintWithBLOBs}
     * @author 张宇
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
     * @author 张宇
     */
    int querySprintFinishedStoryPoint(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询迭代故事点
     *
     * @param sprintId 迭代id
     * @return int
     * @author 张宇
     */
    int querySprintStoryPoint(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType);

    /**
     * 查询迭代工作负载数量
     *
     * @param sprintId 迭代id
     * @return int
     * @author 张宇
     */
    int querySprintWorkload(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType);

    /**
     * 查询迭代已完成的任务数量
     *
     * @param sprintId 迭代id
     * @return int
     * @author 张宇
     */
    int querySprintFinishedTaskNumber(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询迭代任务数量
     *
     * @param sprintId 迭代id
     * @return int
     * @author 张宇
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
     * @author 张宇
     */
    int querySprintFinishedStoryNumber(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询迭代已完成的工作负载
     *
     * @param sprintId  迭代id
     * @param IssueType 问题类型
     * @param Status    状态
     * @return int
     * @author 张宇
     */
    int querySprintFinishedWorkload(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 无人认领的工作时间
     *
     * @param sprintId  迭代id
     * @param IssueType 问题类型
     * @param Status    状态
     * @return int
     * @author 张宇
     */
    int unclaimedWorkHours(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 无人认领的任务数量
     *
     * @param sprintId  迭代id
     * @param IssueType 问题类型
     * @param Status    状态
     * @return int
     * @author 张宇
     */
    int unclaimedTaskNumber(@Param("sprintId") long sprintId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询用户实际工作负载
     * 实际工作量计算方法：进行中（预计工时-剩余工时）+已完成（实际工时）任务卡片总和
     *
     * @param sprintId 迭代id
     * @param userId   用户id
     * @return int
     * @author 张宇
     */
    Integer queryUserActualWorkload(@Param("sprintId") long sprintId, @Param("userId") Long userId, @Param("issueType") Byte IssueType);

    /**
     * 查询用户剩余工作量
     * 剩余工作量计算方法：进行中（剩余工作量）任务卡片总和
     *
     * @param sprintId  迭代id
     * @param userId    用户id
     * @param IssueType 问题类型
     * @param Status    状态
     * @return int
     * @author 张宇
     */
    int queryUserResidueWorkload(@Param("sprintId") long sprintId, @Param("userId") Long userId, @Param("issueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 查询用户的任务数量
     *
     * @param sprintId 迭代id
     * @param userId   用户id
     * @return int
     * @author 张宇
     */
    int queryUserTaskNumber(@Param("sprintId") long sprintId, @Param("userId") Long userId, @Param("issueType") Byte IssueType);


    /**
     * 改变问题状态通过迭代id
     * story级别: 一阶段状态,二阶段状态置初值, 领取人,系统id置空
     * task级别:二阶段状态置107未领取, 领取人,真实,系统id工作时间置空
     *
     * @param sprintId  迭代id
     * @param IssueType 问题类型
     * @param Status    状态
     * @return int
     * @author 张宇
     */
    int changeIssueStatusBySprintId(@Param("sprintId") long sprintId, @Param("IssueType") Byte IssueType, @Param("status") Long Status);

    /**
     * 身份校验(是否为Po,Sm,SprintCreatUser)
     *
     * @param sprintId 迭代id
     * @param userId   用户id
     * @return boolean
     * @author 张宇
     */
    boolean checkIdentityInPoSmOrCreatUser(@Param("sprintId") long sprintId, @Param("userId") long userId);

    /**
     * 迭代是否已经绑定问题
     *
     * @param sprintId 迭代id
     * @return boolean
     * @author 张宇
     */
    boolean sprintBindingIssue(long sprintId);

    /**
     * 查询迭代成员id
     *
     * @param sprintId 迭代id
     * @return {@link List<Long>}
     * @author 张宇
     */
    List<Long> querySprintMembersId(long sprintId);


    /**
     * 查询迭代有相关性故事
     *
     * @param sprintId 迭代id
     * @return boolean
     * @author 张宇
     */
    boolean querySprintHasRelevanceStory(long sprintId);
}
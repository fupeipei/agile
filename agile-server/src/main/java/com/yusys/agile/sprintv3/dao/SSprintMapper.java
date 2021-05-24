package com.yusys.agile.sprintv3.dao;

import com.yusys.agile.sprint.domain.SprintWithBLOBs;
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

    /**
     * 检查迭代的名字
     *
     * @param sprintName 迭代的名字
     * @param tenantCode 租户的代码
     * @return int
     */
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
    int sprintUnfinishedStory(long sprintId);

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
     * 迭代视图 - 迭代详情
     *
     * @param sprintId 迭代id
     * @return {@link SSprintWithBLOBs}
     */
    SSprintWithBLOBs sprintOverViewSprintDetails(long sprintId);

    /**
     * 检查迭代Po
     *
     * @return boolean
     */
    boolean checkSprintPo(@Param("sprintId") long sprintId, @Param("userId") long userId);


    /**
     * 获取迭代信息去掉文本
     * @param sprintId
     * @return
     */
    SSprintWithBLOBs selectByPrimaryKeyNotText(Long sprintId);

}
package com.yusys.agile.sprintv3.service;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintV3.dto.SprintV3UserHourDTO;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.responseModel.SprintMembersWorkHours;
import com.yusys.agile.sprintv3.responseModel.SprintOverView;
import com.yusys.agile.sprintv3.responseModel.SprintStatisticalInformation;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.Date;
import java.util.List;

import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.portal.model.facade.dto.SsoSystemDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.util.code.ReflectUtil;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 14:48
 */
public interface Sprintv3Service {

    /**
     * @param sprintId
     * @Date 2021/5/11
     * @Description 查看迭代编辑页面
     * @Return com.yusys.agile.sprint.dto.SprintDTO
     */
    SprintV3DTO viewEdit(Long sprintId);

    /**
     * 条件分页-查询列表
     *
     * @param dto
     * @param security
     * @author zhaofeng
     * @date 2021/5/11 16:33
     */
    List<SprintListDTO> listSprint(SprintQueryDTO dto, SecurityDTO security);

    /**
     * 新建迭代
     *
     * @param sprintV3DTO 迭代v3dto
     * @return {@link Long}
     * @author 张宇
     */
    Long createSprint(SprintV3DTO sprintV3DTO);

    /**
     * 是否满足编辑条件
     *
     * @param sprintId 迭代id
     * @return boolean
     */
    boolean canEdit(Long sprintId);

    void updateSprint(SprintV3DTO sprintDTO, SecurityDTO securityDTO);

    /**
     * 每天改变迭代状态
     *
     * @author 张宇
     */
    void changeStatusDaily();

    /**
     * 取消迭代
     *
     * @param sprintId 迭代id
     * @return {@link Object}
     * @author 张宇
     */
    String cancelSprint(long sprintId, long userId);

    /**
     * 迭代完成
     *
     * @param sprintId 迭代id
     * @return {@link String}
     * @author 张宇
     */
    String sprintFinish(long sprintId);

    /**
     * 迭代视图 - 迭代详情
     *
     * @param sprintId 迭代id
     * @return {@link SprintOverView}
     * @author 张宇
     */
    SprintOverView sprintOverView(long sprintId);

    /**
     * 查询迭代用户
     *
     * @param sprintId 迭代id
     * @return {@link List<STeamMember>}
     * @author 张宇
     */
    List<STeamMember> querySprintUser(long sprintId);

    /**
     * @param sprintDTO
     * @Date 2021/05/21
     * @Description 迭代添加故事或缺陷
     * @Return int
     */
    boolean arrangeIssue(SprintDTO sprintDTO);

    /**
     * 按团队id查询有效迭代
     *
     * @param teamId
     * @param pageSize
     * @param pageNum
     * @param sprint   迭代名称或编号
     * @author zhaofeng
     * @date 2021/5/24 13:28
     */
    List<SprintListDTO> teamInSprint(Long teamId, Integer pageSize, Integer pageNum, String sprint);

    /**
     * 迭代视图 - 迭代统计详情
     *
     * @param sprintId 迭代id
     * @return {@link SprintStatisticalInformation}
     * @author 张宇
     */
    SprintStatisticalInformation sprintStatisticalInformation(long sprintId);

    /**
     * 查询所有的有效的迭代信息
     *
     * @author wangsh
     * @date 2021/5/24 13:28
     */
    List<SSprint> queryAllSprint();

    List<SprintV3UserHourDTO> getUsersBySprintId(Long sprintId);

    /**
     * 根据系统id获取未开始和执行中的迭代信息
     *
     * @param systemId
     * @return
     */
    List<SprintListDTO> getEffectiveSprintsBySystemId(Long systemId);

    /**
     * 迭代成员工作小时
     *
     * @param sprintId 迭代id
     * @return {@link List<SprintMembersWorkHours>}
     * @author 张宇
     */
    List<SprintMembersWorkHours> sprintMembersWorkHours(long sprintId);

    List<IssueDTO> queryNotRelationStorys(String title, Long teamId, List<Long> systemIds, Integer pageNum, Integer pageSize);


    /**
     * 分页模糊查询迭代下人员
     *
     * @param sprintId
     * @param userName
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SsoUserDTO>  querySprintVagueUser(Long sprintId, String userName, Integer pageNum, Integer pageSize);


    /**
     * 查询迭代下关联的系统
     *
     * @param sprintId
     * @return
     */
    List<SsoSystemDTO> querySystemBySprint(Long sprintId);


    /**
     * @param sprintDays
     * @param sprintTime
     * @Author maxp2
     * @Date 2021/5/27
     * @Description 是否在迭代周期内
     * @Return boolean
     */
    boolean legalDate(String sprintDays, Date sprintTime);

    /**
     * @param
     * @Author maxp2
     * @Date 2021/5/27
     * @Description 查询所有的迭代信息
     * @Return java.util.List<com.yusys.agile.sprintv3.domain.SSprintWithBLOBs>
     */
    List<SSprintWithBLOBs> querySprintList();

    /**
     * @Author maxp2
     * @Date 2021/5/30
     * @Description 获取迭代中人员预估工作量
     * @param sprintId
     * @Return java.lang.Integer
     */
    Integer getWorkload(Long sprintId);

    /**
     *功能描述 根据系统查询迭代
     * @author shenfeng
     * @date 2021/6/1
      * @param systemID
     * @return java.util.List<com.yusys.agile.sprintV3.dto.SprintListDTO>
     */
    List<SprintListDTO> querySprintBySystemId(Long systemID);

    /**
     *功能描述 查询团队下所有未开始和进行中的迭代
     * @author shenfeng
     * @date 2021/6/17
      * @param teamId
     * @return java.util.List<com.yusys.agile.sprintV3.dto.SprintListDTO>
     */
    List<SprintListDTO> listEffectiveSprintByTeamId(Long teamId);
}

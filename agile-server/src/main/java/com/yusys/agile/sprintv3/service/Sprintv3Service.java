package com.yusys.agile.sprintv3.service;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.sprintv3.responseModel.SprintMembersWorkHours;
import com.yusys.agile.sprintv3.responseModel.SprintOverView;
import com.yusys.agile.sprintv3.responseModel.SprintStatisticalInformation;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;

import com.yusys.agile.sprint.dto.SprintDTO;

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
    SprintDTO viewEdit(Long sprintId);

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
     */
    Long createSprint(SprintV3DTO sprintV3DTO);

    /**
     * 是否满足编辑条件
     *
     * @param sprintId 迭代id
     * @return boolean
     */
    boolean canEdit(Long sprintId);

    void updateSprint(SprintDTO sprintDTO, SecurityDTO securityDTO);

    /**
     * 每天改变迭代状态
     */
    void changeStatusDaily();

    /**
     * 取消迭代
     *
     * @param sprintId 迭代id
     * @return {@link Object}
     */
    String cancelSprint(long sprintId, long userId);

    /**
     * 迭代完成
     *
     * @param sprintId 迭代id
     * @return {@link String}
     */
    String sprintFinish(long sprintId);

    /**
     * 迭代视图 - 迭代详情
     *
     * @param sprintId 迭代id
     * @return {@link SprintOverView}
     */
    SprintOverView sprintOverView(long sprintId);

    /**
     * 查询迭代用户
     *
     * @param sprintId 迭代id
     * @return {@link List<STeamMember>}
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
     */
    SprintStatisticalInformation sprintStatisticalInformation(long sprintId);


    /**
     * 查询所有的有效的迭代信息
     * @author wangsh
     * @date 2021/5/24 13:28
     */
    List<SSprint> queryAllSprint();

    List<UserSprintHourDTO> getUsersBySprintId(Long sprintId);

    /**
     * 根据系统id获取未开始和执行中的迭代信息
     * @param systemId
     * @return
     */
    List<SprintListDTO> getEffectiveSprintsBySystemId(Long systemId);

    /**
     *
     * @param sprintId 迭代id
     * @return {@link List<SprintMembersWorkHours>}
     */
    List<SprintMembersWorkHours> sprintMembersWorkHours(long sprintId);

    List<IssueDTO>  queryNotRelationStorys(String title, Long systemId, Integer pageNum, Integer pageSize);
}

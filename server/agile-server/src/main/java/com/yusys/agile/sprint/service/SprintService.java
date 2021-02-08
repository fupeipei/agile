package com.yusys.agile.sprint.service;

import com.yusys.agile.sprint.dto.SprintDTO;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.facade.entity.SsoUser;

import java.util.Date;
import java.util.List;

/**
 *    maxp
 * @Date 2020/4/17
 */
public interface SprintService {

    /**
     * @param sprintDTO
     * @param projectId
     *    maxp
     * @Date 2020/4/17
     * @Description 新建迭代
     * @Return java.lang.Long
     */
    Long createSprint(SprintDTO sprintDTO, Long projectId);

    /**
     * @param sprintId
     * @param projectId
     *    maxp
     * @Date 2020/4/17
     * @Description 查看迭代编辑页面
     * @Return com.yusys.agile.sprint.dto.SprintDTO
     */
    SprintDTO viewEdit(Long sprintId, Long projectId);

    /**
     * @param dateList
     *    maxp
     * @Date 2020/4/17
     * @Description 转换迭代有效日期为String，中间以|隔开
     * @Return java.lang.String
     */
    String convertDateToStr(List<Date> dateList);

    /**
     * @param str
     *    maxp
     * @Date 2020/4/17
     * @Description 将有效日期由String转为timeStamp
     * @Return java.util.List<java.util.Date>
     */
    List<Date> convertStrToDate(String str);

    /**
     * @param sprintId
     *    maxp
     * @Date 2020/4/17
     * @Description 判断该迭代是否满足编辑条件
     * @Return boolean
     */
    boolean canEdit(Long sprintId);

    /**
     * @param sprintDTO
     * @param projectId
     *    maxp
     * @Date 2020/4/17
     * @Description 编辑迭代信息
     * @Return int
     */
    int editSprint(SprintDTO sprintDTO, Long projectId);

    /**
     * @param teamId
     * @param sprintName * @param pageNum
     *                   * @param pageSize
     *                   * @param projectId
     *    maxp
     * @Date 2020/4/17
     * @Description 通过团队id获取迭代信息以及通过迭代名称和status查询
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintDTO>
     */
    List<SprintDTO> getSprintByTeamId(Long teamId, String sprintName,Integer pageNum, Integer pageSize, Long projectId);

    /**
     * @param sprintId
     *    maxp
     * @Date 2020/4/27
     * @Description 获取迭代中人员预估工作量
     * @Return java.lang.Integer
     */
    Integer getWorkload(Long sprintId);

    /**
     * @param sprintDays
     * @param sprintTime
     *    maxp
     * @Date 2020/4/27
     * @Description 是否在迭代周期内
     * @Return boolean
     */
    boolean legalDate(String sprintDays, Date sprintTime);

    /**
     * @param sprintId
     * @param projectId
     *    maxp
     * @Date 2020/4/17
     * @Description 逻辑删除迭代
     * @Return boolean
     */
    boolean deleteSprint(Long sprintId, Long projectId);

    /**
     * @param sprintDTO
     *    maxp
     * @Date 2020/4/17
     * @Description 迭代添加故事或缺陷
     * @Return int
     */
    boolean arrangeIssue(SprintDTO sprintDTO);

    /**
     * @param projectId
     * @param idOrName
     * @param pageNum
     * @param pageSize
     *    maxp
     * @Date 2020/4/28
     * @Description 分页查询项目中所有进行中/已完成迭代
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintDTO>
     */
    List<SprintDTO> queryUnFinishedByProjectId(String idOrName, Long projectId, Integer pageNum, Integer pageSize);

    /**
     * @param sprintName
     * @param sprintType  StringConstant.SPRINT_TYPE
     * @param versionNumber
     * @param projectId
     * @param pageNum
     * @param pageSize
     *    maxp
     * @Date 2020/5/7
     * @Description 获取项目中所有迭代
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintDTO>
     */
    List<SprintDTO> viewSprints(String sprintName,String sprintType,String versionNumber, Long projectId, Integer pageNum, Integer pageSize);

    /**
     * @param projectId
     * @param sprintId
     *    maxp
     * @Date 2020/5/12
     * @Description 根据迭代id获取迭代中人员信息
     * @Return java.util.List<com.ai.portal.model.sso.entity.SsoUser>
     */
    List<SsoUser> listUsersBySprintId(Long projectId, Long sprintId);

    /**
     * 功能描述  查询迭代信息
     *
     * @param sprintId
     * @param projectId
     * @return com.yusys.agile.sprint.dto.SprintDTO
     *
     * @date 2020/5/28
     */
    List<SprintDTO>  selectSprint(Long sprintId, Long projectId) ;

    /**
     * 功能描述  根据迭代id集合查询迭代信息
     *
     * @param sprintIds
     * @return com.yusys.agile.sprint.dto.SprintDTO
     *
     * @date 2020/10/21
     */
    List<SprintDTO>  selectSprintsBySprintIdList(List<Long> sprintIds) ;

    /**
     * @param projectId
     * @param sprintId
     *    maxp
     * @Date 2020/6/9
     * @Description 编辑迭代为已完成状态
     * @Return int
     */
    int completeSprint(Long projectId, Long sprintId);

    /**
     *    maxp
     * @Date 2020/6/18
     * @Description 根据现在时间，把所有迭代未开始状态改为进行中
     * @param
     * @Return void
     */
    void changeStatusDaily();

    /**
     *    maxp
     * @Date 2020/7/16
     * @Description 统计迭代中人员代码提交次数
     * @param projectId
     * @param sprintId
     * @param pageNum
     * @param pageSize
     * @Return com.github.pagehelper.PageInfo
     */
    PageInfo sprintUserInfo(Long projectId,Long sprintId,Integer pageNum, Integer pageSize);

    /**
     *
     * @Date 2020/8/7
     * @Description 获取项目下未开始的迭代
     * @param projectId
     * @return
     */
    List<SprintDTO>  getNotStartedSprint(Long projectId);

    void  distributeStorys(List<Long> listStorys, Long projectId,Long oldSprintId,Long sprintId);

    /**
     * 功能描述: 根据项目id查询迭代
     *
     * @date 2020/8/19
     * @param projectId
     * @return java.util.List<com.yusys.agile.sprint.dto.SprintDTO>
     */
    List<SprintDTO> listUnFinisherSprintsByProjectId(Long projectId, String name, Integer pageNum, Integer pageSize);

    /**
     * 功能描述:根据项目id查询所有迭代信息
     *
     * @date 2020/11/5
     * @param projectId
     * @param sprintName
     * @return java.util.List<com.yusys.agile.sprint.dto.SprintDTO>
     */
    List<SprintDTO> listAllSprintsByProjectId(Long projectId, String sprintName);
}

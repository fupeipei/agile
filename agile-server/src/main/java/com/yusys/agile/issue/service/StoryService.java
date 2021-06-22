package com.yusys.agile.issue.service;


import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.StoryCreatePrepInfoDTO;

import java.util.List;
import java.util.Map;


/**
 *  @Description: 用户故事服务
 *  @author: zhao_yd
 *  @Date: 2021/5/24 1:44 下午
 *
 */

public interface StoryService {

    /**
     * 创建用户故事
     * @param issueDTO
     * @return
     */
    Long createStory(IssueDTO issueDTO);

    /**
     * 查询用户故事
     * @param storyId
     * @return
     */
    IssueDTO queryStory(Long storyId);

    /**
     * 删除用户故事
     * @param storyId
     * @param deleteChild
     */
    void deleteStory(Long storyId, Boolean deleteChild,Long userId);


    /**
     * 编辑用户故事
     * @param issueDTO
     */
    void editStory(IssueDTO issueDTO,Long userId);


    /**
     * 获取创建故事的前置信息（如系统、迭代、人员信息）
     * @param crateType 创建方式 1、kanban 2、列表创建
     * @param sprintId  迭代id
     * @param page     人员分页
     * @param pageSize
     * @return
     */
    StoryCreatePrepInfoDTO getStoryPreInfo(Integer crateType,Long sprintId,Long systemId,Integer page,Integer pageSize,String userName);

    /**
     * 将故事移出迭代
     * @param sprintId
     * @param storyId
     * @return int 故事记录创建条数
     */
    int removeStory4Sprint(Long sprintId, Long storyId);

    /**
     * @Date: 9:31
     * @Description: 复制用户故事
     * @Param: * @param storyId
     * @Return: Long
     */
    Long copyStory(Long storyId);

    int distributeSprint(Long storyId, Long sprintId);

    /**
     * @param pageNum
     * @param pageSize
     * @param title
     * @Date: 13:31
     * @Description: 查询未关联的用户故事列表
     * @Param: * @param featureId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryUnlinkedStory(Long featureId, Integer pageNum, Integer pageSize, String title);

    /**
     * @param issueDTO
     * @Date 2021/2/12
     * @Description 看板上通过迭代id和故事id，故事名称获取故事以及故事下的任务信息
     * @return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> listStorysAndTasks(IssueDTO issueDTO);

    /**
     * @param issueDTO
     * @param projectId
     * @Date 2021/2/27
     * @Description 看板编辑故事状态和任务卡片阻塞状态
     * @Return int
     */
    int editStoryOrTask(IssueDTO issueDTO, Long projectId);

    /**
     * @param issueDTO
     * @param pageNum
     * @param pageSize
     * @Date 2021/2/2
     * @Description 迭代评审获取故事及故事验收标准信息
     * @Return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> listStoryAcceptance(IssueDTO issueDTO,Integer pageNum, Integer pageSize);

    /**
     * @param issueDTO
     * @Date 2021/2/1
     * @Description 编辑故事评审状态及备注
     * @Return int
     */
    int editStoryAssess(IssueDTO issueDTO);

    /**
     * @param pageNum
     * @param pageSize
     * @param title
     * @Date: 2021/2/3 14:57
     * @Description: 分页查询所有用户故事
     * @Param: * @param systemId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryAllStory(Long systemId, Integer pageNum, Integer pageSize, String title);

    /**
     * @param epicId
     * @Date: 2021/2/9 9:48
     * @Description: 查询业务需求下的所有用户故事
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryStoryForEpic(Long projectId, Long epicId);

    /**
     *
     * @Date: 2021/2/9 9:51
     * @Description: 查询研发需求下所有用户故事
     * @Param: * @param projectId
     * @param featureId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */


    /**
     * @param sprintId
     * @Date: 2020/8/7
     * @Description: 查询迭代下未完成故事列表
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> getUnfinishedStoryList(Long sprintId);

    /**
     * @param storyId
     * @Date: 22020/8/7
     * @Description: 校验故事下是否存在未完成任务或未关闭缺陷 ，true表示存在
     * @Param: * @param projectId
     * @Return: boolean
     */
    boolean checkHasUnfinishOrNotRepairIssue(Long storyId);

    /**
     * @param projectId
     * @param featureId
     * @return
     * @Date: 22020/8/7
     * @Description: 查询研发需求下的故事列表
     */
    List<IssueDTO> queryStoryForFeature(Long projectId, Long featureId);

    /**
     * @param listStorys
     * @param sprintId
     * @Date: 22020/8/10
     * @Description: 故事加入到新迭代中
     */
    void distributeSprints(List<Long> listStorys, Long sprintId);

    /**
     * 功能描述: 故事及下面的任务缺陷移到新迭代
     *
     * @param listStorys
     * @param projectId
     * @param oldSprintId
     * @param sprintId
     * @return void
     * @date 2020/8/31
     */
    void distributeStoryAndTaskAndFaultToSprint(List<Long> listStorys, Long projectId, Long oldSprintId, Long sprintId);

    /**
     * 功能描述: 处理只关联迭代的缺陷
     *
     * @param oldSprintId
     * @param sprintId
     * @return void
     * @date 2020/8/31
     */
    List<Issue> dealFaultsOnlyInSprint(Long oldSprintId, Long sprintId);

    /**
     * 功能描述: 查询项目下未完成的故事
     *
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     * @date 2021/3/8
     */
    List<IssueDTO> listUnFinisherStorysByProjectId(Long projectId, String name, Integer pageNum, Integer pageSize);

    /**
     * @param sprintId
     * @description: 查询迭代下未完成的用户故事
     * @date 2020/09/10
     * @return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> getUnfinishedStorysBySprintId(Long sprintId, Integer pageNum, Integer pageSize);

    /**
     * 功能描述
     *
     * @param storyId
     * @return java.util.Map<java.lang.Long, java.lang.String>
     * @date 2020/10/26
     */
    Map<Long, String> getDevlopManager(Long storyId);

    /**
     * 判断迭代已完成，已取消，以及迭代结束日期小于当前时间的迭代
     * @param sprintId 迭代id
     * @param issueId
     */
    void checkSprintParam(Long issueId,Long sprintId);

    /**
     *功能描述 根据系统查询故事
     * @author shenfeng
     * @date 2021/6/1
      * @param systemId
     * @return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryStoryBySystemId(Long systemId,String storyName,Integer pageNum,Integer pageSize);
}

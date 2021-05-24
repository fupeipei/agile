package com.yusys.agile.issue.service;


import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.IssueDTO;

import java.util.List;
import java.util.Map;

public interface StoryService {

    /**
     * @Date: 18:07
     * @Description: 查询用户故事
     * @Param: * @param storyId
     * @Return: com.yusys.agile.issue.dto.IssueDTO
     */
    //IssueDTO queryStory(Long storyId, Long projectId);
    IssueDTO queryStory(Long storyId);

    /**
     * @param deleteChild
     * @Date: 18:08
     * @Description: 删除用户故事
     * @Param: * @param storyId
     * @Return: int
     */
    //void deleteStory(Long storyId, Boolean deleteChild,Long projectId);
    void deleteStory(Long storyId, Boolean deleteChild);

    /**
     * @param
     * @Date: 18:08
     * @Description: 编辑用户故事
     * @Param: * @param issueDTO
     * @Return: void
     */
    void editStory(IssueDTO issueDTO);

    /**
     * @Date: 18:08
     * @Description: 创建用户故事
     * @Param: * @param issueDTO
     * @Return: Long issueId
     */
    Long createStory(IssueDTO issueDTO);

    /**
     * @param projectId
     * @Date: 9:31
     * @Description: 复制用户故事
     * @Param: * @param storyId
     * @Return: Long
     */
    Long copyStory(Long storyId, Long projectId);

    int distributeSprint(Long storyId, Long sprintId);

    /**
     * @param sprintId
     * @param storyId
     * @Date 2020/4/17 17:27
     * @Description 将故事移出迭代
     * @Return int 故事记录创建条数
     */
    int removeStory4Sprint(Long sprintId, Long storyId);

    /**
     * @param pageNum
     * @param pageSize
     * @param title
     * @Date: 13:31
     * @Description: 查询未关联的用户故事列表
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryUnlinkedStory(Long projectId, Integer pageNum, Integer pageSize, String title);

    /**
     * @param issueDTO
     * @Date 2021/2/12
     * @Description 看板上通过迭代id和故事id，故事名称获取故事以及故事下的任务信息
     * @Return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
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
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @Date 2021/2/2
     * @Description 迭代评审获取故事及故事验收标准信息
     * @Return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> listStoryAcceptance(IssueDTO issueDTO, Long projectId, Integer pageNum, Integer pageSize);

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
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryAllStory(Long projectId, Integer pageNum, Integer pageSize, String title);

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
}

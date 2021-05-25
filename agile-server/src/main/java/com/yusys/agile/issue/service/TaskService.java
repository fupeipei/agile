package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;

public interface TaskService {

    /**
     * @param deleteChild
     * @Date: 18:09
     * @Description: 删除任务
     * @Param: * @param taskId
     * @Return: int
     */
    void deleteTask(Long taskId, Boolean deleteChild);

    /**
     * @param
     * @Date: 18:09
     * @Description: 编辑任务
     * @Param: * @param issueDTO
     * @Return: void
     */
    void editTask(IssueDTO issueDTO, SecurityDTO securityDTO);

    /**
     * @Date: 18:09
     * @Description: 查询任务
     * @Param: * @param taskId
     * @Return: com.yusys.agile.issue.dto.IssueDTO
     */
    //IssueDTO queryTask(Long taskId, Long projectId);
    IssueDTO queryTask(Long taskId);

    /**
     * @Date: 18:09
     * @Description: 创建任务
     * @Param: * @param issueDTO
     * @Return: Long issueId
     */
    Long createTask(IssueDTO issueDTO);

    /**
     * @param projectId
     * @Date: 9:31
     * @Description: 复制任务
     * @Param: * @param taskId
     * @Return: Long
     */
    Long copyTask(Long taskId, Long projectId);

    /**
     * @param storyId
     * @Date 2020/4/20 16:07
     * @Description 根据故事Id取消任务，并创建相关任务变更历史
     * @Return int
     */
    int cancel4Story(Long storyId);

    /**
     * @param records
     * @Date 2020/4/20 16:47
     * @Description 创建工作项的历史记录
     * @Return void
     */
    void createIssueHistoryRecords(List<IssueHistoryRecord> records);

    /**
     * @param pageNum
     * @param pageSize
     * @param title
     * @Date: 13:32
     * @Description: 查询未关联的任务列表
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryUnlinkedTask(Long projectId, Integer pageNum, Integer pageSize, String title);

    /**
     * @param issueId
     * @param from
     * @param to
     * @Date 2021/2/7
     * @Description 任务卡片拖拽
     * @Return void
     */
    void dragTask(Long issueId, Long from, Long to,Long userId);

    /**
     * @param code
     * @param storyId
     * @Date: 2021/2/9 10:21
     * @Description: 查询故事下所有任务
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryTaskForStory(Long projectId, Byte code, Long storyId);

    /**
     * @param code
     * @param storyId
     * @Date: 2021/2/9 10:22
     * @Description: 查询故事下所有缺陷
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryFaultForStory(Long projectId, Byte code, Long storyId);

    /**
     * @param pageNum
     * @param pageSize
     * @param title
     * @Date: 2021/2/16 11:22
     * @Description: 查询所有任务
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryAllTask(Long projectId, Integer pageNum, Integer pageSize, String title);

    /**
     * 功能描述: 提供cicd接口-根据任务id信息查询上层故事id集合
     *
     * @param taskIds
     * @return java.util.List<java.lang.Long>
     * @date 2021/2/8
     */
    List<Long> listStoryIdsByTaskIds(List<Long> taskIds);
}

package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.dto.IssueDTO;

import java.util.List;

public interface TaskService {

    /**
     *
     * @Date: 18:09
     * @Description: 删除任务
     * @Param: * @param taskId
    * @param deleteChild
     * @Return: int
     */
    void deleteTask(Long taskId, Boolean deleteChild);

    /**
     *
     * @Date: 18:09
     * @Description: 编辑任务
     * @Param: * @param issueDTO
    * @param
     * @Return: void
     */
    void editTask(IssueDTO issueDTO);

    /**
     *
     * @Date: 18:09
     * @Description: 查询任务
     * @Param: * @param taskId
     * @Return: com.yusys.agile.issue.dto.IssueDTO
     */
    //IssueDTO queryTask(Long taskId, Long projectId);
    IssueDTO queryTask(Long taskId);

    /**
     *
     * @Date: 18:09
     * @Description: 创建任务
     * @Param: * @param issueDTO
     * @Return: Long issueId
     */
    Long createTask(IssueDTO issueDTO);

    /**
     *
     * @Date: 9:31
     * @Description: 复制任务
     * @Param: * @param taskId
    * @param projectId
     * @Return: Long
     */
    Long copyTask(Long taskId, Long projectId);

    /**
     *    maxp
     * @Date 2020/4/20 16:07
     * @Description 根据故事Id取消任务，并创建相关任务变更历史
      * @param storyId
     * @Return int
     */
    int cancel4Story(Long storyId);

    /**
     *    maxp
     * @Date 2020/4/20 16:47
     * @Description 创建工作项的历史记录
     * @param records
     * @Return void
     */
    void createIssueHistoryRecords(List<IssueHistoryRecord> records);

    /**
     *
     * @Date: 13:32
     * @Description: 查询未关联的任务列表
     * @Param: * @param projectId
     * @param pageNum
     * @param pageSize
     * @param title
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryUnlinkedTask(Long projectId,Integer pageNum,Integer pageSize,String title);

    /**
     *    maxp
     * @Date 2020/5/7
     * @Description 任务卡片拖拽
      * @param issueId
     * @param from
     * @param to
     * @Return void
     */
    void dragTask(Long issueId, Long from, Long to);

    /**
     *
     * @Date: 2020/6/9 10:21
     * @Description: 查询故事下所有任务
     * @Param: * @param projectId
    * @param code
    * @param storyId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryTaskForStory(Long projectId, Byte code, Long storyId);

    /**
     *
     * @Date: 2020/6/9 10:22
     * @Description: 查询故事下所有缺陷
     * @Param: * @param projectId
    * @param code
    * @param storyId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryFaultForStory(Long projectId, Byte code, Long storyId);

    /**
     *
     * @Date: 2020/6/16 11:22
     * @Description: 查询所有任务
     * @Param: * @param projectId
    * @param pageNum
    * @param pageSize
    * @param title
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryAllTask(Long projectId, Integer pageNum, Integer pageSize, String title);

    /**
     * 功能描述: 提供cicd接口-根据任务id信息查询上层故事id集合
     *
     * @date 2020/11/17
     * @param taskIds
     * @return java.util.List<java.lang.Long>
     */
    List<Long> listStoryIdsByTaskIds(List<Long> taskIds);
}

package com.yusys.agile.issue.dao;

import com.yusys.agile.burndown.dto.HistogramTaskDTO;
import com.yusys.agile.issue.domain.CustomFieldJsonType;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.domain.IssueRecord;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.servicemanager.dto.ServiceManageIssueDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IssueMapper {
    long countByExample(IssueExample example);

    int deleteByExample(IssueExample example);

    int deleteByPrimaryKey(Long issueId);

    int insert(Issue record);

    int insertSelective(Issue record);

    List<Issue> selectByExampleWithBLOBs(IssueExample example);

    List<Issue> selectByExample(IssueExample example);

    Issue selectByPrimaryKey(Long issueId);

    int updateByExampleSelective(@Param("record") Issue record, @Param("example") IssueExample example);

    int updateByExampleWithBLOBs(@Param("record") Issue record, @Param("example") IssueExample example);

    int updateByExample(@Param("record") Issue record, @Param("example") IssueExample example);

    int updateByPrimaryKeySelective(Issue record);

    int updateByPrimaryKeySelectiveWithNull(Issue record);

    int updateByPrimaryKeyWithBLOBs(Issue record);

    int updateByPrimaryKey(Issue record);

    /**
     * @Date: 18:11
     * @Description: 解除关联子工作项
     * @Param: * @param issueId
     * @Return: void
     */
    void updateStatusAndParentId(Long issueId);

    List<Long> listAllFixedUsers(Long projectId);

    List<Long> listAllCreateUsers(Long projectId);

    List<Long> listAllTestUsers(Long projectId);

    /**
     * @Date: 18:11
     * @Description: 获取迭代下的故事列表
     * @Param: * @param sprintId
     * @Return: java.util.List<com.yusys.agile.issue.domain.Issue>
     */
    List<Issue> getStoryBySprintId(Long sprintId);


    /**
     * @Date: 18:11
     * @Description: 统计迭代下的所有故事数
     * @Param: * @param sprintId
     * @Return: java.lang.Integer
     */
    Integer countStories4Sprint(Long sprintId);

    /**
     * @Date: 18:12
     * @Description: 统计在迭代中的故事数
     * @Param: * @param sprintId
     * @Return: int
     */
    int countInsprintBySprint(Long sprintId);

    /**
     * @Date: 18:13
     * @Description: 统计迭代的所有任务+缺陷数
     * @Param: * @param sprintId
     * @Return: java.lang.Integer
     */
    Integer countTasks4Sprint(Long sprintId);

    /**
     * @Date: 18:13
     * @Description: 统计迭代下未完成的任务+缺陷数
     * @Param: * @param sprintId
     * @Return: int
     */
    int countUnFinishedTasks4Sprint(Long sprintId);

    /**
     * @param issueId
     * @Date: 18:29
     * @Description: 删除关联关系
     * @Param: * @param parentId
     * @Return: void
     */
    void deleteRelation(@Param("parentId") Long parentId, @Param("sprintId") Long sprintId, @Param("issueId") Long issueId);

    /**
     * @param issueId
     * @param sprintId
     * @Date 2020/4/17
     * @Description 让故事下的任务关联迭代
     * @Return int
     */
    int changeSprintIdByStoryId(@Param("issueId") Long issueId, @Param("sprintId") Long sprintId);

    /**
     * @param issueIds
     * @Date 2020/4/20
     * @Description 通过主键id查询无效故事（做判断）
     * @Return int
     */
    int count4ArrangedByIds(@Param("issueIds") List<Long> issueIds);

    /**
     * @param storyId
     * @Date 2020/4/20 16:55
     * @Description 通过故事id将任务移出迭代
     * @Return int
     */
    int updateStatusByParentId(Long storyId);

    /**
     * @Date: 21:56
     * @Description: 获取迭代下的所有任务和缺陷列表
     * @Param: * @param sprintId
     * @Return: java.util.List<com.yusys.agile.issue.domain.Issue>
     */
    List<Issue> getBySprint(Long sprintId);

    /**
     * @param listIssueId
     * @Date: 9:10
     * @Description: 批量建立关联关系
     * @Param: * @param parentId
     * @Return: void
     */
    void createBatchRelation(@Param("listIssueId") List<Long> listIssueId, @Param("sprintId") Long sprintId, @Param("parentId") Long parentId, @Param("updateUid") Long updateUid);

    /**
     * @Date: 13:30
     * @Description: 查询未关联的工作项
     * @Param: * @param example
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> selectByExampleDTO(IssueExample example);

    /**
     * @Date: 16:33
     * @Description: 解除所有子工作项关联关系
     * @Param: * @param issueId
     * @Return: void
     */
    void deleteAllChildRelation(@Param("parentId") Long parentId, @Param("sprintId") Long sprintId);

    List<Issue> getSecondStageNullIssueList(@Param("projectId") Long projectId, @Param("stageId") Long stageId);

    int batchUpdateIssueLaneId(@Param("list") List<Long> list, @Param("projectId") Long projectId, @Param("stageId") Long stageId, @Param("laneId") Long laneId);

    int updateIssueLaneStateNull(Issue record);

    List<Issue> getSecondLaneBindedIssues(@Param("projectId") Long projectId, @Param("stageId") Long stageId, @Param("laneId") Long laneId);

    /**
     * @param sprintId
     * @Date 2021/2/14
     * @Description 统计迭代下所有的任务数
     * @Return java.lang.Integer
     */
    Integer countTasks4SprintId(Long sprintId);

    /**
     * @param sprintId
     * @Date 2021/2/14
     * @Description 统计迭代下完成的任务数
     * @Return int
     */
    int countFinishedTasks4SprintId(Long sprintId);

    /**
     * @param sprintId
     * @Date 2021/2/14
     * @Description 统计迭代下所有的缺陷数
     * @Return java.lang.Integer
     */
    Integer countFaults4SprintId(Long sprintId);

    /**
     * @param sprintId
     * @Date 2021/2/14
     * @Description 统计迭代下未完成的缺陷数
     * @Return int
     */
    int countFinishedFaults4SprintId(Long sprintId);

    /**
     * @param sprintId
     * @Date 2021/2/14
     * @Description 统计迭代中计划工作量
     * @Return java.lang.Integer
     */
    Integer sumPlanWorkload4Sprint(Long sprintId);

    /**
     * @param sprintId
     * @Date 2021/2/14
     * @Description 统计迭代中剩余工作量
     * @Return java.lang.Integer
     */
    Integer sumRemainWorkload4Sprint(Long sprintId);

    /**
     * @Date: 2021/2/21 11:20
     * @Description: 更新迭代id
     * @Param: * @param issue
     * @Return: void
     */
    void updatePrint(Issue issue);

    /**
     * 功能描述: 拖拽变更缺陷
     *
     * @param record
     * @return int
     * @date 2021/2/26
     */
    int updateFaultByPrimaryKeySelectiveWithNull(Issue record);

    /**
     * @Date: 2021/2/26 17:06
     * @Description: 更新处理人
     * @Param: * @param issue
     * @Return: void
     */
    void updateHandler(Issue issue);

    /**
     * 功能描述: 重新打开缺陷
     *
     * @param record
     * @return void
     * @date 2021/2/28
     */
    void reopenFault(Issue record);

    /**
     * @param issueId
     * @Date: 2021/2/3 10:22
     * @Description: 详情显示工作项关联关系列表
     * @Param: projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> listRelation(@Param("issueId") Long issueId, @Param("projectId") Long projectId);

    List<HistogramTaskDTO> getTaskMemberAnalysis(Long sprintId);

    /**
     * @param epicId
     * @Date: 2021/2/9 10:00
     * @Description: 查询业务需求下的所有用户故事
     * @Param: * @param projectId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> queryStoryForEpic(@Param("projectId") Long projectId, @Param("epicId") Long epicId);

    /**
     * @param issueType
     * @Date: 2021/2/10 10:20
     * @Description: 查询模板父工作项列表
     * @Param: projectId
     * @Return: java.util.List<com.yusys.agile.issue.domain.Issue>
     */
    List<Issue> getTemplateParentIssueList(@Param("projectId") Long projectId, @Param("issueType") Byte issueType);

    /**
     * @param record
     * @Date 2021/2/15
     * @Description 看板修改故事状态及任务阻塞状态
     * @Return int
     */
    int updateByPrimaryKeySelectiveWithNotNull(Issue record);

    /**
     * 功能描述  高级搜索
     *
     * @param issueRecord
     * @return java.util.List<com.yusys.agile.issue.domain.Issue>
     * @date 2021/2/16
     */
    List<Issue> queryIssueList(@Param("issueRecord") IssueRecord issueRecord, @Param("customFieldJsonTypeList") List<CustomFieldJsonType> customFieldJsonTypeList);

    /**
     * @param issueId
     * @param sprintId
     * @Date 2021/2/21
     * @Description 修改工作项的迭代id
     * @Return int
     */
    int updateBySprintId(@Param("issueId") Long issueId, @Param("sprintId") Long sprintId);

    /**
     * @param projectId
     * @param systemId
     * @param issueType
     * @Date 2021/2/22
     * @Description 根据项目id和系统id获取工作项信息
     * @Return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> selectBySystemId(@Param("projectId") Long projectId, @Param("systemId") Long systemId, @Param("issueType") Byte issueType);


    /**
     * @param projectId
     * @param systemId
     * @param issueType
     * @Date 2021/3/30
     * @Description 根据项目id、系统id、版本号获取工作项信息
     * @Return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    List<IssueDTO> selectBySystemIdAndVersion(@Param("projectId") Long projectId, @Param("systemId") Long systemId, @Param("versionId") Long versionId, @Param("issueType") Byte issueType);

    /**
     * @param projectId
     * @param userId
     * @Date 2021/2/6
     * @Description 统计人员的任务数
     * @Return int
     */
    int sumTaskByHandler(@Param("projectId") Long projectId, @Param("userId") Long userId);

    /**
     * @param projectId
     * @param userId
     * @Date 2021/2/6
     * @Description 统计人员的工时数
     * @Return int
     */
    int sumWorkloadByUser(@Param("projectId") Long projectId, @Param("userId") Long userId);

    /**
     * @param projectId
     * @param sprintId
     * @param memberIdList
     * @param startIndex
     * @param pageSize
     * @return
     * @descirption 分页查询成员任务
     */
    List<Issue> getProjectMemberTaskList(@Param("projectId") Long projectId, @Param("sprintId") Long sprintId,
                                         @Param("memberIdList") List<Long> memberIdList, @Param("startIndex") long startIndex, @Param("pageSize") long pageSize);

    /**
     * 功能描述:
     *
     * @param id
     * @return java.util.List<java.lang.Long>
     * @date 2021/2/22
     */
    List<Long> getStoryIdsByEpic(@Param("id") Long id);

    /**
     * 功能描述:
     *
     * @param id
     * @return java.util.List<java.lang.Long>
     * @date 2021/2/22
     */
    List<Long> getStroyIdsByFeature(@Param("id") Long id);

    /**
     * 功能描述:查询故事下为完成任务或未关闭缺陷
     *
     * @param storyId
     * @return int
     * @date 2020/8/7
     */
    int countUnfinishOrNotRepairIssue(Long storyId);


    List<Long> selectIssueIdByProjectId(@Param("projectId") Long projectId, @Param("title") String title);

    /**
     * @param issueId
     * @param type
     * @return
     * @description 根据工作项id和类型查询工作项信息
     * @date 2021/3/18
     */
    Issue selectIssueInfo(@Param("issueId") Long issueId, @Param("type") int type);

    /**
     * @param projectId
     * @param issueType
     * @return
     * @description 查询研发需求、故事、任务父工作项
     * @date 2020/08/26
     */
    List<Issue> getIssueTemplateParentList(@Param("projectId") Long projectId, @Param("issueType") Byte issueType);

    /**
     * @param projectId
     * @param offset
     * @param rows
     * @return
     * @description 分页查询缺陷工作项
     * @date 2020/09/08
     */
    List<Issue> getFaultIssuesByPage(@Param("projectId") Long projectId, @Param("offset") int offset, @Param("rows") int rows);

    List<Issue> getEpioByBizNums(List<String> bizNumList);

    List<Issue> getIssueByBizNums(@Param("bizNumList") List<String> bizNumList, @Param("issueType") Byte issueType);

    /**
     * @param featureId
     * @param cmpSyncResult
     * @return
     * @description 更新feature同步状态
     * @date 2020/09/29
     */
    int updateIssueFeatureSyncResult(@Param("featureId") Long featureId, @Param("cmpSyncResult") Byte cmpSyncResult);

    /**
     * @param issueId
     * @return
     * @description 查询epic
     * @date 2020/09/30
     */
    Issue getIssue(@Param("issueId") Long issueId);

    /**
     * @param issueIdList
     * @param stageId
     * @return
     * @description 批量更新工作项阶段状态
     * @date 2020/10/2
     */
    int batchUpdateIssueStageStatus(@Param("issueIdList") List<Long> issueIdList, @Param("stageId") Long stageId, @Param("laneId") Long laneId);

    /**
     * @param startIndex
     * @param pageSize
     * @param issueType
     * @return
     * @description 分页查询工作项列表
     * @date 2020/10/20
     */
    List<Issue> selectIssueList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("issueType") Byte issueType);

    /**
     * @param issueIdList
     * @param cmpSyncResult
     * @return
     * @description 批量更新工作项下cmp
     * @date 2020/10/12
     */
    int batchUpdateIssueCmpSyncResult(@Param("issueIdList") List<Long> issueIdList, @Param("cmpSyncResult") Byte cmpSyncResult);

    /**
     * @param startIndex
     * @param serviceManageIssueDTO
     * @return
     * @description 根据工作项id和类型查询工作项信息
     * @date 2020/10/28
     */
    List<Issue> selectServiceManageIssueList(@Param("startIndex") int startIndex, @Param("serviceManage") ServiceManageIssueDTO serviceManageIssueDTO);

    List<IssueDTO> selectIssueForVersion(IssueDTO issueExample);

    /**
     * @param sprintId
     * @param issueType
     * @Date 2021/2/4
     * @Description 项目及迭代中已完成工作项个数
     * @Return int
     */
    int countAchievedIssues4Sprint(@Param("sprintId") Long sprintId, @Param("projectId") Long projectId, @Param("issueType") Byte issueType);

    /**
     * @param sprintId
     * @param issueType
     * @Date 2021/2/4
     * @Description 项目及迭代中进行中工作项个数
     * @Return int
     */
    int countInsprintIssuesBySprint(@Param("sprintId") Long sprintId, @Param("projectId") Long projectId, @Param("issueType") Byte issueType);

    /**
     * @param sprintId
     * @param issueType
     * @Date 2021/2/4
     * @Description 项目及迭代中未完成工作项个数
     * @Return int
     */
    int countNotStartIssuesBySprint(@Param("sprintId") Long sprintId, @Param("projectId") Long projectId, @Param("issueType") Byte issueType);

    /**
     * @param sprintId
     * @Date 2021/2/4
     * @Description 项目及迭代中未完成任务个数
     * @Return int
     */
    int countInsprintTaskBySprint(@Param("sprintId") Long sprintId, @Param("projectId") Long projectId);

    /**
     * @param sprintId
     * @Date 2021/2/4
     * @Description 项目及迭代中未完成任务个数
     * @Return int
     */
    int countNotStartTaskBySprint(@Param("sprintId") Long sprintId, @Param("projectId") Long projectId);


    /**
     * @param sprintId
     * @Date 2021/2/14
     * @Description 统计项目及迭代下完成的任务数
     * @Return int
     */
    int countFinishedTasks4Project(@Param("sprintId") Long sprintId, @Param("projectId") Long projectId);


    List<Long> listAllIssueId(@Param("issueId") List<Long> issueIdList);


    List<Long> listLevelIssueIdforEpic(@Param("issueIds") List<Long> issueIdList);

    List<Long> listLevelIssueIdforFeature(@Param("issueIds") List<Long> issueIdList, @Param("issueEpicIds") List<Long> issueEpicIds);

    List<Long> listLevelIssueIdforStory(@Param("issueIds") List<Long> issueIdList, @Param("issueFeatureIds") List<Long> issueFeatureIds);

    List<Long> listLevelIssueIdforTask(@Param("issueIds") List<Long> issueIdList, @Param("issueStoryIds") List<Long> issueStoryIds);


    int insertTmp(Issue record);

    int updateTmp(Issue record);

    /**
     * 根据parentId查询子工作项列表，
     * 主要关联kanban_stage_instance表，
     * 根据kanban_stage_instance中的order_id对issue列表升序排序
     *
     * @param parentId
     * @return
     */
    List<Issue> selectIssueListByParentId(@Param("parentId") Long parentId, @Param("kanbanId") Long kanbanId);

    List<Long> getNotCanceledAndOnlineIssueByIssueIdList(@Param("issueIdList") List<Long> issueIdList);

    List<Long> getNotOnlineEpic();

    List<Long> noReqSchedulingEpicIdList();

    List<String> getAllTaskFunTester(Long epicId);

    /**
     * @param issueId
     * @return
     * @description 查询父工作项信息
     * @date 2020/12/15
     */
    Issue getParentIssue(Long issueId);

    /**
     * 查询没有关联迭代的故事
     * @param title
     * @param systemIds
     * @return
     */
    List<IssueDTO> queryNotRelationStory(@Param("title") String title,@Param("systemIds")List<Long> systemIds);
    /**
     * 查询迭代下故事点数的总数
     * @author zhaofeng
     * @date 2021/5/26 14:40
     * @param sprintId
     */
    Integer countStoryPointsForSprint(@Param("sprintId") Long sprintId);
    /**
     * 查询当前日期完成的故事点总数
     * @author zhaofeng
     * @date 2021/5/26 15:40
     * @param sprintId 迭代id
     * @param currTime 当前日期
     */
    Integer countCurrTimeStoryPointsForSprintId(@Param("sprintId") Long sprintId,@Param("currTime") Date currTime);

    /**
     * @Author maxp2
     * @Date 2021/5/27
     * @Description 获取迭代中工作项计划工作量
     * @param sprintId
     * @Return java.lang.Integer
     */
    Integer getPlanWorkload(Long sprintId);

    /**
     * @Author maxp2
     * @Date 2021/5/27
     * @Description 获取迭代中工作项剩余工作量
     * @param sprintId
     * @Return int
     */
    int getRemainWorkload(Long sprintId);
    /**
     * 获取完成的故事点总数
     * @author zhaofeng
     * @date 2021/6/2 20:55
     * @param sprintId
     */
    Integer countFinishedStoryPoint(@Param("sprintId") Long sprintId);

    void batchUpdateIssueSystemId(@Param("issueIdList") List<Issue> issueIdList, @Param("systemId")Long systemId);
}
package com.yusys.agile.commit.service.impl;

import com.yusys.agile.commit.dto.CommitDTO;
import com.yusys.agile.commit.service.CommitService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.service.IssueService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description 成员提交代码业务类
 * @date 2021/2/1
 */
@Service
public class CommitServiceImpl implements CommitService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitServiceImpl.class);

    /**
     * 每次允许查询任务最大数
     */
    private static final int ALLOW_QUERY_TASK_MAX = 1000;

    /**
     * 批任务数据量
     */
    private static final int QUERY_TASK_BATCH_SIZE = 500;

    /**
     * 批处理数据量
     */
    private static final int BATCH_SIZE = 50;

    @Resource
    private IssueService issueService;

    /*@Resource
    private ICmsChangeApi cmsChangeApi;*/

    @Resource
    private IssueMapper issueMapper;

    /**
     * @description 查询成员提交记录
     * @date 2021/2/1
     * @param commitDTO
     * @return
     */
    @Override
    public CommitDTO getMemberCommitRecord(CommitDTO commitDTO) {
        LOGGER.info("getMemberCommitRecord param commitDTO:{}", commitDTO);
        List<Long> memberIdList= commitDTO.getMemberIdList();
        if (CollectionUtils.isEmpty(memberIdList)) {
            throw new RuntimeException("用户编号不能为空");
        }
        //查询指定项目下用户任务总记录数
        long total = issueService.getProjectMemberTaskTotal(commitDTO);
        if (total > ALLOW_QUERY_TASK_MAX) {
            Map<String,List<String>> memberTasksMap = Maps.newHashMap();
            long pages = total % QUERY_TASK_BATCH_SIZE == 0 ? total / QUERY_TASK_BATCH_SIZE : total / QUERY_TASK_BATCH_SIZE + 1;
            for (long i = 0; i < pages; i++) {
                long startIndex = i * QUERY_TASK_BATCH_SIZE;
                List<Issue> issues = issueService.getProjectMemberTaskList(commitDTO, startIndex, QUERY_TASK_BATCH_SIZE);
                Map<String, List<String>> memberTaskMap = dealIssueTasks(issues);
                if (MapUtils.isNotEmpty(memberTaskMap)) {
                    for (Map.Entry<String, List<String>> memberTaskEntry : memberTaskMap.entrySet()){
                        String key = memberTaskEntry.getKey();
                        List<String> value = memberTaskEntry.getValue();
                        if (memberTasksMap.containsKey(key)) {
                            List<String> taskIdList = memberTasksMap.get(key);
                            taskIdList.addAll(value);
                        } else {
                            memberTasksMap.put(key, value);
                        }
                    }
                }
            }
            calculateCodeCommitData(commitDTO, memberTasksMap);
        } else {
            if (total != 0) {
                List<Issue> issues = issueService.getProjectMemberTaskList(commitDTO);
                dealCodeCommitData(commitDTO, issues);
            }
        }
        return commitDTO;
    }

    /**
     * @description 处理代码提交数据
     * @date 2021/2/1
     * @param commitDTO
     * @param issues
     */
    private void dealCodeCommitData(CommitDTO commitDTO, List<Issue> issues) {
        if (CollectionUtils.isNotEmpty(issues)) {
            Map<String, List<String>> memberTaskMap = dealIssueTasks(issues);
            calculateCodeCommitData(commitDTO, memberTaskMap);
        }
    }
    /**
     * @description 根据用户id分组任务
     * @date 2021/2/1
     * @param issues
     * @return
     */
    private Map<String, List<String>> dealIssueTasks(List<Issue> issues) {
        Map<String, List<String>> memberTaskMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(issues)) {
            issues.forEach(task -> {
                String memberId = String.valueOf(task.getHandler());
                String taskId = String.valueOf(task.getIssueId());
                if (memberTaskMap.containsKey(memberId)) {
                    List<String> tasks = memberTaskMap.get(memberId);
                    tasks.add(taskId);
                } else {
                    memberTaskMap.put(memberId, Lists.newArrayList(taskId));
                }
            });
        }
        return memberTaskMap;
    }

    /**
     * @description 计算代码提交数据
     * @date 2021/2/1
     * @param commitDTO
     * @param memberTaskMap
     * @return
     */
    private void calculateCodeCommitData(CommitDTO commitDTO, Map<String, List<String>> memberTaskMap) {
        //提交次数
        List<Map<String, Integer>> commitTimesList = Lists.newArrayList();
        //新增行数
        List<Map<String, Integer>> addLinesList = Lists.newArrayList();
        //删除行数
        List<Map<String, Integer>> deleteLinesList = Lists.newArrayList();
        if (MapUtils.isNotEmpty(memberTaskMap)) {
            for (Map.Entry<String, List<String>> entry : memberTaskMap.entrySet()){
                int commitTimes = 0;
                int addLines = 0;
                int deleteLines = 0;
                List<String> tempTaskIdList = Lists.newArrayList();
                List<String> taskIdList = entry.getValue();
                int size = taskIdList.size();
                for (int i = 0; i < size; i++) {
                    /*tempTaskIdList.add(taskIdList.get(i));
                    if (tempTaskIdList.size() % BATCH_SIZE == 0 || i == size - 1) {
                        List<TaskResponseDTO> commitResponse = cmsChangeApi.queryCommitInfo(tempTaskIdList);
                        if (CollectionUtils.isNotEmpty(commitResponse)) {
                            //遍历单个任务提交次数
                            for (TaskResponseDTO response : commitResponse) {
                                List<com.ai.cicd.model.cms.dto.CommitDTO> commitList = response.getCommits();
                                if (CollectionUtils.isNotEmpty(commitList)) {
                                    Set<String> commitIdSet = Sets.newHashSet();
                                    for (com.ai.cicd.model.cms.dto.CommitDTO commit : commitList) {
                                        commitIdSet.add(commit.getCommitId());
                                        //新增行数
                                        int singleAddLines = Integer.parseInt(commit.getAddCount());
                                        addLines += singleAddLines;
                                        //删除行数
                                        int singleDeleteLines = Integer.parseInt(commit.getDeleteCount());
                                        deleteLines += singleDeleteLines;
                                    }
                                    //提交次数
                                    int singleCommitTimes = commitIdSet.size();
                                    commitTimes += singleCommitTimes;
                                }
                            }
                        }
                        tempTaskIdList.clear();
                    }*/
                }
                //成员编号
                String memberId = entry.getKey();
                //提交次数
                Map<String, Integer> commitTimeMap = Maps.newHashMap();
                commitTimeMap.put(memberId, commitTimes);
                commitTimesList.add(commitTimeMap);
                commitDTO.setCommitTimes(commitTimesList);
                //新增行数
                Map<String, Integer> addLinesMap = Maps.newHashMap();
                addLinesMap.put(memberId, addLines);
                addLinesList.add(addLinesMap);
                commitDTO.setAddLines(addLinesList);
                //删除行数
                Map<String, Integer> deleteLinesMap = Maps.newHashMap();
                deleteLinesMap.put(memberId, deleteLines);
                deleteLinesList.add(deleteLinesMap);
                commitDTO.setDeleteLines(deleteLinesList);
            }
        }
    }

    /**
     * @description 查询工作项下代码提交记录
     * @date 2021/2/1
     * @param issueId
     * @param issueType
     * @param pageNumber
     * @param pageSize
     * @return
     */
   /* @Override
    public PageInfo<List<com.ai.cicd.model.cms.dto.CommitDTO>> getIssueCommitRecord(Long issueId, Byte issueType, Integer pageNumber, Integer pageSize) {
        List<com.ai.cicd.model.cms.dto.CommitDTO> commitList = Lists.newArrayList();
        pageNumber = null == pageNumber ? 1 : pageNumber;
        pageSize = null == pageSize ? 20 : pageSize;
        if (IssueTypeEnum.TYPE_STORY.CODE.equals(issueType)) {
            TaskCommitQueryDTO queryDTO = assembleTaskIdsByStoryId(issueId);
            if (null != queryDTO) {
                queryDTO.setPageNum(pageNumber);
                queryDTO.setPageSize(pageSize);
                return cmsChangeApi.queryCommitInfoByTaskIds(queryDTO);
            } else {
                return new PageInfo(commitList);
            }
        } else {
            return cmsChangeApi.queryCommitInfoByTaskId(String.valueOf(issueId), pageNumber, pageSize);
        }
    }*/

    /**
     * @description 组装故事下多任务提交查询对象
     * @date 2021/2/1
     * @param storyId
     * @return
     */
    /*private TaskCommitQueryDTO assembleTaskIdsByStoryId(Long storyId) {
        TaskCommitQueryDTO commitQueryDTO = null;
        IssueExample issueExample = new IssueExample();
        issueExample.setOrderByClause("issue_id asc");
        issueExample.createCriteria()
            .andParentIdEqualTo(storyId)
                .andIssueTypeEqualTo(IssueTypeEnum.TYPE_TASK.CODE)
                    .andStateEqualTo(StateEnum.U.getValue());
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        if (CollectionUtils.isNotEmpty(issues)) {
            List<String> taskIds = Lists.newArrayList();
            commitQueryDTO = new TaskCommitQueryDTO();
            issues.forEach(issue -> {
                taskIds.add(String.valueOf(issue.getIssueId()));
            });
            commitQueryDTO.setTaskIds(taskIds);
        }
        return commitQueryDTO;
    }*/
}

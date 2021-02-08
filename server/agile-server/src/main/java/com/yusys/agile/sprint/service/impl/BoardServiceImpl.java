package com.yusys.agile.sprint.service.impl;

import com.yusys.agile.sprint.dto.BoardStoryParam;
import com.yusys.agile.sprint.dao.TaskBoardMapper;
import com.yusys.agile.sprint.domain.TaskBoardUnionItem;
import com.yusys.agile.sprint.service.BoardService;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.TaskTypeEnum;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.domain.SprintWithBLOBs;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 *    maxp
 * @Date 2020/4/30
 * @Description
 */
@Service
public class BoardServiceImpl implements BoardService {
    private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
    @Resource
    private TaskBoardMapper taskBoardMapper;
    @Resource
    private SprintMapper sprintMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;

    @Override
    public List<IssueDTO> getStoryWithTask(BoardStoryParam param) {
        Long sprintId = param.getSprintId();
        SprintWithBLOBs sprint = sprintMapper.selectByPrimaryKey(sprintId);
        Optional.ofNullable(sprint).orElseThrow(()-> new BusinessException("迭代计划不存在"));
        int workHours = sprint.getWorkHours();
        String storyKeyWord = param.getStoryKeyWord();
        String taskKeyWord = param.getTaskKeyWord();
        List<Integer> userIds = param.getUserIds();
        List<Integer> moduleId = param.getModuleIds();
        List<Integer> taskTypeIds = param.getTaskTypeIds();
        List<TaskBoardUnionItem> items = taskBoardMapper.search(sprintId, storyKeyWord, moduleId, taskKeyWord, userIds, taskTypeIds);
        if (CollectionUtils.isEmpty(items)) {
            return new ArrayList<>();
        }
        //整理story、task层级关系
        Map<Long, IssueDTO> storyDTOMap = new HashMap<>();
        List<IssueDTO> taskDTOS = new ArrayList<>(items.size());
        List<Long> ownerIds = new ArrayList<>(items.size());
        for (TaskBoardUnionItem item : items) {
            //如果sotryDTOMap中没有对应key，那么创建sotry，补全信息，并加入map
            if (!storyDTOMap.containsKey(item.getStoryId())) {
                IssueDTO storyDTO = generateStory(item);
                storyDTOMap.put(item.getStoryId(), storyDTO);
                //增加缺陷数量和用例数量
            }
                if (null != item.getTaskId()) {
                    //补全部分task信息
                    IssueDTO taskDTO = generateTaskDTO(workHours, ownerIds, item);
                    taskDTOS.add(taskDTO);
                    storyDTOMap.get(item.getStoryId()).getChildren().add(taskDTO);
                }
            //根据人搜索或者任务搜索，并且没有任何任务则剔除对应的故事
            if ((null != userIds || StringUtils.isNotEmpty(taskKeyWord) || null != taskTypeIds)){
                removeEmptyTasksStory(storyDTOMap);
            }
            if (!ownerIds.isEmpty()) {
                List<SsoUser> userDTOS = iFacadeUserApi.listUsersByIds(ownerIds);
                try {
                    List<SsoUserDTO> ssoUserDTOS = ReflectUtil.copyProperties4List(userDTOS, SsoUserDTO.class);
                    Map<Long, SsoUserDTO> userDTOMap = new HashMap<>();
                    for (SsoUserDTO dto : ssoUserDTOS) {
                        userDTOMap.put(dto.getUserId(), dto);
                    }

                    //设置owner信息
                    for (IssueDTO taskDTO : taskDTOS) {
                        Long owner = taskDTO.getHandler();
                        if (null != owner && owner > 0) {
                            SsoUserDTO userDTO = userDTOMap.get(owner);
                            taskDTO.setOwner(userDTO);
                        }
                    }
                } catch (Exception e) {
                    log.error("SsoUser 转换数据异常：{}", e);
                }

            }
        }
        //故事排序
        List<IssueDTO> storyDTOS =createTimeSort(prioritySort(storyDTOMap)); ;
        return storyDTOS;
    }

    private IssueDTO generateTaskDTO(int workHours, List<Long> ownerIds, TaskBoardUnionItem item) {
        IssueDTO taskDTO = new IssueDTO();
        taskDTO.setIssueId(item.getTaskId());
        taskDTO.setTitle(item.getTaskName());
        taskDTO.setDescription(item.getTaskDesc());
        taskDTO.setTaskType(item.getTaskType());
        taskDTO.setState(item.getTaskState());
        Long ownerId = item.getTaskOwner();
        taskDTO.setHandler(ownerId);
        taskDTO.setCreateTime(item.getTaskCreateTime());
        taskDTO.setPlanWorkload(item.getPlanWorkload());
        taskDTO.setRemainWorkload(item.getRemainWorkload());
        taskDTO.setBeginDate(item.getStartTime());
        taskDTO.setEndDate(item.getEndTime());
        taskDTO.setStageId(item.getTaskStageId());
        taskDTO.setBlockState(item.getBlockState());
        //增加任务类型描述
        taskDTO.setTaskTypeDesc(TaskTypeEnum.getName(item.getTaskType()));

        //设置是否超时
        Date start = taskDTO.getBeginDate();
        Date end = taskDTO.getEndDate();
        if(null != start){
            taskDTO.setOverTime(isTaskOverTime(start, end, taskDTO.getPlanWorkload(), workHours));
        }
        if (null != ownerId) {
            ownerIds.add(ownerId);
        }
        return taskDTO;
    }

    private IssueDTO generateStory(TaskBoardUnionItem item) {
        IssueDTO storyDTO = new IssueDTO();
        storyDTO.setIssueId(item.getStoryId());
        storyDTO.setTitle(item.getStoryName());
        storyDTO.setDescription(item.getStoryDesc());
        storyDTO.setState(item.getStoryState());
        storyDTO.setPriority(item.getStoryLevel());
        storyDTO.setSprintId(item.getSprintId());
        storyDTO.setModuleId(item.getModuleId());
        storyDTO.setStageId(item.getStoryStageId());
        storyDTO.setCreateTime(item.getStoryCreateTime());
        storyDTO.setChildren(new ArrayList<>());
        return storyDTO;
    }

    /**
     *    maxp
     * @Date 2020/4/30
     * @Description 故事优先级排序
      * @param storyDTOMap
     * @Return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    private List<IssueDTO> prioritySort(Map<Long, IssueDTO> storyDTOMap) {
        List<IssueDTO> storyDTOS = new ArrayList<>(storyDTOMap.values());
        Collections.sort(storyDTOS, new Comparator<IssueDTO>() {
            @Override
            public int compare(IssueDTO storyDTO1, IssueDTO storyDTO2) {
                if (storyDTO1.getPriority() != null && storyDTO2.getPriority() != null) {
                    return storyDTO2.getPriority().compareTo(storyDTO1.getPriority());
                } else {
                    return storyDTO1.getPriority() == null ? 1 : -1;
                }
            }
        });
        return storyDTOS;
    }

    /**
     *    maxp
     * @Date 2020/5/11
     * @Description 按照故事创建时间排序
      * @param storyDTOS
     * @Return java.util.List<com.yusys.agile.issue.dto.IssueDTO>
     */
    private List<IssueDTO> createTimeSort(List<IssueDTO> storyDTOS) {
        Collections.sort(storyDTOS, new Comparator<IssueDTO>() {
            @Override
            public int compare(IssueDTO storyDTO1, IssueDTO storyDTO2) {
                if (storyDTO1.getPriority() == null && storyDTO2.getPriority() == null) {
                    Date dt1 = storyDTO1.getCreateTime();
                    Date dt2 = storyDTO2.getCreateTime();
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
                return 0;
            }
        });
    return storyDTOS;
    }


    /**
     *    maxp
     * @Date 2020/4/30
     * @Description 设置是否超时
     * @param start
     * @param end
     * @param planWorkload
     * @param workHours
     * @Return boolean
     */
    private boolean isTaskOverTime(Date start, Date end, int planWorkload, int workHours) {
        int cost = DateUtil.getCostDays(start, end == null ? new Date(System.currentTimeMillis()) : end);
        return (cost - 1) * workHours >= planWorkload;
    }

    /**
     *    maxp
     * @Date 2020/4/30
     * @Description 删除掉没有任务的故事
      * @param storyDTOMap
     * @Return void
     */
    private void removeEmptyTasksStory(Map<Long, IssueDTO> storyDTOMap) {
        if (null != storyDTOMap) {
            Iterator<Map.Entry<Long, IssueDTO>> it = storyDTOMap.entrySet().iterator();
            while (it.hasNext()) {
                List<IssueDTO> tasks = it.next().getValue().getChildren();
                if (CollectionUtils.isEmpty(tasks)) {
                    it.remove();
                }
            }
        }
    }
}

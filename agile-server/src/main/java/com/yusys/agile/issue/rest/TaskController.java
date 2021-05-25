package com.yusys.agile.issue.rest;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.TaskStageIdEnum;
import com.yusys.agile.issue.enums.TaskTypeEnum;
import com.yusys.agile.issue.service.TaskService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/issue/v3")
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureController.class);

    @Resource
    private TaskService taskService;

    /**
     * 用户故事下新建任务
     * @param issueDTO
     * @return
     */
    @PostMapping("/createTask")
    public ControllerResponse createTask(@RequestBody IssueDTO issueDTO) {
        try {
            if (StringUtils.isEmpty(issueDTO.getTitle())) {
                return ControllerResponse.fail("新选择任务标题");
            }
            if (issueDTO.getTaskType() == null) {
                return ControllerResponse.fail("请选择任务类型");
            }
            return ControllerResponse.success(taskService.createTask(issueDTO));
        } catch (Exception e) {
            LOGGER.error("新增任务失败：{}", e);
            return ControllerResponse.fail("新增任务失败：" + e.getMessage());
        }

    }

    @GetMapping("/queryTask/{taskId}")
    public ControllerResponse queryTask(@PathVariable("taskId") Long taskId, @RequestHeader(name = "projectId") Long projectId) {
        return ControllerResponse.success(taskService.queryTask(taskId));
    }

    /**
     * 用户故事下删除任务
     * @param taskId
     * @param deleteChild
     * @return
     */
    @DeleteMapping("/deleteTask/{taskId}")
    public ControllerResponse deleteTask(@PathVariable("taskId") Long taskId, @RequestParam(name = "deleteChild") Boolean deleteChild) {
        try {
            taskService.deleteTask(taskId, deleteChild);
        } catch (Exception e) {
            LOGGER.error("删除任务失败：{}", e);
            return ControllerResponse.fail("删除任务失败：" + e.getMessage());
        }
        return ControllerResponse.success("删除任务成功！");
    }

    /**
     * 用户故事下修改任务
     * @param
     * @return
     */
    @PostMapping("/editTask")
    public ControllerResponse editTask(@RequestBody IssueDTO issueDTO) {
//        //暂时先将扩展字段扔掉
//        JSONObject jsonObject = new JSONObject(map);
//        IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
        taskService.editTask(issueDTO);
        return ControllerResponse.success("编辑任务成功！");
    }

    @PutMapping("/copyTask/{taskId}")
    public ControllerResponse copyTask(@PathVariable(name = "taskId") Long taskId, @RequestHeader(name = "projectId") Long projectId) {
        try {
            Long newTaskId = taskService.copyTask(taskId, projectId);
            return ControllerResponse.success(newTaskId);
        } catch (Exception e) {
            LOGGER.error("复制任务失败：{}", e);
            return ControllerResponse.fail("复制任务失败：" + e.getMessage());
        }
    }

    @GetMapping("/queryUnlinkedTask")
    public ControllerResponse queryUnlinkedTask(@RequestHeader(name = "projectId") Long projectId, @RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "title", required = false) String title,
                                                @RequestParam(name = "projectId", required = false) Long paramProjectId) {
        Long finalProjectId = null;
        if (null != paramProjectId) {
            finalProjectId = paramProjectId;
        } else {
            finalProjectId = projectId;
        }
        List<IssueDTO> result;
        try {
            result = taskService.queryUnlinkedTask(finalProjectId, pageNum, pageSize, title);
        } catch (Exception e) {
            LOGGER.error("查询未关联的任务异常", e);
            return ControllerResponse.fail("查询未关联的任务异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param issueId
     * @param from
     * @param to
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2021/2/8
     * @Description 任务卡片拖拽
     */
    @GetMapping("/task/stageId/{issueId}/{from}/{to}")
    public ControllerResponse dragTask(@PathVariable Long issueId, @PathVariable Long from, @PathVariable Long to, @RequestParam(value = "assignUserId") Long userId) {
        try {
            taskService.dragTask(issueId, from, to, userId);
        } catch (Exception e) {
            return ControllerResponse.fail("拖拽任务卡片失败! " + e.getMessage());
        }
        return ControllerResponse.success();
    }

    /**
     * @param storyId
     * @Date: 2021/2/9 10:17
     * @Description: 查询故事下所有任务
     * @Param: * @param projectId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/queryTaskForStory")
    public ControllerResponse queryTaskForStory(@RequestHeader(name = "projectId") Long projectId, @RequestParam(value = "storyId") Long storyId) {
        List<IssueDTO> result;
        try {
            result = taskService.queryTaskForStory(projectId, IssueTypeEnum.TYPE_TASK.CODE, storyId);
        } catch (Exception e) {
            LOGGER.error("查询故事下所有任务异常", e);
            return ControllerResponse.fail("查询故事下所有任务异常：" + e.getMessage());
        }
        return ControllerResponse.success(result);
    }

    /**
     * @param storyId
     * @Date: 2021/2/9 10:17
     * @Description: 查询故事下所有缺陷
     * @Param: * @param projectId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/queryFaultForStory")
    public ControllerResponse queryFaultForStory(@RequestHeader(name = "projectId") Long projectId, @RequestParam(value = "storyId") Long storyId) {
        List<IssueDTO> result;
        try {
            result = taskService.queryFaultForStory(projectId, IssueTypeEnum.TYPE_FAULT.CODE, storyId);
        } catch (Exception e) {
            LOGGER.error("查询故事下所有缺陷异常", e);
            return ControllerResponse.fail("查询故事下所有缺陷异常：" + e.getMessage());
        }
        return ControllerResponse.success(result);
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param title
     * @Date: 2021/2/16 11:22
     * @Description: 查询所有任务
     * @Param: * @param projectId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/queryAllTask")
    public ControllerResponse queryAllTask(@RequestHeader(name = "projectId", required = false) Long projectId, @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "title", required = false) String title) {
        List<IssueDTO> result;
        try {
            result = taskService.queryAllTask(projectId, pageNum, pageSize, title);
        } catch (Exception e) {
            LOGGER.error("查询所有的任务异常", e);
            return ControllerResponse.fail("查询所有的任务异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * 获取工作项任务的阶段列表
     *
     * @return
     */
    @GetMapping("/task/stages")
    public ControllerResponse getIssueTaskStages() {
        Map<Long, String> taskAllStageId = TaskStageIdEnum.getTaskAllStageId();
        return ControllerResponse.success(taskAllStageId);
    }

    /**
     * 获取工作项任务类型列表
     *
     * @return
     */
    @GetMapping("/task/types")
    public ControllerResponse getIssueTaskTypes() {
        Map<Integer, String> taskAllStageId = TaskTypeEnum.getTaskAllTypes();
        return ControllerResponse.success(taskAllStageId);
    }

    /**
     * 功能描述:提供cicd接口-根据任务id信息查询上层故事id集合
     *
     * @param taskIds
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/2/8
     */
    @PostMapping("/task/listStoryIdsByTaskIds")
    public ControllerResponse listStoryIdsByTaskIds(@RequestBody List<Long> taskIds) {

        return ControllerResponse.success(taskService.listStoryIdsByTaskIds(taskIds));
    }
}

package com.yusys.agile.issue.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.service.SysExtendFieldDetailService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Decription 系统下用户故事控制类
 * @author hanke
 * @Date 2021-05-24 16:30;00
 */
@RestController
@RequestMapping("/issue/story")
public class StoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureController.class);
    @Resource
    private StoryService storyService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private IssueFactory issueFactory;
    @Resource
    private IssueService issueService;


    @PostMapping("/create")
    public ControllerResponse createStory(@RequestBody Map<String, Object> map) {
        try {
            //暂时先将扩展字段扔掉
            JSONObject jsonObject = new JSONObject(map);
            IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);

            Long issueId = storyService.createStory(issueDTO);
            //批量新增或者批量更新扩展字段值
            issueDTO.setIssueType(new Byte("3"));
            issueDTO.setIssueId(issueId);
            issueFactory.batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issueId);
            return ControllerResponse.success(issueId);
        } catch (Exception e) {
            LOGGER.error("新增用户故事失败：{}", e);
            return ControllerResponse.fail("新增用户故事失败：" + e.getMessage());
        }
    }


    @GetMapping("/getStoryPreInfo")
    public ControllerResponse getStoryPreInfo(@RequestParam("crateType") Integer crateType,
                                              @RequestParam(value = "sprintId",required = false) Long sprintId,
                                              @RequestParam(value = "systemId" ,required = false) Long systemId,
                                              @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize,
                                              @RequestParam(value = "userName",required = false) String userName) {

        return ControllerResponse.success(storyService.getStoryPreInfo(crateType,sprintId,systemId,pageNum,pageSize,userName));
    }

    @GetMapping("/query/{storyId}")
    public ControllerResponse queryStory(@PathVariable("storyId") Long storyId) {
        IssueDTO issueDTO = storyService.queryStory(storyId);
        Map<String, Object> map = Maps.newHashMap();
        if (null != issueDTO) {
            BeanMap beanMap = BeanMap.create(issueDTO);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        issueService.orgIssueExtendFields(storyId,map);
        return ControllerResponse.success(map);
    }


    @DeleteMapping("/delete/{storyId}")
    public ControllerResponse deleteStory(@PathVariable("storyId") Long storyId, Boolean deleteChild,SecurityDTO securityDTO) {
        try {
            storyService.deleteStory(storyId, deleteChild,securityDTO.getUserId());
        } catch (Exception e) {
            LOGGER.error("删除用户故事失败：{}", e);
            return ControllerResponse.fail("删除用户故事失败：" + e.getMessage());
        }
        return ControllerResponse.success("删除用户故事成功！");
    }


    @PostMapping("/edit")
    public ControllerResponse editStory(@RequestBody Map<String, Object> map,SecurityDTO securityDTO) {
        try {
            //保存工作项基础字段
            JSONObject jsonObject = new JSONObject(map);
            IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
            storyService.editStory(issueDTO,securityDTO.getUserId());
            //批量新增或者批量更新扩展字段值
            issueDTO.setIssueType(new Byte("3"));
            issueFactory.batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issueDTO.getIssueId());
        } catch (Exception e) {
            return ControllerResponse.fail(e.getMessage());
        }
        return ControllerResponse.success("编辑用户故事成功！");
    }

    @PutMapping("/copy/{storyId}")
    public ControllerResponse copyStory(@PathVariable(name = "storyId") Long storyId) {
        try {
            Long newStoryId = storyService.copyStory(storyId);
            return ControllerResponse.success(newStoryId);
        } catch (Exception e) {
            LOGGER.error("复制故事失败：{}", e);
            return ControllerResponse.fail("复制故事失败：" + e.getMessage());
        }

    }

    /**
     * @param pageNum
     * @param pageSize
     * @param title
     * @Date: 2021/2/9 9:44
     * @Description: 查询未关联业务需求的研发需求
     * @Param: * @param projectId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/query/unlinked/stories")
    public ControllerResponse queryUnlinkedStory(@RequestHeader(name = "projectId",required = false) Long projectId, @RequestParam("pageNum") Integer pageNum,
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
            result = storyService.queryUnlinkedStory(finalProjectId, pageNum, pageSize, title);
        } catch (Exception e) {
            LOGGER.error("查询未关联的用户故事异常", e);
            return ControllerResponse.fail("查询未关联的用户故事异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param issueDTO
     * @Date 2021/05/24
     * @Description 团队管理-进入迭代管理-看板
     * 通过迭代id、故事id/故事名称、任务ID/任务名称、任务状态、任务类型、人员,获取故事以及故事下的任务信息
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/list/stories/union/tasks")
    public ControllerResponse listStoriesAndTasks(@RequestBody IssueDTO issueDTO) {
        List<IssueDTO> result = storyService.listStorysAndTasks(issueDTO);
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param issueDTO
     * @param projectId
     * @Date 2021/2/27
     * @Description 看板编辑故事状态和任务卡片阻塞状态
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/edit/storyOrTask")
    public ControllerResponse editStoryOrTask(@RequestBody IssueDTO issueDTO, @RequestHeader(name = "projectId",required = false) Long projectId) {
        int i = storyService.editStoryOrTask(issueDTO, projectId);
        if (i == 0) {
            return ControllerResponse.fail("编辑工作项失败！");
        }
        return ControllerResponse.success("编辑工作项成功！");
    }

    /**
     * @param issueDTO
     * @Date 2021/2/1
     * @Description 模糊分页查询迭代评审获取故事及故事验收标准信息
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/listStoryAcceptance")
    public ControllerResponse listStoryAcceptance(@RequestBody IssueDTO issueDTO) {
        List<IssueDTO> result = storyService.listStoryAcceptance(issueDTO, issueDTO.getPageNum(), issueDTO.getPageSize());
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param issueDTO
     * @Date 2021/2/1
     * @Description 编辑故事评审状态及备注
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/edit/storyAssess")
    public ControllerResponse editStoryAssess(@RequestBody IssueDTO issueDTO) {
        Preconditions.checkArgument(issueDTO.getAssessRemarks().length() <= 100, "评审备注字段过长，不能超过100！");
        int i = storyService.editStoryAssess(issueDTO);
        if (i == 0) {
            return ControllerResponse.fail("编辑故事验收标准失败!");
        }
        return ControllerResponse.success("编辑故事验收标准成功!");
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param title
     * @Date: 2021/2/9 9:44
     * @Description: 查询所有符合条件的研发需求
     * @Param: * @param projectId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/query/all")
    public ControllerResponse queryAllStory(@RequestHeader(name = "projectId", required = false) Long projectId, @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "title", required = false) String title,
                                            @RequestParam(name = "projectId", required = false) Long paramProjectId) {
        Long finalProjectId = null;
        if (null != paramProjectId) {
            finalProjectId = paramProjectId;
        } else {
            finalProjectId = projectId;
        }
        List<IssueDTO> result;
        try {
            result = storyService.queryAllStory(finalProjectId, pageNum, pageSize, title);
        } catch (Exception e) {
            LOGGER.error("查询所有的用户故事异常", e);
            return ControllerResponse.fail("查询所有的用户故事异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param epicId
     * @Date: 2021/2/9 9:47
     * @Description: 查询业务需求下的所有用户故事
     * @Param: * @param projectId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/query/story/for/epic")
    public ControllerResponse queryStoryForEpic(@RequestHeader(name = "projectId",required = false) Long projectId, @RequestParam(value = "epicId") Long epicId) {
        List<IssueDTO> result;
        try {
            result = storyService.queryStoryForEpic(projectId, epicId);
        } catch (Exception e) {
            LOGGER.error("查询业务需求下的所有用户故事异常", e);
            return ControllerResponse.fail("查询业务需求下的所有用户故事异常：" + e.getMessage());
        }
        return ControllerResponse.success(result);
    }

    /**
     * @param featureId
     * @Date: 2021/2/9 9:47
     * @Description: 查询研发需求下的所有用户故事
     * @Param: * @param projectId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/query/story/for/feature")
    public ControllerResponse queryStoryForFeature(@RequestHeader(name = "projectId",required = false) Long projectId, @RequestParam(value = "featureId") Long featureId) {
        List<IssueDTO> result;
        try {
            result = storyService.queryStoryForFeature(projectId, featureId);
        } catch (Exception e) {
            LOGGER.error("查询研发需求下的所有用户故事异常", e);
            return ControllerResponse.fail("查询研发需求下的所有用户故事异常：" + e.getMessage());
        }
        return ControllerResponse.success(result);
    }

    /**
     * @param sprintId
     * @Date 2020/8/7
     * @Description 获取迭代中未完成故事
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/unfinished/{sprintId}")
    public ControllerResponse getUnfinishedStoryList(@PathVariable Long sprintId) {
        return ControllerResponse.success(storyService.getUnfinishedStoryList(sprintId));
    }


    /**
     * 功能描述:提供cicd接口：查项目下所有未完成故事
     *
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/8/19
     */
    @GetMapping("/list/unfinished/story")
    public ControllerResponse listUnFinisherStorysByProjectId(@RequestParam(value = "projectId",required = false) Long projectId,
                                                              @RequestParam(value = "name", required = false) String name,
                                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<IssueDTO> result;
        try {
            result = storyService.listUnFinisherStorysByProjectId(projectId, name, pageNum, pageSize);
        } catch (Exception e) {
            LOGGER.error("查项目下所有未完成故事", e);
            return ControllerResponse.fail("查项目下所有未完成故事异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param sprintId
     * @param pageNum
     * @param pageSize
     * @return
     * @description 提供cicd接口：查询迭代下未完成的用户故事
     */
    @GetMapping("/getUnfinishedStorysBySprintId")
    public ControllerResponse getUnfinishedStorysBySprintId(@RequestParam("sprintId") Long sprintId,
                                                            @RequestParam(name = "pageNum") Integer pageNum,
                                                            @RequestParam(name = "pageSize") Integer pageSize) {
        try {
            List<IssueDTO> result = storyService.getUnfinishedStorysBySprintId(sprintId, pageNum, pageSize);
            return ControllerResponse.success(new PageInfo<>(result));
        } catch (Exception e) {
            LOGGER.error("查询迭代下未完成的用户故事异常,异常信息:{}", e.getMessage());
            return ControllerResponse.fail("查询迭代下未完成的用户故事异常");
        }
    }

    /**
     * 功能描述  查询故事下的开发负责人
     *
     * @param storyId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/10/26
     */
    @GetMapping("/getDevlopManager/{storyId}")
    public ControllerResponse getDevlopManager(@RequestParam("storyId") Long storyId) {
        try {
            Map<Long, String> mapDevlopManager = storyService.getDevlopManager(storyId);
            return ControllerResponse.success(mapDevlopManager);
        } catch (Exception e) {
            LOGGER.error("查询故事下开发负责人异常,异常信息:{}", e.getMessage());
            return ControllerResponse.fail("查询故事下开发负责人异常");
        }
    }

    /**
     *功能描述 根据系统查询故事
     * @author shenfeng
     * @date 2021/6/1
      * @param systemId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/queryStoryBySystemId")
    public ControllerResponse queryStoryBySystemId(@RequestParam(name = "systemId") Long systemId,
                                                   @RequestParam(name = "storyName", required = false) String storyName,
                                                   @RequestParam(name = "pageNum") Integer pageNum,
                                                   @RequestParam(name = "pageSize") Integer pageSize) {
        List<IssueDTO> result;
        try {
            result = storyService.queryStoryBySystemId(systemId,storyName,pageNum,pageSize);
        } catch (Exception e) {
            LOGGER.error("根据系统查询故事异常", e);
            return ControllerResponse.fail("根据系统查询故事异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }
}

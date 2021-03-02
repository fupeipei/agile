package com.yusys.agile.sprint.rest;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.yusys.agile.fault.service.FaultService;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.service.SprintService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.common.enums.ResposeCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020/4/10
 */
@RestController
@RequestMapping("/sprint")
public class SprintController {
    private static final Logger LOG = LoggerFactory.getLogger(SprintController.class);
    @Autowired
    private SprintService sprintService;
    @Autowired
    private StoryService storyService;
    @Autowired
    private FaultService faultService;

    /**
     * @param sprintDTO
     * @param projectId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2020/4/10
     * @Description新增迭代
     */
    @PostMapping("/insertSprint")
    public ControllerResponse createSprint(@RequestBody SprintDTO sprintDTO, @RequestHeader(name = "projectId") Long projectId) {
        checkParameter(sprintDTO);

        Long sprintId = sprintService.createSprint(sprintDTO, projectId);
        if (null == sprintId) {
            return ControllerResponse.fail("新建失败！");
        }
        return ControllerResponse.success(sprintId);
    }

    /**
     * @param sprintDTO
     * @Date 2021/2/2
     * @Description 对传来的参数做判断
     * @Return void
     */
    private void checkParameter(@RequestBody SprintDTO sprintDTO) {
        String str1 = "迭代名称过长,不能大于100!";
        String str2 = "团队名称过长，不能大于100!";
        String str3 = "请选择团队";
        String str4 = "工作时间超长，不能大于24小时!";
        Preconditions.checkArgument(sprintDTO.getSprintName().length() <= 100, str1);
        Preconditions.checkArgument(sprintDTO.getTeamName().length() <= 100, str2);
        Preconditions.checkArgument(sprintDTO.getTeamId() != null || sprintDTO.getTeamName() != null, str3);
        Preconditions.checkArgument(sprintDTO.getWorkHours().intValue() <= 24, str4);
    }

    /**
     * @param projectId
     * @param sprintId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2020/4/10
     * @Description查看迭代编辑页面
     */
    @GetMapping("/getSprint/{sprintId}")
    public ControllerResponse viewEdit(@PathVariable Long sprintId, @RequestHeader(name = "projectId") Long projectId) {
        return ControllerResponse.success(sprintService.viewEdit(sprintId, projectId));
    }

    /**
     * @param projectId
     * @param sprintDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2020/4/10
     * @Description编辑迭代详情
     */
    @PostMapping("/updateSprint")
    public ControllerResponse editSprint(@RequestBody SprintDTO sprintDTO, @RequestHeader(name = "projectId") Long projectId) {
        sprintDTO.setProjectId(projectId);
        if (!sprintService.canEdit(sprintDTO.getSprintId())) {
            return ControllerResponse.fail("迭代已结束，禁止编辑!");
        }
        checkParameter(sprintDTO);
        int i = sprintService.editSprint(sprintDTO, projectId);
        if (i == 0) {
            return ControllerResponse.fail("更新失败!");
        }
        return ControllerResponse.success("更新成功!");
    }

    /**
     * @param teamId
     * @param sprintName
     * @param pageNum
     * @param pageSize
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2020/4/13
     * @Description 通过团队id获取迭代信息以及通过迭代名称查询
     */
    @GetMapping("/listSprint/{teamId}")
    public ControllerResponse getSprintByTeamId(@PathVariable Long teamId, @RequestParam("sprintName") String sprintName,
                                                @RequestParam(name = "pageNum") Integer pageNum,
                                                @RequestParam(name = "pageSize") Integer pageSize,
                                                @RequestHeader(name = "projectId") Long projectId) {
        List<SprintDTO> result = sprintService.getSprintByTeamId(teamId, sprintName, pageNum, pageSize, projectId);
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param sprintId
     * @param projectId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2021/4/16
     * @Description 删除迭代信息
     */
    @DeleteMapping("/deleteSprint/{sprintId}")
    public ControllerResponse deleteSprint(@PathVariable Long sprintId, @RequestHeader(name = "projectId") Long projectId) {
        if (sprintService.deleteSprint(sprintId, projectId)) {
            return ControllerResponse.success("删除迭代信息成功!");
        }

        return ControllerResponse.fail("删除迭代信息失败! ");
    }

    /**
     * @param sprintId
     * @param issueId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2020/3/2 17:43
     * @Description 通过迭代id和故事id将故事移出迭代
     */
    @PutMapping("/issues/{sprintId}/{issueId}")
    public ControllerResponse removeIssue4Sprint(@PathVariable Long sprintId, @PathVariable Long issueId) {
        if (storyService.removeStory4Sprint(sprintId, issueId) != 1) {
            return ControllerResponse.fail("移除迭代失败！");
        }
        return ControllerResponse.success("工作项移除成功！");
    }

    /**
     * @param sprintDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2020/4/17 21:08
     * @Description 迭代添加工作项（故事或缺陷）
     */
    @PostMapping("/relation/issue")
    public ControllerResponse arrangeIssue(@RequestBody SprintDTO sprintDTO, @RequestHeader(name = "projectId") Long projectId) {
        sprintDTO.setProjectId(projectId);
        if (sprintService.arrangeIssue(sprintDTO)) {
            return ControllerResponse.success("关联成功！");
        }
        return ControllerResponse.fail("关联失败！");
    }

    /**
     * @param filter
     * @param pageNum
     * @param pageSize
     * @param projectId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2020/4/28
     * @Description 根据迭代id或迭代名称分页查询迭代列表
     */
    @GetMapping("/listUnFinishedSprint")
    public ControllerResponse queryUnFinishedByProjectId(@RequestParam(name = "filter", required = false) String filter,
                                                         @RequestParam(name = "pageNum") Integer pageNum,
                                                         @RequestParam(name = "pageSize") Integer pageSize,
                                                         @RequestHeader(name = "projectId") Long projectId,
                                                         @RequestParam(name = "projectId", required = false) Long paramProjectId) {
        Long sprintProjectId = null;
        if (null != paramProjectId) {
            sprintProjectId = paramProjectId;
        } else {
            sprintProjectId = projectId;
        }
        List<SprintDTO> result = sprintService.queryUnFinishedByProjectId(filter, sprintProjectId, pageNum, pageSize);
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param sprintDTO
     * @param projectId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2021/2/7
     * @Description 获取项目中所有迭代
     */
    @PostMapping("/listSprint")
    public ControllerResponse viewSprints(@RequestBody SprintDTO sprintDTO, @RequestHeader(name = "projectId") Long projectId) {
        List<SprintDTO> result = sprintService.viewSprints(sprintDTO.getSprintName(), sprintDTO.getSprintType(), sprintDTO.getVersionNumber(),
                projectId, sprintDTO.getPageNum(), sprintDTO.getPageSize());
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param projectId
     * @param sprintId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2021/2/12
     * @Description 根据迭代id获取迭代中人员信息
     */
    @GetMapping("/listUsers/{sprintId}")
    public ControllerResponse listUsersBySprintId(@RequestHeader(name = "projectId") Long projectId, @PathVariable Long sprintId,
                                                  @RequestParam(name = "projectId", required = false) Long paramProjectId) {
        Long finalProjectId;
        if (null != paramProjectId) {
            finalProjectId = paramProjectId;
        } else {
            finalProjectId = projectId;
        }
        return ControllerResponse.success(sprintService.listUsersBySprintId(finalProjectId, sprintId));
    }

    /**
     * @param projectId
     * @param sprintId  编辑迭代为已完成状态
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2021/2/9
     * @Description
     */
    @PutMapping("/editComplete/{sprintId}")
    public ControllerResponse completeSprint(@RequestHeader(name = "projectId") Long projectId, @PathVariable Long sprintId) {
        int i = sprintService.completeSprint(projectId, sprintId);
        if (i > 0) {
            return ControllerResponse.success("迭代已完成！");
        } else if (i == -1) {
            Map<String, List> map = new HashMap(2);
            List<IssueDTO> unfinishedStoryList = storyService.getUnfinishedStoryList(sprintId);
            map.put("unfinishedStoryList", unfinishedStoryList);
            List<SprintDTO> notStartedSprintList = sprintService.getNotStartedSprint(projectId);
            map.put("notStartedSprintList", notStartedSprintList);
            List<IssueDTO> unfinishedFaultList = faultService.getUnBindStoryAndUnFinishedFaultList(projectId, sprintId);
            map.put("unfinishedFaultList", unfinishedFaultList);
            return ControllerResponse.success(ResposeCodeEnum.HAS_UNFINISHED_STORY, map);
        } else {
            return ControllerResponse.fail(ResposeCodeEnum.FAILURE, "当前项目与当前迭代项目不一致！");
        }
    }

    /**
     * @param
     * @Date 2021/2/18
     * @Description 根据现在时间，把所有迭代未开始状态改为进行中
     * @Return void
     */
    @PutMapping("/changeStatusDaily")
    public void changeStatusDaily() {
        LOG.info("sprints progress start time:{}", new Date());
        sprintService.changeStatusDaily();
    }

    /**
     * @param projectId
     * @param sprintId
     * @param pageNum
     * @param pageSize
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2021/2/16
     * @Description 获取迭代中人员代码提交次数
     */
    @GetMapping("/userInfo/{sprintId}")
    public ControllerResponse sprintUserInfo(@RequestHeader(name = "projectId") Long projectId, @PathVariable Long sprintId,
                                             Integer pageNum, Integer pageSize) {
        return ControllerResponse.success(sprintService.sprintUserInfo(projectId, sprintId, pageNum, pageSize));
    }

    /**
     * @param projectId
     * @return
     * @Date 2020/8/7
     * @Description 获取项目下未开始的迭代
     */
    @GetMapping("/getNotStartedSprint")
    public ControllerResponse getNotStartedSprint(@RequestHeader(name = "projectId") Long projectId) {
        return ControllerResponse.success(sprintService.getNotStartedSprint(projectId));
    }

    /**
     * @param sprintDTO
     * @param projectId
     * @param oldSprintId
     * @param sprintId
     * @return
     * @Date 2020/8/10
     * @Description 迭代完成-未完成故事转移到下一个迭代（连带下面的任务和缺陷）
     */
    @PostMapping("/distributeStorys/{sprintId}/{oldSprintId}")
    public ControllerResponse distributeStorys(@RequestBody SprintDTO sprintDTO, @RequestHeader(name = "projectId") Long projectId, @PathVariable Long sprintId, @PathVariable Long oldSprintId) {
        sprintService.distributeStorys(sprintDTO.getListStorys(), projectId, oldSprintId, sprintId);
        return ControllerResponse.success("迭代完成-未完成故事转移到下一个迭代成功");
    }

    /**
     * 功能描述: 提供cicd接口：根据项目id查询下面未完成的迭代
     *
     * @param projectId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/8/19
     */
    @GetMapping("/listUnFinisherSprintsByProjectId")
    public ControllerResponse listSprintsByProjectId(@RequestParam("projectId") Long projectId,
                                                     @RequestParam(name = "name", required = false) String name,
                                                     @RequestParam(name = "pageNum", required = false) Integer pageNum,
                                                     @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        List<SprintDTO> list = sprintService.listUnFinisherSprintsByProjectId(projectId, name, pageNum, pageSize);
        return ControllerResponse.success(new PageInfo<>(list));
    }

    /**
     * 功能描述: 提供cicd接口：根据项目id查询项目下面所有的迭代
     *
     * @param projectId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/3/2
     */
    @GetMapping("/listAllSprintsByProjectId")
    public ControllerResponse listAllSprintsByProjectId(@RequestParam("projectId") Long projectId,
                                                        @RequestParam(name = "name", required = false) String name) {
        List<SprintDTO> list = sprintService.listAllSprintsByProjectId(projectId, name);
        return ControllerResponse.success(new PageInfo<>(list));
    }
}

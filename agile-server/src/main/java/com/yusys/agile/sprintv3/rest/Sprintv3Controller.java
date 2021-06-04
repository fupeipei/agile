package com.yusys.agile.sprintv3.rest;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintV3.dto.SprintV3UserHourDTO;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.dto.SsoSystemDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 14:50
 */
@Api(tags = "迭代管理")
@RestController
@RequestMapping("/v3/sprint")
public class Sprintv3Controller {

    @Autowired
    private Sprintv3Service sprintv3Service;

    @Autowired
    private StoryService storyService;

    /**
     * @param sprintId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @Date 2021/5/10
     * @Description查看迭代编辑页面
     */
    @ApiOperation(value = "查看迭代详情")
    @GetMapping("/getSprint")
    public ControllerResponse viewEdit(Long sprintId) {
        return ControllerResponse.success(sprintv3Service.viewEdit(sprintId));
    }

    /**
     * 团队进入迭代-按团队id查询有效迭代
     *
     * @param teamId
     * @author zhaofeng
     * @date 2021/5/24 11:50
     */
    @GetMapping("/teamInSprint/{teamId}/{pageNum}/{pageSize}")
    public ControllerResponse teamInSprint(@PathVariable("teamId") Long teamId, @PathVariable("pageSize") Integer pageSize, @PathVariable("pageNum") Integer pageNum, @RequestParam("sprint") String sprint) {
        List<SprintListDTO> list = sprintv3Service.teamInSprint(teamId, pageSize, pageNum, sprint);
        return ControllerResponse.success(new PageInfo<SprintListDTO>(list));
    }

    /**
     * 条件-分页查询迭代列表
     *
     * @param dto
     * @param security
     * @author zhaofeng
     * @date 2021/5/11 15:56
     */
    @PostMapping("/listSprint")
    public ControllerResponse listSprint(@RequestBody SprintQueryDTO dto, SecurityDTO security) {
        List<SprintListDTO> list = sprintv3Service.listSprint(dto, security);
        return ControllerResponse.success(new PageInfo<SprintListDTO>(list));
    }

    /**
     * 新建迭代
     *
     * @param sprintV3DTO 迭代v3dto
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @ApiOperation(value = "新建迭代")
    @PostMapping("/createSprint")
    public ControllerResponse createSprint(@RequestBody SprintV3DTO sprintV3DTO, @RequestHeader("tenantCode") String tenantCode) {
        sprintV3DTO.setTenantCode(tenantCode);
        return ControllerResponse.success(sprintv3Service.createSprint(sprintV3DTO));
    }

    @ApiOperation(value = "编辑迭代")
    @PostMapping("/updateSprint")
    public ControllerResponse updateSprint(@RequestBody SprintV3DTO sprintDTO, SecurityDTO securityDTO) {
        try {
            sprintv3Service.updateSprint(sprintDTO, securityDTO);
        } catch (Exception e) {
            return ControllerResponse.fail("编辑迭代失败：" + e.getMessage());
        }
        return ControllerResponse.success("编辑成功");
    }

    /**
     * 取消迭代
     *
     * @param sprintId 迭代id
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @ApiOperation("取消迭代")
    @GetMapping("/cancelSprint")
    public ControllerResponse cancelSprint(long sprintId, long userId) {
        return ControllerResponse.success(sprintv3Service.cancelSprint(sprintId, userId));
    }

    /**
     * 迭代完成
     *
     * @param sprintId 迭代id
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @ApiOperation(value = "迭代完成")
    @GetMapping("/sprintFinish")
    public ControllerResponse sprintFinish(long sprintId) {
        return ControllerResponse.success(sprintv3Service.sprintFinish(sprintId));
    }

    /**
     * 迭代视图 - 迭代详情
     *
     * @param sprintId 迭代id
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @ApiOperation(value = "迭代视图 - 迭代详情")
    @GetMapping("/sprintOverView")
    public ControllerResponse sprintOverView(long sprintId) {
        return ControllerResponse.success(sprintv3Service.sprintOverView(sprintId));
    }

    /**
     * 迭代视图 - 迭代统计详情
     *
     * @param sprintId 迭代id
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @ApiOperation(value = "迭代视图 - 迭代统计详情")
    @GetMapping("/SprintStatisticalInformation")
    public ControllerResponse sprintStatisticalInformation(long sprintId) {
        return ControllerResponse.success(sprintv3Service.sprintStatisticalInformation(sprintId));
    }

    /**
     * 迭代视图 - 成员工时
     *
     * @param sprintId 迭代id
     * @return {@link ControllerResponse}
     * @author 张宇
     */
    @ApiOperation(value = "迭代视图 - 成员工时")
    @GetMapping("/sprintMembersWorkHours")
    public ControllerResponse sprintMembersWorkHours(long sprintId) {
        return ControllerResponse.success(sprintv3Service.sprintMembersWorkHours(sprintId));
    }

    /**
     * 迭代添加工作项（故事或缺陷）
     *
     * @param sprintDTO 迭代dto
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @ApiOperation(value = "关联迭代")
    @PostMapping("/relation/issue")
    public ControllerResponse arrangeIssue(@RequestBody SprintDTO sprintDTO) {
        if (sprintv3Service.arrangeIssue(sprintDTO)) {
            return ControllerResponse.success("关联成功！");
        }
        return ControllerResponse.fail("关联失败！");
    }

    /**
     * 通过迭代id和故事id将故事移出迭代
     *
     * @param sprintId 迭代id
     * @param issueId  工作项id
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @ApiOperation(value = "迭代移除用户故事")
    @PutMapping("/issues/{sprintId}/{issueId}")
    public ControllerResponse removeIssue4Sprint(@PathVariable Long sprintId, @PathVariable Long issueId) {
        if (storyService.removeStory4Sprint(sprintId, issueId) != 1) {
            return ControllerResponse.fail("移除迭代失败！");
        }
        return ControllerResponse.success("工作项移除成功！");
    }

    /**
     * 通过迭代ID查询迭代下的人员
     *
     * @param sprintId 迭代id
     * @return
     */
    @GetMapping("/getUsersBySprintId/{sprintId}")
    public ControllerResponse getUsersBySprintId(@PathVariable Long sprintId) {
        List<SprintV3UserHourDTO> userSprintHourDTOList = sprintv3Service.getUsersBySprintId(sprintId);
        return ControllerResponse.success(userSprintHourDTOList);
    }

    /**
     * 查询系统下未迭代的故事
     *
     * @param title
     * @param systemId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询系统下未迭代的故事")
    @GetMapping("/queryNotRelationStorys")
    public ControllerResponse queryNotRelationStorys(@RequestParam(name = "title", required = false) String title,
                                                     @RequestParam(name = "teamId") Long teamId,
                                                     @RequestParam(name = "systemId", required = false) List<Long> systemId,
                                                     @RequestParam(name = "pageNum") Integer pageNum,
                                                     @RequestParam(name = "pageSize") Integer pageSize) {

        List<IssueDTO> result = sprintv3Service.queryNotRelationStorys(title, teamId, systemId, pageNum, pageSize);
        return ControllerResponse.success(new PageInfo(result));

    }


    /**
     * 模糊分页查询迭代下人员
     *
     * @param userName
     * @param sprintId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/querySprintVagueUser")
    public ControllerResponse querySprintVagueUser(@RequestParam(name = "userName", required = false) String userName,
                                                   @RequestParam(name = "sprintId") Long sprintId,
                                                   @RequestParam(name = "pageNum") Integer pageNum,
                                                   @RequestParam(name = "pageSize") Integer pageSize) {

        List<SsoUserDTO>  result = sprintv3Service.querySprintVagueUser(sprintId, userName, pageNum, pageSize);
        return ControllerResponse.success(result);

    }


    /**
     * 查询系统下所有未开始进行中的迭代信息
     *
     * @param systemId
     * @return
     */
    @GetMapping("/getEffectiveSprints")
    public ControllerResponse getEffectiveSprints(@RequestParam(name = "systemId") Long systemId) {

        List<SprintListDTO> result = sprintv3Service.getEffectiveSprintsBySystemId(systemId);
        return ControllerResponse.success(new PageInfo<>(result));

    }


    /**
     * 查询迭代关联的系统
     *
     * @param sprintId
     * @return
     */
    @GetMapping("/querySystemBySprint")
    public ControllerResponse querySystemBySprint(@RequestParam(name = "sprintId") Long sprintId) {
        List<SsoSystemDTO> result = sprintv3Service.querySystemBySprint(sprintId);
        return ControllerResponse.success(new PageInfo<>(result));

    }

    /**
     * 功能描述
     *
     * @param systemId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @author shenfeng
     * @date 2021/6/1
     */
    @GetMapping("/querySprintBySystemId")
    public ControllerResponse querySprintBySystemId(@RequestParam(name = "systemId") Long systemId) {
        List<SprintListDTO> result = sprintv3Service.querySprintBySystemId(systemId);
        return ControllerResponse.success(result);

    }


}


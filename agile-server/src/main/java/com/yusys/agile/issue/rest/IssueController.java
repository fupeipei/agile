package com.yusys.agile.issue.rest;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueListDTO;
import com.yusys.agile.issue.dto.IssueStageIdCountDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.servicemanager.dto.ServiceManageExceptionDTO;
import com.yusys.agile.servicemanager.dto.ServiceManageIssueDTO;
import com.yusys.agile.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * :
 *
 * @Date: 2020/4/16
 * @Description:
 */
@RestController
public class IssueController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueController.class);
    @Resource
    private IssueService issueService;

    @Resource
    private StoryService storyService;

    /**
     * 功能描述  初始化Issue列表
     *
     * @param map
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/21
     */
    @PostMapping("/issueList/query")
    public ControllerResponse getIssueList(@RequestBody Map<String, Object> map, @RequestHeader(name = "systemId") Long systemId) {
        PageInfo result;
        try {
        if(systemId!=null){
            map.put("systemId",systemId);
        }
            result = issueService.getIssueList(map);
        } catch (Exception e) {
            LOGGER.error("查询Issue异常", e);
            return ControllerResponse.fail("查询Issue异常：" + e.getMessage());
        }
        return ControllerResponse.success(result);
    }

    /**
     * 创建父级关联关系
     * @param parentId 父级id
     * @param issueId 工作项id
     * @return
     */
    @PutMapping("/issue/createRelation/{parentId}/{issueId}")
    public ControllerResponse createRelation(@PathVariable("parentId") Long parentId, @PathVariable("issueId") Long issueId) {
        try {
            issueService.createRelation(parentId, issueId);
        } catch (Exception e) {
            LOGGER.error("建立关联失败：{}", e);
            return ControllerResponse.fail("建立关联失败：" + e.getMessage());
        }

        return ControllerResponse.success("建立关联成功！");
    }

    /**
     * 取消父级关联关系
     * @param parentId 父级id
     * @param issueId 工作项id
     * @return
     */
    @PutMapping("/issue/deleteRelation/{parentId}/{issueId}")
    public ControllerResponse deleteRelation(@PathVariable("parentId") Long parentId, @PathVariable("issueId") Long issueId) {
        try {
            issueService.deleteRelation(parentId, issueId);
        } catch (Exception e) {
            LOGGER.error("删除关联失败：{}", e);
            return ControllerResponse.fail("删除关联失败：" + e.getMessage());
        }

        return ControllerResponse.success("删除关联成功！");
    }

    /**
     * 功能描述  查询当前Issue
     *
     * @param issueId
     * @param issueQuery 1:不查询子，2：查询子
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/21
     */
    @GetMapping("/issue/{issueId}")
    public ControllerResponse getIssue(@PathVariable(name = "issueId") Long issueId, Byte issueQuery) {
        IssueListDTO issueListDTO;
        try {
            issueListDTO = issueService.getIssue(issueId, issueQuery, null);
        } catch (Exception e) {
            LOGGER.error("查询异常：{}", e);
            return ControllerResponse.fail("查询异常：" + e.getMessage());
        }
        return ControllerResponse.success(issueListDTO);
    }


    /**
     * 根据issueId查询当前Issue
     * @param issueId
     * @param systemId
     * @return
     */
    @GetMapping("/issue/getIssueByIssueId/{issueId}")
    public ControllerResponse getIssueByIssueId(@PathVariable(name = "issueId") String issueId, @RequestHeader(name = "systemId",required = false) Long systemId) {
        Map map = new HashMap<>();
        boolean b = StringUtil.isNumeric(issueId);
        if (b) {
            try {
                map = issueService.getIssueByIssueId(Long.parseLong(issueId), systemId);
            } catch (Exception e) {
                LOGGER.error("查询异常：{}", e);
                return ControllerResponse.fail("查询异常：" + e.getMessage());
            }
        }
        return ControllerResponse.success(map);
    }

    /**
     * 功能描述 添加、取消Issue的收藏
     *
     * @param issueId
     * @param isCollect 0：非收藏 1：收藏
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/22
     */
    @GetMapping("/issue/isCollect/{issueId}/{isCollect}")
    public ControllerResponse isCollect(@PathVariable(name = "issueId") Long issueId, @PathVariable(name = "isCollect") Byte isCollect, SecurityDTO securityDTO) {
        try {
            issueService.isCollect(issueId, isCollect, securityDTO);
        } catch (Exception e) {
            LOGGER.error(" 添加、取消Issue的收藏异常：{}", e);
            return ControllerResponse.fail("添加、取消Issue的收藏异常：" + e.getMessage());
        }
        return ControllerResponse.success("操作成功");
    }


    /**
     *  批量建立关联关系
     * @param issueDTO  issueDTO
     * @param securityDTO securityDTO
     * @return 返回成功失败
     */
    @PostMapping("/issue/createBatchRelation")
    public ControllerResponse createBatchRelation(@RequestBody IssueDTO issueDTO, SecurityDTO securityDTO) {
        try {
            storyService.checkSprintParam(issueDTO.getSprintId());
            issueService.createBatchRelation(issueDTO.getParentId(), issueDTO.getListIssueIds(), securityDTO.getUserId());
        } catch (Exception e) {
            LOGGER.error("批量建立关联失败：{}", e);
            return ControllerResponse.fail("批量建立关联失败：" + e.getMessage());
        }
        return ControllerResponse.success("批量建立关联成功！");
    }

    /**
     * 功能描述 根据Id查询所有的Issue以及其children
     *
     * @param rootIds
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/29
     */
    @PostMapping("/issueListByIds")
    public ControllerResponse issueListByIds(@RequestBody JSONObject rootIds, @RequestHeader(name = "projectId",required = false) Long projectId) {
        List<IssueListDTO> result;
        try {
            result = issueService.issueListByIds(rootIds.getString("rootIds"), projectId);
        } catch (Exception e) {
            LOGGER.error("查询Issue异常", e);
            return ControllerResponse.fail("查询Issue异常：" + e.getMessage());
        }
        return ControllerResponse.success(result);
    }

    /**
     * @Date: 10:49
     * @Description: 批量创建工作项
     * @Param: * @param listIssue
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @PostMapping("/issue/createBatchIssue")
    public ControllerResponse createBatchIssue(@RequestParam String listIssue) {
        try {
            issueService.createBatchIssue(listIssue);
        } catch (Exception e) {
            LOGGER.error("批量建立工作项失败：{}", e);
            return ControllerResponse.fail("批量建立工作项失败：" + e.getMessage());
        }
        return ControllerResponse.success("批量建立工作项成功！");
    }

    /**
     * @param handler
     * @Date: 2021/2/26 16:28
     * @Description: 更新处理人
     * @Param: * @param issueId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @PutMapping("/issue/updateHandler/{issueId}")
    public ControllerResponse updateHandler(@PathVariable(name = "issueId") Long issueId, Long handler) {
        try {
            issueService.updateHandler(issueId, handler);
        } catch (Exception e) {
            LOGGER.error(" 更新处理人异常：{}", e);
            return ControllerResponse.fail("更新处理人异常：" + e.getMessage());
        }
        return ControllerResponse.success("更新处理人成功");
    }

    /**
     * @param issueId
     * @param pageNum  分页页数
     * @param pageSize 分页条数
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * :
     * @Date:2021/2/1 11:05
     * @Description:根据issueId，查询操作历史分页数据
     */
    @GetMapping("/issue/query/records")
    public ControllerResponse recordHistories(@RequestParam("issueId") Long issueId,
                                              @RequestParam(name = "pageNum") Integer pageNum,
                                              @RequestParam(name = "pageSize") Integer pageSize,
                                              SecurityDTO securityDTO) {
        return issueService.recordHistories(issueId, pageNum, pageSize, securityDTO);
    }

    /**
     * @param recordId 历史操作主键ID
     * @return :
     * @Date:2021/2/2 11:05
     * @Description:根据recordId，查询富文本内容
     */
    @GetMapping("/issue/history/richText")
    public ControllerResponse getRecordRichText(@RequestParam("recordId") Long recordId) {
        return issueService.getRecordRichText(recordId);
    }

    /**
     * @Date: 2021/2/3 10:15
     * @Description: 详情显示工作项关联关系列表
     * @Param: * @param issueId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/issue/detail/listRelation/{issueId}/{issueType}")
    public ControllerResponse listRelation(@PathVariable("issueId") Long issueId, @PathVariable("issueType") Byte issueType) {
        return ControllerResponse.success(issueService.listRelation(issueId, issueType));
    }

    /**
     * @param projectId
     * @Date 2021/2/22
     * @Description 项目概览页面统计各个阶段的需求个数
     * 看这个产品是否关联了故事，关联了故事就统计故事数，没有故事就统计关联的研发需求数，
     * 没有研发需求就统计关联的业务需求数，都没有关联该产品就为0了，最子到故事
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/issue/stage/count")
    public ControllerResponse countIssueByStageId(@RequestHeader(name = "projectId",required = false) Long projectId, Integer pageNum, Integer pageSize) {
        return ControllerResponse.success(issueService.countIssueByStageId(projectId, pageNum, pageSize));
    }

    /**
     * 需求规划获取列表数据
     *
     * @param title  标题
     * @param stages 阶段ID
     * @return
     */
    @GetMapping("/issue/demand/planning")
    public ControllerResponse getDemandPlans(@RequestParam(value = "title", required = false) String title,
                                             @RequestParam(value = "stages", required = false) String stages, SecurityDTO securityDTO) {
        return ControllerResponse.success(issueService.getDemandPlans(title, stages, securityDTO));
    }

    /**
     * 需求规划中卡片拖拽。可修改迭代编码，父工作项编码
     *
     * @param issueId  工作项ID
     * @param sprintId 迭代编号
     * @param parentId 父工作项编码
     * @return
     */
    @GetMapping("/issue/demand/drag")
    public ControllerResponse dragDemand(@RequestParam("issueId") Long issueId,
                                         @RequestParam(value = "sprintId", required = false) Long sprintId,
                                         @RequestParam(value = "parentId") Long parentId,
                                         @RequestHeader(name = "projectId",required = false) Long projectId) {
        try {
            issueService.dragDemand(issueId, sprintId, parentId, projectId);
        } catch (Exception e) {
            LOGGER.error("卡片拖拽异常：{}", e);
            return ControllerResponse.fail("卡片拖拽异常");
        }
        return ControllerResponse.success("卡片拖拽成功");
    }

    /**
     * @param projectId
     * @param sprintId
     * @param pageNumber
     * @param pageSize
     * @return
     * @description 查询迭代下关联的提交工作项
     * @date 2020/08/04
     */
    @RequestMapping("/issue/querySprintRelatedCommitTaskList")
    public ControllerResponse querySprintRelatedCommitTaskList(@RequestHeader(value = "projectId",required = false) Long projectId, @RequestParam("sprintId") Long sprintId, @RequestParam("queryStr") String queryStr, Integer pageNumber, Integer pageSize) {
        try {
            return ControllerResponse.success(issueService.getSprintRelatedCommitTaskList(projectId, sprintId, queryStr, pageNumber, pageSize));
        } catch (Exception e) {
            LOGGER.error("querySprintRelatedIssues method occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("查询迭代下关联的工作项异常");
        }
    }

    /**
     * @param projectId
     * @Date 2020/8/26
     * @Description 项目概览页面统计各个阶段的需求个数
     * 看这个产品是否关联了故事，关联了故事就统计故事数，没有故事就统计关联的研发需求数，
     * 没有研发需求就统计关联的业务需求数，都没有关联该产品就为0了，最子到故事
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/issue/stage/countForSso")
    public List<IssueStageIdCountDTO> countIssueByStageId(@RequestParam(name = "projectId",required = false) Long projectId) {
        return issueService.countIssueByStageId(projectId, 1, 1000).getList();
    }

    /**
     * @param issueId
     * @return
     * @description 更新工作项上线状态
     * @date 2021/2/7
     */
    @PutMapping("/issue/updateIssueLaunchState/{issueId}/{issueType}")
    public ControllerResponse updateIssueLaunchState(@PathVariable Long issueId, @PathVariable Byte issueType) {
        try {
            issueService.updateIssueLaunchState(issueId, issueType);
            LOGGER.info("更新工作项" + issueId + "已上线状态成功");
            return ControllerResponse.success("更新工作项" + issueId + "已上线状态成功");
        } catch (Exception e) {
            LOGGER.error("updateIssueLaunchState occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("更新工作项" + issueId + "已上线状态异常");
        }
    }

    /**
     * @Date 2020/10/21
     * @Description 根据登入用户获取代办事项
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/issue/getIssueCommissionByHandler")
    public ControllerResponse getIssueCommissionByHandler(Integer pageNum, Integer pageSize) {
        return ControllerResponse.success(issueService.getIssueCommissionByHandler(pageNum, pageSize));
    }

    /**
     * 功能描述 全景图
     *
     * @param issueId
     * @param noLogin 免登录标识，"true"标识免登录调用，other非免登录调用
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/10/22
     */
    @GetMapping("/issue/getIssuePanoramas")
    public ControllerResponse getIssuePanoramas(String issueId, @RequestParam(name = "bizNum", required = false) String bizNum, @RequestHeader(name = "noLogin", required = false) String noLogin) throws Exception {
        return ControllerResponse.success(issueService.getIssuePanoramas(issueId, bizNum, noLogin));
    }

    /**
     * @param serviceManageIssueDTO
     * @return
     * @description 服务治理平台查询接口
     * @date 2020/10/26
     */
    @PostMapping("/issue/queryIssueList")
    public ControllerResponse queryIssueList(@RequestBody ServiceManageIssueDTO serviceManageIssueDTO) {
        try {
            return ControllerResponse.success(issueService.queryIssueList(serviceManageIssueDTO));
        } catch (Exception e) {
            LOGGER.info("服务治理平台查询需求接口异常信息:{}", e.getMessage());
            if (e instanceof ServiceManageExceptionDTO) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("服务治理平台查询需求接口失败");
        }
    }

    /**
     * 功能描述  根据当前issueId 和 issueType查询Epic
     *
     * @param issueId
     * @param issueType
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/2/8
     */

    @GetMapping("/issue/queryIssueEpic")
    public ControllerResponse queryIssueEpic(Long issueId, Byte issueType) {
        try {
            return ControllerResponse.success(issueService.queryIssueEpic(issueId, issueType));
        } catch (Exception e) {
            LOGGER.info("根据当前issueId 和 issueType查询Epic:{}", e.getMessage());
            if (e instanceof ServiceManageExceptionDTO) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("根据当前issueId 和 issueType查询Epic");
        }
    }

    /**
     * 功能描述  根据当前issueId查询客户需求编号
     *
     * @param issueId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/2/18
     */

    @GetMapping("/issue/queryPlanDeployDate")
    public ControllerResponse queryBizNumList(String issueId) {
        try {
            return ControllerResponse.success(issueService.queryBizNumList(issueId));
        } catch (Exception e) {
            LOGGER.info("根据" + issueId + "查询客户需求编号列表失败:{}", e.getMessage());
            if (e instanceof ServiceManageExceptionDTO) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("根据\"+issueId+\"查询客户需求编号列表失败！");
        }
    }

    /**
     * @param issueId
     * @return
     * @description 更新工作项上线状态
     * @date 2021/2/7
     */
    @PutMapping("/issue/updateIssueLaunchStateWithDate/{issueId}/{issueType}/{actualOnlineTime}")
    public ControllerResponse updateIssueLaunchStateWithDate(@PathVariable Long issueId, @PathVariable Byte issueType, @PathVariable String actualOnlineTime) {
        try {
            issueService.updateIssueLaunchStateWithDate(issueId, issueType, actualOnlineTime);
            LOGGER.info("更新工作项" + issueId + "已上线状态成功");
            return ControllerResponse.success("更新工作项" + issueId + "已上线状态成功");
        } catch (Exception e) {
            LOGGER.error("updateIssueLaunchStateWithDate occur exception, message:{}", e.getMessage());
            return ControllerResponse.fail("更新工作项" + issueId + "已上线状态异常");
        }
    }


}

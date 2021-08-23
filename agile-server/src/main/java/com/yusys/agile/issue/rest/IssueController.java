package com.yusys.agile.issue.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.*;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.issue.utils.IssueUpRegularFactory;
import com.yusys.agile.projectmanager.dto.StageNameAndValueDto;
import com.yusys.agile.servicemanager.dto.ServiceManageExceptionDTO;
import com.yusys.agile.utils.StringUtil;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * :
 *
 * @Date: 2020/4/16
 * @Description:
 */
@Slf4j
@RestController
public class IssueController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueController.class);
    @Resource
    private IssueService issueService;

    @Resource
    private StoryService storyService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private IssueFactory issueFactory;
    @Resource
    private IssueUpRegularFactory issueUpRegularFactory;

    /**
     * 功能描述  初始化Issue列表
     *
     * @param map
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/21
     */
    @PostMapping("/issueList/query")
    public ControllerResponse getIssueList(@RequestBody Map<String, Object> map, @RequestHeader(name = "systemId",required = false) Long systemId) {
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
//            Issue parentIssue = issueService.getIssueByIssueId(parentId);
//            if(IssueTypeEnum.TYPE_STORY.CODE.equals(parentIssue.getIssueType())){
//                //故事关联任务
//                if(null != parentIssue.getLaneId()){
//                    //精益故事向上汇总状态
//                    issueService.updateTaskParentStatus(issueId,parentIssue.getKanbanId());
//                }else{
//                    //敏捷故事，先任务汇总故事状态，然后再故事汇总feature、epic状态
//                    //任务汇总故事状态
//                    Issue issue = issueService.getIssueByIssueId(issueId);
//                    issueFactory.updateStoryLaneIdByTaskCount(issue);
//                    //故事汇总feature、epic状态
//                    rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issue.getParentId());
//                }
//            }else{
//                //epic或feature关联
//                rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issueId);
//            }
        } catch (Exception e) {
            LOGGER.error("建立关联失败：{}", e.getMessage());
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
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issueId);
        } catch (Exception e) {
            LOGGER.error("删除关联失败：{}", e.getMessage());
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
            LOGGER.error("查询异常：{}", e.getMessage());
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
                LOGGER.error("查询异常：{}", e.getMessage());
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
            LOGGER.error(" 添加、取消Issue的收藏异常：{}", e.getMessage());
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
            storyService.checkSprintParam(issueDTO.getIssueId(),issueDTO.getSprintId());
            issueService.createBatchRelation(issueDTO.getParentId(), issueDTO.getListIssueIds(), securityDTO.getUserId());
            Issue parentIssue = issueService.getIssueByIssueId(issueDTO.getParentId());
            if(IssueTypeEnum.TYPE_STORY.CODE.equals(parentIssue.getIssueType())){
                Long taskId = issueDTO.getListIssueIds().get(0);
                //故事关联任务
                if(null != parentIssue.getKanbanId()){
                    //精益故事向上汇总状态
                    issueService.updateTaskParentStatus(taskId,parentIssue.getKanbanId());
                }else{
                    //敏捷故事，先任务汇总故事状态，然后再故事汇总feature、epic状态
                    //任务汇总故事状态
                    Issue issue = issueService.getIssueByIssueId(taskId);
                    issueFactory.updateStoryLaneIdByTaskCount(issue);
                    //故事汇总feature、epic状态
                    rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issue.getParentId());
                }
            }else{
                //epic或feature关联
                issueUpRegularFactory.commonIssueUpRegular(issueDTO.getListIssueIds().get(0));
            }
        } catch (Exception e) {
            LOGGER.error("批量建立关联失败：{}", e.getMessage());
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
            LOGGER.error("批量建立工作项失败：{}", e.getMessage());
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
            LOGGER.error(" 更新处理人异常：{}", e.getMessage());
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
            LOGGER.error("卡片拖拽异常：{}", e.getMessage());
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
            LOGGER.info("更新工作项 {} 已上线状态成功" ,issueId );
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
     * @Author maxp2
     * @Date 2021/7/13
     * @Description epic和feature设置归档
     * @param issueId
     * @param isArchive
     * @Return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/issue/isArchive/{issueId}/{isArchive}")
    public ControllerResponse isArchive(@PathVariable(name = "issueId") Long issueId, @PathVariable(name = "isArchive") Byte isArchive) {
        try {
            issueService.isArchive(issueId, isArchive);
        } catch (Exception e) {
            LOGGER.error("添加、取消Issue的归档异常：{}", e.getMessage());
            return ControllerResponse.fail("添加、取消Issue的归档异常：" + e.getMessage());
        }
        return ControllerResponse.success("操作成功");
    }

    /**
     * @Author yuzt
     * @Description 根据featureId获取feature及其下的story和task
     * @Date 10:02 上午 2021/7/14
     * @Param [fertureMsg]
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     **/
    @GetMapping("/issue/queryFeatureById")
    public ControllerResponse queryFeatureById(Issue issue) {
        try {
            return ControllerResponse.success(issueService.getIssueDtoByIssueId(issue));
        } catch (Exception e) {
            LOGGER.info("根据featureId获取feature及其下的所有story和task:{}", e.getMessage());
            return ControllerResponse.fail(e.getMessage());
        }
    }

    @GetMapping("/issue/listIssueOfProjectAndUser")
    public List<IssueDTO> listIssueOfProjectAndUser(@RequestParam(name = "systemIds")List<Long> systemIds,@RequestParam(name = "userId",required = false) Long userId) {
        return issueService.listIssueOfProjectAndUser(systemIds, userId);
    }

    @GetMapping("/issue/getCollectIssueDataBySystemId")
    public List<StageNameAndValueDto> getCollectIssueDataBySystemId(@RequestParam("systemId")Long systemId){
        return issueService.getCollectIssueDataBySystemId(systemId);

    }

    @GetMapping("/issue/queryIssueListBySystemIds")
    public List<IssueDTO> queryIssueListBySystemIds(@RequestParam(name = "systemIds")List<Long> systemIds,@RequestParam(name = "issueType") int issueType){
        return issueService.queryIssueListBySystemIds(systemIds,issueType);
    }


    @PostMapping("/issue/queryEpicList")
    public PageInfo<IssueDTO> queryEpicList(@RequestBody IssueConditionDTO issueConditionDTO) {

        log.info("根据条件查询epic数据 入参条件为:{}", JSON.toJSONString(issueConditionDTO));
        Integer pageNum = issueConditionDTO.getPageNum();
        Integer pageSize = issueConditionDTO.getPageSize();
        List<Long> systemIds = issueConditionDTO.getSystemIds();


        if(CollectionUtils.isEmpty(systemIds)){
            return new PageInfo(Lists.newArrayList());
        }

        String title = issueConditionDTO.getTitle();
        List<IssueDTO> issueDTOS = issueService.queryEpicList(pageNum,pageSize,title,systemIds);

        PageInfo<IssueDTO> pageInfo = new PageInfo<>(issueDTOS);
        log.info("epic数据查询结果集：{}",JSON.toJSONString(pageInfo));
        return pageInfo;
    }
}

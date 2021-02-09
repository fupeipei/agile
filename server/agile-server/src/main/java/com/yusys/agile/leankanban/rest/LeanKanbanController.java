package com.yusys.agile.leankanban.rest;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.consumer.dto.IssueMailSendDto;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.leankanban.dto.IssueResultDTO;
import com.yusys.agile.leankanban.dto.LeanKanbanDTO;
import com.yusys.agile.leankanban.dto.PageResultDTO;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.utils.page.PageQuery;
import com.google.common.collect.Lists;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 赵英东
 */
@RestController
@RequestMapping("/leanKanban")
public class LeanKanbanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeanKanbanController.class);

    @Resource
    private LeanKanbanService leanKanbanService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private IssueMapper issueMapper;


    @Resource
    private IssueFactory issueFactory;

    @PostMapping("/selectKanbanStageInstance")
    public ControllerResponse selectKanbanStageInstanceInfo(@RequestBody LeanKanbanDTO leanKanbanDTO) {
        List<PageResultDTO> pageResultDTOList = leanKanbanDTO.getPageResultDTOList();
        List<PageQuery<Long>> queries = Lists.newArrayList();
        for (PageResultDTO pageResultDTO : pageResultDTOList) {
            PageQuery<Long> query = new PageQuery<>();
            query.setPage(pageResultDTO.getPage());
            query.setPageSize(pageResultDTO.getPageSize());
            query.setQuery(pageResultDTO.getStageId());
            queries.add(query);
        }
        List<KanbanStageInstanceDTO> instanceDTOS =
                leanKanbanService.selectKanbanStageInstanceDTOList(null, queries);
        return ControllerResponse.success(instanceDTOS);
    }

    @GetMapping("/moveIssue")
    public ControllerResponse moveIssue(Long issueId, Long toStageId, Long toLaneId) {
        Issue oldIssue = issueMapper.selectByPrimaryKey(issueId);

        if (IssueTypeEnum.TYPE_FEATURE.CODE.equals(oldIssue.getIssueType())) {
            Long[] stages = generateStageArray(toStageId, toLaneId);
            if (issueFactory.checkFeatureInVersion(stages, oldIssue.getIssueId(), oldIssue.getParentId())) {
                throw new BusinessException("当前feature未绑定任何版本，不允许变更为需求分析分析完成状态！");
            }
        }

        int result = leanKanbanService.moveIssue(issueId, toStageId, toLaneId);
        if (result > 0) {
            Issue issue = issueMapper.selectByPrimaryKey(issueId);
            SecurityDTO userInfo = UserThreadLocalUtil.getUserInfo();
            IssueMailSendDto issueMailSendDto = new IssueMailSendDto(issue, NumberConstant.THREE, userInfo);
            // 工作项向上规整
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issueId);
            //发送变更状态邮件
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE, issueMailSendDto);
            return ControllerResponse.success("移动成功");
        }
        return ControllerResponse.fail("移动失败");
    }

    private Long[] generateStageArray(Long toStageId, Long toLaneId) {
        Long[] stages = new Long[2];
        stages[0] = toStageId;
        if (Optional.ofNullable(toLaneId).isPresent()) {
            stages[1] = toLaneId;
        }
        return stages;
    }

    @PostMapping("/selectLeanKanbanView")
    public ControllerResponse selectLeanKanbanView(@RequestHeader("projectId") Long projectId,
                                                   @RequestBody LeanKanbanDTO leanKanbanDTO) {
        try {
            List<IssueResultDTO> issueResultDTOList = leanKanbanService.selectIssueViewInfo(
                    projectId,
                    leanKanbanDTO.getPageResultDTOList(),
                    leanKanbanDTO.getIssueStringDTO());
            return ControllerResponse.success(issueResultDTOList);
        } catch (Exception e) {
            LOGGER.info("获取精益看板视图异常:{}", e.getMessage());
        }
        return ControllerResponse.fail("获取精益看板视图失败");


    }
}

package com.yusys.agile.issue.utils;

import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.dao.SIssueRichtextMapper;
import com.yusys.agile.issue.domain.*;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.domain.KanbanStageInstanceExample;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.enums.StateEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 工作项富文本处理
 */
@Component
public class IssueRichTextFactory {

    private static final Logger logger = LoggerFactory.getLogger(IssueRichTextFactory.class);
    @Autowired
    private SIssueRichtextMapper richtextMapper;
    @Autowired
    private IssueMapper issueMapper;

    @Resource
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;

    /**
     * 保存工作项富文本内容
     *
     * @param issueId
     * @param description
     */
    public void dealIssueRichText(Long issueId, String description,String acceptanceCriteria,String tenantCode) {
        SIssueRichtextExample example = new SIssueRichtextExample();
        example.createCriteria().andIssueIdEqualTo(issueId).andStateEqualTo(StateEnum.U.toString());
        List<SIssueRichtextWithBLOBs> issueRichTexts = richtextMapper.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isNotEmpty(issueRichTexts)) {
            SIssueRichtextWithBLOBs richText = issueRichTexts.get(0);
            richText.setDescription(description);
            richText.setCreateTime(new Date());
            richText.setAcceptanceCriteria(acceptanceCriteria);
            richtextMapper.updateByPrimaryKeyWithBLOBs(richText);
        } else {
            SIssueRichtextWithBLOBs richText = new SIssueRichtextWithBLOBs();
            richText.setIssueId(issueId);
            richText.setDescription(description);
            richText.setAcceptanceCriteria(acceptanceCriteria);
            richText.setCreateTime(new Date());
            richText.setState(StateEnum.U.toString());
            if (StringUtils.isNotBlank(tenantCode)) {
                richText.setTenantCode(tenantCode);
            }
            richtextMapper.insert(richText);
        }
    }

    /**
     * 查询issue的富文本,赋值给IssueDTO
     *
     * @param issueDTO
     */
    public void queryIssueRichText(IssueDTO issueDTO) {
        Long issueId = issueDTO.getIssueId();
        SIssueRichtextExample example = new SIssueRichtextExample();
        example.createCriteria().andStateEqualTo(StateEnum.U.toString()).andIssueIdEqualTo(issueId);
        List<SIssueRichtextWithBLOBs> issueRichtexts = richtextMapper.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isNotEmpty(issueRichtexts)) {
            SIssueRichtextWithBLOBs richText = issueRichtexts.get(0);
            if (Optional.ofNullable(richText).isPresent()) {
                issueDTO.setDescription(richText.getDescription());
                issueDTO.setAcceptanceCriteria(richText.getAcceptanceCriteria());
            }
        }
    }

    /**
     * 验证工作项所处目标阶段状态下制品数限制，超过则抛出异常
     *
     * @param stageId   一级阶段ID
     * @param landId    二级阶段ID
     * @param projectId 项目ID
     * @param issueType 工作项类型
     */
    public void dealStagesProductLimit(Long stageId, Long landId, Long projectId, Byte issueType) {
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andStageIdEqualTo(stageId)
                .andProjectIdEqualTo(projectId)
                .andIssueTypeEqualTo(issueType)
                .andStateEqualTo(StateEnum.U.toString());
        if (Optional.ofNullable(landId).isPresent()) {
            criteria.andLaneIdEqualTo(landId);
        }
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        if (CollectionUtils.isEmpty(issues)) {
            return;
        }
        KanbanStageInstanceExample kanbanStageInstanceExample = new KanbanStageInstanceExample();
        KanbanStageInstanceExample.Criteria criteria1 = kanbanStageInstanceExample.createCriteria();
        if (Optional.ofNullable(landId).isPresent()) {
            criteria1.andProjectIdEqualTo(projectId).andParentIdEqualTo(stageId).andStageIdEqualTo(landId).andStateEqualTo(StateEnum.U.toString());
        } else {
            criteria1.andProjectIdEqualTo(projectId).andStageIdEqualTo(stageId).andStateEqualTo(StateEnum.U.toString());
        }

        List<KanbanStageInstance> stageInstances = kanbanStageInstanceMapper.selectByExample(kanbanStageInstanceExample);
        if (CollectionUtils.isNotEmpty(stageInstances) && stageInstances.size() == 1) {
            Integer issueSize = issues.size();
            Integer maxNumbers = Optional.ofNullable(stageInstances.get(0).getMaxNumbers()).orElse(0);
            if ((issueSize >= maxNumbers) && maxNumbers > 0) {
                throw new BusinessException("目标工作项制品个数超过最大制品数!");
            }
        }
    }

    /**
     * 根据工作项ID查询富文本信息
     *
     * @param issueId 工作项ID
     * @return
     */
    public SIssueRichtextWithBLOBs getIssueRichText(Long issueId) {
        SIssueRichtextWithBLOBs richtext = null;
        SIssueRichtextExample richtextExample = new SIssueRichtextExample();
        richtextExample.createCriteria().andIssueIdEqualTo(issueId).andStateEqualTo(StateEnum.U.toString());
        List<SIssueRichtextWithBLOBs> issueRichtexts = richtextMapper.selectByExampleWithBLOBs(richtextExample);
        if (CollectionUtils.isNotEmpty(issueRichtexts)) {
            richtext = issueRichtexts.get(0);
        }
        return richtext;
    }
}

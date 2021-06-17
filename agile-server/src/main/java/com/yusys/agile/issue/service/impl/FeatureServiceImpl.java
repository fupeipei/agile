package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.FeatureService;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.leankanban.domain.SLeanKanban;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.portal.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Date: 13:33
 */
@Service
public class FeatureServiceImpl implements FeatureService {

    @Resource
    private IssueFactory issueFactory;

    @Resource
    private IssueMapper issueMapper;

    @Resource
    private LeanKanbanService leanKanbanService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFeature(Long featureId,Boolean deleteChild) {
        issueFactory.deleteIssue(featureId,deleteChild);
    }


    @Override
    public IssueDTO queryFeature(Long featureId) {
        Long projectId = issueFactory.getSystemIdByIssueId(featureId);
        return issueFactory.queryIssue(featureId, projectId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editFeature(IssueDTO issueDTO) {
        if (issueFactory.checkFeatureInVersion(issueDTO.getStages(), issueDTO.getIssueId(), issueDTO.getParentId())) {
            throw new BusinessException("当前feature未绑定任何版本，不允许变更为需求分析完成状态！");
        }
        Issue oldFeature = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        Long projectId = oldFeature.getProjectId();

        Issue feature = issueFactory.editIssue(issueDTO, oldFeature, projectId);
        int count;
        count = issueMapper.updateByPrimaryKeySelectiveWithNull(feature);
        if (count != 1) {
            throw new BusinessException("更新研发需求失败！");
        }

    }

    @Override
    public List<Issue> queryFeatureByEpicIds(List<Long> epicList) {
        IssueExample example = new IssueExample();
        example.createCriteria().andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE).andParentIdIn(epicList);
        return issueMapper.selectByExample(example);
    }

    @Override
    public Long createFeature(IssueDTO issueDTO) {
        Long id = issueFactory.createIssue(issueDTO, "研发需求名称已存在！", "新增研发需求", IssueTypeEnum.TYPE_FEATURE.CODE);
        //根据teamId获取看板id并更新
        SLeanKanban sLeanKanban = leanKanbanService.querySimpleLeanKanbanInfo(issueDTO.getTeamId());
        if(Optional.ofNullable(sLeanKanban).isPresent()){
            Long kanbanId = sLeanKanban.getKanbanId();
            Issue issue = issueMapper.getIssue(id);
            issue.setKanbanId(kanbanId);
            issueMapper.updateByPrimaryKeySelective(issue);
        }
        return id;
    }

    @Override
    public Long copyFeature(Long featureId) {
        return issueFactory.copyIssue(featureId,"该复制的研发需求已失效！", "研发需求名称已存在！", "新增研发需求", IssueTypeEnum.TYPE_FEATURE.CODE);
    }

    @Override
    public List<IssueDTO> queryUnlinkedFeature(Long projectId, Integer pageNum, Integer pageSize, String title) {
        return issueFactory.queryUnlinkedIssue(projectId, IssueTypeEnum.TYPE_FEATURE.CODE, pageNum, pageSize, title, null);
    }

    @Override
    public List<IssueDTO> queryAllFeature(Long projectId, Integer pageNum, Integer pageSize, String title) {
        return issueFactory.queryAllIssue(projectId, IssueTypeEnum.TYPE_FEATURE.CODE, pageNum, pageSize, title, null);
    }

    @Override
    public List<IssueDTO> queryFeatureForEpic(Long projectId, Long epicId) {
        return issueFactory.queryAllIssue(projectId, IssueTypeEnum.TYPE_FEATURE.CODE, null, null, "", epicId);
    }
}

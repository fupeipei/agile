package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.FeatureService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.service.StageService;
import com.yusys.agile.versionmanager.dao.VersionFeatureSyncDataMapper;
import com.yusys.agile.versionmanager.domain.VersionFeatureSyncData;
import com.yusys.agile.versionmanager.enums.OperateTypeEnum;
import com.yusys.portal.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date: 13:33
 */
@Service
public class FeatureServiceImpl implements FeatureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureServiceImpl.class);

    @Resource
    private IssueFactory issueFactory;

    @Resource
    private IssueMapper issueMapper;

    @Resource
    private VersionFeatureSyncDataMapper versionFeatureSyncDataMapper;

    @Resource
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;

    @Resource
    private StageService stageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFeature(Long featureId, Boolean deleteChild) {
        Issue record = issueMapper.selectByPrimaryKey(featureId);
        issueFactory.deleteIssue(featureId, deleteChild);
        dealEpicFeatureSync(featureId);
    }

    /**
     * @param featureId
     * @description 处理需求下分支删除
     * @date 2020/10/1
     */
    private void dealEpicFeatureSync(Long featureId) {
        Issue feature = issueMapper.getIssue(featureId);
        if (null != feature) {
            Long epicId = feature.getParentId();
            if (null != epicId) {
                Issue epic = issueMapper.getIssue(epicId);
                if (null != epic) {
                    VersionFeatureSyncData versionFeatureSyncData = new VersionFeatureSyncData();
                    versionFeatureSyncData.setRequireId(epicId);
                    versionFeatureSyncData.setBranchId(featureId);
                    versionFeatureSyncData.setProjectId(feature.getProjectId());
                    versionFeatureSyncData.setOperateType(OperateTypeEnum.OPERATE_TYPE_DELETE.VALUE.byteValue());
                    int count = versionFeatureSyncDataMapper.insertSelective(versionFeatureSyncData);
                    if (count != 1) {
                        throw new RuntimeException("保存需求分支解绑数据失败");
                    }
                }
            }
        }
    }

    @Override
    public IssueDTO queryFeature(Long featureId) {
        Long projectId = issueFactory.getProjectIdByIssueId(featureId);
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
        return id;
    }

    @Override
    public Long copyFeature(Long featureId, Long projectId) {
        return issueFactory.copyIssue(featureId, projectId, "该复制的研发需求已失效！", "研发需求名称已存在！", "新增研发需求", IssueTypeEnum.TYPE_FEATURE.CODE);
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

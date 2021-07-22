package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IsAchiveEnum;
import com.yusys.agile.issue.enums.IssueStateEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.FeatureService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.leankanban.domain.SLeanKanban;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.enums.TeamTypeEnum;
import com.yusys.agile.zentao.dto.ZtStoryDTO;
import com.yusys.agile.zentao.service.ZenTaoStoryService;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static final Logger log = LoggerFactory.getLogger(FeatureServiceImpl.class);


    @Resource
    private IssueFactory issueFactory;

    @Resource
    private IssueMapper issueMapper;

    @Resource
    private LeanKanbanService leanKanbanService;

    @Resource
    private STeamMapper sTeamMapper;
    @Resource
    private ZenTaoStoryService zenTaoStoryService;
    @Resource
    private IFacadeSystemApi facadeSystemApi;

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
        //判断如果是精益团队则处理人必填
        if(checkIsLeanTeanm(issueDTO.getTeamId()) && null == issueDTO.getHandler()){
            throw new BusinessException("选择了精益团队请选择一个处理人！");
        }
        Issue oldFeature = issueMapper.selectByPrimaryKey(issueDTO.getIssueId());
        //先校验feature是否已经拆分了故事，如果已经拆分了故事则不允许变更团队
        if(hasChildren(issueDTO.getIssueId())&& !issueDTO.getTeamId().equals(oldFeature.getTeamId())){
            throw new BusinessException("Feature已拆分故事不允许变更团队！");
        }

        //先校验feature是否已经拆分了故事，如果已经拆分了故事则不允许变更系统
        if(hasChildren(issueDTO.getIssueId())&& !issueDTO.getSystemId().equals(oldFeature.getSystemId())){
            throw new BusinessException("Feature已拆分故事不允许变更系统！");
        }

        //判断如果feature上的团队发生了变更，那么需要同时更新看板ID，并且将feature的状态置为就绪
        if(!issueDTO.getTeamId().equals(oldFeature.getTeamId())){
            SLeanKanban sLeanKanban = leanKanbanService.querySimpleLeanKanbanInfo(issueDTO.getTeamId());
            if(Optional.ofNullable(sLeanKanban).isPresent()){
                issueDTO.setKanbanId(sLeanKanban.getKanbanId());
                issueDTO.setStages(new Long[]{1L});
            }
        }

        Issue feature = issueFactory.editIssue(issueDTO, oldFeature, null);
        int count;
        count = issueMapper.updateByPrimaryKeySelectiveWithNull(feature);
        if (count != 1) {
            throw new BusinessException("更新研发需求失败！");
        }

    }

    private boolean hasChildren(Long issueId) {
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andParentIdEqualTo(issueId).andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE);
        return CollectionUtils.isNotEmpty(issueMapper.selectByExample(issueExample)) ? true : false;
    }

    @Override
    public List<Issue> queryFeatureByEpicIds(List<Long> epicList) {
        IssueExample example = new IssueExample();
        example.createCriteria().andStateEqualTo(IssueStateEnum.TYPE_VALID.CODE).andParentIdIn(epicList);
        return issueMapper.selectByExample(example);
    }

    @Override
    public Long createFeature(IssueDTO issueDTO) {
        if(Optional.ofNullable(issueDTO.getParentId()).isPresent()){
            Issue issue = issueMapper.selectByPrimaryKey(issueDTO.getParentId());
            if(IsAchiveEnum.ACHIVEA_TRUE.CODE.equals(issue.getIsArchive())){
                throw new BusinessException("工作项已归档不能新增feature");
            }
        }
        //判断如果是精益团队则处理人必填
        if(checkIsLeanTeanm(issueDTO.getTeamId()) && null == issueDTO.getHandler()){
            throw new BusinessException("选择了精益团队请选择一个处理人！");
        }
        Long id = issueFactory.createIssue(issueDTO, "研发需求名称已存在！", "新增研发需求", IssueTypeEnum.TYPE_FEATURE.CODE);

        syncStoryToZenTao(issueDTO);

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

    private boolean checkIsLeanTeanm(Long teamId) {
        STeam sTeam = sTeamMapper.selectByPrimaryKey(teamId);
        if(null != sTeam){
            if(TeamTypeEnum.lean_team.getCode().equals(sTeam.getTeamType())){
                return true;
            }
        }
        return false;
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
    public List<IssueDTO> queryAllFeature(Long systemId, Integer pageNum, Integer pageSize, String title) {
        return issueFactory.queryAllIssue(systemId, IssueTypeEnum.TYPE_FEATURE.CODE, pageNum, pageSize, title, null);
    }

    @Override
    public List<IssueDTO> queryFeatureForEpic(Long projectId, Long epicId) {
        return issueFactory.queryAllIssue(projectId, IssueTypeEnum.TYPE_FEATURE.CODE, null, null, "", epicId);
    }

    /**
     * 对接禅道需求feature
      * @param issueDTO
     */
    public  void  syncStoryToZenTao(IssueDTO issueDTO){
        try{
            //如果系统为空取当前系统
            Long systemId =  issueDTO.getSystemId();
            if (!Optional.ofNullable(systemId).isPresent()) {
                systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
            }
            if(Optional.ofNullable(systemId).isPresent()){
                SsoSystem ssoSystem =  facadeSystemApi.querySystemBySystemId(systemId);
                String systemCode = ssoSystem.getSystemCode();
                ZtStoryDTO ztStoryDTO = new ZtStoryDTO();
                ztStoryDTO.setCode(systemCode);
                ztStoryDTO.setTitle(issueDTO.getTitle());
                zenTaoStoryService.addStory(ztStoryDTO);
            }
        }catch (Exception e){
            log.error("同步禅道需求feature报错"+e.getMessage());
        }
    }
}

package com.yusys.agile.leankanban.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.leankanban.dao.SLeanKanbanMapper;
import com.yusys.agile.leankanban.domain.SLeanKanban;
import com.yusys.agile.leankanban.domain.SLeanKanbanExample;
import com.yusys.agile.leankanban.dto.SLeanKanbanDTO;
import com.yusys.agile.leankanban.service.LeanKanbanService;
import com.yusys.agile.set.stage.dao.KanbanStageInstanceMapper;
import com.yusys.agile.set.stage.domain.KanbanStageInstance;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.set.stage.dto.StageTemplateConfigDTO;
import com.yusys.agile.set.stage.enums.StageTypeEnum;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.agile.set.stage.service.StageTemplateConfigService;
import com.yusys.agile.team.dao.TeamMapper;
import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.service.TeamService;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.utils.CollectionUtil;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  @Description: 看板创建服务类
 *  @author: zhao_yd
 *  @Date: 2021/6/11 2:22 下午
 *
 */

@Slf4j
@Service
public class LeanKanbanServiceImpl  implements LeanKanbanService {

    @Autowired
    private StageTemplateConfigService stageTemplateConfigService;
    @Autowired
    private STeamMapper teamMapper;
    @Autowired
    private SLeanKanbanMapper leanKanbanMapper;
    @Autowired
    private KanbanStageInstanceMapper kanbanStageInstanceMapper;
    @Autowired
    private IStageService stageService;
    @Autowired
    private IssueService issueService;


    @Override
    public void createLeanKanban(Long teamId) {
        log.info("创建看板入参 teamId{}",teamId);
        //1、创建看板实例
        STeam team = teamMapper.selectByPrimaryKey(teamId);
        SLeanKanbanDTO leanKanbanDTO = new SLeanKanbanDTO();
        leanKanbanDTO.setKanbanName(team.getTeamName());
        leanKanbanDTO.setKanbanDesc(team.getTeamName());
        leanKanbanDTO.setStatus(1L);
        leanKanbanDTO.setState(StateEnum.U.getValue());
        leanKanbanDTO.setTeamId(teamId);

        SLeanKanban sLeanKanban = ReflectUtil.copyProperties(leanKanbanDTO, SLeanKanban.class);
        leanKanbanMapper.insertSelective(sLeanKanban);

        //2、填充看板阶段信息
        Long kanbanId = sLeanKanban.getKanbanId();
        List<StageTemplateConfigDTO> defaultStages = stageTemplateConfigService.getDefaultStages();
        List<KanbanStageInstance> kanbanStageInstances;
        try {
            kanbanStageInstances = ReflectUtil.copyProperties4List(defaultStages, KanbanStageInstance.class);
        } catch (Exception e) {
           log.info("模版数据转换异常:{}",e.getMessage());
           throw new BusinessException("团队看板创建失败.");
        }

        log.info("获取看板阶段数据：{}", JSONObject.toJSONString(kanbanStageInstances));
        if(CollectionUtils.isNotEmpty(kanbanStageInstances)){
            for (KanbanStageInstance kanbanStageInstance: kanbanStageInstances) {
                kanbanStageInstance.setTeamId(teamId);
                kanbanStageInstance.setKanbanId(kanbanId);
                kanbanStageInstance.setStageType(StageTypeEnum.LEAN.CODE);
                kanbanStageInstanceMapper.insertSelective(kanbanStageInstance);
            }
        }else {
            throw new BusinessException("团队看板创建失败.");
        }
    }

    @Override
    public SLeanKanbanDTO queryLeanKanbanInfo(Long teamId) {

        SLeanKanbanExample sLeanKanbanExample = new SLeanKanbanExample();
        sLeanKanbanExample.createCriteria().andStateEqualTo(StateEnum.U.getValue()).andTeamIdEqualTo(teamId);
        List<SLeanKanban> sLeanKanbans = leanKanbanMapper.selectByExample(sLeanKanbanExample);

        if(CollectionUtils.isNotEmpty(sLeanKanbans)){

            SLeanKanban sLeanKanban = sLeanKanbans.get(0);
            SLeanKanbanDTO kanbanDTO = ReflectUtil.copyProperties(sLeanKanban, SLeanKanbanDTO.class);
            Long kanbanId = sLeanKanban.getKanbanId();
            List<KanbanStageInstance> stages = kanbanStageInstanceMapper.getStagesByKanbanId(kanbanId);
            List<KanbanStageInstanceDTO> instanceDTOS = null;
            try {
                instanceDTOS = ReflectUtil.copyProperties4List(stages, KanbanStageInstanceDTO.class);
            } catch (Exception e) {
                log.info("精益看板阶段转换异常：{}",e.getMessage());
            }

            if(CollectionUtils.isNotEmpty(instanceDTOS)){
                for(KanbanStageInstanceDTO kanbanStageInstanceDTO : instanceDTOS){
                    Long stageId = kanbanStageInstanceDTO.getStageId();
                    List<KanbanStageInstance> secondStageList = stageService.getSecondStageList(stageId, kanbanId);
                    if(CollectionUtils.isNotEmpty(secondStageList)){
                        try {
                            List<KanbanStageInstanceDTO> secondStages = ReflectUtil.copyProperties4List(secondStageList, KanbanStageInstanceDTO.class);
                            kanbanStageInstanceDTO.setSecondStages(secondStages);
                        } catch (Exception e) {
                            log.info("精益看板二级阶段转换异常：{}",e.getMessage());
                        }
                    }
                }
            }
            kanbanDTO.setKanbanStageInstances(instanceDTOS);

            //工作项数据
            //issueService.getIssueTrees(kanbanId, IssueTypeEnum.TYPE_EPIC.CODE);

            return kanbanDTO;
        }
        return null;
    }

    @Override
    public SLeanKanban querySimpleLeanKanbanInfo(Long teamId) {
        SLeanKanbanExample sLeanKanbanExample = new SLeanKanbanExample();
        sLeanKanbanExample.createCriteria().andStateEqualTo(StateEnum.U.getValue()).andTeamIdEqualTo(teamId);
        List<SLeanKanban> sLeanKanbans = leanKanbanMapper.selectByExample(sLeanKanbanExample);
        if(CollectionUtils.isNotEmpty(sLeanKanbans)){
            return sLeanKanbans.get(0);
        }
        return null;
    }

}

package com.yusys.agile.versionmanagerv3.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.set.stage.constant.StageConstant;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.agile.versionmanagerV3.SVersionIssueRelateDTO;
import com.yusys.agile.versionmanagerV3.SVersionManagerDTO;
import com.yusys.agile.versionmanagerv3.dao.SVersionIssueRelateMapper;
import com.yusys.agile.versionmanagerv3.dao.SVersionManagerMapper;
import com.yusys.agile.versionmanagerv3.domain.SVersionIssueRelate;
import com.yusys.agile.versionmanagerv3.domain.SVersionIssueRelateExample;
import com.yusys.agile.versionmanagerv3.domain.SVersionManager;
import com.yusys.agile.versionmanagerv3.domain.SVersionManagerExample;
import com.yusys.agile.versionmanagerv3.service.SVersionManagerV3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SVersionManagerV3ServiceImpl implements SVersionManagerV3Service {


    @Autowired
    private SVersionManagerMapper sVersionManagerMapper;

    @Autowired
    private SVersionIssueRelateMapper sVersionIssueRelateMapper;

    @Autowired
    private IssueService issueService;

    @Autowired
    private IStageService iStageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveVersionManager(SVersionManagerDTO sVersionManagerDTO) {
        checkUniqueVersionName(sVersionManagerDTO);
        SVersionManager sVersionManager = ReflectUtil.copyProperties(sVersionManagerDTO, SVersionManager.class);
        sVersionManager.setCreateUid(UserThreadLocalUtil.getUserInfo().getUserId());
        sVersionManager.setCreateName(UserThreadLocalUtil.getUserInfo().getUserName());
        sVersionManager.setSystemId(UserThreadLocalUtil.getUserInfo().getSystemId());
        sVersionManagerMapper.insertSelective(sVersionManager);
        List<Long> versionIssueRelateIds = sVersionManagerDTO.getVersionIssueRelateIds();
        this.batchInsertSVersionIssueRelate(versionIssueRelateIds, sVersionManager.getVersionManagerId());
    }


    private void batchInsertSVersionIssueRelate(List<Long> versionIssueRelateIds, Long sVersionManagerId) {
        List<SVersionIssueRelate> sVersionIssueRelateList = new HashSet<>(versionIssueRelateIds).stream().map(x -> {
            SVersionIssueRelate sVersionIssueRelate = new SVersionIssueRelate();
            sVersionIssueRelate.setIssueId(x);
            sVersionIssueRelate.setIssueType(IssueTypeEnum.TYPE_FEATURE.CODE);
            sVersionIssueRelate.setVersionId(sVersionManagerId);
            return sVersionIssueRelate;
        }).collect(Collectors.toList());
        sVersionIssueRelateList.forEach(sVersionIssueRelate -> {
            sVersionIssueRelateMapper.insertSelective(sVersionIssueRelate);
        });
    }

    /**
     * 校验本系统下名称要保持唯一
     *
     * @param sVersionManagerDTO
     */
    private void checkUniqueVersionName(SVersionManagerDTO sVersionManagerDTO) {
        //本系统下发布版本计划唯一
        String versionName = sVersionManagerDTO.getVersionName();
        SVersionManagerExample sVersionManagerExample = new SVersionManagerExample();
        SVersionManagerExample.Criteria criteria = sVersionManagerExample.createCriteria();
        criteria.andStateEqualTo(StateEnum.U.getValue())
                .andVersionNameEqualTo(versionName)
                .andSystemIdEqualTo(UserThreadLocalUtil.getUserInfo().getSystemId());
        List<SVersionManager> sVersionManagers = sVersionManagerMapper.selectByExample(sVersionManagerExample);
        if (CollectionUtils.isNotEmpty(sVersionManagers)
                && (sVersionManagerDTO.getVersionManagerId() == null || !sVersionManagers.get(0).getVersionManagerId().equals(sVersionManagerDTO.getVersionManagerId()))) {
            throw new BusinessException("版本计划名称要唯一");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVersionManager(SVersionManagerDTO sVersionManagerDTO) {
        checkUniqueVersionName(sVersionManagerDTO);
        SVersionManager sVersionManager = ReflectUtil.copyProperties(sVersionManagerDTO, SVersionManager.class);
        int i = sVersionManagerMapper.updateByPrimaryKeySelective(sVersionManager);
        List<Long> versionIssueRelateIds = sVersionManagerDTO.getVersionIssueRelateIds();
        SVersionIssueRelateExample sVersionIssueRelateExample = new SVersionIssueRelateExample();
        sVersionIssueRelateExample.createCriteria()
                .andVersionIdEqualTo(sVersionManagerDTO.getVersionManagerId());
        sVersionIssueRelateMapper.deleteByExample(sVersionIssueRelateExample);
        this.batchInsertSVersionIssueRelate(versionIssueRelateIds, sVersionManagerDTO.getVersionManagerId());
    }

    @Override
    public void deleteVersionManager(Long id) {
        SVersionManager sVersionManager = sVersionManagerMapper.selectByPrimaryKey(id);
        if (Optional.ofNullable(sVersionManager).isPresent()) {
            sVersionManager.setState(StateEnum.E.getValue());
            sVersionManagerMapper.updateByPrimaryKeySelective(sVersionManager);
        }
    }

    @Override
    public PageInfo<SVersionManagerDTO> queryVersionManagerList(Integer pageNum, Integer pageSize, String searchKey) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<SVersionManagerDTO> sVersionManagerDTOList = sVersionManagerMapper.queryVersionManagerListByExample(searchKey, UserThreadLocalUtil.getUserInfo().getSystemId());
        if (CollectionUtils.isNotEmpty(sVersionManagerDTOList)) {
            sVersionManagerDTOList.forEach(x -> {
                SVersionIssueRelateExample sVersionIssueRelateExample = new SVersionIssueRelateExample();
                sVersionIssueRelateExample.setDistinct(true);
                sVersionIssueRelateExample.createCriteria()
                        .andVersionIdEqualTo(x.getVersionManagerId());
                List<SVersionIssueRelate> sVersionIssueRelateList = sVersionIssueRelateMapper.selectByExample(sVersionIssueRelateExample);
                if (CollectionUtils.isNotEmpty(sVersionIssueRelateList)) {
                    try {
                        List<Long> featureIds = sVersionIssueRelateList.stream().map(SVersionIssueRelate::getIssueId).collect(Collectors.toList());
                        List<IssueDTO> issueDTOS = issueService.queryFeatureScheduleRel(featureIds, null, null, null);
                        List<SVersionIssueRelateDTO> sVersionIssueRelateDTOS = buildSVersionIssueRelateList(issueDTOS);
                        x.setSVersionIssueRelateDTOList(sVersionIssueRelateDTOS);
                        x.setRelateNum(sVersionIssueRelateDTOS.size());
                    } catch (Exception e) {
                        log.error("数据转换异常", e);
                    }
                }

            });
        }
        return new PageInfo(sVersionManagerDTOList);
    }

    @Override
    public PageInfo<SVersionIssueRelateDTO> queryRequirementRelList(Integer pageNum, Integer pageSize, String searchKey, Long teamId, String operateType, Long versionManagerId) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        //添加的时候需求列表只能展示feature未关联的版本
        List<Long> filterIssueIds = Lists.newArrayList();
        if ("insert".equals(operateType)) {
            filterIssueIds = sVersionManagerMapper.selectAllIssueIds(null, UserThreadLocalUtil.getUserInfo().getSystemId());
            //修改的时候只能展示feature未关联的版本以及本身已选的feature
        } else if ("update".equals(operateType)) {
            filterIssueIds = sVersionManagerMapper.selectAllIssueIds(versionManagerId, UserThreadLocalUtil.getUserInfo().getSystemId());
        }
        List<IssueDTO> issueDTOS = issueService.queryFeatureScheduleRelByOperateType(teamId, searchKey, UserThreadLocalUtil.getUserInfo().getSystemId(),filterIssueIds);
        return new PageInfo(buildSVersionIssueRelateList(issueDTOS));
    }


    private List<SVersionIssueRelateDTO> buildSVersionIssueRelateList(List<IssueDTO> issueDTOS) throws Exception {
        if (CollectionUtils.isNotEmpty(issueDTOS)) {
            List<SVersionIssueRelateDTO> sVersionIssueRelateDTOS = ReflectUtil.copyProperties4List(issueDTOS, SVersionIssueRelateDTO.class);
            sVersionIssueRelateDTOS.forEach(x -> {
                Long laneId = x.getLaneId();
                Long teamId1 = x.getTeamId();
                String langName = getLangName(teamId1, laneId);
                String firstStageName = StageConstant.FirstStageEnum.getFirstStageName(x.getStageId());
                x.setLangName(langName);
                x.setStageName(firstStageName);
            });
            return sVersionIssueRelateDTOS;
        }
        return new ArrayList<>();
    }


    public String getLangName(Long teamId, Long langId) {
        Map<Long, String> stageMapByTeamId = iStageService.getStageMapByTeamId(teamId);
        return stageMapByTeamId.get(langId);
    }


}

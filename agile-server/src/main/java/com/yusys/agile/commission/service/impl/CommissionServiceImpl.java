package com.yusys.agile.commission.service.impl;

import com.yusys.agile.commission.constants.CommissionConstant;
import com.yusys.agile.commission.dao.CommissionMapper;
import com.yusys.agile.commission.dao.CommissionRecordMapper;
import com.yusys.agile.commission.domain.SCommission;
import com.yusys.agile.commission.domain.SCommissionExample;
import com.yusys.agile.commission.domain.SCommissionRecord;
import com.yusys.agile.commission.dto.SCommissionDTO;
import com.yusys.agile.commission.service.CommissionService;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yusys.portal.facade.client.api.IFacadeProjectApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.entity.SsoProject;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代办业务实现类
 * @date 2021/2/1
 */
@Service
public class CommissionServiceImpl implements CommissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommissionServiceImpl.class);

    @Resource
    private IFacadeProjectApi iFacadeProjectApi;

    @Resource
    private IFacadeUserApi iFacadeUserApi;

    @Resource
    private CommissionMapper commissionMapper;

    @Resource
    private CommissionRecordMapper commissionRecordMapper;

    @Resource
    private IssueMapper issueMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCommission(SCommissionDTO sCommissionDTO) {
        if (null == sCommissionDTO.getCurrentHandler()) {
            return;
        }
        SCommission sCommission = ReflectUtil.copyProperties(sCommissionDTO, SCommission.class);
        sCommission.setCreateUid(UserThreadLocalUtil.getUserInfo().getUserId());
        sCommission.setCreateTime(new Date());
        sCommission.setState(StateEnum.U.getValue());
        sCommission.setTenantCode(UserThreadLocalUtil.getUserInfo().getTenantCode());
        //保存代办
        int count = commissionMapper.insert(sCommission);
        if (count != 1) {
            throw new RuntimeException("保存代办异常");
        }
        saveCommissionRecord(sCommission);
    }

    /**
     * @param sCommission
     * @description 保存代办记录
     * @date 2021/2/1
     */
    private void saveCommissionRecord(SCommission sCommission) {
        //保存代办记录
        SCommissionRecord sCommissionRecord = getCommissionRecord(sCommission);
        int count = commissionRecordMapper.insert(sCommissionRecord);
        if (count != 1) {
            throw new RuntimeException("保存代办记录异常");
        }
    }

    private SCommissionRecord getCommissionRecord(SCommission sCommission) {
        Long commissionId = sCommission.getId();
        SCommissionRecord sCommissionRecord = new SCommissionRecord();
        sCommissionRecord.setCommissonId(commissionId);
        sCommissionRecord.setTitle(sCommission.getTitle());
        sCommissionRecord.setType(sCommission.getType());
        sCommissionRecord.setHandler(sCommission.getCurrentHandler());
        sCommissionRecord.setIssueId(sCommission.getIssueId());
        sCommissionRecord.setProjectId(sCommission.getProjectId());
        sCommissionRecord.setStageId(sCommission.getStageId());
        sCommissionRecord.setLaneId(sCommission.getLaneId());
        sCommissionRecord.setState(sCommission.getState());
        sCommissionRecord.setCreateTime(new Date());
        sCommissionRecord.setCreateUid(sCommission.getCreateUid());
        sCommissionRecord.setTenantCode(sCommission.getTenantCode());
        return sCommissionRecord;
    }

    @Override
    public void updateCommission(int type, SCommissionDTO sCommissionDTO) {
        LOGGER.info("updateCommission method param issueId:{}", sCommissionDTO.getIssueId());
        SCommission sCommission = ReflectUtil.copyProperties(sCommissionDTO, SCommission.class);
        sCommission.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
        sCommission.setUpdateTime(new Date());
        sCommission.setTenantCode(UserThreadLocalUtil.getUserInfo().getTenantCode());
        int count = 0;
        if (type == CommissionConstant.ISSUE_TYPE) {
            count = commissionMapper.updateByIssueIdSelective(sCommission);
        } else if (type == CommissionConstant.PRIMARY_KEY_TYPE) {
            count = commissionMapper.updateByPrimaryKeySelective(sCommission);
        }
        if (count != 1) {
            throw new RuntimeException("更新代办异常");
        }
    }

    @Override
    public PageInfo<List<SCommissionDTO>> getCommissionList(Long currentHandler, String title, Integer pageNum, Integer pageSize) {
        List<SCommissionDTO> sCommissionDTOList = Lists.newArrayList();
        PageHelper.startPage(pageNum, pageSize);
        SCommission sCommission = new SCommission();
        sCommission.setCurrentHandler(currentHandler);
        sCommission.setTitle(title);
        List<SCommission> sCommissionList = commissionMapper.selectCommissionList(sCommission);
        if (CollectionUtils.isNotEmpty(sCommissionList)) {
            try {
                sCommissionDTOList = ReflectUtil.copyProperties4List(sCommissionList, SCommissionDTO.class);
            } catch (Exception e) {
                LOGGER.error("getCommissionList copyProperties4List method param currentHandler:{}, title:{} occur exception:{}", e.getMessage());
            }
            Set<Long> projectIdSet = Sets.newHashSet();
            sCommissionList.forEach(obj -> {
                projectIdSet.add(obj.getProjectId());
            });
            dealCommissionList(sCommissionDTOList, currentHandler, projectIdSet);
        }
        return new PageInfo(sCommissionDTOList);
    }

    /**
     * @param sCommissionDTOList
     * @param currentHandler
     * @param projectIdSet
     * @description 处理代办列表
     * @date 2021/2/1
     */
    private void dealCommissionList(List<SCommissionDTO> sCommissionDTOList, Long currentHandler, Set<Long> projectIdSet) {
        //根据用户id查询用户名称
        SsoUser ssoUser = iFacadeUserApi.queryUserById(currentHandler);
        //批量查询项目名称
        List<Long> projectIdList = Lists.newArrayList(projectIdSet);
        List<SsoProject> ssoProjectList = iFacadeProjectApi.getProjectListByProjectIds(projectIdList);
        if (CollectionUtils.isNotEmpty(ssoProjectList)) {
            Map<Long, String> projectMap = Maps.newHashMap();
            ssoProjectList.forEach(project -> {
                projectMap.put(project.getProjectId(), project.getProjectName());
            });
            for (SCommissionDTO sCommissionDTO : sCommissionDTOList) {
                Long projectId = sCommissionDTO.getProjectId();
                sCommissionDTO.setProjectName(projectMap.get(projectId));
                if (null != ssoUser) {
                    sCommissionDTO.setCurrentHandlerName(ssoUser.getUserName());
                }
            }
        }
    }

    @Override
    public SCommissionDTO getCommissionById(Long commissionId) {
        SCommissionDTO sCommissionDTO = new SCommissionDTO();
        SCommission sCommission = commissionMapper.selectByPrimaryKey(commissionId);
        if (sCommission != null) {
            sCommissionDTO = ReflectUtil.copyProperties(sCommission, sCommissionDTO.getClass());
            Long projectId = sCommissionDTO.getProjectId();
            if (null != projectId) {
                SsoProject ssoProject = iFacadeProjectApi.getProjectInfoById(projectId);
                if (null != ssoProject) {
                    sCommissionDTO.setProjectName(ssoProject.getProjectName());
                }
            }
            Long currentHandler = sCommission.getCurrentHandler();
            if (null != currentHandler) {
                SsoUser ssoUser = iFacadeUserApi.queryUserById(currentHandler);
                if (null != ssoUser) {
                    sCommissionDTO.setCurrentHandlerName(ssoUser.getUserName());
                }
            }
        }
        return sCommissionDTO;
    }

    @Override
    public SCommission getCommissionByIssueId(Long issueId) {
        SCommission sCommission = null;
        SCommissionExample sCommissionExample = new SCommissionExample();
        sCommissionExample.createCriteria().andIssueIdEqualTo(issueId);
        List<SCommission> sCommissionList = commissionMapper.selectByExample(sCommissionExample);
        if (CollectionUtils.isNotEmpty(sCommissionList)) {
            sCommission = sCommissionList.get(0);
        }
        return sCommission;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommission(SCommissionDTO sCommissionDTO) {
        SCommission sCommission = ReflectUtil.copyProperties(sCommissionDTO, SCommission.class);
        sCommission.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
        sCommission.setUpdateTime(new Date());
        int count = commissionMapper.updateCommissionByIssueId(sCommission);
        if (count != 1) {
            throw new RuntimeException("更新代办异常");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateCommission(boolean exist, SCommissionDTO sCommissionDTO, Long issueId) {
        if (exist) {
            updateCommission(sCommissionDTO);
            int result = saveCommissionRecord(issueId);
            if (result != 1) {
                throw new RuntimeException("保存代办记录异常");
            }
        } else {
            saveCommission(sCommissionDTO);
        }
    }

    /**
     * @param issueId
     * @return
     * @description 保存代办记录
     * @date 2021/2/1
     */
    private int saveCommissionRecord(Long issueId) {
        int count = 0;
        SCommissionExample sCommissionExample = new SCommissionExample();
        sCommissionExample.createCriteria().andIssueIdEqualTo(issueId);
        //查询代办是否存在
        List<SCommission> sCommissionList = commissionMapper.selectByExample(sCommissionExample);
        if (CollectionUtils.isNotEmpty(sCommissionList)) {
            Issue issue = issueMapper.selectByPrimaryKey(issueId);
            if (null != issue) {
                SCommission sCommission = sCommissionList.get(0);
                SCommissionRecord sCommissionRecord = generateCommission(issueId, issue, sCommission);
                count = commissionRecordMapper.insert(sCommissionRecord);
            }
        }
        return count;
    }

    private SCommissionRecord generateCommission(Long issueId, Issue issue, SCommission sCommission) {
        Long commissionId = sCommission.getId();
        SCommissionRecord sCommissionRecord = new SCommissionRecord();
        sCommissionRecord.setCommissonId(commissionId);
        sCommissionRecord.setIssueId(issueId);
        sCommissionRecord.setTitle(sCommission.getTitle());
        sCommissionRecord.setType(issue.getIssueType());
        sCommissionRecord.setHandler(issue.getHandler());
        sCommissionRecord.setProjectId(issue.getProjectId());
        sCommissionRecord.setStageId(issue.getStageId());
        sCommissionRecord.setLaneId(issue.getLaneId());
        sCommissionRecord.setState(StateEnum.U.getValue());
        sCommissionRecord.setCreateUid(issue.getCreateUid());
        sCommissionRecord.setCreateTime(new Date());
        sCommissionRecord.setTenantCode(UserThreadLocalUtil.getUserInfo().getTenantCode());
        return sCommissionRecord;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCommission(Long issueId) {
        LOGGER.info("updateCommissionState param issueId:{}", issueId);
        //关闭代办
        SCommissionExample sCommissionExample = new SCommissionExample();
        sCommissionExample.createCriteria().andIssueIdEqualTo(issueId);
        commissionMapper.deleteByExample(sCommissionExample);
    }
}

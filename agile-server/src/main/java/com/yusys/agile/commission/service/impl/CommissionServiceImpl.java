package com.yusys.agile.commission.service.impl;

import com.yusys.agile.commission.constants.CommissionConstant;
import com.yusys.agile.commission.dao.CommissionMapper;
import com.yusys.agile.commission.dao.CommissionRecordMapper;
import com.yusys.agile.commission.domain.Commission;
import com.yusys.agile.commission.domain.CommissionExample;
import com.yusys.agile.commission.domain.CommissionRecord;
import com.yusys.agile.commission.dto.CommissionDTO;
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
    public void saveCommission(CommissionDTO commissionDTO) {
        if (null == commissionDTO.getCurrentHandler()) {
            return;
        }
        Commission commission = ReflectUtil.copyProperties(commissionDTO, Commission.class);
        commission.setCreateUid(UserThreadLocalUtil.getUserInfo().getUserId());
        commission.setCreateTime(new Date());
        commission.setState(StateEnum.U.getValue());
        commission.setTenantCode(UserThreadLocalUtil.getUserInfo().getTenantCode());
        //保存代办
        int count = commissionMapper.insert(commission);
        if (count != 1) {
            throw new RuntimeException("保存代办异常");
        }
        saveCommissionRecord(commission);
    }

    /**
     * @param commission
     * @description 保存代办记录
     * @date 2021/2/1
     */
    private void saveCommissionRecord(Commission commission) {
        //保存代办记录
        CommissionRecord commissionRecord = getCommissionRecord(commission);
        int count = commissionRecordMapper.insert(commissionRecord);
        if (count != 1) {
            throw new RuntimeException("保存代办记录异常");
        }
    }

    private CommissionRecord getCommissionRecord(Commission commission) {
        Long commissionId = commission.getId();
        CommissionRecord commissionRecord = new CommissionRecord();
        commissionRecord.setCommissonId(commissionId);
        commissionRecord.setTitle(commission.getTitle());
        commissionRecord.setType(commission.getType());
        commissionRecord.setHandler(commission.getCurrentHandler());
        commissionRecord.setIssueId(commission.getIssueId());
        commissionRecord.setProjectId(commission.getProjectId());
        commissionRecord.setStageId(commission.getStageId());
        commissionRecord.setLaneId(commission.getLaneId());
        commissionRecord.setState(commission.getState());
        commissionRecord.setCreateTime(new Date());
        commissionRecord.setCreateUid(commission.getCreateUid());
        commissionRecord.setTenantCode(commission.getTenantCode());
        return commissionRecord;
    }

    @Override
    public void updateCommission(int type, CommissionDTO commissionDTO) {
        LOGGER.info("updateCommission method param issueId:{}", commissionDTO.getIssueId());
        Commission commission = ReflectUtil.copyProperties(commissionDTO, Commission.class);
        commission.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
        commission.setUpdateTime(new Date());
        commission.setTenantCode(UserThreadLocalUtil.getUserInfo().getTenantCode());
        int count = 0;
        if (type == CommissionConstant.ISSUE_TYPE) {
            count = commissionMapper.updateByIssueIdSelective(commission);
        } else if (type == CommissionConstant.PRIMARY_KEY_TYPE) {
            count = commissionMapper.updateByPrimaryKeySelective(commission);
        }
        if (count != 1) {
            throw new RuntimeException("更新代办异常");
        }
    }

    @Override
    public PageInfo<List<CommissionDTO>> getCommissionList(Long currentHandler, String title, Integer pageNum, Integer pageSize) {
        List<CommissionDTO> commissionDTOList = Lists.newArrayList();
        PageHelper.startPage(pageNum, pageSize);
        Commission commission = new Commission();
        commission.setCurrentHandler(currentHandler);
        commission.setTitle(title);
        List<Commission> commissionList = commissionMapper.selectCommissionList(commission);
        if (CollectionUtils.isNotEmpty(commissionList)) {
            try {
                commissionDTOList = ReflectUtil.copyProperties4List(commissionList, CommissionDTO.class);
            } catch (Exception e) {
                LOGGER.error("getCommissionList copyProperties4List method param currentHandler:{}, title:{} occur exception:{}", e.getMessage());
            }
            Set<Long> projectIdSet = Sets.newHashSet();
            commissionList.forEach(obj -> {
                projectIdSet.add(obj.getProjectId());
            });
            dealCommissionList(commissionDTOList, currentHandler, projectIdSet);
        }
        return new PageInfo(commissionDTOList);
    }

    /**
     * @param commissionDTOList
     * @param currentHandler
     * @param projectIdSet
     * @description 处理代办列表
     * @date 2021/2/1
     */
    private void dealCommissionList(List<CommissionDTO> commissionDTOList, Long currentHandler, Set<Long> projectIdSet) {
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
            for (CommissionDTO commissionDTO : commissionDTOList) {
                Long projectId = commissionDTO.getProjectId();
                commissionDTO.setProjectName(projectMap.get(projectId));
                if (null != ssoUser) {
                    commissionDTO.setCurrentHandlerName(ssoUser.getUserName());
                }
            }
        }
    }

    @Override
    public CommissionDTO getCommissionById(Long commissionId) {
        CommissionDTO commissionDTO = new CommissionDTO();
        Commission commission = commissionMapper.selectByPrimaryKey(commissionId);
        if (commission != null) {
            commissionDTO = ReflectUtil.copyProperties(commission, commissionDTO.getClass());
            Long projectId = commissionDTO.getProjectId();
            if (null != projectId) {
                SsoProject ssoProject = iFacadeProjectApi.getProjectInfoById(projectId);
                if (null != ssoProject) {
                    commissionDTO.setProjectName(ssoProject.getProjectName());
                }
            }
            Long currentHandler = commission.getCurrentHandler();
            if (null != currentHandler) {
                SsoUser ssoUser = iFacadeUserApi.queryUserById(currentHandler);
                if (null != ssoUser) {
                    commissionDTO.setCurrentHandlerName(ssoUser.getUserName());
                }
            }
        }
        return commissionDTO;
    }

    @Override
    public Commission getCommissionByIssueId(Long issueId) {
        Commission commission = null;
        CommissionExample commissionExample = new CommissionExample();
        commissionExample.createCriteria().andIssueIdEqualTo(issueId);
        List<Commission> commissionList = commissionMapper.selectByExample(commissionExample);
        if (CollectionUtils.isNotEmpty(commissionList)) {
            commission = commissionList.get(0);
        }
        return commission;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommission(CommissionDTO commissionDTO) {
        Commission commission = ReflectUtil.copyProperties(commissionDTO, Commission.class);
        commission.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
        commission.setUpdateTime(new Date());
        int count = commissionMapper.updateCommissionByIssueId(commission);
        if (count != 1) {
            throw new RuntimeException("更新代办异常");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateCommission(boolean exist, CommissionDTO commissionDTO, Long issueId) {
        if (exist) {
            updateCommission(commissionDTO);
            int result = saveCommissionRecord(issueId);
            if (result != 1) {
                throw new RuntimeException("保存代办记录异常");
            }
        } else {
            saveCommission(commissionDTO);
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
        CommissionExample commissionExample = new CommissionExample();
        commissionExample.createCriteria().andIssueIdEqualTo(issueId);
        //查询代办是否存在
        List<Commission> commissionList = commissionMapper.selectByExample(commissionExample);
        if (CollectionUtils.isNotEmpty(commissionList)) {
            Issue issue = issueMapper.selectByPrimaryKey(issueId);
            if (null != issue) {
                Commission commission = commissionList.get(0);
                CommissionRecord commissionRecord = generateCommission(issueId, issue, commission);
                count = commissionRecordMapper.insert(commissionRecord);
            }
        }
        return count;
    }

    private CommissionRecord generateCommission(Long issueId, Issue issue, Commission commission) {
        Long commissionId = commission.getId();
        CommissionRecord commissionRecord = new CommissionRecord();
        commissionRecord.setCommissonId(commissionId);
        commissionRecord.setIssueId(issueId);
        commissionRecord.setTitle(commission.getTitle());
        commissionRecord.setType(issue.getIssueType());
        commissionRecord.setHandler(issue.getHandler());
        commissionRecord.setProjectId(issue.getProjectId());
        commissionRecord.setStageId(issue.getStageId());
        commissionRecord.setLaneId(issue.getLaneId());
        commissionRecord.setState(StateEnum.U.getValue());
        commissionRecord.setCreateUid(issue.getCreateUid());
        commissionRecord.setCreateTime(new Date());
        commissionRecord.setTenantCode(UserThreadLocalUtil.getUserInfo().getTenantCode());
        return commissionRecord;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommissionState(Long issueId, String state) {
        LOGGER.info("updateCommissionState param issueId:{},state:{}", issueId, state);
        //关闭代办
        Commission commission = getCommissionByIssueId(issueId);
        if (null != commission) {
            commission = new Commission();
            commission.setIssueId(issueId);
            Issue issue = issueMapper.selectByPrimaryKey(issueId);
            if (null != issue) {
                commission.setStageId(issue.getStageId());
                commission.setLaneId(issue.getLaneId());
                commission.setCurrentHandler(issue.getHandler());
            }
            commission.setUpdateUid(UserThreadLocalUtil.getUserInfo().getUserId());
            commission.setUpdateTime(new Date());
            commission.setState(state);
            int count = commissionMapper.updateByIssueIdSelective(commission);
            LOGGER.info("updateCommissionState param issueId:{}, state:{}, affect row:{}", count);
            if (count != 1) {
                throw new RuntimeException("updateCommissionState issueId: " + issueId + " state: " + state + "异常");
            }
        }
    }
}

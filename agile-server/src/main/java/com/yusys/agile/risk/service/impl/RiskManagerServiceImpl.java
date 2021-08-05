package com.yusys.agile.risk.service.impl;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.constant.StringConstant;
import com.yusys.agile.projectmanager.dao.SProjectManagerMapper;
import com.yusys.agile.projectmanager.domain.SProjectManager;
import com.yusys.agile.projectmanager.domain.SProjectManagerExample;
import com.yusys.agile.risk.dao.RiskManagerMapper;
import com.yusys.agile.risk.domain.RiskManager;
import com.yusys.agile.risk.domain.RiskManagerExample;
import com.yusys.agile.risk.dto.RiskManagerDTO;
import com.yusys.agile.risk.enums.RiskLevelEnum;
import com.yusys.agile.risk.enums.RiskStatusEnum;
import com.yusys.agile.risk.service.RiskManagerService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName RiskManagerServiceImpl
 * @Description 风险管理实现类
 * @Date 2020/8/11 12:06
 * @Version 1.0
 */
@Slf4j
@Service
public class RiskManagerServiceImpl implements RiskManagerService {
    @Resource
    private RiskManagerMapper riskManagerMapper;
    @Resource
    private IFacadeSystemApi iFacadeSystemApi;
    @Resource
    private SProjectManagerMapper projectManagerMapper;
    @Resource
    private IFacadeUserApi userApi;

    private static Map<Long,String> USERMAP = new ConcurrentHashMap<>();

    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";

    @Override
    public List<RiskManagerDTO> getRiskPages(String title, Byte riskStatus, Integer pageNum, Integer pageSize, String projectName) {
        if (Optional.ofNullable(pageNum).isPresent() && Optional.ofNullable(pageSize).isPresent()) {
            PageHelper.startPage(pageNum, pageSize);
        }

//        RiskManagerExample riskManagerExample = new RiskManagerExample();
//        RiskManagerExample.Criteria criteria = riskManagerExample.createCriteria();
//        if (StringUtils.isNotEmpty(title)) {
//            criteria.andTitleLike(StringUtils.join(StringConstant.PERCENT_SIGN, title, StringConstant.PERCENT_SIGN));
//        }
//        if (Optional.ofNullable(riskStatus).isPresent()) {
//            criteria.andRiskStatusEqualTo(riskStatus);
//        }
//        riskManagerExample.setOrderByClause(CREATE_TIME_DESC);
        String tenantCode = UserThreadLocalUtil.getTenantCode();
        List<RiskManagerDTO> riskManagerDTOS = riskManagerMapper.selectByCondition(title, riskStatus, projectName, tenantCode);
        return getRiskManagerDTOS(riskManagerDTOS);
    }

    @Override
    public void create(RiskManagerDTO riskManagerDTO, SecurityDTO securityDTO) {
        RiskManager riskManager = ReflectUtil.copyProperties(riskManagerDTO, RiskManager.class);
        if (Optional.ofNullable(riskManager.getRiskId()).isPresent()) {
            RiskManager riskManager1 = riskManagerMapper.selectByPrimaryKey(riskManager.getRiskId());
            Optional.ofNullable(riskManager1).orElseThrow(() -> new BusinessException("更新的风险信息不存在"));
            riskManagerMapper.updateByPrimaryKeySelective(riskManager);
        } else {
            riskManager.setCreateName(UserThreadLocalUtil.getUserInfo().getUserName());
            riskManager.setProjectId(riskManagerDTO.getProjectId());
            log.info("获取项目Id:{}",riskManagerDTO.getProjectId());
            riskManagerMapper.insert(riskManager);
        }
    }

    @Override
    public void delete(Long riskId) {
        riskManagerMapper.deleteByPrimaryKey(riskId);
    }

    @Override
    public RiskManagerDTO getRiskInfo(Long riskId) {
        RiskManager riskManager = riskManagerMapper.selectByPrimaryKey(riskId);
        Optional.ofNullable(riskManager).orElseThrow(() -> new BusinessException("风险信息不存在!"));
        RiskManagerDTO riskManagerDTO = ReflectUtil.copyProperties(riskManager, RiskManagerDTO.class);
        Long systemId = riskManagerDTO.getSystemId();
        if (Optional.ofNullable(systemId).isPresent()) {
            SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(systemId);
            if (Optional.ofNullable(ssoSystem).isPresent()) {
                riskManagerDTO.setSystemName(ssoSystem.getSystemName());
            }
        }
        Long projectId = riskManagerDTO.getProjectId();
        String projectName = null;
        if(Optional.ofNullable(projectId).isPresent()){
            SProjectManagerExample example = new SProjectManagerExample();
            example.createCriteria().andStateEqualTo(StateEnum.U.getValue()).andProjectIdEqualTo(projectId);
            List<SProjectManager> sProjectManagers = projectManagerMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(sProjectManagers)){
                projectName = sProjectManagers.get(0).getProjectName();
            }

        }
        Long createUid = riskManager.getCreateUid();
        String userAccount = getUserAccount(createUid);
        riskManagerDTO.setCreateUserAccount(userAccount);

        riskManagerDTO.setProjectName(projectName);
        return riskManagerDTO;
    }

    @Override
    public JSONObject getRiskAnalysisCount(Long projectId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", NumberConstant.ZERO);
        jsonObject.put("count", NumberConstant.ZERO);
        RiskManagerExample riskManagerExample = new RiskManagerExample();
        RiskManagerExample.Criteria criteria = riskManagerExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        List<RiskManager> riskManagers = riskManagerMapper.selectByExample(riskManagerExample);

        RiskManagerExample riskManagerExample1 = new RiskManagerExample();
        RiskManagerExample.Criteria criteria1 = riskManagerExample1.createCriteria();
        criteria1.andProjectIdEqualTo(projectId);
        criteria1.andRiskStatusEqualTo(RiskStatusEnum.DOING.CODE);
        List<RiskManager> riskManagersDoing = riskManagerMapper.selectByExample(riskManagerExample1);
        if (CollectionUtils.isNotEmpty(riskManagers)) {
            jsonObject.put("total", riskManagers.size());
        }
        if (CollectionUtils.isNotEmpty(riskManagersDoing)) {
            jsonObject.put("count", riskManagersDoing.size());
        }

        return jsonObject;
    }

    @Override
    public List<RiskManagerDTO> getRisksColl(Date riskStartTime, Date riskEndTime, SecurityDTO securityDTO) {
        RiskManagerExample riskManagerExample = new RiskManagerExample();
        RiskManagerExample.Criteria criteria = riskManagerExample.createCriteria();
        criteria.andProjectIdEqualTo(securityDTO.getProjectId());

        if (Optional.ofNullable(riskStartTime).isPresent() && Optional.ofNullable(riskEndTime).isPresent()) {
            criteria.andRiskStartTimeGreaterThan(riskStartTime);
            criteria.andRiskStartTimeLessThan(riskEndTime);
        }
        riskManagerExample.setOrderByClause("create_time asc");
        List<RiskManagerDTO> riskManagers = riskManagerMapper.selectByExampleWithDTO(riskManagerExample);
        return getRiskManagerDTOS(riskManagers);
    }

    private List<RiskManagerDTO> getRiskManagerDTOS(List<RiskManagerDTO> riskManagers ) {
        if (CollectionUtils.isNotEmpty(riskManagers)) {
            riskManagers.forEach(riskManager -> {
                if (Optional.ofNullable(riskManager.getRiskLevel()).isPresent()) {
                    riskManager.setRiskLevelName(RiskLevelEnum.getName(riskManager.getRiskLevel()));
                }
                if (Optional.ofNullable(riskManager.getRiskStatus()).isPresent()) {
                    riskManager.setRiskStatusName(RiskStatusEnum.getName(riskManager.getRiskStatus()));
                }
                Long systemId = riskManager.getSystemId();
                if (Optional.ofNullable(systemId).isPresent()) {
                    SsoSystem ssoSystem = iFacadeSystemApi.querySystemBySystemId(systemId);
                    if (Optional.ofNullable(ssoSystem).isPresent()) {
                        riskManager.setSystemName(ssoSystem.getSystemName());
                    }
                }

                Long createUid = riskManager.getCreateUid();
                String userAccount = getUserAccount(createUid);
                riskManager.setCreateUserAccount(userAccount);
            });
        }
        return riskManagers;
    }

    private String getUserAccount(Long userId){
        if(!USERMAP.containsKey(userId) && Optional.ofNullable(userId).isPresent()){
            try {
                SsoUser ssoUser = userApi.queryUserById(userId);
                if(Optional.ofNullable(ssoUser).isPresent()){
                    USERMAP.put(userId,ssoUser.getUserAccount());
                }
            }catch (Exception e){
                log.info("获取人员信息异常：{}",e.getMessage());
            }

        }
        return USERMAP.get(userId);
    }

}

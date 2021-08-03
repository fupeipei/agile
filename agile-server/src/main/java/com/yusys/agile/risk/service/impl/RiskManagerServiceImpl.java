package com.yusys.agile.risk.service.impl;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.constant.StringConstant;
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
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName RiskManagerServiceImpl
 * @Description 风险管理实现类
 * @Date 2020/8/11 12:06
 * @Version 1.0
 */
@Service
public class RiskManagerServiceImpl implements RiskManagerService {
    @Resource
    private RiskManagerMapper riskManagerMapper;
    @Resource
    private IFacadeSystemApi iFacadeSystemApi;

    private static final String CREATE_TIME_DESC = "CREATE_TIME DESC";

    @Override
    public List<RiskManagerDTO> getRiskPages(String title, Byte riskStatus, Integer pageNum, Integer pageSize, SecurityDTO securityDTO) {
        if (Optional.ofNullable(pageNum).isPresent() && Optional.ofNullable(pageSize).isPresent()) {
            PageHelper.startPage(pageNum, pageSize);
        }

        RiskManagerExample riskManagerExample = new RiskManagerExample();
        RiskManagerExample.Criteria criteria = riskManagerExample.createCriteria();
        if (StringUtils.isNotEmpty(title)) {
            criteria.andTitleLike(StringUtils.join(StringConstant.PERCENT_SIGN, title, StringConstant.PERCENT_SIGN));
        }
        if (Optional.ofNullable(riskStatus).isPresent()) {
            criteria.andRiskStatusEqualTo(riskStatus);
        }
        riskManagerExample.setOrderByClause(CREATE_TIME_DESC);
        return getRiskManagerDTOS(riskManagerExample);
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
        return getRiskManagerDTOS(riskManagerExample);
    }

    private List<RiskManagerDTO> getRiskManagerDTOS(RiskManagerExample riskManagerExample) {
        List<RiskManagerDTO> riskManagers = riskManagerMapper.selectByExampleWithDTO(riskManagerExample);
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
            });
        }
        return riskManagers;
    }

}

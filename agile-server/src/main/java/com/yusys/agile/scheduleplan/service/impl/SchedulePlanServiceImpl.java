package com.yusys.agile.scheduleplan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueExample;
import com.yusys.agile.issue.enums.StartScheduleStatusEnum;
import com.yusys.agile.scheduleplan.dao.SEpicSystemRelateMapper;
import com.yusys.agile.scheduleplan.dao.SScheduleMapper;
import com.yusys.agile.scheduleplan.domain.SEpicSystemRelate;
import com.yusys.agile.scheduleplan.domain.SEpicSystemRelateExample;
import com.yusys.agile.scheduleplan.domain.SSchedule;
import com.yusys.agile.scheduleplan.domain.SScheduleExample;
import com.yusys.agile.scheduleplan.dto.ScheduleplanDTO;
import com.yusys.agile.scheduleplan.dto.SystemInfoDTO;
import com.yusys.agile.scheduleplan.dto.ToDoListDTO;
import com.yusys.agile.scheduleplan.service.SchedulePlanService;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.common.enums.YesOrNoEnum;
import com.yusys.portal.model.facade.entity.SsoSystem;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @Description: 需求排期
 *  @author: zhao_yd
 *  @Date: 2021/7/20 10:40 上午
 *
 */

@Slf4j
@Service
public class SchedulePlanServiceImpl implements SchedulePlanService {

    @Autowired
    private SScheduleMapper scheduleMapper;
    @Autowired
    private SEpicSystemRelateMapper epicSystemRelateMapper;
    @Autowired
    private IFacadeUserApi userApi;
    @Autowired
    private IFacadeSystemApi systemApi;
    @Autowired
    private IssueMapper issueMapper;

    private static Map<Long, SsoUser> USERMAP = new ConcurrentHashMap<>();
    private static Map<Long, String> SYSTEMMAP = new ConcurrentHashMap<>();


    @Override
    @Transactional
    public void saveSchedulePlan(ScheduleplanDTO scheduleplanDTO) {
        log.info("需求排期获取接口入参:{}", JSONObject.toJSONString(scheduleplanDTO));
        Long epicId = scheduleplanDTO.getEpicId();
        //删除之前数据
        SScheduleExample sScheduleExample = new SScheduleExample();
        sScheduleExample.createCriteria().andEpicIdEqualTo(epicId);
        SSchedule schedule = new SSchedule();
        schedule.setState(StateEnum.E.getValue());
        scheduleMapper.updateByExampleSelective(schedule,sScheduleExample);

        SEpicSystemRelateExample sEpicSystemRelateExample = new SEpicSystemRelateExample();
        sEpicSystemRelateExample.createCriteria().andEpicIdEqualTo(epicId);
        SEpicSystemRelate epicSystemRelate = new SEpicSystemRelate();
        epicSystemRelate.setState(StateEnum.E.getValue());
        epicSystemRelateMapper.updateByExampleSelective(epicSystemRelate,sEpicSystemRelateExample);

        List<SystemInfoDTO> systemInfo = scheduleplanDTO.getSystemInfo();
        if(CollectionUtils.isNotEmpty(systemInfo)){
            for(SystemInfoDTO systemInfoDTO : systemInfo){
                SEpicSystemRelate sEpicSystemRelate  = new SEpicSystemRelate();
                sEpicSystemRelate.setEpicId(epicId);
                sEpicSystemRelate.setCreateTime(new Date());
                sEpicSystemRelate.setSystemId(systemInfoDTO.getSystemId());
                sEpicSystemRelate.setSystemUid(systemInfoDTO.getSystemUid());
                sEpicSystemRelate.setState(StateEnum.U.getValue());
                Integer value = YesOrNoEnum.NO.getValue();
                sEpicSystemRelate.setIsHandle(Byte.valueOf(value.toString()));
                sEpicSystemRelate.setCreateUid(UserThreadLocalUtil.getUserInfo().getUserId());
                sEpicSystemRelate.setMaster(systemInfoDTO.getMaster());
                epicSystemRelateMapper.insertSelective(sEpicSystemRelate);
            }
        }
        SSchedule sSchedule = ReflectUtil.copyProperties(scheduleplanDTO, SSchedule.class);
        scheduleMapper.insertSelective(sSchedule);
    }

    @Override
    public ScheduleplanDTO getSchedulePlan(Long epicId) {
        SScheduleExample sScheduleExample = new SScheduleExample();
        sScheduleExample.createCriteria().andEpicIdEqualTo(epicId).andStateEqualTo(StateEnum.U.getValue());
        List<SSchedule> sSchedules = scheduleMapper.selectByExample(sScheduleExample);
        if(CollectionUtils.isNotEmpty(sSchedules)){
            SSchedule sSchedule = sSchedules.get(0);
            ScheduleplanDTO scheduleplanDTO = ReflectUtil.copyProperties(sSchedule, ScheduleplanDTO.class);

            SEpicSystemRelateExample epicSystemRelateExample = new SEpicSystemRelateExample();
            epicSystemRelateExample.createCriteria().andEpicIdEqualTo(epicId).andStateEqualTo(StateEnum.U.getValue());
            List<SEpicSystemRelate> sEpicSystemRelates = epicSystemRelateMapper.selectByExample(epicSystemRelateExample);
            if(CollectionUtils.isNotEmpty(sEpicSystemRelates)){
                try {
                    List<SystemInfoDTO> systemInfoDTOS = ReflectUtil.copyProperties4List(sEpicSystemRelates, SystemInfoDTO.class);
                    for(SystemInfoDTO systemInfoDTO:systemInfoDTOS){
                        Long systemId = systemInfoDTO.getSystemId();
                        String systemName = getSystemName(systemId);
                        Long systemUid = systemInfoDTO.getSystemUid();
                        SsoUser ssoUserInfo = getSsoUserInfo(systemUid);
                        String userAccount = ssoUserInfo.getUserAccount();
                        String userName = ssoUserInfo.getUserName();

                        systemInfoDTO.setSystemName(systemName);
                        systemInfoDTO.setSystemUserName(userName);
                        systemInfoDTO.setSystemUserAccount(userAccount);
                    }
                    scheduleplanDTO.setSystemInfo(systemInfoDTOS);
                } catch (Exception e) {
                    log.info("获取排期异常:{}",e.getMessage());
                }
            }
            return scheduleplanDTO;
        }
        return null;
    }

    @Override
    public List<ToDoListDTO> queryToDoList(String target,Integer pageNum, Integer pageSize) {
        if (Optional.ofNullable(pageNum).isPresent() && Optional.ofNullable(pageSize).isPresent()) {
            PageHelper.startPage(pageNum, pageSize);
        }

        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        Long epicId = null;
        try {
             epicId = Long.valueOf(target);
        }catch (Exception e){
            log.info("target不为Long类型{}",target);
        }

        List<ToDoListDTO> toDoListDTOS = epicSystemRelateMapper.queryToDoList(epicId, target, userId);
        if(CollectionUtils.isNotEmpty(toDoListDTOS)){
            for(ToDoListDTO toDoListDTO:toDoListDTOS){
                SsoUser ssoUserInfo = getSsoUserInfo(toDoListDTO.getCreateUid());
                String userAccount = ssoUserInfo.getUserAccount();
                String userName = ssoUserInfo.getUserName();
                toDoListDTO.setCreateUserAccount(userAccount);
                toDoListDTO.setCreateUserName(userName);
            }
            return toDoListDTOS;
        }
        return Lists.newArrayList();
    }

    @Override
    public void dealToDoList(Long relateId) {
        SEpicSystemRelateExample sEpicSystemRelateExample = new SEpicSystemRelateExample();
        sEpicSystemRelateExample.createCriteria().andRelateIdEqualTo(relateId).andStateEqualTo(StateEnum.U.getValue());
        SEpicSystemRelate sEpicSystemRelate = new SEpicSystemRelate();
        sEpicSystemRelate.setIsHandle(Byte.valueOf(YesOrNoEnum.YES.getValue().toString()));
        epicSystemRelateMapper.updateByExampleSelective(sEpicSystemRelate,sEpicSystemRelateExample);
    }

    @Override
    public void startSchedulePlan(Long epicId, Byte state) {
        StartScheduleStatusEnum[] values = StartScheduleStatusEnum.values();
        List<Byte> result = Lists.newArrayList();
        Arrays.asList(values).stream().forEach(s->{
            result.add(s.CODE);
        });

        if(!result.contains(state)){
            new BusinessException("传入参数错误");
        }

        IssueExample example = new IssueExample();
        example.createCriteria().andIssueIdEqualTo(epicId).andStateEqualTo(StateEnum.U.getValue());
        Issue issue = new Issue();
        issue.setStartSchedule(state);
        issueMapper.updateByExampleSelective(issue,example);
    }


    private String getSystemName(Long systemId){
        try {
            if(!SYSTEMMAP.containsKey(systemId)){
                SsoSystem ssoSystem = systemApi.querySystemBySystemId(systemId);
                if(Optional.ofNullable(ssoSystem).isPresent()){
                    SYSTEMMAP.put(systemId,ssoSystem.getSystemName());
                }
            }
            return SYSTEMMAP.get(systemId);
        }catch (Exception e){
            log.info("远程获取系统信息异常:{}",e.getMessage());
        }
      return null;
    }

    private SsoUser getSsoUserInfo(Long userId){
        try {
            if(!USERMAP.containsKey(userId)){
                SsoUser ssoUser = userApi.queryUserById(userId);
                if(Optional.ofNullable(ssoUser).isPresent()){
                    USERMAP.put(userId,ssoUser);
                }
            }
            return USERMAP.get(userId);
        }catch (Exception e){
            log.info("远程获取人员信息异常:{}",e.getMessage());
        }
        return new SsoUser();
    }

}

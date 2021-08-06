package com.yusys.agile.projectmanager.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.projectmanager.dao.SProjectUserDayMapper;
import com.yusys.agile.projectmanager.dao.SProjectUserHourMapper;
import com.yusys.agile.projectmanager.domain.*;
import com.yusys.agile.projectmanager.dto.*;
import com.yusys.agile.projectmanager.service.ProjectManagerService;
import com.yusys.agile.projectmanager.service.SProjectUserHourService;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: SProjectUserHourServiceImpl
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/03 16:57
 */
@Service
@Slf4j
public class SProjectUserHourServiceImpl implements SProjectUserHourService {

    @Autowired
    private SProjectUserHourMapper sProjectUserHourMapper;
    @Autowired
    private SProjectUserDayMapper sProjectUserDayMapper;
    @Autowired
    private ProjectManagerService projectManagerService;
    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private IssueService issueService;

    /**
     * @Author fupp1
     * @Description 获取项目下成员报工统计列表
     * @Date 17:52 2021/8/3
     * @Param [projectId, userId, startDate, endDate]
     * @return java.util.List<com.yusys.agile.projectmanager.dto.ProjectUserHourDto>
     **/
    @Override
    public List<ProjectUserTotalHourDto> listProjectUserHour(ProjectUserHourDto projectUserHourDto){
        // projectId不能为空,userId可以为空，开始时间和结束时间可以为空
        List<ProjectUserTotalHourDto> userList = null;
        try {
            if (!Optional.ofNullable(projectUserHourDto).isPresent() || !Optional.ofNullable(projectUserHourDto.getProjectId()).isPresent()) {
                log.error("获取项目下成员报工统计列表请求参数错误！");
                throw new BusinessException("获取项目下成员报工统计列表请求参数错误");
            }
            if (!Optional.ofNullable(projectUserHourDto.getPageNum()).isPresent() || !Optional.ofNullable(projectUserHourDto.getPageSize()).isPresent()){
                log.error("获取项目下成员报工统计列表请求参数错误！");
                throw new BusinessException("获取项目下成员报工统计列表请求参数错误");
            }
            // 校验开始时间和结束时间，结束时间不能早于开始时间
            if (Optional.ofNullable(projectUserHourDto.getStartDate()).isPresent()
                    && Optional.ofNullable(projectUserHourDto.getEndDate()).isPresent()){
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date_start = df.parse(projectUserHourDto.getStartDate());
                Date date_end = df.parse(projectUserHourDto.getEndDate());
                int compare = DateUtil.compare(date_start, date_end);
                if (compare > 0) {
                    log.error("获取项目下成员报工统计列表请求参数错误！");
                    throw new BusinessException("获取项目下成员报工统计列表请求参数错误");
                }
            }
            PageHelper.startPage(projectUserHourDto.getPageNum(),projectUserHourDto.getPageSize());
            //项目下员工id，进入项目时间
            userList = projectManagerService.queryUserIdListByProIdAndUId(projectUserHourDto.getProjectId(),projectUserHourDto.getUserId());
            // 获取项目下所有的员工id
            List<Long> userIdList = new ArrayList<>();
            userList.forEach(item -> userIdList.add(item.getUserId()));
            // 获取项目下所有员工信息
            List<SsoUser> ssoUsers = iFacadeUserApi.listUsersByIds(userIdList);
            //累计报工天数
            List<ProjectUserTotalHourDto> totalDays = sProjectUserDayMapper.getTotalDays(projectUserHourDto.getProjectId(),projectUserHourDto.getUserId(),
                    projectUserHourDto.getStartDate(),projectUserHourDto.getEndDate());
            //标准工时
            //项目id，员工id，开始时间，结束时间
            userList.forEach(item ->{
                ssoUsers.forEach(u -> {
                    if (Objects.equals(item.getUserId(), u.getUserId())) {
                        item.setUserName(u.getUserName());
                        item.setUserAccount(u.getUserAccount());
                    }
                });
                totalDays.forEach(days ->{
                    if (Objects.equals(item.getUserId(), days.getUserId())) {
                        item.setTotalReallyWorkload(days.getTotalReallyWorkload());
                        item.setTotalPlanWorkload(days.getTotalPlanWorkload());
                        item.setTotalDays(days.getTotalDays());
                    }
                });
            });
        } catch (ParseException e) {
            log.info("获取项目下成员报工统计列表:{}", e.getMessage());
            throw new BusinessException("获取项目下成员报工统计列表:{}", e.getMessage());
        }
        return userList;
    }

    /**
     * @return void
     * @Author fupp1
     * @Description 添加成员登记工时
     * @Date 20:26 2021/8/3
     * @Param [projectUserHourDto, securityDTO]
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editProjectUserHour(ProjectUserDayDto projectUserDayDto, SecurityDTO securityDTO) {
        if (!Optional.ofNullable(projectUserDayDto).isPresent()
                || !Optional.ofNullable(projectUserDayDto.getProjectId()).isPresent()
                || !Optional.ofNullable(projectUserDayDto.getWorkUid()).isPresent()){
            log.error("添加成员登记工时请求参数错误！");
            throw new BusinessException("添加成员登记工时请求参数错误");
        }
        //只允许本人修改自己的报工
        if (!Objects.equals(securityDTO.getUserId(),projectUserDayDto.getWorkUid())){
            log.error("只允许本人报工！");
            throw new BusinessException("只允许本人报工");
        }
        SProjectUserDay sProjectUserDay = new SProjectUserDay();
        BeanUtils.copyProperties(projectUserDayDto,sProjectUserDay);
        sProjectUserDay.setReallyWorkload(projectUserDayDto.getTotalReallyWorkload());
        if (Optional.ofNullable(projectUserDayDto.getDayId()).isPresent()){//编辑
            sProjectUserDay.setUpdateTime(new Date());
            sProjectUserDay.setUpdateUid(securityDTO.getUserId());
            sProjectUserDay.setTenantCode(securityDTO.getTenantCode());
            sProjectUserDayMapper.updateByPrimaryKeySelective(sProjectUserDay);
            //删除历史报工记录
            sProjectUserHourMapper.deleteByDayId(projectUserDayDto.getDayId());
        }else{//新建
            sProjectUserDay.setState(StateEnum.U.getValue());
            sProjectUserDay.setCreateTime(new Date());
            sProjectUserDay.setCreateUid(securityDTO.getUserId());
            sProjectUserDay.setTenantCode(securityDTO.getTenantCode());
            sProjectUserDayMapper.insertSelective(sProjectUserDay);
        }
        List<UserHourDto> userHours = projectUserDayDto.getUserHours();
        List<SProjectUserHour> projectUserHours = new ArrayList<>();
        SProjectUserHour sProjectUserHour;

        for (UserHourDto userHour : userHours) {
            sProjectUserHour = new SProjectUserHour();
            sProjectUserHour.setIssueId(userHour.getIssueId());
            sProjectUserHour.setReallyWorkload(userHour.getReallyWorkload());
            sProjectUserHour.setWorkDate(sProjectUserDay.getWorkDate());
            sProjectUserHour.setWorkUid(projectUserDayDto.getWorkUid());
            sProjectUserHour.setProjectId(projectUserDayDto.getProjectId());
            sProjectUserHour.setDayId(sProjectUserDay.getDayId());
            sProjectUserHour.setState(StateEnum.U.getValue());
            sProjectUserHour.setCreateTime(new Date());
            sProjectUserHour.setCreateUid(securityDTO.getUserId());
            sProjectUserHour.setTenantCode(securityDTO.getTenantCode());
            projectUserHours.add(sProjectUserHour);
        }
        if (Optional.ofNullable(projectUserHours).isPresent() && projectUserHours.size()>0){
            sProjectUserHourMapper.batchInsertSelective(projectUserHours);
        }
    }

    /**
     * @return com.yusys.agile.projectmanager.dto.ProjectUserHourDto
     * @Author fupp1
     * @Description 获取成员项目报工详情
     * @Date 20:26 2021/8/3
     * @Param [hourId]
     **/
    @Override
    public ProjectUserDayDto getProjectUserHourInfo(Long projectId, String workDate, Long userId) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = df.parse(workDate);
        //获取指定日期报工记录
        SProjectUserDayExample sProjectUserDayExample = new SProjectUserDayExample();
        sProjectUserDayExample.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andWorkUidEqualTo(userId).andWorkDateEqualTo(parse).andProjectIdEqualTo(projectId);
        List<SProjectUserDay> sProjectUserDays = sProjectUserDayMapper.selectByExampleWithBLOBs(sProjectUserDayExample);
        ProjectUserDayDto projectUserDayDto = new ProjectUserDayDto();
        if (Optional.ofNullable(sProjectUserDays).isPresent() && sProjectUserDays.size()>0){
            SProjectUserDay sProjectUserDay = sProjectUserDays.get(0);
            BeanUtils.copyProperties(sProjectUserDay,projectUserDayDto);

            SProjectUserHourExample hourExample = new SProjectUserHourExample();
            hourExample.createCriteria().andStateEqualTo(StateEnum.U.getValue()).andDayIdEqualTo(sProjectUserDay.getDayId());
            List<SProjectUserHour> projectUserHours = sProjectUserHourMapper.selectByExample(hourExample);
            List<UserHourDto> userHourDtoList = new ArrayList<>();
            UserHourDto userHourDto;
            for (SProjectUserHour projectUserHour : projectUserHours) {
                userHourDto = new UserHourDto();
                BeanUtils.copyProperties(projectUserHour,userHourDto);
                userHourDtoList.add(userHourDto);
            }
            projectUserDayDto.setUserHours(userHourDtoList);
        }
        return projectUserDayDto;
    }

    /**
     * @return com.yusys.agile.projectmanager.dto.ProjectHourDto
     * @Author fupp1
     * @Description 获取项目工时
     * @Date 10:12 2021/8/5
     * @Param [projectId]
     **/
    @Override
    public ProjectHourDto getProjectHourInfo(Long projectId) {
        ProjectHourDto projectHourDto = new ProjectHourDto();
        projectHourDto.setProjectId(projectId);
        //获取项目下所有的人(进入项目时间)
        List<ProjectUserTotalHourDto> totalHourDtos = projectManagerService.queryUserIdListByProIdAndUId(projectId, null);
        SProjectManager projectManager = projectManagerService.queryProjectManagerInfo(projectId);
        // 项目标准工时：SUM[（项目结束日期-人员进入项目日期）*8]
        Long normalWorkload = 0L;
        for (ProjectUserTotalHourDto totalHourDto : totalHourDtos) {
            normalWorkload += DateUtil.between(totalHourDto.getCreateTime(), projectManager.getEndTime(), DateUnit.DAY) * 8;
        }
        projectHourDto.setNormalWorkload(normalWorkload);
        // 项目预估工时：SUM（项目下所有工作项预估时间）
        List<Issue> issues = issueService.listIssueOfProjectAndUser(projectId, null);
        Long planWorkload = 0L;
        for (Issue issue : issues) {
            planWorkload += issue.getPlanWorkload();
        }
        projectHourDto.setPlanWorkload(planWorkload);
        // 实际工时：SUM（项目成员报工的实际工时）
        Long reallyWorkload = sProjectUserDayMapper.getTotalReallyWorkloadByProId(projectId);
        projectHourDto.setReallyWorkload(reallyWorkload);
        return projectHourDto;
    }
}

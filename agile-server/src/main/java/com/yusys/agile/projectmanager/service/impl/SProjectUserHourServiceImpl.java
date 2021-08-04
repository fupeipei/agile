package com.yusys.agile.projectmanager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.yusys.agile.projectmanager.dao.SProjectUserDayMapper;
import com.yusys.agile.projectmanager.dao.SProjectUserHourMapper;
import com.yusys.agile.projectmanager.dao.SProjectUserRelMapper;
import com.yusys.agile.projectmanager.domain.SProjectUserDay;
import com.yusys.agile.projectmanager.domain.SProjectUserDayExample;
import com.yusys.agile.projectmanager.domain.SProjectUserHour;
import com.yusys.agile.projectmanager.domain.SProjectUserHourExample;
import com.yusys.agile.projectmanager.dto.ProjectUserDayDto;
import com.yusys.agile.projectmanager.dto.ProjectUserHourDto;
import com.yusys.agile.projectmanager.dto.ProjectUserTotalHourDto;
import com.yusys.agile.projectmanager.dto.UserHourDto;
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
    private SProjectUserRelMapper sProjectUserRelMapper;
    @Autowired
    private IFacadeUserApi iFacadeUserApi;
    /**
     * @Author fupp1
     * @Description 获取项目下成员报工统计列表
     * @Date 17:52 2021/8/3
     * @Param [projectId, userId, startDate, endDate]
     * @return java.util.List<com.yusys.agile.projectmanager.dto.ProjectUserHourDto>
     **/
    @Override
    public List<ProjectUserTotalHourDto> listProjectUserHour(ProjectUserHourDto projectUserHourDto) throws ParseException {
        // projectId不能为空,userId可以为空，开始时间和结束时间可以为空
        if (!Optional.ofNullable(projectUserHourDto).isPresent() || !Optional.ofNullable(projectUserHourDto.getProjectId()).isPresent()) {
            log.error("获取项目下成员报工统计列表请求参数错误！");
            throw new BusinessException("获取项目下成员报工统计列表请求参数错误");
        }
        // 校验开始时间和结束时间，结束时间不能早于开始时间
        if (Optional.ofNullable(projectUserHourDto.getStartDate()).isPresent() && Optional.ofNullable(projectUserHourDto.getEndDate()).isPresent()){
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
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
        List<ProjectUserTotalHourDto> userList = sProjectUserRelMapper.queryUserIdListByProIdAndUId(projectUserHourDto.getProjectId(),projectUserHourDto.getUserId());
        // 获取项目下所有的员工id
        List<Long> userIdList = new ArrayList<>();
        userList.forEach(item -> userIdList.add(Long.valueOf(item.getUserId())));
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
    public void editProjectUserHour(ProjectUserDayDto projectUserDayDto, SecurityDTO securityDTO) {

    }

    /**
     * @return com.yusys.agile.projectmanager.dto.ProjectUserHourDto
     * @Author fupp1
     * @Description 获取成员项目报工详情
     * @Date 20:26 2021/8/3
     * @Param [hourId]
     **/
    @Override
    public ProjectUserDayDto getProjectUserHourInfo(Integer projectId, String workDate, Long userId) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date parse = df.parse(workDate);
        //获取指定日期报工记录
        SProjectUserDayExample sProjectUserDayExample = new SProjectUserDayExample();
        sProjectUserDayExample.createCriteria().andStateEqualTo(StateEnum.U.getValue())
                .andWorkUidEqualTo(userId).andWorkDateEqualTo(parse).andProjectIdEqualTo(projectId);
        List<SProjectUserDay> sProjectUserDays = sProjectUserDayMapper.selectByExample(sProjectUserDayExample);
        ProjectUserDayDto projectUserDayDto = new ProjectUserDayDto();
        if (Optional.ofNullable(sProjectUserDays).isPresent()){
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
}

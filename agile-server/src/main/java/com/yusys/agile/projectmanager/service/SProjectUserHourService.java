package com.yusys.agile.projectmanager.service;

import com.yusys.agile.projectmanager.dto.ProjectHourDto;
import com.yusys.agile.projectmanager.dto.ProjectUserDayDto;
import com.yusys.agile.projectmanager.dto.ProjectUserHourDto;
import com.yusys.agile.projectmanager.dto.ProjectUserTotalHourDto;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.text.ParseException;
import java.util.List;

/**
 * @ClassName: SProjectUserHourService
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/03 16:57
 */
public interface SProjectUserHourService {
    /**
     * @Author fupp1
     * @Description 获取项目下成员报工统计列表
     * @Date 17:52 2021/8/3
     * @Param [projectId, userId, startDate, endDate]
     * @return java.util.List<com.yusys.agile.projectmanager.dto.ProjectUserHourDto>
     **/
    List<ProjectUserTotalHourDto> listProjectUserHour(ProjectUserHourDto projectUserHourDto) throws ParseException;

    /**
     * @Author fupp1
     * @Description 添加成员登记工时
     * @Date 20:25 2021/8/3
     * @Param [projectUserHourDto, securityDTO]
     * @return void
     **/
    void editProjectUserHour(ProjectUserDayDto projectUserDayDto, SecurityDTO securityDTO);

    /**
     * @Author fupp1
     * @Description 获取成员项目报工详情
     * @Date 20:32 2021/8/3
     * @Param [hourId]
     * @return com.yusys.agile.projectmanager.dto.ProjectUserHourDto
     **/
    ProjectUserDayDto getProjectUserHourInfo(Long projectId, String workDate, Long userId) throws ParseException;

    ProjectHourDto getProjectHourInfo(Long projectId);
}

package com.yusys.agile.projectmanager.service;

import com.yusys.agile.projectmanager.dto.ProjectUserHourDto;

import java.util.List;

/**
 * @ClassName: SProjectUserHourService
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/03 16:57
 */
public interface SProjectUserHourService {

    List<ProjectUserHourDto> listProjectUserHour(Long projectId, Long userId, String startDate, String endDate);
}

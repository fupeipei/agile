package com.yusys.agile.projectmanager.service.impl;

import com.yusys.agile.projectmanager.dto.ProjectUserHourDto;
import com.yusys.agile.projectmanager.service.SProjectUserHourService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: SProjectUserHourServiceImpl
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/03 16:57
 */
@Service
public class SProjectUserHourServiceImpl implements SProjectUserHourService {
    @Override
    public List<ProjectUserHourDto> listProjectUserHour(Long projectId, Long userId, String startDate, String endDate) {
        return null;
    }
}

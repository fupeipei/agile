package com.yusys.agile.projectmanager.service;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.projectmanager.domain.SStaticProjectData;
import com.yusys.agile.projectmanager.dto.ProjectManagerDto;

import java.util.List;

public interface ProjectManagerService {

    ProjectManagerDto insertProjectManager(ProjectManagerDto projectManagerDto);

    List<SStaticProjectData> queryStaticDataByType(Integer type);

    PageInfo<ProjectManagerDto> queryProjectManagerPageInfo(Integer pageNum, Integer pageSize, String searchKey);
}

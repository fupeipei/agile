package com.yusys.agile.projectmanager.service;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.projectmanager.dto.ProjectDataDto;
import com.yusys.agile.projectmanager.dto.ProjectDemandDto;
import com.yusys.agile.projectmanager.dto.ProjectManagerDto;
import com.yusys.portal.model.facade.dto.SsoUserDTO;

import java.util.List;

public interface ProjectManagerService {

    ProjectManagerDto insertProjectManager(ProjectManagerDto projectManagerDto);

    List<ProjectDataDto> queryStaticData() throws Exception;

    PageInfo<ProjectManagerDto> queryProjectManagerPageInfo(Integer pageNum, Integer pageSize, String searchKey);

    ProjectManagerDto queryProjectManagerByProjectId(Long projectId);

    List<SsoUserDTO> querySsoUserByProjectId(Long projectId);

    ProjectManagerDto updateProjectManager(ProjectManagerDto projectManagerDto);

    List<ProjectDemandDto> queryProjectDemandList(Long projectId);

    List<ProjectManagerDto> queryProjectManagerList();
}

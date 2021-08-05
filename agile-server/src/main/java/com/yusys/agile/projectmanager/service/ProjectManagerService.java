package com.yusys.agile.projectmanager.service;

import com.github.pagehelper.PageInfo;
import com.yusys.agile.projectmanager.domain.SProjectManager;
import com.yusys.agile.projectmanager.dto.ProjectDataDto;
import com.yusys.agile.projectmanager.dto.ProjectDemandDto;
import com.yusys.agile.projectmanager.dto.ProjectManagerDto;
import com.yusys.agile.projectmanager.dto.StageNameAndValueDto;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.facade.entity.SsoUser;

import java.util.List;

public interface ProjectManagerService {

    ProjectManagerDto insertProjectManager(ProjectManagerDto projectManagerDto);

    List<ProjectDataDto> queryStaticData() throws Exception;

    PageInfo<ProjectManagerDto> queryProjectManagerPageInfo(Integer pageNum, Integer pageSize, String searchKey);

    ProjectManagerDto queryProjectManagerByProjectId(Long projectId);

    List<SsoUserDTO> querySsoUserByProjectId(Long projectId);

    ProjectManagerDto updateProjectManager(ProjectManagerDto projectManagerDto);

    List<SProjectManager> queryProjectManagers();

    List<SsoUser> queryUserByProjectId(Long projectId);

    List<ProjectDemandDto> queryProjectDemandList(Long projectId);

    List<ProjectManagerDto> queryProjectManagerList();
}

package com.yusys.agile.projectmanager.service;

import com.yusys.agile.projectmanager.domain.SProjectSystemRel;

import java.util.List;

public interface ProjectSystemRelService {

    List<SProjectSystemRel> queryProjectSystemRelList(Long projectId);
}

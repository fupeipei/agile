package com.yusys.agile.projectmanager.service;

import com.yusys.agile.projectmanager.domain.SStaticProjectData;

public interface StaticProjectDataService {

    SStaticProjectData queryStaticProjectDataById(Long projectStatusId);
}

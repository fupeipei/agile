package com.yusys.agile.projectmanager.service;

import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.facade.dto.SsoUserDTO;

import java.util.List;

public interface ProjectUserRelService {

    PageInfo<SsoUserDTO> queryUserInfoByCondition(Integer pageNum, Integer pageSize, String searchKey, Long projectId);
}

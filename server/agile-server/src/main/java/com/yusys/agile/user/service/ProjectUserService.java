package com.yusys.agile.user.service;


import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.facade.entity.SsoUser;

import java.util.List;


/**
 * @Date: 2021/2/6
 */
public interface ProjectUserService {

    /**
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @Date 2021/2/6
     * @Description 项目概览中获取人员信息
     * @Return com.github.pagehelper.PageInfo
     */
    PageInfo projectUserInfo(Long projectId, Integer pageNum, Integer pageSize);

    /**
     * @param projectId
     * @param pageNum
     * @param pageSize
     * @param ssoUserList
     * @Date 2021/2/16
     * @Description 封装人员信息
     * @Return com.github.pagehelper.PageInfo
     */
    PageInfo commitUserInfo(Long projectId, Long sprintId, Integer pageNum, Integer pageSize, List<SsoUser> ssoUserList);
}

package com.yusys.agile.buildrecords.service;

import com.github.pagehelper.PageInfo;

/**
 * @descriptin 构建部署业务类
 * @date 2021/2/1
 */
public interface BuildDeployService {

    /**
     * @param issueId
     * @param pageNum
     * @param pageSize
     * @return
     * @description 查询流水线构建记录
     */
    PageInfo queryBuildRecord(Long issueId, Integer pageNum, Integer pageSize);

    /**
     * @param issueId
     * @param pageNum
     * @param pageSize
     * @return
     * @description 查询流水线部署记录
     */
    PageInfo queryDeployRecord(Long issueId, Integer pageNum, Integer pageSize);
}

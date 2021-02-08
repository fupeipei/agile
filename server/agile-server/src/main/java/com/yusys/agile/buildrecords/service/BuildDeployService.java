package com.yusys.agile.buildrecords.service;

import com.github.pagehelper.PageInfo;

/**
 * @descriptin 构建部署业务类
 * @date 2021/2/1
 */
public interface BuildDeployService {

    /**
     * @description 查询流水线构建记录
     * @param issueId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo queryBuildRecord(Long issueId, Integer pageNum, Integer pageSize);

    /**
     * @description 查询流水线部署记录
     * @param issueId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo queryDeployRecord(Long issueId, Integer pageNum, Integer pageSize);
}

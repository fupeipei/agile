package com.yusys.agile.versionmanager.service;


import com.yusys.agile.utils.page.PageQuery;
import com.yusys.agile.versionmanager.dto.BjVersionApproveResultDTO;

import java.util.List;

public interface VersionApproveResultService {
    /**
     * @param query
     * @Date 2021/2/8
     * @Description 获取版本审批结果详情信息
     * @Return java.util.List<com.yusys.agile.versionmanager.dto.BjVersionApproveResultDTO>
     */
    List<BjVersionApproveResultDTO> getAllRecordsByPaging(PageQuery<BjVersionApproveResultDTO> query);

    /**
     * @param versionApproveResult
     * @Date 2021/2/8
     * @Description 获取版本审批结果总数
     * @Return int
     */
    int countAllRecords(BjVersionApproveResultDTO versionApproveResult);
}

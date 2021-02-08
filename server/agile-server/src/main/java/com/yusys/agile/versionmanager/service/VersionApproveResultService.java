package com.yusys.agile.versionmanager.service;


import com.yusys.agile.utils.page.PageQuery;
import com.yusys.agile.versionmanager.dto.BjVersionApproveResultDTO;

import java.util.List;

public interface VersionApproveResultService {
    /**
     * @Author maxp
     * @Date 2020/11/17
     * @Description 获取版本审批结果详情信息
     * @param query
     * @Return java.util.List<com.ai.req.agile.versionmanager.dto.BjVersionApproveResultDTO>
     */
    List<BjVersionApproveResultDTO> getAllRecordsByPaging(PageQuery<BjVersionApproveResultDTO> query);

    /**
     * @Author maxp
     * @Date 2020/11/17
     * @Description 获取版本审批结果总数
     * @param versionApproveResult
     * @Return int
     */
    int countAllRecords(BjVersionApproveResultDTO versionApproveResult);
}

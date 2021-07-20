package com.yusys.agile.openapi.service;

import com.yusys.agile.issue.dto.IssueDTO;

import java.util.List;

public interface IssueSyncService {

    /**
     * 新建epic  feature 时同步
     * @param list
     */
    void issueSyncAdd(List<IssueDTO> list);

    /**
     * 修改epic  feature 时同步
     * @param list
     */
    void issueSyncUpdate(List<IssueDTO> list);


    /**
     * 删除epic  feature 时同步
     * @param list
     */
    void issueSyncDelete(List<Long> list);


    /**
     * 测试平台更新需求的阶段与状态时  feature 时同步
     * @param list
     */
    void issueSyncStageIdAndLaneId(List<IssueDTO> list);
}

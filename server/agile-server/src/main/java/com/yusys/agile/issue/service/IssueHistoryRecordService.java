package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.dto.IssueHistoryRecordDTO;

import java.util.List;

public interface IssueHistoryRecordService {

    /**
     * @Date: 18:07
     * @Description: 获取工作项相关的历史记录列表
     * @Param: * @param issueId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueHistoryRecordDTO>
     */
    List<IssueHistoryRecordDTO> listIssueHistoryRecord(Long issueId);

    /**
     * @Date: 18:07
     * @Description: T创建历史记录
     * @Param: * @param history
     * @Return: int
     */
    int createHistory(IssueHistoryRecord history);

    /**
     * @Date: 18:07
     * @Description: 批量新增历史记录
     * @Param: * @param history
     * @Return: int
     */
    int createBatchHistory(List<IssueHistoryRecord> history);
}

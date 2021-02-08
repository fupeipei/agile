package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueHistoryRecordMapper;
import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.domain.IssueHistoryRecordExample;
import com.yusys.agile.issue.dto.IssueHistoryRecordDTO;
import com.yusys.agile.issue.service.IssueHistoryRecordService;
import com.yusys.portal.util.code.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @Date: 9:19
 */
@Service
public class IssueHistoryRecordServiceImpl implements IssueHistoryRecordService {

    private static final Logger log = LoggerFactory.getLogger(IssueHistoryRecordServiceImpl.class);

    @Resource
    private IssueHistoryRecordMapper issueHistoryRecordMapper;

    @Override
    public List<IssueHistoryRecordDTO> listIssueHistoryRecord(Long issueId) {
        List<IssueHistoryRecordDTO> issueHistoryRecordDTOList = new ArrayList<>();
        IssueHistoryRecordExample example = new IssueHistoryRecordExample();
        IssueHistoryRecordExample.Criteria criteria = example.createCriteria();
        criteria.andIssueIdEqualTo(issueId);
        List<IssueHistoryRecord> issueHistoryRecordList = issueHistoryRecordMapper.selectByExample(example);

        try{
            issueHistoryRecordDTOList = ReflectUtil.copyProperties4List(issueHistoryRecordList,IssueHistoryRecordDTO.class);
        }catch(Exception e){
            log.error("列表转换出错{}",e.getMessage());
        }
        return issueHistoryRecordDTOList;
    }

    @Override
    public int createHistory(IssueHistoryRecord history) {
        return issueHistoryRecordMapper.insertSelective(history);
    }

    @Override
    public int createBatchHistory(List<IssueHistoryRecord> history) {
        return issueHistoryRecordMapper.createBatchHistory(history);
    }
}

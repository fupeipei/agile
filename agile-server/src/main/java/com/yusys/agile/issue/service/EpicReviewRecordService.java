package com.yusys.agile.issue.service;

import com.yusys.agile.issue.dto.EpicReviewDto;
import com.yusys.agile.issue.dto.EpicReviewRecordDto;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: EpicReviewRecordService
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/12 14:43
 */
public interface EpicReviewRecordService {

    void saveReview(@Valid EpicReviewDto epicReviewRDto, SecurityDTO securityDTO);

    List<EpicReviewRecordDto> listEpicReview(Long issueId);

    void removeEpicReviewRecord(Long recordId, SecurityDTO securityDTO);
}

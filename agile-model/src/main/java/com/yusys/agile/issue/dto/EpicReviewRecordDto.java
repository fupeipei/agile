package com.yusys.agile.issue.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: EpicReviewRecordDto
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/12 14:52
 */
@Data
public class EpicReviewRecordDto {
    /**
     * 工作项id
     **/
    private Long issueId;
    /**
     * 评论id
     **/
    private Long recordId;
    /**
     * 评分
     **/
    private String reviewScore;
    /**
     * 评价
     **/
    private String reviewDesc;
    /**
     * 评价时间
     **/
    private Date createTime;
    /**
     * 评价人
     **/
    private String userName;
    /**
     * 评价人id
     **/
    private Long createUid;

}

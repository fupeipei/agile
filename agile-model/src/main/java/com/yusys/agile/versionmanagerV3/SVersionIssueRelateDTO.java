package com.yusys.agile.versionmanagerV3;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SVersionIssueRelateDTO implements Serializable {

    private Long relateId;

    private Long issueId;

    private Long issueType;

    private Long versionId;

    /**
     * 上线时间
     */
    private Date releaseDate;

    /**
     * 提测时间时间
     */
    private Date raiseTestDate;

    /**
     * 排期名称
     */
    private String scheduleName;

    // 阶段id
    private Long stageId;

    private Long laneId;

    private Long teamId;

    private String teamName;

    private String langName;

    private String stageName;
}

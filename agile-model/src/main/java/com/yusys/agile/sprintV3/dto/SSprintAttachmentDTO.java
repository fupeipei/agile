package com.yusys.agile.sprintV3.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author shenfeng
 * @description
 * @date 2021/6/28
 */
@Data
public class SSprintAttachmentDTO{
    private Long attachmentId;

    private Long sprintId;

    private String fileUri;

    private String fileName;

    private Integer fileSize;

    private Date uploadTime;

    private Long uploadUid;

    private String tenantCode;

}

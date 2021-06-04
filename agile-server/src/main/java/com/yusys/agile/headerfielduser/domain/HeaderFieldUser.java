package com.yusys.agile.headerfielduser.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class HeaderFieldUser implements Serializable {
    private Long headerUserId;

    private Long fieldId;

    private Long userId;

    private Long projectId;

    private Integer orderNo;

    private Byte fieldType;

    private Byte apply;

    private Byte category;

    private Byte isFilter;

    private String state;

    private String tenantCode;

    private String fieldContent;

    private static final long serialVersionUID = 1L;
}
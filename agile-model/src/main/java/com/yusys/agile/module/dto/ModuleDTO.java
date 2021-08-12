package com.yusys.agile.module.dto;

import lombok.Data;

import java.util.Date;

/**
 * 模块管理Model辅助类
 *
 * @date 2020.5.25
 */
@Data
public class ModuleDTO {
    private Long moduleId;

    private Long systemId;

    private String moduleName;

    private String productName;

    private String state;

    private Long createUid;

    private String userAccount;

    private String userName;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String moduleDesc;

}

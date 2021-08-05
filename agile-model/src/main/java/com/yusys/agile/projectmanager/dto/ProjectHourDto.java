package com.yusys.agile.projectmanager.dto;

import lombok.Data;

/**
 * @ClassName: ProjectHourDto
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/05 10:05
 */
@Data
public class ProjectHourDto {
    /**
     * 项目id
     **/
    private Long projectId;
    /**
     * 项目预估工时
     **/
    protected Long planWorkload;
    /**
     * 实际工时
     **/
    private Long reallyWorkload;
    /**
     * 项目标准工时
     **/
    private Long normalWorkload;

}

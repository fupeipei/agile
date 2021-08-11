package com.yusys.agile.projectmanager.dto;

import lombok.Data;


@Data
public class ProjectDemandDto {

    private Long systemId;

    //系统的名称
    private String systemName;

    //就绪阶段
    private Long readyValue = 0L;

    //分析的值
    private Long analyseValue = 0L;

    //设计的值
    private Long designValue = 0L;

    //开发开发
    private Long devlopmentValue = 0L;

    //测试的值
    private Long testValue = 0L;

    //系统测试的值
    private Long systemTestValue = 0L;
    //发布阶段的值
    private Long releaseValue = 0L;

    //完成阶段的值
    private Long completeValue = 0L;


}

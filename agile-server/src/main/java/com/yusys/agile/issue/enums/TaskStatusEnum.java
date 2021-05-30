package com.yusys.agile.issue.enums;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Date 2021/2/8
 * @Description 任务状态枚举
 */
public enum TaskStatusEnum {
    TYPE_ADD_STATE("未领取", 107L),
    TYPE_RECEIVED_STATE("已领取", 108L),
    TYPE_MODIFYING_STATE("进行中", 109L),
    TYPE_CLOSED_STATE("已完成", 110L);

    public Long CODE;
    private String NAME;



    TaskStatusEnum(String name, Long code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Long code) {
        for (TaskStatusEnum stateType : TaskStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static TaskStatusEnum getByCode(Long code) {
        for (TaskStatusEnum stateType : TaskStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }

    public static Map<Long, String> getTaskAllStageId() {
        Map<Long, String> taskAll = new LinkedHashMap<>();
        for (TaskStatusEnum taskStage : TaskStatusEnum.values()) {
            Long code = taskStage.CODE;
            String name = taskStage.NAME;
            taskAll.put(code, name);
        }
        return taskAll;
    }
}

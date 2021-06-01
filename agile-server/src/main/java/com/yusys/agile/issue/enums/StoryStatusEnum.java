package com.yusys.agile.issue.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Date 2021/2/8
 * @Description 故事状态枚举
 */
public enum StoryStatusEnum {
    TYPE_ADD_STATE("未开始", 104L),
    TYPE_MODIFYING_STATE("进行中", 105L),
    TYPE_CLOSED_STATE("已完成", 106L);

    public Long CODE;
    private String NAME;


    StoryStatusEnum(String name, Long code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Long code) {
        for (StoryStatusEnum stateType : StoryStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static StoryStatusEnum getByCode(Long code) {
        for (StoryStatusEnum stateType : StoryStatusEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }

    public static Map<Long, String> getTaskAllStageId() {
        Map<Long, String> taskAll = new LinkedHashMap<>();
        for (StoryStatusEnum taskStage : StoryStatusEnum.values()) {
            Long code = taskStage.CODE;
            String name = taskStage.NAME;
            taskAll.put(code, name);
        }
        return taskAll;
    }
}

package com.yusys.agile.issue.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Date 2021/2/8
 * @Description 故事状态枚举
 */
public enum StoryStageIdEnum {
    TYPE_ADD_STATE(         "未开始", 110L),
    TYPE_MODIFYING_STATE(   "进行中", 111L),
    TYPE_CLOSED_STATE(      "已完成", 112L);

    public Long CODE;
    public String NAME;


    private StoryStageIdEnum(String name, Long code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Long code) {
        for (StoryStageIdEnum stateType : StoryStageIdEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static StoryStageIdEnum getByCode(Long code) {
        for (StoryStageIdEnum stateType : StoryStageIdEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }

    public static Map<Long, String> getTaskAllStageId() {
        Map<Long, String> taskAll = new LinkedHashMap<>();
        for (StoryStageIdEnum taskStage : StoryStageIdEnum.values()) {
            Long code = taskStage.CODE;
            String name = taskStage.NAME;
            taskAll.put(code, name);
        }
        return taskAll;
    }
}

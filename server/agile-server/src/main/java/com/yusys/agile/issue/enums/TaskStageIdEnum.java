package com.yusys.agile.issue.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *    maxp
 * @Date 2020/5/8
 * @Description 任务状态枚举
 */
public enum TaskStageIdEnum {
    TYPE_ADD_STATE("未领取",100L),
    TYPE_RECEIVED_STATE("已领取",101L),
    TYPE_MODIFYING_STATE("进行中",102L),
    TYPE_BLOCKING_STATE("阻塞中",9999L),
    TYPE_CLOSED_STATE("已完成",103L);

    public Long CODE;
    public String NAME;


    private TaskStageIdEnum(String name, Long code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Long code) {
        for (TaskStageIdEnum stateType : TaskStageIdEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static TaskStageIdEnum getByCode(Long code) {
        for (TaskStageIdEnum stateType : TaskStageIdEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }
    public static Map<Long,String> getTaskAllStageId(){
        Map<Long, String> taskAll = new LinkedHashMap<>();
        for (TaskStageIdEnum taskStage : TaskStageIdEnum.values()) {
            Long code = taskStage.CODE;
            String name = taskStage.NAME;
            taskAll.put(code, name);
        }
        return taskAll;
    }
}

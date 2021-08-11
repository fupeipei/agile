package com.yusys.agile.issue.enums;

public enum EpicStageEnum {

    READY("就绪阶段", 1L),
    ANALYSE("分析阶段", 2L),
    DESIGN("设计阶段", 3L),
    DEVELOPMENT("开发阶段", 4L),
    TEST("测试阶段", 5L),
    SYSTEM_TEST("系统测试阶段", 6L),
    RELEASE("发布阶段", 7L),
    COMPLETE("完成阶段", 8L);

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    EpicStageEnum(String name, Long stageId) {
        Name = name;
        this.stageId = stageId;
    }

    private String Name;

    private Long stageId;

}

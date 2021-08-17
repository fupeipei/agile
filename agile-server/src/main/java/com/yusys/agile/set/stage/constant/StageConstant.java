package com.yusys.agile.set.stage.constant;

/**
 * @description 阶段常量类
 * @date 2020/05/15
 */
public class StageConstant {
    /**
     * 父阶段编号
     */
    public static final Long PARENT_STAGE_ID = -1L;

    /**
     * 有效状态
     */
    public static final String STATE_VALIDATE = "U";

    /**
     * 无效状态
     */
    public static final String STATE_INVALIDATE = "E";

    /**
     * @description 一级阶段
     */
    public enum FirstStageEnum {
        READY_STAGE("就绪阶段", 1L), ANALYSIS_STAGE("分析阶段", 2L), DESIGN_STAGE("设计阶段", 3L),
        DEVELOP_STAGE("开发阶段", 4L), TEST_STAGE("测试阶段", 5L), SYS_TEST_STAGE("系统测试阶段", 6L), ONLINE_STAGE("发布阶段", 7L), FINISH_STAGE("完成阶段", 8L);

        private String name;
        private Long value;

        FirstStageEnum(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Long getValue() {
            return value;
        }

        public static String getFirstStageName(Long id) {
            String name = null;
            for (FirstStageEnum firstStageEnum : FirstStageEnum.values()) {
                if (id.equals(firstStageEnum.getValue())) {
                    name = firstStageEnum.getName();
                    break;
                }
            }
            return name;
        }
    }

    /**
     * @description 阶段层级
     */
    public enum StageLevelEnum {
        FIRST_LEVEL_STAGE("一级阶段", (byte) 1), SECOND_LEVEL_STAGE("二级状态", (byte) 2);

        private String name;
        private Byte value;

        StageLevelEnum(String name, Byte value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Byte getValue() {
            return value;
        }
    }
}

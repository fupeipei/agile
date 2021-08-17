package com.yusys.agile.leankanban.enums;

/**
 *  @Description: 精益看板状态
 *  @author: zhao_yd
 *  @Date: 2021/6/15 6:22 下午
 *
 */

public class LaneKanbanStageConstant {


    /**
     * @description 分析中阶段
     */
    public enum AnalysisStageEnum {
        ONGOING("进行中", 100L),
        FINISH("已完成", 101L);

        private String name;
        private Long value;

        AnalysisStageEnum(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Long getValue() {
            return value;
        }

        public static String getStageName(Long id) {
            String name = null;
            for (AnalysisStageEnum firstStageEnum : AnalysisStageEnum.values()) {
                if (id.equals(firstStageEnum.getValue())) {
                    name = firstStageEnum.getName();
                    break;
                }
            }
            return name;
        }
    }

    /**
     * @description 设计中阶段
     */
    public enum DesignStageEnum {
        ONGOING("进行中", 102L),
        FINISH("已完成", 103L);

        private String name;
        private Long value;

        DesignStageEnum(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Long getValue() {
            return value;
        }

        public static String getStageName(Long id) {
            String name = null;
            for (AnalysisStageEnum firstStageEnum : AnalysisStageEnum.values()) {
                if (id.equals(firstStageEnum.getValue())) {
                    name = firstStageEnum.getName();
                    break;
                }
            }
            return name;
        }
    }

    /**
     * @description 开发中阶段
     */
    public enum DevStageEnum {

        /**故事状态**/
        NOTSTARTED("未开始",104L),
        ONGOING("进行中", 105L),
        FINISH("已完成", 106L),

        /**任务状态**/
        DEVELOPING("开发中",109L),
        DEVFINISH("开发完成",110L);

        private String name;
        private Long value;

        DevStageEnum(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Long getValue() {
            return value;
        }

        public static String getStageName(Long id) {
            String name = null;
            for (AnalysisStageEnum firstStageEnum : AnalysisStageEnum.values()) {
                if (id.equals(firstStageEnum.getValue())) {
                    name = firstStageEnum.getName();
                    break;
                }
            }
            return name;
        }
    }

    /**
     * @description 测试中阶段
     */
    public enum TestStageEnum {
        TESTING("测试中", 111L),
        TESTFINISH("测试完成", 112L);

        private String name;
        private Long value;

        TestStageEnum(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Long getValue() {
            return value;
        }

        public static String getStageName(Long id) {
            String name = null;
            for (AnalysisStageEnum firstStageEnum : AnalysisStageEnum.values()) {
                if (id.equals(firstStageEnum.getValue())) {
                    name = firstStageEnum.getName();
                    break;
                }
            }
            return name;
        }
    }

    /**
     * @description 系统测试中阶段
     */
    public enum SystemTestStageEnum {
        ONGOING("进行中", 113L),
        FINISH("已完成", 114L);

        private String name;
        private Long value;

        SystemTestStageEnum(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Long getValue() {
            return value;
        }

        public static String getStageName(Long id) {
            String name = null;
            for (AnalysisStageEnum firstStageEnum : AnalysisStageEnum.values()) {
                if (id.equals(firstStageEnum.getValue())) {
                    name = firstStageEnum.getName();
                    break;
                }
            }
            return name;
        }
    }


    /**
     * @description 发布阶段
     */
    public enum ReleaseStageEnum {
        ONGOING("进行中", 115L),
        FINISH("已完成", 1116L);

        private String name;
        private Long value;

        ReleaseStageEnum(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Long getValue() {
            return value;
        }

        public static String getStageName(Long id) {
            String name = null;
            for (AnalysisStageEnum firstStageEnum : AnalysisStageEnum.values()) {
                if (id.equals(firstStageEnum.getValue())) {
                    name = firstStageEnum.getName();
                    break;
                }
            }
            return name;
        }
    }

}

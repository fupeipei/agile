package com.yusys.agile.issue.enums;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**

 * @Date 2020/4/30
 * @Description 任务类型枚举
 */
public enum TaskTypeEnum {
    FRONT("前端", 1),
    DESIGN("设计", 2),
    REAR("后端", 3),
    TEST("测试", 4);
    /*TYPE_ANALYSE("分析", 1),
    TYPE_DESIGN("设计", 2),
    TYPE_DEVELOP("开发", 3),
    TYPE_TEST("测试", 4),
    TYPE_DISPOSE("配置", 5),
    TYPE_ENVIRONMENT("环境", 6),
    TYPE_WORD("文档",7),
    TYPE_REVIEW("评审", 8),
    TYPE_STUDY("学习", 9),
    TYPE_FAULT("缺陷", 10),
    TYPE_CHECK("验证", 11);*/

    public Integer CODE;
    public String NAME;


    private TaskTypeEnum(String name, Integer code) {
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (TaskTypeEnum stateType : TaskTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType.NAME;
            }
        }
        return "";
    }

    /**
     * 功能描述: 获取任务的code
     *
     * @param name
     * @return java.lang.Integer

     * @date 2021/2/17
     */
    public static Integer getCode(String name) {
        for (TaskTypeEnum stateType : TaskTypeEnum.values()) {
            if (stateType.NAME.equals(name)) {
                return stateType.CODE;
            }
        }
        return null;
    }

    // 普通方法
    public static TaskTypeEnum getByCode(Integer code) {
        for (TaskTypeEnum stateType : TaskTypeEnum.values()) {
            if (stateType.CODE.equals(code)) {
                return stateType;
            }
        }
        return null;
    }

    /**
     * 功能描述: 获取所有name数组
     *
     * @param
     * @return java.lang.String[]

     * @date 2021/2/17
     */
    public static String[] getNames() {
        List<String> list = Lists.newArrayList();
        for (TaskTypeEnum stateType : TaskTypeEnum.values()) {
            list.add(stateType.NAME);
        }

        if (CollectionUtils.isNotEmpty(list)) {
            return list.toArray(new String[list.size()]);
        }

        return null;
    }

    public static Map<Integer,String> getTaskAllTypes(){
        Map<Integer, String> taskAll = new LinkedHashMap<>();
        for (TaskTypeEnum taskType : TaskTypeEnum.values()) {
            Integer code = taskType.CODE;
            String name = taskType.NAME;
            taskAll.put(code, name);
        }
        return taskAll;
    }
}

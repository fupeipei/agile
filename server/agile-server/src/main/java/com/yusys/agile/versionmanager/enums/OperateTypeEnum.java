package com.yusys.agile.versionmanager.enums;

import java.util.HashMap;
import java.util.Map;

public enum OperateTypeEnum {

    OPERATE_TYPE_ADD(0,"新增"),
    OPERATE_TYPE_MODIFY(1,"修改"),
    OPERATE_TYPE_DELETE(2, "删除");

    public Integer VALUE;
    public String NAME;

    OperateTypeEnum(Integer value, String name){
        this.VALUE=value;
        this.NAME=name;
    }

    public static String getName(Integer value) {
        for (OperateTypeEnum operateType : OperateTypeEnum.values()) {
            if (operateType.VALUE.equals(value)) {
                return operateType.NAME;
            }
        }
        return "";
    }

    public static OperateTypeEnum getByValue(Integer value) {
        for (OperateTypeEnum operateType : OperateTypeEnum.values()) {
            if (operateType.VALUE.equals(value)) {
                return operateType;
            }
        }
        return null;
    }

    public static Map<Integer, String> getVersionStateMap() {
        Map<Integer, String> operateTypeMap = new HashMap<>();
        for (OperateTypeEnum operateType : OperateTypeEnum.values()) {
            operateTypeMap.put(operateType.VALUE,operateType.NAME);
        }
        return operateTypeMap;
    }
}

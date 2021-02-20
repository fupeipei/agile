package com.yusys.agile.constant;

/**
 * @ClassName EmailOperationTypeEnum
 * @Description 工作项操作类型
 * @Date 2021/2/1
 * @Version 1.0
 */
public enum EmailOperationTypeEnum {
    /**
     * 工作项操作类型
     */
    ADD(0,"新增"),
    MODIFY(1,"编辑"),
    DELETE(2,"删除"),
    DRAG(3,"拖拽");

    public Integer CODE;
    public String NAME;

    private EmailOperationTypeEnum(Integer code,String name){
        this.CODE = code;
        this.NAME = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (EmailOperationTypeEnum operationType : EmailOperationTypeEnum.values()) {
            if (operationType.CODE.equals( code)) {
                return operationType.NAME;
            }
        }
        return "";
    }

    // 普通方法
    public static EmailOperationTypeEnum getByCode(String code) {
        for (EmailOperationTypeEnum operationType : EmailOperationTypeEnum.values()) {
            if (operationType.CODE.equals( code)) {
                return operationType;
            }
        }
        return null;
    }

}

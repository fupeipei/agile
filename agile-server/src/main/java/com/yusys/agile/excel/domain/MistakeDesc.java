package com.yusys.agile.excel.domain;

/**
 * 功能描述:错误描述
 *
 * @param
 * @date 2021/2/1
 * @return
 */
public class MistakeDesc {
    public static final String NOTNULLHINT = "为空或者格式有误;";
    public static final String PURE_DIGITAL = "只能填写正整数";
    public static final String DATEFORMATHINT = "请按照模板格式填写大于当前日期的要求上限日期，并且所填日期必须存在";

    public static final String FUNCTIONSCALFORMATHINT = "只能填写数字，且值在1~100之间;";

    public static final String TITTLEBIGTHINT = "字数不能超过200字";

    public static final String NOT_IN_SELECT = "请在下拉中选择";
    public static final String NOT_IN_SELECT_AND_TEMPLATE = "请在下拉中选择或确认是否该迭代下模版";
}

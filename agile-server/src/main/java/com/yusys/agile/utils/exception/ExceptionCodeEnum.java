package com.yusys.agile.utils.exception;

public enum ExceptionCodeEnum {
    PARAM_ERROR(1, "参数异常"), SYS_ERROR(2, "系统异常"), UNAUTHORIZED_ERROR(3, "未登录"), ILLEGALPROJECT_ERROR(4,
            "非法项目"), LOGIN_ERROR(5, "登录错误"), UNACCESS(6, "权限不足"), VERIFYCODE_CREATE_ERROR(7, "生成验证码图片失败!"), VERIFYCODE_NULL_ERROR(8, "验证码不能为空!"), VERIFYCODE_TIMEOUT_ERROR(9, "验证码已失效!"), VERIFYCODE_ERROR(10, "验证码错误!"),
    DMP_PARENT_BACKLOG_NOT_ALLOWED(11, "DMP同步的父需求卡片不允许删除!"), DMP_CHILD_SYSTEM_BACKLOG_NOT_ALLOWED(12, "DMP同步的子系统需求卡片不允许删除!"), DMP_MODULE_DELETE_ERROR(13, "DMP模块需求不允许删除"), DMP_CREATE_BRANCH_ERROR(14, "DMP分支创建中,请稍后");

    private Integer code;
    private String desc;

    ExceptionCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}

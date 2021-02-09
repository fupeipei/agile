package com.yusys.agile.utils.exception;

import java.util.List;

public class AgileError {
    private int code;
    private String message;
    private List<ParamError> paramErrors;

    public AgileError() {
        this.code = ExceptionCodeEnum.SYS_ERROR.getCode();
        this.message = ExceptionCodeEnum.SYS_ERROR.getDesc();
    }

    public AgileError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AgileError(ExceptionCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getDesc();
    }

    public AgileError(List<ParamError> paramErrors) {
        this.code = ExceptionCodeEnum.PARAM_ERROR.getCode();
        this.message = ExceptionCodeEnum.PARAM_ERROR.getDesc();
        this.paramErrors = paramErrors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ParamError> getParamErrors() {
        return paramErrors;
    }

    public void setParamErrors(List<ParamError> paramErrors) {
        this.paramErrors = paramErrors;
    }

}

package com.yusys.agile.utils.exception;

public class AgileException extends RuntimeException {
    private static final long serialVersionUID = 5959500630626136784L;

    private ExceptionCodeEnum exceptionCodeEnum;

    public AgileException(ExceptionCodeEnum exceptionCodeEnum) {
        this.exceptionCodeEnum = exceptionCodeEnum;
    }

    public ExceptionCodeEnum getExceptionCodeEnum() {
        return exceptionCodeEnum;
    }

    public void setExceptionCodeEnum(ExceptionCodeEnum exceptionCodeEnum) {
        this.exceptionCodeEnum = exceptionCodeEnum;
    }

}

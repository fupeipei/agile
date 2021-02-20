package com.yusys.agile.servicemanager.dto;

/**
 * @description 服务治理平台异常类
 *  
 * @date 2021/2/02
 */
public class ServiceManageExceptionDTO extends RuntimeException {

    public ServiceManageExceptionDTO() {
        super();
    }

    public ServiceManageExceptionDTO(String message) {
        super(message);
    }

    public ServiceManageExceptionDTO(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceManageExceptionDTO(Throwable cause) {
        super(cause);
    }

    protected ServiceManageExceptionDTO(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

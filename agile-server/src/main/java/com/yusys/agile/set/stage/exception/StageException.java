package com.yusys.agile.set.stage.exception;

/**
 * @description 阶段异常类
 * @date 2020/05/07
 */
public class StageException extends RuntimeException {

    public StageException() {
        super();
    }

    public StageException(String message) {
        super(message);
    }

    public StageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StageException(Throwable cause) {
        super(cause);
    }

    protected StageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

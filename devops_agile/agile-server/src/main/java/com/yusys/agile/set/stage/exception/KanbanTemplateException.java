package com.yusys.agile.set.stage.exception;

/**
 * @description 看板模板异常类
 *  
 * @date 2020/08/21
 */
public class KanbanTemplateException extends RuntimeException {

    public KanbanTemplateException() {
        super();
    }

    public KanbanTemplateException(String message) {
        super(message);
    }

    public KanbanTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public KanbanTemplateException(Throwable cause) {
        super(cause);
    }

    protected KanbanTemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

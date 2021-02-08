package com.yusys.agile.utils.exception;

public class AESSecurityException extends RuntimeException {
    public AESSecurityException() {
        super();
    }

    public AESSecurityException(String message) {
        super(message);
    }

    public AESSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AESSecurityException(Throwable cause) {
        super(cause);
    }

    protected AESSecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

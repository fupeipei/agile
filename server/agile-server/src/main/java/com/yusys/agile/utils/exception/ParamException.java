package com.yusys.agile.utils.exception;

import java.util.List;

public class ParamException extends RuntimeException {
    private static final long serialVersionUID = 3620988132268400040L;

    private List<ParamError> paramErrors;

    public ParamException(List<ParamError> paramErrors) {
        this.paramErrors = paramErrors;
    }

    public List<ParamError> getParamErrors() {
        return paramErrors;
    }

    public void setParamErrors(List<ParamError> paramErrors) {
        this.paramErrors = paramErrors;
    }

}

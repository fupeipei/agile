package com.yusys.agile.utils.exception;

import java.io.Serializable;

public class ParamError implements Serializable {

    private static final long serialVersionUID = 6408175163093495856L;
    private String name;
    private String message;

    public ParamError(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "name:" + name + ",message:" + message;
    }

}

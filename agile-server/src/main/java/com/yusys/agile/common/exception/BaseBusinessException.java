package com.yusys.agile.common.exception;

import org.apache.commons.lang3.StringUtils;

/**
 *  @Description:
 *
 *  @author: zhao_yd
 *  @Date: 2021/7/2 3:42 下午
 *
 */

public class BaseBusinessException extends RuntimeException {
    private Integer code;
    private String errorMsg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BaseBusinessException() {
        super();
    }

    public BaseBusinessException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
    public BaseBusinessException(Integer code,String errorMsg) {
        super(errorMsg);
        this.code = code;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return this.getErrorMsg();
    }

    /**
     * 根据{0}，{1}等动态替换消息参数
     * @param errorMsg
     * @param args
     */
    public BaseBusinessException(String errorMsg, String... args) {
        if (null != args) {
            for (int i = 0, len = args.length; i < len; i++) {
                errorMsg = errorMsg.replace(getPlaceHolder(i), args[i]);
            }
            this.errorMsg = errorMsg;
        }
    }

    private String getPlaceHolder(int index) {
        return StringUtils.join("{", index, "}");
    }
}

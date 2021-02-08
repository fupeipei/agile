package com.yusys.agile.review.dto;

/**
 * 校验故事能否加入迭代结果DTO
 *
 *
 * @create 2020-09-10 15:35
 */
public class StoryCheckResultDTO {

    /**
     * 是否通过 true通过 false不通过
     */
    private Boolean hasPassed;

    /**
     * 返回信息
     */
    private String msg;

    public Boolean getHasPassed() {
        return hasPassed;
    }

    public void setHasPassed(Boolean hasPassed) {
        this.hasPassed = hasPassed;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
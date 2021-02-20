package com.yusys.agile.changemanagement.dto;

import java.io.Serializable;

/**
 * @description 需求评审结果
 *  
 * @date 2020/12/09
 */
public class EpicReviewResultDTO implements Serializable {

    /**
     * 客户需求编号
     */
    private String bizNum;

    /**
     * 评审结果 01:通过 02:不通过
     */
    private String result = "02";

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "EpicReviewResultDTO{" +
                "bizNum='" + bizNum + '\'' +
                ", result=" + result +
                '}';
    }
}

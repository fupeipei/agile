package com.yusys.agile.excel.domain;

public class Mistake {
    private int rowIndex;//行
    private int columnIndex;//列
    private String describe;//错误描述

    public Mistake() {

    }

    public Mistake(int i, int j) {
        this.rowIndex = i;
        this.columnIndex = j;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}

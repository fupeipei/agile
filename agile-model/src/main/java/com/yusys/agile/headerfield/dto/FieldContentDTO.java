package com.yusys.agile.headerfield.dto;


import java.util.List;
import java.util.Map;

/**
 * @ClassName FieldContentDTO
 * @Description: TODO
 * @Author: libinbin
 * @Date 2021/6/8
 **/
public class FieldContentDTO {
    private String name;
    private String type;
    private String comment;
    private String placeholder;
    private List<Map<String, Object>> optionList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public List<Map<String, Object>> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Map<String, Object>> optionList) {
        this.optionList = optionList;
    }
}

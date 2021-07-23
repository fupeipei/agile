package com.yusys.agile.zentao.domain;

import java.io.Serializable;

public class ZtStoryspec implements Serializable {
    private Integer story;

    private Short version;

    private String title;

    private static final long serialVersionUID = 1L;

    public Integer getStory() {
        return story;
    }

    public void setStory(Integer story) {
        this.story = story;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
}
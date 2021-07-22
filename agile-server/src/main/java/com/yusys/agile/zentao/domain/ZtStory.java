package com.yusys.agile.zentao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ZtStory implements Serializable {
    private Integer id;

    private Integer product;

    private Integer branch;

    private Integer module;

    private String source;

    private String sourcenote;

    private Integer frombug;

    private String title;

    private String keywords;

    private String type;

    private Byte pri;

    private Float estimate;

    private String status;

    private String color;

    private String stage;

    private String openedby;

    private Date openeddate;

    private String assignedto;

    private Date assigneddate;

    private String lasteditedby;

    private Date lastediteddate;

    private String reviewedby;

    private Date revieweddate;

    private String closedby;

    private Date closeddate;

    private String closedreason;

    private Integer tobug;

    private String childstories;

    private String linkstories;

    private Integer duplicatestory;

    private Short version;

    private String deleted;

    private static final long serialVersionUID = 1L;

    }
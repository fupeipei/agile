package com.yusys.agile.zentao.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class ZtModule implements Serializable {
    private Integer id;

    private Integer root;

    private Integer branch;

    private String name;

    private Integer parent;

    private String path;

    private Byte grade;

    private Short order;

    private String type;

    private String owner;

    private String deleted;

    private String collector;

    private static final long serialVersionUID = 1L;

    }
package com.yusys.agile.projectmanager.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDataDto {

    private Integer code;

    private List<SStaticProjectDataDto> sStaticProjectDataDtoList;
}

package com.yusys.agile.projectmanager.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDemandDto {

    private Long systemId;

    private String systemName;

    private List<StageNameAndValueDto> stageNameAndValueDtoList;


}

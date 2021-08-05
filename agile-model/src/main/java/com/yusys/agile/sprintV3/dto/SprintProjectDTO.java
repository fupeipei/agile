package com.yusys.agile.sprintV3.dto;


import com.yusys.agile.team.dto.TeamListDTO;
import lombok.Data;

import java.util.List;

@Data
public class SprintProjectDTO {

    private Long teamId;

    private String teamName;

    private List<TeamListDTO> teamListDTOS;

    private List<SprintListDTO> sprintListDTOS;




}

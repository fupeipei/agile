package com.yusys.agile.scheduleplan.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ScheduleplanDTO {

    private Long epicId;

    private String scheduleplanName;

    private Date releaseDate;

    private Date raiseTestDate;

    private List<SystemInfoDTO> systemInfo;

}

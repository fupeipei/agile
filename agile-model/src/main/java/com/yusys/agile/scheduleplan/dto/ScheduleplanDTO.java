package com.yusys.agile.scheduleplan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ScheduleplanDTO  implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long epicId;

    private String scheduleName;

    private Date releaseDate;

    private Date raiseTestDate;

    private List<SystemInfoDTO> systemInfo;

}

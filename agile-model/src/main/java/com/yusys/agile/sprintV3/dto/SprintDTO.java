package com.yusys.agile.sprintV3.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: SprintDTO
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/30 16:02
 */
@Data
public class SprintDTO {

    private Long teamId;
    private Long sprintId;
    private String sprintName;
    private Integer status;
    private Date startTime;
    private Date endTime;
    private String rateOfProgress;
}

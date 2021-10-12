package com.yusys.agile.sprintV3.dto;

import lombok.Data;

/**
 * @ClassName: TaskDto
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/30 17:09
 */
@Data
public class TaskDto {
    private Long sprintId;
    private Long IssueId;
    private Long laneId;
}

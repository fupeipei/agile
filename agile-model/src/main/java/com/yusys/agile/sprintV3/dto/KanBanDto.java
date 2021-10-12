package com.yusys.agile.sprintV3.dto;

import lombok.Data;


/**
 * @ClassName: KanbanDto
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/30 17:28
 */
@Data
public class KanBanDto {
    private Long teamId;
    private Long kanbanId;
    private String featureInfo;
    private String storyInfo;
    private String taskInfo;
    private String workload;
    private String rateOfProgress;
}

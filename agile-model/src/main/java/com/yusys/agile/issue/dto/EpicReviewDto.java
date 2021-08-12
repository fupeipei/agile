package com.yusys.agile.issue.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @ClassName: EpicReviewRecordDto
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/12 14:34
 */
@Data
public class EpicReviewDto {

    @NotNull(message = "项目id不能为空")
    private Long issueId;
    @NotNull(message = "评分不能为空")
    private String reviewScore;
    @Size(max = 500)
    private String reviewDesc;

}

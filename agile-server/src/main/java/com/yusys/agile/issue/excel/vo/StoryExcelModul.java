package com.yusys.agile.issue.excel.vo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

@Data
@HeadRowHeight(20)	// 表头行高
@ColumnWidth(15)		// 表头行宽
public class StoryExcelModul {

    private String storyName;

    private String storyDesc;

    private String acceptanceCriteria;

    private String sprint;

    private Integer priority;

    private String parentInfo;

    private String storyPoint;

    private String startData;

    private String endData;

    private Integer planWorkload;

}

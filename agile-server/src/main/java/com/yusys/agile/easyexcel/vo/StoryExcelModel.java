package com.yusys.agile.easyexcel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

@Data
@HeadRowHeight(20)	// 表头行高
@ColumnWidth(15)		// 表头行宽
public class StoryExcelModel {

    @ExcelProperty(value = "*故事名称", index = 0)
    private String storyName;

    @ExcelProperty(value = "故事描述", index = 1)
    private String storyDesc;

    @ExcelProperty(value = "验收标准", index = 2)
    private String acceptanceCriteria;

    @ExcelProperty(value = "迭代", index = 3)
    private String sprint;

    @ExcelProperty(value = "优先级", index = 4)
    private Integer priority;

    @ExcelProperty(value = "父工资项", index = 5)
    private String parentInfo;

    @ExcelProperty(value = "故事点", index = 6)
    private String storyPoint;

    @ExcelProperty(value = "开始日期", index = 7)
    private String startData;

    @ExcelProperty(value = "结束日期", index = 8)
    private String endData;

    @ExcelProperty(value = "预计工时", index = 9)
    private Integer planWorkload;

}

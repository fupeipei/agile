package com.yusys.agile.easyexcel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @ClassName TaskExcelModel
 * @Description: TODO
 * @Author: libinbin
 * @Date 2021/5/28
 **/
@Data
@HeadRowHeight(20)	// 表头行高
@ColumnWidth(15)		// 表头行宽
public class TaskExcelModel {

    @ExcelProperty(value = "*故事ID", index = 0)
    private String story;

    @ExcelProperty(value = "*任务标题", index = 1)
    private String taskTitle;

    @ExcelProperty(value = "任务描述", index = 2)
    private String taskDesc;

    @ExcelProperty(value = "*任务类型", index = 3)
    private String taskType;

    @ExcelProperty(value = "预计工时", index = 4)
    private String planWorkload;
}

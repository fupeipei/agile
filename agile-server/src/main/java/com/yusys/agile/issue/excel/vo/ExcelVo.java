package com.yusys.agile.issue.excel.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExcelVo {

    // 根据list导出列表
    private List<List<String>> stringList;

    // 文件名-必传 null时默认名称:文件
    private String fileName;

    // 首行展示数据-必填
    private List<String> cellText;

    // 类型-必传 .xls或.xlsx
    private String fileType;
}

package com.yusys.agile.excel.rest;

import com.yusys.agile.excel.domain.VersionIssue;
import com.yusys.agile.excel.service.VersionIssueExcelService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/**
 * @Date: 2021/2/1
 * @Description 导出版本关联工作项信息
 */
@RestController
@RequestMapping("/version/excel")
public class VersionIssueExcelController {
    @Resource
    private VersionIssueExcelService versionIssueExcelService;

    @PostMapping("/export/issueDatas/versionIdList")
    public void exportIssueDatas(@RequestBody VersionIssue versionIssue, HttpServletResponse response) throws Exception {
        versionIssueExcelService.exportIssueDatas(versionIssue, response);
    }
}

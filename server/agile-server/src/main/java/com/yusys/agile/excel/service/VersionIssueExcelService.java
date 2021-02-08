package com.yusys.agile.excel.service;


import com.yusys.agile.excel.domain.VersionIssue;

import javax.servlet.http.HttpServletResponse;


public interface VersionIssueExcelService {

    /**
     * @Date 2021/2/2
     * @Description 导出版本关联工作项信息
     * @param  versionIssue
     */
    void exportIssueDatas(VersionIssue versionIssue, HttpServletResponse response) throws Exception;
}

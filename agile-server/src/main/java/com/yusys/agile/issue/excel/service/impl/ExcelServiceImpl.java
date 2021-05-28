package com.yusys.agile.issue.excel.service.impl;

import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.excel.service.DownloadExcelTempletService;
import com.yusys.agile.issue.excel.service.ExcelTempletFactory;
import com.yusys.agile.issue.excel.service.IExcelService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 *  @Description: excel 实现类
 *  @author: zhao_yd
 *  @Date: 2021/5/25 8:45 下午
 *
 */
@Service
public class ExcelServiceImpl implements IExcelService {

    @Override
    public void downLoadTemplate(Byte excelType, HttpServletResponse response) {
        String type = IssueTypeEnum.getName(excelType);
        DownloadExcelTempletService downloadExcelTempletService = ExcelTempletFactory.get(type);
        downloadExcelTempletService.download(response);
    }
}

package com.yusys.agile.issue.excel.rest;

import com.yusys.agile.issue.excel.service.DownloadExcelTempletService;
import com.yusys.agile.issue.excel.service.IExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *  @Description: excel 操作类
 *  @author: zhao_yd
 *  @Date: 2021/5/25 8:28 下午
 *
 */

@Slf4j
@RestController
@RequestMapping("/excel")
public class EasyExcelController {

    @Autowired
    private IExcelService excelService;
    @Autowired
    private DownloadExcelTempletService downloadExcelTempletService;


   // @GetMapping(value = "/downloadExcel/template/{excelType}")
    public void download(@PathVariable Byte excelType, HttpServletRequest request, HttpServletResponse response) {
        try {
            downloadExcelTempletService.download();
        } catch (Exception e) {
            log.error("excel模版下载失败：{}", e);
        }
    }
}

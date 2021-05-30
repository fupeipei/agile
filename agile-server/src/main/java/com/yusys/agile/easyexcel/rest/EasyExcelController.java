package com.yusys.agile.easyexcel.rest;

import com.yusys.agile.easyexcel.service.IExcelService;
import com.yusys.agile.easyexcel.vo.ExcelCommentFiled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private IExcelService iExcelService;

    @GetMapping(value = "/downloadExcel/template/{excelType}")
    public void download(@PathVariable Byte excelType,
                         HttpServletResponse response,
                         @RequestParam(value = "sprintId",required = false)Long sprintId) {
        try {
            ExcelCommentFiled filed = new ExcelCommentFiled();
            filed.setSprintId(sprintId);
            iExcelService.downLoadTemplate(excelType,response,filed);
        } catch (Exception e) {
            log.error("excel模版下载失败：{}", e);
        }
    }
}

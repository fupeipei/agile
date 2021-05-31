package com.yusys.agile.easyexcel.rest;

import com.yusys.agile.easyexcel.service.IExcelService;
import com.yusys.agile.easyexcel.vo.ExcelCommentFiled;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


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

    @ApiOperation("模版下载")
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


    @ApiOperation("故事导入导入")
    @PostMapping("/uploadStorys")
    public ControllerResponse uploadStorys(@RequestParam("file") MultipartFile file,
                                           @RequestParam(value = "systemId",required = false) Long systemId){
        try {
            if(!Optional.ofNullable(systemId).isPresent()){
                systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
            }
            iExcelService.uploadStorys(systemId,file);
        }catch (Exception e){
            return ControllerResponse.success("上传成功");
        }
        return ControllerResponse.success("上传成功");
    };
}

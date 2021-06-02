package com.yusys.agile.easyexcel.rest;

import com.yusys.agile.easyexcel.service.IExcelService;
import com.yusys.agile.easyexcel.vo.ExcelCommentFile;
import com.yusys.agile.file.domain.FileInfo;
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
 * @Description: excel 操作类
 * @author: zhao_yd
 * @Date: 2021/5/25 8:28 下午
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
                         @RequestParam(value = "sprintId", required = false) Long sprintId,
                         @RequestParam(value = "systemId", required = false) Long systemId) {
        try {
            ExcelCommentFile filed = new ExcelCommentFile();
            filed.setSprintId(sprintId);
            filed.setSystemId(systemId);
            iExcelService.downLoadTemplate(excelType, response, filed);
        } catch (Exception e) {
            log.error("excel模版下载失败：{}", e);
        }
    }


    @ApiOperation("故事导入")
    @PostMapping("/uploadStorys")
    public ControllerResponse uploadStorys(@RequestParam("file") MultipartFile file,
                                           @RequestParam(value = "systemId") Long systemId) {
        FileInfo fileInfo;
        try {
            if (!Optional.ofNullable(systemId).isPresent()) {
                systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
            }
            ExcelCommentFile commentFile = new ExcelCommentFile();
            commentFile.setSystemId(systemId);

            fileInfo = iExcelService.uploadStorys(file,commentFile);
        } catch (Exception e) {
            return ControllerResponse.fail("上传失败:" + e.getMessage());
        }
        if (Optional.ofNullable(fileInfo).isPresent()) {
            return ControllerResponse.fail(fileInfo);
        }
        return ControllerResponse.success("上传成功");
    }

    @ApiOperation("任务导入")
    @PostMapping("/uploadTasks")
    public ControllerResponse uploadTasks(@RequestParam("file") MultipartFile file,@RequestParam("sprintId")Long sprintId) {
        FileInfo fileInfo;
        try {
            ExcelCommentFile commentFile = new ExcelCommentFile();
            commentFile.setSprintId(sprintId);
            fileInfo = iExcelService.uploadTasks(file,commentFile);
        } catch (Exception e) {
            return ControllerResponse.fail("上传失败:" + e.getMessage());
        }
        if (Optional.ofNullable(fileInfo).isPresent()) {
            return ControllerResponse.fail(fileInfo);
        }
        return ControllerResponse.success("上传成功");
    }

}

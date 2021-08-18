package com.yusys.agile.easyexcel.rest;

import com.yusys.agile.easyexcel.service.IExcelService;
import com.yusys.agile.easyexcel.vo.ExcelCommentField;
import com.yusys.agile.file.domain.FileInfo;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
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

    private static final String UPLOAD = "上传失败:";
    @ApiOperation("模版下载")
    @GetMapping(value = "/downloadExcel/template/{excelType}")
    public void download(@PathVariable Byte excelType,
                         HttpServletResponse response,
                         @RequestParam(value = "sprintId", required = false) Long sprintId,
                         @RequestParam(value = "systemId", required = false) Long systemId) {
        try {
            ExcelCommentField field = new ExcelCommentField();
            field.setSprintId(sprintId);
            field.setSystemId(systemId);
            iExcelService.downLoadTemplate(excelType, response, field);
        } catch (Exception e) {
            log.error("excel模版下载失败：{}", e);
        }
    }


    @ApiOperation("故事导入")
    @PostMapping("/uploadStorys")
    public ControllerResponse uploadStorys(@RequestParam("file") MultipartFile file,
                                           @RequestParam(value = "systemId",required = false) Long systemId) {
        FileInfo fileInfo;
        try {
            if (!Optional.ofNullable(systemId).isPresent()) {
                systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
            }
            ExcelCommentField field = new ExcelCommentField();
            field.setSystemId(systemId);

            fileInfo = iExcelService.uploadStorys(file,field);
        } catch (Exception e) {
            return ControllerResponse.fail(UPLOAD+ e.getMessage());
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
            ExcelCommentField field = new ExcelCommentField();
            field.setSprintId(sprintId);
            fileInfo = iExcelService.uploadTasks(file,field);
        } catch (Exception e) {
            return ControllerResponse.fail(UPLOAD + e.getMessage());
        }
        if (Optional.ofNullable(fileInfo).isPresent()) {
            return ControllerResponse.fail(fileInfo);
        }
        return ControllerResponse.success("上传成功");
    }


    /**
     * 导出工作项
     * @param issueType
     * @param map
     * @param response
     */
    @PostMapping("/export/issues/{issueType}")
    public void exportIssueDatas(@PathVariable("issueType") Byte issueType,
                                 @RequestBody Map<String, Object> map,
                                 HttpServletResponse response,
                                 @RequestParam(value = "systemId",required = false) Long systemId) {
        try {
            if(issueType == null){
                throw new BusinessException("工作项类型不能为空!");
            }

            if(!Optional.ofNullable(systemId).isPresent()){
                systemId = UserThreadLocalUtil.getUserInfo().getSystemId();
            }

            if(Optional.ofNullable(systemId).isPresent()){
                map.put("systemId",systemId.toString());
            }

           iExcelService.exportIssues(issueType, systemId, map,response);
        } catch (Exception e) {
            log.error("导出工作项异常:{}", e.getMessage());
        }
    }


    @ApiOperation("epic导入")
    @PostMapping("/uploadEpics")
    public ControllerResponse uploadEpics(@RequestParam("file") MultipartFile file) {
        FileInfo fileInfo;
        try {
            ExcelCommentField field = new ExcelCommentField();
            fileInfo = iExcelService.uploadEpics(file,field);
        } catch (Exception e) {
            return ControllerResponse.fail(UPLOAD + e.getMessage());
        }
        if (Optional.ofNullable(fileInfo).isPresent()) {
            return ControllerResponse.fail(fileInfo);
        }
        return ControllerResponse.success("上传成功");
    }

}

package com.yusys.agile.excel.rest;

import com.yusys.agile.excel.service.ExcelService;
import com.yusys.agile.utils.ExcelUtil;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * excel操作Controller
 *
 * @create 2021/2/1 17:28
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ExcelService excelService;


    /**
     * 功能描述:
     *
     * @param excelType epic:1 feature:2 story:3 task：4
     * @param projectId
     * @param request
     * @param response
     * @return void
     * @date 2021/2/1
     */
    @GetMapping(value = "/download/template/{excelType}")
    public void download(@PathVariable Byte excelType, @RequestParam(name = "projectId") Long projectId,
                         @RequestParam(name = "sprintId", required = false) Long sprintId, HttpServletRequest request, HttpServletResponse response) {
        try {
            excelService.downloadTemplateByExcelType(excelType, projectId, sprintId, request, response);
        } catch (Exception e) {
            LOGGER.error("excel模版下载失败：{}", e.getMessage());
        }
    }


    /**
     * 功能描述: excel导入
     *
     * @param excelType
     * @param projectId
     * @param multiReq
     * @param request
     * @param response
     * @return
     * @date 2021/2/1
     */
    @PostMapping(value = "/import/{excelType}")
    public ControllerResponse importExcel(@PathVariable Byte excelType, @RequestHeader(name = "projectId") Long projectId,
                                          @RequestParam(name = "sprintId", required = false) Long sprintId, MultipartHttpServletRequest multiReq, HttpServletRequest request, HttpServletResponse response) {
        try {
            return excelService.importExcel(excelType, projectId, sprintId, multiReq, request, response);
        } catch (Exception e) {
            LOGGER.error("导入excel失败：{}", e.getMessage());
            return ControllerResponse.fail("导入excel失败！");
        }
    }

    /**
     * 功能描述: 导出工作项数据
     *
     * @param issueType
     * @param projectId
     * @param map
     * @return
     * @date 2021/2/1
     */
    @PostMapping("/export/issueDatas/{issueType}")
    public void exportIssueDatas(@PathVariable("issueType") Byte issueType, @RequestHeader("projectId") Long projectId, @RequestBody Map<String, Object> map, HttpServletResponse response) {
        try {
            Workbook workbook = excelService.exportIssueDatas(issueType, projectId, map);
            ExcelUtil.writeOutStream(response, UUID.randomUUID() + ".xlsx", workbook);
        } catch (Exception e) {
            LOGGER.error("导出工作项excel异常:{}", e.getMessage());
        }
    }
}
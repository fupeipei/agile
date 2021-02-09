package com.yusys.agile.excel.service;

import com.yusys.portal.model.common.dto.ControllerResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * excel业务类
 *
 * @create 2021-2-2 08:55
 */
public interface ExcelService {

    /**
     * 功能描述: 下载模版
     *
     * @param excelType
     * @param projectId
     * @param request
     * @param response
     * @return void
     * @date 2021/2/2
     */
    void downloadTemplateByExcelType(Byte excelType, Long projectId, Long sprintId, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 功能描述: 导入excel
     *
     * @param excelType
     * @param projectId
     * @param request
     * @param response
     * @return void
     * @date 2021/2/2
     */
    ControllerResponse importExcel(Byte excelType, Long projectId, Long sprintId, MultipartHttpServletRequest multiReq, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 功能描述: 导出工作项数据
     *
     * @param issueType
     * @param projectId
     * @param map
     * @return Workbook
     * @date 2021/2/2
     */
    Workbook exportIssueDatas(Byte issueType, Long projectId, Map<String, Object> map) throws Exception;
}

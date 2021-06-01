package com.yusys.agile.easyexcel.service;

import com.yusys.agile.easyexcel.vo.ExcelCommentFiled;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 *  @Description: excel服务
 *  @author: zhao_yd
 *  @Date: 2021/5/25 8:43 下午
 *
 */

public interface IExcelService {

    /**
     * 模版下载
     * @param excelType 1、epic  2、feature 3、story 4、task
     * @param response
     * @param filed
     */
    void downLoadTemplate(Byte excelType, HttpServletResponse response, ExcelCommentFiled filed);

    /**
     * 用户故事上传
     * @param systemId 系统id
     * @param file
     * @return
     * @throws Exception
     */
    void uploadStorys(Long systemId,MultipartFile file) throws Exception;



    void uploadTasks( Long sprintId,MultipartFile file) throws Exception;
}

package com.yusys.agile.easyexcel.service;

import com.yusys.agile.easyexcel.vo.ExcelCommentFile;
import com.yusys.agile.file.domain.FileInfo;
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
    void downLoadTemplate(Byte excelType, HttpServletResponse response, ExcelCommentFile filed);

    /**
     * 用户故事上传
     * @param file
     * @return
     * @throws Exception
     */
    FileInfo uploadStorys(MultipartFile file,ExcelCommentFile commentFile) throws Exception;


    /**
     * 任务上传
     * @param
     * @param file
     * @param
     * @return
     * @throws Exception
     */
    FileInfo uploadTasks(MultipartFile file,ExcelCommentFile commentFile) throws Exception;
}

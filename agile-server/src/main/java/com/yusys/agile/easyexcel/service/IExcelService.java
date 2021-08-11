package com.yusys.agile.easyexcel.service;

import com.yusys.agile.easyexcel.vo.ExcelCommentField;
import com.yusys.agile.file.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
     * @param field
     */
    void downLoadTemplate(Byte excelType, HttpServletResponse response, ExcelCommentField field);

    /**
     * 用户故事上传
     * @param file
     * @return
     * @throws Exception
     */
    FileInfo uploadStorys(MultipartFile file,ExcelCommentField field) throws Exception;


    /**
     * 任务上传
     * @param
     * @param file
     * @param
     * @return
     * @throws Exception
     */
    FileInfo uploadTasks(MultipartFile file,ExcelCommentField field) throws Exception;


    /**
     * epic上传
     * @param file
     * @return
     * @throws Exception
     */
    FileInfo uploadEpics(MultipartFile file,ExcelCommentField field) throws Exception;


    /**
     * 工作项导出
     * @param issueType
     * @param systemId
     * @param map
     * @throws Exception
     */
    void exportIssues(Byte issueType, Long systemId, Map<String, Object> map,HttpServletResponse response) throws Exception;
}

package com.yusys.agile.file.service;

import com.yusys.agile.file.domain.FileInfo;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传下载service
 *
 *     
 * @create 2020-04-17 14:25
 */
public interface FileService {

    /**
     * 功能描述: 文件上传
     *
     * @param file
     * @return void
     *     
     * @date 2020/4/17
     */
    FileInfo upload(MultipartFile file) throws Exception;

    /**
     * 功能描述: 文件下载
     *     
     * @date 2020/4/20
     * @param
     * @param request
     * @param response
     * @return com.yusys.agile.file.domain.FileInfo
     */
    void download(String fileName,String remoteName, HttpServletRequest request, HttpServletResponse response) throws Exception;
}

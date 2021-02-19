package com.yusys.agile.file.rest;

import com.yusys.agile.file.domain.FileInfo;
import com.yusys.agile.file.service.FileService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传下载
 *
 * @create 2020-04-17 14:22
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Resource
    private FileService fileService;

    /**
     * 功能描述: 文件上传
     *
     * @param file
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/21
     */
    //@CrossOrigin
    @PostMapping("/upload")
    public ControllerResponse upload(@RequestParam("file") MultipartFile file) {

        try {
            FileInfo fileInfo = fileService.upload(file);
            return ControllerResponse.success(fileInfo);
        } catch (Exception e) {
            LOGGER.error("文件上传失败：{}", e.getMessage());
            return ControllerResponse.fail("文件上传失败：" + e.getMessage());
        }

    }

    /**
     * 功能描述: 文件下载
     *
     * @param request
     * @param response
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/21
     */
    //@CrossOrigin
    @GetMapping(value = "/download")
    public void download(@RequestParam(name = "fileName") String fileName,
                         @RequestParam(name = "remoteName") String remoteName,
                         HttpServletRequest request, HttpServletResponse response) {
        try {
            fileService.download(fileName, remoteName, request, response);
        } catch (Exception e) {
            LOGGER.error("文件下载失败：{}", e.getMessage());
        }


    }


}
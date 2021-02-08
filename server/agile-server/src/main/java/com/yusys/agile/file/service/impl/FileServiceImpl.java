package com.yusys.agile.file.service.impl;

import com.yusys.agile.constant.StringConstant;
import com.yusys.agile.file.domain.FileInfo;
import com.yusys.agile.file.service.FileService;
import com.yusys.agile.utils.FileUtil;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.util.dto.SftpFileInfo;
import com.yusys.portal.util.dto.SftpHostDTO;
import com.yusys.portal.util.sh.JschUtil;
import com.yusys.portal.util.sh.JschUtilProxy;
import com.yusys.portal.util.sh.JschWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.StorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件上传下载
 *
 *
 * @create 2020-04-17 14:26
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    private final Integer DEFAULT_NUM = 1024;

    private final String STR_SFTP = "SFTP";

    private final String STR_FASTDFS = "FASTDFS";

    private final String STR_NAS = "NAS";

    @Value("${oss.size.limit}")
    private long maxSize;
    // 方式 linux fastdfs
    @Value("${file.type}")
    private String type;
    // ---linux----
    @Value("${file.ip}")
    private String ip;
    @Value("${file.port}")
    private String port;
    @Value("${file.hostUser}")
    private String hostUser;
    @Value("${file.pwd}")
    private String pwd;
    @Value("${file.remoteDir}")
    private String remoteDir;
    // ---fastdfs----
    @Value("${file.server}")
    private String fileServer;

    @Resource
    private StorageClient storageClient;


    @Override
    public FileInfo upload(MultipartFile file) throws Exception {
        // 校验文件
        checkFile(file);
        FileInfo fileInfo = new FileInfo();
        // linux主机上传
        if (StringUtils.equalsIgnoreCase(type, STR_SFTP)) {
            return linuxUpload(file, fileInfo);
        } else if (StringUtils.equalsIgnoreCase(type, STR_FASTDFS)) {
            return fastdfsUpload(file, fileInfo);
        }else if (StringUtils.equalsIgnoreCase(type, STR_NAS)) {
            return linuxNasUpload(file, fileInfo);
        }
        return fileInfo;
    }

    /**
     * 功能描述:  对文件进行校验
     *
     * @param file
     * @return void
     *
     * @date 2020/4/21
     */
    private void checkFile(MultipartFile file) throws Exception {
        if (null == file) {
            throw new BusinessException("没有获取到上传文件");
        }
        Long fileSize = file.getSize();
        LOGGER.info("上传类型={},上传校验文件大小fileSize={}", type, fileSize);
        if (fileSize > maxSize * DEFAULT_NUM * DEFAULT_NUM) {
            throw new BusinessException("上传文件" + file.getName() + "大小超过上传限制！");
        } else if (fileSize == 0) {
            throw new BusinessException("上传文件" + file.getName() + "为空！");
        }
    }

    /**
     * 功能描述: fastdfs上传文件
     *
     * @param file
     * @param fileInfo
     * @return com.yusys.agile.file.domain.FileInfo
     *
     * @date 2020/4/21
     */
    private FileInfo fastdfsUpload(MultipartFile file, FileInfo fileInfo) throws Exception {
        String realFileName = file.getOriginalFilename();
        int suffixIndex = realFileName.lastIndexOf('.');
        String[] result;
        String suffix;
        if (suffixIndex != -1) {
            suffix = realFileName.substring(realFileName.lastIndexOf('.') + 1, realFileName.length());
            result = storageClient.upload_file(file.getBytes(), suffix, null);
        } else {
            result = storageClient.upload_file(file.getBytes(), null, null);
        }
        String remoteName = result[0] + "/" + result[1];
        fileInfo = new FileInfo(realFileName, remoteName, file.getSize(), null, this.fileServer);
        return fileInfo;
    }

    /**
     * 功能描述: linux服务器上传
     *
     * @param file
     * @param fileInfo
     * @return com.yusys.agile.file.domain.FileInfo
     *
     * @date 2020/4/21
     */
    private FileInfo linuxUpload(MultipartFile file, FileInfo fileInfo) throws Exception {

        // 原文件名
        String originalFilename = file.getOriginalFilename();
        LOGGER.info("原文件名：" + originalFilename);
        // 远程文件名用时间戳生成
        String remoteFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(originalFilename,
                ".");
        LOGGER.info("新文件名：" + remoteFileName);

        LOGGER.info("ip={},hostUser={},pwd={},port={}", ip, hostUser, pwd, port);
        //上传文件
        SftpHostDTO sftpHostDTO = new SftpHostDTO(hostUser, pwd, ip, Integer.valueOf(port), null);
        SftpFileInfo sftpFileInfo = JschUtilProxy.uploadFile(sftpHostDTO, file.getInputStream(), remoteDir, remoteFileName);
        LOGGER.info("上传文件" + originalFilename + "至" + remoteDir + ",生成的远端文件remoteFileName:{}", remoteFileName);

        String remotePath = remoteDir + File.separator + sftpFileInfo.getFileName();
        fileInfo.setSize(file.getSize());
        fileInfo.setFileName(originalFilename);
        fileInfo.setRemoteName(remoteFileName);
        fileInfo.setFileUri(StringConstant.DOWNLOAD_STR1 + remoteFileName + StringConstant.DOWNLOAD_STR2 + originalFilename);
        return fileInfo;


    }


    /**
     * 功能描述: 文件下载
     *
     * @param
     * @param request
     * @param response
     * @return com.yusys.agile.file.domain.FileInfo
     *
     * @date 2020/4/20
     */
    @Override
    public void download(String fileName, String remoteName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        FileInfo fileInfo = new FileInfo();
        if (StringUtils.isBlank(fileName)
                || StringUtils.isBlank(remoteName)) {
            LOGGER.error("下载文件入参为空！");
            throw new BusinessException("下载文件入参错误！");
        }


        // linux主机下载
        if (StringUtils.equalsIgnoreCase(STR_SFTP, type)) {
            sftpDownload(fileName, remoteName, request, response);
        } else if (StringUtils.equalsIgnoreCase(type, STR_FASTDFS)) {
            fastdfsDownload(fileName, remoteName, request, response);
        }else if (StringUtils.equalsIgnoreCase(type, STR_NAS)) {
            nasDownload(fileName, remoteName, request, response);
        }


    }

    /**
     * 功能描述: 下载
     *
     * @param remoteName
     * @param response
     * @return void
     *
     * @date 2020/4/21
     */
    private void fastdfsDownload(String fileName, String remoteName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        fileName = FileUtil.getFileName(request, fileName);
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        if (remoteName.startsWith("/")) {
            remoteName = remoteName.substring(remoteName.indexOf("/") + 1);
        }
        String groupName = remoteName.substring(0, remoteName.indexOf("/"));
        String realRemoteName = remoteName.substring(remoteName.indexOf("/") + 1);
        LOGGER.debug("----groupName={},realRemoteName={}", groupName, realRemoteName);
        byte[] result = storageClient.download_file(groupName, realRemoteName);
        LOGGER.debug("fastdfs下载长度：{}", result.length);
        InputStream inputStream = new ByteArrayInputStream(result);
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
        response.flushBuffer();
        LOGGER.info("fastdfs下载完成");

    }

    /**
     * 功能描述: linux服务器下载
     *
     * @param
     * @param request
     * @param response
     * @return void
     *
     * @date 2020/4/21
     */
    private void sftpDownload(String fileName, String remoteName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 指定目录+远程文件名
        String filePath = remoteDir + File.separator + remoteName;

        // 执行下载操作
        JschWrapper jschWrapper = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {

            jschWrapper = JschUtil.downloadFile(ip, hostUser, pwd,
                    new Long(port).intValue(), filePath);
            inputStream = jschWrapper.getInputStream();

            fileName = FileUtil.getFileName(request, fileName);
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            LOGGER.info("linux下载的文件大小：{}", inputStream.available());
            outputStream = response.getOutputStream();

            IOUtils.copy(inputStream, outputStream);
            response.flushBuffer();
            LOGGER.info("下载完成");


        } catch (Exception e) {
            LOGGER.error("下载[{}]失败：{}", filePath, e.getMessage());
            throw new Exception(e);

        } finally {

            if (null != jschWrapper) {
                jschWrapper.colseResource();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }

        }
    }
    /**
     * 功能描述: linux服务器上传(NAS)
     *
     * @param file
     * @param fileInfo
     * @return com.yusys.agile.file.domain.FileInfo
     *
     * @date 2020/4/21
     */
    private FileInfo linuxNasUpload(MultipartFile file, FileInfo fileInfo) throws Exception {
        try {
        // 原文件名
        String originalFilename = file.getOriginalFilename();
        LOGGER.info("原文件名：" + originalFilename);
        // 远程文件名用时间戳生成
        String remoteFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(originalFilename,
                ".");
        LOGGER.info("新文件名：" + remoteFileName);

        //上传文件
        String filePath  = remoteDir;//nas路径
        String path = filePath + remoteFileName;
        File filetmp = new File(path);
        // 检测是否存在目录
        if (!filetmp.getParentFile().exists()) {
            filetmp.getParentFile().mkdirs();
        }
        file.transferTo(filetmp);// 文件写入

        fileInfo.setSize(file.getSize());
        fileInfo.setFileName(originalFilename);
        fileInfo.setRemoteName(remoteFileName);
        fileInfo.setFileUri(StringConstant.DOWNLOAD_STR1 + remoteFileName + StringConstant.DOWNLOAD_STR2 + originalFilename);

        } catch (IllegalStateException e) {
            LOGGER.error("context", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("context", e.getMessage());
        }
        return fileInfo;
    }
    /**
     * 功能描述: 下载
     *
     * @param remoteName
     * @param response
     * @return void
     *    wangsh
     * @date 2020/06/13
     */
    private void nasDownload(String fileName, String remoteName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //fileName = FileUtil.getFileName(request, fileName);
        //response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename*=utf-8''" + URLEncoder.encode(fileName,"UTF-8"));
        if (remoteName.startsWith("/")) {
            remoteName = remoteName.substring(remoteName.indexOf("/") + 1);
        }
        String realRemoteName = remoteName.substring(remoteName.indexOf("/") + 1);

            //设置文件路径
            String realPath = remoteDir;
            File file = new File(realPath , realRemoteName);
            if (file.exists()) {
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    LOGGER.debug("下载成功");
                } catch (Exception e) {
                    LOGGER.error("error", e.getMessage());
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            LOGGER.error("context", e.getMessage());
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            LOGGER.error("context", e.getMessage());
                        }
                    }
                }
            }
    }
}
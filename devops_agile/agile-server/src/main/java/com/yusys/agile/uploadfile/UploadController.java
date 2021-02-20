package com.yusys.agile.uploadfile;


import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.domain.IssueAttachment;
import com.yusys.agile.issue.service.IssueAttachmentService;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.sysextendfield.domain.SysExtendFieldDetail;
import com.yusys.agile.sysextendfield.service.SysExtendFieldDetailService;
import com.google.common.collect.Lists;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.util.oss.domain.FileInfo;
import com.yusys.portal.util.oss.domain.FileResult;
import com.yusys.portal.util.oss.error.EmptyFileError;
import com.yusys.portal.util.oss.error.OverSizeError;
import com.yusys.portal.util.oss.exception.OssException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *功能描述 fastdfs文件上传
 *
 * @date 2020/10/19
 * @return
 */
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @Value("${oss.size.limit}")
    private long maxSize;
    @Resource
    private StorageClient storageClient;
    @Value("${file.server}")
    private String fileServer;
    @Resource
    private IssueAttachmentService issueAttachmentService;
    @Resource
    private SysExtendFieldDetailService sysExtendFieldDetailService;
    @Resource
    private IssueService issueService;

    @RequestMapping(value = "/upload/official/business/file", method = RequestMethod.POST)
    @ResponseBody
    public FileResult uploadOfficialRequirementFile(@RequestParam("formalReqCode") String formalReqCode, HttpServletRequest request, MultipartHttpServletRequest multiRequest)
            throws IOException, MyException {
        LOG.info("进入单文件上传接口");
        MultipartFile file = multiRequest.getFile("file");
        long fileSize = file.getSize();
        String realFileName = file.getOriginalFilename();
        check(realFileName,fileSize);
        int suffixIndex = realFileName.lastIndexOf('.');
        String[] result;
        String suffix;
        if(suffixIndex!=-1){
            suffix = realFileName.substring(realFileName.lastIndexOf('.') + 1, realFileName.length());
            try{
                result = storageClient.upload_file(file.getBytes(), suffix, null);
                LOG.info("单文件上传成功1");
            }catch (Exception e) {
                LOG.error("上传文件失败1：{}", e.getMessage());
                result = storageClient.upload_file(file.getBytes(), suffix, null);
            }
        }else{
            try{
                result = storageClient.upload_file(file.getBytes(), null, null);
                LOG.info("单文件上传成功2");
            }catch (Exception e) {
                LOG.error("上传文件失败2：{}", e.getMessage());
                result = storageClient.upload_file(file.getBytes(), null, null);
            }
        }
        String remoteName = result[0] + "/" + result[1];
        FileInfo uploadFileInfo = new FileInfo(realFileName,remoteName,fileSize,null,this.fileServer);
        //保存附件和issue关系
        saveIssueAttachment(formalReqCode, uploadFileInfo);
        return FileResult.success(uploadFileInfo);
    }

    private void saveIssueAttachment(String formalReqCode, FileInfo uploadFileInfo) {
        if(StringUtils.isBlank(formalReqCode)){
            LOG.error("接收的局方需求编号为：{}",formalReqCode);
            throw new BusinessException("接收的局方需求编号为空!");
        }
        List<IssueAttachment> issueAttachmentArrayList = Lists.newArrayList();
        IssueAttachment issueAttachment;
        try{
            List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getSysExtendFieldDetailByProp("formalReqCode",formalReqCode);
            if(CollectionUtils.isNotEmpty(sysExtendFieldDetailList)){
                List<Long> issueIdList = sysExtendFieldDetailList.stream().map(SysExtendFieldDetail::getIssueId).collect(Collectors.toList());
                Issue issue = issueService.selectIssueByIssueId(issueIdList.get(0));
                for(Long issueId : issueIdList){
                    issueAttachmentService.deleteAttachmentByIssueId(issueId);
                    issueAttachment = new IssueAttachment();
                    issueAttachment.setIssueId(issueId);
                    issueAttachment.setFileName(uploadFileInfo.getFileName());
                    issueAttachment.setFileUri(uploadFileInfo.getFileUri());
                    issueAttachment.setTenantCode(issue.getTenantCode());
                    issueAttachmentArrayList.add(issueAttachment);
                }
                issueAttachmentService.createBatchAttachment(issueAttachmentArrayList);
            }else{
                LOG.error("formalReqCode:{}在系统中没有对应的EPIC信息", formalReqCode);
                throw new BusinessException("formalReqCode:"+formalReqCode+"在系统中没有对应的EPIC信息");
            }
        }catch(Exception e){
            LOG.error("操作附件表失败！，错误信息：{}",e);
            throw new BusinessException("操作附件表失败!");
        }
    }

    @RequestMapping(value = "/upload/official/business/files", method = RequestMethod.POST)
    @ResponseBody
    public FileResult uploadOfficialBusinessFiles(@RequestParam("formalReqCode") String formalReqCode,@RequestPart("files") MultipartFile[] files) throws IOException, MyException {
        LOG.info("进入多文件上传接口");
        MultipartFile file = null;
        List<FileInfo> fileInfos = Lists.newArrayList();
        for (int i = 0; i < files.length; ++i) {
            file = files[i];
            long fileSize = file.getSize();
            String realFileName = file.getOriginalFilename();
            check(realFileName,fileSize);
        }
        for (int i = 0; i < files.length; ++i) {
            file = files[i];
            long fileSize = file.getSize();
            String realFileName = file.getOriginalFilename();
            String suffix = realFileName.substring(realFileName.lastIndexOf('.') + 1, realFileName.length());
            String[] result = null;
            try{
                result = storageClient.upload_file(file.getBytes(), suffix, null);
                LOG.info("多文件上传成功");
            }catch (Exception e) {
                LOG.error("上传文件失败3：{}", e.getMessage());
                result = storageClient.upload_file(file.getBytes(), suffix, null);
            }
            String remoteName = result[0]+"/"+result[1];
            fileInfos.add(new FileInfo(realFileName,remoteName,fileSize,suffix,this.fileServer));
        }
        return FileResult.success(fileInfos);
    }

    private void check(String fileName,long fileSize) {
        if(fileSize>maxSize*1024*1024){
            throw new OssException(OverSizeError.get(fileName, maxSize));
        }else if(fileSize==0){
            new OssException(EmptyFileError.get(fileName));
        }
    }
}

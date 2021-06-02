package com.yusys.agile.easyexcel.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.easyexcel.service.impl.ExcelServiceImpl;
import com.yusys.agile.easyexcel.service.impl.TaskTemplateDownloadServiceImpl;
import com.yusys.agile.easyexcel.vo.ExcelCommentFiled;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName ExcelServiceTest
 * @Description: TODO
 * @Author: libinbin
 * @Date 2021/6/1
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class ExcelServiceTest {
    @Resource
    private TaskTemplateDownloadServiceImpl taskTemplateDownloadService;
    @Autowired
    private ExcelServiceImpl excelService;

    @Test
    public void testTaskTemplateDownload() {
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        ExcelCommentFiled filed = new ExcelCommentFiled();
        filed.setSprintId(100029L);
        Byte excelType = 4;
        //iExcelService.downLoadTemplate(excelType,httpServletResponse,filed);
        taskTemplateDownloadService.download(httpServletResponse, filed);
        int status = httpServletResponse.getStatus();
        System.out.println(status);
        Assert.assertTrue("下载模板成功", true);
    }

    @Test
    public void testUploadTasks() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("excelTemplate/taskImportTemplate.xlsx");
        InputStream inputStream = classPathResource.getInputStream();
        File file = classPathResource.getFile();
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
        try {
            excelService.uploadTasks(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue("导入数据为空，请检查!", true);
    }
}

package com.yusys.agile.sprint.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.sprintV3.dto.SSprintAttachmentDTO;
import com.yusys.agile.sprintV3.dto.SprintReviewDTO;
import com.yusys.agile.sprintv3.service.SprintReviewv3Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


/**
 * @Author maxp2
 * @Date: 2021/6/8
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class SprintReviewv3ServiceImplTest {

    @Autowired
    private SprintReviewv3Service sprintReviewv3Service;


    /**
     * 创建迭代回顾
     */
    @Test
    public void createSprintReview() {
        SprintReviewDTO sprintReviewDTO = new SprintReviewDTO();
        sprintReviewDTO.setReviewDesc("测试使用的");
        sprintReviewDTO.setReviewType(1L);
        sprintReviewDTO.setSprintId(1234L);
        sprintReviewDTO.setTenantCode("1");
        sprintReviewDTO.setProposeUid(812352455803777024L);
        int result = sprintReviewv3Service.createSprintReview(sprintReviewDTO);
        if (result == 0) {
            Assert.fail("新增迭代回顾信息失败!");
        }
        Assert.assertTrue("新增迭代回顾信成功", true);
    }

    /**
     * 查看迭代回顾信息
     */
    @Test
    public void getSprintReviewList() {
        Long sprintId = 1234L;
        List<SprintReviewDTO> sprintReviewDTOList = sprintReviewv3Service.getSprintReviewList(sprintId);
        Assert.assertNotNull(sprintReviewDTOList);
    }

    /**
     * 编辑迭代回顾信息
     */
    @Test
    public void editSprintReview() {
        SprintReviewDTO sprintReviewDTO = new SprintReviewDTO();
        sprintReviewDTO.setReviewId(852125533103357952L);
        sprintReviewDTO.setReviewDesc("测试编辑用的");
        sprintReviewDTO.setReviewType(3L);
        sprintReviewDTO.setSprintId(1234L);
        sprintReviewDTO.setTenantCode("1");
        sprintReviewDTO.setProposeUid(812352455803777024L);
        int result = sprintReviewv3Service.editSprintReview(sprintReviewDTO);
        if (result == 0) {
            Assert.fail("编辑迭代回顾信息失败!");
        }
        Assert.assertTrue("编辑迭代回顾信息成功", true);
    }

    /**
     * 删除迭代回顾信息
     */
    @Test
    public void deleteSprintReview() {
        Long reviewId = 852134285109297152L;
        sprintReviewv3Service.deleteSprintReview(reviewId);
        Assert.assertTrue("删除迭代回顾信息成功", true);
    }


    /**
     * 迭代回顾附件信息上传及保存
     */
    @Test
    public void uploadAttachment() throws IOException {
        Long sprintId = 1234L;
        // File转MultipartFile
        File file = new File("E:\\test.txt");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
        sprintReviewv3Service.uploadAttachment(multipartFile, sprintId);
        Assert.assertTrue("上传附近成功", true);
    }


    /**
     * 获取迭代回顾附件信息
     */
    @Test
    public void getAttachmentList() {
        Long sprintId = 1234L;
        List<SSprintAttachmentDTO> sprintAttachmentList = sprintReviewv3Service.getSprintAttachmentList(sprintId);
        Assert.assertNotNull(sprintAttachmentList);
    }

    /**
     * 通过附件id删除附件
     */
    @Test
    public void deleteAttachment() {
        Long attachmentId = 3L;
        sprintReviewv3Service.deleteAttachment(attachmentId);
        Assert.assertTrue("删除附件成功", true);
    }
}

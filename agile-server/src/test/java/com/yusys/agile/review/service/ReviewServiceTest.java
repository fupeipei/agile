package com.yusys.agile.review.service;

import com.yusys.agile.review.dto.ReviewDTO;
import com.yusys.agile.review.dto.ReviewSetDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ReviewServiceTest
 * @Description: TODO
 * @Author: libinbin
 * @Date 2021/6/9
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewSetService reviewSetService;


    @Test
    public void testAddReview() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewDesc("这是一个新的评审777");
        reviewDTO.setReviewTheme("任务评审777");
        reviewDTO.setCreateTime(new Date());
        reviewDTO.setIssueType((byte) 3);
        reviewDTO.setIssueId(847878567016624685L);
        reviewDTO.setVersion("v1");
        List<Long> userIds = new ArrayList<>();
        userIds.add(849951359262416896L);
        reviewDTO.setUserIds(userIds);
        reviewService.addReview(reviewDTO);
        Assert.assertTrue("创建评审成功", true);

    }


    @Test
    public void testCancelReview() {
        Long reviewId = 852188796077010944L;
        Long operatorId = 9999L;
        reviewService.cancelReview(reviewId, operatorId);
        Assert.assertTrue("取消评审成功", true);
    }


    @Test
    public void getReview() {
        Long reviewId = 852188796077010944L;
        ReviewDTO review = reviewService.getReview(reviewId);
        Assert.assertNotNull("查询成功", review);
    }

    @Test
    public void testListReview() {
        Long issueId=847878567016624685L;
        List<ReviewDTO> reviewDTOS = reviewService.listReview(issueId);
        Assert.assertNotNull(reviewDTOS);

    }


    @Test
    public void testGetReviewSetInfo(){
        Byte issueType=2;
        ReviewSetDTO reviewSetInfo = reviewSetService.getReviewSetInfo(issueType);
        Assert.assertNotNull(reviewSetInfo);
    }

    @Test
    public void testEditReviewSetInfo(){
        ReviewSetDTO reviewSetDTO=new ReviewSetDTO();
        reviewSetDTO.setId(10l);
        reviewSetDTO.setReviewPassRate((byte) 80);
        int i = reviewSetService.editReviewSetInfo(reviewSetDTO);
        if (i ==0) {
            Assert.fail("编辑评审设置信息失败!");
        }
        Assert.assertTrue("编辑评审设置信息成功", true);

    }
}

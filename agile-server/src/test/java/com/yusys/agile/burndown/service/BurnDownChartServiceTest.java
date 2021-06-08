package com.yusys.agile.burndown.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.burndown.dto.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class BurnDownChartServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BurnDownChartServiceTest.class);

    @Autowired
    private BurnDownChartService burnDownChartService;

    @Test
    public void calculateWorkloadTest() {
        try {
            burnDownChartService.calculateWorkload();
            Assert.assertTrue("calculateWorkloadTest成功", true);
        } catch (Exception e) {
            Assert.assertFalse("calculateWorkloadTest失败", true);
        }
    }

    @Test
    public void getBySprintTest() {
        try {
            Long sprintId = 100029L;
            BurnDownChartDTO chartDTO = burnDownChartService.getBySprint(sprintId);
            Assert.assertTrue("getBySprintTest成功", true);
        } catch (Exception e) {
            Assert.assertFalse("getBySprintTest失败", true);
        }

    }

    @Test
    public void getTasksBySprintTest() {
        try {
            Long sprintId = 891L;
            burnDownChartService.getTasksBySprint(sprintId);
            Assert.assertTrue("getTasksBySprintTest成功", true);
        } catch (Exception e) {
            Assert.assertFalse("getTasksBySprintTest失败", true);
        }

    }

    @Test
    public void getStorysBySprintTest() {
        try {
            Long sprintId = 891L;
            burnDownChartService.getStorysBySprint(sprintId);
            Assert.assertTrue("getStorysBySprintTest成功", true);
        } catch (Exception e) {
            Assert.assertFalse("getStorysBySprintTest失败", true);
        }
    }

    @Test
    public void testGetStoryPointsBySprint() {
        try {
            Long sprintId = 100034L;
            burnDownChartService.getStoryPointBySprint(sprintId);
            Assert.assertTrue("testGetStoryPointsBySprint成功", true);
        } catch (Exception e) {
            Assert.assertFalse("testGetStoryPointsBySprint失败", true);
        }
    }

    @Test
    public void calculateStorys() {
        try {
            burnDownChartService.calculateStorys();
            Assert.assertTrue("calculateStorys成功", true);
        } catch (Exception e) {
            Assert.assertFalse("calculateStorys失败", true);
        }
    }

    @Test
    public void getTaskMemberAnalysisTest() {
        try {
            burnDownChartService.getTaskMemberAnalysis(130080L);
            Assert.assertTrue("getTaskMemberAnalysisTest成功", true);
        } catch (Exception e) {
            Assert.assertFalse("getTaskMemberAnalysisTest失败", true);
        }
    }

    @Test
    public void testGetStorysBySprint() {
        Long sprintId = 100028L;
        BurnDownStoryDTO storysBySprint = burnDownChartService.getStorysBySprint(sprintId);
//        String sprintDays = "1621823328785|1621823328785|1621823328785";
//        List<Date> dateList = convertStrToDate(sprintDays);
        Assert.assertTrue("testGetStorysBySprint成功", true);
    }

    @Test
    public void getTasksBySprint(){
        Long sprint = 100035L;
        BurnDownTaskDTO tasksBySprint = burnDownChartService.getTasksBySprint(sprint);
        Assert.assertNotNull(tasksBySprint);
    }



}
package com.yusys.agile.burndown.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.burndown.dto.*;
import io.micrometer.core.instrument.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class BurnDownChartServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BurnDownChartServiceTest.class);

    @Autowired
    private BurnDownChartService burnDownChartService;

    @Test
    @Transactional
    public void calculateWorkloadTest() {
        try {
            burnDownChartService.calculateWorkload();
            log.info("Junit测试--计算迭代计划数据更新成功");
        } catch (Exception e) {
            log.info("Junit测试--计算迭代计划数据更新成功失败：{}", e);
        }
    }

    @Test
    public void getBySprintTest() {
        try {
            Long sprintId = 100035L;
            BurnDownChartDTO chartDTO = burnDownChartService.getBySprint(sprintId);
            log.info("Junit测试--根据迭代ID查询迭代计划饼图数据成功：{}", chartDTO.toString());
        } catch (Exception e) {
            log.info("Junit测试--根据迭代ID查询迭代计划饼图数据失败：{}", e);
        }

    }

    @Test
    public void getTasksBySprintTest() {
        try {
            Long sprintId = 891L;
            BurnDownTaskDTO taskDTO = burnDownChartService.getTasksBySprint(sprintId);
            log.info("Junit测试--根据迭代ID查询任务饼图数据成功：{}", taskDTO.toString());
        } catch (Exception e) {
            log.info("Junit测试--根据迭代ID查询任务饼图数据失败：{}", e);
        }

    }

    @Test
    public void getStorysBySprintTest() {
        try {
            Long sprintId = 891L;
            BurnDownStoryDTO burnDownStoryDTO = burnDownChartService.getStorysBySprint(sprintId);
            log.info("Junit测试--根据迭代ID查询用户故事饼图数据成功：{}", burnDownStoryDTO.toString());
        } catch (Exception e) {
            log.info("Junit测试--根据迭代ID查询用户故事饼图数据失败：{}", e);
        }
    }

    @Test
    public void testGetStoryPointsBySprint() {
        try {
            Long sprintId = 100034L;
            BurnDownStoryPointDTO storyPointBySprint = burnDownChartService.getStoryPointBySprint(sprintId);
            log.info("Junit测试--根据迭代ID查询故事点数据成功：{}", storyPointBySprint);
        } catch (Exception e) {
            log.info("Junit测试--根据迭代ID查询故事点数据失败：{}", e);
        }
    }

    @Test
    @Transactional
    public void calculateStorys() {
        try {
            burnDownChartService.calculateStorys();
            log.info("Junit测试--计算用户故事数据更新成功");
        } catch (Exception e) {
            log.info("Junit测试--计算用户故事数据更新成功失败：{}", e);
        }
    }

    @Test
    public void getTaskMemberAnalysisTest() {
        try {
            List<HistogramTaskDTO> taskMemberAnalysis = burnDownChartService.getTaskMemberAnalysis(130080L);
            log.info("Junit测试--根据迭代ID获取当前迭代内团队成员工作量和任务数成功：{}", taskMemberAnalysis);
        } catch (Exception e) {
            log.info("Junit测试--根据迭代ID获取当前迭代内团队成员工作量和任务数失败：{}", e);
        }
    }

    @Test
    public void testGetStorysBySprint() {
        Long sprintId = 100028L;
        BurnDownStoryDTO storysBySprint = burnDownChartService.getStorysBySprint(sprintId);
//        String sprintDays = "1621823328785|1621823328785|1621823328785";
//        List<Date> dateList = convertStrToDate(sprintDays);

    }

    @Test
    public void getTasksBySprint(){
        Long sprint = 100035L;
        BurnDownTaskDTO tasksBySprint = burnDownChartService.getTasksBySprint(sprint);
        Assert.assertNotNull(tasksBySprint);
    }



}
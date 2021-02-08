package com.yusys.agile.burndown.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.burndown.dto.BurnDownChartDTO;
import com.yusys.agile.burndown.dto.BurnDownStoryDTO;
import com.yusys.agile.burndown.dto.BurnDownTaskDTO;
import com.yusys.agile.burndown.dto.HistogramTaskDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith( SpringRunner.class )
@SpringBootTest(classes = {AgileApplication.class})
public class BurnDownChartServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BurnDownChartServiceTest.class);

    @Autowired
    private BurnDownChartService burnDownChartService;

    @Test
    @Transactional
    public void calculateWorkloadTest() {
        try{
            burnDownChartService.calculateWorkload();
            log.info("Junit测试--计算迭代计划数据更新成功");
        }catch (Exception e){
            log.info("Junit测试--计算迭代计划数据更新成功失败：{}", e);
        }
    }

    @Test
    public void getBySprintTest() {
        try{
            Long sprintId = 89L;
            BurnDownChartDTO chartDTO = burnDownChartService.getBySprint(sprintId);
            log.info("Junit测试--根据迭代ID查询迭代计划饼图数据成功：{}",chartDTO.toString());
        }catch (Exception e){
            log.info("Junit测试--根据迭代ID查询迭代计划饼图数据失败：{}", e);
        }

    }

    @Test
    public void getTasksBySprintTest() {
        try{
            Long sprintId = 891L;
            BurnDownTaskDTO taskDTO = burnDownChartService.getTasksBySprint(sprintId);
            log.info("Junit测试--根据迭代ID查询任务饼图数据成功：{}",taskDTO.toString());
        }catch (Exception e){
            log.info("Junit测试--根据迭代ID查询任务饼图数据失败：{}", e);
        }

    }

    @Test
    public void getStorysBySprintTest() {
        try{
            Long sprintId = 891L;
            BurnDownStoryDTO burnDownStoryDTO = burnDownChartService.getStorysBySprint(sprintId);
            log.info("Junit测试--根据迭代ID查询用户故事饼图数据成功：{}",burnDownStoryDTO.toString());
        }catch (Exception e){
            log.info("Junit测试--根据迭代ID查询用户故事饼图数据失败：{}", e);
        }
    }

    @Test
    @Transactional
    public void calculateStorys() {
        try{
            burnDownChartService.calculateStorys();
            log.info("Junit测试--计算用户故事数据更新成功");
        }catch (Exception e){
            log.info("Junit测试--计算用户故事数据更新成功失败：{}", e);
        }
    }
    @Test
    public void getTaskMemberAnalysisTest(){
        try{
            List<HistogramTaskDTO> taskMemberAnalysis = burnDownChartService.getTaskMemberAnalysis(130080L);
            log.info("Junit测试--根据迭代ID获取当前迭代内团队成员工作量和任务数成功：{}",taskMemberAnalysis);
        }catch (Exception e){
            log.info("Junit测试--根据迭代ID获取当前迭代内团队成员工作量和任务数失败：{}", e);
        }
    }
}
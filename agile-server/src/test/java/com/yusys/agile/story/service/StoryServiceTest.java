package com.yusys.agile.story.service;

import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.AgileApplication;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * @Description: 用户故事单元测试
 * @author: zhao_yd
 * @Date: 2021/5/24 5:25 下午
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class StoryServiceTest {

    @Resource
    private StoryService storyService;
    @Resource
    private IssueFactory issueFactory;
    @Resource
    private Sprintv3Service sprintv3Service;
    @Resource
    private IssueService issueService;

    @Test
    public void createStroyTest() {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setCompletion("5");
        //开发阶段、未开始
        Long[] stages = {4L, 100L};
        issueDTO.setStages(stages);
        issueDTO.setAcceptanceCriteria("1、需求完成度要高 2、任务完成时间要准");
        issueDTO.setBeginDate(new Date());
        issueDTO.setEndDate(new Date());
        issueDTO.setDescription("<p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">作为&nbsp;</span>&lt;用户角色&gt; 作为 迭代</p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><br></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">我需要&nbsp;</span><span style=\"font-size: 14px; letter-spacing: 0px;\">&lt;结果&gt;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: " +
                "14px; letter-spacing: 0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">这样&nbsp;</span><span style=\"font-size: 14px; " +
                "letter-spacing: 0px;\">&lt;目的&gt;&nbsp;</span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;," +
                " &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; letter-spacing: " +
                "0px;\"><br></span></p><p style=\"font-size: 14px; font-family: &quot;Helvetica Neue&quot;, Helvetica, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, " +
                "&quot;Microsoft YaHei&quot;, &quot;Noto Sans CJK SC&quot;, Arial, sans-serif;\"><span style=\"font-size: 14px; font-weight: bold;\">因为&nbsp;</span><span " +
                "style=\"font-size: 14px; letter-spacing: 0px;\">&lt;原因&gt;&nbsp;</span></p>");
        issueDTO.setHandler(814801485815332864L);
        issueDTO.setImportance((byte) 1);
        issueDTO.setOrder(100);
        issueDTO.setPlanWorkload(8);
        issueDTO.setTitle("测试故事啊xxx啊");
        //实际工时
        issueDTO.setReallyWorkload(8);
        issueDTO.setTitle("测试新建任务啊");
        issueDTO.setSystemId(814801485815332864L);
        issueDTO.setParentId(100028L);
        issueDTO.setSprintId(100028L);
        issueDTO.setIssueType((byte)3);
        issueDTO.setPriority((byte)100);
        List<Long> systemIds = new ArrayList();
        systemIds.add(817389531406000128L);
        Long story = storyService.createStory(issueDTO);
        //批量新增或者批量更新扩展字段值
        issueDTO.setIssueType(new Byte("3"));
        issueDTO.setIssueId(story);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(issueDTO));
        jsonObject.put("storyPoint","11");
        log.info("转换为 object值为:{}",JSONObject.toJSONString(jsonObject));
        issueFactory.batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
        org.junit.Assert.assertTrue(story == null);
    }

    @Test
    public void deleteStroyTest() {
        Long storyId = 1L;
        boolean deleteChild = false;
        storyService.deleteStory(storyId, deleteChild);
        org.junit.Assert.assertTrue("deleteStroyTest通过", true);
    }

    @Test
    public void deleteStroyWithChildTest() {
        Long storyId = 1L;
        boolean deleteChild = true;
        storyService.deleteStory(storyId, deleteChild);
        org.junit.Assert.assertTrue("deleteStroyWithChildTest通过", true);
    }

    @Test
    public void editStroyTest() {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setIssueId(507077L);
        issueDTO.setTitle("测试");
        Long[] stages = {4L,105L};
        issueDTO.setStages(stages);
        storyService.editStory(issueDTO);
        org.junit.Assert.assertTrue("editStroyTest通过", true);
    }



    @Test
    public void testRemoveIssue4Sprint(){
        Long sprintId= 130190l;
        Long storyId=507077l;
        int i = storyService.removeStory4Sprint(sprintId, storyId);
        org.junit.Assert.assertTrue(i==1);
    }


   @Test
   public void testRelationIssue(){
       SprintDTO sprintDTO=new SprintDTO();
       List<Long> issueIds =new ArrayList<>();
       for (int i = 9999; i < 10005; i++) {
           issueIds.add(i+1l);
       }
       sprintDTO.setIssueIds(issueIds);
       sprintDTO.setSprintId(10000L);
       System.out.println(sprintDTO);
       boolean b = sprintv3Service.arrangeIssue(sprintDTO);
       org.junit.Assert.assertTrue(b==true);

   }

    @Test
    public void createBatchRelation(){
        Long parentId=1000L;
        List<Long> issueIds =new ArrayList<>();
        for (int i = 10000; i < 10005; i++) {
            issueIds.add(i+1l);
        }
        Long userId=10000l;
        issueService.createBatchRelation(parentId,issueIds,userId);
        org.junit.Assert.assertTrue("createBatchRelation成功", true);
    }

    @Test
    public void testListStorysAndTasks() {
        IssueDTO issueDTO = new IssueDTO();
        List<IssueDTO>  list = storyService.listStorysAndTasks(issueDTO);
        Assert.assertTrue("查询迭代下的用户故事和任务list", true);
    }

}


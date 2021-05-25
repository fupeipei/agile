package com.yusys.agile.story.service;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.AgileApplication;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.StoryService;
import com.yusys.agile.issue.utils.IssueFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        issueDTO.setTitle("测试新建任务啊");
        issueDTO.setSystemId(814801485815332864L);
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
        Assert.isFalse(story == null);
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
}

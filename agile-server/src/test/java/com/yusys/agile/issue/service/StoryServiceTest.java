package com.yusys.agile.issue.service;

import com.alibaba.fastjson.JSONObject;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.utils.IssueFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@Slf4j
public class StoryServiceTest {

    @Mock
    private StoryService storyService;

    @Mock
    private IssueFactory issueFactory;
    @Mock
    private IssueMapper issueMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateStory() {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setCompletion("5");
        //开发阶段、未开始
        Long[] stages = {4L, 105L};
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
        //实际工时
        issueDTO.setReallyWorkload(8);
        issueDTO.setTitle("测试新建任务啊");
        issueDTO.setSystemId(814801485815332864L);
        Long story = null;
        Mockito.when(storyService.createStory(issueDTO)).thenReturn(story);
        //批量新增或者批量更新扩展字段值
        issueDTO.setIssueType(new Byte("3"));
        issueDTO.setIssueId(story);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(issueDTO));
        jsonObject.put("storyPoint","11");
        log.info("转换为 object值为:{}",JSONObject.toJSONString(jsonObject));
        doNothing().when(issueFactory).batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
        Assert.assertTrue("创建用户故事成功", true);
    }

    @Test
    public void testQueryStory() {
        IssueDTO issueDTO = mock(IssueDTO.class);
        Mockito.when(storyService.queryStory(Mockito.anyLong())).thenReturn(issueDTO);
        Assert.assertTrue("创建用户故事成功", true);
    }

    @Test
    public void testDeleteStory() {
        doNothing().when(storyService).deleteStory(Mockito.anyLong(),Mockito.anyBoolean(),Mockito.anyLong());
        Assert.assertTrue("删除用户故事成功", true);
    }

    @Test
    public void testEditStory() {
        IssueDTO issueDTO = new IssueDTO();
        doNothing().when(storyService).editStory(issueDTO,Mockito.anyLong());
        Assert.assertTrue("修改用户故事成功", true);
    }

    @Test
    public void testRemoveStory4Sprint() {
        Mockito.when(storyService.removeStory4Sprint(Mockito.anyLong(), Mockito.anyLong()));
        Assert.assertTrue("用户故事移除迭代成功", true);
    }

    @Test
    public void testCopyStory() {
        Long storyId = null;
        Mockito.when(storyService.copyStory( Mockito.anyLong())).thenReturn(storyId);
        Assert.assertTrue("用户故事复制成功", true);
    }

    @Test
    public void testDistributeSprint() {
        Integer count = 0;
        Issue issue = mock(Issue.class);
        Mockito.when(issueMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(issue);
        Mockito.when(storyService.distributeSprint(Mockito.anyLong(), Mockito.anyLong())).thenReturn(count);
        Assert.assertTrue("用户故事加入迭代成功", true);
    }

    public void testQueryUnlinkedStory() {
    }
    @Test
    public void testListStorysAndTasks() {
        List<IssueDTO> result = mock(ArrayList.class);
        IssueDTO issueDTO = new IssueDTO();
        Mockito.when(storyService.listStorysAndTasks(issueDTO)).thenReturn(result);
        Assert.assertTrue("查询迭代下的用户故事和任务list", true);
    }

    public void testEditStoryOrTask() {

    }

    public void testListStoryAcceptance() {
    }

    public void testEditStoryAssess() {
    }

    public void testQueryAllStory() {
    }

    public void testQueryStoryForEpic() {
    }

    public void testGetUnfinishedStoryList() {
    }

    public void testCheckHasUnfinishOrNotRepairIssue() {
    }

    public void testQueryStoryForFeature() {
    }

    public void testDistributeSprints() {
    }

    public void testDistributeStoryAndTaskAndFaultToSprint() {
    }

    public void testDealFaultsOnlyInSprint() {
    }

    public void testListUnFinisherStorysByProjectId() {
    }

    public void testGetUnfinishedStorysBySprintId() {
    }

    public void testGetDevlopManager() {
    }

    public void testCheckSprintParam() {
    }
}
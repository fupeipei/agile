package com.yusys.agile.issue.service;


import com.google.common.collect.Lists;
import com.yusys.agile.AgileApplication;
import com.yusys.agile.issue.dto.IssueAttachmentDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.utils.IssueFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.executor.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    private IssueDTO issueDTO;

    @Before
    public void initTaskTestData() {
        issueDTO = new IssueDTO();
        issueDTO.setTitle("这是一条单元测试 -- 新建任务测试");
//      任务执行人
        issueDTO.setHandler(812352455803777024L);
        issueDTO.setBeginDate(new Date());
        issueDTO.setEndDate(new Date());
        issueDTO.setIssueType(IssueTypeEnum.TYPE_TASK.CODE);
//        故事Id
        issueDTO.setParentId(507122L);
//        issueDTO.setImportance();
        issueDTO.setSystemId(817701268263542784L);
        issueDTO.setPlanWorkload(8);
        issueDTO.setDescription("富文本内容");
        issueDTO.setAcceptanceCriteria("验收标准");
//        附件
        List<IssueAttachmentDTO> issueAttachmentDTOS = Lists.newArrayList();
        IssueAttachmentDTO issueAttachmentDTO = new IssueAttachmentDTO();
        issueAttachmentDTO.setAttachmentId(10001L);
        issueAttachmentDTO.setAttachmentDesc("附件描述");
        issueAttachmentDTOS.add(issueAttachmentDTO);
        issueDTO.setAttachments(issueAttachmentDTOS);
    }

    @Test
    public void createIssue() {
        Long taskId = taskService.createTask(issueDTO);
        Assert.assertNotNull(taskId);
    }


    /**
     * 删除用户故事下的任务
     */
    @Test
    public void deleteTask() {
        taskService.deleteTask(846446177436880896L, false);
    }


    @Test
    public void editTask(){
        issueDTO.setIssueId(846689230687264768L);
        taskService.editTask(issueDTO);
    }
}

package com.yusys.agile.issue.service;


import com.google.common.collect.Lists;
import com.yusys.agile.AgileApplication;
import com.yusys.agile.issue.dto.IssueAttachmentDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.StoryCreatePrepInfoDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;
import java.util.PrimitiveIterator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    private IssueDTO issueDTO;

    private SecurityDTO securityDTO;

    @Before
    public void initTaskTestData() {
        issueDTO = new IssueDTO();
        issueDTO.setTitle("这是一条单元测试 -- 新建任务测试");
//      任务执行人
        issueDTO.setHandler(807911012579938304L);
        issueDTO.setBeginDate(new Date());
        issueDTO.setEndDate(new Date());
        issueDTO.setIssueType(IssueTypeEnum.TYPE_TASK.CODE);
//        issueDTO.setImportance();
        issueDTO.setSystemId(814801485815332864L);
        issueDTO.setPlanWorkload(8);
        issueDTO.setParentId(847061835316670464L);
        issueDTO.setDescription("富文本内容");
        issueDTO.setAcceptanceCriteria("验收标准");
//        附件
        List<IssueAttachmentDTO> issueAttachmentDTOS = Lists.newArrayList();
        IssueAttachmentDTO issueAttachmentDTO = new IssueAttachmentDTO();
        issueAttachmentDTO.setAttachmentDesc("附件描述");
        issueAttachmentDTO.setUpdateUid(1111L);
        issueAttachmentDTOS.add(issueAttachmentDTO);
        issueDTO.setAttachments(issueAttachmentDTOS);

        securityDTO = new SecurityDTO();
        securityDTO.setUserId(807911012579938304L);
        securityDTO.setSystemId(817701268263542784L);
        securityDTO.setTenantCode("1");
        securityDTO.setUserName("何红玉1");
        securityDTO.setUserAcct("hehy4");
    }

    @Test
    public void createIssue() {
        Long taskId = taskService.createTask(issueDTO);
        Assert.assertNotNull(taskId);
    }

    @Test
    public void editTask() {
        issueDTO.setIssueId(847116505917378560L);
        Long[] stages = {1L,101L};
        issueDTO.setStages(stages);
        taskService.editTask(issueDTO,securityDTO);
        Assert.assertTrue("editTask成功",true);
    }

    /**
     * 删除用户故事下的任务
     */
    @Test
    public void deleteTask() {
        taskService.deleteTask(846446177436880896L, false);
        Assert.assertTrue("deleteTask成功",true);

    }

    @Test
    public void deleteChildrenTask() {
        taskService.deleteTask(846446177436880896L, true);
        Assert.assertTrue("deleteChildrenTask成功",true);
    }

    @Test
    public void getTaskPreInfo(){
        String userName = "";
        Integer page = 1;
        Integer pageSize = 10;
        Long systemId = 814801485815332864L;
        Long storyId = 847060328389558272L;
        Integer createType = 1;
        StoryCreatePrepInfoDTO taskPreInfo = taskService.getTaskPreInfo(userName, page, pageSize, systemId, storyId, createType);
        System.out.println(taskPreInfo);
    }


}

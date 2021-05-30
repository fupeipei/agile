package com.yusys.agile.issue.service;


import com.google.common.collect.Lists;
import com.yusys.agile.AgileApplication;
import com.yusys.agile.issue.dto.IssueAttachmentDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.StoryCreatePrepInfoDTO;
import com.yusys.agile.issue.enums.CreateTypeEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    private IssueDTO issueDTO;

    private SecurityDTO securityDTO;

    @Autowired
    DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;



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
        UserThreadLocalUtil.setUserInfo(securityDTO);

        //数据准备
        SqlLoadTest.execute("classpath:/sql/sqlFileForTaskService.sql",dataSource,resourceLoader);
    }

    @Test
    public void createIssue() {
        Long taskId = taskService.createTask(issueDTO);
        Assert.assertNotNull(taskId);
    }

    @Test
    public void createIssueNoHandler() {
        issueDTO.setHandler(null);
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
    public void dragTask() {
        //SqlLoadTest.execute("classpath:/sql/sqlFileForTaskService.sql",dataSource,resourceLoader);
        //  task   --119,118,117,116 ,  129  ,128,127,128  ，laneId都是  107

//        TYPE_ADD_STATE("未领取", 107L),
//        TYPE_RECEIVED_STATE("已领取", 108L),
//        TYPE_MODIFYING_STATE("进行中", 109L),
//        TYPE_CLOSED_STATE("已完成", 110L);


        //角色id，PO:104，SM:103，TM:105
        SecurityDTO secDTO = new SecurityDTO();
        secDTO.setUserId(834451097091657728L);
        //secDTO.setSystemId(817701268263542784L);
        secDTO.setTenantCode("1");
        secDTO.setUserName("杜杉");
        secDTO.setUserAcct("dushan1");

        UserThreadLocalUtil.setUserInfo(secDTO);
        long taskId=119L;
        //"未领取", 107L      "已领取", 108L
        taskService.dragTask(taskId,107L,108L,null);
        Assert.assertTrue("deleteTask成功",true);

    }

    @Test
    public void getTaskPreInfo(){
        String userName = "";
        Integer page = 1;
        Integer pageSize = 10;
        Long systemId = 816048836585721856L;
        Long storyId = 507112L;
        Integer createType = CreateTypeEnum.KANBAN.CODE;
        StoryCreatePrepInfoDTO taskPreInfo = taskService.getTaskPreInfo(userName, page, pageSize, systemId, storyId, createType);
        Assert.assertNotNull("查询成功",taskPreInfo);
    }

    @Test
    public void getTaskPreInfoByCreateType(){
        String userName = "";
        Integer page = 1;
        Integer pageSize = 10;
        Long systemId = 816048836585721856L;
        Long storyId = 507112L;
        Integer createType = CreateTypeEnum.LIST.CODE;
        StoryCreatePrepInfoDTO taskPreInfo = taskService.getTaskPreInfo(userName, page, pageSize, systemId, storyId, createType);
        Assert.assertNotNull("查询成功",taskPreInfo);
    }


}

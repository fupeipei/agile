package com.yusys.agile.issue.service;


import com.google.common.collect.Lists;
import com.yusys.agile.AgileApplication;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.IssueAttachmentDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.StoryCreatePrepInfoDTO;
import com.yusys.agile.issue.enums.CreateTypeEnum;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.StoryStatusEnum;
import com.yusys.agile.issue.enums.TaskStatusEnum;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
@Slf4j
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    private IssueDTO issueDTO;

    private SecurityDTO securityDTO;

    @Autowired
    DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private IssueMapper issueMapper;

    private volatile boolean isSqlLoaded=false;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        System.out.println("@BeforeClass");

    }



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
    }

    @Test
    public void createIssue() {
        Long taskId = taskService.createTask(issueDTO);
        Assert.assertNotNull(taskId);
    }

    @Test
    public void copyTask(){
        Long taskId  = 847878567016624175L;
        Long copyTaskId = taskService.copyTask(taskId, null);
        Assert.assertTrue("任务复制成功",true);
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
        //数据准备
        SqlLoadTest.execute("classpath:/sql/sqlFileForTaskService.sql",dataSource,resourceLoader);

        taskService.deleteTask(846446177436880896L, false);
        Assert.assertTrue("deleteTask成功",true);
        //角色id，PO:104，SM:103，TM:105
        SecurityDTO secDTO = new SecurityDTO();
        secDTO.setUserId(834451097091657728L);
        //secDTO.setSystemId(817701268263542784L);
        secDTO.setTenantCode("1");
        secDTO.setUserName("杜杉");
        secDTO.setUserAcct("dushan1");
        UserThreadLocalUtil.setUserInfo(secDTO);
        long taskId=119L;

        //故事下的唯一任务被删除，故事状态改为已完成。
        taskService.deleteTask(126L,false);
        Issue issue1 = issueMapper.selectByPrimaryKey(219L);
        Assert.assertEquals("126L故事状态修改", StoryStatusEnum.TYPE_CLOSED_STATE.CODE,issue1.getLaneId());


        //故事下的非唯一任务被删除，故事状态不变。
        taskService.deleteTask(119L,false);
        issue1 = issueMapper.selectByPrimaryKey(115L);
        Assert.assertEquals("119L原状态保持不变", StoryStatusEnum.TYPE_MODIFYING_STATE.CODE,issue1.getLaneId());

    }

    @Test
    public void deleteChildrenTask() {
        taskService.deleteTask(846446177436880896L, true);
        Assert.assertTrue("deleteChildrenTask成功",true);
    }

    @Test
    public void dragTask() {
        //角色id，PO:104，SM:103，TM:105
        //数据准备
        SqlLoadTest.execute("classpath:/sql/sqlFileForTaskService.sql",dataSource,resourceLoader);

        SecurityDTO secDTO = new SecurityDTO();
        secDTO.setUserId(834451097091657728L);
        //secDTO.setSystemId(817701268263542784L);
        secDTO.setTenantCode("1");
        secDTO.setUserName("杜杉");
        secDTO.setUserAcct("dushan1");
        UserThreadLocalUtil.setUserInfo(secDTO);
        long taskId=119L;

        //"未领取", 107L      "已领取", 108L
        Issue issue = taskService.dragTask(taskId, 107L, TaskStatusEnum.TYPE_RECEIVED_STATE.CODE, null);
        Issue issue1 = issueMapper.selectByPrimaryKey(taskId);
        Assert.assertEquals("sm本人拖拽成功",TaskStatusEnum.TYPE_RECEIVED_STATE.CODE+":"+secDTO.getUserId(),issue1.getLaneId()+":"+issue1.getHandler());


        //回到未领取   处理人改为空
        issue = taskService.dragTask(taskId, TaskStatusEnum.TYPE_RECEIVED_STATE.CODE,107L, null);
        issue1 = issueMapper.selectByPrimaryKey(taskId);
        Assert.assertEquals("sm本人拖回 未领取",TaskStatusEnum.TYPE_ADD_STATE.CODE,issue1.getLaneId());
        Assert.assertNull("清空处理人",issue1.getHandler());


        secDTO = new SecurityDTO();
        secDTO.setUserId(842399068826558464L);
        //secDTO.setSystemId(817701268263542784L);
        secDTO.setTenantCode("1");
        secDTO.setUserName("李艮艮");
        secDTO.setUserAcct("ligg");
        UserThreadLocalUtil.setUserInfo(secDTO);
        taskId=118L;

        //"未领取", 107L      "已领取", 108L
        issue = taskService.dragTask(taskId, 107L, TaskStatusEnum.TYPE_RECEIVED_STATE.CODE, null);
        issue1 = issueMapper.selectByPrimaryKey(taskId);
        Assert.assertEquals("成员拖拽成功",TaskStatusEnum.TYPE_RECEIVED_STATE.CODE+":"+secDTO.getUserId(),issue1.getLaneId()+":"+issue1.getHandler());

        //故事下的非唯一任务被拖动，故事状态不变。
        taskService.dragTask(119L,107L, TaskStatusEnum.TYPE_CLOSED_STATE.CODE, null);
        issue1 = issueMapper.selectByPrimaryKey(115L);
        //进行中
        Assert.assertEquals("119L原状态保持不变", StoryStatusEnum.TYPE_MODIFYING_STATE.CODE,issue1.getLaneId());



        //故事下的唯一任务被拖动，故事状态改为已完成。
        taskService.dragTask(126L,107L, TaskStatusEnum.TYPE_CLOSED_STATE.CODE, null);
        issue1 = issueMapper.selectByPrimaryKey(219L);
        Assert.assertEquals("126L故事状态修改", StoryStatusEnum.TYPE_CLOSED_STATE.CODE,issue1.getLaneId());


        secDTO = new SecurityDTO();
        secDTO.setUserId(834451097091657728L);
        //secDTO.setSystemId(817701268263542784L);
        secDTO.setTenantCode("1");
        secDTO.setUserName("杜杉");
        secDTO.setUserAcct("dushan1");
        UserThreadLocalUtil.setUserInfo(secDTO);
        taskId=118L;

        //"未领取", 107L      "已领取", 108L
        issue = taskService.dragTask(taskId, TaskStatusEnum.TYPE_RECEIVED_STATE.CODE, TaskStatusEnum.TYPE_CLOSED_STATE.CODE, null);
        issue1 = issueMapper.selectByPrimaryKey(taskId);
        Assert.assertEquals("sm拖拽不修改处理人成功",TaskStatusEnum.TYPE_CLOSED_STATE.CODE+":"+842399068826558464L,issue1.getLaneId()+":"+issue1.getHandler());
    }

    @Test(expected = BusinessException.class)
    public void dragTaskSm() {
        //数据准备
        SqlLoadTest.execute("classpath:/sql/sqlFileForTaskService.sql",dataSource,resourceLoader);

        //角色id，PO:104，SM:103，TM:105
        SecurityDTO secDTO = new SecurityDTO();
        secDTO.setUserId(841351045005778944L);
        //secDTO.setSystemId(817701268263542784L);
        secDTO.setTenantCode("1");
        secDTO.setUserName("顼权浩");
        secDTO.setUserAcct("xuqh1");
        UserThreadLocalUtil.setUserInfo(secDTO);
        long taskId=118L;

        //"未领取", 107L      "已领取", 108L
        Issue issue = taskService.dragTask(taskId, 107L, TaskStatusEnum.TYPE_RECEIVED_STATE.CODE, null);
        Issue issue1 = issueMapper.selectByPrimaryKey(taskId);
        Assert.assertEquals("Sm本人拖拽没人报异常",TaskStatusEnum.TYPE_RECEIVED_STATE.CODE+":"+secDTO.getUserId(),issue1.getLaneId()+":"+issue1.getHandler());
    }

    public void dragTaskSm2() {
        //数据准备
        SqlLoadTest.execute("classpath:/sql/sqlFileForTaskService.sql",dataSource,resourceLoader);

        //角色id，PO:104，SM:103，TM:105
        SecurityDTO secDTO = new SecurityDTO();
        secDTO.setUserId(841351045005778944L);
        //secDTO.setSystemId(817701268263542784L);
        secDTO.setTenantCode("1");
        secDTO.setUserName("顼权浩");
        secDTO.setUserAcct("xuqh1");
        UserThreadLocalUtil.setUserInfo(secDTO);

        long taskId=118L;
        Issue issue2 = new Issue();
        issue2.setIssueId(taskId);
        issue2.setHandler(45005778944L);
        issueMapper.updateByPrimaryKeySelectiveWithNull(issue2);


        //"未领取", 107L      "已领取", 108L
        Issue issue = taskService.dragTask(taskId, 107L, TaskStatusEnum.TYPE_RECEIVED_STATE.CODE, null);
        Issue issue1 = issueMapper.selectByPrimaryKey(taskId);
        Assert.assertEquals("Sm拖拽有人成功",TaskStatusEnum.TYPE_RECEIVED_STATE.CODE+":"+secDTO.getUserId(),issue1.getLaneId()+":"+issue1.getHandler());
    }


    @Test(expected = BusinessException.class)
    public void dragTaskPo() {

        //角色id，PO:104，SM:103，TM:105
        SecurityDTO secDTO = new SecurityDTO();
        secDTO.setUserId(834731929562857472L);
        //secDTO.setSystemId(817701268263542784L);
        secDTO.setTenantCode("1");
        secDTO.setUserName("刘行");
        secDTO.setUserAcct("liuxing4");
        UserThreadLocalUtil.setUserInfo(secDTO);
        long taskId=118L;

        //"未领取", 107L      "已领取", 108L
        Issue issue = taskService.dragTask(taskId, 107L, TaskStatusEnum.TYPE_RECEIVED_STATE.CODE, null);
        Issue issue1 = issueMapper.selectByPrimaryKey(taskId);
        Assert.assertEquals("Po本人拖拽成功",TaskStatusEnum.TYPE_RECEIVED_STATE.CODE+":"+secDTO.getUserId(),issue1.getLaneId()+":"+issue1.getHandler());
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

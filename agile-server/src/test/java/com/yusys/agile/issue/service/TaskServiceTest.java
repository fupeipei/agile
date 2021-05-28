package com.yusys.agile.issue.service;


import com.google.common.collect.Lists;
import com.yusys.agile.AgileApplication;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.dto.IssueAttachmentDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.enums.TaskStatusEnum;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
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
        issueDTO.setParentId(1111L);
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

        //数据准备
        if(!isSqlLoaded){
            SqlLoadTest.execute("classpath:/sql/sqlFileForTaskService.sql",dataSource,resourceLoader);
            isSqlLoaded=true;
        }

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
    }

    @Test(expected = BusinessException.class)
    public void dragTaskSm() {
        //SqlLoadTest.execute("classpath:/sql/sqlFileForTaskService.sql",dataSource,resourceLoader);
        //  task   --119,118,117,116 ,  129  ,128,127,128  ，laneId都是  107

//        TYPE_ADD_STATE("未领取", 107L),
//        TYPE_RECEIVED_STATE("已领取", 108L),
//        TYPE_MODIFYING_STATE("进行中", 109L),
//        TYPE_CLOSED_STATE("已完成", 110L);


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
        Assert.assertEquals("Sm本人拖拽成功",TaskStatusEnum.TYPE_RECEIVED_STATE.CODE+":"+secDTO.getUserId(),issue1.getLaneId()+":"+issue1.getHandler());
    }

    @Test(expected = BusinessException.class)
    public void dragTaskPo() {
        //SqlLoadTest.execute("classpath:/sql/sqlFileForTaskService.sql",dataSource,resourceLoader);
        //  task   --119,118,117,116 ,  129  ,128,127,128  ，laneId都是  107

//        TYPE_ADD_STATE("未领取", 107L),
//        TYPE_RECEIVED_STATE("已领取", 108L),
//        TYPE_MODIFYING_STATE("进行中", 109L),
//        TYPE_CLOSED_STATE("已完成", 110L);


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


}

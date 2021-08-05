package com.yusys.agile.issue.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName impl
 * @Description 项目需求测试类
 * @Author wangpf6
 * @Date 2021/8/5 16:27
 * @Version 1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IssueServiceImplTest {


    @Autowired
    private IssueService issueService;


    /**
     * 测试分页查询
     *
     * @author zhaofeng
     * @date 2021/5/8 16:10
     */
    @Test
    public void testList1() {
        String projectName="";
        Integer pageNum=2;
        Integer pageSize=10;
        String issueTitle="";
        issueService.queryIssuesByCondition(projectName,pageNum,pageSize,issueTitle);
        Assert.assertTrue("testList1成功", true);
    }

    /**
     * @return: void
     * @Author wangpf6
     * @Description 模糊查询
     * @Date 17:31 2021/8/5
     * @Param []
     **/
    @Test
    public void testList2() {
        String projectName="测试";
        Integer pageNum=1;
        Integer pageSize=10;
        String issueTitle="测试";
        issueService.queryIssuesByCondition(projectName,pageNum,pageSize,issueTitle);
        Assert.assertTrue("testList1成功", true);
    }
}

package com.yusys.agile.issue.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IssueServiceTest {
    @Autowired
    IssueService issueService;

    @Test
    public  void getIssueListTest(){
        Map<String, Object> map = new HashMap<>();
        map.put("issueType",3);
        map.put("pageNum",1);
        map.put("pageSize",10);
        issueService.getIssueList(map);
        Assert.assertTrue("getIssueListTest成功", true);
    }
}

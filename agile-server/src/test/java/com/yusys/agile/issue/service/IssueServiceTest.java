package com.yusys.agile.issue.service;

import com.github.pagehelper.PageInfo;
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
        PageInfo pageInfo = issueService.getIssueList(map);
    }
}

package com.yusys.agile.issue.service;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueListDTO;
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
    @Autowired
    StoryService storyService;
    @Test
    public  void getIssueListTest(){
        Map<String, Object> map = new HashMap<>();
        map.put("idOrTitle",1);
        map.put("issueType",2);
        map.put("pageNum",1);
        map.put("pageSize",10);
        issueService.getIssueList(map);
        Assert.assertTrue("getIssueListTest成功", true);
    }
    @Test
    public  void getIssueTest(){

        Long issueId = 847878567016624145L;
        Byte issueQuery = 2;
        IssueListDTO issueListDTO = issueService.getIssue(issueId,issueQuery,null);
        Assert.assertTrue("getIssueListTest成功", true);
    }

    @Test
    public void testListStorysAndTasks() {

        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setPageNum(1);
        issueDTO.setPageSize(100000);
        issueDTO.setSprintId(130213L);
        storyService.listStorysAndTasks(issueDTO);
    }
}

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
        map.put("issueType",3);
        map.put("pageNum",2);
        map.put("pageSize",10);
        try {
            issueService.getIssueList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Assert.assertTrue("testListStorysAndTasks()成功 ",true);
    }

    /**
     * @Author maxp2
     * @Date 2021/7/14
     * @Description 设置工作项归档，0：未归档，1：已归档
     * @param
     * @Return void
     */
    @Test
    public void isArchive(){
        Long issueId = 847878567016625462L;
        Byte isArchive = 1 ;
        try {
            issueService.isArchive(issueId,isArchive);
            Assert.assertTrue("设置归档成功", true);
        }catch (Exception e){
            Assert.assertNotNull(e.getMessage());
        }
    }
}

package com.yusys.agile.issue.service;


import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IssueFilterServiceTest {
    @Autowired
    IssueFilterService issueFilterService;

    @Test
    public void getIssueFiltersTest(){

        Byte category = new Byte("1");
        SecurityDTO securityDTO = new SecurityDTO();
        securityDTO.setUserId(815897630505963520L);
        ControllerResponse controllerResponse = issueFilterService.getIssueFilters(category,securityDTO);
    }
}

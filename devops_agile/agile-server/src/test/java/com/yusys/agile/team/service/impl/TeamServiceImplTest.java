package com.yusys.agile.team.service.impl;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.fault.enums.UserRelateTypeEnum;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.agile.team.service.TeamService;
import com.yusys.portal.model.facade.entity.SsoUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith( SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class TeamServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(TeamServiceImplTest.class);
    @Autowired
    private TeamService teamService;
    @Test
    public void getListTeam() {
        try{
            Long projectId = 999999L;
            List<TeamDTO> listTeam = teamService.getListTeam(projectId);
            LOG.info("Junit测试--根据项目Id获取团队列表成功：{}", listTeam.toString());
        }catch (Exception e){
            LOG.error("Junit测试--根据项目Id获取团队列表异常：{}", e);
        }
    }

    @Test
    public void getUserSprintHour() {

    }

    @Test
    public void listMemberUsers() {
        Long subjectId = 999999L;
        List<SsoUser> ssoUsers = teamService.listMemberUsers(subjectId, UserRelateTypeEnum.PROJECT.getValue());
        LOG.info("Junit测试--根据项目Id获取团队成员列表成功：{}", ssoUsers.toString());
    }
}
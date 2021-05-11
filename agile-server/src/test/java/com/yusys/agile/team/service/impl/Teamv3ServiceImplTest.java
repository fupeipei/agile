package com.yusys.agile.team.service.impl;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamUser;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.common.id.IdGenerator;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 单元测试
 *
 * @Author zhaofeng
 * @Date 2021/5/8 15:55
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class Teamv3ServiceImplTest {

    @Autowired
    private Teamv3Service teamv3Service;
    @Autowired
    private STeamMapper sTeamMapper;

    private SecurityDTO securityDTO;

    public STeam initTeam() {
        STeam team = new STeam();
        team.setTeamId(840617539415855106l);
        team.setTeamName("测试团队");
        List<Long> systems = new ArrayList();
        systems.add(816662916938469376L);
        team.setSystemIds(systems);
        List<STeamUser> users = new ArrayList();
        STeamUser user1 = new STeamUser();
        user1.setUserId(816662609684729856L);
        user1.setUserName("a5");
        user1.setUserAccount("a5");
        user1.setSystemId(816662916938469376L);
        users.add(user1);
        team.setTeamUsers(users);
        List<Long> teamPOs = new ArrayList();
        teamPOs.add(807202108292194304L);
        teamPOs.add(816662609684729856L);
        team.setTeamPoS(teamPOs);
        List<Long> teamSmS = new ArrayList();
        teamSmS.add(819577970802257920L);
        team.setTeamSmS(teamSmS);
        return team;
    }

    @Before
    public void setUp() {
        this.securityDTO = new SecurityDTO();
        securityDTO.setUserId(812352455803777024L);
        securityDTO.setSystemId(817701268263542784L);
        securityDTO.setTenantCode("1");
        securityDTO.setUserName("马雪萍");
        securityDTO.setUserAcct("maxueq");
    }

    /**
     * 测试分页查询
     *
     * @author zhaofeng
     * @date 2021/5/8 16:10
     */
    @Test
    public void testList1() {
        TeamQueryDTO dto = new TeamQueryDTO();
        dto.setPageSize(10);
        dto.setPageNum(1);
        List<TeamListDTO> list = teamv3Service.listTeam(dto, this.securityDTO);
        log.info("分页查询列表 list={}", list);
    }
    /**
     * 测试分页查询-team
     * @author zhaofeng
     * @date 2021/5/8 16:10
     */
    @Test
    public void testList2(){
        TeamQueryDTO dto = new TeamQueryDTO();
        dto.setPageSize(10);
        dto.setPageNum(1);
        dto.setTeam("联");
        List<TeamListDTO> list = teamv3Service.listTeam(dto, this.securityDTO);
        log.info("分页查询列表 list={}", list);
    }
    @Test
    public void testList3(){
        TeamQueryDTO dto = new TeamQueryDTO();
        dto.setPageSize(10);
        dto.setPageNum(1);
        dto.setTeam("联");
        dto.setPo("zhaoydd");
        List<TeamListDTO> list = teamv3Service.listTeam(dto, this.securityDTO);
        log.info("分页查询列表 list={}", list);
    }
    /**
     * 新增团队
     */
    @Test
    public void insertTeam() {
        for (int i=0; i<5; i++){
            STeam team = new STeam();
            team.setTeamName("联通ggxx"+i);
            team.setTeamDesc("联通ggxx"+i);
            team.setState(StateEnum.U.getValue());
            team.setTeamPoS(Arrays.asList(841351045005778944L,816974204303933440L));
            team.setTeamSmS(Arrays.asList(817430824963395584L,817436627609112576L));
            List<STeamUser> users = new ArrayList();
            STeamUser user1 = new STeamUser();
            user1.setUserId(816662609684729856L);
            user1.setUserName("a5");
            user1.setUserAccount("a5");
            user1.setSystemId(816662916938469376L);
            users.add(user1);
            team.setTeamUsers(users);
            team.setSystemIds(Arrays.asList(816662916938469376L));
            String s = teamv3Service.insertTeam(team);
            log.info("当前第【{}】条数据{}插入成功",i, team);
        }

    }

    /**
     * 删除团队
     */
    @Test
    public void deleteTeam() {
        String s = teamv3Service.deleteTeam(1);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    /**
     * 更新团队
     */
    @Test
    public void updateTeam() {
        STeam team = initTeam();
        String s = teamv3Service.updateTeam(team);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    /**
     * 查询团队
     */
    @Test
    public void queryTeam() {
        STeam team = initTeam();
        QueryTeamResponse queryTeamResponse = teamv3Service.queryTeam(team.getTeamId());
        System.out.println("******");
        System.out.println(queryTeamResponse);
        Assert.assertNotNull(queryTeamResponse);
    }

}

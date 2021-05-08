package com.yusys.agile.team.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yusys.agile.AgileApplication;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.common.id.IdGenerator;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yusys.agile.teamv3.enums.TeamRoleEnum.*;

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
        systems.add(783336382841401344L);
        systems.add(816664862883926016L);
        systems.add(808372190482649088L);
        team.setSystemIds(systems);
        List<Long> users = new ArrayList();
        users.add(816662609684729856L);
        users.add(807202108292194304L);
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
     * 新增团队
     */
    @Test
    public void insertTeam() {
        STeam team = initTeam();
        if (null == team.getTeamId()) {
            IdGenerator idGenerator = new IdGenerator();
            team.setTeamId(idGenerator.nextId());
        }
        int i = sTeamMapper.insertSelective(team);
        System.out.println(i);

    }

    /**
     * 删除团队
     */
    @Test
    public void deleteTeam() {
        STeam team = initTeam();
        String s = teamv3Service.deleteTeam(team.getTeamId());
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

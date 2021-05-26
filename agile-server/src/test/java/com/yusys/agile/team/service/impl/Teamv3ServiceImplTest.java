package com.yusys.agile.team.service.impl;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.agile.teamv3.domain.STeamMember;
import com.yusys.agile.teamv3.response.QueryTeamResponse;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.common.exception.BusinessException;
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

    private SecurityDTO securityDTO;

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
     * 新增团队--团队名称重复情况
     */
    @Test
    public void insertTeam() {
        STeam team = new STeam();
        team.setTeamName("团队002");
        team.setTeamDesc("描述");
        team.setTenantCode("1");
        STeamMember po1 = new STeamMember();
        po1.setUserId(846427329554370560L);
        po1.setUserName("张宇");
        po1.setUserAccount("zhangyu");
        team.setTeamPoS(Arrays.asList(po1));
        STeamMember sm1 = new STeamMember();
        sm1.setUserId(842765807724986368L);
        sm1.setUserName("ceshieeee");
        sm1.setUserAccount("ceshieeee");
        team.setTeamSmS(Arrays.asList(sm1));
        team.setSystemIds(Arrays.asList(816356430371512320L));
        try {
            teamv3Service.insertTeam(team);
        }catch (Exception e){
            Assert.assertTrue(e.getMessage() != null);
        }
    }
    /**
     * 新增团队--PO、SM重复情况
     */
    @Test
    public void insertTeam2() {
        STeam team = new STeam();
        team.setTeamName("团队003");
        team.setTeamDesc("描述");
        team.setTenantCode("1");
        STeamMember po1 = new STeamMember();
        po1.setUserId(846427329554370560L);
        po1.setUserName("张宇");
        po1.setUserAccount("zhangyu");
        team.setTeamPoS(Arrays.asList(po1));
        STeamMember sm1 = new STeamMember();
        sm1.setUserId(846427329554370560L);
        sm1.setUserName("张宇");
        sm1.setUserAccount("zhangyu");
        team.setTeamSmS(Arrays.asList(sm1));
        team.setSystemIds(Arrays.asList(816356430371512320L));
        try {
            teamv3Service.insertTeam(team);
        }catch (Exception e){
            Assert.assertTrue(e.getMessage() != null);
        }
    }

    /**
     * 删除团队
     */
    @Test
    public void deleteTeam() {
        teamv3Service.deleteTeam(1);
    }

    /**
     * 更新团队
     */
    @Test
    public void updateTeam() {
        STeam team = new STeam();
        team.setTeamId(100002L);
        team.setTeamName("团队003");
        team.setTeamDesc("描述");
        team.setTenantCode("1");
        STeamMember po1 = new STeamMember();
        po1.setUserId(834731929562857472L);
        po1.setUserName("刘行");
        po1.setUserAccount("liuxing4");
        team.setTeamPoS(Arrays.asList(po1));
        STeamMember sm1 = new STeamMember();
        sm1.setUserId(846427329554370560L);
        sm1.setUserName("张宇");
        sm1.setUserAccount("zhangyu");
        team.setTeamSmS(Arrays.asList(sm1));
        team.setSystemIds(Arrays.asList(816356430371512320L));
        try {
            teamv3Service.updateTeam(team);
        }catch (Exception e){
            Assert.assertTrue(e.getMessage() != null);
        }
    }

    /**
     * 查询团队
     */
    @Test
    public void queryTeam() {
        Long teamId = 100007L;
        QueryTeamResponse response = teamv3Service.queryTeam(teamId);
        log.info("团队详情：【{}}", response);
    }

}

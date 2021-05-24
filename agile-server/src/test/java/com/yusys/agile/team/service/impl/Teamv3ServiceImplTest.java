package com.yusys.agile.team.service.impl;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.team.dto.TeamListDTO;
import com.yusys.agile.team.dto.TeamQueryDTO;
import com.yusys.agile.teamv3.service.Teamv3Service;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
     * 新增团队
     */
    @Test
    public void insertTeam() {

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
    }

    /**
     * 查询团队
     */
    @Test
    public void queryTeam() {

    }

}

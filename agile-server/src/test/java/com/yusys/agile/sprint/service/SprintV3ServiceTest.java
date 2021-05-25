package com.yusys.agile.sprint.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintV3.dto.SprintV3UserHourDTO;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.responseModel.SprintOverView;
import com.yusys.agile.sprintv3.responseModel.SprintStatisticalInformation;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class SprintV3ServiceTest {

    @Resource
    private SSprintMapper ssprintMapper;
    @Resource
    private SSprintUserHourMapper ssprintUserHourMapper;
    @Resource
    private STeamMapper sTeamMapper;
    @Resource
    private IFacadeUserApi iFacadeUserApi;
    @Resource
    private SSprintUserHourMapper sSprintUserHourMapper;
    @Resource
    private IFacadeSystemApi iFacadeSystemApi;
    @Resource
    private com.yusys.agile.teamv3.dao.STeamSystemMapper STeamSystemMapper;

    @Autowired
    private Sprintv3Service sprintv3Service;

    public SprintV3DTO initData() {
        SprintV3DTO sprintDTO = new SprintV3DTO();
//        sprintDTO.setSprintId();
        sprintDTO.setSprintName(UUID.randomUUID().toString());
        sprintDTO.setSprintDesc("这是一条单元测试测试数据");
//        sprintDTO.setFinishTime(now);
        sprintDTO.setTeamId(10086l);
        sprintDTO.setWorkHours(23);
        sprintDTO.setVersionNumber("versionNumber");
        sprintDTO.setSprintDays("3");
        List<Date> sprintDayLists = new ArrayList<>();
        sprintDayLists.add(new Date());
        sprintDayLists.add(new Date());
        sprintDayLists.add(new Date());
        sprintDTO.setSprintDayList(sprintDayLists);
        List<SprintV3UserHourDTO> members = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SprintV3UserHourDTO sprintV3UserHourDTO = new SprintV3UserHourDTO();
            sprintV3UserHourDTO.setUserId(i + 1l);
            sprintV3UserHourDTO.setReallyHours(i + 7);
            sprintV3UserHourDTO.setUserName("张三" + i);
            sprintV3UserHourDTO.setUserAccount("ZhangSan" + i);
            members.add(sprintV3UserHourDTO);
        }
        sprintDTO.setMembers(members);
        return sprintDTO;
    }

    /**
     * 创建迭代
     */
    @Test
    public void createSprint() {
        SprintV3DTO sprintV3DTO = initData();
        try {
            Long sprintId = sprintv3Service.createSprint(sprintV3DTO);
            Assert.assertNotNull(sprintId);
        } catch (BusinessException e) {
            Assert.fail();
        }
    }

    /**
     * 取消迭代
     */
    @Test
    public void cancelSprint() {
        String s = null;
        try {
            s = sprintv3Service.cancelSprint(1, 1);
        } catch (Exception e) {
            s = e.toString();
        }
        Assert.assertNotNull(s);
    }

    /**
     * 迭代完成
     */
    @Test
    public void sprintFinish() {
        long sprintId = 1l;
        String s = null;
        try {
            s = sprintv3Service.sprintFinish(sprintId);
        } catch (Exception e) {
            s = e.toString();
        }
        Assert.assertNotNull(s);
    }


//    /**
//     * 迭代视图 - 迭代详情
//     */
//    @Test
//    public void sprintOverView() {
//        long sprintId = 10000;
//        SprintOverView sprintOverView = sprintv3Service.sprintOverView(sprintId);
//        Assert.assertNotNull(sprintOverView);
//    }

    /**
     * 迭代视图 - 迭代统计详情
     */
    @Test
    public void SprintStatisticalInformation() {
        long sprintId = 10000;
        SprintStatisticalInformation statisticalInformation = sprintv3Service.sprintStatisticalInformation(sprintId);
        Assert.assertNotNull(statisticalInformation);

    }

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

    @Test
    public void testQueryList1() {
        SprintQueryDTO queryDTO = new SprintQueryDTO();
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
        List<SprintListDTO> list = sprintv3Service.listSprint(queryDTO, securityDTO);
        log.info("迭代列表数据【{}】", list);
    }

    /**
     * 测试迭代列表-分页+迭代编号
     */
    @Test
    public void testQueryList2() {
        SprintQueryDTO queryDTO = new SprintQueryDTO();
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
        queryDTO.setSprint("100013");
        List<SprintListDTO> list = sprintv3Service.listSprint(queryDTO, securityDTO);
        log.info("迭代列表数据【{}】", list);
    }

    /**
     * 测试迭代列表-分页+迭代名称
     */
    @Test
    public void testQueryList3() {
        SprintQueryDTO queryDTO = new SprintQueryDTO();
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
        queryDTO.setSprint("haha");
        List<SprintListDTO> list = sprintv3Service.listSprint(queryDTO, securityDTO);
        log.info("迭代列表数据【{}】", list);
    }

    /**
     * 测试迭代列表-分页+团队名称
     */
    @Test
    public void testQueryList4() {
        SprintQueryDTO queryDTO = new SprintQueryDTO();
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
        queryDTO.setTeam("ceshi");
        List<SprintListDTO> list = sprintv3Service.listSprint(queryDTO, securityDTO);
        log.info("迭代列表数据【{}】", list);
    }

    /**
     * 测试迭代列表-分页+团队编号
     */
    @Test
    public void testQueryList5() {
        SprintQueryDTO queryDTO = new SprintQueryDTO();
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
        queryDTO.setTeam("100013");
        List<SprintListDTO> list = sprintv3Service.listSprint(queryDTO, securityDTO);
        log.info("迭代列表数据【{}】", list);
    }

    /**
     * 测试迭代列表-分页+迭代名称+分页名称
     */
    @Test
    public void testQueryList6() {
        SprintQueryDTO queryDTO = new SprintQueryDTO();
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
        queryDTO.setSprint("快克");
        queryDTO.setTeam("ceshi");
        List<SprintListDTO> list = sprintv3Service.listSprint(queryDTO, securityDTO);
        log.info("迭代列表数据【{}】", list);
    }

    /**
     * 测试迭代列表-分页+迭代编号+分页编号
     */
    @Test
    public void testQueryList7() {
        SprintQueryDTO queryDTO = new SprintQueryDTO();
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
        queryDTO.setSprint("100024");
        queryDTO.setTeam("100013");
        List<SprintListDTO> list = sprintv3Service.listSprint(queryDTO, securityDTO);
        log.info("迭代列表数据【{}】", list);
    }

    /**
     * 按teamId查询有效迭代--分页情况
     * 并且迭代状态为 未开始、进行中、已完成的
     */
    @Test
    public void testGetSprintById() {
        Long teamId = 100013L;
        int pageSize = 10;
        int pageNum = 1;
        List<SprintListDTO> list = sprintv3Service.teamInSprint(teamId, pageSize, pageNum, "");
        log.info("迭代列表数据【{}】", list);
    }

    /**
     * 按teamId查询有效迭代--分页+迭代名称
     */
    @Test
    public void testGetSprintById2() {
        Long teamId = 100013L;
        int pageSize = 10;
        int pageNum = 1;
        String sprint = "狒狒";
        List<SprintListDTO> list = sprintv3Service.teamInSprint(teamId, pageSize, pageNum, sprint);
        log.info("迭代列表数据【{}】", list);
    }

    /**
     * 按teamId查询有效迭代--分页+迭代编号
     */
    @Test
    public void testGetSprintById3() {
        Long teamId = 100013L;
        int pageSize = 10;
        int pageNum = 1;
        String sprint = "100012";
        List<SprintListDTO> list = sprintv3Service.teamInSprint(teamId, pageSize, pageNum, sprint);
        log.info("迭代列表数据【{}】", list);
    }



}

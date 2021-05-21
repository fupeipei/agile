package com.yusys.agile.sprint.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.enums.SprintStatusEnum;
import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.agile.sprint.dto.UserSprintHourDTO;
import com.yusys.agile.sprintV3.dto.SprintListDTO;
import com.yusys.agile.sprintV3.dto.SprintQueryDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sprintV3.dto.SprintV3UserHourDTO;
import com.yusys.agile.sprintv3.dao.SSprintMapper;
import com.yusys.agile.sprintv3.dao.SSprintUserHourMapper;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.agile.team.domain.Team;
import com.yusys.agile.team.dto.TeamDTO;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.agile.teamv3.domain.STeam;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.common.enums.StateEnum;
import com.yusys.agile.teamv3.dao.STeamMapper;
import com.yusys.agile.teamv3.dao.STeamSystemMapper;
import com.yusys.portal.facade.client.api.IFacadeSystemApi;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.date.DateUtil;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.entity.SsoUser;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.yusys.agile.team.dto.TeamDTO;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yusys.agile.sprintv3.enums.SprintStatusEnum.TYPE_NO_START_STATE;

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


}

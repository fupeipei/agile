package com.yusys.agile.sprint.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.google.common.collect.Lists;
import com.yusys.agile.sprintv3.service.Sprintv3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.facade.client.api.IFacadeProjectApi;
import com.yusys.portal.model.facade.entity.SsoProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class SprintServiceTest {
    @Autowired
    private SprintService sprintService;

    private IFacadeProjectApi iFacadeProjectApi;

    private SprintMapper sprintMapper;
    @Autowired
    private Sprintv3Service sprintv3Service;

    @Before
    public void setup() {
        iFacadeProjectApi = spy(IFacadeProjectApi.class);
        sprintMapper = spy(SprintMapper.class);
    }

    // iFacadeProjectApi返回的对象 此对象用来验证是否存在对应的数据，测试create流程，此对象projectId不能等于projectId
    private void setup_checkDuplicateTeamName_equals(long projectId) {
        SsoProject ssoProject = new SsoProject();
        ssoProject.setProjectId(projectId);
        ssoProject.setProjectName("JUNIT" + ssoProject.getProjectId());
        doReturn(ssoProject).when(iFacadeProjectApi).getProjectInfoById(Mockito.anyLong());

        ReflectionTestUtils.setField(sprintService, "iFacadeProjectApi", iFacadeProjectApi);
    }

    private void setup_checkDuplicateTeamName_not_equals(long projectId, long otherProjectId) {
        assert (projectId != otherProjectId);
        SsoProject ssoProject = new SsoProject();
        ssoProject.setProjectId(otherProjectId);
        ssoProject.setProjectName("JUNIT" + ssoProject.getProjectId());
        doReturn(ssoProject).when(iFacadeProjectApi).getProjectInfoById(Mockito.anyLong());

        ReflectionTestUtils.setField(sprintService, "iFacadeProjectApi", iFacadeProjectApi);
    }

    private SprintDTO buildSprintDTO(long projectId, long sprintId, String status) {
        SprintDTO sprintDTO = new SprintDTO();

        sprintDTO.setCreateUid(11L);

        sprintDTO.setCreateTime(new Date());
        sprintDTO.setEndTime(new Date());
        sprintDTO.setFinishTime(new Date());
        sprintDTO.setUpdateTime(new Date());
        sprintDTO.setUpdateUid(11L);

        sprintDTO.setHold("hold");
        sprintDTO.setImprove("improve");
        sprintDTO.setIssueType(IssueTypeEnum.TYPE_TASK.CODE);
        sprintDTO.setPlanDays(1);
        sprintDTO.setProjectId(projectId);
        sprintDTO.setSprintDays("3");
        sprintDTO.setSprintDesc("3 day");
        sprintDTO.setSprintId(sprintId);
        sprintDTO.setSprintName("sprit name");
        sprintDTO.setSprintRate(1.1);
        sprintDTO.setStartTime(new Date());
        sprintDTO.setState(status);
        sprintDTO.setStatus((byte) 1);
        sprintDTO.setTeamId(1L);
        sprintDTO.setTeamName("teamName");
        sprintDTO.setTerminate("terminate");
        sprintDTO.setUserNum(0);
        sprintDTO.setVersionNumber("v1");
        sprintDTO.setWorkHours(3);


        //sprintDTO.setIssueIds();
//        sprintDTO.setMembers();
//        sprintDTO.setStory();
//        sprintDTO.setTask();
//        sprintDTO.setTeamDTOList();
        return sprintDTO;
    }

    private void buildSprit_dayList(SprintDTO sprintDTO) {
        // spritDayList不能为空, 且必须有值
        List<Date> spritDayList = Lists.newArrayList();
        spritDayList.add(new Date());

        sprintDTO.setSprintDayList(spritDayList);
    }

    private void setup_insertSelective_return_0() {
        doReturn(0).when(sprintMapper).insertSelective(Mockito.any());
        ReflectionTestUtils.setField(sprintService, "sprintMapper", sprintMapper);
    }

    private void setup_versionNum_equals_with_projectId_not_equals(long projectId, long otherProjectId) {
        assert (projectId != otherProjectId);
        SsoProject ssoProject = new SsoProject();
        ssoProject.setProjectId(otherProjectId);
        ssoProject.setProjectName("JUNIT" + ssoProject.getProjectId());

        doReturn(ssoProject).when(iFacadeProjectApi).getProjectInfoById(Mockito.anyLong());
        ReflectionTestUtils.setField(sprintService, "iFacadeProjectApi", iFacadeProjectApi);
    }

    /***
     * springMapper.insertSelective 返回0 插入失败,返回异常
     强制insertSelective返回0,未调用实际方法
     *
     * @param
     * @return {}
     * @throws
     *    hucg
     * @date 2020-05-14 09:47
     */
    @Test(expected = BusinessException.class)
    public void test_BusinessException_insertSelective_failed_CreateSprint() {
        long projectId = 1L;
        long otherProjectId = 1000000L;
        long sprintId = 5001L;
        String status = "U";

        setup_versionNum_equals_with_projectId_not_equals(projectId, otherProjectId);
        setup_insertSelective_return_0();

        SprintDTO sprintDTO = buildSprintDTO(projectId, sprintId, status);
        buildSprit_dayList(sprintDTO);
        sprintService.createSprint(sprintDTO, projectId);
    }

    /***
     * versionNum存在 返回异常
     *
     * @param
     * @return {}
     * @throws
     *    hucg
     * @date 2020-05-14 09:48
     */
    @Test(expected = BusinessException.class)
    public void test_BusinessException_equals_versionNum_CreateSprint() {
        long projectId = 1L;
        long otherProjectId = 1000000L;
        long sprintId = 5001L;
        String status = "U";
        setup_versionNum_equals_with_projectId_not_equals(projectId, otherProjectId);

        SprintDTO sprintDTO = buildSprintDTO(projectId, sprintId, status);
        buildSprit_dayList(sprintDTO);
        sprintService.createSprint(sprintDTO, projectId);
    }

    /***
     * projectId在sso服务中存在返回异常
     *
     * @param
     * @return {}
     * @throws
     *    hucg
     * @date 2020-05-14 09:48
     */
    @Test(expected = BusinessException.class)
    public void test_BusinessException_equals_projectId_CreateSprint() {
        long projectId = 1L;
        long otherProjectId = 1000000L;
        long sprintId = 5001L;
        String status = "U";
        setup_checkDuplicateTeamName_not_equals(projectId, otherProjectId);

        SprintDTO sprintDTO = buildSprintDTO(projectId, sprintId, status);
        buildSprit_dayList(sprintDTO);
        sprintService.createSprint(sprintDTO, projectId);
    }


    //    @Test()
    public void testCreateSprint() {
        long projectId = 1L;
        long otherProjectId = 1000000L;
        long sprintId = 5001L;
        String status = "U";
        String newStatus = "S";

        setup_checkDuplicateTeamName_not_equals(projectId, otherProjectId);
        List<SprintDTO> sprintDTOList = sprintService.queryUnFinishedByProjectId(String.valueOf(sprintId), projectId, 0, 10);

        SprintDTO sprintDTO;
        if (sprintDTOList.size() == 1) {
            sprintDTO = sprintDTOList.get(0);
            buildSprit_dayList(sprintDTO);
        } else {
            sprintDTO = buildSprintDTO(projectId, sprintId, status);
            sprintService.createSprint(sprintDTO, projectId);
        }

        sprintDTO.setState(newStatus);
        sprintService.editSprint(sprintDTO, projectId);
        List<SprintDTO> updateSprintDTOList = sprintService.queryUnFinishedByProjectId(String.valueOf(sprintId), 1L, 0, 10);
        SprintDTO updateSprintDTO = updateSprintDTOList.get(0);
        assert (updateSprintDTOList.size() == 1);
        assert (updateSprintDTO.getState().equals(newStatus));
        assert (!sprintDTO.getState().equals(updateSprintDTO.getState()));
    }

    //    @Test()
    public void testUpdateSprint() {
//        SprintService sprintService = new SprintServiceImpl();
        long projectId = 1L;
        long sprintId = 5001L;
        String status = "U";
        String newStatus = "S";
        List<SprintDTO> sprintDTOList = sprintService.queryUnFinishedByProjectId(String.valueOf(sprintId), projectId, 0, 10);

        // spritDayList不能为空, 且必须有值
        List<Date> spritDayList = Lists.newArrayList();
        spritDayList.add(new Date());

        SsoProject ssoProject = new SsoProject();
        ssoProject.setProjectId(projectId);

        SprintDTO sprintDTO;
        if (sprintDTOList.size() == 1) {
            sprintDTO = sprintDTOList.get(0);
            sprintDTO.setSprintDayList(spritDayList);
        } else {
            sprintDTO = new SprintDTO();

            sprintDTO.setCreateUid(11L);

            sprintDTO.setCreateTime(new Date());
            sprintDTO.setEndTime(new Date());
            sprintDTO.setFinishTime(new Date());
            sprintDTO.setUpdateTime(new Date());
            sprintDTO.setUpdateUid(11L);

            sprintDTO.setHold("hold");
            sprintDTO.setImprove("improve");
            sprintDTO.setIssueType(IssueTypeEnum.TYPE_TASK.CODE);
            sprintDTO.setPlanDays(1);
            sprintDTO.setProjectId(projectId);
            sprintDTO.setSprintDays("3");
            sprintDTO.setSprintDesc("3 day");
            sprintDTO.setSprintId(sprintId);
            sprintDTO.setSprintName("sprit name");
            sprintDTO.setSprintRate(1.1);
            sprintDTO.setStartTime(new Date());
            sprintDTO.setState(status);
            sprintDTO.setStatus((byte) 1);
            sprintDTO.setTeamId(1L);
            sprintDTO.setTeamName("teamName");
            sprintDTO.setTerminate("terminate");
            sprintDTO.setUserNum(0);
            sprintDTO.setVersionNumber("v1");
            sprintDTO.setWorkHours(3);

            sprintDTO.setSprintDayList(spritDayList);

            //sprintDTO.setIssueIds();
//        sprintDTO.setMembers();
//        sprintDTO.setStory();
//        sprintDTO.setTask();
//        sprintDTO.setTeamDTOList();
            doReturn(ssoProject).when(iFacadeProjectApi).getProjectInfoById(Mockito.anyLong());
//            when((Publisher<SsoProject>)iFacadeProjectApi.getProjectInfoById(Mockito.anyLong())).thenReturn(ssoProject);
            sprintService.createSprint(sprintDTO, projectId);

            sprintDTO.setState(newStatus);
            sprintService.editSprint(sprintDTO, projectId);
        }

        List<SprintDTO> updateSprintDTOList = sprintService.queryUnFinishedByProjectId(String.valueOf(sprintId), 1L, 0, 10);
        SprintDTO updateSprintDTO = updateSprintDTOList.get(0);
        assert (updateSprintDTOList.size() == 1);
        assert (updateSprintDTO.getState().equals(newStatus));
        assert (!sprintDTO.getState().equals(updateSprintDTO.getState()));
    }


    @Test
    public void viewSrint(){
        long sprintId=130211;
        SprintDTO sprintDTO = sprintv3Service.viewEdit(sprintId);
        System.out.println(sprintDTO);
    }

 //{sprintId=130211, projectId=null, sprintName='sprit name edit', sprintDesc='描述这是一次迭代', status=3, state='U', startTime=Fri Apr 30 17:25:07 CST 2021, endTime=Fri Apr 30 23:59:59 CST 2021, finishTime=Fri Apr 30 17:25:07 CST 2021, teamId=1, teamName='团队1', workHours=3, versionNumber='v1', createUid=9999, createTime=Fri Apr 30 17:25:07 CST 2021, updateUid=11, updateTime=Fri Apr 30 17:25:07 CST 2021, hold='null', improve='null', terminate='null', sprintDays='1619774706780|1619774706780|1619774706780|1619774706780', sprintDayList=[Fri Apr 30 17:25:06 CST 2021, Fri Apr 30 17:25:06 CST 2021, Fri Apr 30 17:25:06 CST 2021, Fri Apr 30 17:25:06 CST 2021], members=null, teamDTOList=[TeamDTO(teamId=1, teamName=团队1, teamDesc=null, state=U, projectId=null, createTime=Tue May 11 19:51:21 CST 2021, createUid=324234, users=[com.yusys.agile.sprint.dto.UserSprintHourDTO@c8d0ea3], teamSystems=[SsoSystemRestDTO(systemId=807205004631072768, systemCode=DevOps, systemName=DevOps), SsoSystemRestDTO(systemId=816048836585721856, systemCode=maewyfssftt4, systemName=系qdfftts)], tenantCode=1)], sprintRate=null, issueIds=null, issueType=null, planDays=null, story=null, task=null, userNum=null}

}

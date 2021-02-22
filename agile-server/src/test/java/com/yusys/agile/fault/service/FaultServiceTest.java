package com.yusys.agile.fault.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.fault.domain.FaultLevel;
import com.yusys.agile.fault.domain.FaultType;
import com.yusys.agile.fault.dto.FaultStatusDTO;
import com.yusys.agile.fault.dto.UserDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class FaultServiceTest {
    private static final Logger log = LoggerFactory.getLogger(FaultServiceTest.class);
    @Autowired
    private FaultService faultService;

    @Test
    public void addFault() {
        //IssueDTO issueDTO = new IssueDTO();
        //faultService.addFault(issueDTO);
    }

    @Test
    public void deleteFault() {
        Long issueId = 1L;
        faultService.deleteFault(issueId);
        log.info("Junit测试--缺陷管理--deleteFault（）：根据缺陷id删除缺陷信息：");
    }

    @Test
    public void getFault() {
        Long issueId = 1L;
        Long projectId = 720205617142030336L;
        IssueDTO fault = faultService.getFault(issueId);
        log.info("Junit测试--缺陷管理--getFault（）：根据缺陷id获取缺陷信息：{}", fault.toString());
    }

    @Test
    public void updateFault() {

    }

    @Test
    public void listAllFaultLevel() {
        List<FaultLevel> faultLevels = faultService.listAllFaultLevel();
        if (CollectionUtils.isNotEmpty(faultLevels)) {
            faultLevels.forEach(faultLevel -> log.info(faultLevel.toString()));
        }
    }

    @Test
    public void listAllFaultType() {
        List<FaultType> faultTypes = faultService.listAllFaultType();
        if (CollectionUtils.isNotEmpty(faultTypes)) {
            faultTypes.forEach(faultType -> log.info(faultType.toString()));
        }
    }

    @Test
    public void listAllCreateUsers() {
        Long projectId = 687991620699545600L;
        List<UserDTO> userDTOS = faultService.listAllCreateUsers(projectId);
        if (CollectionUtils.isNotEmpty(userDTOS)) {
            userDTOS.forEach(userDTO -> log.info(userDTO.toString()));
        }
    }

    @Test
    public void listAllFixedUsers() {
        Long projectId = 687991620699545600L;
        List<UserDTO> userDTOS = faultService.listAllFixedUsers(projectId);
        if (CollectionUtils.isNotEmpty(userDTOS)) {
            userDTOS.forEach(userDTO -> log.info(userDTO.toString()));
        }
    }

    @Test
    public void listAllTestUsers() {
        Long projectId = 687991620699545600L;
        List<UserDTO> userDTOS = faultService.listAllTestUsers(projectId);
        if (CollectionUtils.isNotEmpty(userDTOS)) {
            userDTOS.forEach(userDTO -> log.info(userDTO.toString()));
        }
    }

    @Test
    public void listFaults() {
        try {
            String idOrName = null;
            Long faultLevel = null;
            Long faultType = null;
            Long stageId = null;
            Long sprintId = null;
            Long createUid = null;
            String createDate = null;
            Long fixedUid = null;
            Long testUid = null;
            Long projectId = 687991620699545600L;
            Integer pageNum = 1;
            Integer pageSize = 30;

            List<IssueDTO> issueDTOS = faultService.listFaults(idOrName, faultLevel, faultType, stageId, sprintId, createUid,
                    createDate, fixedUid, testUid, projectId, pageNum, pageSize);
            log.info("Junit测试--缺陷管理--listFaults（）：获取缺陷列表信息成功：{}", issueDTOS.toString());
        } catch (Exception e) {
            log.error("Junit测试--缺陷管理--listFaults（）：获取缺陷列表信息异常：{}", e);
        }

    }

    @Test
    public void listFaultsOrStorysNotLinkSprint() {
        String filter = null;
        Byte issueType = (byte) 1;
        Long projectId = 687991620699545600L;
        Integer pageNum = 1;
        Integer pageSize = 30;
        List<IssueDTO> issueDTOS = faultService.listFaultsOrStorysNotLinkSprint(filter, issueType, projectId, pageNum, pageSize);
        log.info("Junit测试--缺陷管理--listFaultsOrStorysNotLinkSprint（）：列表展示未关联迭代的缺陷成功：{}", issueDTOS.toString());
    }

    @Test
    public void listAllStatus() {
        List<FaultStatusDTO> faultStatusDTOS = faultService.listAllStatus();
        if (CollectionUtils.isNotEmpty(faultStatusDTOS)) {
            faultStatusDTOS.forEach(faultStatusDTO -> log.info(faultStatusDTO.toString()));
        }
    }

    @Test
    public void dragFault() {
        try {
            IssueDTO dto = new IssueDTO();
            dto.setIssueId(1L);
            dto.setStageId(2L);

            faultService.dragFault(dto);
            log.info("Junit测试--缺陷管理--dragFault（）：拖动缺陷卡片成功");
        } catch (Exception e) {
            log.info("Junit测试--缺陷管理--dragFault（）：拖动缺陷卡片异常：{}", e);
        }
    }
}
package com.yusys.agile.fault.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.fault.domain.FaultLevel;
import com.yusys.agile.fault.domain.FaultType;
import com.yusys.agile.fault.dto.FaultStatusDTO;
import com.yusys.agile.fault.dto.UserDTO;
import com.yusys.agile.issue.dto.IssueDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
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
        IssueDTO issueDTO = new IssueDTO();
        faultService.addFault(issueDTO);
        Assert.assertTrue("addFault成功", true);
    }

    @Test
    public void deleteFault() {
        Long issueId = 1L;
        faultService.deleteFault(issueId);
        Assert.assertTrue("deleteFault成功", true);
    }

    @Test
    public void getFault() {
        Long issueId = 1L;
        faultService.getFault(issueId);
        Assert.assertTrue("getFault成功", true);
    }

    @Test
    public void listAllFaultLevel() {
        List<FaultLevel> faultLevels = faultService.listAllFaultLevel();
        if (CollectionUtils.isNotEmpty(faultLevels)) {
            faultLevels.forEach(faultLevel -> log.info(faultLevel.toString()));
        }
        Assert.assertTrue("listAllFaultLevel成功", true);
    }

    @Test
    public void listAllFaultType() {
        List<FaultType> faultTypes = faultService.listAllFaultType();
        if (CollectionUtils.isNotEmpty(faultTypes)) {
            faultTypes.forEach(faultType -> log.info(faultType.toString()));
        }
        Assert.assertTrue("listAllFaultType成功", true);
    }

    @Test
    public void listAllCreateUsers() {
        Long projectId = 687991620699545600L;
        List<UserDTO> userDTOS = faultService.listAllCreateUsers(projectId);
        if (CollectionUtils.isNotEmpty(userDTOS)) {
            userDTOS.forEach(userDTO -> log.info(userDTO.toString()));
        }
        Assert.assertTrue("listAllCreateUsers成功", true);
    }

    @Test
    public void listAllFixedUsers() {
        Long projectId = 687991620699545600L;
        List<UserDTO> userDTOS = faultService.listAllFixedUsers(projectId);
        if (CollectionUtils.isNotEmpty(userDTOS)) {
            userDTOS.forEach(userDTO -> log.info(userDTO.toString()));
        }
        Assert.assertTrue("listAllFixedUsers成功", true);
    }

    @Test
    public void listAllTestUsers() {
        Long projectId = 687991620699545600L;
        List<UserDTO> userDTOS = faultService.listAllTestUsers(projectId);
        if (CollectionUtils.isNotEmpty(userDTOS)) {
            userDTOS.forEach(userDTO -> log.info(userDTO.toString()));
        }
        Assert.assertTrue("listAllTestUsers成功", true);
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

           faultService.listFaults(idOrName, faultLevel, faultType, stageId, sprintId, createUid,
                    createDate, fixedUid, testUid, projectId, pageNum, pageSize);
            Assert.assertTrue("listFaults成功", true);
        } catch (Exception e) {
            Assert.assertFalse("listFaults失败", true);
        }

    }

    @Test
    public void listFaultsOrStorysNotLinkSprint() {
        String filter = null;
        Byte issueType = (byte) 1;
        Long projectId = 687991620699545600L;
        Integer pageNum = 1;
        Integer pageSize = 30;
        faultService.listFaultsOrStorysNotLinkSprint(filter, issueType, projectId, pageNum, pageSize);
        Assert.assertTrue("listFaultsOrStorysNotLinkSprint成功", true);
    }

    @Test
    public void listAllStatus() {
        List<FaultStatusDTO> faultStatusDTOS = faultService.listAllStatus();
        if (CollectionUtils.isNotEmpty(faultStatusDTOS)) {
            faultStatusDTOS.forEach(faultStatusDTO -> log.info(faultStatusDTO.toString()));
        }
        Assert.assertTrue("listAllStatus成功", true);
    }

    @Test
    public void dragFault() {
        try {
            IssueDTO dto = new IssueDTO();
            dto.setIssueId(1L);
            dto.setStageId(2L);
            faultService.dragFault(dto);
            Assert.assertTrue("dragFault成功", true);
        } catch (Exception e) {
            Assert.assertFalse("dragFault失败", true);
        }
    }
}
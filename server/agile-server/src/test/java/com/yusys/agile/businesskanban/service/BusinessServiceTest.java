package com.yusys.agile.businesskanban.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.businesskanban.domain.BusinessWithBLOBs;
import com.yusys.agile.businesskanban.dto.BusinessDTO;
import com.yusys.agile.businesskanban.dto.BusinessHistoryRecordDTO;
import com.yusys.agile.businesskanban.dto.BusinessResultDTO;
import com.yusys.agile.businesskanban.enums.BusinessLevel;
import com.yusys.agile.businesskanban.enums.BusinessState;
import com.yusys.agile.businesskanban.enums.BusinessType;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class BusinessServiceTest {

    private static final Logger log = LoggerFactory.getLogger(BusinessKanbanServiceTest.class);
    @Autowired
    private BusinessService businessService;

    @Test
    @Transactional
    public void createBusinessTest() {
        try {
            BusinessDTO kanbanDTO = new BusinessDTO();
            kanbanDTO.setKanbanId(3L);
            kanbanDTO.setIsVisible((byte) 0);
            kanbanDTO.setBusinessType(BusinessType.STUDY.getNodeCode());
            kanbanDTO.setBusinessState(BusinessState.PENDING.getNodeCode().byteValue());
            kanbanDTO.setBusinessLevel(BusinessLevel.HIGH.getKey().byteValue());
            kanbanDTO.setBusinessName("事务看板卡片");
            kanbanDTO.setBusinessDesc("事务看板卡片");
            kanbanDTO.setProjectId(681553543076634624L);
            kanbanDTO.setCreateTime(new Date());
            kanbanDTO.setPlanStartTime(new Date());
            kanbanDTO.setPlanEndTimeEnd(new Date());
            kanbanDTO.setStartTime(new Date());
            kanbanDTO.setEndTime(new Date());
            kanbanDTO.setCreateUid(9999L);
            kanbanDTO.setStatus((byte) 0);
            kanbanDTO.setDescText("事务看板卡片0000");
            BusinessDTO business = businessService.createBusiness(kanbanDTO);
            log.info("Junit测试--创建事务卡片成功：{}", business);
        } catch (Exception e) {
            log.info("Junit测试--创建事务卡片失败：{}", e);
        }
    }

    @Test
    @Transactional
    public void deleteBusinessTest() {
        try {
            Long businessId = 1L;
            int num = businessService.deleteBusiness(businessId);
            log.info("Junit测试--删除事务卡片成功：{}", num);
        } catch (Exception e) {
            log.info("Junit测试--删除事务卡片失败：{}", e);
        }
    }

    @Test
    @Transactional
    public void updateBusinessTest() {
        try {
            String str = "{\n" +
                    "\"actualWorkload\": 10,\n" +
                    "\"businessDesc\": \"miaoshu\",\n" +
                    "\"businessId\": 16,\n" +
                    "\"businessImportance\": 0,\n" +
                    "\"businessLevel\": 0,\n" +
                    "\"businessName\": \"Test\",\n" +
                    "\"businessOwner\": \"712700423193878528\",\n" +
                    "\"businessOwnerName\": \"殷利平\",\n" +
                    "\"businessState\": 1,\n" +
                    "\"businessType\": 1000,\n" +
                    "\"createTime\": 1594344970000,\n" +
                    "\"createUid\": \"726827322836901888\",\n" +
                    "\"descText\": \"\",\n" +
                    "\"endTime\": \"\",\n" +
                    "\"isVisible\": 1,\n" +
                    "\"kanbanId\": 15,\n" +
                    "\"planEndTime\": 1593792000000,\n" +
                    "\"planStartTime\": 1593532800000,\n" +
                    "\"planTime\": [1593532800000, 1593792000000],\n" +
                    "\"0\": 1593532800000,\n" +
                    "\"1\": 1593792000000,\n" +
                    "\"planWorkload\": 8,\n" +
                    "\"projectId\": \"718135708161531904\",\n" +
                    "\"startTime\": \"\",\n" +
                    "\"state\": \"U\",\n" +
                    "\"status\": 0,\n" +
                    "\"tenantCode\": \"1\",\n" +
                    "\"time\": [],\n" +
                    "\"updateTime\": 1594606132000,\n" +
                    "\"updateUid\": \"726827322836901888\"\n" +
                    "}";
            BusinessDTO kanbanDTO = JSONObject.parseObject(str, BusinessDTO.class);
            BusinessWithBLOBs businessWithBLOBs = businessService.updateBusiness(kanbanDTO);
            log.info("Junit测试--编辑事务卡片成功：{}", businessWithBLOBs.toString());
        } catch (Exception e) {
            log.info("Junit测试--编辑事务卡片失败：{}", e);
        }
    }

    @Test
    public void getByBusinessIdTest() {
        try {
            Long businessId = 1L;
            List<BusinessHistoryRecordDTO> businessList = businessService.getByBusinessId(businessId, 1, 30);
            log.info("Junit测试--根据事务卡片ID查询卡片历史数据成功：{}", businessList.toString());
        } catch (Exception e) {
            log.info("Junit测试--根据事务卡片ID查询卡片历史数据失败：{}", e);
        }
    }

    @Test
    public void getBusinessInfoTest() {
        try {
            Long businessId = 1L;
            BusinessDTO kanbanDTO = new BusinessDTO();
            kanbanDTO.setBusinessId(businessId);
            kanbanDTO.setKanbanId(3L);
            kanbanDTO.setIsVisible((byte) 0);
            kanbanDTO.setBusinessType(BusinessType.STUDY.getNodeCode());
            kanbanDTO.setBusinessState(BusinessState.PENDING.getNodeCode().byteValue());
            kanbanDTO.setBusinessLevel(BusinessLevel.HIGH.getKey().byteValue());
            kanbanDTO.setBusinessName("事务看板卡片");
            kanbanDTO.setBusinessDesc("事务看板卡片");
            kanbanDTO.setProjectId(681553543076634624L);
            kanbanDTO.setCreateTime(new Date());
            kanbanDTO.setPlanStartTime(new Date());
            kanbanDTO.setPlanEndTimeEnd(new Date());
            kanbanDTO.setStartTime(new Date());
            kanbanDTO.setEndTime(new Date());
            kanbanDTO.setCreateUid(9999L);
            kanbanDTO.setStatus((byte) 0);
            kanbanDTO.setDescText("事务看板卡片0000");
            List<BusinessResultDTO> businessInfos = businessService.getBusinessInfo(kanbanDTO);
            log.info("Junit测试--根据事务卡片条件查询卡片列表数据成功：{}", businessInfos.toString());
        } catch (Exception e) {
            log.info("Junit测试--根据事务卡片条件查询卡片列表数据失败：{}", e);
        }
    }

    @Test
    public void getBusinessInfListTest() {
        try {
            Long businessId = 1L;
            BusinessDTO kanbanDTO = new BusinessDTO();
            kanbanDTO.setBusinessId(businessId);
            kanbanDTO.setKanbanId(3L);
            kanbanDTO.setIsVisible((byte) 0);
            kanbanDTO.setBusinessType(BusinessType.STUDY.getNodeCode());
            kanbanDTO.setBusinessState(BusinessState.PENDING.getNodeCode().byteValue());
            kanbanDTO.setBusinessLevel(BusinessLevel.HIGH.getKey().byteValue());
            kanbanDTO.setBusinessName("事务看板卡片");
            kanbanDTO.setBusinessDesc("事务看板卡片");
            kanbanDTO.setProjectId(681553543076634624L);
            kanbanDTO.setCreateTime(new Date());
            kanbanDTO.setPlanStartTime(new Date());
            kanbanDTO.setPlanEndTimeEnd(new Date());
            kanbanDTO.setStartTime(new Date());
            kanbanDTO.setEndTime(new Date());
            kanbanDTO.setCreateUid(9999L);
            kanbanDTO.setStatus((byte) 0);
            kanbanDTO.setDescText("事务看板卡片0000");
            List<BusinessDTO> businessInfList = businessService.getBusinessInfList(kanbanDTO);
            log.info("Junit测试--根据事务卡片条件查询卡片列表形式数据成功：{}", businessInfList.toString());
        } catch (Exception e) {
            log.info("Junit测试--根据事务卡片条件查询卡片列表形式数据失败：{}", e);
        }
    }
}
package com.yusys.agile.businesskanban.service;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.businesskanban.dto.BusinessKanbanDTO;
import com.yusys.agile.utils.page.PageQuery;
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

@RunWith( SpringRunner.class )
@SpringBootTest(classes = {AgileApplication.class})
public class BusinessKanbanServiceTest {

    private static final Logger log = LoggerFactory.getLogger(BusinessKanbanServiceTest.class);
    @Autowired
    private BusinessKanbanService kanbanService;

    @Test
    @Transactional
    public void createBusinessKanban() {
        try{
            BusinessKanbanDTO kanbanDTO = new BusinessKanbanDTO();
            kanbanDTO.setKanbanName("事务看板Test");
            kanbanDTO.setKanbanDesc("事务看板Test");
            kanbanDTO.setProjectId(681553543076634624L);
            kanbanDTO.setCreateTime(new Date());
            kanbanDTO.setCreateUid(9999L);
            kanbanDTO.setStatus(0L);
            int businessKanban = kanbanService.createBusinessKanban(kanbanDTO);
            log.info("Junit测试--创建事务看板成功：{}",businessKanban);
        }catch (Exception e){
            log.info("Junit测试--创建事务看板失败：{}",e);
        }
    }

    @Test
    @Transactional
    public void deleteBusinessKanban() {
        try{
            Long kanbanId = 3L;
            int businessKanban = kanbanService.deleteBusinessKanban(kanbanId);
            log.info("Junit测试--事务看板删除成功：{}",businessKanban);
        }catch (Exception e){
            log.info("Junit测试--事务看板删除失败：{}",e);
        }
    }

    @Test
    @Transactional
    public void updateBusinessKanban() {
        try{
            BusinessKanbanDTO kanbanDTO = new BusinessKanbanDTO();
            kanbanDTO.setKanbanId(3L);
            kanbanDTO.setKanbanName("JunitTest");
            kanbanDTO.setKanbanDesc("编辑事务看板TEST");
            kanbanDTO.setProjectId(681553543076634624L);
            int businessKanban = kanbanService.updateBusinessKanban(kanbanDTO);
            log.info("Junit测试--编辑事务看板成功：{}",businessKanban);
        }catch (Exception e){
            log.info("Junit测试--编辑事务看板失败：{}",e);
        }
    }

    @Test
    public void getBusinessKanbanList() {
        try{
            PageQuery<BusinessKanbanDTO> query = new PageQuery<>();
            BusinessKanbanDTO businessKanbanDTO = new BusinessKanbanDTO();
            businessKanbanDTO.setProjectId(681553543076634624L);
            businessKanbanDTO.setKanbanName(null);
            query.setQuery(businessKanbanDTO);
            query.setPage(1);
            query.setPageSize(30);

            List<BusinessKanbanDTO> businessKanbanList = kanbanService.getBusinessKanbanList(query);
            int countNum = kanbanService.countBusinessKanbanList(query);
            log.info("Junit测试--查询事务看板列表成功：\n{},\n 总条数：{}",businessKanbanList,countNum);
        }catch (Exception e){
            log.info("Junit测试--查询事务看板列表失败：{}",e);
        }
    }

}
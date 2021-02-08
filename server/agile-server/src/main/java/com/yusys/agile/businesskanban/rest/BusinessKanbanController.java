package com.yusys.agile.businesskanban.rest;

import com.yusys.agile.utils.page.PageQuery;
import com.yusys.agile.utils.result.ResultObjectPage;
import com.yusys.portal.model.common.dto.ControllerResponse;

import com.yusys.agile.businesskanban.dto.BusinessKanbanDTO;
import com.yusys.agile.businesskanban.service.BusinessKanbanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Date: 2021/2/1
 * @Description: 事务看板Controller
 */
@RestController
public class BusinessKanbanController {

   private static final Logger loggr = LoggerFactory.getLogger(BusinessKanbanController.class);
   @Resource
   private BusinessKanbanService businessKanbanService;

   @PostMapping("/businessKanban/create")
   public ControllerResponse createBusinessKanban(@RequestBody BusinessKanbanDTO kanbanDTO){
      int result = businessKanbanService.createBusinessKanban(kanbanDTO);
      if(result != 1){
         return ControllerResponse.fail("创建事务看板失败");
      }
      return ControllerResponse.success("创建事务看板成功");
   };

   @DeleteMapping("/businessKanban/delete/{kanbanId}")
   public ControllerResponse deleteBusinessKanban(@PathVariable("kanbanId") Long kanbanId){
      int result = businessKanbanService.deleteBusinessKanban(kanbanId);
      if(result != 1){
         return ControllerResponse.fail("删除事务看板失败");
      }
      return ControllerResponse.success("删除事务看板成功");
   }

   @PostMapping("/businessKanban/update")
   public ControllerResponse updateBusinessKanban(@RequestBody BusinessKanbanDTO kanbanDTO){
      int result = businessKanbanService.updateBusinessKanban(kanbanDTO);
      if(result != 1){
         return ControllerResponse.fail("更新事务看板失败");
      }
      return ControllerResponse.success("更新事务看板成功");

   };

   @GetMapping("/businessKanban/getBusinessKanbanList")
   public ControllerResponse selectBusinessKanbanList(@RequestHeader(name = "projectId") Long projectId,
                                                      String kanbanName,
                                                      Integer page,
                                                      Integer pageSize){
      PageQuery<BusinessKanbanDTO> query = new PageQuery<>();
      BusinessKanbanDTO businessKanbanDTO = new BusinessKanbanDTO();
      businessKanbanDTO.setProjectId(projectId);
      businessKanbanDTO.setKanbanName(kanbanName);
      query.setQuery(businessKanbanDTO);
      query.setPage(page);
      query.setPageSize(pageSize);
      List<BusinessKanbanDTO> businessKanbanList;
      try {
         businessKanbanList = businessKanbanService.getBusinessKanbanList(query);
      } catch (Exception e) {
        loggr.error("BusinessKanbanController method selectBusinessKanbanList error:{}",e.getMessage());
        return ControllerResponse.fail("获取事务看板异常");
      }
      int total = businessKanbanService.countBusinessKanbanList(query);
      return ControllerResponse.success(new ResultObjectPage(total,businessKanbanList));
   }

   @GetMapping("/businessKanban/getBusinessKanbanListNoPage")
   public ControllerResponse selectBusinessKanbanListNoPage(@RequestHeader(name = "projectId") Long projectId,
                                                      String kanbanName){
      BusinessKanbanDTO businessKanbanDTO = new BusinessKanbanDTO();
      businessKanbanDTO.setProjectId(projectId);
      businessKanbanDTO.setKanbanName(kanbanName);
      List<BusinessKanbanDTO> businessKanbanList;
      try {
         businessKanbanList = businessKanbanService.selectBusinessKanbanListNoPage(businessKanbanDTO);
      } catch (Exception e) {
         loggr.error("BusinessKanbanController method selectBusinessKanbanListNoPage error:{}",e.getMessage());
         return ControllerResponse.fail("获取事务看板异常");
      }
      return ControllerResponse.success(businessKanbanList);
   }
}

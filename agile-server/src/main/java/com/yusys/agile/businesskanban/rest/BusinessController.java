package com.yusys.agile.businesskanban.rest;

import com.yusys.agile.businesskanban.domain.BusinessWithBLOBs;
import com.yusys.agile.businesskanban.dto.BusinessDTO;
import com.yusys.agile.businesskanban.dto.BusinessHistoryRecordDTO;
import com.yusys.agile.businesskanban.dto.BusinessResultDTO;
import com.yusys.agile.businesskanban.service.BusinessService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date: 2021/2/1
 * @Description: 事务Controller
 */
@RestController
public class BusinessController {

    private static final Logger loggr = LoggerFactory.getLogger(BusinessController.class);
    @Resource
    private BusinessService businessService;

    @PostMapping("/business/create")
    public ControllerResponse createBusiness(@RequestBody BusinessDTO businessDTO) {
        try {
            BusinessDTO business = businessService.createBusiness(businessDTO);
            if (null == business.getBusinessId()) {
                return ControllerResponse.fail("创建事务失败");
            }
            return ControllerResponse.success(business);
        } catch (Exception e) {
            loggr.error("BusinessController method createBusiness error:{}", e.getMessage());
            return ControllerResponse.fail("创建事务异常");
        }
    }

    @DeleteMapping("/business/delete/{businessId}")
    public ControllerResponse deleteBusiness(@PathVariable("businessId") Long businessId) {
        try {
            int result = businessService.deleteBusiness(businessId);
            if (result != 1) {
                return ControllerResponse.fail("删除事务失败");
            }
            return ControllerResponse.success("删除事务成功");
        } catch (Exception e) {
            loggr.error("BusinessController method deleteBusiness error:{}", e.getMessage());
            return ControllerResponse.fail("删除事务异常");
        }
    }

    @PostMapping("/business/update")
    public ControllerResponse updateBusiness(@RequestBody BusinessDTO businessDTO) {
        try {
            BusinessWithBLOBs businessWithBLOBs = businessService.updateBusiness(businessDTO);
            return ControllerResponse.success(businessWithBLOBs);
        } catch (Exception e) {
            loggr.error("BusinessController method updateBusiness error:{}", e.getMessage());
            return ControllerResponse.fail("更新事务异常");
        }
    }

    @GetMapping("/business/history")
    public ControllerResponse getByHistoryBusinessId(@RequestParam("businessId") Long businessId,
                                                     @RequestParam("pageNum") Integer pageNum,
                                                     @RequestParam("pageSize") Integer pageSize) {
        List<BusinessHistoryRecordDTO> historys;
        try {
            historys = businessService.getByBusinessId(businessId, pageNum, pageSize);
        } catch (Exception e) {
            loggr.error("BusinessController method getByHistoryBusinessId error:{}", e.getMessage());
            return ControllerResponse.fail("获取事务历史记录异常");
        }
        return ControllerResponse.success(new PageInfo<>(historys));
    }

    @PostMapping("/business/getBusinessInfo")
    public ControllerResponse getBusinessInfo(@RequestBody BusinessDTO businessDTO) {
        List<BusinessResultDTO> result;
        try {
            result = businessService.getBusinessInfo(businessDTO);
        } catch (Exception e) {
            loggr.error("BusinessController method getBusinessInfo error:{}", e.getMessage());
            return ControllerResponse.fail("获取事务视图异常");
        }
        return ControllerResponse.success(result);
    }

    @PostMapping("/business/getBusinessInfList")
    public ControllerResponse getBusinessInfList(@RequestBody BusinessDTO businessDTO) {
        List<BusinessDTO> result;
        try {
            result = businessService.getBusinessInfList(businessDTO);
        } catch (Exception e) {
            loggr.error("BusinessController method getBusinessInfList error:{}", e.getMessage());
            return ControllerResponse.fail("获取事务列表异常");
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    @GetMapping("/business/getFixedIterms")
    public ControllerResponse getFixedIterms() {
        JSONObject fixedIterms;
        try {
            fixedIterms = businessService.getFixedIterms();
        } catch (Exception e) {
            loggr.error("获取枚举类型异常", e.getMessage());
            return ControllerResponse.fail("获取枚举类型异常");
        }
        return ControllerResponse.success(fixedIterms);
    }

    /**
     * @Date 2021/2/1
     * @Description 根据登入用户获取代办事项
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/business/getBusinessCommissionOwner")
    public ControllerResponse getBusinessCommissionOwner(Integer pageNum, Integer pageSize) {
        return ControllerResponse.success(businessService.getBusinessCommissionOwner(pageNum, pageSize));
    }

}

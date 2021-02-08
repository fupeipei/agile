package com.yusys.agile.commission.rest;

import com.yusys.agile.commission.constants.CommissionConstant;
import com.yusys.agile.commission.dto.CommissionDTO;
import com.yusys.agile.commission.service.CommissionService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @description 代办控制类
 * @date 2021/2/1
 */
@RestController
@RequestMapping("/commission")
public class CommissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommissionController.class);

    @Resource
    private CommissionService commissionService;

    /**
     * @description 根据代办标题分页查询代办列表
     * @date 2021/2/1
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/queryCommissionByUserId")
    public ControllerResponse queryCommissionByUserId(@RequestParam(name="title",required = false)String title,
                                                      @RequestParam(name="pageNum")Integer pageNum,
                                                      @RequestParam(name="pageSize")Integer pageSize) {
        Long userId = UserThreadLocalUtil.getUserInfo().getUserId();
        try {
            return ControllerResponse.success(commissionService.getCommissionList(userId, title, pageNum, pageSize));
        } catch (Exception e) {
            LOGGER.error("queryCommissionByUserId method param userId:{} occur exception:{}", userId, e.getMessage());
            return ControllerResponse.fail("查询代办列表异常");
        }
    }

    /**
     * @description 根据代办id查询代办信息
     * @date 2021/2/1
     * @param commissionId
     * @return
     */
    @GetMapping("/queryCommissionById/{commissionId}")
    public ControllerResponse queryCommissionById(@PathVariable Long commissionId) {
        try {
            return ControllerResponse.success(commissionService.getCommissionById(commissionId));
        } catch (Exception e) {
            LOGGER.error("queryCommissionById method param commissionId:{} occur exception:{}", commissionId, e.getMessage());
            return ControllerResponse.fail("查询代办详情异常");
        }
    }

    /**
     * @description 更新代办信息
     * @date 2021/2/1
     * @param commissionDTO
     * @return
     */
    @PostMapping("/modifyCommission")
    public ControllerResponse modifyCommission(@RequestBody CommissionDTO commissionDTO) {
        try {
            commissionService.updateCommission(CommissionConstant.PRIMARY_KEY_TYPE, commissionDTO);
            return ControllerResponse.success("更新代办信息成功");
        } catch (Exception e) {
            LOGGER.error("modifyCommission method param commissionId:{} occur exception:{}", commissionDTO.getId(), e.getMessage());
            return ControllerResponse.fail("更新代办信息失败");
        }
    }

}

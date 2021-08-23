package com.yusys.agile.fault.rest;

import com.yusys.agile.fault.enums.FaultSourceEnum;
import com.yusys.agile.fault.service.FaultService;
import com.yusys.agile.issue.dto.IssueDTO;
import com.github.pagehelper.PageInfo;
import com.yusys.agile.utils.YuItUtil;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 缺陷controller
 *
 * @date 2021/2/2
 */
@RestController
@RequestMapping("/fault")
public class FaultController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FaultController.class);

    @Autowired
    private FaultService faultService;

    /**
     * 功能描述:新增缺陷
     *
     * @param issueDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/2/2
     */

    @PostMapping("/addFault")
    public ControllerResponse addFault(@RequestBody IssueDTO issueDTO, @RequestHeader(name = "projectId") Long projectId) {
        if (YuItUtil.yuItSync()) {
            return ControllerResponse.fail("缺陷模式为从ITC同步缺陷，不能新增！");
        }

        issueDTO.setProjectId(projectId);
        // controller是YuDO自己调的
        issueDTO.setSource(FaultSourceEnum.YuDO2.CODE);
        faultService.addFault(issueDTO);
        return ControllerResponse.success();
    }

    /**
     * 功能描述: 删除缺陷
     *
     * @param issueId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/11
     */

    @DeleteMapping("/deleteFault/{issueId}")
    public ControllerResponse deleteFault(@PathVariable Long issueId) {
        if (YuItUtil.yuItSync()) {
            return ControllerResponse.fail("缺陷模式为从ITC同步缺陷，不能删除！");
        }
        faultService.deleteFault(issueId);
        return ControllerResponse.success();
    }


    /**
     * 功能描述: 查看缺陷详情
     *
     * @param issueId
     * @return com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/13
     */

    @GetMapping("/getFault/{issueId}")
    public ControllerResponse getFault(@PathVariable Long issueId, @RequestHeader(name = "projectId") Long projectId) {
        return ControllerResponse.success(faultService.getFault(issueId));
    }

    /**
     * 功能描述: 修改缺陷
     *
     * @param faultDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/13
     */

    @PostMapping("/updateFault")
    public ControllerResponse updateFault(@RequestBody IssueDTO faultDTO) {
        try {
            faultService.updateFault(faultDTO);
        } catch (Exception e) {
            LOGGER.error("修改缺陷错误 e : {}", e.getMessage());
            return ControllerResponse.fail("修改缺陷失败!");
        }

        return ControllerResponse.success();


    }

    /**
     * 功能描述: 查询所有缺陷级别
     *
     * @param
     * @return com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/11
     */

    @GetMapping("/listAllFaultLevel")
    public ControllerResponse listAllFaultLevel() {
        return ControllerResponse.success(faultService.listAllFaultLevel());
    }

    /**
     * 功能描述: 查询所有缺陷类别
     *
     * @param
     * @return com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/11
     */

    @GetMapping("/listAllFaultType")
    public ControllerResponse listAllFaultType() {
        return ControllerResponse.success(faultService.listAllFaultType());
    }

    /**
     * 功能描述: 查询项目下所有bug的提出人，用于过滤条件
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/14
     */
    @GetMapping("/listAllCreateUsers")
    public ControllerResponse listAllCreateUsers(@RequestHeader(name = "projectId") Long projectId) {
        return ControllerResponse.success(faultService.listAllCreateUsers(projectId));
    }

    /**
     * 功能描述: 查询项目的所有状态
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/14
     */

    @GetMapping("/listAllStatus")
    public ControllerResponse listAllStatus() {

        return ControllerResponse.success(faultService.listAllStatus());
    }

    /**
     * 功能描述: 查询项目下所有bug的提出人，用于过滤条件
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/14
     */

    @GetMapping("/listAllFixedUsers")
    public ControllerResponse listAllFixedUsers(@RequestHeader(name = "projectId") Long projectId) {

        return ControllerResponse.success(faultService.listAllFixedUsers(projectId));
    }

    /**
     * 功能描述: 查询项目下所有bug的提出人，用于过滤条件
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/14
     */

    @GetMapping("/listAllTestUsers")
    public ControllerResponse listAllTestUsers(@RequestHeader(name = "projectId") Long projectId) {

        return ControllerResponse.success(faultService.listAllTestUsers(projectId));
    }


    /**
     * 功能描述: 列表分页查询缺陷
     *
     * @param faultLevel 缺陷级别
     * @param faultType  缺陷类型
     * @param stageId    状态
     * @param sprintId   迭代id
     * @param createUid  创建人id
     * @param createDate 日期格式2020-04-11
     * @param fixedUid   修复人id
     * @param testUid    验证人id
     * @param pageNum    每页数量
     * @param pageSize   页数
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/14
     */

    @GetMapping("/listFaults")
    public ControllerResponse listFaults(@RequestParam(name = "filter", required = false) String filter,
                                         @RequestParam(name = "faultLevel", required = false) Long faultLevel,
                                         @RequestParam(name = "faultType", required = false) Long faultType,
                                         @RequestParam(name = "stageId", required = false) Long stageId,
                                         @RequestParam(name = "sprintId", required = false) Long sprintId,
                                         @RequestParam(name = "createUid", required = false) Long createUid,
                                         @RequestParam(name = "createDate", required = false) String createDate,
                                         @RequestParam(name = "fixedUid", required = false) Long fixedUid,
                                         @RequestParam(name = "testUid", required = false) Long testUid,
                                         @RequestParam(name = "pageNum") Integer pageNum,
                                         @RequestParam(name = "pageSize") Integer pageSize,
                                         @RequestHeader(name = "projectId") Long projectId) {
        try {
            List<IssueDTO> result = faultService.listFaults(filter, faultLevel, faultType, stageId, sprintId, createUid, createDate, fixedUid, testUid, projectId, pageNum, pageSize);
            return ControllerResponse.success(new PageInfo<>(result));
        } catch (Exception e) {
            LOGGER.error("查询缺陷失败! e={}", e.getMessage());
            return ControllerResponse.fail("查询缺陷失败！ " + e.getMessage());
        }


    }

    /**
     * 功能描述: 列表展示未关联迭代的缺陷或故事
     *
     * @param filter
     * @param issueType 3 story 5 fault
     * @param pageNum
     * @param pageSize
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/14
     */

    @GetMapping("/listFaultsOrStorysNotLinkSprint")
    public ControllerResponse listFaultsOrStorysNotLinkSprint(@RequestParam(name = "filter", required = false) String filter,
                                                              @RequestParam(name = "issueType") Byte issueType,
                                                              @RequestParam(name = "pageNum") Integer pageNum,
                                                              @RequestParam(name = "pageSize") Integer pageSize,
                                                              @RequestHeader(name = "projectId") Long projectId) {

        List<IssueDTO> result = faultService.listFaultsOrStorysNotLinkSprint(filter, issueType, projectId, pageNum, pageSize);
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * 功能描述: 拖拽卡片改变缺陷状态，itc则增加调用通知
     *
     * @param issueDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/26
     */
    @PostMapping("/drag")
    public ControllerResponse dragFault(@RequestBody IssueDTO issueDTO) {
        try {
            faultService.dragFault(issueDTO);
        } catch (Exception e) {
            return ControllerResponse.fail("拖拽缺陷卡片失败! " + e.getMessage());
        }
        return ControllerResponse.success();
    }

    /**
     * 功能描述: 缺陷设置阻塞状态
     *
     * @param issueDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/2/1
     */
    @PostMapping("/editBlock")
    public ControllerResponse editBlockState(@RequestBody IssueDTO issueDTO) {
        try {
            faultService.editBlockState(issueDTO);
        } catch (Exception e) {
            return ControllerResponse.fail(" 缺陷设置阻塞状态失败! " + e.getMessage());
        }
        return ControllerResponse.success();
    }

    /**
     * @param projectId
     * @return
     * @description 统计缺陷待修复个数，修复中个数，已修复个数，已关闭个数，总数，修复率
     * @date 2020/09/07
     */
    @GetMapping("/statisticFaults")
    public ControllerResponse statisticFaults(@RequestHeader Long projectId) {
        try {
            return ControllerResponse.success(faultService.statisticFaults(projectId));
        } catch (Exception e) {
            LOGGER.error("统计缺陷修复异常,异常信息:{}", e.getMessage());
            return ControllerResponse.fail("统计缺陷修复异常");
        }
    }
}
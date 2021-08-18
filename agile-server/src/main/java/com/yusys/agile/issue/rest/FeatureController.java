package com.yusys.agile.issue.rest;

import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.FeatureService;
import com.yusys.agile.issue.service.IssueService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/issue/feature")
public class FeatureController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureController.class);

    @Resource
    private FeatureService featureService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private IssueFactory issueFactory;
    @Resource
    private IssueService issueService;

    @PostMapping("/createFeature")
    public ControllerResponse createFeature(@RequestBody Map<String, Object> featureMap) {
        try {
            //保存基本字段
            JSONObject jsonObject = new JSONObject(featureMap);
            IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
            Long issueId = featureService.createFeature(issueDTO);

            //批量新增或者批量更新扩展字段值
            issueDTO.setIssueType(new Byte("2"));
            issueDTO.setIssueId(issueId);
            issueFactory.batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issueId);
            return ControllerResponse.success(issueId);
        } catch (Exception e) {
            LOGGER.error("新增研发需求失败：{}", e);
            return ControllerResponse.fail("新增研发需求失败：" + e.getMessage());
        }
    }

    @GetMapping("/queryFeature/{featureId}")
    public ControllerResponse queryFeature(@PathVariable("featureId") Long featureId) {
        IssueDTO issueDTO = featureService.queryFeature(featureId);
        Map<String, Object> map = Maps.newHashMap();
        if (null != issueDTO) {
            map = BeanMap.create(issueDTO);
        }
        issueService.orgIssueExtendFields(featureId,map);
        return ControllerResponse.success(map);
    }

    @DeleteMapping("/deleteFeature/{featureId}")
    public ControllerResponse deleteFeature(@PathVariable("featureId") Long featureId,Boolean deleteChild) {
        try {
            featureService.deleteFeature(featureId,deleteChild);
        } catch (Exception e) {
            LOGGER.error("删除研发需求失败：{}", e);
            return ControllerResponse.fail("删除研发需求失败：" + e.getMessage());
        }
        return ControllerResponse.success("删除研发需求成功！");
    }

    @PostMapping("/editFeature")
    public ControllerResponse editFeature(@RequestBody Map<String, Object> map) {
        try {
            //组织基本字段信息
            JSONObject jsonObject = new JSONObject(map);
            IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
            featureService.editFeature(issueDTO);
            //批量新增或者批量更新扩展字段值
            issueDTO.setIssueType(new Byte("2"));
            issueFactory.batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issueDTO.getIssueId());
        } catch (Exception e) {
            return ControllerResponse.fail(e.getMessage());
        }
        return ControllerResponse.success("编辑研发需求成功！");
    }

    @PutMapping("/copyFeature/{featureId}")
    public ControllerResponse copyFeature(@PathVariable(name = "featureId") Long featureId) {
        try {
            Long newFeatureId = featureService.copyFeature(featureId);
            return ControllerResponse.success(newFeatureId);
        } catch (Exception e) {
            LOGGER.error("复制研发需求失败：{}", e);
            return ControllerResponse.fail("复制研发需求失败：" + e.getMessage());
        }
    }

    @GetMapping("/queryUnlinkedFeature")
    public ControllerResponse queryUnlinkedFeature(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize,
                                                   @RequestParam(value = "issueId",required = false) Long issueId,
                                                   @RequestParam(value = "systemId",required = false) Long systemId,
                                                   @RequestParam(value = "title", required = false) String title) {
        List<IssueDTO> result;
        try {
            result = featureService.queryUnlinkedFeature(null, pageNum, pageSize, title);
        } catch (Exception e) {
            LOGGER.error("查询未关联的研发需求异常", e);
            return ControllerResponse.fail("查询未关联的研发需求异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    @GetMapping("/queryAllFeature")
    public ControllerResponse queryAllFeature(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                              @RequestParam(value = "issueId",required = false) Long issueId,
                                              @RequestParam(value = "systemId",required = false) Long systemId,
                                              @RequestParam(value = "title", required = false) String title) {
        List<IssueDTO> result;
        try {
            result = featureService.queryAllFeature(systemId, pageNum, pageSize, title);
        } catch (Exception e) {
            LOGGER.error("查询所有的研发需求异常", e);
            return ControllerResponse.fail("查询所有的研发需求异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param epicId
     * @Date: 2021/2/9 9:47
     * @Description: 查询业务需求下的所有研发需求
     * @Param: * @param projectId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/queryFeatureForEpic")
    public ControllerResponse queryFeatureForEpic(@RequestHeader(name = "projectId",required = false) Long projectId, @RequestParam("epicId") Long epicId) {
        List<IssueDTO> result;
        try {
            result = featureService.queryFeatureForEpic(projectId, epicId);
        } catch (Exception e) {
            LOGGER.error("查询epic下所有的研发需求异常", e);
            return ControllerResponse.fail("查询epic下所有的研发需求异常：" + e.getMessage());
        }
        return ControllerResponse.success(result);
    }
}

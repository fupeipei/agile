package com.yusys.agile.issue.rest;

import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.service.FeatureService;
import com.yusys.agile.issue.utils.IssueFactory;
import com.yusys.agile.requirement.domain.SysExtendFieldDetail;
import com.yusys.agile.requirement.service.SysExtendFieldDetailService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
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
public class FeatureController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureController.class);

    @Resource
    private FeatureService featureService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SysExtendFieldDetailService sysExtendFieldDetailService;
    @Resource
    private IssueFactory issueFactory;

    @PostMapping("/issue/createFeature")
    public ControllerResponse createFeature(@RequestBody IssueDTO issueDTO, @RequestHeader(name = "projectId") Long projectId) {
        try {
            //issueDTO.setProjectId(projectId);
            Long paramProjectId = issueDTO.getProjectId();
            if (null == paramProjectId) {
                issueDTO.setProjectId(projectId);
            }
            Long issueId = featureService.createFeature(issueDTO);
            return ControllerResponse.success(issueId);
        } catch (Exception e) {
            LOGGER.error("新增研发需求失败：{}", e);
            return ControllerResponse.fail("新增研发需求失败：" + e.getMessage());
        }
    }

    @GetMapping("/issue/queryFeature/{featureId}")
    public ControllerResponse queryFeature(@PathVariable("featureId") Long featureId, @RequestHeader(name = "projectId") Long projectId) {
        IssueDTO issueDTO = featureService.queryFeature(featureId);
        Map<String, Object> map = Maps.newHashMap();
        if (null != issueDTO) {
            BeanMap beanMap = BeanMap.create(issueDTO);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        if (null != featureId) {
            List<Long> issueIds = Lists.newArrayList();
            issueIds.add(featureId);
            List<SysExtendFieldDetail> sysExtendFieldDetailList = sysExtendFieldDetailService.getIssueExtendDetailList(issueIds);
            for (int i = 0; i < sysExtendFieldDetailList.size(); i++) {
                map.put(sysExtendFieldDetailList.get(i).getFieldId(), sysExtendFieldDetailList.get(i).getValue());
            }
        }
        return ControllerResponse.success(map);
    }

    @DeleteMapping("/issue/deleteFeature/{featureId}")
    public ControllerResponse deleteFeature(@PathVariable("featureId") Long featureId, Boolean deleteChild, @RequestHeader(name = "projectId") Long projectId) {
        try {
            //featureService.deleteFeature(featureId,deleteChild,projectId);
            featureService.deleteFeature(featureId, deleteChild);
        } catch (Exception e) {
            LOGGER.error("删除研发需求失败：{}", e);
            return ControllerResponse.fail("删除研发需求失败：" + e.getMessage());
        }
        return ControllerResponse.success("删除研发需求成功！");
    }

    @PostMapping("/issue/editFeature")
    public ControllerResponse editFeature(@RequestBody Map<String, Object> map, @RequestHeader(name = "projectId") Long projectId) {
        try{
            //暂时先将扩展字段扔掉
            JSONObject jsonObject = new JSONObject(map);
            IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
            featureService.editFeature(issueDTO);
            //批量新增或者批量更新扩展字段值
            issueDTO.setIssueType(new Byte("2"));
            issueFactory.batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issueDTO.getIssueId());
        }catch (Exception e){
            /*if(e instanceof BaseBusinessException){
                return ControllerResponse.fail2(e.getMessage());
            }*/
            return ControllerResponse.fail(e.getMessage());
        }
        return ControllerResponse.success("编辑研发需求成功！");
    }

    @PutMapping("/issue/copyFeature/{featureId}")
    public ControllerResponse copyFeature(@PathVariable(name = "featureId") Long featureId, @RequestHeader(name = "projectId") Long projectId) {
        try {
            Long newFeatureId = featureService.copyFeature(featureId, projectId);
            return ControllerResponse.success(newFeatureId);
        } catch (Exception e) {
            LOGGER.error("复制研发需求失败：{}", e);
            return ControllerResponse.fail("复制研发需求失败：" + e.getMessage());
        }
    }

    @GetMapping("/issue/queryUnlinkedFeature")
    public ControllerResponse queryUnlinkedFeature(@RequestHeader(name = "projectId") Long projectId, @RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "title", required = false) String title,
                                                   @RequestParam(name = "projectId", required = false) Long paramProjectId) {
        Long finalProjectId = null;
        if (null != paramProjectId) {
            finalProjectId = paramProjectId;
        } else {
            finalProjectId = projectId;
        }
        List<IssueDTO> result;
        try {
            result = featureService.queryUnlinkedFeature(finalProjectId, pageNum, pageSize, title);
        } catch (Exception e) {
            LOGGER.error("查询未关联的研发需求异常", e);
            return ControllerResponse.fail("查询未关联的研发需求异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    @GetMapping("/issue/queryAllFeature")
    public ControllerResponse queryAllFeature(@RequestHeader(name = "projectId", required = false) Long projectId, @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "title", required = false) String title,
                                              @RequestParam(name = "projectId", required = false) Long paramProjectId) {
        Long finalProjectId = null;
        if (null != paramProjectId) {
            finalProjectId = paramProjectId;
        } else {
            finalProjectId = projectId;
        }
        List<IssueDTO> result;
        try {
            result = featureService.queryAllFeature(finalProjectId, pageNum, pageSize, title);
        } catch (Exception e) {
            LOGGER.error("查询所有的研发需求异常", e);
            return ControllerResponse.fail("查询所有的研发需求异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }

    /**
     * @param epicId
     *
     * @Date: 2020/6/9 9:47
     * @Description: 查询业务需求下的所有研发需求
     * @Param: * @param projectId
     * @Return: import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/issue/queryFeatureForEpic")
    public ControllerResponse queryFeatureForEpic(@RequestHeader(name = "projectId") Long projectId, @RequestParam("epicId") Long epicId) {
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

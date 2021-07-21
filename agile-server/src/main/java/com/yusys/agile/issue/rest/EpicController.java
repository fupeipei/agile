package com.yusys.agile.issue.rest;

import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.issue.dto.IssueStageIdCountDTO;
import com.yusys.agile.issue.service.EpicService;
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
@RequestMapping("/issue/epic")
public class EpicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EpicController.class);

    @Resource
    private IssueFactory issueFactory;
    @Resource
    private EpicService epicService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private IssueService issueService;

    @PostMapping("/createEpic")
    public ControllerResponse createEpic(@RequestBody Map<String, Object> epicMap){
        try {
            //保存基本字段
            JSONObject jsonObject = new JSONObject(epicMap);
            IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
            //默认为未发起
            issueDTO.setStartSchedule((byte)1);
            Long issueId = epicService.createEpic(issueDTO);

            //批量新增或者批量更新扩展字段值
            issueDTO.setIssueType(new Byte("1"));
            issueDTO.setIssueId(issueId);
            issueFactory.batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
            rabbitTemplate.convertAndSend(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE, issueId);
            return ControllerResponse.success(issueId);
        } catch (Exception e) {
            LOGGER.error("新增业务需求失败：{}", e);
            return ControllerResponse.fail("新增业务需求失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/deleteEpic/{epicId}")
    public ControllerResponse deleteEpic(@PathVariable("epicId") Long epicId,Boolean deleteChild) {
        try {
            epicService.deleteEpic(epicId,deleteChild);
        } catch (Exception e) {
            LOGGER.error("删除业务需求失败：{}", e);
            return ControllerResponse.fail("删除业务需求失败：" + e.getMessage());
        }
        return ControllerResponse.success("删除业务需求成功！");
    }

    @GetMapping("/queryEpic/{epicId}")
    public ControllerResponse queryEpic(@PathVariable("epicId") Long epicId) {
        IssueDTO issueDTO = epicService.queryEpic(epicId);
        Map<String, Object> map = Maps.newHashMap();
        if (null != issueDTO) {
            BeanMap beanMap = BeanMap.create(issueDTO);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        issueService.orgIssueExtendFields(epicId,map);
        return ControllerResponse.success(map);
    }

    @PostMapping("/editEpic")
    public ControllerResponse editEpic(@RequestBody Map<String, Object> map) {
        try {
            //暂时先将扩展字段扔掉
            JSONObject jsonObject = new JSONObject(map);
            IssueDTO issueDTO = JSON.parseObject(jsonObject.toJSONString(), IssueDTO.class);
            epicService.editEpic(issueDTO);
            //批量新增或者批量更新扩展字段值
            issueDTO.setIssueType(new Byte("1"));
            issueFactory.batchSaveOrUpdateSysExtendFieldDetail(jsonObject, issueDTO);
        } catch (Exception e) {
            return ControllerResponse.fail(e.getMessage());
        }

        return ControllerResponse.success("编辑业务需求成功！");
    }

    @PutMapping("/copyEpic/{epicId}")
    public ControllerResponse copyEpic(@PathVariable(name = "epicId") Long epicId) {
        try {
            Long newEpicId = epicService.copyEpic(epicId);
            return ControllerResponse.success(newEpicId);
        } catch (Exception e) {
            LOGGER.error("复制业务需求失败：{}", e);
            return ControllerResponse.fail("复制业务需求失败：" + e.getMessage());
        }
    }

    @GetMapping("/queryAllEpic")
    public ControllerResponse queryAllEpic(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                           @RequestParam(value = "issueId",required = false) Long issueId,
                                           @RequestParam(value = "systemId",required = false) Long systemId,
                                           @RequestParam(value = "title", required = false) String title) {
        List<IssueDTO> result;
        try {
            result = epicService.queryAllEpic(systemId, pageNum, pageSize, title);
        } catch (Exception e) {
            LOGGER.error("查询所有的业务需求异常", e);
            return ControllerResponse.fail("查询所有的业务需求异常：" + e.getMessage());
        }
        return ControllerResponse.success(new PageInfo<>(result));
    }


    /**
     * 功能描述: 根据业务需求或研发需求查询对应的故事id集合
     *
     * @param id   业需或研需id
     * @param type 1 业务需求 2研发需求
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/2/22
     */
    @GetMapping("/queryStroyIds")
    public ControllerResponse queryStroyIds(@RequestParam(value = "id") Long id, @RequestParam(value = "type") Byte type) {
        return ControllerResponse.success(issueFactory.queryStroyIds(id, type));
    }

    /**
     * 功能描述: 按版本统计系统各个阶段需求个数
     *
     * @param projectId 项目id
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/3/30
     */
    @GetMapping("/queryAllEpicCountByVersionId")
    public ControllerResponse queryAllEpicCountByVersionId(@RequestHeader(name = "projectId", required = false) Long projectId) {
        List<IssueStageIdCountDTO> result;
        try {
            result = epicService.queryAllEpicCountByVersionId(projectId);
        } catch (Exception e) {
            LOGGER.error("按版本统计系统各个阶段需求个数异常", e);
            return ControllerResponse.fail("按版本统计系统各个阶段需求个数异常：" + e.getMessage());
        }
        return ControllerResponse.success(result);
    }

    /**
     * 功能描述: 根据epicId查询下面所有的featureId
     *
     * @param epicId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/10/13
     */
    @GetMapping("/queryFeatureIdsByEpicId/{epicId}")
    public ControllerResponse queryFeatureIdsByEpicId(@PathVariable(name = "epicId") Long epicId) {
        return ControllerResponse.success(epicService.queryFeatureIdsByEpicId(epicId));
    }

}

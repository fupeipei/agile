package com.yusys.agile.set.stage.rest.impl;

import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.agile.set.stage.domain.StageInstance;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import com.yusys.agile.set.stage.exception.StageException;
import com.yusys.agile.set.stage.service.StageService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 阶段controller实现类
 *  
 * @create 2020-04-10 16:28
 */
@RestController
@RequestMapping("/stage")
public class StageControllerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(StageControllerImpl.class);

    @Resource
    private StageService stageService;

    /**
     * @description 根据项目id查询阶段列表
     * @param projectId
     * @param paramProjectId
     * @return com.ai.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/getStageList")
    public ControllerResponse queryStageList(@RequestHeader("projectId") Long projectId, @RequestParam(name = "projectId",required = false) Long paramProjectId) {
        try {
            Long stageProjectId = null;
            if (null != paramProjectId) {
                stageProjectId = paramProjectId;
            } else {
                stageProjectId = projectId;
            }
            List<StageInstance> kanbanStageInstances = stageService.getStageList(stageProjectId);
            return ControllerResponse.success(kanbanStageInstances);
        } catch (Exception e) {
            LOGGER.error("queryStageList method occur exception param headerProjectId:{},paramProjectId:{},message:{}", projectId, paramProjectId, e.getMessage());
            if (e instanceof StageException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("阶段列表获取异常");
        }
    }

    /**
     * @description 编辑一阶段
     * @param stageInstanceDTOList
     * @return com.ai.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/configFirstStages")
    public ControllerResponse configFirstStages(@RequestBody List<KanbanStageInstanceDTO> stageInstanceDTOList) {
        try {
            int count = stageService.configFirstStages(stageInstanceDTOList);
            if (count > 0) {
                return ControllerResponse.success("一阶段设置成功");
            }
            return ControllerResponse.fail("一阶段设置失败");
        } catch (Exception e) {
            LOGGER.error("configFirstStages method occur exception param stageInstanceDTOList:{}, message:{}", stageInstanceDTOList, e.getMessage());
            if (e instanceof StageException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("一阶段设置异常");
        }
    }

    /**
     * @description 新增二阶段
     * @param projectId
     * @param kanbanStageInstanceDTO
     * @return com.ai.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/addSecondStages")
    public ControllerResponse addSecondStages(@RequestHeader("projectId") Long projectId, @RequestBody KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        LOGGER.info("addSecondStages methods Params: {}",kanbanStageInstanceDTO.toString());
        try {
            int count = stageService.addSecondStage(projectId, kanbanStageInstanceDTO);
            if (count > 0) {
                return ControllerResponse.success("新增二阶段状态成功");
            }
            return ControllerResponse.fail("新增二阶段状态失败");
        } catch (Exception e) {
            LOGGER.error("addSecondStages method param projectId:{}, kanbanStageInstanceDTO:{}, occur exception message:{}", projectId, kanbanStageInstanceDTO, e.getMessage());
            if (e instanceof StageException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("新增二阶段状态异常");
        }
    }

    /**
     * @description 修改二阶段
     * @param projectId
     * @param kanbanStageInstanceDTO
     * @return com.ai.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/modifySecondStages")
    public ControllerResponse modifySecondStages(@RequestHeader("projectId") Long projectId, @RequestBody KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        try {
            int count = stageService.modifySecondStage(projectId, kanbanStageInstanceDTO);
            if (count > 0) {
                return ControllerResponse.success("编辑二阶段状态成功");
            }
            return ControllerResponse.fail("编辑二阶段状态失败");
        } catch (Exception e) {
            LOGGER.error("modifySecondStages method param projectId:{}, kanbanStageInstanceDTO:{}, occur exception message:{}", projectId, kanbanStageInstanceDTO, e.getMessage());
            if (e instanceof StageException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("编辑二阶段状态异常");
        }
    }

    /**
     * @description 删除二阶段
     * @param projectId
     * @param kanbanStageInstanceDTO
     * @return com.ai.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/deleteSecondStages")
    public ControllerResponse deleteSecondStages(@RequestHeader("projectId") Long projectId, @RequestBody KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        try {
            int count = stageService.deleteSecondStage(projectId, kanbanStageInstanceDTO);
            if (count > 0) {
                return ControllerResponse.success("删除二阶段成功");
            }
            return ControllerResponse.fail("删除二阶段失败");
        } catch (Exception e) {
            LOGGER.error("deleteSecondStages method param projectId:{}, kanbanStageInstanceDTO:{}, occur exception message:{}", projectId, kanbanStageInstanceDTO, e.getMessage());
            if (e instanceof StageException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("删除二阶段异常");
        }
    }

    /**
     * @description 排序二阶段
     * @param projectId
     * @param instanceIds
     * @return com.ai.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/sortSecondStages")
    public ControllerResponse sortSecondStages(@RequestHeader("projectId") Long projectId, @RequestBody List<KanbanStageInstanceDTO> instanceIds) {
        try {
            if (CollectionUtils.isEmpty(instanceIds) || instanceIds.size() != 2) {
                return ControllerResponse.fail("二阶段排序数据有误");
            }
            List<Long> instanceIdList = Lists.newArrayList();
            for (KanbanStageInstanceDTO kanbanStageInstanceDTO : instanceIds) {
                instanceIdList.add(kanbanStageInstanceDTO.getInstanceId());
            }
            int count = stageService.sortSecondStage(projectId, instanceIdList);
            if (count > 0) {
                return ControllerResponse.success("二阶段状态排序成功");
            }
            return ControllerResponse.fail("二阶段状态排序失败");
        } catch (Exception e) {
            LOGGER.error("sortSecondStages method param projectId:{}, instanceIds:{}, occur exception message:{}", projectId, instanceIds, e.getMessage());
            if (e instanceof StageException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("二阶段状态排序异常");
        }
    }

    /**
     * @description 初始化阶段模板数据
     *  
     * @date 2020/05/18
     * @param kanbanStageInstances
     * @return
     */
    @PostMapping("/initKanbanStageTemplateDatas")
    public int initKanbanStageTemplateDatas(@RequestBody List<KanbanStageInstanceDTO> kanbanStageInstances) {
        int count = 0;
        try {
            count = stageService.initKanbanStageList(kanbanStageInstances);
        } catch (Exception e) {
            LOGGER.error("initKanbanStageList method occur exception, message:{}",e.getMessage());
        }
        return count;
    }

    /**
     * @description 新增超时天数、最大制品数、准入规则字段
     *  
     * @date 2020/06/01
     * @param projectId
     * @param kanbanStageInstanceDTO
     * @return
     */
    @PostMapping("/addStagePopUpInfos")
    public ControllerResponse addStagePopUpInfos(@RequestHeader("projectId") Long projectId, @RequestBody KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        try {
            stageService.addStagePopUpInfos(projectId, kanbanStageInstanceDTO);
            return ControllerResponse.success("阶段弹框信息设置成功");
        } catch (Exception e){
            LOGGER.error("addStagePopUpInfos method occur exception, message:{}",e.getMessage());
            return ControllerResponse.fail("阶段弹框信息设置异常");
        }
    }

    /**
     * @description 更新超时天数、最大制品数、准入规则字段
     *  
     * @date 2020/06/01
     * @param kanbanStageInstanceDTO
     * @return
     */
    @PostMapping("/modifyStagePopUpInfos")
    public ControllerResponse modifyStagePopUpInfos(@RequestBody KanbanStageInstanceDTO kanbanStageInstanceDTO) {
        try {
            stageService.modifyStagePopUpInfos(kanbanStageInstanceDTO);
            return ControllerResponse.success("阶段弹框信息修改成功");
        } catch (Exception e) {
            LOGGER.error("modifyStagePopUpInfos method occur exception, message:{}",e.getMessage());
            return ControllerResponse.fail("阶段弹框信息修改异常");
        }
    }

    /**
     * @description 修改一阶段责任人
     * @param instanceId
     * @param handler
     * @return
     */
    @PutMapping("/modifyFirstStageHandler")
    public ControllerResponse modifyFirstStageHandler(Long instanceId, Long handler) {
        try {
            int count = stageService.modifyFirstStageHandler(instanceId, handler);
            if (count > 0) {
                return ControllerResponse.success("修改一阶段责任人成功");
            }
            return ControllerResponse.fail("修改一阶段责任人失败");
        } catch (Exception e) {
            LOGGER.error("modifyFirstStageHandler method occur exception, message:{}", e.getMessage());
            if (e instanceof StageException) {
                return ControllerResponse.fail(e.getMessage());
            }
            return ControllerResponse.fail("修改一阶段责任人异常");
        }
    }
}

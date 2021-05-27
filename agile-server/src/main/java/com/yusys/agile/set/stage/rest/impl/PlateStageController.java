package com.yusys.agile.set.stage.rest.impl;

import com.yusys.agile.set.stage.domain.StageInstance;
import com.yusys.agile.set.stage.service.IStageService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stage")
public class PlateStageController {


    @Autowired
    private IStageService iStageService;

    /**
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @description 根据项目id查询阶段列表
     */
    @GetMapping("/getStage")
    public ControllerResponse queryStageList() {
        try {
            List<StageInstance> kanbanStageInstances = iStageService.getStageList();
            return ControllerResponse.success(kanbanStageInstances);
        } catch (Exception e) {
            return ControllerResponse.fail("阶段列表获取异常");
        }
    }
}

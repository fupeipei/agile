package com.yusys.agile.set.stage.service;

import com.yusys.agile.set.stage.domain.StageInstance;

import java.util.List;

/**
 *  @Description: 阶段service
 *  @author: zhao_yd
 *  @Date: 2021/5/24 3:04 下午
 *
 */

public interface IStageService {

    List<StageInstance> getStageList();

    List<StageInstance> getSecondStageListByParentId(Long stageParentId);
}

package com.yusys.agile.fault.rest;

import com.alibaba.fastjson.JSONObject;
import com.yusys.portal.model.common.dto.ControllerResponse;

/**
 * Itc同步缺陷api
 *
 * @create 2020-04-24 11:21
 */
public interface IFaultSyncApi {

    /**
     * 从ITC同步缺陷接口
     *
     * @param jsonObject
     * @return
     */
    ControllerResponse syncFaultFromITC(JSONObject jsonObject);

    /**
     * 功能描述: 从itc同步删除缺陷
     *
     * @param bugId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/24
     */
    ControllerResponse deleteFaultFromITC(Long bugId);

    /**
     * 功能描述: 从itc同步关闭缺陷
     *
     * @param jsonObject
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/24
     */
    ControllerResponse closeFaultFromITC(JSONObject jsonObject);

    /**
     * 功能描述: 从itc同步重新打开缺陷
     *
     * @param jsonObject
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/26
     */
    ControllerResponse reopenFaultFromITC(JSONObject jsonObject);


    /**
     * 功能描述: 从itc给
     *
     * @param bugId
     * @param storyId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2020/4/27
     */
    ControllerResponse associateStoryFromITC(Long bugId, Long storyId);


}

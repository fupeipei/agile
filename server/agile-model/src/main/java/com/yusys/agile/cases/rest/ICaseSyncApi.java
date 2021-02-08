package com.yusys.agile.cases.rest;



import com.alibaba.fastjson.JSONObject;
import com.yusys.portal.model.common.dto.ControllerResponse;

/**
 * itc同步测试用例api
 *
 *     
 * @create 2020-06-22 09:11
 */
public interface ICaseSyncApi {

    /**
     * 功能描述: 从itc同步测试用例
     *
     * @param jsonObject
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     *     
     * @date 2020/6/22
     */
    ControllerResponse syncCaseFromITC(JSONObject jsonObject);


    /**
     * 功能描述: 从itc修改测试用例
     *
     * @param jsonObject
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     *     
     * @date 2020/6/22
     */
    ControllerResponse editCaseFromITC(JSONObject jsonObject);

    /**
     * 功能描述: 从itc删除测试用例
     *
     * @param caseId
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     *     
     * @date 2020/6/22
     */
    ControllerResponse deleteCaseFromITC(Long caseId);

    /**
     * 功能描述: 从itc同步用例执行情况
     *
     * @param jsonObject
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     *     
     * @date 2020/6/22
     */
    ControllerResponse syncCaseExecuteFromITC(JSONObject jsonObject);
}



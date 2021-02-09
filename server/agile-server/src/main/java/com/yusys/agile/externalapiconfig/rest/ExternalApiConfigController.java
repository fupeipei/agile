package com.yusys.agile.externalapiconfig.rest;

import com.yusys.agile.externalapiconfig.dao.util.ExternalApiConfigUtil;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date: 2021/2/2
 * @Description: TODO
 */
@RestController
public class ExternalApiConfigController {
    @Resource
    private ExternalApiConfigUtil externalApiConfigUtil;


    /**
     * 功能描述：当前是系统所属地
     *
     * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     * @date 2021/2/2
     */

    @GetMapping("/attribution")
    public ControllerResponse getAttribution() {
        return ControllerResponse.success(externalApiConfigUtil.getPropValue("ATTRIBUTION"));
    }

}

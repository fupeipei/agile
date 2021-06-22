package com.yusys.agile.headerfield.rest.impl;

import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * :
 *
 * @Date: 2020/4/13
 * @Description:
 */
@RestController
@RequestMapping("/headerfield")
public class HeaderFieldController {

    @Resource
    HeaderFieldService headerFieldService;

    private static final Logger log = LoggerFactory.getLogger(HeaderFieldController.class);

    /**
     * 功能描述 列头初始化查询接口
     *
     * @param securityDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2020/4/13
     */
    @GetMapping("/queryHeaderFields")
    public ControllerResponse queryHeaderFields(
            SecurityDTO securityDTO,
            @RequestParam("category") Byte category,
            @RequestParam(value = "isFilter", required = false) Byte isFilter,
            @RequestHeader(value = "systemId", required = false) Long systemId) {
            if(Optional.ofNullable(systemId).isPresent()){
                securityDTO.setSystemId(systemId);
            }
        Map map = headerFieldService.queryHeaderFields(securityDTO, category, isFilter);
        return ControllerResponse.success(map);
    }
}

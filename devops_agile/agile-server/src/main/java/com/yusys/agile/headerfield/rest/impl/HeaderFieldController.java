package com.yusys.agile.headerfield.rest.impl;

import com.yusys.agile.headerfield.service.HeaderFieldService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.thread.UserThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *   :
 * @Date: 2020/4/13
 * @Description:
 */
@RestController
@RequestMapping("/headerfield")
public class HeaderFieldController {

    @Resource
    HeaderFieldService  headerFieldService;

    private static final Logger log = LoggerFactory.getLogger(HeaderFieldController.class);
    /**
      *功能描述 列头初始化查询接口
      *   
      * @date 2020/4/13
     * @param securityDTO
      * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @GetMapping("/queryHeaderFields")
    public ControllerResponse queryHeaderFields(SecurityDTO securityDTO, @RequestHeader(name = "projectId") Long  projectId, @RequestParam("category") Byte category,
                                                @RequestParam(value = "isFilter",required = false)Byte isFilter, @RequestParam(name = "projectId",required = false) Long paramProjectId) {
        if (null != paramProjectId) {
            securityDTO.setProjectId(paramProjectId);
        } else {
            securityDTO.setProjectId(projectId);
        }
        securityDTO.setUserId(UserThreadLocalUtil.getUserInfo().getUserId());
        return ControllerResponse.success(headerFieldService.queryHeaderFields(securityDTO,category,isFilter));
    }
}

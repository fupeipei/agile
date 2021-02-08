package com.yusys.agile.headerfielduser.rest.impl;

import com.yusys.agile.headerfielduser.dto.HeaderFieldListDTO;
import com.yusys.agile.headerfielduser.service.HeaderFieldUserService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *   :
 * @Date: 2020/4/15
 */
@RestController
@RequestMapping("/headerFieldUser")
public class HeaderFieldUserController  {

    private static final Logger log = LoggerFactory.getLogger(HeaderFieldUserController.class);
    @Resource
    HeaderFieldUserService headerFieldUserService;

    /**
      *功能描述
      *
      * @date 2020/4/15
      * @param headerFieldListDTO
     * @param projectId
      * @return import com.yusys.portal.model.common.dto.ControllerResponse;
     */
    @PostMapping("/updateList")
    public ControllerResponse updateHeaderFieldUserList(@RequestBody HeaderFieldListDTO headerFieldListDTO, @RequestHeader(name = "projectId") Long  projectId) {
            return ControllerResponse.success(headerFieldUserService.updateHeaderFieldUserList(headerFieldListDTO,projectId));
    }
}

package com.yusys.agile.zentao.rest;


import com.github.pagehelper.PageInfo;
import com.yusys.agile.versionmanagerV3.SVersionIssueRelateDTO;
import com.yusys.agile.versionmanagerV3.SVersionManagerDTO;
import com.yusys.agile.versionmanagerv3.service.SVersionManagerV3Service;
import com.yusys.agile.zentao.domain.ZtStory;
import com.yusys.agile.zentao.dto.ZtStoryDTO;
import com.yusys.agile.zentao.service.ZenTaoStoryService;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/zentao")
public class ZenTaoController {


    @Resource
    private ZenTaoStoryService zenTaoStoryService;
    /**
     * 添加需求
     *
     * @return
     */
    @PostMapping("/addStory")
    public ControllerResponse addStory(@RequestBody ZtStoryDTO ztStoryDTO) {
        try {
            zenTaoStoryService.addStory(ztStoryDTO);
            return ControllerResponse.success();
        } catch (BusinessException bu) {
            return ControllerResponse.fail(bu.getMessage());
        } catch (Exception e) {
            return ControllerResponse.fail("添加需求" + e);
        }
    }

}

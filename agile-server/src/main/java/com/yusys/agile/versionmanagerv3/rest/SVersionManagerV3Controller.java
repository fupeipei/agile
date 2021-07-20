package com.yusys.agile.versionmanagerv3.rest;


import com.github.pagehelper.PageInfo;
import com.yusys.agile.versionmanagerV3.SVersionIssueRelateDTO;
import com.yusys.agile.versionmanagerV3.SVersionManagerDTO;
import com.yusys.agile.versionmanagerv3.service.SVersionManagerV3Service;
import com.yusys.portal.common.exception.BusinessException;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/versionmanager")
public class SVersionManagerV3Controller {


    @Autowired
    private SVersionManagerV3Service sVersionManagerV3Service;


    /**
     * 添加版本计划
     *
     * @return
     */
    @PostMapping("/saveVersionManager")
    public ControllerResponse saveVersionManager(@RequestBody SVersionManagerDTO sVersionManagerDTO) {
        try {
            this.checkVersionManagerParams(sVersionManagerDTO);
            sVersionManagerV3Service.saveVersionManager(sVersionManagerDTO);
            return ControllerResponse.success();
        } catch (BusinessException bu) {
            return ControllerResponse.fail(bu.getMessage());
        } catch (Exception e) {
            return ControllerResponse.fail("添加版本计划失败" + e);
        }
    }

    private void checkVersionManagerParams(SVersionManagerDTO sVersionManagerDTO) {
        List<Long> versionIssueRelateIds = sVersionManagerDTO.getVersionIssueRelateIds();
        Date releaseDate = sVersionManagerDTO.getReleaseDate();
        Date raiseTestDate = sVersionManagerDTO.getRaiseTestDate();
        String versionName = sVersionManagerDTO.getVersionName();
        if (CollectionUtils.isEmpty(versionIssueRelateIds)) {
            throw new BusinessException("请选择需要关联的需求");
        }
        if (StringUtils.isEmpty(versionName)) {
            throw new BusinessException("请选择发版计划名称");
        }
        if (releaseDate.before(raiseTestDate)) {
            throw new BusinessException("提测日期不能小于上线日期");
        }
    }


    @PostMapping("/updateVersionManager")
    public ControllerResponse updateVersionManager(@RequestBody SVersionManagerDTO sVersionManagerDTO) {
        try {
            this.checkVersionManagerParams(sVersionManagerDTO);
            sVersionManagerV3Service.updateVersionManager(sVersionManagerDTO);
            return ControllerResponse.success();
        } catch (BusinessException bu) {
            return ControllerResponse.fail(bu.getMessage());
        } catch (Exception e) {
            return ControllerResponse.fail("修改版本计划失败" + e);
        }
    }


    @PostMapping("/deleteVersionManager/{id}")
    public ControllerResponse deleteVersionManager(@PathVariable(name = "id") Long id) {
        sVersionManagerV3Service.deleteVersionManager(id);
        return ControllerResponse.success();
    }

    @GetMapping("/queryVersionManagerList")
    public ControllerResponse queryVersionManagerList(@RequestParam(name = "pageNum") Integer pageNum,
                                                      @RequestParam(name = "pageSize") Integer pageSize,
                                                      @RequestParam(name = "searchKey", required = false) String searchKey
    ) {
        PageInfo<SVersionManagerDTO> sVersionManagerDTOPageInfo = sVersionManagerV3Service.queryVersionManagerList(pageNum, pageSize, searchKey);
        return ControllerResponse.success(sVersionManagerDTOPageInfo);
    }

    @GetMapping("/queryRequirementRelList")
    public ControllerResponse queryRequirementRelList(@RequestParam(name = "pageNum") Integer pageNum,
                                                      @RequestParam(name = "pageSize") Integer pageSize,
                                                      @RequestParam(name = "searchKey", required = false) String searchKey,
                                                      @RequestParam(name = "teamId", required = false) Long teamId) {
        try {
            PageInfo<SVersionIssueRelateDTO> sVersionIssueRelateDTOPageInfo = sVersionManagerV3Service.queryRequirementRelList(pageNum, pageSize, searchKey, teamId);
            return ControllerResponse.success(sVersionIssueRelateDTOPageInfo);
        } catch (Exception e) {
            return ControllerResponse.fail("查询关联需求失败" + e);
        }
    }

}

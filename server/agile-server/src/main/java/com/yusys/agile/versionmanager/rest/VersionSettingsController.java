package com.yusys.agile.versionmanager.rest;

import com.yusys.agile.versionmanager.domain.VersionSettings;
import com.yusys.agile.versionmanager.service.VersionSettingsService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName VersionSettingsController
 * @Description 版本设置允许哪些工作项类型纳入版本
 * @Date 2020/8/19 16:19
 * @Version 1.0
 */
@RestController
@RequestMapping("/version/settings")
public class VersionSettingsController {

    @Autowired
    private VersionSettingsService versionSettingsService;

    /**
     * 获取版本管理纳入的工作项类型
     *
     * @param securityDTO
     * @return
     */
    @GetMapping("/pull")
    public ControllerResponse getVersionSettings(SecurityDTO securityDTO) {
        List<VersionSettings> list = versionSettingsService.getVersionSettings(securityDTO);
        return ControllerResponse.success(list);
    }

    /**
     * 保存/取消工作项类型纳入版本设置
     *
     * @param versionSettings
     * @param securityDTO
     * @return
     */
    @GetMapping("/push")
    public ControllerResponse pushVersionSettings(@RequestBody VersionSettings versionSettings, SecurityDTO securityDTO) {
        versionSettingsService.pushVersionSetting(versionSettings, securityDTO);
        return ControllerResponse.success("工作项允许纳入版本设置成功");
    }
}

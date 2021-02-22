package com.yusys.agile.versionmanager.service;

import com.yusys.agile.versionmanager.domain.VersionSettings;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;

/**
 * @ClassName VersionSettingsService
 * @Description TODO
 * @Date 2020/8/19 16:54
 * @Version 1.0
 */
public interface VersionSettingsService {
    /**
     * 根据项目ID获取工作项类型允许纳入版本设置
     *
     * @param securityDTO
     * @return
     */
    List<VersionSettings> getVersionSettings(SecurityDTO securityDTO);

    /**
     * 保存/取消工作项类型纳入版本设置
     *
     * @param versionSettings
     * @param securityDTO
     * @return
     */
    void pushVersionSetting(VersionSettings versionSettings, SecurityDTO securityDTO);
}

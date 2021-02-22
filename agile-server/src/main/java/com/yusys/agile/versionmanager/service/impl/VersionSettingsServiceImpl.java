package com.yusys.agile.versionmanager.service.impl;

import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.versionmanager.dao.VersionSettingsMapper;
import com.yusys.agile.versionmanager.domain.VersionSettings;
import com.yusys.agile.versionmanager.domain.VersionSettingsExample;
import com.yusys.agile.versionmanager.enums.VersionSettingEnum;
import com.yusys.agile.versionmanager.service.VersionSettingsService;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName VersionSettingsServiceImpl
 * @Description TODO
 * @Date 2020/8/19 17:12
 * @Version 1.0
 */
@Service
public class VersionSettingsServiceImpl implements VersionSettingsService {

    @Resource
    private VersionSettingsMapper settingsMapper;

    @Override
    public List<VersionSettings> getVersionSettings(SecurityDTO securityDTO) {
        VersionSettingsExample settingsExample = new VersionSettingsExample();
        settingsExample.createCriteria().andProjectIdEqualTo(securityDTO.getProjectId());
        List<VersionSettings> versionSettings = settingsMapper.selectByExample(settingsExample);
        //初始化枚举数据，如果不存在，插入数据库，并返回
        initVersionSettings(versionSettings, securityDTO);
        return versionSettings;
    }

    @Override
    public void pushVersionSetting(VersionSettings versionSettings, SecurityDTO securityDTO) {
        settingsMapper.updateByPrimaryKey(versionSettings);
    }

    private void initVersionSettings(List<VersionSettings> versionSettings, SecurityDTO securityDTO) {
        Long userId = securityDTO.getUserId();
        Long projectId = securityDTO.getProjectId();
        for (VersionSettingEnum settingEnum : VersionSettingEnum.values()) {
            byte issueType = settingEnum.getIssueType();
            VersionSettingsExample settingsExample = new VersionSettingsExample();
            settingsExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andIssueTypeEqualTo(issueType);
            List<VersionSettings> versionSettingsList = settingsMapper.selectByExample(settingsExample);
            if (CollectionUtils.isEmpty(versionSettingsList)) {
                VersionSettings settings = new VersionSettings();
                settings.setProjectId(projectId);
                settings.setIssueType(issueType);
                settings.setDeployType(NumberConstant.ZERO.byteValue());
                settings.setCreateName(securityDTO.getUserName());
                settingsMapper.insert(settings);
                versionSettings.add(settings);
            }
        }
    }
}

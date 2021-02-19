package com.yusys.agile.noticesettings.service;

import com.yusys.agile.noticesettings.dto.MailSwitchDTO;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;

/**
 * @ClassName: MailSwitchService
 * @Description: 通知设置Service
 * :
 * @CreateDate: 2020/06/16 17:28
 * @Version 1.0
 */
public interface MailSwitchService {
    /**
     * 添加或更新个人通知设置
     *
     * @param mailSwitchDTO
     * @param securityDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    ControllerResponse pushNoticeSettings(MailSwitchDTO mailSwitchDTO, SecurityDTO securityDTO);

    /**
     * 根据用户ID和项目编码查询邮箱设置
     *
     * @param securityDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    ControllerResponse pullNoticeSettings(SecurityDTO securityDTO);


}

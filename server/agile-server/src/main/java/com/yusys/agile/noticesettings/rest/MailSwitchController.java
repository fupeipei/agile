package com.yusys.agile.noticesettings.rest;

import com.yusys.agile.noticesettings.dto.MailSwitchDTO;
import com.yusys.agile.noticesettings.service.MailSwitchService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: MailSwitchController
 * @Description: 通知设置controller
 *   :
 * @CreateDate: 2020/06/16 17:28
 * @Version 1.0
 */
@RestController
@RequestMapping("/notice/settings")
public class MailSwitchController {
    @Autowired
    private MailSwitchService mailSwitchService;


    /**
     * 添加或更新个人通知设置
     * @param mailSwitchDTO
     * @param securityDTO
     * @return
     */
    @PostMapping("/push")
    public ControllerResponse pushNoticeSettings(@RequestBody MailSwitchDTO mailSwitchDTO, SecurityDTO securityDTO){
        return mailSwitchService.pushNoticeSettings(mailSwitchDTO, securityDTO);
    }


    /**
     * 根据用户ID和项目编码查询邮箱设置
     * @param securityDTO
     * @return
     */
    @GetMapping("/pull")
    public ControllerResponse pullNoticeSettings(SecurityDTO securityDTO){
        return mailSwitchService.pullNoticeSettings(securityDTO);
    }

}

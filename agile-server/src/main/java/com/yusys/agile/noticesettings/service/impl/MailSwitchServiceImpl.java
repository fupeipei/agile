package com.yusys.agile.noticesettings.service.impl;

import com.yusys.agile.noticesettings.MailSwitchEnum;
import com.yusys.agile.noticesettings.dao.MailSwitchMapper;
import com.yusys.agile.noticesettings.domain.MailSwitch;
import com.yusys.agile.noticesettings.domain.MailSwitchExample;
import com.yusys.agile.noticesettings.dto.MailSwitchDTO;
import com.yusys.agile.noticesettings.service.MailSwitchService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import com.yusys.portal.util.code.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: MailSwitchServiceImpl
 * @Description: 通知设置Service Impl
 * :
 * @CreateDate: 2020/06/16 17:28
 * @Version 1.0
 */
@Service
public class MailSwitchServiceImpl implements MailSwitchService {
    @Autowired
    private MailSwitchMapper mailSwitchMapper;

    @Override
    public ControllerResponse pushNoticeSettings(MailSwitchDTO mailSwitchDTO, SecurityDTO securityDTO) {
        MailSwitch mailSwitch = ReflectUtil.copyProperties(mailSwitchDTO, MailSwitch.class);
        mailSwitchMapper.updateByPrimaryKeySelective(mailSwitch);
        return ControllerResponse.success("通知设置成功!");
    }

    @Override
    public ControllerResponse pullNoticeSettings(SecurityDTO securityDTO) {
        Long useId = securityDTO.getUserId();
        Long projectId = securityDTO.getProjectId();
        MailSwitchExample example = new MailSwitchExample();
        example.createCriteria().andUserIdEqualTo(useId).andProjectIdEqualTo(projectId);
        List<MailSwitch> mailSwitches = mailSwitchMapper.selectByExample(example);

        //初始化枚举数据，如果不存在，插入数据库，并返回
        initNoticeSettings(mailSwitches, securityDTO);
        return ControllerResponse.success(mailSwitches);
    }

    private void initNoticeSettings(List<MailSwitch> mailSwitchList, SecurityDTO securityDTO) {
        Long userId = securityDTO.getUserId();
        Long projectId = securityDTO.getProjectId();
        for (MailSwitchEnum switchEnum : MailSwitchEnum.values()) {
            byte type = switchEnum.getType();
            byte mailType = switchEnum.getMailType();
            byte mailSwitch = switchEnum.getMailSwitch();
            String mailSwitchName = switchEnum.getMailSwitchName();
            byte subMailSwitch = switchEnum.getSubMailSwitch();
            String subMailSwitchName = switchEnum.getSubMailSwitchName();
            MailSwitchExample switchExample = new MailSwitchExample();
            switchExample.createCriteria().andUserIdEqualTo(userId)
                    .andProjectIdEqualTo(projectId)
                    .andMailTypeEqualTo(mailType);
            List<MailSwitch> mailSwitches = mailSwitchMapper.selectByExample(switchExample);
            if (CollectionUtils.isEmpty(mailSwitches)) {
                MailSwitch mailSwitchClass = new MailSwitch();
                mailSwitchClass.setUserId(userId);
                mailSwitchClass.setProjectId(projectId);
                mailSwitchClass.setType(type);
                mailSwitchClass.setMailType(mailType);
                mailSwitchClass.setMainSwitch(mailSwitch);
                mailSwitchClass.setMainSwitchName(mailSwitchName);
                mailSwitchClass.setSubSwitch(subMailSwitch);
                mailSwitchClass.setSubSwitchName(subMailSwitchName);
                mailSwitchMapper.insert(mailSwitchClass);
                mailSwitchList.add(mailSwitchClass);
            }
        }
    }
}

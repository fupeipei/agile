package com.yusys.agile.noticesettings.dao;

import com.yusys.agile.noticesettings.domain.MailSwitch;
import com.yusys.agile.noticesettings.domain.MailSwitchExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MailSwitchMapper {
    long countByExample(MailSwitchExample example);

    int deleteByExample(MailSwitchExample example);

    int deleteByPrimaryKey(Long mailId);

    int insert(MailSwitch record);

    int insertSelective(MailSwitch record);

    List<MailSwitch> selectByExample(MailSwitchExample example);

    MailSwitch selectByPrimaryKey(Long mailId);

    int updateByExampleSelective(@Param("record") MailSwitch record, @Param("example") MailSwitchExample example);

    int updateByExample(@Param("record") MailSwitch record, @Param("example") MailSwitchExample example);

    int updateByPrimaryKeySelective(MailSwitch record);

    int updateByPrimaryKey(MailSwitch record);

    List<MailSwitch> selectMailSwtchByMailType(@Param("mailType") Byte mailType);
}
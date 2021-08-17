package com.yusys.agile.noticesettings;


/**
 * @ClassName: MailSwitchServiceImpl
 * @Description: 邮件模版枚举
 * :
 * @CreateDate: 2020/06/16 17:28
 * @Version 1.0
 */

public enum MailSwitchEnum {
    /**
     * 通知设置业务类型
     */
    EPIC((byte) 1, (byte) 1, (byte) 0, "业务需求变更通知", (byte) 0, "仅接收与自身有关的邮件"),
    FEATURE((byte) 1, (byte) 2, (byte) 0, "研发需求变更通知", (byte) 0, "仅接收与自身有关的邮件"),
    STORY((byte) 1, (byte) 3, (byte) 0, "用户故事变更通知", (byte) 0, "仅接收与自身有关的邮件"),
    TASK((byte) 1, (byte) 4, (byte) 0, "任务变更通知", (byte) 0, "仅接收与自身有关的邮件"),
    BUG((byte) 1, (byte) 5, (byte) 0, "测试缺陷变更通知", (byte) 0, "仅接收与自身有关的邮件"),
    OVERDUE((byte) 2, (byte) 6, (byte) 0, "超期", (byte) 0, "仅接收与自身有关的邮件"),
    OVERTIME((byte) 2, (byte) 7, (byte) 0, "超时", (byte) 0, "仅接收与自身有关的邮件");

    /**
     * 设置类型：1邮件设置 2工作项每日提醒
     */
    private Byte type;
    /**
     * 邮件业务类型
     */
    private Byte mailType;

    /**
     * 操作是否启用，默认为0
     */
    private Byte mailSwitch;

    /**
     * 工作项变更名称
     */
    private String mailSwitchName;

    /**
     * 子操作是否启用，默认为0
     */
    private Byte subMailSwitch;

    /**
     * 工作项变更名称
     */
    private String subMailSwitchName;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getMailType() {
        return mailType;
    }

    public void setMailType(Byte mailType) {
        this.mailType = mailType;
    }

    public Byte getMailSwitch() {
        return mailSwitch;
    }

    public void setMailSwitch(Byte mailSwitch) {
        this.mailSwitch = mailSwitch;
    }

    public String getMailSwitchName() {
        return mailSwitchName;
    }

    public void setMailSwitchName(String mailSwitchName) {
        this.mailSwitchName = mailSwitchName;
    }

    public Byte getSubMailSwitch() {
        return subMailSwitch;
    }

    public void setSubMailSwitch(Byte subMailSwitch) {
        this.subMailSwitch = subMailSwitch;
    }

    public String getSubMailSwitchName() {
        return subMailSwitchName;
    }

    public void setSubMailSwitchName(String subMailSwitchName) {
        this.subMailSwitchName = subMailSwitchName;
    }

    MailSwitchEnum(Byte type, Byte mailType, Byte mailSwitch, String mailSwitchName, Byte subMailSwitch, String subMailSwitchName) {
        this.type = type;
        this.mailType = mailType;
        this.mailSwitch = mailSwitch;
        this.mailSwitchName = mailSwitchName;
        this.subMailSwitch = subMailSwitch;
        this.subMailSwitchName = subMailSwitchName;
    }


    // 普通方法
    public static MailSwitchEnum getByCode(String code) {
        for (MailSwitchEnum switchEnum : MailSwitchEnum.values()) {
            if (switchEnum.mailType.toString().equals(code)) {
                return switchEnum;
            }
        }
        return null;
    }
}

package com.yusys.agile.versionmanager.enums;


/**
 * @ClassName: MailSwitchServiceImpl
 * @Description: 工作项类型允许纳入版本管理枚举类
 * :
 * @CreateDate: 2020/08/19 17:28
 * @Version 1.0
 */

public enum VersionSettingEnum {
    /**
     * 工作项类型
     */
    EPIC((byte) 1, "业务需求变"),
    FEATURE((byte) 2, "研发需求"),
    STORY((byte) 3, "用户故事"),
    TASK((byte) 4, "任务"),
    BUG((byte) 5, "测试缺陷");

    /**
     * 工作项类型
     */
    private Byte issueType;
    /**
     * 名称
     */
    private String name;

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private VersionSettingEnum(Byte issueType, String name) {
        this.issueType = issueType;
        this.name = name;
    }

    // 普通方法
    public static VersionSettingEnum getByCode(String code) {
        for (VersionSettingEnum switchEnum : VersionSettingEnum.values()) {
            if (switchEnum.issueType.equals(code)) {
                return switchEnum;
            }
        }
        return null;
    }
}

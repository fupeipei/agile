package com.yusys.agile.versionmanager.enums;

/**
 * @description 同步系统枚举
 *  
 * @date 2020/09/23
 */
public enum SyncSystemEnum {
    ITC((byte)1, "itc"),
    CMP((byte)2, "cmp");

    private Byte systemId;
    private String systemName;

    SyncSystemEnum(Byte systemId, String systemName) {
        this.systemId = systemId;
        this.systemName = systemName;
    }

    public Byte getSystemId() {
        return systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public static String getSystemName(Byte systemId){
        String systemName = null;
        for (SyncSystemEnum syncSystemEnum : SyncSystemEnum.values()){
            if (syncSystemEnum.systemId.equals(systemId)) {
                systemName = syncSystemEnum.systemName;
            }
        }
        return systemName;
    }
}

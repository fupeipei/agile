package com.yusys.agile.versionmanager.enums;

public enum SyncResultEnum {

    UNSYNCHRONIZE((byte)-1, "未同步"),
    SYNCHRONIZE_FAILED((byte)0, "同步失败"),
    SYNCHRONIZED_SUCCESS((byte)1, "同步成功");

    private Byte id;
    private String name;

    private SyncResultEnum(Byte id, String name) {
        this.id = id;
        this.name = name;
    }

    public Byte getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static String getName(Byte id) {
        String name = null;
        for (SyncResultEnum syncResultEnum : SyncResultEnum.values()) {
            if (syncResultEnum.getId().equals(id)) {
                name = syncResultEnum.name;
            }
        }
        return name;
    }
}

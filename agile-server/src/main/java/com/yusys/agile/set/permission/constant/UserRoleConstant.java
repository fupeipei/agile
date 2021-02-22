package com.yusys.agile.set.permission.constant;

/**
 * @description 用户角色常量类
 * @date 2020/05/20
 */
public class UserRoleConstant {

    public enum UserRoleEnum {
        PROJECT_ROLE(1, "项目级角色"), APPLICATION_ROLE(3, "应用级角色"), PLATFORM_ROLE(4, "平台级角色");

        private Integer code;
        private String name;

        UserRoleEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}

package com.yusys.agile.fault.dto;

/**
 * 用户信息封装DTO
 *
 *
 * @create 2020-04-14 14:01
 */
public class UserDTO {

    private Long userId;

    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
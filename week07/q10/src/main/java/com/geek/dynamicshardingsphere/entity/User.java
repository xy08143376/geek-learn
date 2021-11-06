package com.geek.dynamicshardingsphere.entity;

public class User {
    /**
    * 自增主键
    */
    private Long userId;

    /**
    * 用户名
    */
    private String username;

    /**
    * 手机号
    */
    private String phone;

    /**
    * 用户密码
    */
    private String pwd;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
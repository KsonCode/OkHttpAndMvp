package com.example.kson.okhttpandmvp.bean;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/09
 * Description:登录
 */
public class LoginBean {
    public String msg;
    public String code;
    public User data;

    public class User{
        public String nickname;
        public String icon;
        public String mobile;

    }
}

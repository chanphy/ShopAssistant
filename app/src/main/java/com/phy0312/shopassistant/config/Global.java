package com.phy0312.shopassistant.config;

import com.phy0312.shopassistant.model.UserInfo;

/**
 * 程序启动后保存的一些状态
 * Created by dingdj on 2015/1/3.
 */
public class Global {
    /**
     * 用户信息
     */
    public static UserInfo userInfo;

    /**
     * 用户是否已登录
     * @return
     */
    public static boolean hasLogin(){
        return userInfo != null;
    }
}

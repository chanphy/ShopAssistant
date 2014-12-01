package com.phy0312.shopassistant.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * description: 应用主要的SP<br/>
 * author: dingdj<br/>
 * date: 2014/11/24<br/>
 */
public class MainSp {

    private static final String SP_NAME = "main_sp";

    private static final String CONFIG_KEY_FIRST_USE = "config_key_first_use";

    private static final String CONFIG_KEY_COOKIE = "config_key_cookie";

    private SharedPreferences sp;

    private boolean isFirstUse;
    private String cookie;

    public MainSp(Context mContext) {
        sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        isFirstUse = sp.getBoolean(CONFIG_KEY_FIRST_USE, true);
        cookie = sp.getString(CONFIG_KEY_COOKIE, "");
    }

    public boolean isFirstUse() {
        return isFirstUse;
    }

    public void setFirstUse(boolean isFirstUse) {
        this.isFirstUse = isFirstUse;
        sp.edit().putBoolean(CONFIG_KEY_FIRST_USE, isFirstUse).commit();
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
        sp.edit().putString(CONFIG_KEY_COOKIE, cookie);
    }
}

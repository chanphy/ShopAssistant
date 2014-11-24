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

    private SharedPreferences sp;

    private boolean isFirstUse;

    public MainSp(Context mContext) {
        sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        isFirstUse = sp.getBoolean(CONFIG_KEY_FIRST_USE, true);
    }

    public boolean isFirstUse() {
        return isFirstUse;
    }

    public void setFirstUse(boolean isFirstUse) {
        this.isFirstUse = isFirstUse;
        sp.edit().putBoolean(CONFIG_KEY_FIRST_USE, isFirstUse).commit();
    }
}

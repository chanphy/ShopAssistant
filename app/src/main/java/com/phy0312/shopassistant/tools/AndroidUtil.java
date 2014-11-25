package com.phy0312.shopassistant.tools;

import android.content.Context;
import android.content.Intent;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/25<br/>
 */
public class AndroidUtil {


    /**
     * 安全启动一个Activity
     * @param ctx
     * @param intent
     */
    public static void startActivity(Context ctx, Intent intent) {
        if (intent == null) return;
        try {
            ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

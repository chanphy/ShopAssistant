package com.phy0312.shopassistant.tools;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.config.Global;
import com.phy0312.shopassistant.model.UserInfo;
import com.phy0312.shopassistant.net.JsonCookieSupportRequest;
import com.phy0312.shopassistant.net.RequestResponseDataParseUtil;
import com.phy0312.shopassistant.net.URLManager;

import org.json.JSONObject;

/**
 * 用户登录管理
 * Created by dingdj on 2015/1/3.
 */
public class LoginUtil {

    public static void tryLogin(){
        try{
            JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.POST, URLManager.USER_AUTO_LOGIN, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            new RequestResponseDataParseUtil.ResponseParse() {
                                @Override
                                public void parseResponseDataSection(JSONObject dataJsonObject) {//登录成功处理
                                    String userName = dataJsonObject.optString("userName");
                                    String userId = dataJsonObject.optString("userId");
                                    UserInfo userInfo = new UserInfo();
                                    userInfo.setUserId(userId);
                                    userInfo.setUserName(userName);
                                    Global.userInfo = userInfo;
                                }
                            }.onResponse(jsonObject);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
            MainApplication.appContext.getRequestQueue().add(request);
        }catch(Exception e) {
            e.printStackTrace();
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("1");
        userInfo.setUserName("dingdongjin");
        Global.userInfo = userInfo;
    }
}

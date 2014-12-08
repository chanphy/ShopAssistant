package com.phy0312.shopassistant.net;

import com.phy0312.shopassistant.MainApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/12/1<br/>
 */
public class URLManager {

    public static String DEAL_URL = "http://lite.m.dianping.com/Fy6ffEz88j?uid=com_phy0312_shopassistant&longitude=119.320102&latitude=26.087019&" +
            "hasheader=1&category="+ utf8Encode("美食");

    public static String USER_LOGIN = "http://IP/action.ashx/commonaction/";
    public static String FOOD_STORE_LIST = "http://IP/action.ashx/commonaction/";
    public static String COUPON_LIST = "http://IP/action.ashx/commonaction/";
    public static String ACTIVITY_LIST = "http://IP/action.ashx/commonaction/";
    public static String PRODUCT_LIST = "http://IP/action.ashx/commonaction/";


    public static String utf8Encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDealUrl() {
        if(MainApplication.appContext.curLatLng == null) {
            return DEAL_URL;
        }else{
            double latitude = MainApplication.appContext.curLatLng[0];
            double longtitude = MainApplication.appContext.curLatLng[1];
            return "http://lite.m.dianping.com/Fy6ffEz88j?uid=com_phy0312_shopassistant&longitude="+longtitude+"&latitude="+latitude+"&" +
                    "hasheader=1&category="+ utf8Encode("美食");
        }
    }
}

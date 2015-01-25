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

    //服务端接口地址
    public static String ADS_URL = "http://42.121.28.162:8099/Admin/PhoneService/Commonaction/2";

    public static String MAIN_FRAME_URL = "http://42.121.28.162:8099/Admin/PhoneService/Commonaction/4";

    public static String ACTIVITY_LIST = "http://42.121.28.162:8099/Admin/PhoneService/Commonaction/5";

    public static String ACTIVITY_DETAIL_LIST = "http://42.121.28.162:8099/Admin/PhoneService/Commonaction/6";

    public static String COUPON_LIST = "http://42.121.28.162:8099/Admin/PhoneService/Commonaction/7";

    public static String COUPON_DETAIL_LIST = "http://42.121.28.162:8099/Admin/PhoneService/Commonaction/8";

    public static String SHOP_LIST = "http://42.121.28.162:8099/Admin/PhoneService/Commonaction/9";

    public static String DEAL_URL = "http://lite.m.dianping.com/Fy6ffEz88j?uid=com_phy0312_shopassistant&longitude=119.320102&latitude=26.087019&" +
            "hasheader=1&category="+ utf8Encode("美食");

    public static String USER_LOGIN = "http://IP/action.ashx/commonaction/";
    public static String USER_AUTO_LOGIN = "http://IP/action.ashx/commonaction/";
    public static String FOOD_STORE_LIST = "http://IP/action.ashx/commonaction/";
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

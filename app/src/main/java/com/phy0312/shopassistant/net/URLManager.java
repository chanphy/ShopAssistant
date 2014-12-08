package com.phy0312.shopassistant.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/12/1<br/>
 */
public class URLManager {

    public static String DEAL_URL = "http://lite.m.dianping.com/Fy6ffEz88j?uid=com_phy0312_shopassistant&longitude=119.280&latitude=26.080&" +
            "hasheader=1&category="+ utf8Encode("美食");

    public static String USER_LOGIN = "http://IP/action.ashx/commonaction/";
    public static String FOOD_STORE_LIST = "http://IP/action.ashx/commonaction/";
    public static String COUPON_LIST = "http://IP/action.ashx/commonaction/";
    public static String ACTIVITY_LIST = "http://IP/action.ashx/commonaction/";
    public static String PRODUCT_LIST = "http://IP/action.ashx/commonaction/";


    public static String utf8Encode(String str) {
        try {
            return URLEncoder.encode("美食", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

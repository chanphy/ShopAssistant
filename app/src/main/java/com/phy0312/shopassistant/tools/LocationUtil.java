package com.phy0312.shopassistant.tools;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by dingdj on 2014/12/6.
 */
public class LocationUtil {

    /**
     * 启动定位配置
     * @author dingdj
     * Date:2013-11-8下午3:34:06
     */
    public static void setLocationOption(LocationClient mLocClient){
        if(mLocClient == null) return;
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("gcj02");
        option.setOpenGps(false);
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setScanSpan(800); //设置为app主动请求定位
        option.setProdName("com.phy0312.shopassistant");
        mLocClient.setLocOption(option);
    }
}

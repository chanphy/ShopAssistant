package com.phy0312.shopassistant;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.phy0312.shopassistant.tools.CrashHandler;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/24<br/>
 */
public class MainApplication extends Application {

    private RequestQueue requestQueue;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    public static MainApplication appContext;

    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance(this);
        initImageLoader(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        appContext = this;
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }


    /**
     * 监听函数，得到当前位置信息 并通知界面进行刷新
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

        }
    }


}

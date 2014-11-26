package com.phy0312.shopassistant.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/25<br/>
 */
public class AndroidUtil {

    public static final String TAG = AndroidUtil.class.getSimpleName();

    public static final String MAILTO_EMAIL = "mailto:softtestddj@163.com";

    /**
     * 安全启动一个Activity
     *
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

    /**
     * 获取versionName
     *
     * @param context
     * @param packageName
     * @return String
     */
    public static String getVersionName(Context context, String packageName) {
        String versionName = "";
        try {
            PackageInfo packageinfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
            versionName = packageinfo.versionName;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return versionName;
    }


    /**
     * 获取手机信息
     *
     * @param ctx
     * @return 手机信息
     */
    public static String getPhoneInfo(Context ctx) {
        String result = "";
        try {
            result = "Phone=" + android.os.Build.MODEL + "\n";
            result += "CPU=" + getCPUABI() + "\n";
            result += "Resolution=" + getScreenResolution(ctx) + "\n";
            result += "FirmwareVersion=" + getFirmWareVersion() + "\n";
        } catch (Exception e) {
            return "";
        }

        return result;
    }


    /**
     * 获取CPU_ABI
     *
     * @return String
     */
    public static String getCPUABI() {
        String abi = Build.CPU_ABI;
        abi = (abi == null || abi.trim().length() == 0) ? "" : abi;
        // 检视是否有第二类型，1.6没有这个字段
        try {
            String cpuAbi2 = Build.class.getField("CPU_ABI2").get(null).toString();
            cpuAbi2 = (cpuAbi2 == null || cpuAbi2.trim().length() == 0) ? null : cpuAbi2;
            if (cpuAbi2 != null) {
                abi = abi + "," + cpuAbi2;
            }
        } catch (Exception e) {
        }
        return abi;
    }

    /**
     * 返回屏幕分辨率,字符串型。如 320x480
     *
     * @param ctx
     * @return String
     */
    public static String getScreenResolution(Context ctx) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) ctx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        String resolution = width + "x" + height;
        return resolution;
    }


    public static String getFirmWareVersion() {
        final String version_3 = "1.5";
        final String version_4 = "1.6";
        final String version_5 = "2.0";
        final String version_6 = "2.0.1";
        final String version_7 = "2.1";
        final String version_8 = "2.2";
        final String version_9 = "2.3";
        final String version_10 = "2.3.3";
        final String version_11 = "3.0";
        final String version_12 = "3.1";
        final String version_13 = "3.2";
        final String version_14 = "4.0";
        final String version_15 = "4.0.3";
        final String version_16 = "4.1.1";
        final String version_17 = "4.2";
        final String version_18 = "4.3";
        final String version_19 = "4.4";
        String versionName = "";
        try {
            // android.os.Build.VERSION.SDK_INT Since: API Level 4
            // int version = android.os.Build.VERSION.SDK_INT;
            int version = Integer.parseInt(android.os.Build.VERSION.SDK);
            switch (version) {
                case 3:
                    versionName = version_3;
                    break;
                case 4:
                    versionName = version_4;
                    break;
                case 5:
                    versionName = version_5;
                    break;
                case 6:
                    versionName = version_6;
                    break;
                case 7:
                    versionName = version_7;
                    break;
                case 8:
                    versionName = version_8;
                    break;
                case 9:
                    versionName = version_9;
                    break;
                case 10:
                    ;
                    versionName = version_10;
                    break;
                case 11:
                    versionName = version_11;
                    break;
                case 12:
                    versionName = version_12;
                    break;
                case 13:
                    versionName = version_13;
                    break;
                case 14:
                    versionName = version_14;
                    break;
                case 15:
                    versionName = version_15;
                    break;
                case 16:
                    versionName = version_16;
                    break;
                case 17:
                    versionName = version_17;
                    break;
                case 18:
                    versionName = version_18;
                    break;
                case 19:
                    versionName = version_19;
                    break;
                default:
                    versionName = version_7;
            }
        } catch (Exception e) {
            versionName = version_7;
        }
        return versionName;
    }

}

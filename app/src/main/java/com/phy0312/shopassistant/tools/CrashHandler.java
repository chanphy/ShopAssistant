package com.phy0312.shopassistant.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.WindowManager;

import com.phy0312.shopassistant.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/26<br/>
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = CrashHandler.class.getSimpleName();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    @SuppressWarnings("all")
    private HandlerThread thread;
    private Handler handler;

    private static CrashHandler INSTANCE;

    private CrashHandler(Context context) {
        this.mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        thread = new HandlerThread("damon");
        thread.start();
        handler = new Handler(thread.getLooper());
    }

    public static CrashHandler getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler(context);
        }
        return INSTANCE;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }


    /**
     * 自定义错误处理
     */
    private boolean handleException(final Throwable ex) {
        Log.e(TAG, "handleException");

        if (ex == null) {
            return false;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        String message = mContext.getString(R.string.alert_error);
                        builder.setMessage(message);
                        builder.setTitle(mContext.getString(R.string.alert_error_title));
                        builder.setPositiveButton(mContext.getString(R.string.send_cofirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendCrashReport(ex);
                                exit(1);
                            }
                        });
                        builder.setNegativeButton(mContext.getString(R.string.common_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                exit(1);
                            }
                        });
                        Dialog dialog = builder.create();
                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        dialog.show();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return true;
    }

    /**
     * 退出进程
     */
    private void exit(int code) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(code);
    }


    /**
     * 获取异常信息
     */
    private String getThrowableMessage(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }


    private void sendCrashReport(Throwable ex) {
        String result = getThrowableMessage(ex);
        final String versionName = AndroidUtil.getVersionName(mContext, mContext.getPackageName());
        final String content = result + "\n\n\n" + AndroidUtil.getPhoneInfo(mContext);
        final Uri uri = Uri.parse(AndroidUtil.MAILTO_EMAIL);
        Intent in = new Intent(Intent.ACTION_SENDTO, uri);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.bug_feedback_title, versionName));
        in.putExtra(Intent.EXTRA_TEXT, content + "\n" + StringUtils.parseLongToKbOrMb(content.length(), 3));
        try {
            mContext.startActivity(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
